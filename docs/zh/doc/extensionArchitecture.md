---
title: 扩展架构说明
description: CrychicDoc 中配置、运行时、组件、插件与样式的职责边界与落点规则。
hidden: false
---

# 扩展架构说明

本页说明 CrychicDoc 的框架代码应该放在哪里，以及如何保持契约、运行时与渲染层职责清晰。

## 架构分层规则

- `api` 负责契约事实来源
- `runtime` 负责共享状态与生命周期
- `theme/components` 负责渲染
- `config` 负责项目默认值与注册接线
- `docs/**` 负责示例、用户文档与开发手册

## 创建普通页面

新增普通文档页时：

1. 在两个语言目录中创建相同相对路径的 markdown 文件。
2. 从最基础的 frontmatter 开始：

```yaml
---
title: 示例页面
layout: doc
description: 这页讲什么。
---
```

3. 只有在页面需要从导航、文档入口页或首页被发现时，才补这些入口。
4. 如果页面引入了新的契约或工作流，也要同步更新对应开发文档。

## 创建首页或 Hero 页面

落地页或开发入口页使用这一套：

1. 使用 `layout: home`。
2. 先定义 Hero frontmatter：

```yaml
---
layout: home
hero:
  name: Developer Docs
  text: Extend CrychicDoc Safely
  tagline: Runtime、frontmatter、plugin 与内容注册
  actions:
    - theme: brand
      text: Development Workflow
      link: /en/doc/developmentWorkflow
---
```

3. Hero 主体稳定后，再补 `features` 或 `featureCards`。
4. 如果页面成为主要入口，同步更新 `docs/en/index.md`、`docs/zh/index.md` 与 locale nav 文件。

## 组件扩展

1. 新组件放入 `.vitepress/theme/components/<category>/`。
2. 需复用的组件导出到 `.vitepress/utils/vitepress/componentRegistry/**`。
3. 面向 Markdown 的组件在 `.vitepress/utils/vitepress/components.ts` 注册。
4. 组件有 UI 文案时补充 locale 资源。
5. 保持组件 ID 与 i18n 映射一致。

## 深入说明：注册新的内容组件

当组件属于内容层，并会出现在 markdown 页面或文档壳层时，按这条链路处理：

1. 在 `.vitepress/theme/components/content/` 下创建组件。
2. 如果该目录使用 barrel，先导出到 `.vitepress/theme/components/content/index.ts`。
3. 如果主题内部其他模块要通过 registry 复用它，再导出到 `.vitepress/utils/vitepress/componentRegistry/contentRegistry.ts`。
4. 如果 markdown 页面需要直接通过标签名调用它，再注册到 `.vitepress/utils/vitepress/components.ts`。
5. 如果组件使用了 `useSafeI18n`，补全 locale JSON，并保持 `component-id-mapping.json` 对齐。
6. 如果它由 markdown 插件输出，确保插件输出的是已经注册过的标签。
7. 至少补一页真实 markdown 示例，确保构建过程会验证它。

可以这样理解：

- `componentRegistry/contentRegistry.ts`
  给主题内部复用。
- `components.ts`
  给 markdown / 全局标签注册。

## 函数与运行时扩展

1. 纯数据规范化逻辑放入 `.vitepress/utils/vitepress/api/**`。
2. 共享状态型能力放入 `.vitepress/utils/vitepress/runtime/**`。
3. 从最近的 `index.ts` barrel 暴露公共入口。
4. 优先建设一个共享运行时，而不是在多个组件里各写一套 observer。

已有优秀样例：

- 主题同步：`.vitepress/utils/vitepress/runtime/theme/**`
- Hero 导航自适应：`.vitepress/utils/vitepress/runtime/hero/navAdaptiveState.ts`
- Frontmatter 契约规范化：`.vitepress/utils/vitepress/api/frontmatter/hero/HeroFrontmatterApi.ts`

## 配置扩展

主要文件：

