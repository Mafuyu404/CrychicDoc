import type { DefaultTheme, HeadConfig, UserConfig } from "vitepress";
import type { Plugin } from "vite";
import { execFileSync } from "node:child_process";
import { existsSync } from "node:fs";
import { resolve } from "path";
import { fileURLToPath } from "url";
import {
    getProjectInfo,
    isFeatureEnabled,
    getPaths,
    getLanguageLinks,
    autoDiscoverLanguageModules,
    projectConfig,
} from "../utils/config/project-config";
import { templateCompilerOptions } from "@tresjs/core";
import llmstxt from "vitepress-plugin-llms";

import { sidebarPlugin } from "../utils/sidebar/plugin";
import { markdown } from "./markdown-plugins";
import {
    groupIconVitePlugin,
    localIconLoader,
} from "vitepress-plugin-group-icons";
import { m1honoTemplateDerivedDocsSyncPlugin } from "../utils/vitepress/dev/m1honoTemplateDerivedDocsSyncPlugin";

const projectInfo = getProjectInfo();
const projectPaths = getPaths();
const shouldForceOptimizeDeps =
    process.env.M1HONO_FORCE_OPTIMIZE_DEPS === "1" ||
    process.env.M1HONO_FORCE_OPTIMIZE_DEPS === "true";
const shouldDebugTemplateWatchers =
    process.env.M1HONO_DEBUG_WATCHERS === "1";
const shouldForceGitChangelog =
    process.env.M1HONO_FORCE_GIT_CHANGELOG === "1" ||
    process.env.M1HONO_FORCE_GIT_CHANGELOG === "true";
const shouldDisableGitChangelog =
    process.env.M1HONO_DISABLE_GIT_CHANGELOG === "1" ||
    process.env.M1HONO_DISABLE_GIT_CHANGELOG === "true";
const currentNodeMajorVersion = Number.parseInt(
    process.versions.node.split(".")[0] ?? "0",
    10,
);

type M1honoViteWatchMode = "auto" | "native" | "poll";

function resolveViteCacheMode(): "build" | "dev" {
    const explicitMode = process.env.M1HONO_VITE_CACHE_MODE?.trim();
    if (explicitMode === "build" || explicitMode === "dev") {
        return explicitMode;
    }

    for (const arg of process.argv) {
        if (arg === "build") {
            return "build";
        }

        if (arg === "dev" || arg === "serve") {
            return "dev";
        }
    }

    return process.env.NODE_ENV === "production" ? "build" : "dev";
}

function resolveViteCacheDir() {
    const explicitCacheDir = process.env.M1HONO_VITE_CACHE_DIR?.trim();
    if (explicitCacheDir) {
        return explicitCacheDir;
    }

    // Keep the dev-server dep optimizer isolated from one-off build runs.
    return resolve(
        projectPaths.root,
        projectPaths.cache,
        resolveViteCacheMode(),
    );
}

function resolveViteWatchMode(): M1honoViteWatchMode {
    const explicitWatchMode = process.env.M1HONO_VITE_WATCH_MODE?.trim().toLowerCase();
    if (
        explicitWatchMode === "auto" ||
        explicitWatchMode === "native" ||
        explicitWatchMode === "poll"
    ) {
        return explicitWatchMode;
    }

    return "auto";
}

function resolveViteWatchInterval() {
    const explicitInterval = Number.parseInt(
        process.env.M1HONO_VITE_WATCH_INTERVAL?.trim() ?? "",
        10,
    );

    if (Number.isFinite(explicitInterval) && explicitInterval >= 50) {
        return explicitInterval;
    }

    return 150;
}

function shouldUsePollingWatcher() {
    const watchMode = resolveViteWatchMode();
    if (watchMode === "poll") {
        return true;
    }

    if (watchMode === "native") {
        return false;
    }

    // Native fs events are unreliable in the current VitePress/Vite stack on Node 25+.
    return currentNodeMajorVersion >= 25;
}

function resolveViteServerConfig() {
    if (!isViteDevMode || !shouldUsePollingWatcher()) {
        return undefined;
    }

    return {
        watch: {
            usePolling: true,
            interval: resolveViteWatchInterval(),
        },
    };
}

const viteCacheDir = resolveViteCacheDir();
import contributors from "../config/contributors.json";

interface Contributor {
    avatar: string;
    [key: string]: any;
}

