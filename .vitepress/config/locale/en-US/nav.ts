import type { NavItem } from "../../../utils/config/navTypes";

const kubePreview = (
    title: string,
    desc: string,
    body: string,
    background: string,
) => ({
    title,
    desc,
    body,
    media: {
        type: "screenshot" as const,
        background,
        aspect: "21 / 9",
        alt: title,
    },
});

const enNav: NavItem[] = [
    {
        text: "Home",
        link: "/",
    },
    {
        text: "KubeJS",
        dropdown: {
            layout: "columns",
            panels: [
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
                                        "linear-gradient(135deg, #0d3b66 0%, #2a9d8f 100%)",
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
                                        "linear-gradient(140deg, #264653 0%, #3a86ff 100%)",
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
                                        "linear-gradient(135deg, #3d405b 0%, #81b29a 100%)",
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
                                        "linear-gradient(130deg, #4a4e69 0%, #f2a65a 100%)",
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
                                        "linear-gradient(135deg, #1d3557 0%, #457b9d 100%)",
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
                                        "linear-gradient(140deg, #003049 0%, #2a9d8f 100%)",
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
                                        "linear-gradient(125deg, #1b4332 0%, #2d6a4f 100%)",
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
                                        "linear-gradient(135deg, #6a040f 0%, #f48c06 100%)",
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
                                        "linear-gradient(135deg, #6c584c 0%, #dda15e 100%)",
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
                                        "linear-gradient(135deg, #3a0ca3 0%, #7209b7 100%)",
                                    ),
                                },
                                {
                                    text: "KubeJS Course",
                                    link: "/modpack/kubejs/1.20.1/KubeJSCourse/README",
                                    desc: "Long-form tutorials, projects, and addon practices.",
                                    preview: kubePreview(
                                        "1.20.1 KubeJS Course",
                                        "Structured training content from basics to advanced.",
                                        `Course content includes:

- KubeJS basics and advanced modules
- Addon ecosystem walkthroughs
- Project-style examples and resource pipelines`,
                                        "linear-gradient(140deg, #1f2937 0%, #3b82f6 100%)",
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
            ],
        },
    },
    {
        text: "Docs",
        dropdown: {
            layout: "columns",
            panels: [
                {
                    groups: [
                        {
                            label: "Documentation",
                            items: [
                                {
                                    text: "Docs Home",
                                    link: "/doc/",
                                    desc: "Documentation index",
                                },
                                {
                                    text: "Cooperation Rules",
                                    link: "/doc/rules",
                                    desc: "Contribution and collaboration standards",
                                },
                                {
                                    text: "Plugins Guide",
                                    link: "/doc/pluginsGuide",
                                    desc: "Markdown plugins and component usage",
                                },
                                {
                                    text: "Guide",
                                    link: "/doc/guide/",
                                    desc: "Onboarding and topic navigation",
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
                                },
                                {
                                    text: "Development Workflow",
                                    link: "/doc/developmentWorkflow",
                                    desc: "Change order, verification, and sync expectations",
                                },
                                {
                                    text: "Extension Architecture",
                                    link: "/doc/extensionArchitecture",
                                    desc: "Where config, runtime, components, and styles belong",
                                },
                                {
                                    text: "Hero Extension",
                                    link: "/doc/heroExtension",
                                    desc: "Typography, floating, shader, and background extension playbook",
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
                                },
                                {
                                    text: "VSCode Snippets",
                                    link: "/doc/vscodeSnippetsGuide",
                                    desc: "Snippet-driven writing workflow",
                                },
                                {
                                    text: "LiteTree Guide",
                                    link: "/doc/litetreeGuide",
                                    desc: "Tree-based presentation components",
                                },
                            ],
                        },
                    ],
                },
            ],
        },
    },
    {
        text: "Content",
        dropdown: {
            layout: "columns",
            panels: [
                {
                    groups: [
                        {
                            label: "Core Sections",
                            items: [
                                {
                                    text: "Mods",
                                    link: "/mods/",
                                    desc: "Mod references by category",
                                },
                                {
                                    text: "Modpack",
                                    link: "/modpack/",
                                    desc: "Modpack docs and recommendations",
                                },
                                {
                                    text: "Develop",
                                    link: "/develop/",
                                    desc: "Development and modding guides",
                                },
                                {
                                    text: "Info",
                                    link: "/info/",
                                    desc: "Q&A, requests, and discussion entries",
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
                                    text: "Developers",
                                    link: "/developers/",
                                    desc: "Team and contributor spaces",
                                },
                                {
                                    text: "Tags",
                                    link: "/tags",
                                    desc: "Topic index across all pages",
                                },
                            ],
                        },
                    ],
                },
            ],
        },
    },
];

export default enNav;
