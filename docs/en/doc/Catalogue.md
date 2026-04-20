---
title: Documentation Writing Standards
description: Workflows, structural conventions, and style guidance for contributing documentation to CrychicDoc.
hidden: false
priority: -1000000
progress: 100
state: preliminary
---

# Documentation Writing Standards {#main}

::: alert {"type": "success", "title": "Notice", "border": "start"}
This page collects the collaboration flow, structural conventions, and writing boundaries for CrychicDoc documentation. Implementation details are covered in the topic pages listed below.
:::

## Related Pages

| Page | Notes |
| --- | --- |
| [Sidebar Configuration](./sidebarGuide) | Explains the sidebar generation rules and the fields that usually need to be edited when pages or directories change. |
| [Styles & Plugins](./pluginsGuide) | A guide to the Markdown extensions, containers, and custom components available in CrychicDoc. |
| [LiteTree Component](./litetreeGuide) | A guide to using LiteTree to build clear and information-dense tree structures. |
| [Extension Architecture](./extensionArchitecture) | Where configuration, runtime logic, components, plugins, and styles should live in CrychicDoc. |
| [Hero Extension Playbook](./heroExtension) | How to extend hero typography, floating items, shaders, background renderers, and nav/search visuals in CrychicDoc. |
| [Framework Maintainability Guide](./frameworkMaintainability) | Maintenance guidance for components, i18n, navigation layout, floating elements, and markdown plugins. |

## External References

