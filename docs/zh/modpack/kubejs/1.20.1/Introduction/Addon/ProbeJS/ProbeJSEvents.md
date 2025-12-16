---
title: ProbeJS事件
tags:
  - ProbeJS
  - Events
  - JSDoc
progress: 10
hidden: false
priority: 400
---

# ProbeJS Events Reference

## generateDoc 事件 {#event}

### 概述
`generateDoc` 事件在 ProbeJS 每次类型导出前触发，允许你在运行时动态添加 Snippet、自定义类型和文档修改。

### 触发时机
- 在 ProbeJS 生成类型文件前（如 `/probejs dump` 或世界加载时）。

### 参数
- `event` (对象)：提供添加 Snippet、自定义类型、修改文档的方法。

### 关键方法
- `event.addSnippet(type, choices, description?)`：添加动态 Snippet。
- `event.customSnippet(type, prefixes, body, description?)`：添加完全自定义 Snippet。
- `event.specialType(type, values)`：注册补全用的特殊类型。

### 用法示例
::: code-group
```js [基础]
ProbeJSEvents.generateDoc(event => {
  event.addSnippet("metal", ["gold", "iron", "copper", "netherite"], "A collection of metals");
});
```
```js [进阶]
ProbeJSEvents.generateDoc(event => {
  event.customSnippet(
    "itemstack",
    ["@itemstack"],
    [`"${1}x ${2|${Item.list.map(stack => stack.id).join(",")}|}"`],
    "Create ItemStack with count and item."
  );
});
```
:::

### 典型场景
- 为 VSCode 补全提供自定义代码片段。
- 动态生成注册表或物品列表。
- 增强自定义内容的文档和上手体验。

### JSDoc 与类型补全集成
- Snippet 和特殊类型可在 JSDoc 注释中引用，提升补全。
- 用 `@type` 或 `@param` 搭配 `specialType` 注册的类型。

### 常见误区
- Snippet 类型需唯一，避免与内置类型冲突。
- 修改 Snippet 逻辑后需重新 dump 并重载。

---

## generateType 事件 {#event-generatetype}

### 概述
`generateType` 事件允许你在 ProbeJS 类型生成阶段注入或修改类型定义。

### 触发时机
- 类型文件生成时，在 `generateDoc` 之后。

### 参数
- `event` (对象)：提供添加或覆盖类型定义的方法。

### 用法示例
::: code-group
```js [基础]
ProbeJSEvents.generateType(event => {
  event.addType("MyCustomType", {
    foo: "string",
    bar: "number"
  });
});
```
:::

### 典型场景
- 为 JSDoc 注释添加自定义类型。
- 为模组内容补丁或扩展类型定义。

### JSDoc 与类型补全集成
- 此处添加的类型可在 `@type`、`@param` 等中引用，获得 IDE 补全。

### 常见误区
- 类型名不得与核心类型冲突。
- 变更需重新 dump 才生效。

---

## generateSpecialType 事件 {#event-generatespecialtype}

### 概述
`generateSpecialType` 事件适用于需要注册枚举或值集等特殊类型补全的高级用法。

### 触发时机
- 类型生成时，在 `generateType` 之后。

### 参数
- `event` (对象)：提供注册特殊类型的方法。

### 用法示例
::: code-group
```js [基础]
ProbeJSEvents.generateDoc(event => {
  event.specialType("SussyBaka", [1, 2, 3, "sus", "lol", "Internal.ItemStack_"]);
});
```
:::

### 典型场景
- 注册枚举或值集供 JSDoc 和补全使用。
- 为脚本 API 提供自定义值列表。

### JSDoc 与类型补全集成
- 在 JSDoc 中用 `@type {SussyBaka}` 等获得补全。

### 常见误区
- 特殊类型名需唯一。
- 值列表应全面、及时更新。

---

## 其他 ProbeJS 事件 {#event-other}

ProbeJS 未来可能提供更多事件，详见 [ProbeJS Wiki](https://github.com/Prunoideae/ProbeJS/wiki)。
