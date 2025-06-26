---
title: JSDoc基础
description: 介绍 JSDoc 的基本语法、常用标签和在 JavaScript/KubeJS/ProbeJS 中的通用注释方法，适合新手入门。
tags:
  - JSDoc
  - Basic
progress: 90
---

# JSDoc 基础语法与通用用法

JSDoc 是 JavaScript 的注释标准，能为代码提供类型提示、文档生成和开发辅助。KubeJS/ProbeJS 项目中，JSDoc 注释配合类型文件可极大提升开发体验。

## 常用标签与用法 {#tag-common}
- `@type`：变量类型
- `@param`：函数参数类型
- `@returns`：返回值类型
- `@typedef`：自定义类型
- `@template`：泛型
- `@this`：指定 this 上下文
- `@property`：对象属性
- `@callback`：回调类型
- `@see`：文档跳转
- `@enum`：枚举类型
- `@deprecated`：标记已废弃
- `@example`：代码示例
- `@default`：默认值
- `@const`：常量
- `@readonly`：只读属性
- `@module`：模块声明
- `@namespace`：命名空间
- `@exports`：导出
- `@private` `@protected` `@public`：访问修饰符
- `@ignore`：忽略文档
- `@inheritdoc` `@override`：继承/重写 - 不支持
- `@augments` `@mixes`：继承/混入 - 不支持
- `@fires` `@event`：事件 - 不支持
- `@yields` `@async`：生成器/异步 - 不支持
- `@throws`：抛出异常
- `@summary` `@description`：摘要/描述
- `@author` `@version` `@since` `@license` `@todo`：元信息

## 变量类型注释 {#tag-type-annotation}
::: code-group
```js [基础]
/** @type {number} */
let count = 0;
```
```js [KubeJS]
/** @type {Internal.LivingEntity} */
let living = event.entity;
```
:::

## 函数参数与返回值注释 {#tag-param-returns}
::: code-group
```js [基础]
/**
 * @param {string} id
 * @returns {number}
 */
function getIdLength(id) {
  return id.length;
}
```
```js [KubeJS]
/**
 * @param {Internal.ItemBuilder} item
 * @returns {void}
 */
function setupItem(item) {
  item.maxStackSize(16);
}
```
:::

## 对象与属性注释 {#tag-typedef-property}
::: code-group
```js [@typedef]
/**
 * @typedef {Object} MyData
 * @property {string} name
 * @property {number} value
 */
```
```js [KubeJS]
/**
 * @typedef {Object} CustomItem
 * @property {string} id
 * @property {number} power
 */
```
:::

## 枚举与常量 {#tag-enum-const}
::: code-group
```js [@enum]
/**
 * @enum {number}
 */
const Direction = {
  Up: 0,
  Down: 1
};
```
```js [@const]
/** @const {number} */
const MAX_COUNT = 100;
```
:::

## 只读与默认值 {#tag-readonly-default}
::: code-group
```js [@readonly]
/** @readonly */
let version = '1.0.0';
```
```js [@default]
/**
 * @param {string} [name="Steve"]
 */
function hello(name) {}
```
:::

## 模块与命名空间 {#tag-module-namespace}
::: code-group
```js [@module]
/** @module MyModule */
```
```js [@namespace]
/** @namespace MyNamespace */
```
:::

## 访问修饰符与导出 {#tag-access-export}
::: code-group
```js [@private]
/** @private */
function _internal() {}
```
```js [@exports]
/** @exports MyExport */
```
:::

## 抛出异常与生成器 {#tag-throws-yields}
::: code-group
```js [@throws]
/**
 * @throws {Error} Something went wrong
 */
function mayThrow() {}
```
```js [@yields]
/**
 * @yields {number}
 */
function* gen() { yield 1; }
```
:::

## 示例、描述与元信息 {#tag-example-meta}
::: code-group
```js [@example]
/**
 * @example
 * hello('Steve');
 */
function hello(name) {}
```
```js [@summary]
/**
 * @summary 简要说明
 * @description 详细描述
 * @author Crychic
 * @version 1.0.0
 * @since 2024-01-01
 * @license MIT
 * @todo 优化实现
 */
```
:::

## 其他标签与特殊用法 {#tag-other}
::: code-group
```js [@ignore]
/** @ignore */
```
```js [@deprecated]
/** @deprecated 请使用 newMethod 替代 */
function oldMethod() {}
```
::: 