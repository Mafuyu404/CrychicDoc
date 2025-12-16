---
title: Documentation Writing Standards
description: Official standards, workflows, and style guidelines required for participating in CrychicDoc documentation writing.
progress: 100
state: preliminary
priority: -10
hidden: false
---

# Documentation Writing Standards {#main}

::: alert {"type": "success", "title": "Notice", "border": "start"}
This document is the **first** standard you need to understand when participating in the CrychicDoc project. It details the collaboration process, content writing standards, sidebar configuration methods, and all available styles and components.
:::

## Contribution {#contribution}

### Specific Steps {#workflow-steps}

<LiteTree>
#workflow=color:white;background:#1976d2;padding:2px 6px;border-radius:3px;font-size:12px;
---
{#workflow}1. Fork & Clone
    Fork the main repository to your account, then clone it locally.
{#workflow}2. Sync and Create Branch
    Before making changes, sync with the main repository, then create a new branch for your modifications.
{#workflow}3. Modify and Commit
    Make changes on your new branch and commit with clear commit messages.
{#workflow}4. Create Pull Request
    Push your branch to your forked repository and create a Pull Request to the main repository.
</LiteTree>

::: alert {"type": "warning", "title": "Important Reminder"}
Please be sure to follow the **Conventional Commits** specification when writing commit messages, which helps automatically generate changelogs and version management.
:::

:::: stepper
@tab Initial Setup
```bash
# Clone your fork
git clone https://github.com/-%YourName/CrychicDoc.git
cd CrychicDoc

# Add upstream (main repository)
git remote add upstream https://github.com/PickAID/CrychicDoc.git
```

::: v-info
Necessary configuration steps when first participating in the project.
:::

@tab Start New Contribution
```bash
# Sync latest changes from main repository
git fetch upstream
git checkout main
git merge upstream/main

# Create a branch for your new feature or fix
git checkout -b -%branch
```

@tab Submit Your Changes
```bash
# Add your changes
git add .

# Commit changes (following Conventional Commits specification)
git commit -m "feat: add KubeJS event handling documentation"

# Push to your forked repository
git push origin -%branch
```

::: v-success
Commit message format: `type: brief description`
:::
::::

## Project Structure {#structure}

::: alert {"type": "info", "title": "Project Structure Overview"}
Below is the complete project structure of CrychicDoc, including explanations of key files and directories. Understanding the project structure helps you quickly locate files and understand code organization.
:::

<LiteTree>
// Define status and type styles
#config=color:white;background:#1976d2;padding:2px 6px;border-radius:3px;font-size:12px;
#content=color:white;background:#4caf50;padding:2px 6px;border-radius:3px;font-size:12px;
#script=color:white;background:#ff9800;padding:2px 6px;border-radius:3px;font-size:12px;
#ignore=color:#666;background:#f5f5f5;padding:2px 6px;border-radius:3px;font-size:12px;
.important=font-weight:bold;color:#d32f2f;
.folder=color:#1976d2;font-weight:500;
// Define icons
folder=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMjQgMjQiPjxwYXRoIGZpbGw9ImN1cnJlbnRDb2xvciIgZD0iTTEwIDRIOGEyIDIgMCAwIDAtMiAydjEyYTIgMiAwIDAgMCAyIDJoOGEyIDIgMCAwIDAgMi0yVjhhMiAyIDAgMCAwLTItMmgtM2wtMi0yWiIvPjwvc3ZnPg==
ts=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMTUgMTUiPjxwYXRoIGZpbGw9Im5vbmUiIHN0cm9rZT0iIzMxNzhDNiIgZD0iTTEyLjUgOHYtLjE2N2MwLS43MzYtLjU5Ny0xLjMzMy0xLjMzMy0xLjMzM0gxMGExLjUgMS41IDAgMSAwIDAgM2gxYTEuNSAxLjUgMCAwIDEgMCAzaC0xQTEuNSAxLjUgMCAwIDEgOC41IDExTTggNi41SDNtMi41IDBWMTNNMS41LjVoMTN2MTRIOS41eiIvPjwvc3ZnPg==
js=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMjQgMjQiPjxwYXRoIGZpbGw9IiNmN2RmMWUiIGQ9Ik0zIDNoMTh2MThIM1ptMTYuNTI1IDE0LjVjLS4zLS4zNTQtLjc5NS0uNjI5LTEuNzE3LS42MjljLS44ODEgMC0xLjQzOS4zMTgtMS40MzkuNzE4YzAgLjM5Ni4zNzMuNjM3IDEuMTU2Ljk2N2MxLjMzMi41ODYgMi4yODEgMS4wOTMgMi4yODEgMi4zOGMwIDEuMzItMS4yMDMgMi4xNDMtMi45NzQgMi4xNDNjLTEuMjEzIDAtMi4yNzEtLjQ2Mi0yLjk1LTEuMDc0bC44NzUtMS4yNzNjLjQzMy4zODkgMS4wNjQuNzI0IDEuNjY0LjcyNGMuNzA2IDAgMS4wNjQtLjMzMSAxLjA2NC0uNzMzYzAtLjQ0OS0uMzc2LS43MjQtMS4yNDUtMS4wMzNjLTEuMzI1LS40ODgtMi4xMzItMS4yNS0yLjEzMi0yLjM2M2MwLTEuMzk0IDEuMDI5LTIuMTQzIDIuODU2LTIuMTQzYzEuMDY0IDAgMS43NDUuMzI4IDIuMzc3Ljg1OWwtLjgzIDEuMjQxWm0tNS44NDUtLjMzNWMuMzY2LjgxNS4zNjYgMS41NzcuMzY2IDIuNDd2My45MDZoLTEuODc2VjE5LjZjMC0xLjUyNy0uMDYtMi4xOC0uNTUtMi40OGMtLjQxLS4yODgtMS4wNzYtLjI3NC0xLjYxOC0uMTA3Yy0uMzc4LjExNy0uNzEzLjMzNS0uNzEzIDEuMDc0djUuMDU2SDYuNDI3VjEyLjgyaDEuODc2djIuMTEzYy43NDctLjM5OSAxLjU3Ny0uNzM4IDIuNjQ1LS43MzhjLjc2NCAwIDEuNTc3LjI1MyAyLjA2OS43ODdjLjQ5OC41NTIuNjI2IDEuMTU3LjcyMyAxLjk5MVoiLz48L3N2Zz4=
md=data:image/svg+xml;charset=utf-8;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIzMiIgaGVpZ2h0PSIzMiIgdmlld0JveD0iMCAwIDI0IDI0Ij48IS0tIEljb24gZnJvbSBNYXRlcmlhbCBTeW1ib2xzIGJ5IEdvb2dsZSAtIGh0dHBzOi8vZ2l0aHViLmNvbS9nb29nbGUvbWF0ZXJpYWwtZGVzaWduLWljb25zL2Jsb2IvbWFzdGVyL0xJQ0VOU0UgLS0+PHBhdGggZmlsbD0iIzg4ODg4OCIgZD0iTTkgMThxLS44MjUgMC0xLjQxMi0uNTg3VDcgMTZWNHEwLS44MjUuNTg4LTEuNDEyVDkgMmg5cS44MjUgMCAxLjQxMy41ODhUMjAgNHYxMnEwIC44MjUtLjU4NyAxLjQxM1QxOCAxOHptLTQgNHEtLjgyNSAwLTEuNDEyLS41ODdUMyAyMFY2aDJ2MTRoMTF2MnptNS4yNS05aDEuNVY4LjVoMXYzaDEuNXYtM2gxVjEzaDEuNVY4cTAtLjQyNS0uMjg4LS43MTJUMTUuNzUgN2gtNC41cS0uNDI1IDAtLjcxMi4yODhUMTAuMjUgOHoiLz48L3N2Zz4=
json=data:image/svg+xml;charset=utf-8;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIzMiIgaGVpZ2h0PSIzMiIgdmlld0JveD0iMCAwIDI0IDI0Ij48IS0tIEljb24gZnJvbSBNYXRlcmlhbCBTeW1ib2xzIGJ5IEdvb2dsZSAtIGh0dHBzOi8vZ2l0aHViLmNvbS9nb29nbGUvbWF0ZXJpYWwtZGVzaWduLWljb25zL2Jsb2IvbWFzdGVyL0xJQ0VOU0UgLS0+PHBhdGggZmlsbD0iIzg4ODg4OCIgZD0iTTQuNzUgMTVINi41cS40MjUgMCAuNzEzLS4yODhUNy41IDE0VjlINnY0Ljc1SDVWMTIuNUgzLjc1VjE0cTAgLjQyNS4yODguNzEzVDQuNzUgMTVtNC40MjUgMGgxLjVxLjQyNSAwIC43MTMtLjI4OHQuMjg3LS43MTJ2LTEuNXEwLS40MjUtLjI4OC0uNzEydC0uNzEyLS4yODhoLTEuMjV2LTEuMjVoMXYuNWgxLjI1VjEwcTAtLjQyNS0uMjg4LS43MTJUMTAuNjc2IDloLTEuNXEtLjQyNSAwLS43MTIuMjg4VDguMTc1IDEwdjEuNXEwIC40MjUuMjg4LjcxM3QuNzEyLjI4N2gxLjI1djEuMjVoLTF2LS41aC0xLjI1VjE0cTAgLjQyNS4yODguNzEzdC43MTIuMjg3bTQuNC0xLjV2LTNoMXYzem0tLjI1IDEuNWgxLjVxLjQyNSAwIC43MTMtLjI4OHQuMjg3LS43MTJ2LTRxMC0uNDI1LS4yODctLjcxMlQxNC44MjUgOWgtMS41cS0uNDI1IDAtLjcxMi4yODh0LS4yODguNzEydjRxMCAuNDI1LjI4OC43MTN0LjcxMi4yODdtMy4xNzUgMGgxLjI1di0yLjYyNWwxIDIuNjI1SDIwVjloLTEuMjV2Mi42MjVMMTcuNzUgOUgxNi41ek0zIDIwcS0uODI1IDAtMS40MTItLjU4N1QxIDE4VjZxMC0uODI1LjU4OC0xLjQxMlQzIDRoMThxLjgyNSAwIDEuNDEzLjU4OFQyMyA2djEycTAgLjgyNS0uNTg3IDEuNDEzVDIxIDIweiIvPjwvc3ZnPg==
---
{.important}CrychicDoc                         // {.important}Main project
    [folder] .github                            // {#script}CI/CD scripts
        workflows                               // Automated build scripts
    [folder] .vitepress                         // {#config}VitePress configuration
        [folder] config                         // {.important}All project configurations
            [folder] lang                         // {.important}Multi-language configuration
            [folder] locale                         // {.important}Localization configuration
                [folder] langcode                // {.important}Language-specific configurations
                    [folder] componennts            // Component translation keys
                    [folder] snippets               // Homepage floating text translation keys
                    [ts] footer.ts                  //! Footer configuration
            [folder] sidebar                         // {.important}Sidebar configuration
            [ts] common-config.ts                      // VitePress configuration
            [json] contributors.json                      //! Contributors configuration
            [ts] markdown-plugins.ts                      // Markdown plugin configuration
            [ts] project-config.ts                       //! Main project configuration
        [folder] plugins                        // {.important}Custom plugins
        [folder] theme                          // {.important}Custom theme
            [folder] components                 // Vue components
            [folder] styles                     // CSS styles
        [ts] config.mts                         // {.important}VitePress configuration
        [ts] index.ts                           // {.important}Sidebar configuration
    [folder] .vscode                            // {#config}VS Code settings
        [md] snippets                           // Markdown code snippets
    [folder] docs                               // {#content}Content directory
        [folder] public                         // Static resources
        [folder] zh                             // {#content}Chinese content
            [md] Various files                        // Documentation files
        [folder] en                             // {#content}English content
            [md] Various files                        // Documentation files
    [md] README.md                              // {.important}Project description
    LICENSE                                     // {#config}CC BY-SA 4.0
    .gitignore                                  // {#config}Git ignore rules
</LiteTree>

## Writing Standards {#content}

**Core Guide Documents:**
- **[Styles and Plugins Guide](./pluginsGuide.md)** - Markdown extensions and custom components.
- **[Sidebar Configuration Practical Guide](./sidebarGuide.md)** - Configure and manage the sidebar.

**Auxiliary Tool Guides:**
- [LiteTree Component Usage Guide](./litetreeGuide.md) - Create elegant tree structures
- [VSCode Code Snippets Usage Guide](./vscodeSnippetsGuide.md) - Improve documentation writing efficiency.


### Frontmatter Configuration {#frontmatter}

Each Markdown file should include a `frontmatter` block to configure page metadata:

:::: chart-grid {"columns": 2, "gap": "24px"}

::: v-info Required Fields
- **`title`** (`string`) - Page title, displayed in sidebar
- **`priority`** (`number`) - Sidebar sorting, smaller numbers appear first
:::

::: v-success Optional Fields
- **`description`** (`string`) - Page description, used for SEO
- **`authors`** (`string[]`) - List of page authors
- **`progress`** (`number`) - Document completion progress (0-100)
- **`state`** (`string`) - Document status
- **`hidden`** (`boolean`) - Hide page
:::

::::

::: alert {"type": "info", "title": "Frontmatter Example"}
```yaml
---
title: KubeJS Event System
description: Deep dive into KubeJS event handling mechanisms
priority: 10
authors: ["Zhang San", "Li Si"]
progress: 85
state: preliminary
---
```
:::

### Titles and Anchors {#headings-anchors}

::: stepper
@tab Title Hierarchy
- Each document **must** have exactly one `H1` level title (`#`)
- Title hierarchy should increment progressively, no skipping levels
- Recommend using up to `H4` level maximum

@tab Anchor Settings
To generate clear URLs, please add custom anchors to all titles:
```markdown
### This is a Title {#a-clear-anchor}
```
:::

::::: chart-grid {"columns": 3, "gap": "20px"}

::: v-warning Respect Originality
Please do not arbitrarily modify or delete others' work on a large scale without communicating with the original author.
:::

::: v-info Active Communication
If you have any questions or suggestions, please communicate through GitHub Issues or the community.
:::

::: v-success Contributor Attribution
Third-party document authors need to submit at least one content modification for the system to correctly identify GitHub accounts.
:::

:::::