---
title: JSDoc的实例
description: 介绍如何在 KubeJS 脚本中用 JSDoc 提升类型补全，涵盖多种 JSDoc 标签在实际脚本中的独特应用。
tags:
  - JSDoc
  - KubeJS
  - ProbeJS
progress: 95
hidden: false
priority: 300
---
<llm-only>
This Chinese page (zh/modpack/kubejs/1.20.1/Introduction/Addon/ProbeJS/JSDocKubeJSUsage.md) mirrors the corresponding English content; include it when generating LLM-friendly documentation and keep the original structure.
</llm-only>


# KubeJS/ProbeJS 场景下的 JSDoc 实践

## @typedef 在自定义全局数据结构
```js
/**
 * @typedef {Object} CustomRecipe
 * @property {string} id
 * @property {string[]} ingredients
 * @property {string} result
 */
/** @type {CustomRecipe} */
const recipe = {
  id: 'my:super_recipe',
  ingredients: ['minecraft:diamond', 'minecraft:stick'],
  result: 'my:super_sword'
};
```

## @enum 用于常量集
```js
/**
 * @enum {string}
 */
const ToolTier = {
  WOOD: 'wood',
  IRON: 'iron',
  DIAMOND: 'diamond'
};
// 用于物品注册
ServerEvents.registry('item', event => {
  event.create('my_pickaxe', 'pickaxe').tier(ToolTier.DIAMOND);
});
```

## @callback 用于回调类型
```js
/**
 * @callback ItemFilter
 * @param {Internal.ItemStack} stack
 * @returns {boolean}
 */
/** @type {ItemFilter} */
const isDiamond = stack => stack.id === 'minecraft:diamond';
```

## @template 泛型函数
```js
/**
 * @template T
 * @param {T[]} arr
 * @returns {T}
 */
function first(arr) { return arr[0]; }
```

## @deprecated 标记废弃 API
```js
/**
 * @deprecated 请使用 newRegisterItem 替代
 */
function oldRegisterItem() {}
```

## @event 标注自定义事件
```js
/**
 * @event CustomEvent
 * @property {Internal.Player} player
 * @property {string} message
 */
// 用于文档和类型补全
```

## @throws 标注异常
```js
/**
 * @throws {Error} 当物品不存在时抛出
 */
function getItemOrThrow(id) {
  const item = Item.of(id);
  if (!item) throw new Error('Item not found');
  return item;
}
```

## @see 交叉引用
```js
/**
 * @see https://jsdoc.app/
 * @see KubeJS/ProbeJS 类型文件
 */
```

## @readonly 只读属性
```js
/** @readonly */
const VERSION = '1.0.0';
```

## @default 默认参数
```js
/**
 * @param {string} [name="Steve"]
 * 尽管KubeJS不支持Default，JsDoc并不参与代码的运行，所以不会报错。
 * 可用作规范
 */
function greet(name) {}
```

## @private 私有方法
```js
/** @private */
function _internalHelper() {}
```

## @public 公共方法
```js
/** @public */
function registerGlobalEvent() {}
```

## @todo 待办事项
```js
/** @todo 支持更多物品类型 */
function registerSpecialItems() {}
```

## @example 代码示例
```js
/**
 * @example
 * addCustomRecipe('my:super_recipe', ['minecraft:diamond'], 'my:super_sword');
 */
function addCustomRecipe(id, ingredients, result) {}
```

## @summary/@description/@author/@since/@license
```js
/**
 * @summary 注册自定义物品
 * @description 该函数用于批量注册自定义物品，支持多种类型。
 * @author Crychic
 * @since 2024-01-01
 * @license MIT
 */
function registerItems() {}
```

::: alert {"type": "success", "title": "注意", "variant": "outlined", "density": "compact"}
合理使用这一类JSDoc很有利于多人协作和大型项目
:::

## 事件参数类型提升 {#event-param-type-upgrade}
::: code-group
```js [无类型注释]
ItemEvents.entityInteracted(event => {
  event.entity.potionEffects.add('minecraft:night_vision', 200, 0, false, true); // 可能无补全
});
```
```js [JSDoc 类型提升]
ItemEvents.entityInteracted(event => {
  /** @type {Internal.LivingEntity} */
  const living = event.entity;
  living.potionEffects.add('minecraft:night_vision', 200, 0, false, true); // 有补全
});
```
:::

::: code-group
```js [BlockEvents]
BlockEvents.broken(event => {
  /** @type {Internal.Player} */
  const player = event.player;
  player.tell('Block broken!');
});
```
```js [PlayerEvents]
PlayerEvents.chat(event => {
  /** @type {Internal.Player} */
  const player = event.player;
  player.tell('You said: ' + event.message);
});
```
```js [ServerEvents]
ServerEvents.recipes(/** @param {Internal.RecipesEventJS} event */ event => {
  event.addShaped('minecraft:diamond', [
    'AAA',
    'AAA',
    'AAA'
  ], {
    A: 'minecraft:coal'
  });
});
```
```js [FTBTeamsEvents]
FTBTeamsEvents.playerJoinedParty(event => {
  /** @type {Internal.Player} */
  const player = event.player;
  player.tell('Welcome to the party!');
});
```
:::

## 注册事件与 Tag/Registry 操作类型注释 {#event-tag-registry-annotation}
::: code-group
```js [注册事件]
ServerEvents.recipes(/** @param {Internal.RecipesEventJS} event */ event => {
  // event.addShaped(...)
});
```
```js [Tag 操作-批量添加]
ServerEvents.tags('item', /** @param {TagEvent.Item} event */ event => {
  event.add('forge:ingots/iron', [
    'minecraft:iron_ingot',
    'minecraft:raw_iron'
  ]);
});
```
```js [Tag 操作-条件添加]
ServerEvents.tags('block', /** @param {TagEvent.Block} event */ event => {
  if (global.isModLoaded('some_mod')) {
    event.add('forge:ores/special', 'some_mod:special_ore');
  }
});
```
```js [Registry 操作-动态生成]
ServerEvents.registry('item', /** @param {Registry.Item} event */ event => {
  ['my_item1', 'my_item2'].forEach(id => {
    event.create(id, 'basic');
  });
});
```
:::

#  {#tips}
- 事件参数类型提升时，确保类型与实际对象兼容，否则补全虽有但运行时报错。
- Tag/Registry 操作建议用 JSDoc 明确类型，便于多人协作和后期维护。
- 动态 Snippet/Special 类型需唯一命名，避免与内置类型冲突。
- 类型文件变更后需 `/probejs dump` 并重启 VSCode。
- 善用类型文件和 ProbeJS Wiki 查找、反查类型定义。