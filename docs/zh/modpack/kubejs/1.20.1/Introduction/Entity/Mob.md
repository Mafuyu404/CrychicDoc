---
title: Mob（生物）
description: Mob 类的属性、AI、目标选择与 KubeJS 用法
progress: 80
tags:
  - KubeJS
  - Entity
  - Mob
---

# Mob

## 概述 {#overview}

Mob 是 Minecraft 中所有具有 AI 行为的生物实体基类，继承自 LivingEntity。涵盖怪物、动物、中立生物等，支持 AI 目标、目标选择器、行为树等高级机制。

## 机制说明 {#mechanism}

- 继承自 LivingEntity，扩展了 AI 目标（goalSelector）、目标选择器（targetSelector）、大脑（Brain）等。
- 支持 AI 行为树、目标优先级、攻击/防御/移动等多种行为。
- 常用方法：addGoal、addTargetGoal、setTarget、getTarget、isAggressive、canAttack、wander、lookAt 等。
- 支持事件驱动、tick 自动更新、NBT 持久化。

## 常用方法 {#common_methods}

```js
// 添加 AI 目标
const MeleeAttackGoal = Java.loadClass('net.minecraft.world.entity.ai.goal.MeleeAttackGoal');
mob.goalSelector.addGoal(0, new MeleeAttackGoal(mob, 1.0, true));

// 设置攻击目标
mob.setTarget(targetEntity);
const currentTarget = mob.getTarget();

// 判断是否为攻击性生物
if (mob.isAggressive()) { /* ... */ }
```

## 注意事项 {#notes}

- 仅 Mob 及其子类支持 AI 目标、目标选择等操作。