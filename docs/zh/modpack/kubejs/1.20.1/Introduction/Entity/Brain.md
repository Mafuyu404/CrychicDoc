---
title: Brain（AI 大脑）
description: 实体 AI Brain 系统机制、KubeJS 支持现状与用法
progress: 60
tags:
  - KubeJS
  - Entity
  - Brain
  - AI
---

# Brain {#Brain}

## 概述 {#overview}

Brain 系统是 Minecraft 1.16+ 实体 AI 的核心，负责管理实体的行为决策、记忆模块（MemoryModule）、行为树（Behavior）等。通过 Brain，实体可实现复杂的 AI 行为、状态切换和记忆管理。

## 机制说明 {#mechanism}

- 每个 Mob 拥有一个 Brain 实例，包含多个 MemoryModule（记忆模块）和 Behavior（行为节点）。
- MemoryModule 用于存储实体的短期/长期记忆（如目标、路径、冷却等）。
- Behavior 行为树定义实体在不同状态下的决策逻辑。
- Brain 支持 tick 自动更新、事件驱动、状态切换等。

## KubeJS 支持与用法 {#kubejs_support}

::: alert {"type": "info", "title": "研究中"}
KubeJS 对 Brain/Memory/Behavior 的支持有限，部分 API 仍在调研与开发中。常见用法包括：
- 通过 Java 反射访问 Brain、MemoryModule、Behavior 类
- 读取/写入实体记忆（如目标、冷却、路径）
- 事件脚本中动态调整实体 AI 状态
:::

### 示例：读取/写入记忆 {#memory_example}

```js
const $MemoryModuleType = Java.loadClass('net.minecraft.world.entity.ai.memory.MemoryModuleType');
const brain = mob.getBrain();
const walkTarget = brain.getMemory($MemoryModuleType.WALK_TARGET);
brain.setMemory($MemoryModuleType.WALK_TARGET, newTarget);
brain.eraseMemory($MemoryModuleType.WALK_TARGET, newTarget);
```