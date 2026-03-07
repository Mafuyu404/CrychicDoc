import type { NavItem, NavPanel } from "../../../utils/config/navTypes";
import {
    createDropdownNavItem,
    createLinkedNavItem,
    createNavDropdown,
    createNavItems,
    createNavPreviewPanel,
    createScreenshotMedia,
    createShowcasePreview,
} from "../../../utils/config/navFactory";

const kubePreview = createShowcasePreview;
const previewMedia = createScreenshotMedia;
const kubejsCourseUrl = "https://gumeng.gitbook.io/kubejs-jiao-cheng-1.20.1";

const homeNav = createLinkedNavItem("Home", "/");

const kubePanels: NavPanel[] = [
                {
                    weight: 1.15,
                    groups: [
                        {
                            label: "1.21 (Latest)",
                            items: [
                                {
                                    text: "Overview",
                                    link: "/modpack/kubejs/1.21/",
                                    desc: "Newest branch for modern 1.21 packs.",
                                    badge: {
                                        text: "latest",
                                        type: "new",
                                        pulse: true,
                                    },
                                    preview: kubePreview(
                                        "KubeJS 1.21 Overview",
                                        "Start here for the latest scripting branch.",
                                        `**Best for Minecraft 1.21 pack development.**

- Active branch in CrychicDoc
- Fastest path to current API behavior
- Recommended route: Introduction -> Addon -> LootJS

Use this branch when your runtime target is 1.21.`,
                                        {
                                            src: "/imgs/screenshots/nav/en-kubejs-121-home.png",
                                            background:
                                                "linear-gradient(135deg, #0d3b66 0%, #2a9d8f 100%)",
                                        },
                                    ),
                                },
                                {
                                    text: "Introduction",
                                    link: "/modpack/kubejs/1.21/Introduction/",
                                    desc: "Core concepts, script model, and entry flow.",
                                    preview: kubePreview(
                                        "1.21 Introduction",
                                        "Core concepts before deep addon work.",
                                        `Covers the foundation of the 1.21 branch:

- Startup / Server / Client script mental model
- Core KubeJS workflow and naming conventions
- Base context needed for addon APIs`,
                                        {
                                            src: "/imgs/screenshots/nav/en-kubejs-121-home.png",
                                            background:
                                                "linear-gradient(140deg, #264653 0%, #3a86ff 100%)",
                                        },
                                    ),
                                },
                                {
                                    text: "Addon",
                                    link: "/modpack/kubejs/1.21/Introduction/Addon/",
                                    desc: "Extended ecosystem and module integration.",
                                    preview: kubePreview(
                                        "1.21 Addon",
                                        "Extension layer for advanced integrations.",
                                        `Use Addon docs when built-in APIs are not enough:

- Integrate ecosystem modules
- Compose advanced script capabilities
- Reach feature depth required by larger packs`,
                                        {
                                            src: "/imgs/screenshots/nav/en-kubejs-121-home.png",
                                            background:
                                                "linear-gradient(135deg, #3d405b 0%, #81b29a 100%)",
                                        },
                                    ),
                                },
                                {
                                    text: "LootJS Guide",
                                    link: "/modpack/kubejs/1.21/Introduction/Addon/LootJs/LootJs",
                                    desc: "Loot table and drop logic extension focus.",
                                    preview: kubePreview(
                                        "1.21 LootJS",
                                        "Targeted guide for loot modifiers and tables.",
                                        `LootJS is a high-impact addon path:

- Build deterministic loot logic
- Modify existing tables with clear script steps
- Combine with events for dynamic progression`,
                                        {
                                            src: "/imgs/screenshots/nav/en-kubejs-121-home.png",
                                            background:
                                                "linear-gradient(130deg, #4a4e69 0%, #f2a65a 100%)",
                                        },
                                    ),
                                },
                            ],
                        },
                    ],
                },
                {
                    weight: 1.3,
                    groups: [
                        {
                            label: "1.20.1 (Core Path)",
                            items: [
                                {
                                    text: "Overview",
                                    link: "/modpack/kubejs/1.20.1/Introduction/Description",
                                    desc: "Most complete branch with broad coverage.",
                                    preview: kubePreview(
                                        "KubeJS 1.20.1 Overview",
                                        "Deepest and broadest branch in this doc set.",
                                        `The 1.20.1 section is the main learning backbone:

- Introduction, Upgrade, CodeShare, and course resources
- Rich examples for scripting patterns
- Practical reference for production modpacks`,
                                        {
                                            src: "/imgs/screenshots/nav/en-kubejs-1201-intro.png",
                                            background:
                                                "linear-gradient(135deg, #1d3557 0%, #457b9d 100%)",
                                        },
                                    ),
                                },
                                {
                                    text: "Introduction",
                                    link: "/modpack/kubejs/1.20.1/Introduction/Description",
                                    desc: "Events, recipes, entities, tags, and systems.",
                                    preview: kubePreview(
                                        "1.20.1 Introduction",
                                        "API-first learning path with broad subsystem coverage.",
                                        `Entry points included:

- Event lifecycle and script partitioning
- Recipe and item/block registration
- Entity, tag, loot table, and network references`,
                                        {
                                            src: "/imgs/screenshots/nav/en-kubejs-1201-intro.png",
                                            background:
                                                "linear-gradient(140deg, #003049 0%, #2a9d8f 100%)",
                                        },
                                    ),
                                },
                                {
                                    text: "Event API",
                                    link: "/modpack/kubejs/1.20.1/Introduction/Event/",
                                    desc: "Startup, server, and client event model.",
                                    preview: kubePreview(
                                        "1.20.1 Event API",
                                        "Operational center for script-driven behavior.",
                                        `Plan event hooks by runtime scope:

- StartupScript for registrations
- ServerScript for game logic
- ClientScript for client-only behavior`,
                                        {
                                            src: "/imgs/screenshots/nav/en-kubejs-1201-intro.png",
                                            background:
                                                "linear-gradient(125deg, #1b4332 0%, #2d6a4f 100%)",
                                        },
                                    ),
                                },
                                {
                                    text: "Recipe API",
                                    link: "/modpack/kubejs/1.20.1/Introduction/Recipe/",
                                    desc: "Create, modify, and remove recipe flows.",
                                    preview: kubePreview(
                                        "1.20.1 Recipe API",
                                        "Most used section for modpack balancing.",
                                        `Recipe docs cover the full lifecycle:

- Add custom recipes
- Remove or patch upstream recipes
- Use filters for safe batch operations`,
                                        {
                                            src: "/imgs/screenshots/nav/en-kubejs-1201-intro.png",
                                            background:
                                                "linear-gradient(135deg, #6a040f 0%, #f48c06 100%)",
                                        },
                                    ),
                                },
                                {
                                    text: "Upgrade",
                                    link: "/modpack/kubejs/1.20.1/Upgrade/",
                                    desc: "Migration notes and compatibility adjustments.",
                                    preview: kubePreview(
                                        "1.20.1 Upgrade",
                                        "Migration checklist for older scripts.",
                                        `Use this before porting old script sets:

- JavaScript behavior changes
- GlobalScope updates and renamed helpers
- Variable and data-type compatibility notes`,
                                        {
                                            src: "/imgs/screenshots/nav/en-kubejs-1201-intro.png",
                                            background:
                                                "linear-gradient(135deg, #6c584c 0%, #dda15e 100%)",
                                        },
                                    ),
                                },
                                {
                                    text: "CodeShare",
                                    link: "/modpack/kubejs/1.20.1/CodeShare/",
                                    desc: "Community snippets for direct adaptation.",
                                    preview: kubePreview(
                                        "1.20.1 CodeShare",
                                        "Reusable script patterns from real packs.",
                                        `Use CodeShare to bootstrap implementation:

- Compact examples for common mechanics
- Practical snippets you can refactor into your pack
- Good source for style and structure conventions`,
                                        {
                                            src: "/imgs/screenshots/nav/en-kubejs-1201-intro.png",
                                            background:
                                                "linear-gradient(135deg, #3a0ca3 0%, #7209b7 100%)",
                                        },
                                    ),
                                },
                                {
                                    text: "KubeJS Course",
                                    href: kubejsCourseUrl,
                                    desc: "Third-party GitBook course with long-form tutorials and addon practice.",
                                    badge: {
                                        text: "3rd-party",
                                        type: "info",
                                    },
                                    preview: kubePreview(
                                        "1.20.1 KubeJS Course",
                                        "Externally maintained GitBook course from the third-party author team.",
                                        `Course content includes:

- KubeJS basics and advanced modules
- Addon ecosystem walkthroughs
- Project-style examples and resource pipelines`,
                                        {
                                            src: "/imgs/screenshots/nav/en-kubejs-course.png",
                                            background:
                                                "linear-gradient(140deg, #1f2937 0%, #3b82f6 100%)",
                                        },
                                    ),
                                },
                            ],
                        },
                    ],
                },
                {
                    weight: 1,
                    groups: [
                        {
                            label: "Version Hub",
                            items: [
                                {
                                    text: "KubeJS Home",
                                    link: "/modpack/kubejs/",
                                    desc: "Top-level portal for all version branches.",
                                    preview: kubePreview(
                                        "KubeJS Version Hub",
                                        "Global entry for picking the right version path.",
                                        `Use the hub when routing contributors:

- Pick docs by Minecraft target version
- Keep onboarding aligned to actual pack runtime
- Avoid API mismatches across branches`,
                                        "linear-gradient(135deg, #283618 0%, #606c38 100%)",
                                    ),
                                },
                            ],
                        },
                        {
                            label: "1.19.2",
                            items: [
                                {
                                    text: "Overview",
                                    link: "/modpack/kubejs/1.19.2/",
                                    desc: "Legacy branch for existing 1.19.2 packs.",
                                    badge: {
                                        text: "legacy",
                                        type: "info",
                                    },
                                    preview: kubePreview(
                                        "KubeJS 1.19.2",
                                        "Maintenance path for older 1.19.2 deployments.",
                                        `Use this branch for compatibility work:

- Keep legacy script packs maintainable
- Validate old API assumptions before migrating
- Pair with XPlus tutorials for applied examples`,
                                        "linear-gradient(135deg, #495057 0%, #6c757d 100%)",
                                    ),
                                },
                                {
                                    text: "XPlus Tutorial",
                                    link: "/modpack/kubejs/1.19.2/XPlusKubeJSTutorial/README",
                                    desc: "Applied examples and community guidance.",
                                    preview: kubePreview(
                                        "1.19.2 XPlus Tutorial",
                                        "Practical scripts for legacy pack operations.",
                                        `Useful for quick implementation references:

- Readable scenario-driven examples
- Practical troubleshooting hints
- Faster onboarding for older project stacks`,
                                        "linear-gradient(135deg, #33415c 0%, #5c677d 100%)",
                                    ),
                                },
                            ],
                        },
                        {
                            label: "1.18.2",
                            items: [
                                {
                                    text: "Overview",
                                    link: "/modpack/kubejs/1.18.2/",
                                    desc: "Oldest maintained branch in this repository.",
                                    badge: {
                                        text: "legacy",
                                        type: "warning",
                                    },
                                    preview: kubePreview(
                                        "KubeJS 1.18.2",
                                        "Archive-compatible path for long-lived packs.",
                                        `Use only for 1.18.2 maintenance:

- Preserve stability in old servers
- Document known version constraints
- Treat as reference when planning migrations`,
                                        "linear-gradient(135deg, #5f0f40 0%, #9a031e 100%)",
                                    ),
                                },
                                {
                                    text: "XPlus Tutorial",
                                    link: "/modpack/kubejs/1.18.2/XPlusKubeJSTutorial/README",
                                    desc: "Legacy tutorial examples for 1.18.2.",
                                    preview: kubePreview(
                                        "1.18.2 XPlus Tutorial",
                                        "Scenario snippets for historical script setups.",
                                        `These examples help when touching very old packs:

- Demonstrate legacy script style
- Provide quick testable references
- Useful for phased migration preparation`,
                                        "linear-gradient(135deg, #540b0e 0%, #9e2a2b 100%)",
                                    ),
                                },
                            ],
                        },
                    ],
                },
];

