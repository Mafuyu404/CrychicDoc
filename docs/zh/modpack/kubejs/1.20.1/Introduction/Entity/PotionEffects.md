---
title: 药水效果
description: LivingEntity 药水效果系统完整操作指南
tags:
  - KubeJS
  - LivingEntity
  - PotionEffects
  - StatusEffects
progress: 90
---

# {{ $frontmatter.title }}

## 概述 {#overview}

药水效果（PotionEffects）系统允许为生物实体添加临时或持久的状态效果。基于 [Forge 1.20.1 JavaDocs](https://mcstreetguy.github.io/ForgeJavaDocs/1.20.1-47.1.0/index.html)，只有继承自 `LivingEntity` 的实体才能应用药水效果。

### 基本要求 {#basic-requirements}

::: v-warning 适用限制
只有"生物实体"（LivingEntity）才能添加药水效果，并且实体不能对指定效果免疫。

**不适用的实体**：
- 矿车（非生物实体）
- 盔甲架（虽然是 LivingEntity，但免疫所有药水效果）

**适用的实体**：
- 玩家（Player）
- 生物（Mob）
- 动物（Animal）
- 怪物（Monster）
:::

## 效果操作方法 {#effect-operations}

### 添加效果 {#adding-effects}

`entity.potionEffects.add()` 方法提供多种重载形式：

```js
// 完整参数版本
entity.potionEffects.add(effectId, duration, amplifier, ambient, visible);

// 简化版本（默认参数）
entity.potionEffects.add(effectId, duration, amplifier);
entity.potionEffects.add(effectId, duration);
entity.potionEffects.add(effectId);
```

#### 参数说明 {#parameter-description}

| 参数 | 类型 | 默认值 | 描述 |
|------|------|--------|------|
| `effectId` | `string` | 必需 | 药水效果的注册名 |
| `duration` | `number` | 200 | 持续时间（tick，20tick=1秒） |
| `amplifier` | `number` | 0 | 效果等级（0=游戏内1级） |
| `ambient` | `boolean` | false | 是否为环境效果（信标等产生） |
| `visible` | `boolean` | true | 是否显示粒子效果 |

### 添加效果示例 {#adding-effects-examples}

```js
ItemEvents.entityInteracted(event => {
    const { entity, target, hand } = event;
    if (hand !== 'main_hand' || !target.isLiving()) return;
    
    const livingEntity = target;
    
    // 完整参数：夜视效果，10秒，1级，非环境效果，显示粒子
    livingEntity.potionEffects.add('minecraft:night_vision', 200, 0, false, true);
    
    // 简化版本：夜视效果，10秒，1级（默认非环境，显示粒子）
    livingEntity.potionEffects.add('minecraft:night_vision', 200, 0);
    
    // 更简化：夜视效果，10秒，1级（默认10秒，1级）
    livingEntity.potionEffects.add('minecraft:night_vision', 200);
    
    // 最简化：夜视效果（默认10秒，1级）
    livingEntity.potionEffects.add('minecraft:night_vision');
});
```

### 检查效果存在 {#checking-effects}

```js
// 检查是否拥有特定效果
const hasNightVision = entity.hasEffect('minecraft:night_vision');

// 获取特定效果实例
const nightVisionEffect = entity.getEffect('minecraft:night_vision');

if (nightVisionEffect) {
    console.log(`夜视等级: ${nightVisionEffect.amplifier + 1}`);
    console.log(`剩余时间: ${nightVisionEffect.duration} ticks`);
}
```

### 移除效果 {#removing-effects}

```js
// 移除特定效果
entity.removeEffect('minecraft:night_vision');

// 移除所有效果
entity.removeAllEffects();

// 移除指定药水效果
entity.potionEffects.map.forEach((effect, instance) => {
        if (effect.id == 'minecraft:regeneration') {
            instance.effect.removeAttributeModifiers(villager, villager.getAttributes(), effect.amplifier);
        }
    });
```

## 实际应用示例 {#practical-examples}

### 条件性效果应用 {#conditional-effect-application}

```js
EntityEvents.hurt(event => {
    const { entity, damage, source } = event;
    
    if (!entity.isLiving()) return;
    
    // 受到魔法伤害时给予抗性
    if (source.type == 'magic') {
        entity.potionEffects.add('minecraft:resistance', 100, 1);
        entity.tell('§6获得魔法抗性！');
    }
    
    // 生命值过低时给予再生
    if (entity.health < entity.maxHealth * 0.3) {
        entity.potionEffects.add('minecraft:regeneration', 200, 0);
        entity.tell('§a生命力回复中...');
    }
});
```

### 区域效果系统 {#area-effect-system}

```js
PlayerEvents.tick(event => {
    const player = event.player;
    
    // 每5秒检查一次
    if (event.player.age % 100 !== 0) return;
    
    const biome = player.level.getBiome(player.blockPosition()).unwrapKey().get().location().toString();
    
    // 根据生物群系应用效果
    switch (biome) {
        case 'minecraft:desert':
            if (!player.hasEffect('minecraft:fire_resistance')) {
                player.potionEffects.add('minecraft:fire_resistance', 120);
                player.tell('§e沙漠的炎热让你获得了抗火能力');
            }
            break;
            
        case 'minecraft:dark_forest':
            if (!player.hasEffect('minecraft:night_vision')) {
                player.potionEffects.add('minecraft:night_vision', 120);
                player.tell('§8黑暗森林的神秘力量增强了你的视力');
            }
            break;
            
        case 'minecraft:ocean':
            if (!player.hasEffect('minecraft:water_breathing')) {
                player.potionEffects.add('minecraft:water_breathing', 120);
                player.tell('§b海洋的祝福让你能在水中呼吸');
            }
            break;
    }
});
```