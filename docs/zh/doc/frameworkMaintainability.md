---
title: 框架可维护性指南
description: 面向组件扩展、i18n、导航布局、浮动元素与 Markdown 插件的维护规范。
hidden: false
priority: 110
---

# 框架可维护性指南

## 概述

本页整理 CrychicDoc 框架层的维护约定。改动一旦涉及共享字段、运行时行为、组件结构或 Markdown 插件，就应先处理字段定义、默认值与整理逻辑，再进入运行时与视图层。  
共享问题应在共享层解决，不应以页面级补丁代替结构性修改。

## 相关页面

- [扩展架构说明](./extensionArchitecture)
- [Hero 扩展手册](./heroExtension)

## 目录职责

- 运行时与 API：`.vitepress/utils/vitepress/**`
- 主题组件：`.vitepress/theme/components/**`
- 多语言资源：`.vitepress/config/locale/**`
- Markdown 插件实现：`.vitepress/plugins/**`
- 插件注册入口：`.vitepress/config/markdown-plugins.ts`

内部导入必须统一使用别名（`@utils`、`@config`、`@components`），避免路径耦合。

## 可扩展 API（无需改系统内核）

### 1. 排版风格注册表

API：`@utils/vitepress/api/frontmatter/hero/HeroTypographyRegistryApi`

```ts
import { heroTypographyRegistry } from "@utils/vitepress/api/frontmatter/hero";

heroTypographyRegistry.registerStyle({
    type: "editorial-soft",
    aliases: ["soft-editorial"],
    motion: {
        intensity: 0.9,
        title: { x: 6, y: -4, scale: 1.03 },
        text: { x: 8, y: 3, scale: 1.02 },
        tagline: { x: 4, y: 6, scale: 1.01 },
        image: { x: 5, y: -2, scale: 1.015 },
        transitionDuration: 520,
        transitionDelayStep: 36,
        transitionEasing: "cubic-bezier(0.2, 0.9, 0.2, 1)",
    },
});
```

随后在 frontmatter 中配置 `hero.typography.type: editorial-soft` 即可生效。

### 2. 导航下拉布局注册表

API：`@utils/vitepress/api/navigation/NavDropdownLayoutRegistryApi`

```ts
import { navDropdownLayoutRegistry } from "@utils/vitepress/api/navigation";
import VPNavLayoutEditorial from "@components/navigation/layouts/VPNavLayoutEditorial.vue";

navDropdownLayoutRegistry.registerLayout("editorial", VPNavLayoutEditorial);
```

导航配置中可直接使用：

```ts
dropdown: {
  layout: "editorial",
  panels: [...]
}
```

也可按条目指定组件覆盖：

```ts
dropdown: {
  layoutComponent: "VPNavLayoutEditorial",
  panels: [...]
}
```

### 3. 浮动元素类型注册表

API：`@utils/vitepress/api/frontmatter/hero/FloatingElementRegistryApi`

```ts
import { floatingElementRegistry } from "@utils/vitepress/api/frontmatter/hero";

floatingElementRegistry.registerType({
    type: "keyword-chip",
    renderAs: "badge",
    className: "floating-keyword-chip",
});
```

frontmatter 示例：

```yaml
hero:
  floating:
    items:
      - type: keyword-chip
        text: Event API
```

若需完全自定义渲染，可直接指定组件：

```yaml
hero:
  floating:
    items:
      - component: HeroFloatingCourseCard
        componentProps:
          title: KubeJS Course
          provider: GitBook
```

## 新组件开发流程

1. 在 `.vitepress/theme/components/<category>/` 新建组件。
2. 若组件需要被复用，导出到 `.vitepress/utils/vitepress/componentRegistry/` 对应注册表。
3. 若需在 Markdown 中直接使用，加入 `.vitepress/utils/vitepress/components.ts`。
4. 新增多语言 JSON：
   - `.vitepress/config/locale/en-US/components/...`
   - `.vitepress/config/locale/zh-CN/components/...`
5. 在 `.vitepress/config/locale/component-id-mapping.json` 更新组件 ID 与文件路径映射。

最小 i18n 组件模式：

```vue
<script setup lang="ts">
import { useSafeI18n } from "@utils/i18n/locale";

const { t } = useSafeI18n("my-component", {
  title: "Default title",
});
</script>

<template>
  <h2>{{ t.title }}</h2>
</template>
```

## i18n 体系说明

- `useSafeI18n` 基于 VitePress 语言状态进行响应式解析。
- 翻译缓存按 `componentId@locale` 维度管理。
- 切换语言时，组件文本可无刷新更新。
- 缺失键自动回退到代码中的默认文案。
- 组件路径映射由 `component-id-mapping.json` 统一维护。

