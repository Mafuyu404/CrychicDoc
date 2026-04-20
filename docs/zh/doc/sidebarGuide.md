---
title: 侧边栏配置
description: 说明 CrychicDoc 的侧边栏如何生成，以及新增目录和页面时该改哪些字段。
hidden: false
priority: 10
---

# 侧边栏配置 {#main}

CrychicDoc 的侧边栏由 Markdown 文件与目录级 `frontmatter` 自动生成。新增目录、调整页面顺序或修改展开方式时，通常只需要改动页面本身或对应目录下的 `sidebarIndex.md`。

## 生成方式 {#core-concept}

CrychicDoc 的侧边栏系统并不是手写一份庞大的导航配置，而是通过读取 Markdown 文件与目录元数据中的 `frontmatter` 自动生成。作者真正需要维护的，通常只有页面标题、排序、目录级元数据，以及少量控制展开方式的字段。

::: v-info 提示
这意味着大多数时候你不需要直接维护一份独立的 sidebar 树。真正需要注意的，是页面元数据有没有写对，以及目录层的约定有没有被破坏。
:::

## 常用字段 {#core-fields}

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

## 新建文档区 {#tutorial}

::::: stepper
@tab 建一个根目录
假设你要为一个新的 Mod `AwesomeMod` 建一块文档区。

#### 先创建目录结构
在 `docs/zh/mods/` 下创建新目录 `AwesomeMod`，并在其中创建：
- `sidebarIndex.md`（目录侧边栏元数据）

#### 然后在 `sidebarIndex.md` 里声明为根
编辑 `docs/zh/mods/AwesomeMod/sidebarIndex.md` 的 frontmatter:
```yaml
---
root: true
title: "AwesomeMod 指南"
priority: 100 # 假设这是第100个mod
---
```
@tab 加页面并排序
现在在 `AwesomeMod` 目录下添加 `getting-started.md` 和 `features.md`，再用 `priority` 定义它们的先后顺序。

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
@tab 拆出一个子分组
把 `features.md` 组织到“功能”这个子分组里。

#### 先建立子目录和侧边栏文件
在 `AwesomeMod` 目录下创建 `features/` 子目录，并将 `features.md` 移入其中。然后，在 `features/` 目录下创建 `sidebarIndex.md`。

#### 再配置子目录里的 `sidebarIndex.md`
编辑 `AwesomeMod/features/sidebarIndex.md` 的 frontmatter，为其指定一个标题和优先级。
```yaml
---
title: "功能详解"
priority: 20
---
```
@tab 检查结果
::: alert {"type": "warning", "title": "重要步骤"}
侧边栏现在会在开发与构建过程中自动生成，不再需要单独执行一个 `sidebar` 脚本。
:::
```bash
yarn docs:dev
# 或
yarn docs:build
```
开发时，保存后通常就能看到更新；若你想做一次完整校验，直接运行 `yarn docs:build` 即可。
:::::

## 其他常见情况 {#advanced-tips}

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

如果你的目标只是让当前 root 决定“这一棵 sidebar 里子目录项默认折叠、跟随自己、还是默认展开”，现在就只使用 `useChildrenCollapsed`。不要再把这种显示层控制分散到多个旧字段里。

#### `useChildrenCollapsed` 字段

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

这意味着第一方文档仍然可以继续用 `Catalogue.md` 作为目录页，而站内整理的第三方文档如果本身已经有 `README.md`，就不需要再额外补一个 `Catalogue.md`。真正决定目录如何显示的，仍然是 `sidebarIndex.md` 里的目录级元数据；落地页文件只是“点进去以后打开哪一页”的问题。
