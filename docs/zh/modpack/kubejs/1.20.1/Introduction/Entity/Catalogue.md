---
title: 实体系统目录
description: KubeJS 实体系统相关文档导航与索引
progress: 70
tags:
  - KubeJS
  - Entity
---

# {{ $frontmatter.title }}

## 概述 {#overview}

本页为 KubeJS 实体系统相关文档的导航与索引，便于查阅各类实体操作、AI、属性、事件等内容。

## 分类 {#categories}

## 用法示例 {#examples}

## 文档导航 {#docs}

- [属性 Attribute](./Attribute.md)
- [AI 目标 Goal](./Goal.md)
- [AI 大脑 Brain](./Brain.md)
- [方法 Methods](./Methods.md)
- [生物实体 LivingEntity](./LivingEntity.md)
- [生物 Mob](./Mob.md)
- [玩家 Player](./Player.md)
- [药水效果 PotionEffects](./PotionEffects.md)
- [向量 Vector](./Vector.md)
- [生成/刷怪 Spawn](./Spawn.md)

## FAQ / 未完成 {#faq}

::: alert {"type": "error", "title": "警告"}
部分条目内容尚未完善，后续将持续补充。
:::

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