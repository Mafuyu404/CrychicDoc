---
title: Framework Maintainability Guide
description: Engineering guide for extending components, i18n, navigation layouts, floating elements, and markdown plugins without changing core system code.
---

# Framework Maintainability Guide

## Scope and Objective

This guide defines the extension-first engineering model for this VitePress framework.  
The primary objective is to allow feature growth through configuration and registration APIs, not core rewrites.

## Detailed Pages

- [Development Workflow](./developmentWorkflow)
- [Extension Architecture](./extensionArchitecture)
- [Hero Extension Playbook](./heroExtension)

## Source-of-Truth Structure

- Runtime and APIs: `.vitepress/utils/vitepress/**`
- Theme components: `.vitepress/theme/components/**`
- Locale resources: `.vitepress/config/locale/**`
- Markdown plugins: `.vitepress/plugins/**`
- Plugin registration: `.vitepress/config/markdown-plugins.ts`

All internal imports must use project aliases (`@utils`, `@config`, `@components`) to keep refactors stable.

## Extension APIs (No Core Edits Required)

### 1. Typography Style Registry

API: `@utils/vitepress/api/frontmatter/hero/HeroTypographyRegistryApi`

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

Then use `hero.typography.type: editorial-soft` in frontmatter.  
No core typography runtime changes are required.

### 2. Navigation Dropdown Layout Registry

API: `@utils/vitepress/api/navigation/NavDropdownLayoutRegistryApi`

```ts
import { navDropdownLayoutRegistry } from "@utils/vitepress/api/navigation";
import VPNavLayoutEditorial from "@components/navigation/layouts/VPNavLayoutEditorial.vue";

navDropdownLayoutRegistry.registerLayout("editorial", VPNavLayoutEditorial);
```

Then in nav config:

```ts
dropdown: {
  layout: "editorial",
  panels: [...]
}
```

Alternative override (per-item):

```ts
dropdown: {
  layoutComponent: "VPNavLayoutEditorial",
  panels: [...]
}
```

### 3. Floating Element Type Registry

API: `@utils/vitepress/api/frontmatter/hero/FloatingElementRegistryApi`

```ts
import { floatingElementRegistry } from "@utils/vitepress/api/frontmatter/hero";

floatingElementRegistry.registerType({
    type: "keyword-chip",
    renderAs: "badge",
    className: "floating-keyword-chip",
});
```

Frontmatter usage:

```yaml
hero:
  floating:
    items:
      - type: keyword-chip
        text: Event API
```

For fully custom UI, bind to a registered Vue component:

```yaml
hero:
  floating:
    items:
      - component: HeroFloatingCourseCard
        componentProps:
          title: KubeJS Course
          provider: GitBook
```

## Creating a New Component

1. Create the component file under `.vitepress/theme/components/<category>/`.
2. Export it from the corresponding registry barrel under `.vitepress/utils/vitepress/componentRegistry/` if it must be reusable globally.
3. Register it in `.vitepress/utils/vitepress/components.ts` if Markdown/global component usage is required.
4. Add locale JSON files under:
   - `.vitepress/config/locale/en-US/components/...`
   - `.vitepress/config/locale/zh-CN/components/...`
5. Add or update component ID mapping in `.vitepress/config/locale/component-id-mapping.json`.

Minimal i18n component pattern:

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

## i18n System Contract

- `useSafeI18n` now resolves locale reactively from VitePress language state.
- Translation buckets are cached per `componentId@locale`.
- Locale switches update component text without reload.
- Missing keys automatically fall back to default translations passed in code.
- Component file-path mapping is resolved from `component-id-mapping.json`.

This design is resilient for dynamic locale switches and avoids stale translation state from singleton non-reactive access.

## Adding a New Markdown Plugin

1. Add plugin implementation in `.vitepress/plugins/`.
2. Wire it in `.vitepress/config/markdown-plugins.ts`.
3. If plugin output needs a custom component, register the component via `components.ts`.
4. Add localized usage examples in docs pages.

## Theme Sync Standard (First Enter + Reload Safe)

Use this standard for every component that has theme-sensitive visuals (hero backgrounds, themed assets, icon sets, metadata-like visual blocks, nav cards).

