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
| `root` | `boolean` | Mark a directory's `index.md` as a new root node in the sidebar. |
| `title` | `string` | **Required**. Title displayed in the sidebar. |
| `priority`| `number` | **Required**. Sort weight, **smaller numbers appear first**. |
| `hidden` | `boolean` | If `true`, this page or directory will not appear in the sidebar. |
| `externalLinks` | `object[]`| Add links to external websites in `root` nodes. |

## Practical Tutorial: Configuring a New Documentation Area {#tutorial}

::::: stepper
@tab Task 1: Create Root Directory
Suppose you want to create a documentation area for a new Mod `AwesomeMod`.

#### Step 1: Create Directory Structure
Create a new directory `AwesomeMod` under `docs/zh/mods/` and create an `index.md` file in it.

#### Step 2: Declare as Root in `index.md`
Edit the frontmatter of `docs/zh/mods/AwesomeMod/index.md`:
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

#### Step 1: Create Subdirectory and `index.md`
Create a `features/` subdirectory under the `AwesomeMod` directory and move `features.md` into it. Then create an `index.md` file in the `features/` directory.

#### Step 2: Configure in Subdirectory `index.md`
Edit the frontmatter of `AwesomeMod/features/index.md` to specify a title and priority for it.
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

Add `externalLinks` in the frontmatter of the root directory `AwesomeMod/index.md`.

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