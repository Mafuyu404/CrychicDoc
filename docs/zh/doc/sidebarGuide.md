---
title: 侧边栏配置
description: 一份任务驱动的实用指南，教您如何高效地配置和管理 CrychicDoc 的侧边栏。
hidden: false
priority: 20
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
| `root` | `boolean` | 将一个目录的 `sidebarIndex.md` 标记为侧边栏的新根节点。 |
| `title` | `string` | **必需**。侧边栏中显示的标题。 |
| `priority`| `number` | **必需**。排序权重，**数字越小，越靠前**。 |
| `maxDepth` | `number` | 控制当前目录视图向下展开的层级深度。 |
| `hidden` | `boolean` | 如果为 `true`，则此页面或目录不会显示在侧边栏中。 |
| `externalLinks` | `object[]`| 在 `root` 节点中添加指向外部网站的链接。 |
| `useChildrenCollapsed` | `object` | 只控制当前生成树里子目录项如何折叠或展开，不改写子项自己的 frontmatter。 |

## 实践教程：配置一个新文档区 {#tutorial}

::::: stepper
@tab 任务一：创建根目录
假设您要为一个新的 Mod `AwesomeMod` 创建文档区。

#### 步骤 1: 创建目录结构
在 `docs/zh/mods/` 下创建新目录 `AwesomeMod`，并在其中创建：
- `sidebarIndex.md`（目录侧边栏元数据）

#### 步骤 2: 在 `sidebarIndex.md` 中声明为根
编辑 `docs/zh/mods/AwesomeMod/sidebarIndex.md` 的 frontmatter:
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

#### 步骤 1: 创建子目录和侧边栏文件
在 `AwesomeMod` 目录下创建 `features/` 子目录，并将 `features.md` 移入其中。然后，在 `features/` 目录下创建 `sidebarIndex.md`。

#### 步骤 2: 在子目录 `sidebarIndex.md` 中配置
编辑 `AwesomeMod/features/sidebarIndex.md` 的 frontmatter，为其指定一个标题和优先级。
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

在根目录 `AwesomeMod/sidebarIndex.md` 的 frontmatter 中添加 `externalLinks`。

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


### 使用 `useChildrenCollapsed` 管理当前树里的子项折叠 {#tip-view-control}

如果你的目标只是让当前 root 决定“这一棵 sidebar 里子目录项默认折叠、跟随自己、还是默认展开”，现在只使用 `useChildrenCollapsed`。

#### `useChildrenCollapsed` 的字段约定

| 字段路径 | 类型 | 默认行为 | 作用 |
|:---|:---|:---|:---|
| `useChildrenCollapsed` | `object` | 不写则保持每个子项自己的 `collapsed` | 当前生成树里的子项折叠规则。 |
| `useChildrenCollapsed.mode` | `"children" \| "self" \| "collapsed" \| "open"` | `children` | 子项是沿用自己、跟随当前目录、强制折叠，还是强制展开。 |
| `useChildrenCollapsed.depth` | `number` | `1` | 影响深度。`1` 只影响直接子项，`2` 继续影响孙级。 |

这个配置只影响**当前这次 sidebar 生成树里的显示状态**：

- 不会改写子目录自己的 `collapsed`
- 不会改写子目录自己的 `maxDepth`
- 不会改写任何子文档 frontmatter
- 不会接管遍历控制，也不会替代 `root`

最小示例：

```yaml
---
root: true
title: "Modpack"
useChildrenCollapsed:
  mode: collapsed
  depth: 1
---
```

这表示当前 root 在这棵树里把直接子目录项默认显示为折叠。

如果你想让子项跟随当前目录自己的 `collapsed`：

```yaml
---
root: true
collapsed: true
useChildrenCollapsed:
  mode: self
  depth: 2
---
```

这会让子项和孙级子项在当前树里都跟随当前目录的折叠状态。

### 目录落地页文件约定 {#tip-directory-landing}

当一个目录需要可点击的“落地页”时，系统会按顺序查找这些文件：

- `index.md`
- `Catalogue.md`
- `README.md`

这意味着第一方文档仍然可以继续用 `Catalogue.md` 作为目录页，而站内整理的第三方文档如果本身已经有 `README.md`，就不需要再额外补一个 `Catalogue.md`。