该实现避免了非响应式单例造成的语言状态滞后问题。

## 新增 Markdown 插件流程

1. 在 `.vitepress/plugins/` 添加插件实现。
2. 在 `.vitepress/config/markdown-plugins.ts` 注册插件。
3. 若插件输出依赖自定义组件，按组件流程注册全局组件。
4. 补充中英文文档示例并验证渲染。

## 主题同步规范（首屏进入 + 刷新一致）

所有存在主题敏感视觉的组件（Hero 背景、主题图标、导航卡片、元信息类视觉块等）必须遵循以下规则：

1. 不要在业务组件中直接读取 DOM 主题类名作为唯一来源。
2. 不要只依赖原始 `useData().isDark` 处理首屏视觉切换。
3. 使用 `getThemeRuntime(isDark)`，并基于 `effectiveDark`、`themeReady`、`version` 做主题分支，保证首次进入、刷新与运行时切换都一致。
4. Hero 子组件中统一使用 `useHeroTheme()`，优先读取 `isDarkRef.value` 与 `resolveThemeValueByMode(...)`（或其兄弟方法 `resolveThemeColorByMode` / `resolveThemeSourceByMode`）。
5. 对首屏敏感的 Hero 视觉层，必须通过 `themeReady` 控制渲染，避免 light/dark 闪烁。
6. 禁止自动从 dark 回退到 light，或从 light 回退到 dark。共享解析器（`resolveThemeValueByMode`、`resolveThemeColorByMode`、`resolveThemeSourceByMode`）统一遵循同一条取值规则：优先读取对应主题值，没有时才回退到通用 `value`。
7. 组件目录只保留视图渲染。若主题同步需要 observer、调度或共享生命周期，必须移动到 `.vitepress/utils/vitepress/runtime/theme/**`。

相关 API：
- `@utils/vitepress/runtime/theme/themeRuntime`（`getThemeRuntime`）
- `@utils/vitepress/runtime/theme/heroThemeContext`
- `@utils/vitepress/runtime/theme/themeValueResolver`

最小示例：

```ts
import { useData } from "vitepress";
import { getThemeRuntime } from "@utils/vitepress/runtime/theme";

const { isDark } = useData();
const { effectiveDark, themeReady, version } = getThemeRuntime(isDark);
```

## 尺寸监听规范（Resize）

所有尺寸敏感组件必须使用共享的尺寸监听运行时，禁止重复手写 `ResizeObserver` 生命周期。

1. 使用 `createElementResizeState(targetRef, onResize, { debounceMs })`。
2. 目标元素挂载后执行 `reobserve(targetRef.value)`。
3. 除非有特殊能力缺口，否则禁止在组件内部单独 new `ResizeObserver`。
4. 清理逻辑由共享运行时负责，组件仅保留业务更新函数。

相关 API：
- `@utils/vitepress/runtime/viewport/elementResizeState`

最小示例：

```ts
import { createElementResizeState } from "@utils/vitepress/runtime/viewport";

const targetRef = ref<HTMLElement | null>(null);
const { reobserve } = createElementResizeState(
  targetRef,
  () => syncLayout(),
  { debounceMs: 80 },
);
```

## 日常开发流程

修改框架能力时，建议按以下顺序推进，避免字段定义、运行时与组件各写各的：

1. 先改定义与整理层。
   在 `.vitepress/utils/vitepress/api/**` 更新类型、schema、整理逻辑。
2. 再改共享运行时。
   主题同步、尺寸监听、Hero 自适应、DOM 观察等状态逻辑统一放到 `.vitepress/utils/vitepress/runtime/**`。
3. 最后改视图组件。
   `.vitepress/theme/components/**` 只负责渲染与轻量组合，不承担复杂生命周期。
4. 同步更新示例与文档。
   新 frontmatter 键或扩展点必须至少有一个 markdown 示例，并写明用法。
5. 合并前执行对应校验命令。

推荐在仓库根目录执行：

```bash
yarn locale
yarn tags
yarn docs:build
```

侧边栏会随开发与构建流程自动生成，通常不需要额外的独立维护步骤。

## 代码放置规则

以下目录是主要职责边界：

- `.vitepress/config/project-config.ts`
  站点级产品配置、功能开关、语言列表、搜索提供方、部署、社交链接。
- `.vitepress/config/lang/**`
  导航、主题、搜索等多语言配置。
- `.vitepress/config/shaders/**`
  内置 shader 模板与 shader 注册表。
- `.vitepress/config/markdown-plugins.ts`
  Markdown 插件组合入口与注册顺序。
- `.vitepress/plugins/**`
  markdown-it 插件实现。
- `.vitepress/theme/components/**`
  Vue 视图层，只消费规范化后的配置与运行时状态。
