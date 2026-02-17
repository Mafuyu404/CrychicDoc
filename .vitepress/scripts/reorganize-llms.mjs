import fs from "node:fs/promises";
import { execFileSync } from "node:child_process";
import path from "node:path";

const KEEP_AREAS = new Set(["develop", "modpack"]);
const LANGUAGE_ORDER = ["zh", "en"];
const LANGUAGE_LABEL = {
    zh: "Chinese Documentation",
    en: "English Documentation",
};

/**
 * Normalizes a path to a VitePress-like route path.
 *
 * @param {string} value
 * @returns {string}
 */
function normalizeRoutePath(value) {
    let result = value || "";
    result = result.replace(/^https?:\/\/[^/]+/i, "");
    result = result.replace(/\/{2,}/g, "/");
    result = result.replace(/\/index\.html$/i, "/");
    result = result.replace(/\/index\.md$/i, "/");
    if (!result.startsWith("/")) {
        result = `/${result}`;
    }
    return result;
}

/**
 * Builds path variants used for matching sidebar links and llms links.
 *
 * @param {string} value
 * @returns {string[]}
 */
function buildPathVariants(value) {
    const route = normalizeRoutePath(value);
    const variants = new Set([route]);
    if (route.endsWith(".md")) {
        variants.add(route.replace(/\.md$/i, ".html"));
    }
    if (route.endsWith(".html")) {
        variants.add(route.replace(/\.html$/i, ".md"));
    }
    if (route.endsWith("/")) {
        variants.add(`${route}index.md`);
        variants.add(`${route}index.html`);
    }
    return Array.from(variants);
}

/**
 * Parses a route path into language and area segments.
 *
 * @param {string} routePath
 * @returns {{lang: string, area: string}}
 */
function parseRouteMeta(routePath) {
    const segments = normalizeRoutePath(routePath).split("/").filter(Boolean);
    return {
        lang: segments[0] || "",
        area: segments[1] || "",
    };
}

/**
 * Reads llms entry data from a markdown list line.
 *
 * @param {string} line
 * @returns {{line: string, title: string, url: string, routePath: string} | null}
 */
function parseEntryLine(line) {
    const titleMatch = line.match(/\[([^\]]+)\]/);
    const urlMatch = line.match(/\((https?:\/\/[^\)]+)\)/);
    if (!titleMatch || !urlMatch) {
        return null;
    }
    const routePath = normalizeRoutePath(new URL(urlMatch[1]).pathname);
    return {
        line,
        title: titleMatch[1],
        url: urlMatch[1],
        routePath,
    };
}

/**
 * Resolves a docs markdown file path from a route path.
 *
 * @param {string} docsRoot
 * @param {string} routePath
 * @returns {string}
 */
function toDocFilePath(docsRoot, routePath) {
    let relativePath = normalizeRoutePath(routePath).replace(/^\//, "");
    if (relativePath.endsWith(".html")) {
        relativePath = relativePath.replace(/\.html$/i, ".md");
    } else if (relativePath.endsWith("/")) {
        relativePath = `${relativePath}index.md`;
    } else if (!relativePath.endsWith(".md")) {
        relativePath = `${relativePath}.md`;
    }
    return path.join(docsRoot, relativePath);
}

/**
 * Extracts a concise llm-only summary from a markdown file.
 *
 * @param {string} filePath
 * @returns {Promise<string>}
 */
async function extractLlmOnlySummary(filePath) {
    try {
        const content = await fs.readFile(filePath, "utf8");
        const match = content.match(/<llm-only>([\s\S]*?)<\/llm-only>/i);
        if (!match) {
            return "";
        }
        const cleaned = match[1]
            .replace(/```[\s\S]*?```/g, " ")
            .replace(/<[^>]*>/g, " ")
            .split(/\r?\n/)
            .map((line) => line.trim().replace(/^[\-\*\d\.\)\s]+/, ""))
            .filter(Boolean)
            .slice(0, 3)
            .join(" ");
        return cleaned.trim();
    } catch {
        return "";
    }
}

/**
 * Loads sidebars by calling the shared sidebar generation system.
 *
 * @param {string} projectRoot
 * @returns {Record<string, Record<string, any[]>>}
 */
function loadSidebars(projectRoot) {
    const output = execFileSync(
        "npx",
        ["tsx", ".vitepress/scripts/export-llms-sidebars.ts"],
        {
            cwd: projectRoot,
            encoding: "utf8",
            maxBuffer: 50 * 1024 * 1024,
        },
    );
    return JSON.parse(output);
}

/**
 * Builds an index from sidebar links to ordering metadata.
 *
 * @param {Record<string, Record<string, any[]>>} sidebarByLang
 * @returns {Map<string, {order: number, route: string, chain: string[]}>}
 */
