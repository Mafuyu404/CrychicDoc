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

const homeNav = createLinkedNavItem("首页", "/");

const kubePreviewMedia = createScreenshotMedia(
    "KubeJS 分区",
    "/imgs/screenshots/nav/zh-kubejs-1201-intro.png",
    "linear-gradient(135deg, #1d3557 0%, #457b9d 100%)",
);

const modsPreviewMedia = createScreenshotMedia(
    "模组分区",
    "/imgs/screenshots/nav/zh-mods-home.png",
    "linear-gradient(135deg, #18223b 0%, #334e68 100%)",
);

const modpackPreviewMedia = createScreenshotMedia(
    "整合包分区",
    "/imgs/screenshots/nav/zh-modpack-home.png",
    "linear-gradient(135deg, #1a2a45 0%, #31517d 100%)",
);

const developPreviewMedia = createScreenshotMedia(
    "开发分区",
    "/imgs/screenshots/nav/zh-develop-home.png",
    "linear-gradient(135deg, #1a2238 0%, #35506f 100%)",
);

const supportPreviewMedia = createScreenshotMedia(
    "文档与信息",
    "/imgs/screenshots/nav/zh-doc-home.png",
    "linear-gradient(140deg, #162034 0%, #243b63 100%)",
);

const supportDocsGroup = createNavGroup("站点文档", [
    createNavLink("文档规范", "/doc/Catalogue", {
        desc: "协作流程、结构约定与写作边界",
    }),
    createNavLink("侧边栏指南", "/doc/sidebarGuide", {
        desc: "目录、排序与落地页规则",
    }),
    createNavLink("样式与插件", "/doc/pluginsGuide", {
        desc: "Markdown 扩展、容器与自定义组件",
    }),
    createNavLink("LiteTree组件", "/doc/litetreeGuide", {
        desc: "文档树形结构的写法与展示方式",
    }),
]);

const supportGuideGroup = createNavGroup("专题导览", [
    createNavLink("社区链接", "/doc/guide/community", {
        desc: "社区站点、代码托管与常用外链",
    }),
    createNavLink("资源工具", "/doc/guide/resource", {
        desc: "开发与创作常用工具",
    }),
    createNavLink("Minecraft 站点", "/doc/guide/minecraft", {
        desc: "Minecraft 资料与站点入口",
    }),
    createNavLink("像素艺术", "/doc/guide/pixelart", {
        desc: "像素画与建模工具整理",
    }),
    createNavLink("KubeJS 链接", "/doc/guide/KubeJS", {
        desc: "KubeJS 社区与相关资源入口",
    }),
]);

const supportInfoGroup = createNavGroup("支持与说明", [
    createNavLink("总览", "/info/Catalogue", {
        desc: "问答、建议与补充说明",
    }),
    createNavLink("文档问答", "/info/doc/Q&A/Catalogue", {
        desc: "关于文档的常见问题",
    }),
    createNavLink("KubeJS 问答", "/info/KubeJS/Q&A/Catalogue", {
        desc: "KubeJS 相关问题整理",
    }),
    createNavLink("讨论看板", "/info/KubeJS/borad/Catalogue", {
        desc: "代码分享、纠错、招募与社区话题",
    }),
    createNavLink(
        "模块请求",
        "/info/doc/module-request/Catalogue",
        {
            desc: "新增模块与内容请求",
        },
    ),
]);

