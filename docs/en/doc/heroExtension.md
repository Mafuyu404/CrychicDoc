---
title: Hero Extension Playbook
description: How to extend hero typography, floating items, shaders, background renderers, and nav/search visuals in CrychicDoc.
---

# Hero Extension Playbook

Hero work should start from the contract layer and flow downward into runtime and rendering.

## Core Hero Extension Points

- `.vitepress/utils/vitepress/api/frontmatter/hero/HeroFrontmatterApi.ts`
- `.vitepress/utils/vitepress/api/frontmatter/hero/HeroTypographyRegistryApi.ts`
- `.vitepress/utils/vitepress/api/frontmatter/hero/FloatingElementRegistryApi.ts`
- `.vitepress/utils/vitepress/runtime/hero/navAdaptiveState.ts`
- `.vitepress/theme/components/hero/background/BackgroundLayer.vue`
- `.vitepress/config/shaders/index.ts`
- `.vitepress/config/shaders/templates/base-shader.ts`

## Add a Typography Style

Do not add a new `if styleType === ...` branch directly in hero components.
Register the style through the typography registry and let the runtime resolve it.

Use one of these registration modes:

1. Shared built-in style
   Add the definition to `DEFAULT_TYPOGRAPHY_STYLE_DEFINITIONS` in `.vitepress/utils/vitepress/api/frontmatter/hero/HeroTypographyRegistryApi.ts`.
2. Project-local bootstrap style
   Call `heroTypographyRegistry.registerStyle(...)` from a startup module imported by `.vitepress/theme/index.ts`.

Keep the canonical `type` and all aliases lowercase because the registry normalizes them.

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

Registration checklist:

1. Define motion defaults in `HeroTypographyRegistryApi.ts`.
2. Keep the canonical type and aliases lowercase.
3. If the new style needs different structure or class hooks, update hero content components or the shared hero typography CSS.
4. Verify the resolved style through `.vitepress/utils/vitepress/runtime/hero/typographyState.ts`, not by copying motion logic into multiple components.
5. Add a real frontmatter example and update the docs.

## Create a New Hero Page

Use a hero page when the page itself is a landing surface instead of a normal article.

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
      link: /en/doc/heroExtension
---
```

Checklist:

1. Start from `layout: home`.
2. Keep hero configuration in frontmatter instead of page-local component hacks.
3. If the page becomes a main entry point, update the docs hub and locale nav in the same change.

## Add a Floating Element Type

```ts
import { floatingElementRegistry } from "@utils/vitepress/api/frontmatter/hero";

floatingElementRegistry.registerType({
  type: "keyword-chip",
  renderAs: "badge",
  className: "floating-keyword-chip",
});
```

## Add a New Hero Feature

When the feature is author-facing and reusable:

1. Add the field to `.vitepress/utils/vitepress/api/frontmatter/hero/HeroFrontmatterApi.ts` and normalize it there.
2. If it needs shared state, timing, observers, or viewport logic, add or extend `.vitepress/utils/vitepress/runtime/hero/**`.
3. Render the feature only after the contract shape is stable.
4. Add real examples in both locale trees.
5. If it changes home hero actions or named links, update the related nav/home docs at the same time.

## Add a Shader Preset

1. Create or extend a preset under `.vitepress/config/shaders/**`.
2. Reuse helpers from `.vitepress/config/shaders/templates/base-shader.ts` where possible.
3. Export the preset from `.vitepress/config/shaders/index.ts`.
4. Reference the preset through normalized frontmatter rather than page-local imports.

## Add a New Background Renderer Type

1. Add the type to `HeroFrontmatterApi.ts`.
2. Add the rendering branch in `.vitepress/theme/components/hero/background/BackgroundLayer.vue`.
3. Create a dedicated renderer under `.vitepress/theme/components/hero/background/`.
4. Move observers or theme-sensitive lifecycle into shared runtime modules.
5. Add real examples in both locale trees.

## Extend Nav and Search Visuals

1. Put adaptive calculations in `.vitepress/utils/vitepress/runtime/hero/navAdaptiveState.ts`.
2. Put theme-safe value resolution in the shared theme runtime.
3. Expose author-facing colors through frontmatter-backed CSS variables.
4. Avoid direct DOM theme reads inside nav, search, or hero child components.

## Hero Extension Checklist

1. The new field or type is normalized in the API layer.
2. Rendering components consume normalized values only.
3. Theme and resize behavior use shared runtime helpers.
4. The change is documented in both `docs/en` and `docs/zh`.
5. `yarn build` passes before syncing the change elsewhere.
