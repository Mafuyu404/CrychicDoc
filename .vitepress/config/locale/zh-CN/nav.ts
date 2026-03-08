import type { NavItem, NavPanel } from "../../../utils/config/navTypes";
import {
    createDropdownNavItem,
    createLinkedNavItem,
    createNavDropdown,
    createNavItems,
    createNavPreviewPanel,
    createShowcasePreview,
    createScreenshotMedia,
} from "../../../utils/config/navFactory";

const kubePreview = createShowcasePreview;
const previewMedia = createScreenshotMedia;
const kubejsCourseUrl = "/modpack/kubejs/1.20.1/KubeJSCourse/README";

const homeNav = createLinkedNavItem("首页", "/");

const kubePanels: NavPanel[] = [
    {
        weight: 1.15,
        groups: [
            {
                label: "1.21（最新）",
                items: [
                    {
                        text: "版本 1.21 总览",
                        link: "/modpack/kubejs/1.21/",
                        desc: "面向 Minecraft 1.21 现代整合包的最新分支。",
                        badge: {
                            text: "最新",
                            type: "new",
                            pulse: true,
                        },
                        preview: kubePreview(
                            "版本 1.21 文档总览",
                            "当前脚本分支的首选入口。",
                            `**适用于 Minecraft 1.21 整合包开发。**

- CrychicDoc 当前活跃分支
- 对齐最新 API 行为的最快路径
- 推荐阅读顺序：Introduction -> Addon -> LootJS

当运行目标为 1.21 时，请优先使用本分支。`,
                            {
                                src: "/imgs/screenshots/nav/zh-kubejs-121-home.png",
                                background:
                                    "linear-gradient(135deg, #0d3b66 0%, #2a9d8f 100%)",
                            },
                        ),
                    },
                    {
                        text: "Introduction",
                        link: "/modpack/kubejs/1.21/Introduction/",
                        desc: "脚本生命周期与结构模型的基础章节。",
                        preview: kubePreview(
                            "版本 1.21 Introduction",
                            "进入扩展层前的核心概念基础。",
                            `建议先建立以下认知：

- Startup / Server / Client 脚本边界
- 工作流与命名规范
- Addon 与 LootJS 前置上下文`,
                            {
                                src: "/imgs/screenshots/nav/zh-kubejs-121-home.png",
                                background:
                                    "linear-gradient(140deg, #264653 0%, #3a86ff 100%)",
                            },
                        ),
                    },
                    {
                        text: "Addon 扩展",
                        link: "/modpack/kubejs/1.21/Introduction/Addon/",
                        desc: "生态联动与扩展能力构建的正式参考。",
                        preview: kubePreview(
                            "版本 1.21 Addon 扩展",
                            "高级模块集成与兼容性管理参考。",
                            `重点包括：

- 模组能力接入
- 扩展功能组合
- 跨模块兼容约束`,
                            {
                                src: "/imgs/screenshots/nav/zh-kubejs-121-home.png",
                                background:
                                    "linear-gradient(135deg, #3d405b 0%, #81b29a 100%)",
                            },
                        ),
                    },
                    {
                        text: "LootJS 指南",
                        link: "/modpack/kubejs/1.21/Introduction/Addon/LootJs/LootJs",
                        desc: "聚焦掉落逻辑与战利品表扩展。",
                        preview: kubePreview(
                            "版本 1.21 LootJS",
                            "用于 Loot Modifier 与 Loot Table 的定向参考。",
                            `LootJS 是高影响力扩展路径：

- 构建可验证的确定性掉落逻辑
- 使用清晰脚本步骤修改既有战利品表
- 与事件系统联动实现动态进度控制`,
                            {
                                src: "/imgs/screenshots/nav/zh-kubejs-121-home.png",
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
                label: "1.20.1（核心路径）",
                items: [
                    {
                        text: "版本 1.20.1 总览",
                        link: "/modpack/kubejs/1.20.1/",
                        desc: "覆盖最完整的综合文档分支。",
                        badge: {
                            text: "完整",
                            type: "info",
                        },
                        preview: kubePreview(
                            "版本 1.20.1 文档总览",
                            "用于实施、迁移与复核的综合知识基线。",
                            `本分支包含：

- Introduction
- Event API
- Recipe API
- Upgrade
- CodeShare
- KubeJS 课程文档`,
                            {
                                src: "/imgs/screenshots/nav/zh-kubejs-1201-intro.png",
                                background:
                                    "linear-gradient(135deg, #1d3557 0%, #457b9d 100%)",
                            },
                        ),
                    },
                    {
                        text: "Introduction",
                        link: "/modpack/kubejs/1.20.1/Introduction/",
                        desc: "事件、配方、实体、标签等核心 API 基线。",
                        preview: kubePreview(
                            "版本 1.20.1 Introduction",
                            "面向 API 的学习路径，覆盖核心子系统。",
                            `建议重点阅读以下入口：

- 事件生命周期与脚本分区
- 配方与物品/方块注册
- 实体、标签、战利品表与网络参考`,
                            {
                                src: "/imgs/screenshots/nav/zh-kubejs-1201-intro.png",
                                background:
                                    "linear-gradient(140deg, #003049 0%, #2a9d8f 100%)",
                            },
                        ),
                    },
                    {
                        text: "事件系统",
                        link: "/modpack/kubejs/1.20.1/Introduction/Event/",
                        desc: "Startup、Server、Client 事件生命周期规范。",
                        preview: kubePreview(
                            "版本 1.20.1 事件系统",
                            "脚本驱动行为的核心执行中心。",
                            `请按运行时作用域规划事件钩子：

- StartupScript：用于注册阶段
- ServerScript：用于服务端逻辑
- ClientScript：用于客户端专属行为`,
                            {
                                src: "/imgs/screenshots/nav/zh-kubejs-1201-intro.png",
                                background:
                                    "linear-gradient(125deg, #1b4332 0%, #2d6a4f 100%)",
                            },
                        ),
                    },
                    {
                        text: "配方系统",
                        link: "/modpack/kubejs/1.20.1/Introduction/Recipe/",
                        desc: "新增、修改与移除配方的治理流程。",
                        preview: kubePreview(
                            "版本 1.20.1 配方系统",
                            "整合包配平最常用的实施章节。",
                            `配方文档覆盖完整生命周期：

- 新增自定义配方
- 移除或修补上游配方
- 使用过滤器执行安全批处理`,
                            {
                                src: "/imgs/screenshots/nav/zh-kubejs-1201-intro.png",
                                background:
                                    "linear-gradient(135deg, #6a040f 0%, #f48c06 100%)",
                            },
                        ),
                    },
                    {
                        text: "Upgrade",
                        link: "/modpack/kubejs/1.20.1/Upgrade/",
                        desc: "遗留脚本迁移与兼容性调整方法。",
                        preview: kubePreview(
                            "版本 1.20.1 Upgrade",
                            "旧脚本迁移前的核对清单。",
                            `在迁移旧脚本集前，请重点核对：

- JavaScript 行为差异
- GlobalScope 更新与重命名助手
- 变量与数据类型兼容性`,
                            {
                                src: "/imgs/screenshots/nav/zh-kubejs-1201-intro.png",
                                background:
                                    "linear-gradient(135deg, #6c584c 0%, #dda15e 100%)",
                            },
                        ),
                    },
                    {
                        text: "CodeShare",
                        link: "/modpack/kubejs/1.20.1/CodeShare/",
                        desc: "可直接改造的社区脚本片段集合。",
                        preview: kubePreview(
                            "版本 1.20.1 CodeShare",
                            "来自真实整合包的可复用脚本模式。",
                            `可用于快速启动实现：

- 常见机制的紧凑示例
- 可直接重构到当前整合包的实践片段
- 可作为风格与结构规范参考`,
                            {
                                src: "/imgs/screenshots/nav/zh-kubejs-1201-intro.png",
                                background:
                                    "linear-gradient(135deg, #3a0ca3 0%, #7209b7 100%)",
                            },
                        ),
                    },
                    {
                        text: "KubeJS 课程文档",
                        link: kubejsCourseUrl,
                        desc: "第三方维护的 GitBook 课程体系，覆盖基础到项目实践。",
                        badge: {
                            text: "第三方",
                            type: "info",
                        },
                        preview: kubePreview(
                            "KubeJS 课程文档",
                            "由第三方作者团队维护的外部 GitBook 课程。",
                            `课程内容包括：

- KubeJS 基础与进阶模块
- Addon 生态联动路径
- 项目化案例与资源管线`,
                            {
                                src: "/imgs/screenshots/nav/zh-kubejs-course.png",
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
                label: "版本中心",
                items: [
                    {
                        text: "KubeJS 总入口",
                        link: "/modpack/kubejs/",
                        desc: "面向所有版本分支的顶层入口。",
                        preview: kubePreview(
                            "KubeJS 版本总入口",
                            "用于选择正确版本路径的统一入口。",
                            `在组织协作时建议先通过版本中心分流：

- 按 Minecraft 目标版本选择文档分支
- 保证 onboarding 与真实运行环境一致
- 避免跨版本 API 误用`,
                            "linear-gradient(135deg, #283618 0%, #606c38 100%)",
                        ),
                    },
                    {
                        text: "第三方文档矩阵",
                        desc: "所有外部平台直达链接汇总。",
                        preview: kubePreview(
                            "第三方文档矩阵",
                            "KubeJS 与课程相关外部平台直达入口。",
                            `| 平台 | 直达链接 |
| --- | --- |
| KubeJS 官方源码仓库 | [GitHub](https://github.com/KubeJS-Mods/KubeJS/tree/2001) |
| KubeJS 官方 Wiki | [Wiki](https://kubejs.com/wiki) |
| KubeJS 社区 Discord | [Discord](https://discord.gg/lat) |
| KubeJS 课程 GitBook | [GitBook](https://gumeng.gitbook.io/kubejs-jiao-cheng-1.20.1) |
| KubeJS 课程 Gitee 仓库 | [Gitee](https://gitee.com/gumengmengs/kubejs-course) |
| KubeJS 课程 GitHub 仓库 | [GitHub](https://github.com/Gu-meng/kubejs-course) |
| KubeJS 课程问题反馈入口 | [Issue Tracker](https://gitee.com/gumengmengs/kubejs-course/issues/new/choose) |`,
                            "linear-gradient(135deg, #425466 0%, #5f7d95 100%)",
                        ),
                    },
                ],
            },
            {
                label: "1.19.2",
                items: [
                    {
                        text: "总览",
                        link: "/modpack/kubejs/1.19.2/",
                        desc: "用于现有 1.19.2 项目的遗留维护分支。",
                        badge: {
                            text: "遗留",
                            type: "info",
                        },
                        preview: kubePreview(
                            "KubeJS 1.19.2",
                            "面向旧版 1.19.2 部署的维护路径。",
                            `本分支适用于兼容性维护工作：

- 保持历史脚本包可维护性
- 在迁移前核验旧 API 假设
- 配合 XPlus 教程进行场景化参考`,
                            "linear-gradient(135deg, #495057 0%, #6c757d 100%)",
                        ),
                    },
                    {
                        text: "XPlus 教程",
                        link: "/modpack/kubejs/1.19.2/XPlusKubeJSTutorial/README",
                        desc: "面向遗留场景的实践示例与社区经验。",
                        preview: kubePreview(
                            "1.19.2 XPlus 教程",
                            "用于旧版整合包运维的实战脚本参考。",
                            `可用于快速实施与排障：

- 场景驱动的可读示例
- 面向实践的问题处理提示
- 旧项目栈的更快 onboarding`,
                            "linear-gradient(135deg, #33415c 0%, #5c677d 100%)",
                        ),
                    },
                ],
            },
            {
                label: "1.18.2",
                items: [
                    {
                        text: "总览",
                        link: "/modpack/kubejs/1.18.2/",
                        desc: "仓库中最早仍维护的遗留分支。",
                        badge: {
                            text: "遗留",
                            type: "warning",
                        },
                        preview: kubePreview(
                            "KubeJS 1.18.2",
                            "面向长周期服务器的归档兼容路径。",
                            `请仅用于 1.18.2 存量维护：

- 保持旧服稳定运行
- 记录已知版本约束
- 作为升级迁移前的基线参考`,
                            "linear-gradient(135deg, #5f0f40 0%, #9a031e 100%)",
                        ),
                    },
                    {
                        text: "XPlus 教程",
                        link: "/modpack/kubejs/1.18.2/XPlusKubeJSTutorial/README",
                        desc: "适配 1.18.2 的遗留教程示例。",
                        preview: kubePreview(
                            "1.18.2 XPlus 教程",
                            "面向历史脚本结构的场景化片段。",
                            `在维护极老版本整合包时可用于：

- 复现遗留脚本风格
- 快速可测试的参考片段
- 分阶段迁移准备`,
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

const docsPreview = createNavPreviewPanel(
    "文档中心",
    "工程规范、写作工具与扩展手册的统一入口。",
    `| 轨道 | 可解决的问题 |
| --- | --- |
| 框架 | 结构边界、扩展归属、Hero 规则 |
| 写作 | 侧边栏、片段、内容工作流 |
| 插件 | Markdown 容器与组件式增强 |

> 在修改主题、运行时或共享结构前，请先以文档中心为准。`,
    previewMedia(
        "文档中心",
        "/imgs/screenshots/nav/zh-doc-home.png",
        "linear-gradient(140deg, #162034 0%, #243b63 100%)",
    ),
    {
        link: "/doc/",
    },
);

const docsPanels: NavPanel[] = [
    {
        groups: [
            {
                label: "文档中心",
                items: [
                    {
                        text: "文档主页",
                        link: "/doc/",
                        desc: "文档目录与入口",
                        preview: kubePreview(
                            "文档主页",
                            "站点文档系统的总入口。",
                            `可从这里快速按意图分流：

- 框架规则与扩展契约
- 插件与组件写作指南
- 工作流与侧边栏维护说明`,
                            {
                                src: "/imgs/screenshots/nav/zh-doc-home.png",
                                background:
                                    "linear-gradient(140deg, #162034 0%, #243b63 100%)",
                                aspect: "16 / 10",
                            },
                        ),
                    },
                    {
                        text: "合作须知",
                        link: "/doc/rules",
                        desc: "贡献协作规范与流程",
                        preview: kubePreview(
                            "合作须知",
                            "面向文档与工程协作的统一规范。",
                            `在做共享结构改动前，请先核对这份规则。

| 关注点 | 作用 |
| --- | --- |
| 命名 | 保持可搜索性 |
| 评审流 | 避免跨仓漂移 |
| 贡献方式 | 统一输出风格 |`,
                            {
                                src: "/imgs/screenshots/rulesDark.png",
                                background:
                                    "linear-gradient(135deg, #2a2238 0%, #3f2f56 100%)",
                                aspect: "16 / 10",
                            },
                        ),
                    },
                    {
                        text: "插件指南",
                        link: "/doc/pluginsGuide",
                        desc: "Markdown 插件与组件能力",
                        preview: kubePreview(
                            "插件指南",
                            "用于富文本容器和组件式渲染的参考页。",
                            `这里聚焦站点的富格式能力：

- markdown-it 容器
- 组件驱动的内容块
- 可安全扩展的插件使用方式`,
                            {
                                src: "/imgs/screenshots/nav/zh-doc-plugins.png",
                                background:
                                    "linear-gradient(135deg, #1c2438 0%, #33507b 100%)",
                                aspect: "16 / 10",
                            },
                        ),
                    },
                    {
                        text: "导航指南",
                        link: "/doc/guide/",
                        desc: "主题导航与使用说明",
                        preview: kubePreview(
                            "导航指南",
                            "用于快速理解站点结构和入口关系的说明页。",
                            `当你需要的是“怎么找到内容”，而不是“怎么扩展系统”时，应先看这里。

> 它适合在正式改动前快速定位主题路径。`,
                            {
                                src: "/imgs/screenshots/nav/zh-doc-home.png",
                                background:
                                    "linear-gradient(135deg, #1a2430 0%, #30475a 100%)",
                                aspect: "16 / 10",
                            },
                        ),
                    },
                ],
            },
            {
                label: "框架开发",
                items: [
                    {
                        text: "框架可维护性",
                        link: "/doc/frameworkMaintainability",
                        desc: "高层工程规范与扩展标准",
                        preview: kubePreview(
                            "框架可维护性",
                            "用于保持站点结构稳定的工程规则总览。",
                            `修改运行时、主题或共享契约前，应先阅读本页。

- 职责边界
- 同步要求
- 安全扩展规则`,
                            {
                                src: "/imgs/screenshots/nav/zh-doc-framework.png",
                                background:
                                    "linear-gradient(135deg, #1e2233 0%, #314a77 100%)",
                                aspect: "16 / 10",
                            },
                        ),
                    },
                    {
                        text: "开发工作流",
                        link: "/doc/developmentWorkflow",
                        desc: "改动顺序、校验与同步规则",
                        preview: kubePreview(
                            "开发工作流",
                            "面向项目族改动的标准顺序与验证方法。",
                            `建议按以下顺序执行结构性改动：

1. 先改共享契约
2. 再改组件与运行时
3. 校验构建与 locale 输出
4. 最后同步模板系仓库`,
                            {
                                src: "/imgs/screenshots/nav/zh-doc-framework.png",
                                background:
                                    "linear-gradient(135deg, #16253b 0%, #234d68 100%)",
                                aspect: "16 / 10",
                            },
                        ),
                    },
                    {
                        text: "扩展架构说明",
                        link: "/doc/extensionArchitecture",
                        desc: "配置、运行时、组件与样式的职责边界",
                        preview: kubePreview(
                            "扩展架构说明",
                            "用于判断配置、运行时、组件与样式归属的责任图。",
                            `当你不确定“这段逻辑该放哪”时，就应该看这页。

| 内容 | 归属 |
| --- | --- |
| locale / 配置 | config |
| 运行时状态 | runtime |
| UI 渲染 | theme components |`,
                            {
                                src: "/imgs/screenshots/nav/zh-doc-framework.png",
                                background:
                                    "linear-gradient(135deg, #192035 0%, #234860 100%)",
                                aspect: "16 / 10",
                            },
                        ),
                    },
                    {
                        text: "Hero 扩展手册",
                        link: "/doc/heroExtension",
                        desc: "排版、浮动元素、Shader 与背景扩展方案",
                        preview: kubePreview(
                            "Hero 扩展手册",
                            "Hero 页面在排版、浮动元素、Shader 与背景层上的扩展说明。",
                            `这是 Hero 领域的专用扩展手册：

- TypographyStyle 注册
- Floating 元素扩展策略
- Shader / Background 职责边界`,
                            {
                                src: "/imgs/screenshots/nav/zh-doc-framework.png",
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
                label: "写作工具",
                items: [
                    {
                        text: "侧边栏指南",
                        link: "/doc/sidebarGuide",
                        desc: "侧边栏配置与结构管理",
                        preview: kubePreview(
                            "侧边栏指南",
                            "用于目录结构、落地页与自动生成侧边栏行为的参考。",
                            `当你要改目录入口、索引页或 landing route 时，请优先看这里。

- 侧边栏源配置
- 自动 landing 规则
- 缓存与重建预期`,
                            {
                                src: "/imgs/screenshots/nav/zh-doc-sidebar.png",
                                background:
                                    "linear-gradient(135deg, #1a2337 0%, #3a547c 100%)",
                                aspect: "16 / 10",
                            },
                        ),
                    },
                    {
                        text: "VSCode 片段指南",
                        link: "/doc/vscodeSnippetsGuide",
                        desc: "基于代码片段的写作工作流",
                        preview: kubePreview(
                            "VSCode 片段指南",
                            "用于 frontmatter 与内容模板快速写作的说明页。",
                            `这个页面更关注提效：

- frontmatter 补全
- 常用文档片段骨架
- 大型文档集的安全写作`,
                            {
                                src: "/imgs/VSCode/1.png",
                                background:
                                    "linear-gradient(135deg, #111827 0%, #1d4ed8 100%)",
                                aspect: "16 / 10",
                            },
                        ),
                    },
                    {
                        text: "LiteTree 指南",
                        link: "/doc/litetreeGuide",
                        desc: "树形展示组件使用方式",
                        preview: kubePreview(
                            "LiteTree 指南",
                            "用于树形结构展示组件的说明和使用约束。",
                            `当内容适合层级展开而不是长段落时，LiteTree 会更合适。

> 它特别适合 API、流程和依赖关系说明。`,
                            {
                                src: "/imgs/screenshots/nav/zh-doc-sidebar.png",
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

const docsNav = createDropdownNavItem(
    "文档",
    createNavDropdown(docsPanels, {
        layout: "columns",
        preview: docsPreview,
    }),
);

const contentPreview = createNavPreviewPanel(
    "内容地图",
    "按内容类型而不是版本路径浏览站点。",
    `| 分区 | 适合查什么 |
| --- | --- |
| 模组 | 模组资料与参考页 |
| 整合包 | 面向包或版本分支的知识 |
| 开发 | 作者工作流与实现指南 |
| 信息 | Q&A、建议与支持入口 |`,
    previewMedia(
        "内容地图",
        "/imgs/screenshots/nav/zh-modpack-home.png",
        "linear-gradient(135deg, #18253d 0%, #324e7c 100%)",
    ),
    {
        link: "/modpack/",
    },
);

const contentPanels: NavPanel[] = [
    {
        groups: [
            {
                label: "核心模块",
                items: [
                    {
                        text: "模组",
                        link: "/mods/",
                        desc: "按分类整理的模组资料",
                        preview: kubePreview(
                            "模组",
                            "按主题归类的模组资料与参考入口。",
                            `当你需要的是专题查找，而不是整合包分支路径时，应先看模组区。`,
                            {
                                src: "/imgs/screenshots/nav/zh-mods-home.png",
                                background:
                                    "linear-gradient(135deg, #18223b 0%, #334e68 100%)",
                                aspect: "16 / 10",
                            },
                        ),
                    },
                    {
                        text: "整合包",
                        link: "/modpack/",
                        desc: "整合包文档与推荐",
                        preview: kubePreview(
                            "整合包",
                            "面向包、版本分支与 KubeJS 路线的内容入口。",
                            `当文档与运行目标或包版本强相关时，应从这里进入。`,
                            {
                                src: "/imgs/screenshots/nav/zh-modpack-home.png",
                                background:
                                    "linear-gradient(135deg, #1a2a45 0%, #31517d 100%)",
                                aspect: "16 / 10",
                            },
                        ),
                    },
                    {
                        text: "开发",
                        link: "/develop/",
                        desc: "开发与模组制作指南",
                        preview: kubePreview(
                            "开发",
                            "面向工具链、数据包与作者工作流的实现指南。",
                            `当你更关心“怎么做”，而不是“内容分区在哪”时，优先走开发区。`,
                            {
                                src: "/imgs/screenshots/nav/zh-develop-home.png",
                                background:
                                    "linear-gradient(135deg, #1a2238 0%, #35506f 100%)",
                                aspect: "16 / 10",
                            },
                        ),
                    },
                    {
                        text: "信息",
                        link: "/info/",
                        desc: "Q&A、建议与讨论入口",
                        preview: kubePreview(
                            "信息",
                            "用于问题、建议与社区式说明的内容入口。",
                            `当你在找的是补充说明、请求背景或问答型内容时，请从这里进入。`,
                            {
                                src: "/imgs/screenshots/nav/zh-info-home.png",
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
                label: "社区",
                items: [
                    {
                        text: "标签",
                        link: "/tags",
                        desc: "按主题聚合页面内容",
                        preview: kubePreview(
                            "标签",
                            "用于跨分区查找相关内容的主题索引。",
                            `当你只记得主题，不记得路径时，标签页会比直接翻目录更高效。`,
                            {
                                src: "/imgs/screenshots/nav/zh-tags-home.png",
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

const contentNav = createDropdownNavItem(
    "内容",
    createNavDropdown(contentPanels, {
        layout: "columns",
        preview: contentPreview,
    }),
);

const zhNav: NavItem[] = createNavItems(homeNav, kubeNav, docsNav, contentNav);

export default zhNav;
