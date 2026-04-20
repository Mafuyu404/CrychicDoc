import { NavItem } from "@utils/config/navTypes";

class NavTemplatePreviewFactory {
    create(
        title: string,
        desc: string,
        body: string,
        background: string,
    ) {
        return {
            title,
            desc,
            body,
            media: {
                type: "screenshot" as const,
                background,
                aspect: "21 / 9",
                alt: title,
            },
        };
    }
}

class NavTemplateBuilder {
    private readonly previewFactory = new NavTemplatePreviewFactory();

    build(): NavItem[] {
        return [
            this.createHomeItem(),
            this.createDocsItem(),
            this.createResourcesItem(),
        ];
    }

    private createHomeItem(): NavItem {
        return {
            text: "Home",
            link: "/",
        };
    }

    private createDocsItem(): NavItem {
        return {
            text: "Docs",
            dropdown: {
                layout: "spotlight",
                panels: [
                    {
                        featured: {
                            title: "Getting Started",
                            desc: "Project onboarding and structure overview.",
                            link: "/doc/",
                            media: {
                                type: "image",
                                src: "/svg/logo.svg",
                                alt: "Project logo",
                                background:
                                    "linear-gradient(135deg, rgba(15, 23, 42, 0.9) 0%, rgba(30, 64, 175, 0.85) 100%)",
                            },
                        },
                        groups: [
                            {
                                label: "Core",
                                items: [
                                    {
                                        text: "Architecture",
                                        link: "/doc/frameworkMaintainability",
                                        desc: "System architecture and extension model.",
                                        preview: this.previewFactory.create(
                                            "Architecture",
                                            "How navigation, hero, and metadata layers are organized.",
                                            `Use this section to standardize team contributions:

- Navigation runtime and locale contract
- Hero frontmatter contract and theme-sync rules
- Metadata and shared utility API boundaries`,
                                            "linear-gradient(135deg, #1d3557 0%, #457b9d 100%)",
                                        ),
                                    },
                                    {
                                        text: "Plugins Guide",
                                        link: "/doc/pluginsGuide",
                                        desc: "Supported rich containers and syntax extensions.",
                                        preview: this.previewFactory.create(
                                            "Plugins Guide",
                                            "Authoring syntax for rich documentation pages.",
                                            `Include formal examples for:

- Custom containers
- Tabs and code groups
- Embedded media and advanced markdown blocks`,
                                            "linear-gradient(135deg, #2a9d8f 0%, #1d3557 100%)",
                                        ),
                                    },
                                ],
                            },
                            {
                                label: "Developer Guides",
                                items: [
                                    {
                                        text: "Framework Maintainability",
                                        link: "/doc/frameworkMaintainability",
                                        desc: "High-level engineering rules and extension standards.",
                                    },
                                    {
                                        text: "Extension Architecture",
                                        link: "/doc/extensionArchitecture",
                                        desc: "Placement guide for components, runtime, config, and styles.",
                                    },
                                    {
                                        text: "Hero Extension",
                                        link: "/doc/heroExtension",
                                        desc: "Typography, floating, shader, and background extension playbook.",
                                    },
                                ],
                            },
                        ],
                    },
                ],
            },
        };
    }

    private createResourcesItem(): NavItem {
        return {
            text: "Resources",
            dropdown: {
                layout: "columns",
                panels: [
                    {
                        weight: 1,
                        groups: [
                            {
                                label: "Internal",
                                items: [
                                    {
                                        text: "KubeJS Hub",
                                        link: "/modpack/kubejs/",
                                        desc: "Version-oriented KubeJS document index.",
                                    },
                                    {
                                        text: "Develop",
                                        link: "/develop/",
                                        desc: "Development standards and implementation references.",
                                    },
                                ],
                            },
                        ],
                    },
                    {
                        weight: 1,
                        groups: [
                            {
                                label: "External",
                                items: [
                                    {
                                        text: "GitHub",
                                        href: "https://github.com/",
                                        desc: "Source hosting and review workflow.",
                                    },
                                    {
                                        text: "Discord",
                                        href: "https://discord.gg/",
                                        desc: "Community communication channel.",
                                    },
                                ],
                            },
                        ],
                    },
                ],
            },
        };
    }
}

export function createNavTemplate() {
    return new NavTemplateBuilder().build();
}

const navTemplate = createNavTemplate();

export default navTemplate;
