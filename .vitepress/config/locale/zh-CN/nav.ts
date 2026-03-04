import type { NavItem } from "../../../utils/config/nav-types";

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

const zhNav: NavItem[] = [
    {
        text: "首页",
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
                                        "linear-gradient(135deg, #0d3b66 0%, #2a9d8f 100%)",
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
                                        "linear-gradient(140deg, #264653 0%, #3a86ff 100%)",
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
                                        "linear-gradient(135deg, #3d405b 0%, #81b29a 100%)",
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
                                        "linear-gradient(135deg, #1d3557 0%, #457b9d 100%)",
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
                                        "linear-gradient(140deg, #003049 0%, #2a9d8f 100%)",
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
                                        "linear-gradient(125deg, #1b4332 0%, #2d6a4f 100%)",
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
                                        "linear-gradient(135deg, #6a040f 0%, #f48c06 100%)",
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
                                        "linear-gradient(135deg, #6c584c 0%, #dda15e 100%)",
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
                                        "linear-gradient(135deg, #3a0ca3 0%, #7209b7 100%)",
                                    ),
                                },
                                {
                                    text: "KubeJS 课程文档",
                                    link: "/modpack/kubejs/1.20.1/KubeJSCourse/README",
                                    desc: "从基础到项目实践的结构化课程体系。",
                                    preview: kubePreview(
                                        "KubeJS 课程文档",
                                        "从基础到进阶的系统化训练内容。",
                                        `课程内容包括：

- KubeJS 基础与进阶模块
- Addon 生态联动路径
- 项目化案例与资源管线`,
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
            ],
        },
    },
    {
        text: "文档",
        dropdown: {
            layout: "columns",
            panels: [
                {
                    groups: [
                        {
                            label: "文档中心",
                            items: [
                                {
                                    text: "文档主页",
                                    link: "/doc/",
                                    desc: "文档目录与入口",
                                },
                                {
                                    text: "合作须知",
                                    link: "/doc/rules",
                                    desc: "贡献协作规范与流程",
                                },
                                {
                                    text: "插件指南",
                                    link: "/doc/pluginsGuide",
                                    desc: "Markdown 插件与组件能力",
                                },
                                {
                                    text: "导航指南",
                                    link: "/doc/guide/",
                                    desc: "主题导航与使用说明",
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
                                },
                                {
                                    text: "VSCode 片段指南",
                                    link: "/doc/vscodeSnippetsGuide",
                                    desc: "基于代码片段的写作工作流",
                                },
                                {
                                    text: "LiteTree 指南",
                                    link: "/doc/litetreeGuide",
                                    desc: "树形展示组件使用方式",
                                },
                            ],
                        },
                    ],
                },
            ],
        },
    },
    {
        text: "内容",
        dropdown: {
            layout: "columns",
            panels: [
                {
                    groups: [
                        {
                            label: "核心模块",
                            items: [
                                {
                                    text: "模组",
                                    link: "/mods/",
                                    desc: "按分类整理的模组资料",
                                },
                                {
                                    text: "整合包",
                                    link: "/modpack/",
                                    desc: "整合包文档与推荐",
                                },
                                {
                                    text: "开发",
                                    link: "/develop/",
                                    desc: "开发与模组制作指南",
                                },
                                {
                                    text: "信息",
                                    link: "/info/",
                                    desc: "Q&A、建议与讨论入口",
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
                                    text: "开发者",
                                    link: "/developers/",
                                    desc: "团队与贡献者空间",
                                },
                                {
                                    text: "标签",
                                    link: "/tags",
                                    desc: "按主题聚合页面内容",
                                },
                            ],
                        },
                    ],
                },
            ],
        },
    },
];

export default zhNav;
