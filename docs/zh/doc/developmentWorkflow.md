---
title: 开发工作流
description: CrychicDoc 框架层改动的推荐顺序、校验命令与同步规则。
---

# 开发工作流

本页用于说明如何在不破坏 CrychicDoc 共享框架契约的前提下修改站点能力。

## 先从契约层开始

建议按以下顺序推进：

1. 先修改 `.vitepress/utils/vitepress/api/**`。
2. 再修改 `.vitepress/utils/vitepress/runtime/**`。
3. 最后修改 `.vitepress/theme/components/**`。
4. 同步更新 `docs/en/**` 与 `docs/zh/**` 的示例和参考文档。
5. 若改动属于共享框架能力，再同步回模板仓库及其他模板派生仓库。

## 校验命令

在仓库根目录执行：

```bash
yarn locale
yarn sidebar
yarn tags
yarn build
```

若影响配置或 frontmatter 契约，还应执行：

```bash
yarn sync-config
yarn frontmatter
```

## 改动归类规则

- `.vitepress/utils/vitepress/api/**`：契约层
- `.vitepress/utils/vitepress/runtime/**`：共享运行时
- `.vitepress/theme/components/**`：渲染层
- `.vitepress/config/**`：项目默认配置、语言配置、Shader 注册与插件组合
- `.vitepress/plugins/**`：Markdown-it 插件实现
- `.vitepress/theme/styles/**`：共享样式层
- `docs/**`：产品文档、示例与开发者参考

## 常见任务起点

遇到这些任务时，优先从下面的入口开始：

- 新建普通页面
  先在 `docs/en/**` 与 `docs/zh/**` 下创建相同相对路径的页面，再判断是否需要补顶层导航或首页入口。
- 新建 Hero 页面
  先从 `layout: home` 与 `hero` / `features` frontmatter 开始，再把新的 Hero 契约补到 `.vitepress/utils/vitepress/api/frontmatter/hero/**`。
- 新建可复用组件
  先在 `.vitepress/theme/components/**` 创建组件，再补齐 registry 导出、全局 markdown 注册、locale JSON 与 i18n mapping。
- 新建 Hero 特性
  先改 `.vitepress/utils/vitepress/api/frontmatter/hero/**`，再把共享状态放到 `.vitepress/utils/vitepress/runtime/hero/**`，最后再改 Hero 渲染组件。
- 新建 Markdown 插件
  先在 `.vitepress/plugins/**` 实现，再接入 `.vitepress/config/markdown-plugins.ts`，并同步补齐渲染组件与文档示例。

## 同步规则

当改动属于共享框架而不仅仅是 CrychicDoc 内容时：

1. 判断上游模板是否应成为事实来源。
2. 尽量与模板保持相同的文件职责划分。
3. 不要把本应进入共享运行时或 API 层的问题，留成 CrychicDoc 私有补丁。
4. 校验后同步更新其他模板派生仓库中的开发文档。

## 提交前检查

1. 契约是否定义在 `api` 层，而不是藏在组件里？
2. 共享生命周期逻辑是否在 `runtime`，而不是本地重复实现？
3. 英中两套文档是否已同步？
4. 示例是否真实可运行？
5. 上下游同步路径是否清晰？