function generateAvatarUrl(username: string) {
    return `https://github.com/${username}.png`;
}

function normalizeLlmsDomain(domain?: string) {
    return domain?.replace(/\/+$/u, "") || undefined;
}

function createLlmsSettings() {
    return {
        ...projectConfig.llms,
        domain: normalizeLlmsDomain(
            projectConfig.llms?.domain || projectInfo.homepage,
        ),
        generateLLMsTxt: projectConfig.llms?.generateLLMsTxt ?? true,
        generateLLMsFullTxt: projectConfig.llms?.generateLLMsFullTxt ?? true,
        generateLLMFriendlyDocsForEachPage:
            projectConfig.llms?.generateLLMFriendlyDocsForEachPage ?? true,
        injectLLMHint: projectConfig.llms?.injectLLMHint ?? true,
    };
}

function createLlmsReorganizePlugin(): Plugin | null {
    if (!isFeatureEnabled("llms")) {
        return null;
    }
    const llmsSettings = createLlmsSettings();
    if (llmsSettings.generateLLMsTxt === false) {
        return null;
    }

    const scriptPath = resolve(projectPaths.scripts, "reorganize-llms.mjs");
    if (!existsSync(scriptPath)) {
        return null;
    }

    return {
        name: "m1honoTemplate-llms-reorganize",
        apply: "build",
        closeBundle() {
            execFileSync(process.execPath, [scriptPath], {
                cwd: resolve(process.cwd(), projectPaths.root),
                stdio: "inherit",
            });
        },
    };
}

const llmsReorganizePlugin = createLlmsReorganizePlugin();
const shouldEnableLlmsPluginInCurrentMode =
    isFeatureEnabled("llms") && resolveViteCacheMode() === "build";
const isViteDevMode = resolveViteCacheMode() === "dev";

function formatGitProbeError(error: unknown) {
    if (error instanceof Error) {
        return error.message;
    }

    return String(error);
}

function canEnableGitChangelogPlugin() {
    if (!isFeatureEnabled("gitChangelog") || shouldDisableGitChangelog) {
        return false;
    }

    if (!shouldForceGitChangelog) {
        console.warn(
            "[m1hono] Git changelog plugin is disabled by default in this repo. Set M1HONO_FORCE_GIT_CHANGELOG=1 to opt in after verifying git path history lookups are stable.",
        );
        return false;
    }

    const probeDir = resolve(
        projectPaths.root,
        "docs/zh/modpack/kubejs/1.20.1/GettingStart",
    );
    const probeFiles = ["Catalogue.md", "Debugging.md"];

    for (const probeFile of probeFiles) {
        const probePath = resolve(probeDir, probeFile);

        if (!existsSync(probePath)) {
            continue;
        }

        try {
            execFileSync(
                "git",
                [
                    "log",
                    "--max-count=10",
                    "--format=%H",
                    "--follow",
                    "--",
                    probeFile,
                ],
                {
                    cwd: probeDir,
                    stdio: "pipe",
                    timeout: 3000,
                },
            );
        } catch (error) {
            console.warn(
                `[m1hono] Disabled git changelog plugin because path-scoped git history lookup failed for ${probePath}: ${formatGitProbeError(error)}`,
            );
            return false;
        }
    }

    return true;
}

const shouldEnableGitChangelogPlugin = canEnableGitChangelogPlugin();