1. Do not read DOM theme classes directly in feature components.
2. Do not use raw `useData().isDark` as the sole source for first-paint visuals.
3. Use `getThemeRuntime(isDark)` and consume `effectiveDark`, `themeReady`, and `version` for visual decisions that must be stable on first enter, reload, and runtime toggle.
4. Inside hero descendants, use `useHeroTheme()` and prefer `isDarkRef.value` plus `resolveThemeValueByMode(...)` (or the sibling helpers `resolveThemeColorByMode` / `resolveThemeSourceByMode`).
5. For first-paint-sensitive hero parts, gate rendering with `themeReady` in `VPHero` to avoid light/dark flash.
6. Never fall back from dark to light or light to dark automatically. Shared resolvers (`resolveThemeValueByMode`, `resolveThemeColorByMode`, `resolveThemeSourceByMode`) follow the `dark ?? value` and `light ?? value` contract.
7. Component folders stay view-only. If theme sync needs observers, scheduling, or shared lifecycle, move that logic into `.vitepress/utils/vitepress/runtime/theme/**`.

Reference APIs:
- `@utils/vitepress/runtime/theme/themeRuntime` (`getThemeRuntime`)
- `@utils/vitepress/runtime/theme/heroThemeContext`
- `@utils/vitepress/runtime/theme/themeValueResolver`

Minimal pattern:

```ts
import { useData } from "vitepress";
import { getThemeRuntime } from "@utils/vitepress/runtime/theme";

const { isDark } = useData();
const { effectiveDark, themeReady, version } = getThemeRuntime(isDark);
```

## Resize Sync Standard

All resize-sensitive components must use the shared resize runtime instead of ad-hoc observers.

1. Use `createElementResizeState(targetRef, onResize, { debounceMs })`.
2. Re-observe once the target element exists (`if (targetRef.value) reobserve(targetRef.value)`).
3. Do not create manual `ResizeObserver` instances unless the shared API cannot satisfy a special case.
4. Keep cleanup lifecycle in the shared runtime, not duplicated per component.

Reference API:
- `@utils/vitepress/runtime/viewport/elementResizeState`

Minimal pattern:

```ts
import { createElementResizeState } from "@utils/vitepress/runtime/viewport";

const targetRef = ref<HTMLElement | null>(null);
const { reobserve } = createElementResizeState(
  targetRef,
  () => syncLayout(),
  { debounceMs: 80 },
);
```

## Day-to-Day Development Workflow

Use this order when changing framework behavior so contracts stay synchronized:

1. Change the contract first.
   Update schema, types, and normalization in `.vitepress/utils/vitepress/api/**`.
2. Change shared runtime second.
   Put stateful lifecycle, DOM observation, viewport sync, theme sync, or adaptive logic in `.vitepress/utils/vitepress/runtime/**`.
3. Change view components third.
   Keep `.vitepress/theme/components/**` focused on rendering and light composition.
4. Update examples and docs immediately.
   If a new frontmatter key or extension point exists, add at least one markdown example and document the intended usage.
5. Run the matching verification commands before merge.

Recommended command sequence from repo root:

```bash
yarn locale
yarn sidebar
yarn tags
yarn build
```

When config or frontmatter contracts changed, also run:

```bash
yarn sync-config
yarn frontmatter
```

## Code Placement Guide

Use these directories as the primary source of truth:

- `.vitepress/config/project-config.ts`
  Site-level product settings, feature toggles, language list, search provider, deployment, social links.
- `.vitepress/config/lang/**`
  Nav/theme/search locale modules.
- `.vitepress/config/shaders/**`
  Built-in shader templates and shader registry.
- `.vitepress/config/markdown-plugins.ts`
  Markdown plugin composition and registration order.
- `.vitepress/plugins/**`
  Markdown-it plugin implementations.
- `.vitepress/theme/components/**`
  Vue rendering layer. Components should stay thin and consume normalized config/runtime state.
- `.vitepress/theme/styles/**`
  Global style layers, variables, plugin styles, shared component CSS.
- `.vitepress/utils/vitepress/api/**`
  Schemas, normalization, registries, contract types, extension APIs.
- `.vitepress/utils/vitepress/runtime/**`
  Stateful domains such as theme sync, viewport sync, hero behavior, media/runtime observers.