- `.vitepress/theme/styles/**`
  全局样式层、变量、插件皮肤、共享组件样式。
- `.vitepress/utils/vitepress/api/**`
  字段定义、整理逻辑、注册表、扩展入口。
- `.vitepress/utils/vitepress/runtime/**`
  有状态域：主题同步、尺寸同步、Hero 行为、媒体观察等。
- `.vitepress/utils/vitepress/componentRegistry/**`
  可复用组件的统一导出入口。
- `.vitepress/utils/vitepress/components.ts`
  markdown / 运行时全局组件注册。

判断时可以这样分：

- 负责解析、规范化、校验配置的，放 `api`。
- 负责生命周期、DOM 协调、观察器的，放 `runtime`。
- 只负责展示的，放 `theme/components`。

## 运行时与函数扩展规范

新增函数、composable、service、controller 时：

1. 纯函数与字段整理辅助逻辑放在 `api`，不要塞进组件。
2. 有状态控制器放在 `runtime`，生命周期复杂时优先采用小粒度类式 API。
3. 新增公共能力要从最近的 `index.ts` barrel 导出。
4. 共享或时序敏感逻辑不要在多个组件里重复直接读取 DOM。
5. 优先做一个共享运行时，而不是在多个组件里重复 new `MutationObserver` / `ResizeObserver`。

当前可参考的成熟模式：

- 主题稳定化：`.vitepress/utils/vitepress/runtime/theme/**`
- 元素尺寸运行时：`.vitepress/utils/vitepress/runtime/viewport/**`
- Hero 导航自适应：`.vitepress/utils/vitepress/runtime/hero/navAdaptiveState.ts`

## 组件与全局注册规范

新增 Vue 组件时：

1. 放到 `.vitepress/theme/components/<category>/` 的正确分类目录。
2. 若需要被框架代码复用，先导出到 `.vitepress/utils/vitepress/componentRegistry/**` 对应 barrel。
3. 若需要在 Markdown 中直接使用，再注册到 `.vitepress/utils/vitepress/components.ts`。
4. 若组件带文案，补齐多语言资源并同步组件 ID 映射。

面向 Markdown 的组件注册链建议固定为：

`组件文件` -> `componentRegistry barrel` -> `components.ts` -> markdown / 运行时消费

## 配置扩展规范

新增配置或 frontmatter 字段时：

1. 先在 `.vitepress/utils/vitepress/api/frontmatter/hero/HeroFrontmatterApi.ts` 或对应 API 模块补充类型。
2. 在同一层完成旧格式与新格式的规范化。
3. 视图组件只读取规范化后的结果，不自行兼容多种输入形态。
4. 若字段影响导航、搜索、主题行为，应更新对应 runtime controller，而不是在组件内部重复处理。
5. 同步补充文档页与示例页面。

站点级配置变更时：

1. 更新 `.vitepress/config/project-config.ts`。
2. 若涉及标签或搜索 locale，同步更新 `.vitepress/config/lang/**`。
3. 若配置需要向文档元数据或生成内容传播，直接执行 `yarn docs:build`，确认最终生成结果符合预期。

## 样式扩展规范

全局样式层有明确顺序，遵循 `.vitepress/theme/styles/index.css` 的导入层级：

1. 配置变量
2. 基础样式
3. 插件样式
4. 共享组件样式

不同样式需求使用不同载体：

- 组件内 scoped `<style>`：
  只处理组件局部布局与外观。
- `.vitepress/theme/styles/**` 全局 CSS：
  处理跨组件 token、插件皮肤、布局原语、全站级选择器。
- frontmatter / config 驱动的 CSS 变量：
  处理运行时主题值，尤其是 hero/background/nav/search 颜色。

若一个问题可以通过 CSS 变量规则或 scoped 样式解决，就不要新增全局 ad-hoc 选择器。

## Hero 扩展手册

完整的 Hero 扩展指南 — 包括排版样式、浮动元素、Shader 模板、背景渲染器、导航搜索视觉 — 请参考专门页面：

**→ [Hero 扩展手册](./heroExtension)**

## 文档与校验清单

每个面向框架的扩展至少应同时交付：

1. 类型更新
2. runtime / component 集成
3. 至少一个 markdown 示例
4. 中英文文档同步
5. PR 或 handoff 中记录执行过的验证命令

最低验证要求：

- `yarn locale`
- `yarn tags`
- `yarn docs:build`

## 维护规范

- 非简单状态逻辑必须采用小粒度、可组合的类/API 结构。
- 禁止新增旧路径兼容 re-export 文件。
- 内部代码统一使用 `@` 别名导入。
- 所有扩展入口必须通过注册机制暴露。
- 至少执行以下校验：
  - `npx tsc --noEmit`
