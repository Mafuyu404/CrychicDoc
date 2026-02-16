---
title: Item
hidden: false
priority: 110
collapsed: true
---

<llm-only>
## KubeJS 物品系统

这是KubeJS物品系统的核心文档页面。用于注册和管理Minecraft中的自定义物品。

### 物品注册基础

```javascript
// StartupScript 中注册物品
StartupEvents.init(event => {
    // 简单物品注册
    event.create('my_mod:custom_item')
        .displayName('Custom Item')
        .rarity('epic')
        .glow(true);

    // 工具物品注册
    event.create('my_mod:custom_sword')
        .displayName('Custom Sword')
        .tier('diamond', 1561, 7.0, 3.0, 12)
        .attackDamageBonus(2.0);

    // 食物物品注册
    event.create('my_mod:custom_food')
        .displayName('Custom Food')
        .food(8, 0.8)
        .alwaysEat()
        .effect('minecraft:speed', 60, 1, 1);
});
```

### ItemType 类型定义

| 类型 | 用途 | 特殊属性 |
|-----|------|---------|
| basic | 普通物品 | 默认类型 |
| sword | 剑 | 自动添加攻击伤害 |
| pickaxe | 镐 | 挖掘速度加成 |
| axe | 斧 | 挖掘速度加成 |
| shovel | 铲 | 挖掘速度加成 |
| hoe | 锄 | 无特殊属性 |
| helmet | 头盔 | 护甲值 |
| chestplate | 胸甲 | 护甲值 |
| leggings | 护腿 | 护甲值 |
| boots | 靴子 | 护甲值 |
| bow | 弓 | 拉弓速度 |
| crossbow | 弩 | 特殊属性 |
| trident | 三叉戟 | 忠诚、激流等 |
| shield | 盾牌 | 反射伤害 |
|_food | 食物 | 饱食度恢复 |
| music_disc | 唱片 | 音乐ID |

### 重要约束

- 物品ID必须使用小写字母和下划线
- displayName用于设置游戏内显示名称
- 工具类物品必须设置tier属性
- 食物物品可以使用effect添加食用效果
- 自定义物品默认在创造模式物品栏中可见

### 相关文档

- 物品注册详情：查看Register目录
- 方块系统：Block/index.md
- 配方系统：Recipe/index.md
- 标签系统：Tag/index.md
</llm-only>

