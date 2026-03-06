---
title: Hero 扩展手册
description: 如何在 CrychicDoc 中扩展 Hero 排版、浮动元素、Shader、背景渲染器与导航搜索视觉。
---

# Hero 扩展手册

Hero 相关能力必须从契约层开始，再向下流向运行时与渲染层。

## Hero 关键扩展点

- `.vitepress/utils/vitepress/api/frontmatter/hero/HeroFrontmatterApi.ts`
- `.vitepress/utils/vitepress/api/frontmatter/hero/HeroTypographyRegistryApi.ts`
- `.vitepress/utils/vitepress/api/frontmatter/hero/FloatingElementRegistryApi.ts`
- `.vitepress/utils/vitepress/runtime/hero/navAdaptiveState.ts`
- `.vitepress/theme/components/hero/background/BackgroundLayer.vue`
- `.vitepress/config/shaders/index.ts`
- `.vitepress/config/shaders/templates/base-shader.ts`

## 新增排版风格

不要在 Hero 组件里直接新增 `if styleType === ...` 这样的分支。  
应通过 typography registry 注册新样式，让运行时统一解析。

有两种注册方式：

1. 共享内建样式
   直接把定义加入 `.vitepress/utils/vitepress/api/frontmatter/hero/HeroTypographyRegistryApi.ts` 里的 `DEFAULT_TYPOGRAPHY_STYLE_DEFINITIONS`。
2. 项目级启动注册
   在一个被 `.vitepress/theme/index.ts` 引入的启动模块里调用 `heroTypographyRegistry.registerStyle(...)`。

canonical `type` 与 aliases 都要保持小写，因为 registry 会按小写规则解析。

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

注册检查清单：

1. 在 `HeroTypographyRegistryApi.ts` 里定义 motion defaults。
2. 保持 canonical type 与 aliases 为小写。
3. 如果新样式需要不同的结构或 class hook，同步更新 Hero 内容组件或共享 Hero 排版 CSS。
4. 通过 `.vitepress/utils/vitepress/runtime/hero/typographyState.ts` 验证运行时解析，不要把 motion 逻辑复制到多个组件里。
5. 补真实 frontmatter 示例，并更新文档。

## 创建新的 Hero 页面

当页面本身是入口页或落地页时，使用 Hero 页面：

```yaml
---
layout: home
hero:
  name: Developer Docs
  text: Extend Hero, Runtime, and Nav
  tagline: Contract-first configuration with shared runtime behavior
  typography:
    type: floating-tilt
  actions:
    - theme: brand
      text: Hero Extension
      link: /zh/doc/heroExtension
---
```

检查清单：

1. 以 `layout: home` 开始。
2. 把 Hero 配置保持在 frontmatter 中，避免页面局部组件 hack。
3. 如果该页面成为主入口，同步更新文档入口页与 locale nav。

## 新增浮动元素类型

```ts
import { floatingElementRegistry } from "@utils/vitepress/api/frontmatter/hero";

floatingElementRegistry.registerType({
  type: "keyword-chip",
  renderAs: "badge",
  className: "floating-keyword-chip",
});
```

## 新增 Hero 特性

如果这是一个作者可配置且可复用的 Hero 特性：

1. 先在 `.vitepress/utils/vitepress/api/frontmatter/hero/HeroFrontmatterApi.ts` 中新增字段并做规范化。
2. 如果它需要共享状态、定时、observer 或 viewport 逻辑，在 `.vitepress/utils/vitepress/runtime/hero/**` 中新增或扩展运行时模块。
3. 契约形状稳定后，再在对应 Hero 组件中渲染它。
4. 在中英文文档树中补真实示例。
5. 如果它会影响首页 Hero actions 或命名链接，也要同步更新相关导航/首页文档。

## 新增 Shader 预设

1. 在 `.vitepress/config/shaders/**` 下创建或扩展预设。
2. 尽量复用 `.vitepress/config/shaders/templates/base-shader.ts` 的工具函数。
3. 在 `.vitepress/config/shaders/index.ts` 中统一导出。
4. 通过规范化后的 frontmatter 引用预设，而不是在页面里直接 import。

## 新增背景渲染器类型

1. 先在 `HeroFrontmatterApi.ts` 中加入新类型并完成规范化。
2. 在 `.vitepress/theme/components/hero/background/BackgroundLayer.vue` 中增加对应分发分支。
3. 在 `.vitepress/theme/components/hero/background/` 下创建专用渲染组件。
4. 若依赖主题同步或 observer，请放入共享运行时。
5. 在中英文文档树中补充真实示例。

## 扩展导航与搜索视觉

1. 自适应计算放入 `.vitepress/utils/vitepress/runtime/hero/navAdaptiveState.ts`。
2. 主题安全取值逻辑放入共享主题运行时。
3. 若视觉需要按页面配置，优先通过 frontmatter 驱动 CSS 变量暴露给作者。
4. 禁止在导航、搜索或 Hero 子组件中直接读取 DOM 主题类名。

## Hero 扩展完成前检查

1. 新字段或新类型已经在 API 层规范化。
2. 渲染组件只消费规范化后的值。
3. 主题与尺寸逻辑复用了共享运行时。
4. 英中两套文档已同步。
5. `yarn build` 通过后再同步到其他仓库。
