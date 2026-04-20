---
title: Hero Extension Playbook
description: How to extend hero typography, floating items, shaders, background renderers, and nav/search visuals in CrychicDoc.
hidden: false
priority: 100
---

# Hero Extension Playbook

Do not start hero work inside components. Define the frontmatter fields, defaults, and registry entry points first, then update runtime, and only then touch rendering.

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
  tagline: Shared runtime behavior with clean frontmatter
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
3. Render the feature only after the fields and defaults are stable.
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

## How HeroTypographyRegistry Works

**Source file**: `.vitepress/utils/vitepress/api/frontmatter/hero/HeroTypographyRegistryApi.ts`
**Singleton import**: `import { heroTypographyRegistry } from "@utils/vitepress/api/frontmatter/hero";`

The `HeroTypographyRegistryApi` class manages hero typography styles — each style defines how hero text elements (title, text, tagline, image) move in response to mouse/viewport interaction (parallax motion). Instead of scattering `if styleType === ...` branches across hero components, styles are registered here and resolved at runtime through a single lookup.

### Built-in Styles

| Canonical Type | Aliases | Description |
|---|---|---|
| `"floating-tilt"` | `["default"]` | Default parallax effect with tilting motion for all hero text nodes. Used when no type is specified or an unknown type falls back. |
| `"grouped-float"` | — | All text nodes move as a group with uniform floating motion. |
| `"slanted-wrap"` | — | Diagonal motion pattern with wrapped text alignment. |
| `"none"` | `["static"]` | Disables all motion. Use when the page needs a completely static hero. |

If frontmatter specifies an unrecognized type (e.g., `type: banana`), the registry falls back to `"floating-tilt"`.

### Motion Fields Explained

Each style defines motion defaults for four **per-node** targets and four **global** controls:

**Per-node motion** (defined for `title`, `text`, `tagline`, `image`):

| Field | Type | Meaning |
|---|---|---|
| `x` | `number` | Horizontal pixel offset for parallax. Positive = moves right on mouse move. |
| `y` | `number` | Vertical pixel offset for parallax. Positive = moves down on mouse move. |
| `scale` | `number` | Zoom factor. `1.0` = no zoom, `1.03` = 3% zoom-in on hover. |

**Global motion controls**:

| Field | Type | Meaning |
|---|---|---|
| `intensity` | `number` | Multiplier for all motion values. `1.0` = full, `0.5` = half intensity. |
| `transitionDuration` | `number` | Duration (ms) for motion transitions. |
| `transitionDelayStep` | `number` | Stagger delay (ms) between each node's transition start. Creates a cascading effect. |
| `transitionEasing` | `string` | CSS easing function for transitions. |

### API Methods

```ts
import { heroTypographyRegistry } from "@utils/vitepress/api/frontmatter/hero";

// Register a single style with motion defaults
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

// Register multiple styles at once
heroTypographyRegistry.registerStyles([
  { type: "cinematic", motion: { /* ... */ } },
  { type: "minimal-slide", aliases: ["slide"], motion: { /* ... */ } },
]);

// Check if a style exists
heroTypographyRegistry.hasStyle("editorial-soft"); // true
heroTypographyRegistry.hasStyle("default");        // true (alias for floating-tilt)

// Resolve a type name to its canonical form (follows aliases, applies fallback)
heroTypographyRegistry.resolveStyleType("default");  // "floating-tilt"
heroTypographyRegistry.resolveStyleType("static");   // "none"
heroTypographyRegistry.resolveStyleType("banana");   // "floating-tilt" (fallback)

// Get a CLONED copy of motion defaults (safe to mutate)
const motion = heroTypographyRegistry.resolveMotionDefaults("floating-tilt");
motion.intensity = 0.5; // Safe — this is a deep clone, not the original

// List all registered style type names
heroTypographyRegistry.listStyleTypes(); // ["floating-tilt", "grouped-float", "slanted-wrap", "none", ...]
```

### Resolution Flow

1. Frontmatter `hero.typography.type` is read (e.g., `"default"`).
2. `resolveStyleType("default")` checks aliases → finds `"floating-tilt"`.
3. `resolveMotionDefaults("floating-tilt")` returns a **deep clone** of the style's motion config.
4. The runtime applies these values to hero text nodes for parallax rendering.
5. If the type is unknown at step 2, the registry silently falls back to `"floating-tilt"`.

> **Clone safety**: `resolveMotionDefaults` always returns a new object. Components can freely mutate the returned motion config without affecting the registry's stored defaults.

---

## How FloatingElementRegistry Works

**Source file**: `.vitepress/utils/vitepress/api/frontmatter/hero/FloatingElementRegistryApi.ts`
**Singleton import**: `import { floatingElementRegistry } from "@utils/vitepress/api/frontmatter/hero";`

The `FloatingElementRegistryApi` class manages floating element types — the decorative items (badges, icons, images, code snippets, etc.) that can appear around the hero area. Each type definition controls how the element is rendered.

### Built-in Types

| Type | Description |
|---|---|
| `"text"` | Plain text element. Default fallback when an unknown type is used. |
| `"card"` | Card-style container with optional shadow and border. |
| `"image"` | Image element for hero decorations. |
| `"lottie"` | Lottie animation player. |
| `"badge"` | Small label-style element (tags, keywords). |
| `"icon"` | Icon element using the project's icon system. |
| `"stat"` | Statistic display (number + label). |
| `"code"` | Code snippet block. |
| `"shape"` | Geometric SVG shape. |