- `.vitepress/utils/vitepress/componentRegistry/**`
  Reusable export barrels for globally shared components.
- `.vitepress/utils/vitepress/components.ts`
  Final global component registration for markdown/runtime use.

Rule of thumb:

- If it parses or validates config, it belongs in `api`.
- If it owns lifecycle or DOM coordination, it belongs in `runtime`.
- If it just renders props/state, it belongs in `theme/components`.

## Runtime and Function Extension Playbook

When adding a new function, composable, service, or controller:

1. Put pure contract helpers in `api`, not `theme/components`.
2. Put stateful controllers in `runtime`, preferably as small class-based modules when lifecycle is non-trivial.
3. Export new public APIs from the nearest `index.ts` barrel.
4. Avoid direct DOM reads in feature components when the behavior is shared or timing-sensitive.
5. Prefer one shared observer/runtime over repeated `MutationObserver` or `ResizeObserver` instances inside many components.

Good examples already in the framework:

- Theme stabilization: `.vitepress/utils/vitepress/runtime/theme/**`
- Element resize runtime: `.vitepress/utils/vitepress/runtime/viewport/**`
- Hero nav adaptation: `.vitepress/utils/vitepress/runtime/hero/navAdaptiveState.ts`

## Component and Global Registration Playbook

When adding a new Vue component:

1. Create it under the correct folder in `.vitepress/theme/components/<category>/`.
2. If it should be imported by other framework code, export it from the matching barrel in `.vitepress/utils/vitepress/componentRegistry/**`.
3. If markdown users should be able to write it directly, register it in `.vitepress/utils/vitepress/components.ts`.
4. If it has UI text, add locale resources and keep component ID mapping synchronized.

For markdown-facing components, the registry chain should stay:

`component file` -> `componentRegistry barrel` -> `components.ts` -> markdown/runtime consumption

## Configuration Extension Playbook

For new configuration or frontmatter fields:

1. Add the type to `.vitepress/utils/vitepress/api/frontmatter/hero/HeroFrontmatterApi.ts` or the relevant API module.
2. Normalize legacy and modern forms in the same API layer.
3. Keep rendering components consuming normalized values only.
4. If the field affects nav/search/theme behavior, update the matching runtime controller rather than duplicating logic in the component.
5. Add a docs example page and update the maintainability/reference docs in the same change.

For site-level feature changes:

1. Update `.vitepress/config/project-config.ts`.
2. Update locale config under `.vitepress/config/lang/**` if labels or search locales change.
3. Run `yarn sync-config` and `yarn frontmatter` when the configuration must propagate into docs metadata or generated content.

## Style Extension Playbook

Global styling is layered deliberately. Follow the import order in `.vitepress/theme/styles/index.css`:

1. Config variables
2. Base styles
3. Plugin styles
4. Shared component styles

Use the right style vehicle for the job:

- Scoped `<style>` in a component:
  Use for component-local layout and visuals.
- Global CSS under `.vitepress/theme/styles/**`:
  Use for cross-component tokens, plugin skinning, layout primitives, and theme-wide selectors.
- CSS variables via frontmatter/config:
  Use for runtime-themable values, especially hero/background/nav/search colors.

Do not introduce ad-hoc global selectors when a CSS variable contract or scoped rule is enough.

## Hero Extension Playbook

For the full hero extension guide — including typography styles, floating elements, shader templates, background renderers, and nav/search visuals — see the dedicated page:

**→ [Hero Extension Playbook](./heroExtension)**

## Documentation and Verification Checklist

Every framework-facing extension should ship with:

1. Type updates
2. Runtime/component integration
3. At least one markdown example
4. Locale/doc updates in both languages when applicable
5. Verification commands recorded in the PR or handoff

Minimum verification for framework work:

- `yarn locale`
- `yarn sidebar`
- `yarn tags`
- `yarn build`

## Maintenance Rules

- Keep APIs small and class-based where lifecycle/state is non-trivial.
- Do not add compatibility re-export stubs for deprecated paths.
- Use alias imports only (`@...`) for internal code.
- Keep extension points registration-driven.
- Validate with:
  - `npx tsc --noEmit`