function buildSidebarIndex(sidebarByLang) {
    const index = new Map();
    let order = 0;

    /**
     * @param {any[]} items
     * @param {string[]} chain
     * @param {string} routeKey
     */
    const walk = (items, chain, routeKey) => {
        for (const item of items || []) {
            const nextChain = item?.text ? [...chain, String(item.text)] : chain;
            if (item?.link) {
                for (const variant of buildPathVariants(item.link)) {
                    if (!index.has(variant)) {
                        index.set(variant, { order, route: routeKey, chain: nextChain });
                    }
                }
                order += 1;
            }
            if (Array.isArray(item?.items) && item.items.length > 0) {
                walk(item.items, nextChain, routeKey);
            }
        }
    };

    for (const lang of LANGUAGE_ORDER) {
        const sidebar = sidebarByLang[lang] || {};
        const routes = Object.keys(sidebar).sort((a, b) => a.localeCompare(b));
        for (const routeKey of routes) {
            const meta = parseRouteMeta(routeKey);
            if (!KEEP_AREAS.has(meta.area)) {
                continue;
            }
            walk(sidebar[routeKey], [], routeKey);
        }
    }
    return index;
}

/**
 * Builds a fallback section title from route path.
 *
 * @param {string} routeKey
 * @returns {string}
 */
function routeTitle(routeKey) {
    const segments = normalizeRoutePath(routeKey).split("/").filter(Boolean);
    if (segments.length <= 2) {
        return segments[1] || segments[0] || routeKey;
    }
    return segments.slice(2).join(" / ");
}

/**
 * Builds a fallback chain for entries that are not resolved by sidebar links.
 *
 * @param {string} routePath
 * @param {string} area
 * @returns {string[]}
 */
function buildExternalCompatibleChain(routePath, area) {
    const segments = normalizeRoutePath(routePath).split("/").filter(Boolean);
    const scoped = segments.slice(2).map((segment) => decodeURIComponent(segment));
    if (scoped.length === 0) {
        return [area, "external"];
    }

    const last = scoped[scoped.length - 1];
    const isFile = /\.(md|html)$/i.test(last);
    const dirs = isFile ? scoped.slice(0, -1) : scoped;

    if (dirs.length === 0) {
        const stem = isFile ? last.replace(/\.(md|html)$/i, "") : "root";
        return [area, "external", stem];
    }

    return [area, "external", ...dirs];
}

const llmsPath = new URL("../dist/llms.txt", import.meta.url);
const projectRoot = path.resolve(new URL("../../", import.meta.url).pathname);
const docsRoot = path.resolve(new URL("../../docs", import.meta.url).pathname);
const raw = await fs.readFile(llmsPath, "utf8");
const lines = raw.split(/\r?\n/);
const firstEntryIndex = lines.findIndex((line) => line.trim().startsWith("- ["));
const firstSectionIndex = lines.findIndex(
    (line, index) =>
        index > 0 &&
        line.trim().startsWith("# ") &&
        line.trim() !== "# CrychicDoc",
);
let headerEndIndex = lines.length;
if (firstEntryIndex >= 0 && firstSectionIndex >= 0) {
    headerEndIndex = Math.min(firstEntryIndex, firstSectionIndex);
} else if (firstEntryIndex >= 0) {
    headerEndIndex = firstEntryIndex;
} else if (firstSectionIndex >= 0) {
    headerEndIndex = firstSectionIndex;
}
const headerLines = lines.slice(0, headerEndIndex);
const entryLines = lines.filter((line) => line.trim().startsWith("- ["));
const parsedEntries = entryLines.map(parseEntryLine).filter(Boolean);
const sidebars = loadSidebars(projectRoot);
const sidebarIndex = buildSidebarIndex(sidebars);
const summaryCache = new Map();
const grouped = new Map();
const seenUrls = new Set();

