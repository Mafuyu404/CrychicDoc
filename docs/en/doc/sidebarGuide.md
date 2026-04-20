---
title: Sidebar Configuration
description: Explains how CrychicDoc's sidebar is generated and which fields you usually need to edit when pages or directories change.
hidden: false
priority: 10
---

# Sidebar Configuration {#main}

CrychicDoc builds its sidebar from Markdown files and directory-level `frontmatter`. When you add a section, reorder pages, or change expansion behavior, the relevant edits usually belong in the page itself or the matching `sidebarIndex.md`.

## Generation Rules {#core-concept}

CrychicDoc's sidebar is generated automatically from Markdown files and directory metadata. In most cases, the only things you need to maintain are page titles, ordering, directory-level metadata, and a few fields that control how the current tree expands.

::: v-info Tip
Most of the time you do not need to hand-maintain a separate sidebar tree. What matters is whether the page metadata is correct and whether the directory-level conventions are still intact.
:::

## Common Fields {#core-fields}

To effectively manage the sidebar, you need to understand the following key `frontmatter` fields.

| Field | Type | Description |
|:---|:---|:---|
| `root` | `boolean` | Mark a directory's `sidebarIndex.md` as a new root node in the sidebar. |
| `title` | `string` | **Required**. Title displayed in the sidebar. |
| `priority`| `number` | **Required**. Sort weight, **smaller numbers appear first**. |
| `maxDepth` | `number` | Controls how deep the current directory view expands downward. |
| `hidden` | `boolean` | If `true`, this page or directory will not appear in the sidebar. |
| `externalLinks` | `object[]`| Add links to external websites in `root` nodes. |
| `useChildrenCollapsed` | `object` | Controls only how child directory items appear in the current generated tree, without rewriting child frontmatter. |

## New Documentation Areas {#tutorial}

::::: stepper
@tab Create a Root Directory
Suppose you want to create a documentation area for a new mod called `AwesomeMod`.

#### Start with the directory structure
Create a new directory `AwesomeMod` under `docs/en/mods/`, then create:
- `sidebarIndex.md` for sidebar metadata.

#### Then declare it as a root in `sidebarIndex.md`
Edit the frontmatter of `docs/en/mods/AwesomeMod/sidebarIndex.md`:
```yaml
---
root: true
title: "AwesomeMod Guide"
priority: 100 # Assuming this is the 100th mod
---
```
@tab Add Pages and Sort Them
Add `getting-started.md` and `features.md` under `AwesomeMod`, then use `priority` to decide their order.

**`getting-started.md`:**
```yaml
---
title: "Quick Start"
priority: 1
---
```
**`features.md`:**
```yaml
---
title: "Feature Introduction"
priority: 10
---
```
@tab Create a Subgroup
Move `features.md` into a dedicated "Features" subgroup.

#### First create the subdirectory and sidebar file
Create a `features/` subdirectory under the `AwesomeMod` directory and move `features.md` into it. Then create `sidebarIndex.md` inside `features/`.

#### Then configure the subdirectory `sidebarIndex.md`
Edit the frontmatter of `AwesomeMod/features/sidebarIndex.md` to specify a title and priority for it.
```yaml
---
title: "Feature Details"
priority: 20
---
```
@tab Check the Result
::: alert {"type": "warning", "title": "Important Step"}
The sidebar is generated during development and build. You do not need a separate sidebar script anymore.
:::
```bash
yarn docs:dev
# or
yarn docs:build
```
In day-to-day authoring, saving the file is usually enough. For a full verification pass, run `yarn docs:build`.
:::::

## Other Common Cases {#advanced-tips}

### Adding External Links {#tip-external-links}

Add `externalLinks` in the frontmatter of the root directory `AwesomeMod/sidebarIndex.md`.

```yaml
---
root: true
title: "AwesomeMod Guide"
# ... other configurations ...
externalLinks:
  - text: "Official Wiki"
    link: "https://awesomemod.com/wiki"
    priority: -999 # Use negative numbers to make links appear first
  - text: "GitHub Repository"
    link: "https://github.com/user/awesomemod"
    priority: -1000
---
```


### Using `useChildrenCollapsed` for Current-Tree Folding {#tip-view-control}

If your real goal is just to decide how child directory items appear in the current sidebar tree, use `useChildrenCollapsed`.

#### The `useChildrenCollapsed` Field

| Field path | Type | Default behavior | Effect |
|:---|:---|:---|:---|
| `useChildrenCollapsed` | `object` | omitted means "keep each child item's own `collapsed` value" | Current-tree child folding rule. |
| `useChildrenCollapsed.mode` | `"children" \| "self" \| "collapsed" \| "open"` | `children` | Keep each child as-is, follow the current directory, force collapsed, or force open. |
| `useChildrenCollapsed.depth` | `number` | `1` | Affects how many descendant levels inherit the rule. |

This only affects **how directory items appear in the current generated sidebar tree**:

- it does not rewrite a child directory's own `collapsed`;
- it does not rewrite a child directory's own `maxDepth`;
- it does not rewrite child document frontmatter;
- it does not take over traversal ownership.

Minimal example:

```yaml
---
root: true
title: "Modpack"
useChildrenCollapsed:
  mode: collapsed
  depth: 1
---
```

This keeps the authoring model simple: direct child directory items in this tree start collapsed.

If you want descendants to follow the current directory's own `collapsed` value:

```yaml
---
root: true
collapsed: true
useChildrenCollapsed:
  mode: self
  depth: 2
---
```

That makes children and grandchildren in the current tree follow the current directory state.

### Directory Landing File Rules {#tip-directory-landing}

When a directory needs a clickable landing page, the system looks for these files in order:

- `index.md`
- `Catalogue.md`
- `README.md`

Directory-level metadata usually belongs in `sidebarIndex.md`.

This keeps first-party sections free to use `Catalogue.md`, while mirrored third-party docs can rely on their existing `README.md` instead of carrying an extra `Catalogue.md`.
