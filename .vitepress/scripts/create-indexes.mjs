#!/usr/bin/env node

import { existsSync, readFileSync, unlinkSync, writeFileSync } from "node:fs";
import { basename, join, relative, resolve } from "node:path";
import { fileURLToPath } from "node:url";
import glob from "fast-glob";
import matter from "gray-matter";
import { getLanguageCodes } from "../utils/config/project-config.ts";
import { getSrcPath } from "../utils/config/path-resolver.js";

const SIDEBAR_CONFIG_FILE = "sidebarIndex.md";
const DIRECTORY_DESCRIPTION_FILE = "Description.md";
const LEGACY_INDEX_FILE = "index.md";

class IndexGenerator {
    constructor() {
        this.processedDirs = 0;
        this.skippedDirs = 0;
        this.errorDirs = 0;
        this.srcPath = getSrcPath();
        this.languages = getLanguageCodes();
    }

    parseArgs(args) {
        const config = {
            path: null,
            template: "default",
            dryRun: false,
            verbose: false,
            force: false,
            keepLegacyIndex: false,
            exclude: ["node_modules", ".git", ".vitepress", "public"],
        };

        for (let i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--path":
                case "-p":
                    config.path = args[++i];
                    break;
                case "--template":
                case "-t":
                    config.template = args[++i];
                    break;
                case "--dry-run":
                case "-d":
                    config.dryRun = true;
                    break;
                case "--verbose":
                case "-v":
                    config.verbose = true;
                    break;
                case "--force":
                case "-f":
                    config.force = true;
                    break;
                case "--keep-legacy-index":
                    config.keepLegacyIndex = true;
                    break;
                case "--exclude":
                    config.exclude.push(args[++i]);
                    break;
                case "--help":
                case "-h":
                    this.showHelp();
                    process.exit(0);
                    break;
                default:
                    break;
            }
        }