| Link | Role |
| --- | --- |
| [VitePress Docs](https://vitepress.dev/) | Official framework documentation |

## Contribution {#contribution}

### Workflow {#workflow-steps}

<LiteTree>
#workflow=color:white;background:#1976d2;padding:2px 6px;border-radius:3px;font-size:12px;
---
{#workflow}1. Fork & Clone
    Fork the main repository to your own account, then clone it locally.
{#workflow}2. Sync and Create a Branch
    Before you start editing, sync with the upstream repository and create a branch for the change.
{#workflow}3. Edit and Commit
    Make the change on your branch and commit it with a clear message.
{#workflow}4. Open a Pull Request
    Push your branch to your fork and open a pull request back to the main repository.
</LiteTree>

::: alert {"type": "warning", "title": "Important Reminder"}
Please follow the **Conventional Commits** format for commit messages. It keeps changelogs and version tracking manageable.
:::

:::: stepper
@tab Initial Setup
```bash
# Clone your fork
git clone https://github.com/-%YourName/CrychicDoc.git
cd CrychicDoc

# Add upstream
git remote add upstream https://github.com/PickAID/CrychicDoc.git
```

::: v-info
These are the basic setup steps for a first contribution.
:::

@tab Start a New Contribution
```bash
# Sync with upstream
git fetch upstream
git checkout main
git merge upstream/main

# Create a branch for the new change
git checkout -b -%branch
```

@tab Submit Your Change
```bash
# Stage the change
git add .

# Commit the change
git commit -m "feat: add KubeJS event handling documentation"

# Push to your fork
git push origin -%branch
```

::: v-success
Commit message format: `type: brief description`
:::
::::

## Project Structure {#structure}

::: alert {"type": "info", "title": "Project Structure Overview"}
Below is the main CrychicDoc project structure with brief notes on what each area is for. Knowing where things live makes later edits much easier.
:::

<LiteTree>
#config=color:white;background:#1976d2;padding:2px 6px;border-radius:3px;font-size:12px;
#content=color:white;background:#4caf50;padding:2px 6px;border-radius:3px;font-size:12px;
#script=color:white;background:#ff9800;padding:2px 6px;border-radius:3px;font-size:12px;
#ignore=color:#666;background:#f5f5f5;padding:2px 6px;border-radius:3px;font-size:12px;
.important=font-weight:bold;color:#d32f2f;
.folder=color:#1976d2;font-weight:500;
folder=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMjQgMjQiPjxwYXRoIGZpbGw9ImN1cnJlbnRDb2xvciIgZD0iTTEwIDRIOGEyIDIgMCAwIDAtMiAydjEyYTIgMiAwIDAgMCAyIDJoOGEyIDIgMCAwIDAgMi0yVjhhMiAyIDAgMCAwLTItMmgtM2wtMi0yWiIvPjwvc3ZnPg==
ts=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMTUgMTUiPjxwYXRoIGZpbGw9Im5vbmUiIHN0cm9rZT0iIzMxNzhDNiIgZD0iTTEyLjUgOHYtLjE2N2MwLS43MzYtLjU59Ny0xLjMzMy0xLjMzMy0xLjMzM0gxMGExLjUgMS41IDAgMSAwIDAgM2gxYTEuNSAxLjUgMCAwIDEgMCAzaC0xQTEuNSAxLjUgMCAwIDEgOC41IDExTTggNi41SDNtMi41IDBWMTNNMS41LjVoMTN2MTRIOS41eiIvPjwvc3ZnPg==
js=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMjQgMjQiPjxwYXRoIGZpbGw9IiNmN2RmMWUiIGQ9Ik0zIDNoMTh2MThIM1ptMTYuNTI1IDE0LjVjLS4zLS4zNTQtLjc5NS0uNjI5LTEuNzE3LS42MjljLS44ODEgMC0xLjQzOS4zMTgtMS40MzkuNzE4YzAgLjM5Ni4zNzMuNjM3IDEuMTU2Ljk2N2MxLjMzMi41ODYgMi4yODEgMS4wOTMgMi4yODEgMi4zOGMwIDEuMzItMS4yMDMgMi4xNDMtMi45NzQgMi4xNDNjLTEuMjEzIDAtMi4yNzEtLjQ2Mi0yLjk1LTEuMDc0bC44NzUtMS4yNzNjLjQzMy4zODkgMS4wNjQuNzI0IDEuNjY0LjcyNGMuNzA2IDAgMS4wNjQtLjMzMSAxLjA2NC0uNzMzYzAtLjQ0OS0uMzc2LS43MjQtMS4yNDUtMS4wMzNjLTEuMzI1LS40ODgtMi4xMzItMS4yNS0yLjEzMi0yLjM2M2MwLTEuMzk0IDEuMDI5LTIuMTQzIDIuODU2LTIuMTQzYzEuMDY0IDAgMS43NDUuMzI4IDIuMzc3Ljg1OWwtLjgzIDEuMjQxWm0tNS44NDUtLjMzNWMuMzY2LjgxNS4zNjYgMS41NzcuMzY2IDIuNDd2My45MDZoLTEuODc2VjE5LjZjMC0xLjUyNy0uMDYtMi4xOC0uNTUtMi40OGMtLjQxLS4yODgtMS4wNzYtLjI3NC0xLjYxOC0uMTA3Yy0uMzc4LjExNy0uNzEzLjMzNS0uNzEzIDEuMDc0djUuMDU2SDYuNDI3VjEyLjgyaDEuODc2djIuMTEzYy43NDctLjM5OSAxLjU3Ny0uNzM4IDIuNjQ1LS43MzhjLjc2NCAwIDEuNTc3LjI1MyAyLjA2OS43ODdjLjQ5OC41NTIuNjI2IDEuMTU3LjcyMyAxLjk5MVoiLz48L3N2Zz4=
md=data:image/svg+xml;charset=utf-8;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIzMiIgaGVpZ2h0PSIzMiIgdmlld0JveD0iMCAwIDI0IDI0Ij48IS0tIEljb24gZnJvbSBNYXRlcmlhbCBTeW1ib2xzIGJ5IEdvb2dsZSAtIGh0dHBzOi8vZ2l0aHViLmNvbS9nb29nbGUvbWF0ZXJpYWwtZGVzaWduLWljb25zL2Jsb2IvbWFzdGVyL0xJQ0VOU0UgLS0+PHBhdGggZmlsbD0iIzg4ODg4OCIgZD0iTTkgMThxLS44MjUgMC0xLjQxMi0uNTg3VDcgMTZWNHEwLS44MjUuNTg4LTEuNDEyVDkgMmg5cS44MjUgMCAxLjQxMy41ODhUMjAgNHYxMnEwIC44MjUtLjU4NyAxLjQxM1QxOCAxOHptLTQgNHEtLjgyNSAwLTEuNDEyLS41ODdUMyAyMFY2aDJ2MTRoMTF2MnptNS4yNS05aDEuNVY4LjVoMXYzaDEuNXYtM2gxVjEzaDEuNVY4cTAtLjQyNS0uMjg4LS43MTJUMTUuNzUgN2gtNC41cS0uNDI1IDAtLjcxMi4yODhUMTAuMjUgOHoiLz48L3N2Zz4=
json=data:image/svg+xml;charset=utf-8;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIzMiIgaGVpZ2h0PSIzMiIgdmlld0JveD0iMCAwIDI0IDI0Ij48IS0tIEljb24gZnJvbSBNYXRlcmlhbCBTeW1ib2xzIGJ5IEdvb2dsZSAtIGh0dHBzOi8vZ2l0aHViLmNvbS9nb29nbGUvbWF0ZXJpYWwtZGVzaWduLWljb25zL2Jsb2IvbWFzdGVyL0xJQ0VOU0UgLS0+PHBhdGggZmlsbD0iIzg4ODg4OCIgZD0iTTQuNzUgMTVINi41cS40MjUgMCAuNzEzLS4yODhUNy41IDE0VjlINnY0Ljc1SDVWMTIuNUgzLjc1VjE0cTAgLjQyNS4yODguNzEzVDQuNzUgMTVtNC40MjUgMGgxLjVxLjQyNSAwIC43MTMtLjI4OHQuMjg3LS43MTJ2LTEuNXEwLS40MjUtLjI4OC0uNzEydC0uNzEyLS4yODhoLTEuMjV2LTEuMjVoMXYuNWgxLjI1VjEwcTAtLjQyNS0uMjg4LS43MTJUMTAuNjc2IDloLTEuNXEtLjQyNSAwLS43MTIuMjg4VDguMTc1IDEwdjEuNXEwIC40MjUuMjg4LjcxM3QuNzEyLjI4N2gxLjI1djEuMjVoLTF2LS41aC0xLjI1VjE0cTAgLjQyNS4yODguNzEzdC43MTIuMjg3bTQuNC0xLjV2LTNoMXYzem0tLjI1IDEuNWgxLjVxLjQyNSAwIC43MTMtLjI4OHQuMjg3LS43MTJ2LTRxMC0uNDI1LS4yODctLjcxMlQxNC44MjUgOWgtMS41cS0uNDI1IDAtLjcxMi4yODh0LS4yODguNzEydjRxMCAuNDI1LjI4OC43MTN0LjcxMi4yODdtMy4xNzUgMGgxLjI1di0yLjYyNWwxIDIuNjI1SDIwVjloLTEuMjV2Mi42MjVMMTcuNzUgOUgxNi41ek0zIDIwcS0uODI1IDAtMS40MTItLjU4N1QxIDE4VjZxMC0uODI1LjU4OC0xLjQxMlQzIDRoMThxLjgyNSAwIDEuNDEzLjU4OFQyMyA2djEycTAgLjgyNS0uNTg3IDEuNDEzVDIxIDIweiIvPjwvc3ZnPg==
---
{.important}CrychicDoc                         // {.important}Main project
    [folder] .github                            // {#script}CI/CD scripts
        workflows                               // Automated build scripts
    [folder] .vitepress                         // {#config}VitePress configuration
        [folder] config                         // {.important}All project configuration
            [folder] lang                         // {.important}Locale configuration
            [folder] locale                         // {.important}Localization resources
                [folder] langcode                // {.important}Per-language data
                    [folder] componennts            // Component translation keys
                    [folder] snippets               // Home floating text translation keys
                    [ts] footer.ts                  //! Footer configuration
            [folder] sidebar                         // {.important}Sidebar configuration
            [ts] common-config.ts                      // Shared VitePress configuration
            [json] contributors.json                      //! Contributor configuration
            [ts] markdown-plugins.ts                      // Markdown plugin registration
            [ts] project-config.ts                       //! Main project configuration
        [folder] plugins                        // {.important}Custom plugins
        [folder] theme                          // {.important}Custom theme
            [folder] components                 // Vue components
            [folder] styles                     // CSS styles
        [ts] config.mts                         // {.important}VitePress entry configuration
        [ts] index.ts                           // {.important}Sidebar entry point
    [folder] .vscode                            // {#config}VS Code workspace settings
        [json] settings.json                    // Workspace editor settings
        [json] launch.json                      // Debug configuration
    [folder] docs                               // {#content}Content directory
        [folder] public                         // Static assets
        [folder] zh                             // {#content}Chinese content
            [md] Various files                  // Documentation files
        [folder] en                             // {#content}English content
            [md] Various files                  // Documentation files
    [md] README.md                              // {.important}Project overview
    LICENSE                                     // {#config}CC BY-SA 4.0
    .gitignore                                  // {#config}Git ignore rules
</LiteTree>

## Writing Conventions {#content}

**Core Guides:**
- **[Styles & Plugins Guide](./pluginsGuide.md)** - Markdown extensions and custom components.
- **[Sidebar Configuration](./sidebarGuide.md)** - Sidebar generation rules and directory-level field usage.

**Supporting Guides:**
- [LiteTree Component](./litetreeGuide.md) - Build clear tree-style structures in Markdown.

### Frontmatter Configuration {#frontmatter}

Each Markdown file should include a `frontmatter` block for page metadata:

:::: chart-grid {"columns": 2, "gap": "24px"}

::: v-info Required Fields
- **`title`** (`string`) - Page title shown in the sidebar
- **`priority`** (`number`) - Sidebar sort order; smaller values appear first
:::

::: v-success Optional Fields
- **`description`** (`string`) - Page description, also used for SEO
- **`authors`** (`string[]`) - List of page authors
- **`progress`** (`number`) - Documentation progress (0-100)
- **`state`** (`string`) - Documentation status
- **`metadata`** (`boolean | string | object`) - Metadata layout control. Supports `doc`, `kubejs`, `modding`, `mod`, or `false`
- **`hidden`** (`boolean`) - Hide the page
- **`root`** (`boolean`) - Mark a directory `sidebarIndex.md` as a sidebar root
- **`collapsed`** (`boolean`) - Control whether a directory item starts collapsed
- **`maxDepth`** (`number`) - Control directory expansion depth
- **`externalLinks`** (`object[]`) - External links for directory-level nodes
- **`useChildrenCollapsed`** (`object`) - Control how child directory items are folded in the current generated tree
:::

::::

::: alert {"type": "info", "title": "Frontmatter Example"}
```yaml
---
title: KubeJS Event System
description: A closer look at the KubeJS event system
priority: 10
authors: ["Zhang San", "Li Si"]
progress: 85
state: preliminary
metadata: doc
---
```
:::

::: details Metadata Mode Examples
```yaml
---
metadata:
  mode: kubejs
  current:
    label: 1.20.1
    value: kubejs-2001.6.5-build.7
  requiredMods:
    - name: ProbeJS
      version: probejs-6.0.1
  routes:
    - server_scripts
---
```

```yaml
---
metadata:
  mode: modding
  current:
    label: 1.20.4
    value: NeoForge 20.4.x
  stack:
    - name: NeoGradle
      version: userdev 7.0.124
  routes:
    - datagen
    - registry
---
```

```yaml
---
metadata:
  mode: mod
  side: both
  latest: 1.21.x
  supported: [1.21.x, 1.20.1, 1.19.2, 1.18.2]
  loaders: [Forge, NeoForge]
  sources:
    curseforge: https://www.curseforge.com/minecraft/mc-mods/example
    modrinth: https://modrinth.com/mod/example
---
```
:::

When `metadata.mode: mod`, `side` can be `server`, `client`, or `both`.

Directory-level metadata usually lives in `sidebarIndex.md`. If a directory needs a clickable landing page, the system looks for `index.md -> Catalogue.md -> README.md` in that order.

### Headings and Anchors {#headings-anchors}

::: stepper
@tab Heading Levels
- Every document should have exactly one `H1` heading (`#`)
- Heading levels should increase step by step rather than skipping levels
- In most cases, `H4` is a practical upper limit

@tab Anchor Settings
Add explicit anchors to headings so generated URLs stay stable and readable:
```markdown
### This Is a Heading {#a-clear-anchor}
```
:::

::::: chart-grid {"columns": 3, "gap": "20px"}

::: v-warning Respect Original Work
Do not rewrite or remove large parts of someone else's work without talking to the original author first.
:::

::: v-info Communicate Early
If you have questions or suggestions, raise them through GitHub Issues or the relevant community channels.
:::

::: v-success Contributor Attribution
Third-party authors should submit at least one real content change so the system can match the correct GitHub account.
:::

:::::
