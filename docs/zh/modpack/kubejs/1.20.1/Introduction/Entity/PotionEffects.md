---
title: 药水效果
description: LivingEntity 药水效果系统完整操作指南
tags:
  - KubeJS
  - LivingEntity
  - PotionEffects
progress: 90
---

# {{ $frontmatter.title }}

## 概述 {#overview}

药水效果（PotionEffects）系统允许为生物实体添加、移除和检测各种状态效果。仅 LivingEntity 及其子类支持。

## 适用范围 {#applicability}

::: alert {"type": "warning", "title": "适用限制"}
仅 LivingEntity（生物实体）可添加药水效果，部分实体（如矿车、盔甲架）不适用或对部分效果免疫。
:::

## 基础用法 {#basic_usage}

### 添加药水效果 {#add_effect}

```js
entity.potionEffects.add('minecraft:night_vision', 200, 0, false, true);
```

### 检查药水效果 {#check_effect}

```js
const hasNightVision = entity.hasEffect('minecraft:night_vision');
```

### 移除药水效果 {#remove_effect}

```js
entity.removeEffect('minecraft:night_vision');
entity.removeAllEffects();
```

## 参数说明 {#parameter_description}

| 参数         | 类型      | 默认值 | 说明                 |
|--------------|-----------|--------|----------------------|
| effectId     | string    | 必需   | 药水效果注册名       |
| duration     | number    | 200    | 持续时间（tick）     |
| amplifier    | number    | 0      | 效果等级（0为1级）   |
| ambient      | boolean   | false  | 是否为环境效果       |
| visible      | boolean   | true   | 是否显示粒子         |

## 进阶用法与示例 {#advanced_usage}

```js
// 条件性应用
if (entity.health < entity.maxHealth * 0.3) {
    entity.potionEffects.add('minecraft:regeneration', 200, 0);
}

// 区域效果
PlayerEvents.tick(event => {
    const player = event.player;
    if (player.level.getBiome(player.blockPosition()).toString() === 'minecraft:desert') {
        player.potionEffects.add('minecraft:fire_resistance', 120);
    }
});
```