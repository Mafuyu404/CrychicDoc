---
title: Extension Architecture
description: Source-of-truth directories and placement rules for configuration, runtime, components, plugins, and styles.
---

# Extension Architecture

This page explains where framework code belongs in CrychicDoc and how to keep contracts, runtime logic, and rendering concerns separated.

## Architectural Rule

Use this split consistently:

- `api` owns contract truth
- `runtime` owns shared state and lifecycle
- `theme/components` owns rendering
- `config` owns project defaults and registration wiring
- `docs/**` owns examples, user guides, and developer-facing documentation

## Create a Standard Page

For a normal documentation page:

1. Create the markdown file under the same relative path in both locale trees.
2. Start with simple page frontmatter:

```yaml
---
title: Example Page
layout: doc
description: What this page covers.
---
```

3. Add nav, docs-hub, or home-page entry points only if the page must be discoverable from those surfaces.
4. If the page introduces a new contract or workflow, update the related developer doc in the same change.

## Create a Home or Hero Page

For a landing page or developer hub page:

1. Use `layout: home`.
2. Define the hero contract before adding view-specific styling:

```yaml
---
layout: home
hero:
  name: Developer Docs
  text: Extend CrychicDoc Safely
  tagline: Runtime, frontmatter, plugins, and content registration
  actions:
    - theme: brand
      text: Development Workflow
      link: /en/doc/developmentWorkflow
---
```

3. Add `features` or `featureCards` only after the hero structure is stable.
4. If the page becomes a primary entry point, update `docs/en/index.md`, `docs/zh/index.md`, and the locale nav files in the same change.

## Component Extension

When adding a new component:

1. Place it under `.vitepress/theme/components/<category>/`.
2. Export reusable ones through `.vitepress/utils/vitepress/componentRegistry/**`.
3. Register markdown-facing ones in `.vitepress/utils/vitepress/components.ts`.
4. Add locale resources if the component renders UI text.
5. Keep component IDs and i18n mapping synchronized.

## Register New Content

For content-style components that appear inside markdown pages or document chrome:

1. Create the component in `.vitepress/theme/components/content/`.
2. Export it from `.vitepress/theme/components/content/index.ts` when that barrel is used.
3. Export it from `.vitepress/utils/vitepress/componentRegistry/contentRegistry.ts` when theme internals should import it through the registry layer.
4. Register it in `.vitepress/utils/vitepress/components.ts` when markdown pages should reference it directly by tag name.
5. If it uses `useSafeI18n`, add locale JSON files and keep `component-id-mapping.json` aligned.
6. If it is emitted by a markdown plugin, make sure the plugin outputs a registered tag.
7. Add at least one real markdown example page so the component is validated by the build.

Use this rule:

- `componentRegistry/contentRegistry.ts`
  Internal reuse inside the theme.
- `components.ts`
  Public markdown/global registration.

## Function and Runtime Extension

When adding a composable, service, controller, or helper:

1. Put pure normalization in `.vitepress/utils/vitepress/api/**`.
2. Put shared stateful behavior in `.vitepress/utils/vitepress/runtime/**`.
3. Export public entry points from the nearest `index.ts` barrel.
4. Prefer one shared runtime over many component-local observers.

Strong examples already in the repo:

- Theme sync: `.vitepress/utils/vitepress/runtime/theme/**`
- Hero nav adaptation: `.vitepress/utils/vitepress/runtime/hero/navAdaptiveState.ts`
- Frontmatter contract normalization: `.vitepress/utils/vitepress/api/frontmatter/hero/HeroFrontmatterApi.ts`

## Configuration Extension

Primary files:

- `.vitepress/config/project-config.ts`
- `.vitepress/config/lang/**`
- `.vitepress/config/markdown-plugins.ts`
- `.vitepress/config/shaders/**`

Checklist:

1. Define the field or registry entry.
2. Normalize it in the API layer.
3. Consume only normalized values in runtime or views.
4. Add docs examples immediately.
5. Run `yarn sync-config` and `yarn frontmatter` when generated metadata depends on the change.

## Style Extension

Follow the import layering in `.vitepress/theme/styles/index.css`.

- Scoped `<style>`: component-local visuals
- Global CSS under `.vitepress/theme/styles/**`: shared tokens, plugin skinning, cross-component selectors
- Frontmatter/config-backed CSS variables: theme-sensitive or runtime-sensitive values

## Create a New Markdown Plugin

### Quick Start with Plugin Factories

The project provides two reusable factories that handle boilerplate for you:

- **`tab-plugin-factory.ts`** — For tab-based plugins (stepper, carousel, comparison, etc.). Produces plugins with `@tab` syntax.
- **`container-plugin-factory.ts`** — For block-container plugins that wrap content without tabs.

A complete template with four working example plugins lives at:

```
.vitepress/plugins/example-new-plugin.ts
```

Minimal factory-based plugin:

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

### Manual Implementation

For plugins that do not fit the tab or container pattern:

1. Implement the plugin in `.vitepress/plugins/**`.
2. Register it in `.vitepress/config/markdown-plugins.ts`.
3. Register any required rendering component in `.vitepress/utils/vitepress/components.ts`.
4. Add usage pages under both locale trees.

If the plugin is meant to become a reusable authoring surface, also document:

