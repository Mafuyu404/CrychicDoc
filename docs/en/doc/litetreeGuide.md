---
title: LiteTree Component
description: 在 VitePress 中使用 LiteTree 创建优雅、信息丰富的树形结构的完整指南。
layout: doc
priority: 40
hidden: false
---

# LiteTree 组件使用指南 {#main}

::: v-info
LiteTree 是一个为 VitePress 设计的轻量级树形结构组件。它使用一种类似 YAML 的、基于缩进的语法，使其在 Markdown 文档中书写和维护变得异常简单和直观。
:::

<Linkcard url="https://zhangfisher.github.io/lite-tree/" title="LiteTree 官方文档" description="查看官方文档以获取更多信息。" />

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
有许多免费的在线工具可以将你的 SVG 代码转换为 Base64。我们推荐使用 [SVG to Base64 Converter](https://www.base64-image.de/)，因为它非常简单直接。

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

## VSCode 代码片段

项目包含 LiteTree 的完整 VSCode 代码片段。主要片段包括:

- **`@file-tree`** - 基础树结构
- **`@file-tree-advanced`** - 带变量和样式的树
- **`@lite-style-var`** - 样式变量定义
- **`@lite-class-var`** - 类变量定义
- **`@lite-icon-var`** - 图标变量定义
- **`@icon-folder`**, **`@icon-file`**, **`@icon-js`**, **`@icon-ts`**, **`@icon-vue`** - 常用图标
- **`@lite-status-styles`** - 预定义状态样式
- **`@lite-filetype-styles`** - 预定义文件类型样式
