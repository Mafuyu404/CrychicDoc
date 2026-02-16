---
title: ProbeJS Events
tags:
  - ProbeJS
  - Events
  - JSDoc
progress: 10
hidden: false
priority: 400
---

# ProbeJS Events Reference

## generateDoc Event {#event}

### Overview
The `generateDoc` event triggers before each ProbeJS type export, allowing you to dynamically add Snippets, custom types, and documentation modifications at runtime.

### Trigger Timing
- Before ProbeJS generates type files (e.g., `/probejs dump` or on world load).

### Parameters
- `event` (object): Provides methods to add Snippets, custom types, and modify documentation.

### Key Methods
- `event.addSnippet(type, choices, description?)`: Add dynamic Snippet.
- `event.customSnippet(type, prefixes, body, description?)`: Add fully custom Snippet.
- `event.specialType(type, values)`: Register special type for completion.

### Usage Examples
::: code-group
```js [Basic]
ProbeJSEvents.generateDoc(event => {
  event.addSnippet("metal", ["gold", "iron", "copper", "netherite"], "A collection of metals");
});
```
```js [Advanced]
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

### Typical Scenarios
- Provide custom code snippets for VSCode completion.
- Dynamically generate registry or item lists.
- Enhance documentation and onboarding experience for custom content.

### JSDoc and Type Completion Integration
- Snippets and special types can be referenced in JSDoc comments to improve completion.
- Use with `@type` or `@param` together with types registered via `specialType`.

### Common Misconceptions
- Snippet types must be unique, avoid conflicting with built-in types.
- After modifying Snippet logic, need to re-dump and reload.

---

## generateType Event {#event-generatetype}

### Overview
The `generateType` event allows you to inject or modify type definitions during the ProbeJS type generation phase.

### Trigger Timing
- During type file generation, after `generateDoc`.

### Parameters
- `event` (object): Provides methods to add or override type definitions.

### Usage Examples
::: code-group
```js [Basic]
ProbeJSEvents.generateType(event => {
  event.addType("MyCustomType", {
    foo: "string",
    bar: "number"
  });
});
```
:::

### Typical Scenarios
- Add custom types for JSDoc comments.
- Add type definitions for mod content patches or extensions.

### JSDoc and Type Completion Integration
- Types added here can be referenced in `@type`, `@param`, etc., to get IDE completion.

### Common Misconceptions
- Type names must not conflict with core types.
- Changes require re-dumping to take effect.

---

## generateSpecialType Event {#event-generatespecialtype}

### Overview
The `generateSpecialType` event is for advanced usage requiring registration of special types like enums or value sets for completion.

### Trigger Timing
- During type generation, after `generateType`.

### Parameters
- `event` (object): Provides methods to register special types.

### Usage Examples
::: code-group
```js [Basic]
ProbeJSEvents.generateDoc(event => {
  event.specialType("SussyBaka", [1, 2, 3, "sus", "lol", "Internal.ItemStack_"]);
});
```
:::

### Typical Scenarios
- Register enums or value sets for JSDoc and completion.
- Provide custom value lists for script APIs.

### JSDoc and Type Completion Integration
- Use `@type {SussyBaka}` in JSDoc to get completion.

### Common Misconceptions
- Special type names must be unique.
- Value lists should be comprehensive and kept up to date.

---

## Other ProbeJS Events {#event-other}

ProbeJS may provide more events in the future. For details, see [ProbeJS Wiki](https://github.com/Prunoideae/ProbeJS/wiki).
