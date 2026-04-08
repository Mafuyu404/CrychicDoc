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
| `collapseControl` | `object` | Controls whether child directories / child roots in the current sidebar view start collapsed or expanded. |
| `viewControl` | `object` | Advanced only. Decides traversal ownership in rare nested-root cases. |

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

### Using `itemOrder` to Implicitly Set Priority {#tip-item-order}

::: v-info Tip
`itemOrder` is essentially an alternative way of writing `priority`. The index position of items in `itemOrder` will be read by the script and assigned to the corresponding item's `priority`.
:::

::: v-warning Warning: `priority` Takes Precedence
If a file is defined in `itemOrder` and also has a `priority` field in **its own** frontmatter, then **the file's own `priority` field will win**. Therefore, we recommend directly using `priority` in files for explicit sorting. !!When there are many files, using itemOrder becomes a bit messy!!
:::

### Using `collapseControl` for Root-Level Folding {#tip-view-control}

If your real goal is just to decide which child directories or child roots start collapsed in the current sidebar view, prefer `collapseControl`. This is now the recommended day-to-day config.

#### `collapseControl` Field Contract

| Field path | Type | Default behavior | Effect |
|:---|:---|:---|:---|
| `collapseControl` | `object` | omitted means "keep each child item's own `collapsed` value" | Collapse control block for the current sidebar view. |
| `collapseControl.default` | `boolean` | omitted | Default collapsed state for child directory items in the current view. |
| `collapseControl.paths` | `Record<string, boolean>` | empty object | Per-path collapsed overrides relative to the current sidebar view root. |

This only affects **how directory items appear in the current generated sidebar**:

- it does not rewrite a child root's own `collapsed`;
- it does not rewrite a child root's own `maxDepth`;
- it does not rewrite child document frontmatter.

Minimal example:

```yaml
---
root: true
title: "Modpack"
collapsed: false
collapseControl:
  default: true
---
```

This keeps the current root open, while child directory items inside that view start collapsed.

If you need a few exceptions:

```yaml
---
root: true
title: "Modpack"
collapseControl:
  default: true
  paths:
    "kubejs/1.20.1": false
    "kubejs/1.21": false
---
```

Paths are always written **relative to the current sidebar view root**, not relative to the site root and not relative to the current markdown file.

#### `viewControl` Is Now Advanced Only

`viewControl` still exists, but it is now only about which root acts as the traversal controller during the current generation pass. It should not be your primary tool for everyday folding, and it should not be treated as a way to force one shared `maxDepth` onto every child root.

Most roots only need:

```yaml
---
root: true
hidden: false
collapsed: false
maxDepth: 0
---
```

### Directory Landing File Rules {#tip-directory-landing}

When a directory needs a clickable landing page, the system looks for these files in order:

- `index.md`
- `Catalogue.md`
- `Description.md`
- `README.md`

This keeps first-party sections free to use `Catalogue.md`, while mirrored third-party docs can rely on their existing `README.md` instead of carrying an extra `Catalogue.md`.

#### Advanced Override: Force a Child to Stay With or Escape the Parent Controller

Only if you are actively using nested-root ownership, use these advanced overrides:

```yaml
---
title: "1.20.1"
viewControl:
  controlledByParent: false
---
```

```yaml
---
title: "API"
viewControl:
  controlledByParent: true
---
```
