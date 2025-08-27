---
title: LiteTree组件
description: 在 VitePress 中使用 LiteTree 创建优雅、信息丰富的树形结构的完整指南。
layout: doc
priority: 40
hidden: false
---

# LiteTree 组件使用指南 {#main}

::: v-info
[LiteTree](https://zhangfisher.github.io/lite-tree/) 是一个为 VitePress 设计的轻量级树形结构组件。它使用一种类似 YAML 的、基于缩进的语法，使其在 Markdown 文档中书写和维护变得异常简单和直观。
:::

## 核心特性 {#core-features}

<LiteTree>
#feature=color:white;background:#1976d2;padding:2px 6px;border-radius:3px;font-size:12px;
---
{#feature}轻量级
    无任何第三方依赖，体积小巧。
{#feature}Markdown 友好
    使用基于缩进的 `lite` 格式，完美融入 Markdown。
{#feature}高度可定制
    支持自定义节点样式、标签、注释和图标。
{#feature}强大的变量系统
    支持定义和复用样式及图标。
{#feature}内置标记
    提供一组预设的标记符，用于表示不同的状态。
</LiteTree>

## 使用自定义图标 {#using-custom-icons}

`LiteTree` 的一大特色是支持自定义图标，但这需要将 SVG 图像转换为 Base64 编码的 Data URI。

::::: stepper
@tab 步骤一：获取 SVG 图标
首先，你需要一个 SVG 图像。你可以使用像 [Figma](https://www.figma.com/)、[Iconify](https://iconify.design/) 或 [Material Design Icons](https://materialdesignicons.com/) 这样的工具来获取或创建 SVG 图标。

::: v-success 提示
确保你的 SVG 代码是简洁和优化过的，以减小文件大小。
:::
@tab 步骤二：转换为 Base64 格式
有许多免费的在线工具可以将你的 SVG 代码转换为 Base64。我们推荐使用 [SVG to Base64 Converter](https://uutool.cn/svg-base64/)，因为它非常简单直接。

1.  **粘贴 SVG 代码**: 将你的完整 SVG 代码（包括 `<svg>` 标签）粘贴到网站的输入框中。
2.  **复制 Base64 URI**: 复制生成的 `data:image/svg+xml;base64,...` 格式的完整 URI。
@tab 步骤三：在 LiteTree 中应用
将复制的 URI 用作图标变量的值：
```markdown
<LiteTree>
// 定义你的自定义图标
myIcon=data:image/svg+xml;base64,...
---
[myIcon] 这是一个带有自定义图标的节点
</LiteTree>
```
:::::

## 基础语法 {#basic-syntax}

### 创建简单树形结构 {#basic-tree}

通过缩进（推荐使用 4 个空格）来表示层级关系。

::: demo 基础树形结构
<LiteTree>
公司架构
    行政中心
        总裁办
        人力资源部
    市场中心
        市场部
        销售部
</LiteTree>
:::

### 添加状态标记 {#tree-with-markers}

使用行尾注释 `//` 加上特定的符号，可以为节点添加状态标记。

::: demo 标准标记符
<LiteTree>
项目状态
    已完成任务      //v    成功标记
    新增功能        //+    添加标记
    废弃代码        //-    删除标记
    发现错误        //x    错误标记
    修改文件        //*    修改标记
    重要项目        //!    重要标记
</LiteTree>
:::

### 添加标签 {#tree-with-tags}

在节点标题后紧随 `(tag,tag,tag)` 用来表示节点的标签，支持多个标签。

::: alert {"type": "info", "title": "标签语法规则"}
- 多个标签之间使用 `,` 分隔
- 标签前可以指定 `{...CSS样式...}`，用来指定标签的嵌入样式
- 标签内容也可以包含 `[图标名]`
:::

::: demo 基础标签
<LiteTree>
技术栈
    前端开发
        Node.js项目(JavaScript,TypeScript,React)
        Vue.js项目(Vue,Vite,Pinia)
    后端开发
        API服务({color:white;background-color:#ff9e9e;border:1px solid red}Spring,{color:white;background-color:#9e9eff;border:1px solid blue}Express,{color:white;background-color:#bfffbf;border:1px solid green}FastAPI)
</LiteTree>
:::

::: demo 带图标的标签
<LiteTree>
// 定义图标
github=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMjQgMjQiPjxwYXRoIGZpbGw9ImN1cnJlbnRDb2xvciIgZD0iTTEyIDJBMTAgMTAgMCAwIDAgMiAxMmMwIDQuNDIgMi44NyA4LjE3IDYuODQgOS41Yy41LjA4LjY2LS4yMy42Ni0uNXYtMS42OWMtMi43Ny42LTMuMzYtMS4zNC0zLjM2LTEuMzRjLS40Ni0xLjE2LTEuMTEtMS40Ny0xLjExLTEuNDdjLS45MS0uNjIuMDctLjYxLjA3LS42MWMxIDAtMS41MyAxLjAzLTEuNTMgMS4wM2MuODcgMS41MiAyLjM0IDEuMDcgMi45MS44M2MuMDktLjY1LjM1LTEuMDkuNjMtMS4zNGMtMi4yMi0uMjUtNC41NS0xLjExLTQuNTUtNC45MmMwLTEuMTEuMzgtMiAxLjAzLTIuNzFjLS4xLS4yNS0uNDUtMS4yOS4xLTIuNjRjMCAwIC44NC0uMjcgMi43NSAxLjAyYy44MS0uMjMgMS42OC0uMzQgMi41NC0uMzVjLjg2LjAxIDEuNzMuMTIgMi41NC4zNWMxLjkxLTEuMjkgMi43NS0xLjAyIDIuNzUtMS4wMmMuNTUgMS4zNS4yIDIuMzkuMSAyLjY0Yy42NS43MSAxLjAzIDEuNiAxLjAzIDIuNzFjMCAzLjgyLTIuMzQgNC42Ni00LjU3IDQuOTFjLjM2LjMxLjY4LjkyLjY4IDEuODV2Mi43NGMwIC4yNy4xNi41OS42Ny41QzE5LjE0IDIwLjE2IDIyIDE2LjQyIDIyIDEyQTEwIDEwIDAgMCAwIDEyIDJaIi8+PC9zdmc+
gitee=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMjQgMjQiPjxwYXRoIGZpbGw9IiNkNzI4MjgiIGQ9Ik0xMiAyLjI1YzUuMzg0IDAgOS43NSA0LjM2NiA5Ljc1IDkuNzVzLTQuMzY2IDkuNzUtOS43NSA5Ljc1UzIuMjUgMTcuMzg0IDIuMjUgMTJTNi42MTYgMi4yNSAxMiAyLjI1WiIvPjxwYXRoIGZpbGw9IndoaXRlIiBkPSJNMTEuMzc4IDguNDc4SDcuNzIyYy0uMzMzIDAtLjYwMy4yNy0uNjAzLjYwM3YuOTE4YzAgLjMzMy4yNy42MDMuNjAzLjYwM2gzLjY1NnYuOTE4SDcuNzIyYy0uMzMzIDAtLjYwMy4yNy0uNjAzLjYwM3YuOTE4YzAgLjMzMy4yNy42MDMuNjAzLjYwM2gzLjY1NnYuOTE4SDcuNzIyYy0uMzMzIDAtLjYwMy4yNy0uNjAzLjYwM3YuOTE4YzAgLjMzMy4yNy42MDMuNjAzLjYwM2g4LjU1NmMuMzMzIDAgLjYwMy0uMjcuNjAzLS42MDNWOC40NzhjMC0uMzMzLS4yNy0uNjAzLS42MDMtLjYwM2gtNS41WiIvPjwvc3ZnPg==
---
代码仓库
    项目管理
        主仓库([github]GitHub,Gitee)
        镜像仓库([gitee]备份仓库)
</LiteTree>
:::

### 添加注释 {#tree-with-comments}

使用 `//` 后面内容代表节点的注释，显示在最右侧。

::: alert {"type": "warning", "title": "注释语法注意事项"}
- 注释内容默认是灰色显示
- 注释内容可以包含 `{...CSS样式...}`，用来指定注释的嵌入样式
- 注释内容也可以包含 `[图标名]` 和超链接
- **注意**：`//` 后必须有空白字符
- 在移动端（`@media screen and (max-width: 480px)`）时注释不显示
:::

::: demo 基础注释
<LiteTree>
项目文件
    配置文件
        package.json                // 项目依赖配置
        vite.config.js             // {color:white;background-color:#ff9e9e;border:1px solid red}构建配置文件
    源代码
        main.js                     // 应用入口文件
        App.vue                     // {color:blue;font-weight:bold}根组件
</LiteTree>
:::

::: demo 带图标和链接的注释
<LiteTree>
// 定义图标
star=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMjQgMjQiPjxwYXRoIGZpbGw9ImdvbGQiIGQ9Im0xMiAxNS40bC0zLjc2IDIuMjdhMS0xIDAgMCAxLTEuNTMtMS4xMUw3LjMgMTMuNGwtMy4yMy0uMjhhMS0xIDAgMCAxLS41Ni0xLjc1bDIuNjctMi4xMmwtLjY1LTMuMDlhMS0xIDAgMCAxIDEuNDgtMS4xM0wxMiA3LjRsNC4yOC0yLjQ3YTEgMSAwIDAgMSAxLjQ4IDEuMTNsLS42NSAzLjA5TDE5Ljc4IDExYTEgMSAwIDAgMS0uNTYgMS43NWwtMy4yMy4yOGMtLjUxIDIuMTYtLjYzIDMuNTUtMS43NiAyLjM3WiIvPjwvc3ZnPg==
yes=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMjQgMjQiPjxwYXRoIGZpbGw9ImdyZWVuIiBkPSJtMTAuNiAxNi4yIDcuODUtNy44NWMuMjMtLjIzLjIzLS42MSAwLS44NGwtLjg0LS44NGMtLjIzLS4yMy0uNjEtLjIzLS44NCAwTDEwLjE4IDE0LjJsLTMuNDktMy40OWMtLjIzLS4yMy0uNjEtLjIzLS44NCAwbC0uODQuODRjLS4yMy4yMy0uMjMuNjEgMCAuODRsNC43NiA0Ljc2Yy4yMy4yMy42MS4yMy44NCAwWiIvPjwvc3ZnPg==
---
开源项目
    文档站点
        VitePress                   // [star]现代静态站点生成器
        Docusaurus                  // [yes]Facebook开源文档框架
    前端工具
        Vite                        // 构建工具详情[官网:star](https://vitejs.dev/)
        Vue.js                      // [star]渐进式[yes]JavaScript框架
</LiteTree>
:::

## 变量系统 {#variable-system}

::: alert {"type": "info", "title": "变量定义位置"}
变量定义**必须**位于树内容的顶部，并以 `---` 分隔符与树的主体内容隔开。
:::

### 定义样式变量 (`#name=styles`) {#style-variables}

用于定义可复用的 CSS 样式。

::: demo 样式变量
<LiteTree>
// 定义样式变量
#important=color:red;font-weight:bold;background:#ffe6e6;padding:2px 6px;border-radius:3px;
#success=color:green;font-weight:bold;background:#e6ffe6;padding:2px 6px;border-radius:3px;
---
项目文件
    {#important}关键文件
    {#success}已完成文件
</LiteTree>
:::

### 定义类变量 (`.name=styles`) {#class-variables}

用于定义 CSS 类，便于统一样式。

::: demo 类变量
<LiteTree>
// 定义类变量
.folder=color:#1976d2;font-weight:500;
.file=color:#666;
---
{.folder}源代码
    {.file}main.js
    {.file}config.json
</LiteTree>
:::

### 定义图标变量 (`name=base64data`) {#icon-variables}

使用 Base64 编码的 SVG 定义自定义图标。

::: demo 图标变量
<LiteTree>
// 定义图标变量 (参见上面的转换指南)
folder=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMjQgMjQiPjxwYXRoIGZpbGw9ImN1cnJlbnRDb2xvciIgZD0iTTEwIDRIOGEyIDIgMCAwIDAtMiAydjEyYTIgMiAwIDAgMCAyIDJoOGEyIDIgMCAwIDAgMi0yVjhhMiAyIDAgMCAwLTItMmgtM2wtMi0yWiIvPjwvc3ZnPg==
file=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMjQgMjQiPjxwYXRoIGZpbGw9ImN1cnJlbnRDb2xvciIgZD0iTTE0IDJINmEyIDIgMCAwIDAtMiAydjE2YTIgMiAwIDAgMCAyIDJoMTJhMiAyIDAgMCAwIDItMlY4bC02LTZtNCA5VjlsNCA0aC00WiIvPjwvc3ZnPg==
---
[folder] 前端项目
    [folder] src
        [file] Header.vue
    [file] package.json
</LiteTree>
:::

## 完整示例 {#full-example}

:::demo 完整示例
<LiteTree>
// 样式定义
#new=color:white;background:#4caf50;padding:1px 4px;border-radius:2px;font-size:12px;
#deprecated=color:white;background:#f44336;padding:1px 4px;border-radius:2px;font-size:12px;
.important=font-weight:bold;color:#1976d2;
// 图标定义
vue=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMjQgMjQiPjxwYXRoIGZpbGw9IiM0Y2FmNTAiIGQ9Ik0yIDIwaDIwTDEyIDR6Ii8+PC9zdmc+
ts=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMTUgMTUiPjxwYXRoIGZpbGw9Im5vbmUiIHN0cm9rZT0iIzMxNzhDNiIgZD0iTTEyLjUgOHYtLjE2N2MwLS43MzYtLjU5Ny0xLjMzMy0xLjMzMy0xLjMzM0gxMGExLjUgMSLNSAxIDAgMSAwIDAgM2gxYTEuNSAxLjUgMCAwIDEgMCAzaC0xQTEuNSAxLjUgMCAwIDEgOC41IDExTTggNi41SDNtMi4lIDBWMTNNMS41LjVoMTN2MTRIOS41eiIvPjwvc3ZnPg==
---
{.important}CrychicDoc 项目
    .vitepress                    // {.important}配置目录
        config
            [ts] index.ts         // {#new}更新配置
        plugins                   // {.important}自定义插件
            [ts] custom-alert.ts  // {#new}警告插件
        theme
            [vue] components      // {.important}Vue 组件
                [vue] CustomAlert.vue  // {#new}新组件
    docs
        zh                        // 中文文档
            styleList.md          // {#deprecated}需要更新
        en                        // 英文文档
            litetree-guide.md   // {#new}本指南
    package.json                  //v    项目配置
    README.md                     //!    {.important}重要文档
</LiteTree>
:::

## 内联样式

### 直接颜色样式

使用 `{color:value}` 语法直接对文本应用样式:

:::demo 内联颜色
<LiteTree>
项目状态
    {color:green}已完成功能
    {color:orange}进行中
    {color:red}严重问题
    {color:blue;font-weight:bold}重要说明
</LiteTree>
:::

### 混合样式

结合变量、内联样式、图标和标记符:

:::demo 完整示例
<LiteTree>
// 样式定义
#new=color:white;background:#4caf50;padding:1px 4px;border-radius:2px;font-size:12px;
#deprecated=color:white;background:#f44336;padding:1px 4px;border-radius:2px;font-size:12px;
.important=font-weight:bold;color:#1976d2;
// 图标定义
vue=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMjQgMjQiPjxwYXRoIGZpbGw9IiM0Y2FmNTAiIGQ9Ik0yIDIwaDIwTDEyIDR6Ii8+PC9zdmc+
ts=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMTUgMTUiPjxwYXRoIGZpbGw9Im5vbmUiIHN0cm9rZT0iIzMxNzhDNiIgZD0iTTEyLjUgOHYtLjE2N2MwLS43MzYtLjU5Ny0xLjMzMy0xLjMzMy0xLjMzM0gxMGExLjUgMSLNSAxIDAgMSAwIDAgM2gxYTEuNSAxLjUgMCAwIDEgMCAzaC0xQTEuNSAxLjUgMCAwIDEgOC41IDExTTggNi41SDNtMi4lIDBWMTNNMS41LjVoMTN2MTRIOS41eiIvPjwvc3ZnPg==
---
{.important}CrychicDoc 项目
    .vitepress                    // {.important}配置目录
        config
            [ts] index.ts         // {#new}更新配置
        plugins                   // {.important}自定义插件
            [ts] custom-alert.ts  // {#new}警告插件
        theme
            [vue] components      // {.important}Vue 组件
                [vue] CustomAlert.vue  // {#new}新组件
    docs
        zh                        // 中文文档
            styleList.md          // {#deprecated}需要更新
        en                        // 英文文档
            litetree-guide.md   // {#new}本指南
    package.json                  //v    项目配置
    README.md                     //!    {.important}重要文档
</LiteTree>
:::

## 高级功能 {#advanced-features}

### 标签与注释组合使用 {#tags-and-comments-combined}

标签和注释可以同时使用，创建更丰富的节点信息。

::: demo 标签注释组合
<LiteTree>
// 定义图标和样式
#priority=color:white;background:#e91e63;padding:1px 4px;border-radius:2px;font-size:11px;
#status=color:white;background:#4caf50;padding:1px 4px;border-radius:2px;font-size:11px;
star=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMjQgMjQiPjxwYXRoIGZpbGw9ImdvbGQiIGQ9Im0xMiAxNS40bC0zLjc2IDIuMjdhMS0xIDAgMCAxLTEuNTMtMS4xMUw3LjMgMTMuNGwtMy4yMy0uMjhhMS0xIDAgMCAxLS41Ni0xLjc1bDIuNjctMi4xMmwtLjY1LTMuMDlhMS0xIDAgMCAxIDEuNDgtMS4xM0wxMiA3LjRsNC4yOC0yLjQ3YTEgMSAwIDAgMSAxLjQ4IDEuMTNsLS42NSAzLjA5TDE5Ljc4IDExYTEgMSAwIDAgMS0uNTYgMS43NWwtMy4yMy4yOGMtLjUxIDIuMTYtLjYzIDMuNTUtMS43NiAyLjM3WiIvPjwvc3ZnPg==
---
产品开发计划
    核心功能(Vue.js,TypeScript,{#priority}High)    // [star]框架核心 - {#status}开发中
    用户界面(CSS,Vuetify,{#priority}Medium)       // UI组件库 - {#status}设计阶段
    API接口(Node.js,Express)                      // 后端服务 - 待启动
</LiteTree>
:::

### 深层嵌套与复杂结构 {#complex-nested-structures}

LiteTree 支持任意深度的嵌套，适合表示复杂的层级结构。

::: demo 复杂嵌套结构
<LiteTree>
// 定义各种样式和图标
#module=color:white;background:#2196f3;padding:2px 6px;border-radius:3px;font-size:12px;
#component=color:white;background:#ff9800;padding:2px 6px;border-radius:3px;font-size:12px;
#utility=color:#666;background:#f5f5f5;padding:2px 6px;border-radius:3px;font-size:12px;
.folder=color:#1976d2;font-weight:500;
.file=color:#666;
---
{.folder}大型前端项目架构                         // {#module}微前端架构
    {.folder}基础设施层                          //! 核心基础设施
        {.folder}构建工具                        // 开发工具链
            webpack.config.js                   // 主配置文件
            babel.config.js                     // JS转译配置
            {.folder}插件扩展                     // 自定义插件
                custom-loader.js                // {#utility}自定义加载器
                optimize-plugin.js              // {#utility}优化插件
        {.folder}开发服务器                       // 本地开发环境
            dev-server.js                       // 开发服务器配置
            proxy.config.js                     // 代理配置
    {.folder}应用层                              // {#module}业务逻辑层
        {.folder}页面组件                         // 页面级组件
            {.folder}用户管理                     // 用户相关页面
                UserList.vue                    // {#component}用户列表
                UserDetail.vue                  // {#component}用户详情
                UserForm.vue                    // {#component}用户表单
            {.folder}订单管理                     // 订单相关页面
                OrderList.vue                   // {#component}订单列表
                OrderTracking.vue               // {#component}订单跟踪
        {.folder}通用组件                         // 可复用组件
            Button.vue                          // {#component}按钮组件
            Modal.vue                           // {#component}弹窗组件
            DataTable.vue                       // {#component}数据表格
    {.folder}服务层                              // {#module}数据服务层
        {.folder}API服务                         // 外部接口
            user.service.js                     // 用户API服务
            order.service.js                    // 订单API服务
        {.folder}状态管理                         // 应用状态
            store.js                            // Vuex/Pinia存储
            modules                             // 状态模块
</LiteTree>
:::

## 常见用例

### 项目文件结构

:::demo 项目结构
<LiteTree>
// 文件类型样式
.folder=color:#1976d2;font-weight:500;
.file=color:#666;
.config=color:#f57c00;font-weight:500;
.doc=color:#8bc34a;
// 状态样式
#completed=color:green;background:#e6ffe6;padding:1px 3px;border-radius:2px;font-size:11px;
#inprogress=color:orange;background:#fff3e0;padding:1px 3px;border-radius:2px;font-size:11px;
#todo=color:red;background:#ffe6e6;padding:1px 3px;border-radius:2px;font-size:11px;
---
{.folder}我的项目
    {.folder}src                  //v    {#completed}结构完成
        {.folder}components       //+    {#inprogress}添加组件中
            {.file}Header.vue     //v    {#completed}已完成
            {.file}Footer.vue     //+    {#todo}待办
        {.folder}pages
            {.file}Home.vue       //v    {#completed}已完成
            {.file}About.vue      //*    {#inprogress}更新中
    {.config}package.json         //v    {#completed}已配置
    {.config}vite.config.js       //*    {#inprogress}优化中
    {.doc}README.md               //!    {#todo}需要文档
</LiteTree>
:::

### 团队组织

:::demo 团队结构
<LiteTree>
// 团队角色样式
#lead=color:white;background:#1976d2;padding:2px 6px;border-radius:3px;font-size:12px;
#senior=color:#1976d2;background:#e3f2fd;padding:2px 6px;border-radius:3px;font-size:12px;
#junior=color:#666;background:#f5f5f5;padding:2px 6px;border-radius:3px;font-size:12px;
---
开发团队
    前端团队                      // {#lead}团队负责人: 张三
        React 开发者              //+    团队扩充中
            李四                  // {#senior}高级开发
            王五                  // {#junior}初级开发
        Vue 开发者                //v    团队完整
            赵六                  // {#senior}高级开发
            钱七                  // {#junior}初级开发
    后端团队                      // {#lead}团队负责人: 孙八
        API 开发                  //*    重构中
            周九                  // {#senior}高级开发
            吴十                  // {#junior}初级开发
        数据库团队                //!    关键项目
            郑一                  // {#senior}高级开发
</LiteTree>
:::

## VSCode 代码片段 {#vscode-snippets}

项目包含 LiteTree 的完整 VSCode 代码片段，帮助您快速创建各种树形结构。

### 基础片段 {#basic-snippets}

::: alert {"type": "info", "title": "使用方法"}
在 Markdown 文件中输入片段前缀（如 `@file-tree`），然后按 `Tab` 键即可插入对应的代码模板。
:::

| 片段前缀 | 描述 | 用途 |
|:---|:---|:---|
| `@file-tree` | 基础树结构 | 创建简单的文件目录树 |
| `@file-tree-advanced` | 高级树结构 | 包含变量定义和样式的完整树 |
| `@lite-tree-with-tags` | 带标签的树 | 展示标签功能的树结构 |
| `@lite-tree-with-comments` | 带注释的树 | 展示注释功能的树结构 |

### 变量定义片段 {#variable-snippets}

| 片段前缀 | 描述 | 生成内容 |
|:---|:---|:---|
| `@lite-style-var` | 样式变量定义 | `#name=color:value;background:value;` |
| `@lite-class-var` | 类变量定义 | `.name=color:value;font-weight:value;` |
| `@lite-icon-var` | 图标变量定义 | `name=data:image/svg+xml;base64,...` |

### 预设图标片段 {#icon-snippets}

| 片段前缀 | 图标类型 | Base64编码 |
|:---|:---|:---|
| `@icon-folder` | 文件夹图标 | 蓝色文件夹SVG |
| `@icon-file` | 文件图标 | 通用文件SVG |
| `@icon-js` | JavaScript图标 | JS文件类型图标 |
| `@icon-ts` | TypeScript图标 | TS文件类型图标 |
| `@icon-vue` | Vue.js图标 | Vue组件图标 |
| `@icon-github` | GitHub图标 | GitHub品牌图标 |
| `@icon-star` | 星标图标 | 金色星形图标 |

### 预设样式片段 {#preset-style-snippets}

| 片段前缀 | 描述 | 包含样式 |
|:---|:---|:---|
| `@lite-status-styles` | 状态样式集 | 成功、警告、错误、信息状态样式 |
| `@lite-filetype-styles` | 文件类型样式 | 文件夹、文件、配置文件样式 |
| `@lite-priority-styles` | 优先级样式 | 高、中、低优先级标签样式 |

### 完整示例片段 {#example-snippets}

::: demo 使用代码片段创建的树结构
<LiteTree>
// 通过 @lite-status-styles 生成的样式
#success=color:white;background:#4caf50;padding:2px 6px;border-radius:3px;font-size:12px;
#warning=color:white;background:#ff9800;padding:2px 6px;border-radius:3px;font-size:12px;
#error=color:white;background:#f44336;padding:2px 6px;border-radius:3px;font-size:12px;
// 通过 @icon-folder 等生成的图标
folder=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMjQgMjQiPjxwYXRoIGZpbGw9ImN1cnJlbnRDb2xvciIgZD0iTTEwIDRIOGEyIDIgMCAwIDAtMiAydjEyYTIgMiAwIDAgMCAyIDJoOGEyIDIgMCAwIDAgMi0yVjhhMiAyIDAgMCAwLTItMmgtM2wtMi0yWiIvPjwvc3ZnPg==
---
[folder] 项目根目录                        // {#success}代码片段演示
    src                                  // 源代码目录
        components({#warning}Vue,React)   // 组件库
        utils                            // {#error}工具函数
</LiteTree>
:::