const kubeNav = createDropdownNavItem(
    "KubeJS",
    createNavDropdown(kubePanels, {
        layout: "columns",
    }),
);

const docsPanels: NavPanel[] = [
            {
                groups: [
                    {
                        label: "Documentation",
                        items: [
                            {
                                text: "Docs Home",
                                link: "/doc/",
                                desc: "Documentation index",
                                preview: kubePreview(
                                    "Docs Home",
                                    "Start here for the site’s documentation system.",
                                    `Browse the documentation by intent:

- framework rules and extension contracts
- plugin and component authoring guides
- writing workflow and sidebar maintenance`,
                                    {
                                        src: "/imgs/screenshots/nav/en-doc-home.png",
                                        background:
                                            "linear-gradient(140deg, #162034 0%, #243b63 100%)",
                                        aspect: "16 / 10",
                                    },
                                ),
                            },
                            {
                                text: "Cooperation Rules",
                                link: "/doc/rules",
                                desc: "Contribution and collaboration standards",
                                preview: kubePreview(
                                    "Cooperation Rules",
                                    "Editorial and engineering collaboration expectations.",
                                    `Use this page before making shared structural changes.

| Focus | Why it matters |
| --- | --- |
| naming | keeps content searchable |
| review flow | avoids drift across repos |
| contribution style | keeps docs consistent |`,
                                    {
                                        src: "/imgs/screenshots/rulesDark.png",
                                        background:
                                            "linear-gradient(135deg, #2a2238 0%, #3f2f56 100%)",
                                        aspect: "16 / 10",
                                    },
                                ),
                            },
                            {
                                text: "Plugins Guide",
                                link: "/doc/pluginsGuide",
                                desc: "Markdown plugins and component usage",
                                preview: kubePreview(
                                    "Plugins Guide",
                                    "Reference for rich markdown containers and custom rendering.",
                                    `This guide covers the site’s richer authoring surface:

- markdown-it containers
- component-backed blocks
- extension-safe plugin usage`,
                                    {
                                        src: "/imgs/screenshots/nav/en-doc-plugins.png",
                                        background:
                                            "linear-gradient(135deg, #1c2438 0%, #33507b 100%)",
                                        aspect: "16 / 10",
                                    },
                                ),
                            },
                            {
                                text: "Guide",
                                link: "/doc/guide/",
                                desc: "Onboarding and topic navigation",
                                preview: kubePreview(
                                    "Guide",
                                    "Orientation page for navigating the doc set quickly.",
                                    `Use the guide when you need routing more than deep implementation detail.

> It is the fastest way to decide where a topic belongs before editing.`,
                                    {
                                        src: "/imgs/screenshots/nav/en-doc-home.png",
                                        background:
                                            "linear-gradient(135deg, #1a2430 0%, #30475a 100%)",
                                        aspect: "16 / 10",
                                    },
                                ),
                            },
                        ],
                    },
                    {
                        label: "Framework",
                        items: [
                            {
                                text: "Framework Maintainability",
                                link: "/doc/frameworkMaintainability",
                                desc: "High-level engineering rules and extension standards",
                                preview: kubePreview(
                                    "Framework Maintainability",
                                    "Architecture rules for keeping the site stable.",
                                    `Read this before touching runtime, theme, or shared contracts.

- ownership boundaries
- sync expectations
- maintenance-safe extension rules`,
                                    {
                                        src: "/imgs/screenshots/nav/en-doc-framework.png",
                                        background:
                                            "linear-gradient(135deg, #1e2233 0%, #314a77 100%)",
                                        aspect: "16 / 10",
                                    },
                                ),
                            },
                            {
                                text: "Development Workflow",
                                link: "/doc/developmentWorkflow",
                                desc: "Change order, verification, and sync expectations",
                                preview: kubePreview(
                                    "Development Workflow",
                                    "Order of operations for safe changes across the project family.",
                                    `Follow this workflow for structural work:

1. change shared contract
2. update components/runtime
3. verify build and locale output
4. sync template-derived surfaces`,
                                    {
                                        src: "/imgs/screenshots/nav/en-doc-framework.png",
                                        background:
                                            "linear-gradient(135deg, #16253b 0%, #234d68 100%)",
                                        aspect: "16 / 10",
                                    },
                                ),
                            },
                            {
                                text: "Extension Architecture",
                                link: "/doc/extensionArchitecture",
                                desc: "Where config, runtime, components, and styles belong",
                                preview: kubePreview(
                                    "Extension Architecture",
                                    "Responsibility map for config, runtime, components, and styles.",
                                    `Use this when you need to decide **where** a change belongs.

| Surface | Place it in |
| --- | --- |
| locale/config | config tree |
| runtime state | runtime modules |
| UI rendering | theme components |`,
                                    {
                                        src: "/imgs/screenshots/nav/en-doc-framework.png",
                                        background:
                                            "linear-gradient(135deg, #192035 0%, #234860 100%)",
                                        aspect: "16 / 10",
                                    },
                                ),
                            },
                            {
                                text: "Hero Extension",
                                link: "/doc/heroExtension",
                                desc: "Typography, floating, shader, and background extension playbook",
                                preview: kubePreview(
                                    "Hero Extension",
                                    "Practical playbook for typography, floating items, shaders, and background layers.",
                                    `This is the Hero-specific extension manual:

- typography style registration
- floating element strategy
- shader/background extension boundaries`,
                                    {
                                        src: "/imgs/screenshots/nav/en-doc-framework.png",
                                        background:
                                            "linear-gradient(135deg, #111d34 0%, #2b355d 100%)",
                                        aspect: "16 / 10",
                                    },
                                ),
                            },
                        ],
                    },
                ],
            },
            {
                groups: [
                    {
                        label: "Authoring Tools",
                        items: [
                            {
                                text: "Sidebar Guide",
                                link: "/doc/sidebarGuide",
                                desc: "Sidebar config and structure workflow",
                                preview: kubePreview(
                                    "Sidebar Guide",
                                    "Reference for section structure, landing pages, and generated sidebar behavior.",
                                    `Use this when editing directory entries, auto-index pages, or landing routes.

- sidebar source files
- generated landing behavior
- cache and rebuild expectations`,
                                    {
                                        src: "/imgs/screenshots/nav/en-doc-sidebar.png",
                                        background:
                                            "linear-gradient(135deg, #1a2337 0%, #3a547c 100%)",
                                        aspect: "16 / 10",
                                    },
                                ),
                            },
                            {
                                text: "VSCode Snippets",
                                link: "/doc/vscodeSnippetsGuide",
                                desc: "Snippet-driven writing workflow",
                                preview: kubePreview(
                                    "VSCode Snippets",
                                    "Author faster with frontmatter and content snippets.",
                                    `This guide focuses on writing efficiency:

- frontmatter completions
- repeatable doc section scaffolds
- safer authoring for large doc sets`,
                                    {
                                        src: "/imgs/VSCode/1.png",
                                        background:
                                            "linear-gradient(135deg, #111827 0%, #1d4ed8 100%)",
                                        aspect: "16 / 10",
                                    },
                                ),
                            },
                            {
                                text: "LiteTree Guide",
                                link: "/doc/litetreeGuide",
                                desc: "Tree-based presentation components",
                                preview: kubePreview(
                                    "LiteTree Guide",
                                    "Guide to tree-based presentation blocks for hierarchical explanations.",
                                    `Use LiteTree when content benefits from structured reveal instead of long paragraphs.

> It is especially useful for APIs, workflows, and dependency maps.`,
                                    {
                                        src: "/imgs/screenshots/nav/en-doc-sidebar.png",
                                        background:
                                            "linear-gradient(135deg, #1b263b 0%, #415a77 100%)",
                                        aspect: "16 / 10",
                                    },
                                ),
                            },
                        ],
                    },
                ],
            },
];

