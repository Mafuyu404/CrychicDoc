import type { DefaultTheme, HeadConfig, UserConfig } from "vitepress";
import { resolve } from "path";
import { fileURLToPath } from "url";
import { 
    getProjectInfo, 
    isFeatureEnabled, 
    getPaths,
    getLanguageLinks,
} from "./project-config";
import { sidebarPlugin } from "../utils/sidebar/";
import { markdown } from "./markdown-plugins";
import {
    groupIconVitePlugin,
    localIconLoader,
} from "vitepress-plugin-group-icons";
import { GitChangelog, GitChangelogMarkdownSection } from '@nolebase/vitepress-plugin-git-changelog/vite';

const projectInfo = getProjectInfo();
const projectPaths = getPaths();
import contributors from "../config/contributors.json";

interface Contributor {
    avatar: string;
    [key: string]: any;
}

function generateAvatarUrl(username: string) {
    return `https://github.com/${username}.png`;
}

export const commonConfig: UserConfig<DefaultTheme.Config> = {
    title: projectInfo.name,
    description: "A Minecraft development documentation website.",
    base: projectInfo.base,
    
    srcDir: projectPaths.src,
    outDir: projectPaths.build,
    cacheDir: projectPaths.cache,
    
    lastUpdated: true,
    cleanUrls: true,
    metaChunk: true,
    ignoreDeadLinks: true,
    
    sitemap: {
        hostname: 'https://docs.variedmc.cc',
        lastmodDateOnly: false
    },

    head: [
        ["link", { 
            rel: "icon", 
            href: projectInfo.favicon.startsWith('http') 
                ? projectInfo.favicon 
                : `${projectInfo.base}${projectInfo.favicon}` 
        }],
        ["meta", { name: "keywords", content: (projectInfo as any).keyWords?.join(", ") || "vitepress, template, documentation" }],
        ["meta", { name: "author", content: projectInfo.author }],
        ["meta", { property: "og:title", content: projectInfo.name }],
        ["meta", { property: "og:description", content: "A Minecraft development documentation website." }],
        ["meta", { property: "og:url", content: projectInfo.homepage }],
        ["meta", { property: "og:type", content: "website" }],
    ] as HeadConfig[],

    transformHead({ assets }) {
        const faviconHref = projectInfo.favicon.startsWith('http') 
            ? projectInfo.favicon 
            : `${projectInfo.base}${projectInfo.favicon}`;
        return [
            ["link", { rel: "icon", href: faviconHref }],
        ];
    },

    markdown: { ...markdown },

    mermaid: isFeatureEnabled('mermaid') ? {
        startOnLoad: true,
        securityLevel: "loose",
        theme: "default",
    } : undefined,

    vue: {
        template: {
            compilerOptions: {
                whitespace: "preserve"
            },
        },
    },

    themeConfig: {
        logo: projectInfo.logo,
        
        socialLinks: (projectInfo.headerSocialLinks && projectInfo.headerSocialLinks.length > 0) ? projectInfo.headerSocialLinks : [],

        langMenuLabel: "Change Language",

        editLink: isFeatureEnabled('editLink') && projectInfo.editLink ? {
            pattern: projectInfo.editLink.pattern,
            text: projectInfo.editLink.text || "Edit this page"
        } : undefined,
    } satisfies DefaultTheme.Config,

    vite: {
        resolve: {
            alias: [
                {
                    find: /^.*\/VPHero\.vue$/,
                    replacement: fileURLToPath(
                        new URL(
                            "../theme/components/VPHero.vue",
                            import.meta.url
                        )
                    ),
                },
                {
                    find: /^.*\/VPFeatures\.vue$/,
                    replacement: fileURLToPath(
                        new URL(
                            "../theme/components/VPFeatures.vue",
                            import.meta.url
                        )
                    ),
                },
                {
                    find: /^.*\/VPButton\.vue$/,
                    replacement: fileURLToPath(
                        new URL(
                            "../theme/components/VPButton.vue",
                            import.meta.url
                        )
                    ),
                },
                {
                    find: /^.*\/VPNavBarTranslations\.vue$/,
                    replacement: fileURLToPath(
                        new URL(
                            "../theme/components/VPNavBarTranslations.vue",
                            import.meta.url
                        )
                    ),
                },
                {
                    find: "@utils",
                    replacement: resolve(projectPaths.vitepress, "utils"),
                },
                {
                    find: "@config",
                    replacement: resolve(projectPaths.vitepress, "config"),
                },
                {
                    find: "@components",
                    replacement: resolve(projectPaths.vitepress, "theme/components"),
                },
                {
                    find: "@/locale",
                    replacement: resolve(projectPaths.config, "locale"),
                },
            ],
        },
        optimizeDeps: {
            exclude: [
                "@nolebase/vitepress-plugin-git-changelog",
                "@nolebase/vitepress-plugin-enhanced-readabilities",
                "@nolebase/vitepress-plugin-inline-link-preview",
                "shiki-magic-move",
                "virtual:nolebase-git-changelog"
            ],
            include: [
                'vue',
                '@vueuse/core',
                'mermaid',
                'vitepress-plugin-nprogress',
                'vitepress-plugin-tabs/client',
                '@lite-tree/vue'
            ],
            force: true
        },
        build: {
            chunkSizeWarningLimit: 1500,
            target: 'esnext',
            minify: 'esbuild'
        },
        ssr: {
            noExternal: [
                "vuetify",
                "@nolebase/*",
                "vitepress-plugin-tabs",
                "shiki-magic-move",
                "markdown-it-multiple-choice"
            ],
            external: [
                "path",
                "fs",
                "fast-glob",
                "gray-matter"
            ]
        },
        css: {
            preprocessorOptions: {
                scss: {
                    api: "modern",
                },
            },
        },
        define: {
            __VUE_PROD_HYDRATION_MISMATCH_DETAILS__: process.env.NODE_ENV === 'development',
            __VUE_OPTIONS_API__: true,
            __VUE_PROD_DEVTOOLS__: false
        },
        plugins: [
            GitChangelog({
                repoURL: () => projectInfo.repository.url,
                mapAuthors: (contributors as Contributor[]).map((author) => ({
                    ...author,
                    avatar: generateAvatarUrl(author.avatar),
                })),
            }),
            GitChangelogMarkdownSection(),
            sidebarPlugin({
                languages: getLanguageLinks().map(link => link.replace(/^\/|\/$/g, '')),
                debug: process.env.NODE_ENV === 'development',
                docsDir: projectPaths.docs,
                cacheDir: projectPaths.cache
            }),
            groupIconVitePlugin({
                customIcon: {
                    mcmeta: localIconLoader(
                        import.meta.url,
                        "../../docs/public/svg/minecraft.svg"
                    ),
                    json: localIconLoader(
                        import.meta.url,
                        "../../docs/public/svg/json.svg"
                    ),
                    md: localIconLoader(
                        import.meta.url,
                        "../../docs/public/svg/markdown.svg"
                    ),
                    kubejs: localIconLoader(
                        import.meta.url,
                        "../../docs/public/svg/kubejs.svg"
                    ),
                    js: "logos:javascript",
                    sh: localIconLoader(
                        import.meta.url,
                        "../../docs/public/svg/powershell.svg"
                    ),
                    npm: localIconLoader(
                        import.meta.url,
                        "../../docs/public/svg/npm.svg"
                    ),
                    neoforge: localIconLoader(
                        import.meta.url,
                        "../../docs/public/svg/neoforge.svg"
                    ),
                    forge: localIconLoader(
                        import.meta.url,
                        "../../docs/public/svg/forge.svg"
                    ),
                    fabric: localIconLoader(
                        import.meta.url,
                        "../../docs/public/svg/fabric.svg"
                    ),
                    ts: "logos:typescript-icon-round",
                    java: "logos:java",
                    css: "logos:css-3",
                    git: "logos:git-icon",
                },
            })
        ],
    },
};

export default commonConfig;