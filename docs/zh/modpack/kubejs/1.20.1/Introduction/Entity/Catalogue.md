---
title: 实体系统目录
description: KubeJS 实体系统完整文档目录
tags:
    - KubeJS
    - Entity
    - Catalogue
    - Navigation
progress: 100
---

# {{ $frontmatter.title }}

## 概述 {#overview}

本目录提供了 KubeJS 实体系统所有相关文档的快速导航。实体系统是 Minecraft 中最复杂的组成部分之一，涵盖了从基础实体操作到高级 AI 行为的各个方面。

## 快速参考 {#quick-reference}

### 常用操作 {#common-operations}

```js
// 基础实体检查
entity.isPlayer(); // 是否为玩家
entity.isLiving(); // 是否为生物
entity.type; // 实体类型

// 位置与移动
entity.position(); // 获取位置向量
entity.setMotion(x, y, z); // 设置移动速度
entity.teleportTo(x, y, z); // 传送

// 生物实体操作
entity.health; // 当前生命值
entity.maxHealth; // 最大生命值
entity.potionEffects.add(); // 添加药水效果

// 玩家特有操作
player.tell(); // 发送消息
player.give(); // 给予物品
player.teleportTo(); // 传送
```

### 数据存储 {#data-storage}

```js
// 使用 persistentData（KubeJS 自定义系统）
entity.persistentData.putString(key, value);
entity.persistentData.getString(key);

// 实体标签（用于选择器）
entity.addTag("custom_tag");
entity.removeTag("custom_tag");
```

### AI 控制 {#ai-control}

```js
// 基础 AI 控制
mob.setNoAi(true); // 禁用 AI
mob.setTarget(entity); // 设置攻击目标

// 高级 AI 目标（需要 Java.loadClass）
const FloatGoal = Java.loadClass("net.minecraft.world.entity.ai.goal.FloatGoal");
mob.goalSelector.addGoal(0, new FloatGoal(mob));
```

## 常见问题解答 {#faq}

### 位置与移动相关 {#position-movement-faq}

**Q: Motion 和 DeltaMovement 有什么区别？**

A: 它们是同一概念的不同表示方法，Motion 是 KubeJS 简化的 API，DeltaMovement 是 Minecraft 原生 API。详见 [向量文档](./Vector#motion-system)。

**Q: 如何计算两个实体之间的距离？**

A: 使用 `entity1.position().distanceTo(entity2.position())` 或查看 [向量文档](./Vector) 了解更多距离计算方法。

**Q: 如何让实体向特定方向移动？**

A: 使用向量计算方向，然后设置 Motion。详细示例请参考 [向量文档](./Vector#common-use-cases)。

### 数据存储相关 {#data-storage-faq}

**Q: persistentData 和 NBT 有什么区别？**

A: persistentData 是 KubeJS 的自定义数据系统，不是 Minecraft 原生的 NBT。

**Q：为什么我的Nbt修改不起作用？**

A：KubeJS只允许使用mergeNbt()添加实体其已有的属性，例如当生物本就拥有`Sample`属性，就可以使用`mergeNbt({Sample:1})`来修改这个属性，但是无法新添加自定义的属性。

**Q: 如何存储自定义数据？**

A: 使用 `entity.persistentData.putString()`、`putInt()`、`putBoolean()` 等方法。