const docsPreview = createNavPreviewPanel(
    "Documentation Hub",
    "Engineering rules, authoring tools, and extension playbooks.",
    `| Track | What it gives you |
| --- | --- |
| Framework | Structure, extension boundaries, Hero rules |
| Authoring | Sidebar, snippets, and writing workflows |
| Plugins | Rich Markdown containers and custom components |

> Treat the docs hub as the source of truth before changing theme, runtime, or content architecture.`,
    previewMedia(
        "Documentation Hub",
        "/imgs/screenshots/nav/en-doc-home.png",
        "linear-gradient(140deg, #162034 0%, #243b63 100%)",
    ),
    {
        link: "/doc/",
    },
);

const docsNav = createDropdownNavItem(
    "Docs",
    createNavDropdown(docsPanels, {
        layout: "columns",
        preview: docsPreview,
    }),
);

const contentPanels: NavPanel[] = [
            {
                groups: [
                    {
                        label: "Core Sections",
                        items: [
                            {
                                text: "Mods",
                                link: "/mods/",
                                desc: "Mod references by category",
                                preview: kubePreview(
                                    "Mods",
                                    "Reference pages and notes grouped by mod category.",
                                    `Use Mods when you need a topic-oriented lookup instead of a pack-oriented walkthrough.`,
                                    {
                                        src: "/imgs/screenshots/nav/en-mods-home.png",
                                        background:
                                            "linear-gradient(135deg, #18223b 0%, #334e68 100%)",
                                        aspect: "16 / 10",
                                    },
                                ),
                            },
                            {
                                text: "Modpack",
                                link: "/modpack/",
                                desc: "Modpack docs and recommendations",
                                preview: kubePreview(
                                    "Modpack",
                                    "Pack-specific routes, versioned KubeJS docs, and implementation references.",
                                    `This is the right path when the docs depend on a target runtime or pack branch.`,
                                    {
                                        src: "/imgs/screenshots/nav/en-modpack-home.png",
                                        background:
                                            "linear-gradient(135deg, #1a2a45 0%, #31517d 100%)",
                                        aspect: "16 / 10",
                                    },
                                ),
                            },
                            {
                                text: "Develop",
                                link: "/develop/",
                                desc: "Development and modding guides",
                                preview: kubePreview(
                                    "Develop",
                                    "Implementation guides for tooling, data packs, and author workflows.",
                                    `Good entry when you need build guidance more than content browsing.`,
                                    {
                                        src: "/imgs/screenshots/nav/en-develop-home.png",
                                        background:
                                            "linear-gradient(135deg, #1a2238 0%, #35506f 100%)",
                                        aspect: "16 / 10",
                                    },
                                ),
                            },
                            {
                                text: "Info",
                                link: "/info/",
                                desc: "Q&A, requests, and discussion entries",
                                preview: kubePreview(
                                    "Info",
                                    "Support-style entries, questions, and community-requested notes.",
                                    `Use Info when you are looking for issue context, requests, or quick answers outside the main docs tree.`,
                                    {
                                        src: "/imgs/screenshots/nav/en-info-home.png",
                                        background:
                                            "linear-gradient(135deg, #2d1f3f 0%, #54406f 100%)",
                                        aspect: "16 / 10",
                                    },
                                ),
                            },
                        ],
                    },
                ],
            },
            {
                groups: [
                    {
                        label: "Community",
                        items: [
                            {
                                text: "Tags",
                                link: "/tags",
                                desc: "Topic index across all pages",
                                preview: kubePreview(
                                    "Tags",
                                    "Cross-cutting index for finding related pages without knowing the exact section first.",
                                    `Tags help when content spans multiple sections or when route memory is weak.`,
                                    {
                                        src: "/imgs/screenshots/nav/en-tags-home.png",
                                        background:
                                            "linear-gradient(135deg, #172234 0%, #3b5872 100%)",
                                        aspect: "16 / 10",
                                    },
                                ),
                            },
                        ],
                    },
                ],
            },
];

const contentPreview = createNavPreviewPanel(
    "Content Atlas",
    "Browse the site by content type instead of version branch.",
    `| Section | Best for |
| --- | --- |
| Mods | reference pages and mod notes |
| Modpack | pack-specific knowledge and KubeJS branches |
| Develop | authoring and implementation guides |
| Info | requests, Q&A, and support-oriented pages |`,
    previewMedia(
        "Content Atlas",
        "/imgs/screenshots/nav/en-modpack-home.png",
        "linear-gradient(135deg, #18253d 0%, #324e7c 100%)",
    ),
    {
        link: "/modpack/",
    },
);

const contentNav = createDropdownNavItem(
    "Content",
    createNavDropdown(contentPanels, {
        layout: "columns",
        preview: contentPreview,
    }),
);

const enNav: NavItem[] = createNavItems(homeNav, kubeNav, docsNav, contentNav);

export default enNav;
