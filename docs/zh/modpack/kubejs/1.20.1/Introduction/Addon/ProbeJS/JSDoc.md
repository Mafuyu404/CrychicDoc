---
title: JSDoc文档
description: 全面介绍 JSDoc 在 KubeJS/ProbeJS 脚本开发中的类型补全、最佳实践、与类型文件协同、ProbeJS 高级特性与常见问题。
tags:
  - JSDoc
  - KubeJS
  - ProbeJS
progress: 100
hidden: false
priority: 100
---
<llm-only>
This Chinese page (zh/modpack/kubejs/1.20.1/Introduction/Addon/ProbeJS/JSDoc.md) mirrors the corresponding English content; include it when generating LLM-friendly documentation and keep the original structure.
</llm-only>


# JSDoc

## 目录
- [类型补全的意义与痛点](#type-completion-significance)
- [JSDoc 基础与进阶用法](#jsdoc-basics-advanced)
- [KubeJS 类型文件与 JSDoc 协同](#kubejs-typefiles-jsdoc)
- [ProbeJS 高级特性](#probejs-advanced)
- [小贴士](#tips)

## 类型补全的意义与痛点 {#type-completion-significance}
KubeJS/ProbeJS 脚本开发高度依赖类型补全。由于 JavaScript 的弱类型特性，KubeJS 事件参数往往类型宽泛，导致 IDE 补全不准确，开发体验受限。合理使用 JSDoc 注释，结合 ProbeJS 生成的类型文件，可极大提升类型推断和补全能力。

**常见痛点：**
- 事件参数如 `event.entity` 仅为 `Entity`，无法补全 `potionEffects` 等方法。
- 注册、Tag 操作等 API 类型复杂，难以手动推断。
- 类型补全失效、类型冲突、类型刷新等 IDE 问题。

## JSDoc 基础与进阶用法 {#jsdoc-basics-advanced}
JSDoc 是 JavaScript 的类型注释标准。通过 `@type`、`@param`、`@returns`、`@typedef`、`@template`、`@this` 等标签，能为变量、函数、对象、泛型、this 上下文等提供类型提示。

- [基础语法与通用用法详见 → JSDocBasics.md](./JSDocBasics.md)
- [KubeJS/ProbeJS 场景实践详见 → JSDocKubeJSUsage.md](./JSDocKubeJSUsage.md)
- [高级技巧与最佳实践详见 → JSDocAdvanced.md](./JSDocAdvanced.md)

## KubeJS 类型文件与 JSDoc 协同 {#kubejs-typefiles-jsdoc}
KubeJS/ProbeJS 会自动生成类型文件（如 `Internal.*`、`TagEvent.*`、`Registry.*`），配合 JSDoc 注释可实现精准类型补全。

**查找类型定义：**
- 在脚本中用 `@type`、`@param` 指定类型，如 `Internal.LivingEntity`、`TagEvent.Item`。
- 按住 `Ctrl` 并点击类型名，可跳转到类型定义（如 `typefiles/1.20.1/probe/generated/events.d.ts`）。
- 结合 VSCode 的 jsconfig.json 配置，确保类型文件被正确索引。

**示例：**
```js
/** @type {Internal.LivingEntity} */
let living = event.entity;
```

## ProbeJS 高级特性 {#probejs-advanced}
ProbeJS 支持通过 `generateDoc` 事件动态生成 Snippet、Special 类型、文档属性等，极大提升开发体验。

### 动态 Snippet 生成 {#snippet-generation}
```js
ProbeJSEvents.generateDoc(event => {
  event.addSnippet("metal", ["gold", "iron", "copper", "netherite"], "A collection of metals");
});
```
详见：[Dynamic Snippet Generation](https://github.com/Prunoideae/ProbeJS/wiki/01.-Dynamic-Snippet-Generation)

### 动态文档变更与 Special 类型 {#special-type-generation}
```js
ProbeJSEvents.generateDoc(event => {
  event.specialType("SussyBaka", [1, 2, 3, "sus", "lol", "Internal.ItemStack_"]);
});
```
详见：[Dynamic Document Modification](https://github.com/Prunoideae/ProbeJS/wiki/02.-Dynamic-Document-Modification)

## 小贴士 {#tips}
- JSDoc 只影响类型提示，不改变 JS 运行时类型。
- 类型补全失效时，检查 jsconfig.json、类型文件、重启 VSCode、重新生成类型文件。
- 避免多版本类型文件混用，防止类型冲突。
- 善用 `@typedef`、`@type` 扩展全局类型。
- 参考类型文件和 ProbeJS Wiki，反查类型定义和用法。