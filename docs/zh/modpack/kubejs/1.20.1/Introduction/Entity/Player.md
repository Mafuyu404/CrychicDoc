---
title: 玩家实体
description: Player 类的属性、方法、事件与 KubeJS 用法
progress: 80
tags:
  - KubeJS
  - Entity
  - Player
hidden: false
priority: 500
---

# Player

## 概述 {#overview}

Player 是 Minecraft 中代表玩家本体的实体类，继承自 LivingEntity。拥有独特的能力系统、背包、经验、游戏模式等，支持丰富的脚本操作。

## 机制说明 {#mechanism}

- 继承自 LivingEntity，扩展了能力（abilities）、背包（inventory）、经验（experience）、游戏模式（gameMode）、成就（advancements）等系统。
- 支持 tick 自动更新、事件驱动、NBT 持久化。
- 常用方法：getName、getUuid、getAbilities、getInventory、addExperience、setGameMode、sendMessage、isCreative、isSpectator、isSleeping 等。

## 常用方法 {#common_methods}

```js
// 获取玩家名称和 UUID
const name = player.getName();
// 如果要获取可以直接使用的名字
const name = plaer.username;
const uuid = player.getUuid();

// 发送消息
player.sendMessage('Hello, world!');

// 检查创造/旁观模式
if (player.isCreative()) { /* ... */ }
if (player.isSpectator()) { /* ... */ }

// 添加经验
player.addExperience(100);

// 获取背包内容
const inventory = player.getInventory();
```

## 注意事项 {#notes}

- 仅 Player 实体支持能力、背包、经验、游戏模式等操作。
- 某些方法仅在服务端/特定事件中可用，注意兼容性。
- 详细类型与参数建议参考 [Forge JavaDocs](https://mcstreetguy.github.io/ForgeJavaDocs/1.20.1-47.1.0/index.html)。

## 进阶说明 {#advanced}

- Player 是 KubeJS 玩家脚本开发的核心，推荐结合原版源码与 Forge 文档深入研究。
- 相关子类：ServerPlayer、AbstractClientPlayer 等。
