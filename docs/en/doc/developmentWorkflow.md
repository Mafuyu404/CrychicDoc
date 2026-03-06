---
title: Development Workflow
description: Recommended change order, verification commands, and sync rules for framework work in CrychicDoc.
---

# Development Workflow

This page defines how to change CrychicDoc's framework layer without drifting away from the shared template architecture.

## Start With the Contract

Use this order when changing framework behavior:

1. Update `.vitepress/utils/vitepress/api/**` first.
   Change types, schema, normalization, registries, and compatibility behavior here.
2. Update `.vitepress/utils/vitepress/runtime/**` second.
   Move lifecycle, theme sync, viewport sync, DOM observation, and adaptive state into shared runtime modules.
3. Update `.vitepress/theme/components/**` third.
   Keep rendering components focused on view composition and normalized runtime state.
4. Update `docs/en/**` and `docs/zh/**` examples and reference docs in the same change.
5. Sync relevant changes back to `M1honoVitepressTemplate` and other template-derived repositories when the framework contract changed.

## Verification Commands

Run these commands from the repository root after framework changes:

```bash
yarn locale
yarn sidebar
yarn tags
yarn build
```

Run these too when config or frontmatter contracts changed:

```bash
yarn sync-config
yarn frontmatter
```

## Change Routing Guide

- `.vitepress/utils/vitepress/api/**`: contract layer
- `.vitepress/utils/vitepress/runtime/**`: shared runtime state
- `.vitepress/theme/components/**`: rendering layer
- `.vitepress/config/**`: project defaults, locale wiring, shader registry, markdown plugin composition
- `.vitepress/plugins/**`: markdown-it implementations
- `.vitepress/theme/styles/**`: shared CSS variables and global style layers
- `docs/**`: product docs, examples, and contributor-facing references

## Task Entry Patterns

Use these starting points for common work:

- New normal page
  Start in `docs/en/**` and `docs/zh/**` with matching relative paths, then decide whether the page also needs top-level nav or home-page entry points.
- New hero page
  Start with `layout: home`, author `hero` and `features` frontmatter first, and only then wire new hero contracts into `.vitepress/utils/vitepress/api/frontmatter/hero/**`.
- New reusable component
  Start in `.vitepress/theme/components/**`, then finish the full registration chain: component registry export, markdown/global registration, locale JSON, and i18n mapping.
- New hero feature
  Start in `.vitepress/utils/vitepress/api/frontmatter/hero/**`, move shared state into `.vitepress/utils/vitepress/runtime/hero/**`, and keep render changes in `.vitepress/theme/components/hero/**`.
- New markdown plugin
  Start in `.vitepress/plugins/**`, register it in `.vitepress/config/markdown-plugins.ts`, and add the rendering component plus docs examples in the same change.

## Sync Rule

When a change modifies the shared framework instead of just CrychicDoc content:

1. Identify whether the upstream template should become the source of truth.
2. Keep file ownership aligned with the template where possible.
3. Avoid CrychicDoc-only workarounds for problems that belong in shared runtime or API layers.
4. Update the mirrored developer docs in other template-based repositories after verification.

## Review Checklist

1. Is the contract defined in `api`, not hidden in a component?
2. Is shared lifecycle logic in `runtime`, not repeated locally?
3. Is the extension documented in both English and Chinese?
4. Are examples compile-safe and based on real keys/components?
5. Is the upstream/downstream sync plan clear?
