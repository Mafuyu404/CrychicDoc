---
title: Tag
hidden: false
priority: 70
collapsed: true
---

<!--
<llm-only>
## KubeJS 标签系统

这是KubeJS标签系统的核心文档页面。标签用于对Minecraft中的游戏内容进行分类和筛选，是实现跨模组兼容性的重要机制。

### 标签类型

| 类型 | 命名空间 | 用途 |
|-----|---------|------|
| Item | items/ | 物品分类，用于配方、掉落等 |
| Block | blocks/ | 方块分类，用于挖掘、交互等 |
| EntityType | entity_types/ | 实体分类，用于刷怪、效果等 |
| Biome | biomes/ | 生物群系分类，用于世界生成等 |
| Fluid | fluids/ | 流体分类 |
| Structure | structures/ | 结构分类 |

### 标签操作基础

```javascript
// StartupScript 中创建和操作标签
StartupEvents.init(event => {
    // 创建物品标签
    event.createTag('items', 'my_mod:useful_items', [
        'minecraft:apple',
        'minecraft:bread',
        'my_mod:custom_food'
    ]);

    // 创建方块标签
    event.createTag('blocks', 'my_mod:mineable_ores', [
        'minecraft:coal_ore',
        'minecraft:iron_ore',
        'my_mod:custom_ore'
    ]);

    // 添加到现有标签
    event.addToTag('items', 'minecraft:cookies', '#forge:foods');

    // 从标签中移除
    event.removeFromTag('items', 'minecraft:rotten_flesh', '#forge:foods');
});
```

### 标签格式规范

标签JSON文件位置：`data/<namespace>/tags/<type>/<path>.json`

```json
{
  "values": [
    "minecraft:stone",
    "minecraft:granite",
    "#my_mod:building_blocks"
  ],
  "replace": false
}
```

### 标签引用语法

在KubeJS中引用标签：

- `#namespace:tag_path` - 引用标签
- `tag` - 标签前缀表示引用标签而非直接物品

```javascript
// 使用标签作为配方输入
event.recipes.craftingTable('output', '#my_mod:useful_items');
```

### 重要约束

- 标签名称使用小写字母和下划线
- 标签路径不能包含大写字母
- 使用 `#` 前缀在代码中引用标签
- 标签可以包含其他标签（嵌套）
- 确保标签路径与Minecraft命名规范兼容

### 相关文档

- 标签目录：Tag/Catalogue.md
- 标签描述：Tag/Description.md
- 物品系统：Item/index.md
- 方块系统：Block/index.md
- 配方系统：Recipe/index.md
</llm-only>
-->

