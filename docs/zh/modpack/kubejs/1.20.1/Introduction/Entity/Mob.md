---
title: Mob（生物）
description: Mob 类的属性、AI、目标选择与 KubeJS 用法
progress: 90
tags:
    - KubeJS
    - Entity
    - Mob
---

# Mob

## 概述 {#overview}

Mob 是 Minecraft 中所有具有 AI 行为的生物实体基类，继承自 LivingEntity。涵盖怪物、动物、中立生物等，支持 AI 目标、目标选择器、行为树等高级机制。

## 机制说明 {#mechanism}

-   继承自 LivingEntity，扩展了 AI 目标（goalSelector）、目标选择器（targetSelector）、大脑（Brain）等。
-   支持 AI 行为树、目标优先级、攻击/防御/移动等多种行为。
-   常用方法：addGoal、addTargetGoal、setTarget、getTarget、isAggressive、canAttack、wander、lookAt 等。
-   支持事件驱动、tick 自动更新、NBT 持久化。

## 常见方法 {#common_methods}

```js
// 添加 AI 目标
const MeleeAttackGoal = Java.loadClass("net.minecraft.world.entity.ai.goal.MeleeAttackGoal");
mob.goalSelector.addGoal(0, new MeleeAttackGoal(mob, 1.0, true));

// 设置攻击目标
mob.setTarget(targetEntity);
const currentTarget = mob.getTarget();

// 判断是否为攻击性生物
if (mob.isAggressive()) {
    /* ... */
}
```

## 常见子类与扩展 {#subclasses}

### PathfinderMob（寻路生物）{#PathfinderMob}

- 绝大多数生物的直接父类，具备基础寻路与拴绳逻辑。
- 常用方法/属性：

| 方法/属性                | 说明                       |
|-------------------------|----------------------------|
| getNavigation()         | 获取寻路控制器             |
| moveTo(x, y, z, speed)  | 移动到指定位置             |
| isPathFinding()         | 是否正在寻路               |

::: code-group

```js [KubeJS]
mob.getNavigation().moveTo(100, 64, 100, 1.0)
```

:::

---

### Animal（动物）{#Animal}

- 代表可繁殖的动物，如牛、羊、猪、鸡等。
- 常用方法/属性：

| 方法/属性   | 说明               |
| ----------- | ------------------ |
| isBaby()    | 是否为幼年         |
| setAge(age) | 设置年龄           |
| canMate(other) | 是否可与指定动物繁殖 |
| setInLove(player) | 进入繁殖状态   |

::: code-group

```js [KubeJS]
if (mob.isBaby()) mob.setAge(0)
if (mob.canMate(other)) mob.setInLove(player)
```

:::

---

### TamableMob（可驯服生物）{#TamableMob}

- 代表如狼、猫、鹦鹉、狐狸等可被玩家驯服的生物。
- 常用方法/属性：

| 方法/属性      | 说明                 |
| -------------- | -------------------- |
| isTame()       | 是否已被驯服         |
| setTame(bool)  | 设置驯服状态         |
| getOwner()     | 获取主人实体         |
| setOwner(uuid) | 设置主人             |
| isInSittingPose() | 是否坐下           |
| setSitting(bool)  | 设置坐下状态       |

::: code-group

```js [KubeJS]
if (mob.isTame()) mob.setSitting(true)
```

:::

---

### Monster（怪物）{#Monster}

- 代表敌对生物，如僵尸、骷髅、苦力怕等。
- 常用方法/属性：

| 方法/属性    | 说明               |
| ------------ | ------------------ |
| isAggressive() | 是否为攻击性生物 |
| setTarget(entity) | 设置攻击目标   |
| getTarget()      | 获取当前目标    |
| canAttack(entity)| 能否攻击目标    |

注：这些方法并非Monster独有方法

::: code-group

```js [KubeJS]
if (mob.isAggressive()) mob.setTarget(player)
```

:::

---

### WaterAnimal（水生生物）{#WaterAnimal}

- 代表如鱼、海豚、鱿鱼等水生生物。
- **无独有 API，常用方法均为 Mob 通用。**
- 其"水生"特性体现在 `mobType`、呼吸机制、拴绳等重写。

| 方法/属性                | 说明                       |
|-------------------------|----------------------------|
| mobType                 | 类型标识，水生为 'WATER'   |
| isInWaterOrBubble()     | 是否在水或气泡柱中         |
| setAirSupply(value)     | 设置空气值（Mob通用）      |
| getAirSupply()          | 获取空气值（Mob通用）      |

注：这些方法并非WaterAnimal独有方法

::: code-group

```js [KubeJS]
if (mob.mobType == 'WATER' && !mob.isInWaterOrBubble()) {
  mob.setAirSupply(0)
}
```

:::

#### 说明 {#tips}
- 判断水生生物推荐用 `mobType`，如 `mob.mobType == 'WATER'`。
- `setAirSupply`、`getAirSupply`、`isInWaterOrBubble` 等为所有 Mob 通用，非水生专属。

---

### AmbientCreature（环境生物）{#AmbientCreature}

- 代表如蝙蝠等无害环境生物。
- **无特殊扩展，继承自 Mob。**

| 方法/属性 | 说明           |
| --------- | -------------- |
| -         | 无特殊扩展     |

---

## 注意事项 {#notes}

- 仅 Mob 及其子类支持 AI 目标、目标选择等操作。
- 某些方法仅特定子类可用，使用前建议用 JSDoc 标注类型提升补全体验。
- 若需进一步确认 API，请参考 typefiles/1.20.1 目录下的类型定义和 Forge JavaDocs。
