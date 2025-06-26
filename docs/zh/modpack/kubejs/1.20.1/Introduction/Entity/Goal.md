---
title: Goal
description: 操作Goal
tags:
    - KubeJS
    - Entity
    - AI
    - Goal
progress: 90
---

# {{ $frontmatter.title }}

## 概述 {#overview}

AI 目标系统是 Minecraft 实体 AI 的核心，定义了实体的行为优先级和决策逻辑。

```
Entity (实体)
└── Mob (生物)
    ├── goalSelector (行为目标选择器)
    │   ├── FloatGoal (浮水目标)
    │   ├── RandomStrollGoal (随机游走)
    │   └── LookAtPlayerGoal (看向玩家)
    └── targetSelector (攻击目标选择器)
        ├── HurtByTargetGoal (反击目标)
        └── NearestAttackableTargetGoal (最近敌人)
```

## 目标操作 {#goal-operations}

### 添加目标 {#adding-goals}

```js
// 加载常用目标类
const FloatGoal = Java.loadClass("net.minecraft.world.entity.ai.goal.FloatGoal");
const MeleeAttackGoal = Java.loadClass("net.minecraft.world.entity.ai.goal.MeleeAttackGoal");
const RandomStrollGoal = Java.loadClass("net.minecraft.world.entity.ai.goal.RandomStrollGoal");
const LookAtPlayerGoal = Java.loadClass("net.minecraft.world.entity.ai.goal.LookAtPlayerGoal");

// 添加行为目标
mob.goalSelector.addGoal(0, new FloatGoal(mob));
mob.goalSelector.addGoal(3, new MeleeAttackGoal(mob, 1.0, true));
mob.goalSelector.addGoal(7, new RandomStrollGoal(mob, 1.0));
```

### 移除目标 {#removing-goals}

```js
// 移除所有目标
mob.goalSelector.removeAllGoals();
mob.targetSelector.removeAllGoals();

// 基础 AI 控制
mob.setTarget(targetEntity); // 设置攻击目标
mob.getTarget(); // 获取当前目标
mob.setNoAi(true); // 禁用 AI
mob.setNoAi(false); // 启用 AI
```

## 常用目标类型 {#common-goal-types}

### 基础行为目标 {#basic-behavior-goals}

| 目标类 | 描述 | 构造参数 |
|--------|------|----------|
| `FloatGoal` | 在水中浮起 | `(mob)` |
| `RandomStrollGoal` | 随机游走 | `(mob, speed)` |
| `LookAtPlayerGoal` | 看向玩家 | `(mob, targetType, range)` |
| `RandomLookAroundGoal` | 随机环顾 | `(mob)` |

### 攻击目标 {#attack-goals}

| 目标类 | 描述 | 构造参数 |
|--------|------|----------|
| `MeleeAttackGoal` | 近战攻击 | `(mob, speed, followTarget)` |
| `RangedAttackGoal` | 远程攻击 | `(mob, speed, attackRadius, attackInterval)` |
| `HurtByTargetGoal` | 反击攻击者 | `(mob, ...allies)` |
| `NearestAttackableTargetGoal` | 攻击最近敌人 | `(mob, targetClass, mustSee)` |

### 回避目标 {#avoidance-goals}

| 目标类 | 描述 | 构造参数 |
|--------|------|----------|
| `AvoidEntityGoal` | 避开特定实体 | `(mob, entityType, distance, walkSpeed, sprintSpeed)` |
| `PanicGoal` | 受伤后逃跑 | `(mob, speed)` |

## 实际应用示例 {#practical-examples}

```js
EntityEvents.spawned(event => {
    const entity = event.entity;

    if (entity.type === "minecraft:zombie") {
        // 加载所需的目标类
        const FloatGoal = Java.loadClass("net.minecraft.world.entity.ai.goal.FloatGoal");
        const MeleeAttackGoal = Java.loadClass("net.minecraft.world.entity.ai.goal.MeleeAttackGoal");
        const RandomStrollGoal = Java.loadClass("net.minecraft.world.entity.ai.goal.RandomStrollGoal");
        const LookAtPlayerGoal = Java.loadClass("net.minecraft.world.entity.ai.goal.LookAtPlayerGoal");
        const HurtByTargetGoal = Java.loadClass("net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal");
        const NearestAttackableTargetGoal = Java.loadClass("net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal");
        const PlayerClass = Java.loadClass("net.minecraft.world.entity.player.Player");

        // 清除默认目标
        entity.goalSelector.removeAllGoals();
        entity.targetSelector.removeAllGoals();

        // 添加基础目标
        entity.goalSelector.addGoal(0, new FloatGoal(entity));
        entity.goalSelector.addGoal(3, new MeleeAttackGoal(entity, 1.0, true));
        entity.goalSelector.addGoal(7, new RandomStrollGoal(entity, 1.0));
        entity.goalSelector.addGoal(8, new LookAtPlayerGoal(entity, PlayerClass, 8.0));

        // 添加攻击目标
        entity.targetSelector.addGoal(1, new HurtByTargetGoal(entity));
        entity.targetSelector.addGoal(2, new NearestAttackableTargetGoal(entity, PlayerClass, true));
        
        entity.customName = "定制僵尸";
    }
});
```

## 优先级管理 {#priority-management}

### 优先级规则 {#priority-rules}

- **数值越小，优先级越高**
- **相同优先级的目标会并行执行**
- **高优先级目标会中断低优先级目标**

```js
// 优先级示例
entity.goalSelector.addGoal(0, floatGoal);        // 最高优先级：浮水
entity.goalSelector.addGoal(1, panicGoal);        // 高优先级：逃跑
entity.goalSelector.addGoal(2, meleeAttackGoal);  // 中优先级：攻击
entity.goalSelector.addGoal(7, randomStrollGoal); // 低优先级：游走
entity.goalSelector.addGoal(8, lookAtPlayerGoal); // 最低优先级：看向玩家
```

## 限制和注意事项 {#limitations-and-notes}

### KubeJS 限制 {#kubejs-limitations}

1. **需要 Java.loadClass 语法**：不能直接使用字符串或类名
2. **需要 new 关键字**：必须实例化目标对象
3. **类型检查**：某些目标仅适用于特定实体类型
4. **依赖关系**：某些目标需要特定的实体能力

### 实体类型兼容性 {#entity-type-compatibility}

```js
// 检查实体类型兼容性
if (entity.type !== 'minecraft:player') {
    // 只有非玩家实体才能使用 AI 目标
    const goal = new SomeGoal(entity);
    entity.goalSelector.addGoal(priority, goal);
    // 如果你担心Goal也许不适用于这个实体，可以使用Try catch
    try {
        const goal = new SomeGoal(entity);
        entity.goalSelector.addGoal(priority, goal);
    } catch (Exception ignored) {}
}

//当前，比起Try catch，使用类型判断永远是最优的解决方案

// PathfinderMob 特定的目标
const $PathfinderMob = Java.loadClass("net.minecraft.world.entity.PathfinderMob");
if (entity instanceof $PathfinderMob) {
    // 某些目标只适用于 PathfinderMob
    const navigationGoal = new SomeNavigationGoal(entity);
    entity.goalSelector.addGoal(priority, navigationGoal);
}
```