const kubeNav = createDropdownNavItem(
    "KubeJS",
    createNavDropdown(
        [
            createNavPanel([
                createNavGroup("主要维护版本", [
                    createNavLink(
                        "1.21",
                        "/modpack/kubejs/1.21/Catalogue",
                        {
                            desc: "当前优先维护分支",
                            badge: createNavBadge("推荐", "new", {
                                pulse: true,
                            }),
                        },
                    ),
                    createNavLink(
                        "1.20.1",
                        "/modpack/kubejs/1.20.1/Catalogue",
                        {
                            desc: "目前内容最完整的主干",
                            badge: createNavBadge("完整", "info"),
                        },
                    ),
                    createNavLink(
                        "升级说明",
                        "/modpack/kubejs/1.20.1/Upgrade/Catalogue",
                        {
                            desc: "旧脚本迁移与兼容差异",
                        },
                    )
                ]),
            ]),
            createNavPanel([
                createNavGroup("历史版本文档", [
                    createNavLink(
                        "版本总览",
                        "/modpack/kubejs/Catalogue",
                        {
                            desc: "按 Minecraft 版本进入",
                        },
                    ),
                    createNavLink(
                        "1.19.2",
                        "/modpack/kubejs/1.19.2/Catalogue",
                        {
                            desc: "遗留维护分支",
                            badge: createNavBadge("遗留", "warning"),
                        },
                    ),
                    createNavLink(
                        "1.18.2",
                        "/modpack/kubejs/1.18.2/Catalogue",
                        {
                            desc: "较旧版本存量维护",
                            badge: createNavBadge("遗留", "warning"),
                        },
                    ),
                ]),
            ]),
            createNavPanel([
                createNavGroup("官方文档", [
                    createNavLink("官方 Wiki", "https://kubejs.com/wiki", {
                        desc: "KubeJS 官方文档",
                        badge: createNavBadge("外部", "info"),
                    }),
                    createNavLink("下载页面", "https://kubejs.com/downloads", {
                        desc: "官方版本与下载入口",
                        badge: createNavBadge("外部", "info"),
                    }),
                    createNavLink(
                        "GitHub",
                        "https://github.com/KubeJS-Mods/KubeJS/tree/2001",
                        {
                            desc: "官方源码仓库",
                            badge: createNavBadge("外部", "info"),
                        },
                    ),
                    createNavLink("Discord", "https://discord.gg/lat", {
                        desc: "KubeJS 社区讨论",
                        badge: createNavBadge("外部", "info"),
                    }),
                ]),
                createNavGroup("第三方文档", [
                    createNavLink(
                        "KubeJS Course 1.20.1",
                        "/modpack/kubejs/1.20.1/KubeJSCourse/README",
                        {
                            desc: "站内整理的 1.20.1 第三方课程入口",
                            badge: createNavBadge("站内整理", "warning"),
                        },
                    ),
                    createNavLink(
                        "XPlusKubeJSTutorial 1.19.2",
                        "/modpack/kubejs/1.19.2/XPlusKubeJSTutorial/README",
                        {
                            desc: "Wudji 编写的 1.19.2 第三方教程正文",
                            badge: createNavBadge("站内整理", "warning"),
                        },
                    ),
                    createNavLink(
                        "XPlusKubeJSTutorial 1.18.2",
                        "/modpack/kubejs/1.18.2/XPlusKubeJSTutorial/README",
                        {
                            desc: "Wudji 编写的 1.18.2 第三方教程正文",
                            badge: createNavBadge("站内整理", "warning"),
                        },
                    ),
                ]),
            ]),
        ],
        {
            layout: "columns",
            preview: createNavPreviewPanel(
                "KubeJS 分区",
                "优先从站内版本目录进入，再根据需要跳转到官方或第三方文档。",
                `| 路径 | 适合用途 |
| --- | --- |
| 1.21 | 当前维护中的新版本内容 |
| 1.20.1 | 内容最完整，适合系统查阅 |
| Upgrade | 脚本迁移与版本差异 |
| 官方文档 | 权威定义、下载、源码 |
| 第三方文档 | 直达 1.20.1 Course 与 1.19.2/1.18.2 XPlus 教程正文 |`,
                kubePreviewMedia,
                { link: "/modpack/kubejs/Catalogue" },
            ),
        },
    ),
);

const modsNav = createDropdownNavItem(
    "模组",
    createNavDropdown(
        [
            createNavPanel([
                createNavGroup("分类", [
                    createNavLink("总览", "/mods/Catalogue", {
                        desc: "所有模组分类入口",
                    }),
                    createNavLink("科技", "/mods/tech/Catalogue", {
                        desc: "科技向模组与资料",
                    }),
                    createNavLink("冒险", "/mods/adventure/Catalogue", {
                        desc: "冒险与战斗相关内容",
                    }),
                    createNavLink("仓储", "/mods/storages/Catalogue", {
                        desc: "仓储与物流类内容",
                    }),
                    createNavLink("性能", "/mods/performance/Catalogue", {
                        desc: "客户端与服务端优化",
                    }),
                    createNavLink("自定义", "/mods/custom/Catalogue", {
                        desc: "自定义内容与特化条目",
                    }),
                ]),
            ]),
        ],
        {
            layout: "columns",
            preview: createNavPreviewPanel(
                "模组分区",
                "按主题查看模组资料，而不是按版本线索查找。",
                `| 分类 | 内容方向 |
| --- | --- |
| 科技 | 机制、流程、自动化 |
| 冒险 | 战斗、附魔、探索 |
| 仓储 | 存储与物流 |
| 性能 | 客户端/服务端优化 |
| 自定义 | 特化模组与项目页 |`,
                modsPreviewMedia,
                { link: "/mods/Catalogue" },
            ),
        },
    ),
);

