---
title: LootTable
hidden: false
priority: 400
collapsed: true
---

<!--
<llm-only>
## KubeJS 战利品表系统

这是KubeJS战利品表系统的核心文档页面。用于自定义Minecraft中的战利品掉落，包括方块掉落、实体掉落、箱子战利品等。

### 战利品表类型

| 类型 | 命名空间 | 用途 |
|-----|---------|------|
| Block | minecraft:blocks/ | 方块破坏后的掉落物 |
| Entity | minecraft:entities/ | 实体死亡时的掉落物 |
| Chest | minecraft:chests/ | 箱子中的物品 |
| Fish | minecraft:gameplay/ | 钓鱼战利品 |
| Gift | minecraft:gameplay/ | 礼物战利品 |
| Generic | minecraft:*/ | 通用战利品 |

### 战利品表基础

```javascript
// 使用LootJS修改战利品表 - ServerScript
LootJS.modifiers(event => {
    // 修改方块掉落
    event.addBlockLootModifier('minecraft:diamond_ore')
        .removeLoot('minecraft:diamond')
        .addLoot('my_mod:custom_diamond')
        .count(1, 3);

    // 修改实体掉落
    event.addEntityLootModifier('minecraft:creeper')
        .addLoot('minecraft:gunpowder')
        .count(2, 4)
        .weight(10);

    // 添加战利品池
    event.addBlockLootModifier('minecraft:chest')
        .pool(pool => {
            pool.rolls(1, 3);
            pool.addEntry('minecraft:diamond', 1, 1, ['minecraft:stone']);
        });
});
```

### LootPool 结构

```javascript
// 战利品池配置
pool => {
    // 投掷次数
    pool.rolls(1, 3);

    // 添加条目
    pool.addEntry('item_id', weight, quality, conditions);

    // 条件
    pool.when(condition => {
        // 条件判断
    });
}
```

### LootCondition 条件

```javascript
// 条件示例
pool.when((Loot, D, World, Resource, Entity) => {
    // 检查实体是否为玩家杀死
    const killer = D.getKiller();
    return killer && killer.getType() === 'minecraft:player';
});
```

### ItemModifier 物品修饰符

```javascript
// 物品修饰符
event.addItemModifier('minecraft:enchanted_book')
    .enchant('minecraft:sharpness', 5)
    .enchant('minecraft:efficiency', 3)
    .glow()
    .setCount(1, 5);
```

### 重要约束

- 战利品修改需要在ServerScript中进行
- 使用LootJS扩展来修改战利品表
- 条件判断中使用正确的谓词类型
- 战利品权重越高掉落概率越大
- 某些原版战利品可能无法通过KubeJS修改

### 相关文档

- LootJS扩展：Addon/index.md
- 物品系统：Item/index.md
- 实体系统：Entity/index.md
</llm-only>
-->