- the markdown syntax
- the rendered component name
- frontmatter dependencies
- copy-paste-safe examples in both locales

## Services and System Layer

Beyond `api` and `runtime`, two additional utility layers exist:

### Services

Location: `.vitepress/utils/vitepress/services/**`

Services provide cross-cutting helpers consumed by both runtime and components:

- `homeLinkService.ts` — Resolves locale-aware home page links.
- `metadataService.ts` — Aggregates page metadata from frontmatter and config.

Services are stateless and should not hold reactive state. If state is needed, promote the logic to `runtime`.

### System Abstractions

Location: `.vitepress/utils/vitepress/system/**`

Platform-specific abstractions for file I/O and environment detection:

- `FileSystem.ts` — Portable file system interface.
- `NodeFileSystem.ts` — Node.js implementation of the file system interface.

These are used by build-time scripts and should not be imported in client-side components.

## Navigation Runtime

Location: `.vitepress/utils/vitepress/runtime/navigation/**`

Shared state for navigation behavior that is not hero-specific:

- `breadcrumbState.ts` — Computes breadcrumb segments from the current route.
- `navHoverPreviewState.ts` — Controls link hover-preview behavior.

Follow the same rules as other runtime modules: keep rendering logic in components, keep state logic here.

## Viewport Runtime

Location: `.vitepress/utils/vitepress/runtime/viewport/**`

Shared viewport and layout utilities:

- `elementResizeState.ts` — Debounced element resize observer (use `createElementResizeState`).
- `viewportState.ts` — Reactive viewport dimensions (use `createViewportState`).
- `breakpoints.ts` — Responsive breakpoint resolver (`resolveBreakpoint`).
- `rafQueue.ts` — `requestAnimationFrame` batching queue.
- `debounce.ts` — Generic debounce utility.

Prefer these shared utilities over ad-hoc `ResizeObserver` or `window.addEventListener('resize', ...)` in components.

## Component Registry Barrels

Location: `.vitepress/utils/vitepress/componentRegistry/**`

Each barrel exports components for a specific domain. Use the correct barrel when exporting new components:

- `contentRegistry.ts` — Markdown-facing content components (alerts, dialogs, charts, etc.).
- `navigationRegistry.ts` — Navigation components (breadcrumb, nav layouts, etc.).
- `mediaRegistry.ts` — Media rendering components (image viewers, video players, etc.).
- `uiRegistry.ts` — Generic UI primitives (buttons, cards, etc.).

The chain is: `component file` → `registry barrel` → `components.ts` (global registration).

## Import Alias Reference

The project defines TypeScript path aliases in `tsconfig.json` and Vite aliases in `.vitepress/config/common-config.ts`. Use these consistently in all internal imports:

| Alias | Resolves To | Use For |
|:------|:------------|:--------|
| `@utils` | `.vitepress/utils/` | Runtime, API, i18n, helpers |
| `@config` | `.vitepress/utils/config/` | Config utilities (NOT `.vitepress/config/`!) |
| `@components` | `.vitepress/theme/components/` | Component imports from other components or runtime |
| `@/locale` | `.vitepress/config/locale/` | Locale resource imports |

::: warning Common Gotcha: `@config` ≠ `.vitepress/config/`
The `@config` alias points to `.vitepress/utils/config/`, which holds config utility code. It does **not** point to `.vitepress/config/`, which holds project-level configuration files (shaders, lang, markdown-plugins, etc.).

For files under `.vitepress/config/` (e.g., shader templates), use **relative imports** adjusted to your file's location:

```ts
// Correct — relative path for .vitepress/config/shaders/
import { registerShaderTemplate } from "../../config/shaders";

// Wrong — @config points to .vitepress/utils/config/, not .vitepress/config/
import { registerShaderTemplate } from "@config/shaders"; // ❌ Will not resolve
```
:::

## File Ownership Table

- New hero field or nested frontmatter key: `.vitepress/utils/vitepress/api/frontmatter/**`
- Theme-ready lifecycle or observer logic: `.vitepress/utils/vitepress/runtime/**`
- Navigation state or breadcrumb logic: `.vitepress/utils/vitepress/runtime/navigation/**`
- Viewport or resize behavior: `.vitepress/utils/vitepress/runtime/viewport/**`
- Cross-cutting stateless helper: `.vitepress/utils/vitepress/services/**`
- Build-time platform abstraction: `.vitepress/utils/vitepress/system/**`
- New Vue block or visual surface: `.vitepress/theme/components/**`
- New markdown syntax: `.vitepress/plugins/**` plus `.vitepress/config/markdown-plugins.ts`
- New global token or shared skin: `.vitepress/theme/styles/**`
- New developer handbook page: `docs/en/doc/**` and `docs/zh/doc/**`

## Related Pages

- [Framework Maintainability Guide](./frameworkMaintainability) — Theme sync standard, resize standard, and full extension API reference.
- [Development Workflow](./developmentWorkflow) — Change ordering, verification commands, and upstream sync rules.
- [Hero Extension Playbook](./heroExtension) — Typography, floating elements, shaders, backgrounds, and nav/search visuals.
- [Styles & Plugins Guide](./pluginsGuide) — All available Markdown plugins and custom Vue components.
