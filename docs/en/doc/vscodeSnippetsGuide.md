---
title: VSCode Code Snippets
description: Documentation VSCode code snippets designed to improve documentation writing efficiency.
layout: doc
hidden: false
state: outdated
priority: 50
---

# VSCode Code Snippets Usage Guide

This guide explains how to use the optimized VSCode code snippets in the CrychicDoc project to write Markdown documentation more efficiently.

## Quick Start

When editing `.md` files in VSCode, type the snippet **prefix** (e.g., `@page-template`) and press `Tab` or `Enter` to expand it.

## Code Snippet Categories

All code snippets have been reorganized and standardized for easy finding and use. They are divided into the following categories:

-   **VitePress Frontmatter & Page Templates**: For quickly building pages and configuring metadata.
-   **VitePress Built-in Markdown Features**: For VitePress-specific containers and code blocks.
-   **Custom Plugins & Components**: For custom components developed in the project, such as dialogs, alerts, etc.
-   **Markdown General Tools**: For standard Markdown elements like headings and tables.
-   **LLM Content Tools**: For adding context or instructions visible only to language models.

---

## VitePress Frontmatter & Page Templates

Quickly generate page structures and configure Frontmatter metadata.

### Page & Section Templates

| Prefix                  | Description                                            |
| :---------------------- | :---------------------------------------------- |
| `@page-template`        | Quickly build a standard VitePress documentation page.         |
| `@root-template`        | Quickly build a new root section for the sidebar.                  |
| `@frontmatter`          | Insert a basic Frontmatter block (`---`).         |
| `@frontmatter-complete` | Generate a complete Frontmatter block with all common fields. |

### Individual Frontmatter Fields

All Frontmatter field code snippets now use the `@fm-` prefix for consistency.

| Prefix              | Description                                       | Output Example                        |
| :---------------- | :----------------------------------------- | :------------------------------ |
| `@fm-title`       | Page title.                                 | `title: Page Title`             |
| `@fm-description` | Page description.                                 | `description: Page description` |
| `@fm-root`        | Whether page is a sidebar root node.                   | `root: true`                    |
| `@fm-collapsed`   | Whether this sidebar section is collapsed by default.                 | `collapsed: true`               |
| `@fm-hidden`      | Hide this page from the sidebar.                     | `hidden: true`                  |
| `@fm-layout`      | Page layout (`doc`, `home`, `page`).         | `layout: doc`                   |
| `@fm-prevnext`    | Set previous/next page navigation links.                | `prev: false` `next: false`     |
| `@fm-authors`     | Page authors.                                 | `authors:\n  - author`          |
| `@fm-tags`        | Page tags.                                 | `tags:\n  - tag`                |
| `@fm-progress`    | Page completion progress.                             | `progress: 100`                 |
| `@fm-state`       | Document status (`preliminary`, `published`, etc.). | `state: preliminary`            |

---

## VitePress Built-in Markdown Features

Quickly use Markdown extensions provided by VitePress. All these code snippets now use the `@vp-` prefix.

### Tip Containers

| Prefix          | Description                       |
| :------------ | :------------------------- |
| `@vp-info`    | VitePress info container.       |
| `@vp-tip`     | VitePress tip container.       |
| `@vp-warning` | VitePress warning container.       |
| `@vp-danger`  | VitePress danger/error container.  |
| `@vp-details` | VitePress collapsible details container. |

### Code Features

| Prefix                | Description                                   |
| :------------------ | :------------------------------------- |
| `@vp-codegroup`     | VitePress code group for tab-style code blocks. |
| `@vp-codelines`     | Code block with line numbers.                       |
| `@vp-codehighlight` | Code block with specific line highlighting.                   |

---

## Custom Plugins & Components

Code snippets for custom Vue components and Markdown plugins developed for this project.

### Component Code Snippets

Complete set of code snippets for all interactive components in CrychicDoc.

#### Mermaid Diagrams

| Prefix                 | Description                   |
| :------------------- | :--------------------- |
| `@mermaid-flowchart` | Flowchart with decision nodes.   |
| `@mermaid-journey`   | User journey diagram for processes. |
| `@mermaid-sequence`  | Sequence diagram for interactions.     |
| `@mermaid-gantt`     | Gantt chart for project timelines. |
| `@mermaid-class`     | Class diagram for object relationships.       |
| `@mermaid-state`     | State diagram for state machines.       |

#### Timeline Plugin

| Prefix                 | Description                               |
| :------------------- | :--------------------------------- |
| `@timeline`          | Single timeline entry with date and event.     |
| `@timeline-multiple` | Multiple timeline entries for chronological content. |

#### Video Components

| Prefix        | Description                   |
| :---------- | :--------------------- |
| `@bilibili` | Bilibili video embed component.     |
| `@youtube`  | YouTube video embed component. |

#### Damage Chart Components

| Prefix                        | Description                                |
| :-------------------------- | :---------------------------------- |
| `@damage-chart-static`      | Static Minecraft damage chart for documentation. |
| `@damage-chart-interactive` | Interactive damage chart with user controls.        |
| `@damage-chart-full`        | Full-featured damage chart with all options.      |

#### Media & Documentation Components

| Prefix                     | Description                                 |
| :----------------------- | :----------------------------------- |
| `@pdf-viewer`            | PDF viewer component for document embedding.      |
| `@linkcard`              | Link card component for external links.             |
| `@linkcard-full`         | Full-featured link card with all attributes.       |
| `@contributors`          | GitHub contributors component.                  |
| `@contributors-advanced` | Advanced contributors component with custom title and language. |
| `@commits-counter`       | GitHub commits counter component.              |
| `@responsible-editor`    | Responsible editor component (uses frontmatter).    |
| `@comment`               | Comment section component (Giscus integration).           |

