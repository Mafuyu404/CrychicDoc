import type { NavItem } from "../../../utils/config/navTypes";
import {
    createDropdownNavItem,
    createLinkedNavItem,
    createNavBadge,
    createNavDropdown,
    createNavGroup,
    createNavItems,
    createNavLink,
    createNavPanel,
    createNavPreviewPanel,
    createScreenshotMedia,
} from "../../../utils/config/navFactory";

const homeNav = createLinkedNavItem("Home", "/");

const kubePreviewMedia = createScreenshotMedia(
    "KubeJS Area",
    "/imgs/screenshots/nav/en-kubejs-1201-intro.png",
    "linear-gradient(135deg, #1d3557 0%, #457b9d 100%)",
);

const modsPreviewMedia = createScreenshotMedia(
    "Mods",
    "/imgs/screenshots/nav/en-mods-home.png",
    "linear-gradient(135deg, #18223b 0%, #334e68 100%)",
);

const modpackPreviewMedia = createScreenshotMedia(
    "Modpack",
    "/imgs/screenshots/nav/en-modpack-home.png",
    "linear-gradient(135deg, #1a2a45 0%, #31517d 100%)",
);

const developPreviewMedia = createScreenshotMedia(
    "Develop",
    "/imgs/screenshots/nav/en-develop-home.png",
    "linear-gradient(135deg, #1a2238 0%, #35506f 100%)",
);

const supportPreviewMedia = createScreenshotMedia(
    "Docs & Info",
    "/imgs/screenshots/nav/en-doc-home.png",
    "linear-gradient(140deg, #162034 0%, #243b63 100%)",
);

const supportDocsGroup = createNavGroup("Site Docs", [
    createNavLink("Overview", "/doc/Catalogue", {
        desc: "Structure and writing rules",
    }),
    createNavLink("Rules", "/doc/rules", {
        desc: "Editing and collaboration rules",
    }),
    createNavLink("Sidebar Guide", "/doc/sidebarGuide", {
        desc: "Directory, ordering, and landing-page rules",
    }),
    createNavLink(
        "Development Workflow",
        "/doc/developmentWorkflow",
        {
            desc: "Change order and sync expectations",
        },
    ),
]);

const supportGuideGroup = createNavGroup("Guide Hubs", [
    createNavLink("Community Links", "/doc/guide/community", {
        desc: "Community sites, code hosting, and common external links",
    }),
    createNavLink("Resource Tools", "/doc/guide/resource", {
        desc: "Tools used across docs, modding, and content work",
    }),
    createNavLink("Minecraft Sites", "/doc/guide/minecraft", {
        desc: "Minecraft references and site entry points",
    }),
    createNavLink("Pixel Art", "/doc/guide/pixelart", {
        desc: "Pixel-art and modeling tools",
    }),
    createNavLink("KubeJS Links", "/doc/guide/KubeJS", {
        desc: "KubeJS community and supporting resources",
    }),
]);

const supportInfoGroup = createNavGroup("Support", [
    createNavLink("Overview", "/info/Catalogue", {
        desc: "Questions, requests, and support notes",
    }),
    createNavLink("Doc Q&A", "/info/doc/Q&A/Catalogue", {
        desc: "Questions about the documentation",
    }),
    createNavLink("KubeJS Q&A", "/info/KubeJS/Q&A/Catalogue", {
        desc: "KubeJS-specific questions",
    }),
    createNavLink("Board", "/info/KubeJS/borad/Catalogue", {
        desc: "Code sharing, correction reports, recruiting, and community topics",
    }),
    createNavLink(
        "Module Requests",
        "/info/doc/module-request/Catalogue",
        {
            desc: "Requests for new modules or sections",
        },
    ),
]);

