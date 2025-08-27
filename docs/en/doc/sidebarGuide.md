---
title: Sidebar Configuration
description: 一份任务驱动的实用指南，教您如何高效地配置和管理 CrychicDoc 的侧边栏。
priority: 20
hidden: false
---

# 侧边栏配置实用指南 {#main}

本指南将通过一系列常见的任务，带您了解如何配置和管理 CrychicDoc 的侧边栏。

## 核心理念：Frontmatter 驱动配置 {#core-concept}

CrychicDoc 的侧边栏系统通过读取您在 Markdown 文件中设置的 `frontmatter` 来**自动生成**配置。您只需要在文档中声明元数据（如 `title`, `priority`），然后运行一个脚本，侧边栏就会自动更新。

::: v-info 提示
这个自动化流程意味着您几乎不需要手动管理复杂的配置文件。
:::

## 核心配置字段 {#core-fields}

为了有效地管理侧边栏，您需要了解以下几个关键的 `frontmatter` 字段。

| 字段 | 类型 | 描述 |
|:---|:---|:---|
| `root` | `boolean` | 将一个目录的 `index.md` 标记为侧边栏的新根节点。 |
| `title` | `string` | **必需**。侧边栏中显示的标题。 |
| `priority`| `number` | **必需**。排序权重，**数字越小，越靠前**。 |
| `hidden` | `boolean` | 如果为 `true`，则此页面或目录不会显示在侧边栏中。 |
| `externalLinks` | `object[]`| 在 `root` 节点中添加指向外部网站的链接。 |

## 实践教程：配置一个新文档区 {#tutorial}

::::: stepper
@tab 任务一：创建根目录
假设您要为一个新的 Mod `AwesomeMod` 创建文档区。

#### 步骤 1: 创建目录结构
在 `docs/zh/mods/` 下创建新目录 `AwesomeMod`，并在其中创建一个 `index.md` 文件。

#### 步骤 2: 在 `index.md` 中声明为根
编辑 `docs/zh/mods/AwesomeMod/index.md` 的 frontmatter:
```yaml
---
root: true
title: "AwesomeMod 指南"
priority: 100 # 假设这是第100个mod
---
```
@tab 任务二：添加页面并排序
现在，我们在 `AwesomeMod` 目录下添加 `getting-started.md` 和 `features.md`。通过 `priority` 字段来定义它们的顺序。

**`getting-started.md`:**
```yaml
---
title: "快速入门"
priority: 1
---
```
**`features.md`:**
```yaml
---
title: "功能介绍"
priority: 10
---
```
@tab 任务三：创建子分组
将 `features.md` 组织到一个“功能”子分组中。

#### 步骤 1: 创建子目录和 `index.md`
在 `AwesomeMod` 目录下创建 `features/` 子目录，并将 `features.md` 移入其中。然后，在 `features/` 目录下创建一个 `index.md` 文件。

#### 步骤 2: 在子目录 `index.md` 中配置
编辑 `AwesomeMod/features/index.md` 的 frontmatter，为其指定一个标题和优先级。
```yaml
---
title: "功能详解"
priority: 20
---
```
@tab 任务四：更新侧边栏
::: alert {"type": "warning", "title": "重要步骤"}
在修改了任何 `frontmatter` (特别是 `priority`) 之后，您**必须**运行以下脚本来应用更改。
:::
```bash
npm run docs:sidebar
```
这个脚本会读取您所有的 `frontmatter` 配置，并自动重新生成侧边栏。
:::::

## 高级技巧与提示 {#advanced-tips}

### 添加外部链接 {#tip-external-links}

在根目录 `AwesomeMod/index.md` 的 frontmatter 中添加 `externalLinks`。

```yaml
---
root: true
title: "AwesomeMod 指南"
# ... 其他配置 ...
externalLinks:
  - text: "官方 Wiki"
    link: "https://awesomemod.com/wiki"
    priority: -999 # 使用负数让链接靠前显示
  - text: "GitHub 仓库"
    link: "https://github.com/user/awesomemod"
    priority: -1000
---
```

### 使用 `itemOrder` 隐式设置优先级 {#tip-item-order}

::: v-info 提示
`itemOrder` 本质上是 `priority` 的一种替代写法。`itemOrder` 中项目的索引位置会被脚本读取并赋值给对应项目的 `priority`。
:::

::: v-warning 警告：`priority` 优先
如果一个文件同时在 `itemOrder` 中被定义，并且**自身**的 frontmatter 中也有一个 `priority` 字段，那么**文件自身的 `priority` 字段会胜出**。因此，我们推荐直接在文件内使用 `priority` 来进行明确的排序。!!文件多起来用itemOrder就有点m了!!
:::
