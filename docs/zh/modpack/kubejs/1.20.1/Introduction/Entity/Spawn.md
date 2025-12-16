---
title: 生成实体
tags:
  - KubeJS
  - Entity
hidden: false
priority: 50
---

# 实体生成

## 前言 {#introduction}

## 指定的生成 {#custom_spawn}

- 借聊天事件为例，生成尸壳。

- 并非瞬时生成，生成总是有约几十刻的延时，正常现象，原因未探明。

```js
PlayerEvents.chat(event => {
    const { message, player, player: { block, block: { x, y, z, } }, server, level } = event;
    if (message !== 'test') return;
    // 新建一个尸壳实体
    const husk = level.createEntity('minecraft:husk');
    // 设置位置
    husk.setPosition(x, y, z);
    // 设置了显示名字
    husk.setCustomName(Component.of('僵尸测试员'));
    // 设置nbt
    husk.mergeNbt({ NoAI: true });
    // 生成 不调用此函数实体不生成
    husk.spawn();
})
```

有时你会希望能够操作模组提供的生物或拥有特定方法的生物，却无法找到需要使用的方法，你需要查看[JSoc](../Addon/ProbeJS/JSDoc)章节

例如我希望操作马匹穿脱马鞍马凯或控制驯服状态：

```js
ItemEvents.entityInteracted(event => {
    const { player, target, item } = event;
    if (target.type == "minecraft:horse") {
        // 为了让相关方法正常补全，我们需要明确类型注解
        /**
         * @type {Internal.Horse} horse
         */
        const horse = target;
        horse.setTamed(true);
        horse.setOwner(player);
        horse.setCustomName(Component.literal("§6我的坐骑"));
        horse.setVariant("white");
        if (horse.isArmor(item)) {
            horse.setArmor(item);
        }
        horse.equipSaddle("players");
    }
})
```
