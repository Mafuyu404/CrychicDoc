# Fullscreen Dialog Plain Design

## Scope

This change only adjusts fullscreen dialog behavior in:

- `.vitepress/theme/components/content/MdDialog.vue`
- `.vitepress/theme/components/content/MdMultiPageDialog.vue`

Regular non-fullscreen dialogs should keep their current structure and behavior.

## Goals

- Remove the app-like fullscreen toolbar treatment.
- Make fullscreen dialogs read like plain documentation pages.
- Keep desktop fullscreen content at a constrained reading width.
- Make mobile fullscreen truly edge-to-edge with no visual gaps.
- Preserve close and multi-page navigation affordances.

## Agreed Design

### Single-page dialog

- Remove the fullscreen `v-toolbar`.
- Render a plain fullscreen header inside the document flow.
- Header contains:
  - title on the left
  - close button on the right
- Content remains inside a centered reading-width container.

### Multi-page dialog

- Remove the fullscreen `v-toolbar`.
- Reuse the same plain fullscreen header structure.
- Show a lightweight page meta / navigation row below the title.
- Keep the non-fullscreen navigation layout unchanged.

### Desktop fullscreen layout

- Fullscreen surface fills the viewport.
- Document content is centered and width-constrained for reading.
- Explicit `width` or `maxWidth` should control the inner reading container width in fullscreen mode.
- Component default dialog widths should not override fullscreen reading width unless the author explicitly passed them.
- Visual style should be neutral and plain, not branded like an app bar.

### Mobile fullscreen layout

- True edge-to-edge layout.
- No outer margin.
- No rounded corners.
- No artificial max-width gap.
- Padding should respect safe-area insets.

## Non-goals

- No redesign of non-fullscreen dialogs.
- No changes to dialog markdown syntax or plugin behavior.
- No broader Vuetify modal refactor.
