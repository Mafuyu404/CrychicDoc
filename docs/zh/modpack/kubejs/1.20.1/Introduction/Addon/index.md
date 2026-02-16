---
title: Addon
hidden: false
priority: 10000000000
collapsed: true
---

<llm-only>
## KubeJS 扩展插件系统

这是KubeJS扩展插件的核心文档页面。扩展插件用于扩展KubeJS的核心功能，提供额外的API和事件处理能力。

### 主要扩展插件

| 扩展 | 用途 | 依赖 |
|-----|------|------|
| ProbeJS | 类型生成和IDE自动补全 | KubeJS Common |
| LootJS | 高级战利品表修改和自定义战利品 | KubeJS Common |
| KubeJS Plus | 额外工具函数和简写 | KubeJS Server |

### LootJS 战利品系统

```javascript
// 使用LootJS修改战利品表
LootJS.modifiers(event => {
    // 添加自定义战利品到所有箱子
    event.addBlockLootModifier('minecraft:chest')
        .addLoot('my_mod:rare_item')
        .weight(1);

    // 修改实体掉落
    event.addEntityLootModifier('minecraft:zombie')
        .removeLoot('minecraft:rotten_flesh')
        .addLoot('minecraft:gold_ingot')
        .count(1, 3);

    // 添加条件掉落
    event.addBlockLootModifier('minecraft:player_head')
        .when((Loot, D, World, Resource, Entity) => {
            return Entity && Entity.getType() === 'minecraft:player';
        })
        .addLoot('my_mod:player_skull');
});
```

### ProbeJS 类型生成

ProbeJS 自动生成JSII类型定义文件，提供：

- IDE自动补全支持
- 类型检查
- 文档提示

```javascript
// ProbeJS 会自动生成类型定义
// 在IDE中会自动提示可用的方法
const item = event.create('my_mod:item');
item.displayName('Custom Item'); // IDE会提示可用方法
```

### 扩展加载顺序

```
1. KubeJS Startup
2. KubeJS Server
3. LootJS
4. 其他扩展
```

### 重要约束

- 扩展插件需要在对应的脚本类型中运行
- LootJS modifier必须在ServerScript中
- 某些扩展可能有版本兼容性问题
- 使用扩展前请阅读对应扩展的文档

### 相关文档

- 战利品表：LootTable/index.md
- LootJS 详细文档：查看LootJS专用文档
- 事件系统：Event/index.md
</llm-only>