#### Enhanced Plugin Components

| Prefix                 | Description                               |
| :------------------- | :--------------------------------- |
| `@carousel-simple`   | Simple image carousel with default settings.       |
| `@carousel-advanced` | Advanced carousel with complete configuration options.       |
| `@stepper`           | Basic step guide component.                 |
| `@stepper-advanced`  | Advanced stepper with detailed content and code blocks. |
| `@iframe`            | Basic embedded iframe component.             |
| `@iframe-advanced`   | Advanced iframe with width and height configuration.    |

#### Demo Block Combinations

| Prefix             | Description                          |
| :--------------- | :---------------------------- |
| `@demo-mermaid`  | Demo block containing Mermaid diagram. |
| `@demo-timeline` | Demo block containing timeline component.    |
| `@demo-video`    | Demo block containing video component.      |
| `@demo-chart`    | Demo block containing damage chart component.  |

### Dialog Plugin

| Prefix              | Description                                                           |
| :---------------- | :------------------------------------------------------------- |
| `@dialog-def`     | Create a dialog **definition** block. Internal content is rendered as Markdown.     |
| `@dialog-trigger` | Create an inline **trigger** link for triggering dialogs.                 |
| `@dialog-full`    | Create a complete definition block and a trigger in demo block for quick testing. |

### Alert Plugin

The project supports traditional v-alert format and new CustomAlert with JSON configuration.

#### Traditional v-alert Format

| Prefix             | Description                                                            |
| :--------------- | :-------------------------------------------------------------- |
| `@alert`         | Generic alert container, type selectable via dropdown (`success`, `info`, etc.). |
| `@alert-success` | Success alert.                                                    |
| `@alert-info`    | Info alert.                                                    |
| `@alert-warning` | Warning alert.                                                    |
| `@alert-error`   | Error alert.                                                    |

#### CustomAlert with JSON Configuration

**Complete Alert Templates:**

| Prefix                     | Description                                 |
| :----------------------- | :----------------------------------- |
| `@custom-alert`          | Generic custom alert, type and title selectable. |
| `@custom-alert-success`  | Quick success alert with JSON configuration.       |
| `@custom-alert-info`     | Quick info alert with JSON configuration.       |
| `@custom-alert-warning`  | Quick warning alert with JSON configuration.       |
| `@custom-alert-error`    | Quick error alert with JSON configuration.       |
| `@custom-alert-advanced` | Alert with variant and density options.           |
| `@custom-alert-styled`   | Alert with border and color styling.           |
| `@custom-alert-themed`   | Alert with light/dark theme colors.        |
| `@custom-alert-icon`     | Alert with custom icon.               |
| `@custom-alert-full`     | Full-featured alert with all configuration options.     |
| `@custom-alert-minimal`  | Minimal alert with only type specified.             |

**Single Configuration Properties:**

| Prefix                         | Description                                                                |
| :--------------------------- | :------------------------------------------------------------------ |
| `@alert-config-type`         | Alert type property (`success`, `info`, `warning`, `error`).            |
| `@alert-config-title`        | Alert title property.                                                    |
| `@alert-config-text`         | Alert text content property.                                                |
| `@alert-config-variant`      | Alert variant property (`flat`, `elevated`, `tonal`, etc.).                   |
| `@alert-config-density`      | Alert density property (`default`, `comfortable`, `compact`).              |
| `@alert-config-border`       | Alert border property (`start`, `end`, `top`, `bottom`, `true`, `false`). |
| `@alert-config-color`        | Alert custom color property.                                              |
| `@alert-config-light-color`  | Alert light theme color property.                                            |
| `@alert-config-dark-color`   | Alert dark theme color property.                                            |
| `@alert-config-theme-colors` | Light and dark theme colors.                                                |
| `@alert-config-icon`         | Alert custom icon property.                                              |

### Other Components

| Prefix         | Description               |
| :----------- | :----------------- |
| `@carousel`  | Image carousel plugin.     |
| `@iframe`    | Embedded iframe plugin. |
| `@stepper`   | Step indicator component.   |
| `@file-tree` | File tree structure component.   |
| `@linkcard`  | Link card component.     |

---

## Markdown General Tools

Code snippets for standard Markdown syntax and text formatting.

| Prefix                       | Description                            |
| :------------------------- | :------------------------------ |
| `@h1`, `@h2`, `@h3`, `@h4` | Level 1 to 4 headings.                 |
| `@table`                   | Insert a 2x2 Markdown table. |
| `@toc`                     | Insert a table of contents with anchor links.      |
| `@demo`                    | Demo block container for showing examples.    |
| `@spoiler`                 | Hidden/spoiler text (`!!text!!`).    |
| `@mark`                    | Highlight (mark) text (`==text==`). |
| `@insert`                  | Inserted text (`++text++`).       |
| `@sub`                     | Subscript text.                      |
| `@sup`                     | Superscript text.                      |

---

## LLM Content Tools

Code snippets for adding context or instructions visible only to language models.

| Prefix                | Description                                  |
| :------------------ | :------------------------------------ |
| `@llm-only`         | Content block visible only to LLM.                 |
| `@llm-exclude`      | Content block invisible to LLM (excluded from context). |
| `@llm-instructions` | AI assistant instruction block.                       |
| `@llm-context`      | Provide context information for LLM.               |