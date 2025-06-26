---
title: 生物实体
description: LivingEntity 在 KubeJS 中的完整操作指南
tags:
    - KubeJS
    - LivingEntity
    - Health
    - Equipment
    - AI
progress: 90
---

# {{ $frontmatter.title }}

## 概述 {#overview}

`LivingEntity` 是所有具有生命值的实体的基类，包括玩家、怪物、动物等。基于 [Forge 1.20.1 JavaDocs](https://mcstreetguy.github.io/ForgeJavaDocs/1.20.1-47.1.0/index.html)，这个类提供了生命值管理、装备系统、AI 行为等核心功能。

### 继承关系 {#inheritance-hierarchy}

```
Entity
└── LivingEntity
    ├── Player
    ├── Mob
    │   ├── PathfinderMob
    │   ├── Monster
    │   └── AgeableMob
    └── ArmorStand
```

## 生命值系统 {#health-system}

### 基础属性 {#basic-properties}

| 属性              | 描述       |
| ----------------- | ---------- |
| `health`          | 当前生命值 |
| `maxHealth`       | 最大生命值 |
| `isAlive()`       | 是否存活   |
| `isDeadOrDying()` | 是否死亡   |

### 生命值操作 {#health-operations}

```js
// 生命值设置
entity.health = 20.0;
entity.heal(amount); // 治疗

// 最大生命值修改
const healthAttribute = entity.getAttribute("minecraft:generic.max_health");
if (healthAttribute) {
    healthAttribute.baseValue = 40.0;
}
```

### 伤害系统 {#damage-system}

```js
// 应用伤害
const damageSource = entity.damageSources().magic();
entity.hurt(damageSource, 5.0);

// 常用伤害类型
entity.damageSources().generic(); // 通用伤害
entity.damageSources().magic(); // 魔法伤害
entity.damageSources().fall(); // 跌落伤害
entity.damageSources().onFire(); // 火焰伤害
```

## 装备系统 {#equipment-system}

### 装备槽位 {#equipment-slots}

| 槽位       | 描述 |
| ---------- | ---- |
| `mainhand` | 主手 |
| `offhand`  | 副手 |
| `head`     | 头部 |
| `chest`    | 胸部 |
| `legs`     | 腿部 |
| `feet`     | 脚部 |

### 装备操作 {#equipment-operations}

```js
// 获取装备
const mainHandItem = entity.mainHandItem;
const helmet = entity.getItemBySlot("head");

// 设置装备
entity.setMainHandItem(Item.of("minecraft:diamond_sword"));
entity.setItemSlot("head", Item.of("minecraft:diamond_helmet"));
```

## 药水效果 {#potion-effects}

LivingEntity 支持药水效果的基础操作：

```js
// 基础效果操作
entity.potionEffects.add("minecraft:strength", 1200, 1);
entity.removeEffect("minecraft:strength");
entity.hasEffect("minecraft:strength");
```

详细的药水效果操作请参考 [药水效果文档](./PotionEffects)。

## 属性系统 {#attribute-system}

### 常用属性 {#common-attributes}

| 属性                        | 描述     |
| --------------------------- | -------- |
| `generic.max_health`        | 最大生命 |
| `generic.movement_speed`    | 移动速度 |
| `generic.attack_damage`     | 攻击伤害 |
| `generic.armor`             | 护甲值   |
| `generic.armor_toughness`   | 护甲韧性 |

### 属性操作 {#attribute-operations}

```js
// 获取属性
const speedAttr = entity.getAttribute("minecraft:generic.movement_speed");
console.log(`当前速度: ${speedAttr.baseValue}`);

// 修改基础值
speedAttr.baseValue = 0.5;

// 添加修饰符
entity.modifyAttribute("minecraft:generic.movement_speed", "speed_boost", 0.2, "addition");
```

详细的属性操作请参考 [属性系统文档](./Attribute)。

## AI 系统 {#ai-system}

### 基础 AI 控制 {#basic-ai-control}

```js
// AI 控制
entity.setNoAi(true);  // 禁用 AI
entity.setNoAi(false); // 启用 AI

// 攻击目标（仅适用于 Mob）
if (entity.type !== 'minecraft:player') {
    entity.setTarget(targetEntity);
    const currentTarget = entity.getTarget();
}
```

## 状态检查 {#status-checks}

### 生存状态 {#survival-status}

```js
// 基础状态
const isAlive = entity.isAlive();
const isDead = entity.isDeadOrDying();
const isInvulnerable = entity.isInvulnerable();

// 环境状态
const isInWater = entity.isInWater();
const isOnFire = entity.isOnFire();
const isOnGround = entity.onGround;
```

### 行为状态 {#behavior-status}

```js
// 行为相关
const isSleeping = entity.isSleeping();
const isSprinting = entity.isSprinting();
const isCrouching = entity.isShiftKeyDown();
const isSwimming = entity.isSwimming();
```

## 数据持久化 {#data-persistence}

```js
// 持久化数据存储
entity.persistentData.putString("custom_type", "elite");
entity.persistentData.putInt("level", 5);

// 读取数据
const customType = entity.persistentData.getString("custom_type");
const level = entity.persistentData.getInt("level");
```

### 注意事项 {#important-notes}

**关于实体标签（Tags）**：

-   实体的 `tags` 属性是用于数据包标签系统，不是用于自定义数据存储
-   数据包标签用于 `@e[tag=...]` 选择器和条件判断
-   自定义数据存储应使用 `persistentData`

```js
// 正确：使用 persistentData 存储自定义数据
entity.persistentData.putString("role", "boss");

// 标签用于选择器，不是数据存储
entity.addTag("boss_mob"); // 用于 /kill @e[tag=boss_mob]
```