for (const entry of parsedEntries) {
    const { lang, area } = parseRouteMeta(entry.routePath);
    if (!LANGUAGE_ORDER.includes(lang) || !KEEP_AREAS.has(area)) {
        continue;
    }
    if (seenUrls.has(entry.url)) {
        continue;
    }
    seenUrls.add(entry.url);

    let sidebarMeta = null;
    for (const variant of buildPathVariants(entry.routePath)) {
        if (sidebarIndex.has(variant)) {
            sidebarMeta = sidebarIndex.get(variant);
            break;
        }
    }

    const routeKey = sidebarMeta?.route || `/${lang}/${area}/external/`;
    const parentChain = sidebarMeta?.chain && sidebarMeta.chain.length > 1
        ? sidebarMeta.chain.slice(0, -1)
        : buildExternalCompatibleChain(entry.routePath, area);
    const chainKey = parentChain.join("||");
    const groupKey = `${lang}||${routeKey}||${chainKey}`;

    if (!grouped.has(groupKey)) {
        grouped.set(groupKey, {
            lang,
            routeKey,
            chain: parentChain,
            order: sidebarMeta?.order ?? Number.MAX_SAFE_INTEGER,
            entries: [],
            summary: "",
        });
    }

    const group = grouped.get(groupKey);
    group.entries.push({
        line: entry.line,
        title: entry.title,
        order: sidebarMeta?.order ?? Number.MAX_SAFE_INTEGER,
    });

    if (!group.summary) {
        const docPath = toDocFilePath(docsRoot, entry.routePath);
        if (!summaryCache.has(docPath)) {
            summaryCache.set(docPath, await extractLlmOnlySummary(docPath));
        }
        group.summary = summaryCache.get(docPath);
    }
}

/**
 * Creates an empty TOC node.
 *
 * @param {string} label
 * @returns {{label: string, order: number, summary: string, entries: {line: string, title: string, order: number}[], children: Map<string, any>}}
 */
function createNode(label) {
    return {
        label,
        order: Number.MAX_SAFE_INTEGER,
        summary: "",
        entries: [],
        children: new Map(),
    };
}

/**
 * Renders a node recursively into markdown lines.
 *
 * @param {any} node
 * @param {number} level
 * @param {string} lang
 * @param {string[]} output
 */
function renderNode(node, level, lang, output) {
    if (node.label) {
        output.push(`${"#".repeat(Math.min(level, 6))} ${node.label}`);
        output.push("");
    }

    const sortedChildren = Array.from(node.children.values()).sort((a, b) => {
        if (a.order !== b.order) {
            return a.order - b.order;
        }
        return a.label.localeCompare(b.label, lang === "zh" ? "zh-CN" : "en", {
            numeric: true,
        });
    });

    const shouldRenderEntries = node.children.size === 0;
    if (shouldRenderEntries && node.summary) {
        output.push(`> ${node.summary}`);
        output.push("");
    }
    if (shouldRenderEntries) {
        const sortedEntries = node.entries.sort((a, b) => {
            if (a.order !== b.order) {
                return a.order - b.order;
            }
            return a.title.localeCompare(b.title, lang === "zh" ? "zh-CN" : "en", {
                numeric: true,
            });
        });
        for (const entry of sortedEntries) {
            output.push(entry.line);
        }
        if (sortedEntries.length > 0) {
            output.push("");
        }
    }

    for (const child of sortedChildren) {
        renderNode(child, level + 1, lang, output);
    }
}

const treesByLanguage = new Map();
for (const lang of LANGUAGE_ORDER) {
    treesByLanguage.set(lang, createNode(""));
}

for (const group of grouped.values()) {
    const root = treesByLanguage.get(group.lang);
    if (!root) {
        continue;
    }

    const routeMeta = parseRouteMeta(group.routeKey);
    if (!KEEP_AREAS.has(routeMeta.area)) {
        continue;
    }

    const normalizedChain = group.chain.filter(Boolean);
    const firstChain = normalizedChain[0]?.toLowerCase();
    const areaName = routeMeta.area.toLowerCase();
    const chainWithoutArea =
        firstChain === areaName ? normalizedChain.slice(1) : normalizedChain;
    if (chainWithoutArea.length === 0) {
        continue;
    }

    let current = root;
    const fullPath = [routeMeta.area, ...chainWithoutArea];
    for (const segment of fullPath) {
        if (!current.children.has(segment)) {
            current.children.set(segment, createNode(segment));
        }
        current = current.children.get(segment);
        current.order = Math.min(current.order, group.order);
    }

    if (!current.summary && group.summary) {
        current.summary = group.summary;
    }
    current.entries.push(...group.entries);
}

const output = [...headerLines];
for (const lang of LANGUAGE_ORDER) {
    const root = treesByLanguage.get(lang);
    if (!root || root.children.size === 0) {
        continue;
    }
    output.push("");
    output.push(`# ${LANGUAGE_LABEL[lang]}`);
    output.push("");

    const areas = ["develop", "modpack"];
    for (const area of areas) {
        const node = root.children.get(area);
        if (!node) {
            continue;
        }
        renderNode(node, 2, lang, output);
    }
}

while (output.length > 0 && output[output.length - 1] === "") {
    output.pop();
}

await fs.writeFile(llmsPath, `${output.join("\n")}\n`, "utf8");
console.log(`✅ Reorganized llms.txt`);
console.log(`📊 Groups: ${grouped.size}, Entries: ${seenUrls.size}`);