        return config;
    }

    showHelp() {
        console.log(`
📁 Sidebar Scaffolding Generator

USAGE:
  npm run index -- [options]

REQUIRED:
  -p, --path <path>            Target directory path (relative to docs root)

OPTIONS:
  -t, --template <type>        Template type (default/advanced) [default: default]
  -d, --dry-run                Preview changes without writing files
  -v, --verbose                Show detailed information
  -f, --force                  Overwrite existing sidebarIndex.md and Description.md
      --keep-legacy-index      Keep legacy index.md files after migration
      --exclude <pattern>      Exclude directories matching pattern
  -h, --help                   Show this help

GENERATED FILES:
  - sidebarIndex.md            Sidebar/frontmatter configuration file
  - Description.md             Directory landing content page (non-empty)

SUPPORTED LANGUAGES:
  ${this.languages.join(", ")}

EXAMPLES:
  npm run index -- -p zh
  npm run index -- -p en --template advanced --dry-run
  npm run index -- -p zh/guides --force --verbose
  npm run index -- -p zh --exclude "temp" --exclude "draft"
  npm run index -- -p en --keep-legacy-index

SOURCE DIRECTORY: ${this.srcPath}
        `);
    }

    validateConfig(config) {
        if (!config.path) {
            console.error("❌ Error: --path is required");
            console.log(`Available languages: ${this.languages.join(", ")}`);
            return false;
        }

        const fullPath = resolve(this.srcPath, config.path);
        if (!existsSync(fullPath)) {
            console.error(
                `❌ Error: Path "${config.path}" does not exist in ${this.srcPath}`,
            );
            console.log(`Available languages: ${this.languages.join(", ")}`);
            return false;
        }

        if (!["default", "advanced"].includes(config.template)) {
            console.error('❌ Error: Template must be "default" or "advanced"');
            return false;
        }

        return true;
    }

    formatTitle(dirName) {
        return dirName
            .replace(/[-_]/g, " ")
            .replace(/\b\w/g, (value) => value.toUpperCase())
            .trim();
    }

    createSidebarTemplate(title, templateType) {
        const frontmatter =
            templateType === "advanced"
                ? {
                      title,
                      collapsed: true,
                      hidden: false,
                      priority: Number.MAX_SAFE_INTEGER,
                  }
                : { title };

        return matter.stringify(
            [
                `This file stores sidebar metadata for the \`${title}\` directory.`,
                "Edit frontmatter keys here to control sidebar behavior.",
            ].join("\n\n"),
            frontmatter,
        );
    }

    createDescriptionTemplate(title, templateType) {
        const lines = [
            `# ${title}`,
            "",
            `This page provides an overview for the **${title}** directory.`,
            "",
            "Use this page to introduce structure, scope, and recommended reading order.",
        ];

        if (templateType === "advanced") {
            lines.push(
                "",
                "## Scope",
                "",
                "Describe the boundaries and intended audience for this section.",
                "",
                "## Reading Guide",
                "",
                "- Start with foundational concepts.",
                "- Continue with implementation examples.",
                "- Finish with operational references.",
            );
        }

        return matter.stringify(`${lines.join("\n")}\n`, { title });
    }

    extractLegacyContents(legacyIndexPath, title, templateType) {
        if (!existsSync(legacyIndexPath)) {
            return {
                sidebar: this.createSidebarTemplate(title, templateType),
                description: this.createDescriptionTemplate(title, templateType),
            };
        }

        const legacyRaw = readFileSync(legacyIndexPath, "utf8");
        const parsed = matter(legacyRaw);
        const descriptionTitle = parsed.data?.title || title;
        const descriptionBody = parsed.content?.trim();

        const sidebar = matter.stringify(
            "This file stores sidebar metadata migrated from legacy index.md.",
            parsed.data || { title },
        );

        const description = matter.stringify(
            `${descriptionBody || this.createDescriptionTemplate(descriptionTitle, templateType).replace(/^---[\s\S]*?---\n?/, "")}\n`,
            { title: descriptionTitle },
        );

        return { sidebar, description };
    }

    async findDirectoriesNeedingScaffolding(config) {
        try {
            const basePath = resolve(this.srcPath, config.path);
            const patterns = [`${basePath}/**/`];
            const excludePatterns = config.exclude.map((pattern) =>
                pattern.startsWith("**/") ? pattern : `**/${pattern}/**`,
            );

            const allDirs = await glob(patterns, {
                ignore: excludePatterns,
                onlyDirectories: true,
                absolute: true,
            });

            const dirsNeedingScaffolding = [];
            for (const dir of allDirs) {
                const sidebarPath = join(dir, SIDEBAR_CONFIG_FILE);
                const descriptionPath = join(dir, DIRECTORY_DESCRIPTION_FILE);
                const legacyIndexPath = join(dir, LEGACY_INDEX_FILE);

                const markdownFiles = await glob("*.md", {
                    cwd: dir,
                    ignore: [SIDEBAR_CONFIG_FILE, DIRECTORY_DESCRIPTION_FILE],
                });
                const subdirs = await glob("*/", { cwd: dir });

                if (markdownFiles.length === 0 && subdirs.length === 0) {
                    continue;
                }

                const needsSidebar = !existsSync(sidebarPath) || config.force;
                const needsDescription = !existsSync(descriptionPath) || config.force;
                const hasLegacyIndex = existsSync(legacyIndexPath);
                const needsLegacyMigration =
                    hasLegacyIndex && (!config.keepLegacyIndex || config.force);

                if (needsSidebar || needsDescription || needsLegacyMigration) {
                    dirsNeedingScaffolding.push({
                        path: dir,
                        sidebarPath,
                        descriptionPath,
                        legacyIndexPath,
                        hasLegacyIndex,
                        needsSidebar,
                        needsDescription,
                        relativePath: relative(basePath, dir) || ".",
                        dirName: basename(dir),
                    });
                }
            }

            return dirsNeedingScaffolding;
        } catch (error) {
            console.error("❌ Error finding directories:", error.message);
            return [];
        }
    }

    createScaffoldingFiles(dirInfo, template, dryRun = false, keepLegacyIndex = false) {
        const title = this.formatTitle(dirInfo.dirName);
        const { sidebar, description } = this.extractLegacyContents(
            dirInfo.legacyIndexPath,
            title,
            template,
        );

        if (dryRun) {
            if (dirInfo.needsSidebar) {
                console.log(`📝 Would write: ${dirInfo.relativePath}/${SIDEBAR_CONFIG_FILE}`);
            }
            if (dirInfo.needsDescription) {
                console.log(
                    `📝 Would write: ${dirInfo.relativePath}/${DIRECTORY_DESCRIPTION_FILE}`,
                );
            }
            if (dirInfo.hasLegacyIndex && !keepLegacyIndex) {
                console.log(`🧹 Would remove: ${dirInfo.relativePath}/${LEGACY_INDEX_FILE}`);
            }
            return true;
        }

        try {
            if (dirInfo.needsSidebar) {
                writeFileSync(dirInfo.sidebarPath, sidebar, "utf8");
            }

            if (dirInfo.needsDescription) {
                writeFileSync(dirInfo.descriptionPath, description, "utf8");
            }

            if (dirInfo.hasLegacyIndex && !keepLegacyIndex) {
                const maybeRootIndex =
                    dirInfo.legacyIndexPath === resolve(this.srcPath, LEGACY_INDEX_FILE) ||
                    dirInfo.legacyIndexPath === resolve(this.srcPath, "en", LEGACY_INDEX_FILE) ||
                    dirInfo.legacyIndexPath === resolve(this.srcPath, "zh", LEGACY_INDEX_FILE);
                if (!maybeRootIndex) {
                    unlinkSync(dirInfo.legacyIndexPath);
                }
            }

            return true;
        } catch (error) {
            console.error(
                `❌ Error writing scaffolding for ${dirInfo.relativePath}:`,
                error.message,
            );
            return false;
        }
    }

    async processDirectories(config) {
        console.log("🔍 Finding directories that need sidebar scaffolding...");
        console.log(`📂 Source directory: ${this.srcPath}`);
        console.log(`🎯 Target path: ${config.path}`);

        const directories = await this.findDirectoriesNeedingScaffolding(config);
        if (directories.length === 0) {
            console.log(
                "✅ No directories require updates. sidebarIndex.md and Description.md are already in place.",
            );
            return;
        }

        console.log(
            `\n📋 Found ${directories.length} directories requiring scaffolding updates:`,
        );

        for (const dirInfo of directories) {
            const success = this.createScaffoldingFiles(
                dirInfo,
                config.template,
                config.dryRun,
                config.keepLegacyIndex,
            );

            if (success) {
                if (config.dryRun) {
                    this.skippedDirs++;
                } else {
                    this.processedDirs++;
                    if (config.verbose) {
                        console.log(`✅ Updated: ${dirInfo.relativePath}`);
                    }
                }
            } else {
                this.errorDirs++;
            }
        }

        console.log("\n📊 Summary:");
        if (config.dryRun) {
            console.log(`   📋 Would update: ${this.skippedDirs} directories`);
        } else {
            console.log(`   ✅ Updated: ${this.processedDirs} directories`);
            console.log(`   ❌ Errors: ${this.errorDirs} directories`);
        }
    }

    async run() {
        const args = process.argv.slice(2);
        const config = this.parseArgs(args);

        if (!this.validateConfig(config)) {
            process.exit(1);
        }

        try {
            await this.processDirectories(config);
        } catch (error) {
            console.error("❌ Unexpected error:", error.message);
            process.exit(1);
        }
    }
}

if (process.argv[1] === fileURLToPath(import.meta.url)) {
    const generator = new IndexGenerator();
    generator.run();
}

export default IndexGenerator;