- `.vitepress/config/project-config.ts`
- `.vitepress/config/lang/**`
- `.vitepress/config/markdown-plugins.ts`
- `.vitepress/config/shaders/**`

操作检查清单：

1. 定义字段或注册表条目。
2. 在 API 层完成规范化。
3. 运行时与视图层只消费规范化后的值。
4. 立即补充文档示例。
5. 若生成的元数据依赖该变更，执行 `yarn sync-config` 与 `yarn frontmatter`。

## 样式扩展

请遵循 `.vitepress/theme/styles/index.css` 的导入层次。

- 局部视觉：scoped `<style>`
- 跨组件共享 Token 与全局选择器：`.vitepress/theme/styles/**`
- 主题敏感或运行时敏感的值：frontmatter 或配置驱动 CSS 变量

## 创建新的 Markdown 插件

### 使用插件工厂快速创建

项目提供了两个可复用的工厂函数，帮你处理样板代码：

- **`tab-plugin-factory.ts`** — 用于带 tab 的插件（stepper、carousel、comparison 等），生成 `@tab` 语法。
- **`container-plugin-factory.ts`** — 用于无 tab 的块容器插件。

一个包含四个完整示例的模板文件在：

```
.vitepress/plugins/example-new-plugin.ts
```

最小工厂插件示例：

```ts
import { createTabPlugin, configMappers } from "./tab-plugin-factory";

export const gallery = createTabPlugin({
  name: "gallery",
  containerComponent: "div",
  tabComponent: "img",
  configMapping: {
    columns: configMappers.attr("data-columns"),
  },
  defaultConfig: { columns: 2 },
  containerRenderer: (info, config, parsedConfig) =>
    `<div class="md-gallery"${parsedConfig}>`,
  tabRenderer: (data) =>
    `<img src="${data.title}" alt="Gallery image ${data.index + 1}">`,
});
```

### 手动实现

若插件不适合 tab 或 container 模式：

1. 在 `.vitepress/plugins/**` 实现。
2. 在 `.vitepress/config/markdown-plugins.ts` 注册。
3. 若渲染依赖 Vue 组件，在 `.vitepress/utils/vitepress/components.ts` 一并注册。
4. 在中英文文档树中增加示例页面。

如果插件会成为一个可复用的作者入口，也要同时补上：

- markdown 语法
- 对应渲染组件名
- 是否依赖 frontmatter
- 两个语言都能直接复制使用的示例

## 服务与系统抽象层

除 `api` 和 `runtime` 外，还有两个实用工具层：

### Services（服务层）

位置：`.vitepress/utils/vitepress/services/**`

提供跨领域的无状态辅助逻辑，被运行时与组件共同消费：

- `homeLinkService.ts` — 解析多语言首页链接。
- `metadataService.ts` — 聚合来自 frontmatter 和配置的页面元数据。

服务必须无状态。如果需要响应式状态，应提升到 `runtime` 层。

### System（系统抽象层）

位置：`.vitepress/utils/vitepress/system/**`

平台相关的文件 IO 与环境检测抽象：

- `FileSystem.ts` — 可移植文件系统接口。
- `NodeFileSystem.ts` — Node.js 环境下的文件系统实现。

这些模块仅供构建脚本使用，不应在客户端组件中导入。

## 导航运行时

位置：`.vitepress/utils/vitepress/runtime/navigation/**`

非 Hero 相关的导航共享状态：

- `breadcrumbState.ts` — 根据当前路由计算面包屑层级。
- `navHoverPreviewState.ts` — 控制链接悬停预览行为。

遵循与其他 runtime 模块相同的规则：渲染逻辑留在组件中，状态逻辑留在这里。

## Viewport 运行时

位置：`.vitepress/utils/vitepress/runtime/viewport/**`

共享的视口与布局工具：

