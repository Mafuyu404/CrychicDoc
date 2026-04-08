---
authors: ['Gu-meng']
---
# File Priority
Code executes from top to bottom, and files are loaded from `a` to `z` by default.

In KubeJS, you can call methods/variables across files, but only if the source file is loaded earlier (higher priority).

Note: cross-file does not mean cross-side. For example, you cannot call `server` methods from `startup`. If you really need cross-side data, see [Global variables](../KubeJSAdvanced/GlobalVariable.md).

KubeJS provides a comment to set file priority: `// priority: 10`

Put this comment at the top of the file (first line).

The number after the colon is the priority value.

Higher number = loaded earlier. Lower number = loaded later.

Files without priority are loaded by filename (`a` to `z`).
