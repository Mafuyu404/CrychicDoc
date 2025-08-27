---
title: 文档编写规范
description: 参与 CrychicDoc 文档编写所需遵循的官方规范、工作流与样式指南。
progress: 100
state: preliminary
priority: -10
hidden: false
---

# 文档编写规范 {#main}

::: alert {"type": "success", "title": "🎉 欢迎贡献者！", "border": "start"}
本文档是您参与 CrychicDoc 项目所需了解的**唯一**规范。它详细说明了协作流程、内容编写标准、侧边栏配置方法以及所有可用的样式与组件。
:::

## 合作 {#contribution}

### 具体步骤 {#workflow-steps}

<LiteTree>
#workflow=color:white;background:#1976d2;padding:2px 6px;border-radius:3px;font-size:12px;
---
{#workflow}1. Fork & Clone
    将主仓库 Fork 到您的账户，然后 Clone 到本地。
{#workflow}2. 同步与创建分支
    在开始修改前，与主仓库同步，然后为您的修改创建一个新分支。
{#workflow}3. 修改与提交
    在您的新分支上进行修改，并使用清晰的提交信息进行 Commit。
{#workflow}4. 发起 Pull Request
    将您的分支推送到您 Fork 的仓库，并创建一个 Pull Request 到主仓库。
</LiteTree>

::: alert {"type": "warning", "title": "⚡ 重要提醒"}
请务必遵循 **Conventional Commits** 规范来编写提交信息，这有助于自动生成更新日志和版本管理。
:::

:::: stepper
@tab 🔧 初始配置
```bash
# Clone 您的 Fork
git clone https://github.com/-%YourName/CrychicDoc.git
cd CrychicDoc

# 添加上游（主仓库）
git remote add upstream https://github.com/PickAID/CrychicDoc.git
```

::: v-info
第一次参与项目时的必要配置步骤。
:::

@tab 🚀 开始新的贡献
```bash
# 从主仓库同步最新更改
git fetch upstream
git checkout main
git merge upstream/main

# 为您的新功能或修复创建一个分支
git checkout -b -%branch
```

@tab 📝 提交您的修改
```bash
# 添加您的修改
git add .

# 提交更改（遵循 Conventional Commits 规范）
git commit -m "feat: 添加 KubeJS 事件处理文档"

# 推送到您 Fork 的仓库
git push origin -%branch
```

::: v-success
提交信息格式：`type: 简短描述`
:::
::::

## 项目结构 {#structure}

::: alert {"type": "info", "title": "项目结构概览"}
下方是 CrychicDoc 的完整项目结构，包含了关键文件和目录的用途说明。理解项目结构有助于您快速定位文件和理解代码组织方式。
:::

<LiteTree>
// 定义状态和类型样式
#config=color:white;background:#1976d2;padding:2px 6px;border-radius:3px;font-size:12px;
#content=color:white;background:#4caf50;padding:2px 6px;border-radius:3px;font-size:12px;
#script=color:white;background:#ff9800;padding:2px 6px;border-radius:3px;font-size:12px;
#ignore=color:#666;background:#f5f5f5;padding:2px 6px;border-radius:3px;font-size:12px;
.important=font-weight:bold;color:#d32f2f;
.folder=color:#1976d2;font-weight:500;
// 定义图标
folder=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMjQgMjQiPjxwYXRoIGZpbGw9ImN1cnJlbnRDb2xvciIgZD0iTTEwIDRIOGEyIDIgMCAwIDAtMiAydjEyYTIgMiAwIDAgMCAyIDJoOGEyIDIgMCAwIDAgMi0yVjhhMiAyIDAgMCAwLTItMmgtM2wtMi0yWiIvPjwvc3ZnPg==
ts=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMTUgMTUiPjxwYXRoIGZpbGw9Im5vbmUiIHN0cm9rZT0iIzMxNzhDNiIgZD0iTTEyLjUgOHYtLjE2N2MwLS43MzYtLjU5Ny0xLjMzMy0xLjMzMy0xLjMzM0gxMGExLjUgMS41IDAgMSAwIDAgM2gxYTEuNSAxLjUgMCAwIDEgMCAzaC0xQTEuNSAxLjUgMCAwIDEgOC41IDExTTggNi41SDNtMi41IDBWMTNNMS41LjVoMTN2MTRIOS41eiIvPjwvc3ZnPg==
js=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMjQgMjQiPjxwYXRoIGZpbGw9IiNmN2RmMWUiIGQ9Ik0zIDNoMTh2MThIM1ptMTYuNTI1IDE0LjVjLS4zLS4zNTQtLjc5NS0uNjI5LTEuNzE3LS42MjljLS44ODEgMC0xLjQzOS4zMTgtMS40MzkuNzE4YzAgLjM5Ni4zNzMuNjM3IDEuMTU2Ljk2N2MxLjMzMi41ODYgMi4yODEgMS4wOTMgMi4yODEgMi4zOGMwIDEuMzItMS4yMDMgMi4xNDMtMi45NzQgMi4xNDNjLTEuMjEzIDAtMi4yNzEtLjQ2Mi0yLjk1LTEuMDc0bC44NzUtMS4yNzNjLjQzMy4zODkgMS4wNjQuNzI0IDEuNjY0LjcyNGMuNzA2IDAgMS4wNjQtLjMzMSAxLjA2NC0uNzMzYzAtLjQ0OS0uMzc2LS43MjQtMS4yNDUtMS4wMzNjLTEuMzI1LS40ODgtMi4xMzItMS4yNS0yLjEzMi0yLjM2M2MwLTEuMzk0IDEuMDI5LTIuMTQzIDIuODU2LTIuMTQzYzEuMDY0IDAgMS43NDUuMzI4IDIuMzc3Ljg1OWwtLjgzIDEuMjQxWm0tNS44NDUtLjMzNWMuMzY2LjgxNS4zNjYgMS41NzcuMzY2IDIuNDd2My45MDZoLTEuODc2VjE5LjZjMC0xLjUyNy0uMDYtMi4xOC0uNTUtMi40OGMtLjQxLS4yODgtMS4wNzYtLjI3NC0xLjYxOC0uMTA3Yy0uMzc4LjExNy0uNzEzLjMzNS0uNzEzIDEuMDc0djUuMDU2SDYuNDI3VjEyLjgyaDEuODc2djIuMTEzYy43NDctLjM5OSAxLjU3Ny0uNzM4IDIuNjQ1LS43MzhjLjc2NCAwIDEuNTc3LjI1MyAyLjA2OS43ODdjLjQ5OC41NTIuNjI2IDEuMTU3LjcyMyAxLjk5MVoiLz48L3N2Zz4=
md=data:image/svg+xml;charset=utf-8;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIzMiIgaGVpZ2h0PSIzMiIgdmlld0JveD0iMCAwIDI0IDI0Ij48IS0tIEljb24gZnJvbSBNYXRlcmlhbCBTeW1ib2xzIGJ5IEdvb2dsZSAtIGh0dHBzOi8vZ2l0aHViLmNvbS9nb29nbGUvbWF0ZXJpYWwtZGVzaWduLWljb25zL2Jsb2IvbWFzdGVyL0xJQ0VOU0UgLS0+PHBhdGggZmlsbD0iIzg4ODg4OCIgZD0iTTkgMThxLS44MjUgMC0xLjQxMi0uNTg3VDcgMTZWNHEwLS44MjUuNTg4LTEuNDEyVDkgMmg5cS44MjUgMCAxLjQxMy41ODhUMjAgNHYxMnEwIC44MjUtLjU4NyAxLjQxM1QxOCAxOHptLTQgNHEtLjgyNSAwLTEuNDEyLS41ODdUMyAyMFY2aDJ2MTRoMTF2MnptNS4yNS05aDEuNVY4LjVoMXYzaDEuNXYtM2gxVjEzaDEuNVY4cTAtLjQyNS0uMjg4LS43MTJUMTUuNzUgN2gtNC41cS0uNDI1IDAtLjcxMi4yODhUMTAuMjUgOHoiLz48L3N2Zz4=
json=data:image/svg+xml;charset=utf-8;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIzMiIgaGVpZ2h0PSIzMiIgdmlld0JveD0iMCAwIDI0IDI0Ij48IS0tIEljb24gZnJvbSBNYXRlcmlhbCBTeW1ib2xzIGJ5IEdvb2dsZSAtIGh0dHBzOi8vZ2l0aHViLmNvbS9nb29nbGUvbWF0ZXJpYWwtZGVzaWduLWljb25zL2Jsb2IvbWFzdGVyL0xJQ0VOU0UgLS0+PHBhdGggZmlsbD0iIzg4ODg4OCIgZD0iTTQuNzUgMTVINi41cS40MjUgMCAuNzEzLS4yODhUNy41IDE0VjlINnY0Ljc1SDVWMTIuNUgzLjc1VjE0cTAgLjQyNS4yODguNzEzVDQuNzUgMTVtNC40MjUgMGgxLjVxLjQyNSAwIC43MTMtLjI4OHQuMjg3LS43MTJ2LTEuNXEwLS40MjUtLjI4OC0uNzEydC0uNzEyLS4yODhoLTEuMjV2LTEuMjVoMXYuNWgxLjI1VjEwcTAtLjQyNS0uMjg4LS43MTJUMTAuNjc2IDloLTEuNXEtLjQyNSAwLS43MTIuMjg4VDguMTc1IDEwdjEuNXEwIC40MjUuMjg4LjcxM3QuNzEyLjI4N2gxLjI1djEuMjVoLTF2LS41aC0xLjI1VjE0cTAgLjQyNS4yODguNzEzdC43MTIuMjg3bTQuNC0xLjV2LTNoMXYzem0tLjI1IDEuNWgxLjVxLjQyNSAwIC43MTMtLjI4OHQuMjg3LS43MTJ2LTRxMC0uNDI1LS4yODctLjcxMlQxNC44MjUgOWgtMS41cS0uNDI1IDAtLjcxMi4yODh0LS4yODguNzEydjRxMCAuNDI1LjI4OC43MTN0LjcxMi4yODdtMy4xNzUgMGgxLjI1di0yLjYyNWwxIDIuNjI1SDIwVjloLTEuMjV2Mi42MjVMMTcuNzUgOUgxNi41ek0zIDIwcS0uODI1IDAtMS40MTItLjU4N1QxIDE4VjZxMC0uODI1LjU4OC0xLjQxMlQzIDRoMThxLjgyNSAwIDEuNDEzLjU4OFQyMyA2djEycTAgLjgyNS0uNTg3IDEuNDEzVDIxIDIweiIvPjwvc3ZnPg==
---
{.important}CrychicDoc                         // {.important}主项目
    [folder] .github                            // {#script}CI/CD脚本
        workflows                               // 自动构建脚本
    [folder] .vitepress                         // {#config}VitePress配置
        [folder] config                         // {.important}项目所有配置
            [folder] lang                         // {.important}多语言配置
            [folder] locale                         // {.important}本地化配置
            [folder] sidebar                         // {.important}侧边栏配置
            [ts] common-config.ts                      // VitePress配置
            [json] contributors.json                      // 贡献者配置
            [ts] markdown-plugins.ts                      // md插件配置
            [ts] project-config.ts                       // 项目主配置
        [folder] plugins                        // {.important}自定义插件
        [folder] theme                          // {.important}自定义主题
            [folder] components                 // Vue组件
            [folder] styles                     // CSS样式
        [ts] config.mts                         // {.important}VitePress配置
        [ts] index.ts                           // {.important}侧边栏配置
    [folder] .vscode                            // {#config}VS Code设置
        [md] snippets                           // Markdown代码片段
    [folder] docs                               // {#content}内容目录
        [folder] public                         // 静态资源
        [folder] zh                             // {#content}中文内容
            [md] 各种文件                        // 文档文件
        [folder] en                             // {#content}英文内容
            [md] 各种文件                        // 文档文件
    [md] README.md                              // {.important}项目说明
    LICENSE                                     // {#config}CC BY-SA 4.0
    .gitignore                                  // {#config}Git忽略规则
</LiteTree>

## 编写规范 {#content}

**核心指南文档：**
- **[样式与插件指南](./pluginsGuide.md)** - Markdown扩展与自定义组件。
- **[侧边栏配置实用指南](./sidebarGuide.md)** - 配置和管理侧边栏。

**辅助工具指南：**
- [LiteTree 组件使用指南](./litetreeGuide.md) - 创建优雅的树形结构
- [VSCode 代码片段使用指南](./vscodeSnippetsGuide.md) - 提高文档编写效率。


### Frontmatter配置 {#frontmatter}

每个 Markdown 文件都应包含一个 `frontmatter` 块，用于配置页面的元数据：

:::: chart-grid {"columns": 2, "gap": "24px"}

::: v-info 必需字段
- **`title`** (`string`) - 页面标题，显示在侧边栏
- **`priority`** (`number`) - 侧边栏排序，数字越小越靠前
:::

::: v-success 可选字段
- **`description`** (`string`) - 页面描述，用于 SEO
- **`authors`** (`string[]`) - 页面作者列表
- **`progress`** (`number`) - 文档完成进度 (0-100)
- **`state`** (`string`) - 文档状态
- **`hidden`** (`boolean`) - 隐藏页面
:::

::::

::: alert {"type": "info", "title": "Frontmatter 示例"}
```yaml
---
title: KubeJS 事件系统
description: 深入了解 KubeJS 的事件处理机制
priority: 10
authors: ["张三", "李四"]
progress: 85
state: preliminary
---
```
:::

### 📝 标题与锚点 {#headings-anchors}

::: stepper
@tab 📑 标题层级
- 每个文档**必须**有且只有一个 `H1` 级别的标题 (`#`)
- 标题层级应逐级递增，不能跳级
- 建议最多使用到 `H4` 级别

@tab 🔗 锚点设置
为了生成清晰的 URL，请为所有标题添加自定义锚点：
```markdown
### 这是一个标题 {#a-clear-anchor}
```

@tab ✅ 最佳实践
- 锚点使用英文和连字符
- 保持锚点简洁明了
- 避免使用特殊字符
:::

::::: chart-grid {"columns": 3, "gap": "20px"}

::: v-warning 尊重原创
请勿在未与原作者沟通的情况下，擅自大规模修改或删除他人的创作。
:::

::: v-info 积极沟通
如果您有任何疑问或建议，请通过 GitHub Issues 或社区进行沟通。
:::

::: v-success 贡献者署名
第三方文档作者需至少提交一次内容修改，以便系统正确识别 GitHub 账户。
:::

:::::