export const commonConfig: UserConfig<DefaultTheme.Config> = {
    title: projectInfo.name,
    description: "A template for Vitepress documentation",
    base: projectInfo.base,

    srcDir: projectPaths.src,
    outDir: projectPaths.build,
    cacheDir: viteCacheDir,

    lastUpdated: true,
    cleanUrls: true,
    metaChunk: true,
    ignoreDeadLinks: true,

    head: [
        [
            "link",
            {
                rel: "icon",
                href: projectInfo.favicon.startsWith("http")
                    ? projectInfo.favicon
                    : `${projectInfo.base}${projectInfo.favicon}`,
            },
        ],
        [
            "meta",
            {
                name: "keywords",
                content:
                    (projectInfo as any).keyWords?.join(", ") ||
                    "vitepress, template, documentation",
            },
        ],
        ["meta", { name: "author", content: projectInfo.author }],
        ["meta", { property: "og:title", content: projectInfo.name }],
        [
            "meta",
            {
                property: "og:description",
                content: "A template for Vitepress documentation",
            },
        ],
        ["meta", { property: "og:url", content: projectInfo.homepage }],
        ["meta", { property: "og:type", content: "website" }],
    ] as HeadConfig[],

    transformHead({ assets }) {
        const faviconHref = projectInfo.favicon.startsWith("http")
            ? projectInfo.favicon
            : `${projectInfo.base}${projectInfo.favicon}`;
        return [["link", { rel: "icon", href: faviconHref }]];
    },

    markdown: { ...markdown },

    vue: {
        template: {
            compilerOptions: {
                whitespace: "preserve",
                // TresJS template compiler options will be added via vite config
                ...templateCompilerOptions.template.compilerOptions,
            },
        },
    },

    themeConfig: {
        logo: projectInfo.logo,

        socialLinks:
            projectInfo.headerSocialLinks &&
            projectInfo.headerSocialLinks.length > 0
                ? projectInfo.headerSocialLinks
                : [],

        langMenuLabel: "Change Language",

        editLink:
            isFeatureEnabled("editLink") && projectInfo.editLink
                ? {
                      pattern: projectInfo.editLink.pattern,
                      text: projectInfo.editLink.text || "Edit this page",
                  }
                : undefined,
    } satisfies DefaultTheme.Config,

    vite: {
        server: resolveViteServerConfig(),
        resolve: {
            alias: [
                {
                    find: /^.*\/VPHero\.vue$/,
                    replacement: fileURLToPath(
                        new URL(
                            "../theme/components/VPHero.vue",
                            import.meta.url,
                        ),
                    ),
                },
                {
                    find: /^.*\/VPFeatures\.vue$/,
                    replacement: fileURLToPath(
                        new URL(
                            "../theme/components/VPFeatures.vue",
                            import.meta.url,
                        ),
                    ),
                },
                {
                    find: /^.*\/VPButton\.vue$/,
                    replacement: fileURLToPath(
                        new URL(
                            "../theme/components/VPButton.vue",
                            import.meta.url,
                        ),
                    ),
                },
                {
                    find: /^.*\/VPNavBarTranslations\.vue$/,
                    replacement: fileURLToPath(
                        new URL(
                            "../theme/components/VPNavBarTranslations.vue",
                            import.meta.url,
                        ),
                    ),
                },
                {
                    find: /^.*\/VPNavScreenTranslations\.vue$/,
                    replacement: fileURLToPath(
                        new URL(
                            "../theme/components/VPNavScreenTranslations.vue",
                            import.meta.url,
                        ),
                    ),
                },
                {
                    find: /^.*\/VPNav\.vue$/,
                    replacement: fileURLToPath(
                        new URL(
                            "../theme/components/navigation/nav/VPNav.vue",
                            import.meta.url,
                        ),
                    ),
                },
                {
                    find: /^.*\/VPNavBar\.vue$/,
                    replacement: fileURLToPath(
                        new URL(
                            "../theme/components/navigation/nav/VPNavBar.vue",
                            import.meta.url,
                        ),
                    ),
                },
                {
                    find: /^.*\/VPLocalNav\.vue$/,
                    replacement: fileURLToPath(
                        new URL(
                            "../theme/components/navigation/outline/VPLocalNav.vue",
                            import.meta.url,
                        ),
                    ),
                },
                {
                    find: /^.*\/VPLocalNavOutlineDropdown\.vue$/,
                    replacement: fileURLToPath(
                        new URL(
                            "../theme/components/navigation/outline/VPLocalNavOutlineDropdown.vue",
                            import.meta.url,
                        ),
                    ),
                },
                {
                    find: /^motion-dom$/,
                    replacement: resolve(
                        projectPaths.root,
                        "node_modules/motion-dom/dist/es/index.mjs",
                    ),
                },
                {
                    find: /^motion-utils$/,
                    replacement: resolve(
                        projectPaths.root,
                        "node_modules/motion-utils/dist/es/index.mjs",
                    ),
                },
                {
                    find: "@utils",
                    replacement: resolve(projectPaths.vitepress, "utils"),
                },
                {
                    find: "@config",
                    replacement: resolve(
                        projectPaths.vitepress,
                        "utils/config",
                    ),
                },
                {
                    find: "@components",
                    replacement: resolve(
                        projectPaths.vitepress,
                        "theme/components",
                    ),
                },
                {
                    find: "@/locale",
                    replacement: resolve(projectPaths.config, "locale"),
                },
            ],
        },
        optimizeDeps: {
            exclude: [
                "@nolebase/vitepress-plugin-enhanced-readabilities",
                "@nolebase/vitepress-plugin-inline-link-preview",
                "@nolebase/vitepress-plugin-git-changelog/client",
                "shiki-magic-move",
            ],
            include: [
                "vue",
                "@vueuse/core",
                "mermaid",
                "vitepress-plugin-nprogress",
                "vitepress-plugin-tabs/client",
                "@lite-tree/vue",
            ],
            // Keep dev prebundle chunk ids stable across normal restarts.
            // Opt in only when dependency prebundling really needs a reset.
            force: shouldForceOptimizeDeps,
        },
        build: {
            chunkSizeWarningLimit: 1500,
            target: "esnext",
            minify: "esbuild",
        },
        ssr: {
            noExternal: [
                "vuetify",
                "@nolebase/vitepress-plugin-enhanced-readabilities",
                "@nolebase/vitepress-plugin-inline-link-preview",
                "@nolebase/markdown-it-bi-directional-links",
                "@nolebase/vitepress-plugin-highlight-targeted-heading",
                "vitepress-plugin-tabs",
                "shiki-magic-move",
                "markdown-it-multiple-choice",
                "motion-v",
                "framer-motion",
                "motion-dom",
                "motion-utils",
            ],
            external: [
                "path",
                "fs",
                "fast-glob",
                "gray-matter",
                "@nolebase/vitepress-plugin-git-changelog",
            ],
        },
        css: {
            preprocessorOptions: {
                scss: {
                    api: "modern",
                },
            },
        },
        define: {
            __VUE_PROD_HYDRATION_MISMATCH_DETAILS__:
                isViteDevMode,
            __VUE_OPTIONS_API__: true,
            __VUE_PROD_DEVTOOLS__: false,
            __GIT_CHANGELOG_ENABLED__: shouldEnableGitChangelogPlugin,
        },
        plugins: [
            ...(shouldEnableGitChangelogPlugin
                ? [
                      (async () => {
                          const { GitChangelog, GitChangelogMarkdownSection } =
                              await import(
                                  "@nolebase/vitepress-plugin-git-changelog/vite"
                              );
                          return [
                              // @ts-ignore
                              GitChangelog({
                                  repoURL: () => projectInfo.repository.url,
                                  mapAuthors: (
                                      contributors as Contributor[]
                                  ).map((author) => ({
                                      ...author,
                                      avatar: generateAvatarUrl(author.avatar),
                                  })),
                                  maxGitLogCount: 50,
                              }),
                              // @ts-ignore
                              GitChangelogMarkdownSection(),
                          ];
                      })(),
                  ]
                : []),
            // Conditionally load sidebar plugin based on autoSidebar feature flag
            ...(isFeatureEnabled("autoSidebar")
                ? [
                      // @ts-ignore
                      sidebarPlugin({
                          languages: getLanguageLinks().map((link) =>
                              link.replace(/^\/|\/$/g, ""),
                          ),
                          debug: shouldDebugTemplateWatchers,
                          docsDir: projectPaths.docs,
                          hotRestartOnIndexChange: false,
                      }),
                  ]
                : []),
            ...(shouldEnableLlmsPluginInCurrentMode
                ? [llmstxt(createLlmsSettings())]
                : []),
            ...(llmsReorganizePlugin ? [llmsReorganizePlugin] : []),
            m1honoTemplateDerivedDocsSyncPlugin({
                rootDir: projectPaths.root,
                docsDir: projectPaths.docs,
                debug: shouldDebugTemplateWatchers,
            }),
            // @ts-ignore
            groupIconVitePlugin({
                customIcon: {
                    json: localIconLoader(
                        import.meta.url,
                        `../../docs/public/svg/json.svg`,
                    ),
                    md: localIconLoader(
                        import.meta.url,
                        `../../docs/public/svg/markdown.svg`,
                    ),
                    ts: "logos:typescript-icon-round",
                    java: "logos:java",
                    css: "logos:css-3",
                    git: "logos:git-icon",
                },
            }),
        ],
    },
};

export default commonConfig;
