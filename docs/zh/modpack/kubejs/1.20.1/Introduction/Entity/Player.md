---
title: 玩家实体
description: Player 在 KubeJS 中的完整操作指南
tags:
  - KubeJS
  - Player
progress: 90
---

# {{ $frontmatter.title }}

## 概述 {#overview}

`Player` 是 `LivingEntity` 的子类，代表游戏中的玩家实体。

### 特殊属性 {#special-properties}

玩家实体相比其他生物实体具有以下独特特性：

- **背包系统**：包含主背包、快捷栏、装备槽、副手槽
- **经验系统**：等级、经验值、附魔等级
- **权限系统**：创造模式、飞行权限、操作权限
- **通信系统**：聊天、标题、动作栏消息
- **统计系统**：游戏时间、成就、数据追踪

## 类型注解和JSDoc {#type-annotations-jsdoc}

### ServerPlayer vs Player {#serverplayer-vs-player}

在服务端脚本中，某些方法需要使用 JSDoc 注解来获得 `ServerPlayer` 的完整功能：

```js
/**
 * ServerPlayer 专用方法需要明确类型注解
 * @param {Internal.ServerPlayer} player - 服务端玩家对象
 */
function handleServerPlayer(player) {
    // 这些方法需要 ServerPlayer 类型
    player.doCloseContainer();           // 关闭容器界面
    player.openMenu(menuProvider);     // 打开自定义菜单
    player.connection.disconnect("踢死你");    // 断开连接
    
    // 权限相关（ServerPlayer 专用）
    player.hasPermissions(permissionLevel);
    player.server.getProfilePermissions(player.gameProfile);
    
    // 服务端专用统计
    player.getStats();
    player.resetStat(statType);
}

// 在事件中的使用示例
PlayerEvents.loggedIn(event => {
    /** @type {Internal.ServerPlayer} */
    const serverPlayer = event.player;
    
    // 现在可以使用所有 ServerPlayer 方法
    serverPlayer.doCloseContainer();
});
```

### 客户端玩家限制 {#client-player-limitations}

```js
// 客户端脚本中的玩家对象功能有限
ClientEvents.loggedIn(event => {
    const clientPlayer = event.player; // 类型为 LocalPlayer
    
    // 可用方法有限，主要是显示相关
    clientPlayer.displayClientMessage(text, actionBar);
    clientPlayer.playSound(sound, volume, pitch);
});
```

## 玩家信息获取 {#player-information}

### 基础属性 {#basic-properties}

| 属性 | 方法 | 描述 |
|------|------|------|
| 玩家名称 | `player.username` | 获取玩家用户名 |
| 显示名称 | `player.displayName` | 获取显示名称 |
| UUID | `player.uuid` | 获取玩家唯一标识符 |
| 游戏模式 | `player.gameMode` | 获取当前游戏模式 |

### 状态检查 {#status-checks}

```js
// 检查玩家状态
if (player.isCreative()) {
    // 创造模式逻辑
}

if (player.isSpectator()) {
    // 观察者模式逻辑  
}

if (player.isSleeping()) {
    // 睡眠状态逻辑
}

// 在线状态检查
const isOnline = player.level.players().contains(player);
```

## 背包与物品管理 {#inventory-management}

### 背包操作 {#inventory-operations}

```js
// 基础背包操作
player.inventory.insertItem(slot, itemStack, simulate);
player.inventory.extractItem(slot, amount, simulate);
player.inventory.getStackInSlot(slot);
player.inventory.setStackInSlot(slot, itemStack);

// 快捷栏操作
player.inventory.selected; // 当前选中槽位
player.inventory.setItem(slot, itemStack);

// 装备槽操作
const helmet = player.getItemBySlot("head");
player.setItemSlot("head", Item.of("minecraft:diamond_helmet"));
```

### 物品查找与管理 {#item-management}

