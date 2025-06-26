---
title: Goal
description: KubeJS 实体 AI 目标（Goal）系统 API 参考与用法说明
tags:
    - KubeJS
    - Entity
    - Goal
progress: 90
---

# Goal

## 概述 {#overview}

AI 目标（Goal）系统是 Minecraft 实体 AI 的核心，定义了实体的行为优先级与决策逻辑。KubeJS 支持通过 Java 反射机制操作实体的 AI 目标，实现自定义行为。

```
Entity
└── Mob
    ├── goalSelector
    └── targetSelector
```

- `goalSelector`：行为目标选择器（如移动、观察、浮水等）
- `targetSelector`：攻击目标选择器（如反击、锁定最近敌人等）

## 目标操作 {#operations}

### 添加目标 {#add}

```js
const FloatGoal = Java.loadClass("net.minecraft.world.entity.ai.goal.FloatGoal");
const MeleeAttackGoal = Java.loadClass("net.minecraft.world.entity.ai.goal.MeleeAttackGoal");
const RandomStrollGoal = Java.loadClass("net.minecraft.world.entity.ai.goal.RandomStrollGoal");

mob.goalSelector.addGoal(0, new FloatGoal(mob));
mob.goalSelector.addGoal(3, new MeleeAttackGoal(mob, 1.0, true));
mob.goalSelector.addGoal(7, new RandomStrollGoal(mob, 1.0));
```

- **优先级说明**：数值越小，优先级越高。
- 必须使用 `Java.loadClass` 加载目标类，并用 `new` 实例化。

### 移除目标 {#remove}

```js
mob.goalSelector.removeAllGoals();
mob.targetSelector.removeAllGoals();
```

- 可通过 `removeAllGoals()` 清空所有目标。
- 相关基础 AI 控制：
  - `mob.setTarget(targetEntity)` 设置攻击目标
  - `mob.getTarget()` 获取当前目标
  - `mob.setNoAi(true/false)` 启用/禁用 AI

## 常用目标类型 {#common}

### 行为目标 {#behavior}

| 目标类 | 描述 | 构造参数 |
|--------|------|----------|
| `FloatGoal` | 在水中浮起 | `(mob)` |
| `RandomStrollGoal` | 随机游走 | `(mob, speed)` |
| `LookAtPlayerGoal` | 看向玩家 | `(mob, targetType, range)` |
| `RandomLookAroundGoal` | 随机环顾 | `(mob)` |

### 攻击与目标选择 {#attack}

| 目标类 | 描述 | 构造参数 |
|--------|------|----------|
| `MeleeAttackGoal` | 近战攻击 | `(mob, speed, followTarget)` |
| `RangedAttackGoal` | 远程攻击 | `(mob, speed, attackRadius, attackInterval)` |
| `HurtByTargetGoal` | 反击攻击者 | `(mob, ...allies)` |
| `NearestAttackableTargetGoal` | 攻击最近敌人 | `(mob, targetClass, mustSee)` |

### 回避与特殊目标 {#avoidance_goals}

| 目标类 | 描述 | 构造参数 |
|--------|------|----------|
| `AvoidEntityGoal` | 避开特定实体 | `(mob, entityType, distance, walkSpeed, sprintSpeed)` |
| `PanicGoal` | 受伤后逃跑 | `(mob, speed)` |

## 优先级管理 {#priority}

- **数值越小，优先级越高**
- 相同优先级目标会并行执行
- 高优先级目标会中断低优先级目标

```js
entity.goalSelector.addGoal(0, floatGoal);        // 最高优先级
entity.goalSelector.addGoal(1, panicGoal);        // 高优先级
entity.goalSelector.addGoal(2, meleeAttackGoal);  // 中优先级
entity.goalSelector.addGoal(7, randomStrollGoal); // 低优先级
entity.goalSelector.addGoal(8, lookAtPlayerGoal); // 最低优先级
```

## 实际应用示例 {#examples}

```js
EntityEvents.spawned(event => {
    const entity = event.entity;
    if (entity.type === "minecraft:zombie") {
        const FloatGoal = Java.loadClass("net.minecraft.world.entity.ai.goal.FloatGoal");
        const MeleeAttackGoal = Java.loadClass("net.minecraft.world.entity.ai.goal.MeleeAttackGoal");
        const RandomStrollGoal = Java.loadClass("net.minecraft.world.entity.ai.goal.RandomStrollGoal");
        const LookAtPlayerGoal = Java.loadClass("net.minecraft.world.entity.ai.goal.LookAtPlayerGoal");
        const HurtByTargetGoal = Java.loadClass("net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal");
        const NearestAttackableTargetGoal = Java.loadClass("net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal");
        const PlayerClass = Java.loadClass("net.minecraft.world.entity.player.Player");

        entity.goalSelector.removeAllGoals();
        entity.targetSelector.removeAllGoals();

        entity.goalSelector.addGoal(0, new FloatGoal(entity));
        entity.goalSelector.addGoal(3, new MeleeAttackGoal(entity, 1.0, true));
        entity.goalSelector.addGoal(7, new RandomStrollGoal(entity, 1.0));
        entity.goalSelector.addGoal(8, new LookAtPlayerGoal(entity, PlayerClass, 8.0));
        entity.targetSelector.addGoal(1, new HurtByTargetGoal(entity));
        entity.targetSelector.addGoal(2, new NearestAttackableTargetGoal(entity, PlayerClass, true));
        entity.customName = "定制僵尸";
    }
});
```

### 实体类型兼容性 {#compatibility}

```js
// 仅非玩家实体可添加 AI 目标
if (entity.type !== 'minecraft:player') {
    const goal = new SomeGoal(entity);
    entity.goalSelector.addGoal(priority, goal);
}

// 推荐类型判断优于 try-catch
const $PathfinderMob = Java.loadClass("net.minecraft.world.entity.PathfinderMob");
if (entity instanceof $PathfinderMob) {
    const navigationGoal = new SomeNavigationGoal(entity);
    entity.goalSelector.addGoal(priority, navigationGoal);
}
```