- `elementResizeState.ts` — 防抖元素尺寸观察器（使用 `createElementResizeState`）。
- `viewportState.ts` — 响应式视口尺寸（使用 `createViewportState`）。
- `breakpoints.ts` — 响应式断点解析（`resolveBreakpoint`）。
- `rafQueue.ts` — `requestAnimationFrame` 批处理队列。
- `debounce.ts` — 通用防抖工具。

优先使用这些共享工具，避免在组件中手动 new `ResizeObserver` 或 `window.addEventListener('resize', ...)`。

## 组件注册表 Barrel

位置：`.vitepress/utils/vitepress/componentRegistry/**`

每个 barrel 文件导出特定领域的组件，新增组件时请选择正确的 barrel：

- `contentRegistry.ts` — 面向 Markdown 的内容组件（alert、dialog、chart 等）。
- `navigationRegistry.ts` — 导航组件（面包屑、导航布局等）。
- `mediaRegistry.ts` — 媒体渲染组件（图片查看器、视频播放器等）。
- `uiRegistry.ts` — 通用 UI 原语（按钮、卡片等）。

注册链路：`组件文件` → `registry barrel` → `components.ts`（全局注册）。

## 导入别名参考

项目在 `tsconfig.json` 和 `.vitepress/config/common-config.ts` 中定义了路径别名。内部导入必须统一使用这些别名：

| 别名 | 解析目标 | 用途 |
|:-----|:---------|:-----|
| `@utils` | `.vitepress/utils/` | 运行时、API、i18n、工具函数 |
| `@config` | `.vitepress/utils/config/` | 配置工具代码（**不是** `.vitepress/config/`！） |
| `@components` | `.vitepress/theme/components/` | 组件间或运行时引用组件 |
| `@/locale` | `.vitepress/config/locale/` | 多语言资源导入 |

::: warning 常见陷阱：`@config` ≠ `.vitepress/config/`
`@config` 别名指向 `.vitepress/utils/config/`，那里存放的是配置工具代码。它**不指向** `.vitepress/config/`，后者存放的是项目级配置文件（shader、lang、markdown-plugins 等）。

对于 `.vitepress/config/` 下的文件（如 shader 模板），请使用**相对路径**：

```ts
// 正确 — 用相对路径引用 .vitepress/config/shaders/
import { registerShaderTemplate } from "../../config/shaders";

// 错误 — @config 指向 .vitepress/utils/config/，不是 .vitepress/config/
import { registerShaderTemplate } from "@config/shaders"; // ❌ 无法解析
```
:::

## 文件职责速查表

- 新的 hero 字段或嵌套 frontmatter 键：`.vitepress/utils/vitepress/api/frontmatter/**`
- 主题稳定、观察器或共享生命周期：`.vitepress/utils/vitepress/runtime/**`
- 导航状态或面包屑逻辑：`.vitepress/utils/vitepress/runtime/navigation/**`
- 视口或尺寸相关行为：`.vitepress/utils/vitepress/runtime/viewport/**`
- 跨领域无状态辅助工具：`.vitepress/utils/vitepress/services/**`
- 构建时平台抽象：`.vitepress/utils/vitepress/system/**`
- 新的 Vue 视觉块或结构组件：`.vitepress/theme/components/**`
- 新的 Markdown 语法：`.vitepress/plugins/**` 与 `.vitepress/config/markdown-plugins.ts`
- 新的全局 Token 或共享皮肤：`.vitepress/theme/styles/**`
- 新的开发者手册页面：`docs/en/doc/**` 与 `docs/zh/doc/**`

## 相关页面

- [框架可维护性指南](./frameworkMaintainability) — 主题同步规范、尺寸监听规范与完整扩展 API 参考。
- [开发工作流](./developmentWorkflow) — 改动顺序、校验命令与上游同步规则。
- [Hero 扩展手册](./heroExtension) — 排版、浮动元素、Shader、背景渲染器与导航搜索视觉。
- [样式与插件指南](./pluginsGuide) — 所有可用 Markdown 插件与自定义 Vue 组件。