const kubeNav = createDropdownNavItem(
    "KubeJS",
    createNavDropdown(
        [
            createNavPanel([
                createNavGroup("Maintained Versions", [
                    createNavLink(
                        "1.21",
                        "/modpack/kubejs/1.21/Introduction/Catalogue",
                        {
                            desc: "Current preferred branch",
                            badge: createNavBadge("recommended", "new", {
                                pulse: true,
                            }),
                        },
                    ),
                    createNavLink(
                        "1.20.1",
                        "/modpack/kubejs/1.20.1/Introduction/Catalogue",
                        {
                            desc: "Most complete branch in the repo",
                            badge: createNavBadge("complete", "info"),
                        },
                    ),
                    createNavLink(
                        "Upgrade Notes",
                        "/modpack/kubejs/1.20.1/Upgrade/Catalogue",
                        {
                            desc: "Migration and compatibility notes",
                        },
                    )
                ]),
            ]),
            createNavPanel([
                createNavGroup("Legacy Versions", [
                    createNavLink("Version Hub", "/modpack/kubejs/Catalogue", {
                        desc: "Pick the right Minecraft branch",
                    }),
                    createNavLink("1.19.2", "/modpack/kubejs/1.19.2/Catalogue", {
                        desc: "Legacy maintenance branch",
                        badge: createNavBadge("legacy", "warning"),
                    }),
                    createNavLink("1.18.2", "/modpack/kubejs/1.18.2/Catalogue", {
                        desc: "Older branch kept for maintenance",
                        badge: createNavBadge("legacy", "warning"),
                    }),
                ]),
            ]),
            createNavPanel([
                createNavGroup("Official Docs", [
                    createNavLink("Official Wiki", "https://kubejs.com/wiki", {
                        desc: "Official KubeJS docs",
                        badge: createNavBadge("external", "info"),
                    }),
                    createNavLink("Downloads", "https://kubejs.com/downloads", {
                        desc: "Official download page",
                        badge: createNavBadge("external", "info"),
                    }),
                    createNavLink(
                        "GitHub",
                        "https://github.com/KubeJS-Mods/KubeJS/tree/2001",
                        {
                            desc: "Official source repository",
                            badge: createNavBadge("external", "info"),
                        },
                    ),
                    createNavLink("Discord", "https://discord.gg/lat", {
                        desc: "Community discussion",
                        badge: createNavBadge("external", "info"),
                    }),
                ]),
                createNavGroup("Third-Party Docs", [
                    createNavLink(
                        "KubeJS Course 1.20.1",
                        "/modpack/kubejs/1.20.1/KubeJSCourse/README",
                        {
                            desc: "In-site entry for mirrored third-party docs for 1.20.1",
                            badge: createNavBadge("in-site", "warning"),
                        },
                    ),
                    createNavLink(
                        "XPlus Tutorial 1.19.2",
                        "/modpack/kubejs/1.19.2/XPlusKubeJSTutorial/README",
                        {
                            desc: "Direct entry to the Wudji tutorial for Minecraft 1.19.2",
                            badge: createNavBadge("in-site", "warning"),
                        },
                    ),
                    createNavLink(
                        "XPlus Tutorial 1.18.2",
                        "/modpack/kubejs/1.18.2/XPlusKubeJSTutorial/README",
                        {
                            desc: "Direct entry to the Wudji tutorial for Minecraft 1.18.2",
                            badge: createNavBadge("in-site", "warning"),
                        },
                    ),
                ]),
            ]),
        ],
        {
            layout: "columns",
            preview: createNavPreviewPanel(
                "KubeJS Area",
                "Start from in-site version branches, then jump to official or third-party references as needed.",
                `| Path | Best for |
| --- | --- |
| 1.21 | current maintained branch |
| 1.20.1 | deepest in-site coverage |
| Upgrade | migration and compatibility |
| Official docs | authoritative references |
| Third-party docs | direct entry to Course 1.20.1 and XPlus 1.19.2/1.18.2 |`,
                kubePreviewMedia,
                { link: "/modpack/kubejs/Catalogue" },
            ),
        },
    ),
);

const modsNav = createDropdownNavItem(
    "Mods",
    createNavDropdown(
        [
            createNavPanel([
                createNavGroup("Categories", [
                    createNavLink("Overview", "/mods/Catalogue", {
                        desc: "All mod categories",
                    }),
                    createNavLink("Tech", "/mods/tech/Catalogue", {
                        desc: "Technology-focused mods",
                    }),
                    createNavLink("Adventure", "/mods/adventure/Catalogue", {
                        desc: "Adventure and combat content",
                    }),
                    createNavLink("Storage", "/mods/storages/Catalogue", {
                        desc: "Storage and logistics",
                    }),
                    createNavLink("Performance", "/mods/performance/Catalogue", {
                        desc: "Client and server optimization",
                    }),
                    createNavLink("Custom", "/mods/custom/Catalogue", {
                        desc: "Custom or specialized entries",
                    }),
                ]),
            ]),
        ],
        {
            layout: "columns",
            preview: createNavPreviewPanel(
                "Mods",
                "Browse by mod topic instead of version branch.",
                `| Category | Focus |
| --- | --- |
| Tech | mechanics and automation |
| Adventure | combat and exploration |
| Storage | logistics and storage |
| Performance | optimization |
| Custom | specialized project pages |`,
                modsPreviewMedia,
                { link: "/mods/Catalogue" },
            ),
        },
    ),
);