```js
// 查找物品
const foundSlot = player.inventory.find("minecraft:diamond");
const itemCount = player.inventory.count("minecraft:diamond");
const allItems = player.inventory.getAllItems();

// 给予物品
player.give(Item.of("minecraft:diamond", 10));
player.giveInHand(Item.of("minecraft:diamond_sword"));

// 清空操作
player.inventory.clear(); // 清空所有
player.inventory.clear("minecraft:dirt"); // 清空指定物品
```

## 经验与等级系统 {#experience-system}

### 经验管理 {#experience-management}

| 属性 | 描述 |
|------|------|
| `experienceLevel` | 当前等级 |
| `experienceProgress` | 当前等级进度 (0.0-1.0) |
| `totalExperience` | 总经验值 |

```js
// 经验操作
player.giveExperiencePoints(amount);  // 给予经验点
player.giveExperienceLevels(levels);  // 给予等级
player.setExperiencePoints(points);   // 设置经验点
player.setExperienceLevel(level);     // 设置等级

// 获取经验信息
const currentLevel = player.experienceLevel;
const totalExp = player.totalExperience;
const progress = player.experienceProgress;
```

### 附魔等级 {#enchantment-levels}

```js
// 附魔相关
const enchantmentSeed = player.enchantmentSeed;
const availableLevels = player.experienceLevel;

// 重置附魔种子
player.enchantmentSeed = Math.floor(Math.random() * 1000000);
```

## 通信 {#communication}

### 消息发送 {#message-sending}

```js
// 聊天消息
player.tell("§a欢迎来到服务器！");
player.tell(Component.literal("欢迎消息"));

// 客户端消息
player.displayClientMessage(Component.literal("状态更新"), false);

// 动作栏消息  
player.displayClientMessage(Component.literal("动作栏信息"), true);
```

### 声音播放 {#sound-playing}

```js
// 播放系统声音
player.playNotifySound("minecraft:entity.experience_orb.pickup", 1.0, 1.0);

// 播放自定义声音
player.playSound("minecraft:entity.villager.trade", 1.0, 1.2);
```

## 位置与传送 {#position-teleport}

### 位置获取 {#position-getting}

```js
// 当前位置
const position = {
    x: player.x,
    y: player.y, 
    z: player.z,
    dimension: player.level.dimension
};

// 方块位置
const blockPos = player.blockPosition();
const biome = player.level.getBiome(blockPos);
```

### 传送操作 {#teleport-operations}

```js
// 同维度传送
player.teleportTo(x, y, z);

// 跨维度传送
player.teleportTo("minecraft:the_nether", x, y, z, yaw, pitch);
player.teleportTo("minecraft:the_end", x, y, z, yaw, pitch);

// 传送到其他实体
const location = entity.blockPosition();
player.teleportTo(location);

// 例如想要传送到其他玩家的话
const otherPlayer = player.server.getPlayer("PlayerName");
// 判断玩家是否存在
if (otherPlayer) {
    const location = otherPlayer.blockPosition();
    const dimension = otherPlayer.level.dimension;
    const yaw = otherPlayer.yRot;
    const pitch = otherPlayer.xRot;
    // 获取必要信息
    player.teleportTo(dimension, location.x, location.y, location.z, yaw, pitch);

}
```

## 权限与游戏模式 {#permissions-gamemode}

### 游戏模式管理 {#gamemode-management}

```js
// 设置游戏模式
player.setGameMode("creative");
player.setGameMode("survival"); 
player.setGameMode("adventure");
player.setGameMode("spectator");

// 检查当前模式
const currentMode = player.gameMode;
const isCreative = player.isCreative();
const isSpectator = player.isSpectator();
```

### 权限检查 {#permission-checking}

