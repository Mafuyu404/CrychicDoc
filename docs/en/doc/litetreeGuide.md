---
title: LiteTree Component
description: A complete guide to using LiteTree to create elegant and informative tree structures in VitePress.
layout: doc
priority: 40
hidden: false
---

# LiteTree Component Usage Guide {#main}

::: v-info
[LiteTree](https://zhangfisher.github.io/lite-tree/) is a lightweight tree structure component designed for VitePress. It uses a YAML-like, indentation-based syntax that makes writing and maintaining it exceptionally simple and intuitive in Markdown documents.
:::

## Core Features {#core-features}

<LiteTree>
#feature=color:white;background:#1976d2;padding:2px 6px;border-radius:3px;font-size:12px;
---
{#feature}Lightweight
    No third-party dependencies, small size.
{#feature}Markdown Friendly
    Uses indentation-based `lite` format, perfectly integrated into Markdown.
{#feature}Highly Customizable
    Supports custom node styles, tags, comments, and icons.
{#feature}Powerful Variable System
    Supports defining and reusing styles and icons.
{#feature}Built-in Markers
    Provides a set of preset markers to represent different states.
</LiteTree>

## Using Custom Icons {#using-custom-icons}

A major feature of `LiteTree` is support for custom icons, but this requires converting SVG images to Base64-encoded Data URIs.

::::: stepper
@tab Step 1: Get SVG Icons
First, you need an SVG image. You can use tools like [Figma](https://www.figma.com/), [Iconify](https://iconify.design/), or [Material Design Icons](https://materialdesignicons.com/) to get or create SVG icons.

::: v-success Tip
Make sure your SVG code is concise and optimized to reduce file size.
:::
@tab Step 2: Convert to Base64 Format
There are many free online tools that can convert your SVG code to Base64. We recommend using [SVG to Base64 Converter](https://uutool.cn/svg-base64/) because it's very simple and straightforward.

1.  **Paste SVG Code**: Paste your complete SVG code (including `<svg>` tags) into the website's input box.
2.  **Copy Base64 URI**: Copy the generated complete URI in `data:image/svg+xml;base64,...` format.
@tab Step 3: Apply in LiteTree
Use the copied URI as the value for an icon variable:
```markdown
<LiteTree>
// Define your custom icon
myIcon=data:image/svg+xml;base64,...
---
[myIcon] This is a node with a custom icon
</LiteTree>
```
:::::

## Basic Syntax {#basic-syntax}

### Creating Simple Tree Structures {#basic-tree}

Use indentation (4 spaces recommended) to represent hierarchical relationships.

::: demo Basic Tree Structure
<LiteTree>
Company Structure
    Administrative Center
        President's Office
        Human Resources Department
    Marketing Center
        Marketing Department
        Sales Department
</LiteTree>
:::

### Adding Status Markers {#tree-with-markers}

Use end-of-line comments `//` with specific symbols to add status markers to nodes.

::: demo Standard Markers
<LiteTree>
Project Status
    Completed Tasks      //v    Success marker
    New Features        //+    Add marker
    Deprecated Code     //-    Delete marker
    Found Errors        //x    Error marker
    Modified Files      //*    Modify marker
    Important Projects  //!    Important marker
</LiteTree>
:::

### Adding Tags {#tree-with-tags}

Use `(tag,tag,tag)` immediately after the node title to represent node tags, supporting multiple tags.

::: alert {"type": "info", "title": "Tag Syntax Rules"}
- Multiple tags are separated by `,`
- Tags can have `{...CSS styles...}` specified before them for embedded styling
- Tag content can also include `[iconName]`
:::

::: demo Basic Tags
<LiteTree>
Technology Stack
    Frontend Development
        Node.js Project(JavaScript,TypeScript,React)
        Vue.js Project(Vue,Vite,Pinia)
    Backend Development
        API Services({color:white;background-color:#ff9e9e;border:1px solid red}Spring,{color:white;background-color:#9e9eff;border:1px solid blue}Express,{color:white;background-color:#bfffbf;border:1px solid green}FastAPI)
</LiteTree>
:::

::: demo Tags with Icons
<LiteTree>
// Define icons
github=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMjQgMjQiPjxwYXRoIGZpbGw9ImN1cnJlbnRDb2xvciIgZD0iTTEyIDJBMTAgMTAgMCAwIDAgMiAxMmMwIDQuNDIgMi44NyA4LjE3IDYuODQgOS41Yy41LjA4LjY2LS4yMy42Ni0uNXYtMS42OWMtMi43Ny42LTMuMzYtMS4zNC0zLjM2LTEuMzRjLS40Ni0xLjE2LTEuMTEtMS40Ny0xLjExLTEuNDdjLS45MS0uNjIuMDctLjYxLjA3LS42MWMxIDAtMS41MyAxLjAzLTEuNTMgMS4wM2MuODcgMS41MiAyLjM0IDEuMDcgMi45MS44M2MuMDktLjY1LjM1LTEuMDkuNjMtMS4zNGMtMi4yMi0uMjUtNC41NS0xLjExLTQuNTUtNC45MmMwLTEuMTEuMzgtMiAxLjAzLTIuNzFjLS4xLS4yNS0uNDUtMS4yOS4xLTIuNjRjMCAwIC44NC0uMjcgMi43NSAxLjAyYy44MS0uMjMgMS42OC0uMzQgMi41NC0uMzVjLjg2LjAxIDEuNzMuMTIgMi41NC4zNWMxLjkxLTEuMjkgMi43NS0xLjAyIDIuNzUtMS4wMmMuNTUgMS4zNS4yIDIuMzkuMSAyLjY0Yy42NS43MSAxLjAzIDEuNiAxLjAzIDIuNzFjMCAzLjgyLTIuMzQgNC42Ni00LjU3IDQuOTFjLjM2LjMxLjY4LjkyLjY4IDEuODV2Mi43NGMwIC4yNy4xNi41OS42Ny41QzE5LjE0IDIwLjE2IDIyIDE2LjQyIDIyIDEyQTEwIDEwIDAgMCAwIDEyIDJaIi8+PC9zdmc+
gitee=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMjQgMjQiPjxwYXRoIGZpbGw9IiNkNzI4MjgiIGQ9Ik0xMiAyLjI1YzUuMzg0IDAgOS43NSA0LjM2NiA9Ljc1IDkuNzVzLTQuMzY2IDkuNzUtOS43NSA5Ljc1UzIuMjUgMTcuMzg0IDIuMjUgMTJTNi42MTYgMi4yNSAxMiAyLjI1WiIvPjxwYXRoIGZpbGw9IndoaXRlIiBkPSJNMTEuMzc4IDguNDc4SDcuNzIyYy0uMzMzIDAtLjYwMy4yNy0uNjAzLjYwM3YuOTE4YzAgLjMzMy4yNy42MDMuNjAzLjYwM2gzLjY1NnYuOTE4SDcuNzIyYy0uMzMzIDAtLjYwMy4yNy0uNjAzLjYwM3YuOTE4YzAgLjMzMy4yNy42MDMuNjAzLjYwM2gzLjY1NnYuOTE4SDcuNzIyYy0uMzMzIDAtLjYwMy4yNy0uNjAzLjYwM3YuOTE4YzAgLjMzMy4yNy42MDMuNjAzLjYwM2g4LjU1NmMuMzMzIDAgLjYwMy0uMjcuNjAzLS42MDNWOC40NzhjMC0uMzMzLS4yNy0uNjAzLS42MDMtLjYwM2gtNS41WiIvPjwvc3ZnPg==
---
Code Repository
    Project Management
        Main Repository([github]GitHub,Gitee)
        Mirror Repository([gitee]Backup Repository)
</LiteTree>
:::

### Adding Comments {#tree-with-comments}

Content after `//` represents node comments, displayed on the far right.

::: alert {"type": "warning", "title": "Comment Syntax Notes"}
- Comment content is displayed in gray by default
- Comment content can include `{...CSS styles...}` to specify embedded styling for comments
- Comment content can also include `[iconName]` and hyperlinks
- **Note**: There must be whitespace after `//`
- Comments are not displayed on mobile (`@media screen and (max-width: 480px)`)
:::

::: demo Basic Comments
<LiteTree>
Project Files
    Configuration Files
        package.json                // Project dependency configuration
        vite.config.js             // {color:white;background-color:#ff9e9e;border:1px solid red}Build configuration file
    Source Code
        main.js                     // Application entry file
        App.vue                     // {color:blue;font-weight:bold}Root component
</LiteTree>
:::

::: demo Comments with Icons and Links
<LiteTree>
// Define icons
star=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMjQgMjQiPjxwYXRoIGZpbGw9ImdvbGQiIGQ9Im0xMiAxNS40bC0zLjc2IDIuMjdhMS0xIDAgMCAxLTEuNTMtMS4xMUw3LjMgMTMuNGwtMy4yMy0uMjhhMS0xIDAgMCAxLS41Ni0xLjc1bDIuNjctMi4xMmwtLjY1LTMuMDlhMS0xIDAgMCAxIDEuNDgtMS4xM0wxMiA3LjRsNC4yOC0yLjQ3YTEgMSAwIDAgMSAxLjQ4IDEuMTNsLS42NSAzLjA5TDE5Ljc4IDExYTEgMSAwIDAgMS0uNTYgMS43NWwtMy4yMy4yOGMtLjUxIDIuMTYtLjYzIDMuNTUtMS43NiAyLjM3WiIvPjwvc3ZnPg==
yes=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMjQgMjQiPjxwYXRoIGZpbGw9ImdyZWVuIiBkPSJtMTAuNiAxNi4yIDcuODUtNy44NWMuMjMtLjIzLjIzLS42MSAwLS44NGwtLjg0LS44NGMtLjIzLS4yMy0uNjEtLjIzLS44NCAwTDEwLjE4IDE0LjJsLTMuNDktMy40OWMtLjIzLS4yMy0uNjEtLjIzLS44NCAwbC0uODQuODRjLS4yMy4yMy0uMjMuNjEgMCAuODRsNC43NiA0Ljc2Yy4yMy4yMy42MS4yMy44NCAwWiIvPjwvc3ZnPg==
---
Open Source Projects
    Documentation Sites
        VitePress                   // [star]Modern static site generator
        Docusaurus                  // [yes]Facebook open source documentation framework
    Frontend Tools
        Vite                        // Build tool details[Website:star](https://vitejs.dev/)
        Vue.js                      // [star]Progressive[yes]JavaScript framework
</LiteTree>
:::

## Variable System {#variable-system}

::: alert {"type": "info", "title": "Variable Definition Location"}
Variable definitions **must** be located at the top of the tree content and separated from the main tree content by a `---` separator.
:::

### Defining Style Variables (`#name=styles`) {#style-variables}

Used to define reusable CSS styles.

::: demo Style Variables
<LiteTree>
// Define style variables
#important=color:red;font-weight:bold;background:#ffe6e6;padding:2px 6px;border-radius:3px;
#success=color:green;font-weight:bold;background:#e6ffe6;padding:2px 6px;border-radius:3px;
---
Project Files
    {#important}Critical Files
    {#success}Completed Files
</LiteTree>
:::

### Defining Class Variables (`.name=styles`) {#class-variables}

Used to define CSS classes for unified styling.

::: demo Class Variables
<LiteTree>
// Define class variables
.folder=color:#1976d2;font-weight:500;
.file=color:#666;
---
{.folder}Source Code
    {.file}main.js
    {.file}config.json
</LiteTree>
:::

### Defining Icon Variables (`name=base64data`) {#icon-variables}

Use Base64-encoded SVG to define custom icons.

::: demo Icon Variables
<LiteTree>
// Define icon variables (see conversion guide above)
folder=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMjQgMjQiPjxwYXRoIGZpbGw9ImN1cnJlbnRDb2xvciIgZD0iTTEwIDRIOGEyIDIgMCAwIDAtMiAydjEyYTIgMiAwIDAgMCAyIDJoOGEyIDIgMCAwIDAgMi0yVjhhMiAyIDAgMCAwLTItMmgtM2wtMi0yWiIvPjwvc3ZnPg==
file=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMjQgMjQiPjxwYXRoIGZpbGw9ImN1cnJlbnRDb2xvciIgZD0iTTE0IDJINmEyIDIgMCAwIDAtMiAydjE2YTIgMiAwIDAgMCAyIDJoMTJhMiAyIDAgMCAwIDItMlY4bC02LTZtNCA5VjlsNCA0aC00WiIvPjwvc3ZnPg==
---
[folder] Frontend Project
    [folder] src
        [file] Header.vue
    [file] package.json
</LiteTree>
:::

## Complete Example {#full-example}

:::demo Complete Example
<LiteTree>
// Style definitions
#new=color:white;background:#4caf50;padding:1px 4px;border-radius:2px;font-size:12px;
#deprecated=color:white;background:#f44336;padding:1px 4px;border-radius:2px;font-size:12px;
.important=font-weight:bold;color:#1976d2;
// Icon definitions
vue=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMjQgMjQiPjxwYXRoIGZpbGw9IiM0Y2FmNTAiIGQ9Ik0yIDIwaDIwTDEyIDR6Ii8+PC9zdmc+
ts=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMTUgMTUiPjxwYXRoIGZpbGw9Im5vbmUiIHN0cm9rZT0iIzMxNzhDNiIgZD0iTTEyLjUgOHYtLjE2N2MwLS43MzYtLjU5Ny0xLjMzMy0xLjMzMy0xLjMzM0gxMGExLjUgMS41IDAgMSAwIDAgM2gxYTEuNSAxLjUgMCAwIDEgMCAzaC0xQTEuNSAxLjUgMCAwIDEgOC41IDExTTggNi41SDNtMi41IDBWMTNNMS41LjVoMTN2MTRIOS41eiIvPjwvc3ZnPg==
---
{.important}CrychicDoc Project
    .vitepress                    // {.important}Configuration directory
        config
            [ts] index.ts         // {#new}Updated configuration
        plugins                   // {.important}Custom plugins
            [ts] custom-alert.ts  // {#new}Alert plugin
        theme
            [vue] components      // {.important}Vue components
                [vue] CustomAlert.vue  // {#new}New component
    docs
        zh                        // Chinese documentation
            styleList.md          // {#deprecated}Needs update
        en                        // English documentation
            litetree-guide.md   // {#new}This guide
    package.json                  //v    Project configuration
    README.md                     //!    {.important}Important documentation
</LiteTree>
:::

## Inline Styles

### Direct Color Styles

Use `{color:value}` syntax to apply styles directly to text:

:::demo Inline Colors
<LiteTree>
Project Status
    {color:green}Completed Features
    {color:orange}In Progress
    {color:red}Critical Issues
    {color:blue;font-weight:bold}Important Notes
</LiteTree>
:::

### Mixed Styles

Combining variables, inline styles, icons, and markers:

:::demo Complete Example
<LiteTree>
// Style definitions
#new=color:white;background:#4caf50;padding:1px 4px;border-radius:2px;font-size:12px;
#deprecated=color:white;background:#f44336;padding:1px 4px;border-radius:2px;font-size:12px;
.important=font-weight:bold;color:#1976d2;
// Icon definitions
vue=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMjQgMjQiPjxwYXRoIGZpbGw9IiM0Y2FmNTAiIGQ9Ik0yIDIwaDIwTDEyIDR6Ii8+PC9zdmc+
ts=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMTUgMTUiPjxwYXRoIGZpbGw9Im5vbmUiIHN0cm9rZT0iIzMxNzhDNiIgZD0iTTEyLjUgOHYtLjE2N2MwLS43MzYtLjU5Ny0xLjMzMy0xLjMzMy0xLjMzM0gxMGExLjUgMS41IDAgMSAwIDAgM2gxYTEuNSAxLjUgMCAwIDEgMCAzaC0xQTEuNSAxLjUgMCAwIDEgOC41IDExTTggNi41SDNtMi41IDBWMTNNMS41LjVoMTN2MTRIOS41eiIvPjwvc3ZnPg==
---
{.important}CrychicDoc Project
    .vitepress                    // {.important}Configuration directory
        config
            [ts] index.ts         // {#new}Updated configuration
        plugins                   // {.important}Custom plugins
            [ts] custom-alert.ts  // {#new}Alert plugin
        theme
            [vue] components      // {.important}Vue components
                [vue] CustomAlert.vue  // {#new}New component
    docs
        zh                        // Chinese documentation
            styleList.md          // {#deprecated}Needs update
        en                        // English documentation
            litetree-guide.md   // {#new}This guide
    package.json                  //v    Project configuration
    README.md                     //!    {.important}Important documentation
</LiteTree>
:::

## Advanced Features {#advanced-features}

### Combined Use of Tags and Comments {#tags-and-comments-combined}

Tags and comments can be used simultaneously to create richer node information.

::: demo Tags and Comments Combined
<LiteTree>
// Define icons and styles
#priority=color:white;background:#e91e63;padding:1px 4px;border-radius:2px;font-size:11px;
#status=color:white;background:#4caf50;padding:1px 4px;border-radius:2px;font-size:11px;
star=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMjQgMjQiPjxwYXRoIGZpbGw9ImdvbGQiIGQ9Im0xMiAxNS40bC0zLjc2IDIuMjdhMS0xIDAgMCAxLTEuNTMtMS4xMUw3LjMgMTMuNGwtMy4yMy0uMjhhMS0xIDAgMCAxLS41Ni0xLjc1bDIuNjctMi4xMmwtLjY1LTMuMDlhMS0xIDAgMCAxIDEuNDgtMS4xM0wxMiA3LjRsNC4yOC0yLjQ3YTEgMSAwIDAgMSAxLjQ4IDEuMTNsLS42NSAzLjA5TDE5Ljc4IDExYTEgMSAwIDAgMS0uNTYgMS43NWwtMy4yMy4yOGMtLjUxIDIuMTYtLjYzIDMuNTUtMS43NiAyLjM3WiIvPjwvc3ZnPg==
---
Product Development Plan
    Core Features(Vue.js,TypeScript,{#priority}High)    // [star]Framework core - {#status}In development
    User Interface(CSS,Vuetify,{#priority}Medium)       // UI component library - {#status}Design phase
    API Interface(Node.js,Express)                      // Backend service - To be started
</LiteTree>
:::

### Deep Nesting and Complex Structures {#complex-nested-structures}

LiteTree supports arbitrary depth nesting, suitable for representing complex hierarchical structures.

::: demo Complex Nested Structure
<LiteTree>
// Define various styles and icons
#module=color:white;background:#2196f3;padding:2px 6px;border-radius:3px;font-size:12px;
#component=color:white;background:#ff9800;padding:2px 6px;border-radius:3px;font-size:12px;
#utility=color:#666;background:#f5f5f5;padding:2px 6px;border-radius:3px;font-size:12px;
.folder=color:#1976d2;font-weight:500;
.file=color:#666;
---
{.folder}Large Frontend Project Architecture                         // {#module}Micro-frontend architecture
    {.folder}Infrastructure Layer                          //! Core infrastructure
        {.folder}Build Tools                        // Development toolchain
            webpack.config.js                   // Main configuration file
            babel.config.js                     // JS transpilation configuration
            {.folder}Plugin Extensions                     // Custom plugins
                custom-loader.js                // {#utility}Custom loader
                optimize-plugin.js              // {#utility}Optimization plugin
        {.folder}Development Server                       // Local development environment
            dev-server.js                       // Development server configuration
            proxy.config.js                     // Proxy configuration
    {.folder}Application Layer                              // {#module}Business logic layer
        {.folder}Page Components                         // Page-level components
            {.folder}User Management                     // User-related pages
                UserList.vue                    // {#component}User list
                UserDetail.vue                  // {#component}User details
                UserForm.vue                    // {#component}User form
            {.folder}Order Management                     // Order-related pages
                OrderList.vue                   // {#component}Order list
                OrderTracking.vue               // {#component}Order tracking
        {.folder}Common Components                         // Reusable components
            Button.vue                          // {#component}Button component
            Modal.vue                           // {#component}Modal component
            DataTable.vue                       // {#component}Data table
    {.folder}Service Layer                              // {#module}Data service layer
        {.folder}API Services                         // External interfaces
            user.service.js                     // User API service
            order.service.js                    // Order API service
        {.folder}State Management                         // Application state
            store.js                            // Vuex/Pinia storage
            modules                             // State modules
</LiteTree>
:::

## Common Use Cases

### Project File Structure

:::demo Project Structure
<LiteTree>
// File type styles
.folder=color:#1976d2;font-weight:500;
.file=color:#666;
.config=color:#f57c00;font-weight:500;
.doc=color:#8bc34a;
// Status styles
#completed=color:green;background:#e6ffe6;padding:1px 3px;border-radius:2px;font-size:11px;
#inprogress=color:orange;background:#fff3e0;padding:1px 3px;border-radius:2px;font-size:11px;
#todo=color:red;background:#ffe6e6;padding:1px 3px;border-radius:2px;font-size:11px;
---
{.folder}My Project
    {.folder}src                  //v    {#completed}Structure complete
        {.folder}components       //+    {#inprogress}Adding components
            {.file}Header.vue     //v    {#completed}Completed
            {.file}Footer.vue     //+    {#todo}To do
        {.folder}pages
            {.file}Home.vue       //v    {#completed}Completed
            {.file}About.vue      //*    {#inprogress}Updating
    {.config}package.json         //v    {#completed}Configured
    {.config}vite.config.js       //*    {#inprogress}Optimizing
    {.doc}README.md               //!    {#todo}Documentation needed
</LiteTree>
:::

### Team Organization

:::demo Team Structure
<LiteTree>
// Team role styles
#lead=color:white;background:#1976d2;padding:2px 6px;border-radius:3px;font-size:12px;
#senior=color:#1976d2;background:#e3f2fd;padding:2px 6px;border-radius:3px;font-size:12px;
#junior=color:#666;background:#f5f5f5;padding:2px 6px;border-radius:3px;font-size:12px;
---
Development Team
    Frontend Team                      // {#lead}Team Lead: Zhang San
        React Developers              //+    Team expansion
            Li Si                  // {#senior}Senior Developer
            Wang Wu                  // {#junior}Junior Developer
        Vue Developers                //v    Complete team
            Zhao Liu                  // {#senior}Senior Developer
            Qian Qi                  // {#junior}Junior Developer
    Backend Team                      // {#lead}Team Lead: Sun Ba
        API Development                  //*    Refactoring
            Zhou Jiu                  // {#senior}Senior Developer
            Wu Shi                  // {#junior}Junior Developer
        Database Team                //!    Critical project
            Zheng Yi                  // {#senior}Senior Developer
</LiteTree>
:::

## VSCode Code Snippets {#vscode-snippets}

The project includes complete VSCode code snippets for LiteTree to help you quickly create various tree structures.

### Basic Snippets {#basic-snippets}

::: alert {"type": "info", "title": "Usage"}
In Markdown files, type the snippet prefix (like `@file-tree`) and press `Tab` to insert the corresponding code template.
:::

| Snippet Prefix | Description | Purpose |
|:---|:---|:---|
| `@file-tree` | Basic tree structure | Create simple file directory trees |
| `@file-tree-advanced` | Advanced tree structure | Complete tree with variable definitions and styles |
| `@lite-tree-with-tags` | Tree with tags | Tree structure showing tag functionality |
| `@lite-tree-with-comments` | Tree with comments | Tree structure showing comment functionality |

### Variable Definition Snippets {#variable-snippets}

| Snippet Prefix | Description | Generated Content |
|:---|:---|:---|
| `@lite-style-var` | Style variable definition | `#name=color:value;background:value;` |
| `@lite-class-var` | Class variable definition | `.name=color:value;font-weight:value;` |
| `@lite-icon-var` | Icon variable definition | `name=data:image/svg+xml;base64,...` |

### Preset Icon Snippets {#icon-snippets}

| Snippet Prefix | Icon Type | Base64 Encoding |
|:---|:---|:---|
| `@icon-folder` | Folder icon | Blue folder SVG |
| `@icon-file` | File icon | Generic file SVG |
| `@icon-js` | JavaScript icon | JS file type icon |
| `@icon-ts` | TypeScript icon | TS file type icon |
| `@icon-vue` | Vue.js icon | Vue component icon |
| `@icon-github` | GitHub icon | GitHub brand icon |
| `@icon-star` | Star icon | Gold star icon |

### Preset Style Snippets {#preset-style-snippets}

| Snippet Prefix | Description | Included Styles |
|:---|:---|:---|
| `@lite-status-styles` | Status style set | Success, warning, error, info status styles |
| `@lite-filetype-styles` | File type styles | Folder, file, configuration file styles |
| `@lite-priority-styles` | Priority styles | High, medium, low priority tag styles |

### Complete Example Snippets {#example-snippets}

::: demo Tree Structure Created with Code Snippets
<LiteTree>
// Generated by @lite-status-styles
#success=color:white;background:#4caf50;padding:2px 6px;border-radius:3px;font-size:12px;
#warning=color:white;background:#ff9800;padding:2px 6px;border-radius:3px;font-size:12px;
#error=color:white;background:#f44336;padding:2px 6px;border-radius:3px;font-size:12px;
// Generated by @icon-folder etc.
folder=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxZW0iIGhlaWdodD0iMWVtIiB2aWV3Qm94PSIwIDAgMjQgMjQiPjxwYXRoIGZpbGw9ImN1cnJlbnRDb2xvciIgZD0iTTEwIDRIOGEyIDIgMCAwIDAtMiAydjEyYTIgMiAwIDAgMCAyIDJoOGEyIDIgMCAwIDAgMi0yVjhhMiAyIDAgMCAwLTItMmgtM2wtMi0yWiIvPjwvc3ZnPg==
---
[folder] Project Root Directory                        // {#success}Code snippet demo
    src                                  // Source code directory
        components({#warning}Vue,React)   // Component library
        utils                            // {#error}Utility functions
</LiteTree>
:::