If frontmatter specifies an unrecognized type, the registry falls back to `"text"`.

### Type Definition Shape

```ts
interface FloatingElementTypeDefinition {
  type: string;         // Canonical type name (lowercase)
  renderAs?: string;    // Map to another type's renderer (e.g., renderAs: "badge" uses badge rendering)
  component?: Component; // Vue component for fully custom rendering
  className?: string;   // Additional CSS class applied to the floating wrapper
}
```

- **`renderAs`**: Reuses another type's renderer. Example: `{ type: "keyword-chip", renderAs: "badge" }` — renders keyword-chip items using the badge renderer.
- **`component`**: Overrides rendering entirely with a custom Vue component. Use this when `renderAs` is insufficient.
- **`className`**: Injects a CSS class for type-specific styling without needing a custom renderer.

### API Methods

```ts
import { floatingElementRegistry } from "@utils/vitepress/api/frontmatter/hero";

// Register a single type
floatingElementRegistry.registerType({
  type: "keyword-chip",
  renderAs: "badge",
  className: "floating-keyword-chip",
});

// Register multiple types at once
floatingElementRegistry.registerTypes([
  { type: "author-avatar", renderAs: "image", className: "floating-avatar" },
  { type: "live-metric", component: LiveMetricWidget },
]);

// Resolve a type to its definition (returns fallback "text" definition if unknown)
const def = floatingElementRegistry.resolveType("keyword-chip");
// { type: "keyword-chip", renderAs: "badge", className: "floating-keyword-chip" }

const unknown = floatingElementRegistry.resolveType("banana");
// Returns the "text" type definition (fallback)

// List all registered type names
floatingElementRegistry.listRegisteredTypes();
// ["text", "card", "image", "lottie", "badge", "icon", "stat", "code", "shape", "keyword-chip", ...]
```

### Resolution Flow

1. Frontmatter `hero.floating.items[].type` is read (e.g., `"keyword-chip"`).
2. `resolveType("keyword-chip")` looks up the registry → returns its `FloatingElementTypeDefinition`.
3. If the type has `renderAs`, the renderer delegates to that type's visual logic.
4. If the type has `component`, the custom Vue component is mounted instead.
5. If the type is unknown, the registry silently falls back to the `"text"` definition.

---

## How the Shader Registry Works

**Source file**: `.vitepress/config/shaders/index.ts`
**Import path**: No alias exists for `.vitepress/config/`. Use relative imports from your file location (e.g., `'../../../../config/shaders'` from a component in the hero directory).

The shader registry manages WebGL shader presets for hero backgrounds. Each preset defines fragment shader code and configurable parameters (colors, speed, scale, etc.). Presets are referenced by name in frontmatter — the runtime resolves them at render time.

### Built-in Shader Presets

| Preset Name | Description |
|---|---|
| `water` | Animated water surface with wave distortion. |
| `noise` | Perlin/simplex noise pattern with animated evolution. |
| `galaxy` | Cosmic starfield with swirling nebula effects. |
| `plasma` | Colorful plasma waves with gradient blending. |
| `ripple` | Concentric ripple effects emanating from center. |
| `silk` | Smooth flowing silk/fabric animation. |

### API Functions

```ts
// NOTE: No @config alias resolves to .vitepress/config/.
// Use a relative import from your file's location.
import {
  listShaderTemplates,
  getShaderTemplate,
  getShaderTemplateByType,
  registerShaderTemplate,
} from "../../../../config/shaders";
import type { ShaderTemplate } from "../../../../config/shaders";

// List all registered shader preset names
const names = listShaderTemplates();
// ["water", "noise", "galaxy", "plasma", "ripple", "silk"]

// Get a shader template by name
const silk = getShaderTemplate("silk");

// Get a shader template by its type field
const waterShader = getShaderTemplateByType("water");

// Register a custom shader preset
registerShaderTemplate({
  name: "aurora",
  type: "aurora",
  // ... shader configuration (fragment code, uniforms, params)
});
```

### Frontmatter Usage

```yaml
hero:
  background:
    type: shader
    shader:
      preset: silk
```

The runtime reads `hero.background.shader.preset`, calls `getShaderTemplate("silk")` to load the shader configuration, and passes it to the WebGL renderer in `BackgroundLayer.vue`.

### Extension Steps

1. Create a new shader file (e.g., `.vitepress/config/shaders/aurora.ts`).
2. Reuse helpers from `.vitepress/config/shaders/templates/base-shader.ts` for common uniforms and setup.
3. Export the preset from `.vitepress/config/shaders/index.ts` via `registerShaderTemplate(...)`.
4. Reference the preset in frontmatter: `shader.preset: aurora`.
5. Test rendering in both light and dark themes — shader colors often need per-theme adjustments.

> **Import path warning**: The `@config` alias resolves to `.vitepress/utils/config/`, NOT `.vitepress/config/`. Shader files live at `.vitepress/config/shaders/`, so you must use relative paths. See the [Import Alias Reference](./extensionArchitecture#import-alias-reference) in the Extension Architecture doc for details.

---

## Related Pages

- [Framework Maintainability Guide](./frameworkMaintainability) — Theme sync standard, resize standard, and full extension API reference.
- [Extension Architecture](./extensionArchitecture) — File ownership rules, import alias reference, and layer placement guide.
- [Styles & Plugins Guide](./pluginsGuide) — All available Markdown plugins including shader-effect containers.