```js
/**
 * 权限检查需要 ServerPlayer 类型
 * @param {Internal.ServerPlayer} player
 */
function checkPermissions(player) {
    // 检查操作权限等级
    const hasOpLevel1 = player.hasPermissions(1);
    const hasOpLevel2 = player.hasPermissions(2);
    const hasOpLevel3 = player.hasPermissions(3);
    const hasOpLevel4 = player.hasPermissions(4);
    
    return {
        canUseBasicCommands: hasOpLevel1,
        canModifyWorld: hasOpLevel2,
        canBanPlayers: hasOpLevel3,
        hasFullControl: hasOpLevel4
    };
}
```

### 能力管理 {#abilities-management}

```js
// 飞行能力
player.abilities.mayfly = true;      // 允许飞行
player.abilities.flying = true;      // 当前飞行状态

// 其他能力
player.abilities.invulnerable = true;  // 无敌模式
player.abilities.instabuild = true;    // 瞬间破坏

// 通知客户端能力更新
// 一般不使用似乎也有效
player.onUpdateAbilities();
```

::: v-info 实际上
一般通过player.abilities.invulnerable来判断玩家是否为创造模式。
:::

## 统计与数据 {#statistics-data}

### 游戏统计 {#game-statistics}

```js
/**
 * 获取玩家统计数据（需要 ServerPlayer）
 * @param {Internal.ServerPlayer} player
 */
function getPlayerStats(player) {
    const stats = player.getStats();
    
    // 常用统计类型
    const playTime = stats.getPlayTime()
    const deaths = stats.getDeaths();
    const mobKills = stats.getMobKills();
    const damageDealt = stats.getDamageDealt();
    
    return { playTime, deaths, mobKills, damageDealt };
}
```

### 数据持久化 {#data-persistence}

```js
// 使用 persistentData 存储玩家数据
player.persistentData.putString("guild", "warriors");
player.persistentData.putInt("coins", 1000);
player.persistentData.putBoolean("vip_status", true);

// 读取持久化数据
const guild = player.persistentData.getString("guild");
const coins = player.persistentData.getInt("coins");
const isVip = player.persistentData.getBoolean("vip_status");

// 复合数据存储
const achievements = player.persistentData.getCompound("achievements");
achievements.putBoolean("first_kill", true);
achievements.putLong("total_playtime", 3600000);
player.persistentData.put("achievements", achievements);
```

## 实用方法集合 {#utility-methods}

### 世界交互 {#world-interaction}

```js
// 获取玩家看向的方块
const hitResult = player.pick(5.0, 0.0, false);
if (hitResult.type === "block") {
    const targetBlock = player.level.getBlock(hitResult.blockPos);
    console.log(`玩家正在看向: ${targetBlock.id}`);
}

// 当前，KubeJS 提供了RayTraceJS来更方便地获取玩家正在看的物品
const tryTrace = entity.rayTrace(10.0, false) // param 1 距离，param 2 是否检测流体
    rayTrace.block // 正在看着的方块，可以为null
    rayTrace.entity // 正在看着的实体，可以为null
    rayTrace.type // 如果block 与 entity都为null，type为miss，否则为entity或block之一

// 获取玩家周围的实体
const nearbyEntities = player.level.getEntitiesWithin( AABB.of(
    player.x - 5, player.y - 5, player.z - 5,
    player.x + 5, player.y + 5, player.z + 5)
);
```

## 实际应用示例 {#practical-examples}

### 玩家加入处理 {#player-join-handling}

```js
PlayerEvents.loggedIn(event => {
    /** @type {Internal.ServerPlayer} */
    const player = event.player;
    
    // 欢迎消息
    player.tell("§a欢迎回到服务器！");
    
    // 检查首次加入
    if (!player.persistentData.contains("first_join")) {
        player.persistentData.putBoolean("first_join", true);
        player.persistentData.putLong("join_time", Date.now());
        
        // 给予新手物品
        player.give("minecraft:bread", 10);
        player.give("minecraft:wooden_sword");
        
        player.tell("§e这是你第一次加入服务器，已为你准备了一些物品！");
    }
    
    // 更新最后登录时间
    player.persistentData.putLong("last_login", Date.now());
});
```