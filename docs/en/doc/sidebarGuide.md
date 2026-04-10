---
title: Sidebar Configuration
description: A task-driven practical guide that teaches you how to efficiently configure and manage CrychicDoc's sidebar.
priority: 20
hidden: false
---

# Sidebar Configuration Practical Guide {#main}

This guide will walk you through how to configure and manage CrychicDoc's sidebar through a series of common tasks.

## Core Concept: Frontmatter-Driven Configuration {#core-concept}

CrychicDoc's sidebar system **automatically generates** configuration by reading the `frontmatter` you set in Markdown files. You only need to declare metadata (like `title`, `priority`) in documents, then run a script, and the sidebar will automatically update.

::: v-info Tip
This automated process means you almost never need to manually manage complex configuration files.
:::

## Core Configuration Fields {#core-fields}

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

## Practical Tutorial: Configuring a New Documentation Area {#tutorial}

::::: stepper
@tab Task 1: Create Root Directory
Suppose you want to create a documentation area for a new Mod `AwesomeMod`.

#### Step 1: Create Directory Structure
Create a new directory `AwesomeMod` under `docs/zh/mods/`, then create:
- `sidebarIndex.md` for sidebar metadata.

#### Step 2: Declare as Root in `sidebarIndex.md`
Edit the frontmatter of `docs/zh/mods/AwesomeMod/sidebarIndex.md`:
```yaml
---
root: true
title: "AwesomeMod Guide"
priority: 100 # Assuming this is the 100th mod
---
```
@tab Task 2: Add Pages and Sort
Now, we add `getting-started.md` and `features.md` under the `AwesomeMod` directory. Use the `priority` field to define their order.

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
@tab Task 3: Create Subgroups
Organize `features.md` into a "Features" subgroup.

#### Step 1: Create Subdirectory and Sidebar Files
Create a `features/` subdirectory under the `AwesomeMod` directory and move `features.md` into it. Then create `sidebarIndex.md` inside `features/`.

#### Step 2: Configure in Subdirectory `sidebarIndex.md`
Edit the frontmatter of `AwesomeMod/features/sidebarIndex.md` to specify a title and priority for it.
```yaml
---
title: "Feature Details"
priority: 20
---
```
@tab Task 4: Update Sidebar
::: alert {"type": "warning", "title": "Important Step"}
After modifying any `frontmatter` (especially `priority`), you **must** run the following script to apply the changes.
:::
```bash
npm run docs:sidebar
```
This script will read all your `frontmatter` configurations and automatically regenerate the sidebar.
:::::

## Advanced Tips {#advanced-tips}

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

#### `useChildrenCollapsed` Field Contract

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

This keeps first-party sections free to use `Catalogue.md`, while mirrored third-party docs can rely on their existing `README.md` instead of carrying an extra `Catalogue.md`.
