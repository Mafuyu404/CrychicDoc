---
title: 生物实体
description: LivingEntity 类的属性、方法、事件与 KubeJS 用法
progress: 80
tags:
  - KubeJS
  - Entity
  - LivingEntity
hidden: false
priority: 300
---

<llm-only>
LivingEntity 是所有带生命值实体的基类，KubeJS 中要点包括：
1. 继承自 Entity；
2. 生命值管理：`getHealth`、`setHealth`、`heal`、`hurt`；
3. 药水效果：`addEffect`、`removeEffect`、`hasEffect`；
4. 装备：`getItemBySlot`、`setItemSlot`；
5. 属性：`getAttribute`、`modifyAttribute`；
6. 许多方法仅在服务端有效，建议查阅 Forge JavaDocs。
</llm-only>

# LivingEntity

## 概述 {#overview}

LivingEntity 是 Minecraft 所有"有生命实体"的基类，涵盖玩家、怪物、动物等。其扩展自 Entity，提供了生命值、药水效果、AI、装备栏等核心能力。

## 机制说明 {#mechanism}

- 继承自 `Entity`，扩展了生命值、AI、药水、装备、饥饿、属性等系统。
- 拥有 `AttributeMap`（属性）、`MobEffectMap`（药水）、装备栏等。
- 支持 `tick` 自动更新、事件驱动、`NBT` 持久化。
- 常用方法：`getHealth/setHealth`、`addEffect`、`getAttribute`、`attack`、`hurt`、`heal`、`isAlive`、`isSleeping` 等。

## 典型用法与脚本接口 {#usage-examples}

```js
// 获取生命值
const hp = entity.getHealth();
// 设置生命值
entity.setHealth(10);
// 添加药水效果
entity.addEffect('minecraft:regeneration', 200, 1);
// 检查是否为玩家
if (entity.isPlayer()) { /* ... */ }
```