const modpackNav = createDropdownNavItem(
    "Modpack",
    createNavDropdown(
        [
            createNavPanel([
                createNavGroup("Entries", [
                    createNavLink("Overview", "/modpack/Catalogue", {
                        desc: "Top-level modpack content",
                    }),
                    createNavLink("Recommendations", "/modpack/recommendation/Catalogue", {
                        desc: "Recommended packs and notes",
                    }),
                    createNavLink("KubeJS Area", "/modpack/kubejs/Catalogue", {
                        desc: "Dedicated KubeJS versions and topics",
                    }),
                ]),
            ]),
        ],
        {
            layout: "columns",
            preview: createNavPreviewPanel(
                "Modpack",
                "Keeps pack-oriented content together while KubeJS has its own top-level slot.",
                `| Entry | Purpose |
| --- | --- |
| Overview | top-level modpack content |
| Recommendations | curated pack notes |
| KubeJS Area | dedicated scripting docs |`,
                modpackPreviewMedia,
                { link: "/modpack/Catalogue" },
            ),
        },
    ),
);

const developNav = createDropdownNavItem(
    "Develop",
    createNavDropdown(
        [
            createNavPanel([
                createNavGroup("Entry", [
                    createNavLink("Overview", "/develop/Catalogue", {
                        desc: "Development content entry",
                    }),
                ]),
                createNavGroup("Maintained Versions", [
                    createNavLink(
                        "NeoForge 1.20.4",
                        "/develop/modding/1.20.4/Neoforge/Catalogue",
                        {
                            desc: "Current NeoForge path in this repo",
                            badge: createNavBadge("NeoForge", "new"),
                        },
                    ),
                    createNavLink(
                        "Minecraft 1.21",
                        "/develop/modding/1.21/Catalogue",
                        {
                            desc: "Notes for 1.21 modding",
                        },
                    ),
                    createNavLink("Mixin", "/develop/modding/Mixin/Catalogue", {
                        desc: "Core Mixin notes in the maintained path",
                    }),
                ]),
            ]),
            createNavPanel([
                createNavGroup("Data And Tools", [
                    createNavLink("Plugins", "/develop/plugin/Catalogue", {
                        desc: "Site plugins and extension work",
                    }),
                    createNavLink(
                        "Datapack",
                        "/develop/vanilla/datapack/Catalogue",
                        {
                            desc: "Datapack-related docs",
                        },
                    ),
                    createNavLink(
                        "Resource Pack",
                        "/develop/vanilla/resourcepack/Catalogue",
                        {
                            desc: "Resource pack docs",
                        },
                    ),
                ]),
                createNavGroup("Official References", [
                    createNavLink(
                        "NeoForged Docs",
                        "https://docs.neoforged.net/",
                        {
                            desc: "Official NeoForged documentation",
                            badge: createNavBadge("external", "info"),
                        },
                    ),
                    createNavLink(
                        "Toolchain Docs",
                        "https://docs.neoforged.net/toolchain/docs/",
                        {
                            desc: "Official NeoForged toolchain docs",
                            badge: createNavBadge("external", "info"),
                        },
                    ),
                    createNavLink(
                        "NeoGradle Plugin",
                        "https://docs.neoforged.net/toolchain/docs/plugins/ng/",
                        {
                            desc: "Official NeoGradle plugin docs",
                            badge: createNavBadge("external", "info"),
                        },
                    ),
                    createNavLink(
                        "NeoForged Site",
                        "https://neoforged.net/",
                        {
                            desc: "Project site and releases",
                            badge: createNavBadge("external", "info"),
                        },
                    ),
                ]),
            ]),
        ],
        {
            layout: "columns",
            preview: createNavPreviewPanel(
                "Develop",
                "In-site docs organize learning paths; official docs remain the source for authoritative definitions.",
                `| Layer | Role |
| --- | --- |
| in-site docs | curated structure in CrychicDoc |
| NeoForge topics | collected NeoForge study paths |
| official references | NeoForged docs, toolchain docs, and NeoGradle plugin docs |`,
                developPreviewMedia,
                { link: "/develop/Catalogue" },
            ),
        },
    ),
);

const supportNav = createDropdownNavItem(
    "Docs & Info",
    createNavDropdown(
        [
            createNavPanel([supportDocsGroup, supportInfoGroup]),
            createNavPanel([supportGuideGroup]),
        ],
        {
            layout: "columns",
            preview: createNavPreviewPanel(
                "Docs & Info",
                "Site docs and support flows share one column, while guide hubs stay in a separate column for clearer scanning.",
                `| Column | Description |
| --- | --- |
| Docs & Info | structure, rules, Q&A, board topics, and module requests |
| Guide Hubs | community links, tools, and topical gateways |`,
                supportPreviewMedia,
                { link: "/doc/Catalogue" },
            ),
        },
    ),
);

const enNav: NavItem[] = createNavItems(
    homeNav,
    kubeNav,
    modsNav,
    modpackNav,
    developNav,
    supportNav,
);

export default enNav;
