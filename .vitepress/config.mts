import { defineConfig } from "vitepress";
import { withMermaid } from "vitepress-plugin-mermaid";
import withDrawio from "@dhlx/vitepress-plugin-drawio";
import { existsSync, readFileSync } from "node:fs";
import { dirname, join, basename } from "node:path";
import matter from "gray-matter";

import { commonConfig } from "./config/common-config";
import {
    generateLocalesConfigAuto,
    getProjectInfo,
    isFeatureEnabled,
    resolveThemeSearchConfig,
} from "./utils/config/project-config";

const SIDEBAR_INDEX_CANDIDATES = [
    "sidebarIndex.md",
    "root.md",
    "index.md",
    "Catalogue.md",
];

const SIDEBAR_INDEX_SET = new Set(
    SIDEBAR_INDEX_CANDIDATES.map((n) => n.toLowerCase()),
);

const metadataCache = new Map<string, unknown>();

function resolveDirectoryMetadata(dir: string): unknown {
    if (metadataCache.has(dir)) return metadataCache.get(dir);

    for (const candidate of SIDEBAR_INDEX_CANDIDATES) {
        const filePath = join(dir, candidate);
        if (existsSync(filePath)) {
            try {
                const raw = readFileSync(filePath, "utf-8");
                const { data } = matter(raw);
                const metadata = data?.metadata ?? null;
                metadataCache.set(dir, metadata);
                return metadata;
            } catch {
                break;
            }
        }
    }

    metadataCache.set(dir, null);
    return null;
}

const { locales, searchLocales } = await generateLocalesConfigAuto(true);
const resolvedSearchConfig = resolveThemeSearchConfig(searchLocales);
const projectInfo = getProjectInfo();

const finalConfig = {
    ...(commonConfig as any),
    locales,
    themeConfig: {
        ...commonConfig.themeConfig,
        search: resolvedSearchConfig,
    },
    transformPageData(pageData: { filePath: string; frontmatter: Record<string, unknown> }) {
        if (pageData.frontmatter.metadata != null) return;
        if (SIDEBAR_INDEX_SET.has(basename(pageData.filePath).toLowerCase())) return;

        const dir = dirname(join("docs", pageData.filePath));
        const inherited = resolveDirectoryMetadata(dir);
        if (inherited) {
            pageData.frontmatter.metadata = inherited;
        }
    },
};

let config = defineConfig(finalConfig);

if (isFeatureEnabled("mermaid")) {
    config = withMermaid(config);
}

if (isFeatureEnabled("drawio")) {
    config = withDrawio(config, projectInfo.drawio);
}

export default config;