const modpackNav = createDropdownNavItem(
    "整合包",
    createNavDropdown(
        [
            createNavPanel([
                createNavGroup("内容入口", [
                    createNavLink("总览", "/modpack/Catalogue", {
                        desc: "整合包相关内容入口",
                    }),
                    createNavLink("推荐", "/modpack/recommendation/Catalogue", {
                        desc: "整合包推荐与整理",
                    }),
                    createNavLink("KubeJS 分区", "/modpack/kubejs/Catalogue", {
                        desc: "独立的 KubeJS 版本与专题入口",
                    }),
                ]),
            ]),
        ],
        {
            layout: "columns",
            preview: createNavPreviewPanel(
                "整合包分区",
                "保留整合包主题入口，同时把 KubeJS 单独抬成主导航。",
                `| 入口 | 作用 |
| --- | --- |
| 总览 | 浏览整合包相关内容 |
| 推荐 | 查看整合包推荐与整理 |
| KubeJS 分区 | 进入独立的脚本与版本文档 |`,
                modpackPreviewMedia,
                { link: "/modpack/Catalogue" },
            ),
        },
    ),
);

const developNav = createDropdownNavItem(
    "开发",
    createNavDropdown(
        [
            createNavPanel([
                createNavGroup("内容入口", [
                    createNavLink("总览", "/develop/Catalogue", {
                        desc: "开发内容总入口",
                    }),
                ]),
                createNavGroup("主要维护版本", [
                    createNavLink(
                        "NeoForge 1.20.4",
                        "/develop/modding/1.20.4/Neoforge/Catalogue",
                        {
                            desc: "当前已整理的 NeoForge 路线",
                            badge: createNavBadge("NeoForge", "new"),
                        },
                    ),
                    createNavLink(
                        "Minecraft 1.21",
                        "/develop/modding/1.21/Catalogue",
                        {
                            desc: "面向 1.21 的开发笔记",
                        },
                    ),
                    createNavLink("Mixin", "/develop/modding/Mixin/Catalogue", {
                        desc: "纳入当前维护路径的 Mixin 说明",
                    }),
                ]),
            ]),
            createNavPanel([
                createNavGroup("资源与工具", [
                    createNavLink("插件", "/develop/plugin/Catalogue", {
                        desc: "站点插件与扩展能力",
                    }),
                    createNavLink("数据包", "/develop/vanilla/datapack/Catalogue", {
                        desc: "Datapack 开发内容",
                    }),
                    createNavLink(
                        "资源包",
                        "/develop/vanilla/resourcepack/Catalogue",
                        {
                            desc: "Resource Pack 相关内容",
                        },
                    ),
                ]),
                createNavGroup("官方参考", [
                    createNavLink(
                        "NeoForged Docs",
                        "https://docs.neoforged.net/",
                        {
                            desc: "NeoForged 官方文档",
                            badge: createNavBadge("外部", "info"),
                        },
                    ),
                    createNavLink(
                        "Toolchain Docs",
                        "https://docs.neoforged.net/toolchain/docs/",
                        {
                            desc: "NeoForged 工具链文档入口",
                            badge: createNavBadge("外部", "info"),
                        },
                    ),
                    createNavLink(
                        "NeoGradle 插件",
                        "https://docs.neoforged.net/toolchain/docs/plugins/ng/",
                        {
                            desc: "NeoGradle 插件文档",
                            badge: createNavBadge("外部", "info"),
                        },
                    ),
                    createNavLink(
                        "NeoForged 官网",
                        "https://neoforged.net/",
                        {
                            desc: "项目站点与版本信息",
                            badge: createNavBadge("外部", "info"),
                        },
                    ),
                ]),
            ]),
        ],
        {
            layout: "columns",
            preview: createNavPreviewPanel(
                "开发分区",
                "站内内容负责整理路径，官方文档负责权威定义，两者不要混为一谈。",
                `| 层级 | 内容定位 |
| --- | --- |
| 站内开发文档 | 面向 CrychicDoc 当前整理结构 |
| NeoForge 专题 | 本站收录的 NeoForge 学习线 |
| 官方参考 | NeoForged 文档、Toolchain 与 NeoGradle 插件原始文档 |`,
                developPreviewMedia,
                { link: "/develop/Catalogue" },
            ),
        },
    ),
);

const supportNav = createDropdownNavItem(
    "文档与信息",
    createNavDropdown(
        [
            createNavPanel([supportDocsGroup, supportInfoGroup]),
            createNavPanel([supportGuideGroup]),
        ],
        {
            layout: "columns",
            preview: createNavPreviewPanel(
                "文档与信息",
                "站点文档与信息支持共用一列，Guide 导览独立一列，结构更紧凑但仍然分工明确。",
                `| 分栏 | 内容定位 |
| --- | --- |
| 文档与信息列 | 文档规范、侧边栏与插件指南、问答、讨论看板与模块请求 |
| Guide 导览列 | 社区链接、资源工具与专题入口 |`,
                supportPreviewMedia,
                { link: "/doc/Catalogue" },
            ),
        },
    ),
);

const zhNav: NavItem[] = createNavItems(
    homeNav,
    kubeNav,
    modsNav,
    modpackNav,
    developNav,
    supportNav,
);

export default zhNav;
