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

1. Implement plugins in `.vitepress/plugins/**`.
2. Register them in `.vitepress/config/markdown-plugins.ts`.
3. Register any required rendering component in `.vitepress/utils/vitepress/components.ts`.
4. Add usage pages under both locale trees.

If the plugin is meant to become a reusable authoring surface, also document:

- the markdown syntax
- the rendered component name
- frontmatter dependencies
- copy-paste-safe examples in both locales

## File Ownership Table

- New hero field or nested frontmatter key: `.vitepress/utils/vitepress/api/frontmatter/**`
- Theme-ready lifecycle or observer logic: `.vitepress/utils/vitepress/runtime/**`
- New Vue block or visual surface: `.vitepress/theme/components/**`
- New markdown syntax: `.vitepress/plugins/**` plus `.vitepress/config/markdown-plugins.ts`
- New global token or shared skin: `.vitepress/theme/styles/**`
- New developer handbook page: `docs/en/doc/**` and `docs/zh/doc/**`
