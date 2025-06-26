---
title: 怪物实体
description: Mob 在 KubeJS 中的完整操作指南
tags:
  - KubeJS
  - Mob
  - AI
  - Pathfinding
  - Behavior
progress: 90
---

# {{ $frontmatter.title }}

## 概述 {#overview}

`Mob` 是 `LivingEntity` 的子类，代表游戏中所有具有人工智能的实体。基于 [Forge 1.20.1 JavaDocs](https://mcstreetguy.github.io/ForgeJavaDocs/1.20.1-47.1.0/index.html)，Mob 类提供了 AI 目标系统、基础行为控制、社交行为等功能。

### 继承关系 {#inheritance-hierarchy}

```
LivingEntity
└── Mob
    ├── PathfinderMob (可寻路的生物)
    │   ├── AgeableMob (可成长的生物)
    │   │   ├── Animal (动物)
    │   │   └── AbstractVillager (村民类)
    │   └── WaterAnimal (水生动物)
    └── Monster (怪物)
        ├── Enemy (敌对生物)
        └── FlyingMob (飞行生物)
```

## AI 目标系统 {#goal-system}

### 目标选择器类型 {#goal-selector-types}

Mob 的 AI 行为由目标系统控制，分为两个主要部分：

- **目标选择器** (`goalSelector`)：控制日常行为
- **攻击目标选择器** (`targetSelector`)：控制攻击目标选择

### 基础 AI 控制 {#basic-ai-control}

```js
// AI 状态控制
mob.setNoAi(true);           // 禁用 AI
mob.setNoAi(false);          // 启用 AI
mob.isNoAi();                // 检查 AI 状态

// 攻击目标管理
mob.setTarget(targetEntity); // 设置攻击目标
mob.getTarget();             // 获取当前目标

// 敌对状态检查
mob.isAggressive();          // 是否处于攻击状态
mob.canAttack(targetEntity); // 是否可以攻击目标
```

### 目标操作 {#goal-operations}

```js
// 移除所有目标
mob.goalSelector.removeAllGoals();
mob.targetSelector.removeAllGoals();

// 添加目标需要使用 Java.loadClass（参见 Goal 文档）
const FloatGoal = Java.loadClass("net.minecraft.world.entity.ai.goal.FloatGoal");
mob.goalSelector.addGoal(0, new FloatGoal(mob));
```

详细的目标操作请参考 [AI目标系统文档](./Goal)。

## 寻路系统限制 {#pathfinding-limitations}

### KubeJS 寻路限制 {#kubejs-pathfinding-limitations}

::: v-warning 寻路功能限制
KubeJS 核心模组不支持直接的寻路控制，以下方法在 KubeJS 中不可用：

```js
// ❌ 这些方法在 KubeJS 中不工作
mob.navigation.moveTo(x, y, z, speed);
mob.navigation.moveTo(targetEntity, speed);
mob.navigation.stop();
mob.navigation.isDone();
mob.navigation.isStuck();
```
:::

### 可用的替代方案 {#available-alternatives}

```js
// ✅ 可用的替代方案
mob.teleportTo(x, y, z);     // 直接传送
mob.setPos(x, y, z);         // 设置位置

// 通过速度推动实体
mob.deltaMovement = { x: 0.5, y: 0.0, z: 0.0 };
mob.push(0.5, 0.0, 0.0);
```

## 生物属性管理 {#mob-attributes}

### 移动相关属性 {#movement-attributes}

| 属性 | 描述 | 默认值 |
|------|------|--------|
| `generic.movement_speed` | 移动速度 | 0.25 |
| `generic.flying_speed` | 飞行速度 | 0.4 |
| `generic.follow_range` | 跟随范围 | 16.0 |

### 攻击属性 {#attack-attributes}

| 属性 | 描述 |
|------|------|
| `generic.attack_damage` | 攻击伤害 |
| `generic.attack_speed` | 攻击速度 |
| `generic.attack_knockback` | 攻击击退 |

### 属性操作 {#attribute-operations}

```js
// 属性修改示例
const speedAttr = mob.getAttribute('minecraft:generic.movement_speed');
speedAttr.baseValue = 0.5;

const damageAttr = mob.getAttribute('minecraft:generic.attack_damage');
damageAttr.baseValue = 10;
```

## 社交行为系统 {#social-behavior}

### 敌对关系管理 {#hostility-management}

```js
// 攻击目标设置
mob.setTarget(targetEntity);    // 设置攻击目标
mob.getTarget();                // 获取当前目标

// 敌对状态检查
mob.isAggressive();             // 是否处于攻击状态
mob.canAttack(targetEntity);    // 是否可以攻击目标
```

### 群体行为特性 {#group-behavior-characteristics}

某些生物具有内置的群体行为特性：

- **狼群行为**：狼会协同攻击
- **僵尸群聚**：僵尸会召唤援军
- **村民恐慌**：村民会相互通知危险

## 特殊行为控制 {#special-behaviors}

### 驯服系统 {#taming-system}

```js
// 驯服状态（仅适用于可驯服生物）
const isTamed = mob.isTame();              // 是否已驯服
const owner = mob.getOwner();              // 获取主人
mob.setOwner(player);                      // 设置主人
```

### 繁殖系统 {#breeding-system}

```js
// 繁殖相关（仅适用于动物）
const canMate = mob.canMate(otherMob);     // 是否可以交配
const age = mob.getAge();                  // 获取年龄
mob.setAge(age);                           // 设置年龄
const isBaby = mob.isBaby();               // 是否为幼体
```

## 持久化数据 {#persistent-data}

### 数据存储 {#data-storage}

```js
// persistentData 操作
mob.persistentData.putString("mob_type", "elite");
mob.persistentData.putInt("level", 10);
mob.persistentData.putBoolean("is_boss", true);

// 读取数据
const mobType = mob.persistentData.getString("mob_type");
const level = mob.persistentData.getInt("level");
const isBoss = mob.persistentData.getBoolean("is_boss");
```

### 自定义名称与标签 {#custom-names-tags}

```js
// 名称设置
mob.setCustomName("§c精英怪物");        // 设置自定义名称
const customName = mob.getCustomName();  // 获取自定义名称
mob.setCustomNameVisible(true);          // 显示名称

// 实体标签（用于选择器，不是数据存储）
mob.addTag('elite_mob');                 // 添加标签
mob.removeTag('elite_mob');              // 移除标签
const tags = mob.getTags();              // 获取所有标签
```

## 声音与效果 {#sounds-effects}

### 声音播放 {#sound-playing}

```js
// 播放生物音效
mob.playSound("minecraft:entity.zombie.ambient", 1.0, 1.0);

// 获取生物特定音效
const ambientSound = mob.getAmbientSound();    // 环境音效
const hurtSound = mob.getHurtSound();          // 受伤音效
const deathSound = mob.getDeathSound();        // 死亡音效
```

### 粒子效果 {#particle-effects}

```js
// 在生物周围产生粒子效果
mob.level.addParticle(
    "minecraft:flame", 
    mob.x, mob.y + 1, mob.z, 
    0, 0.1, 0
);
```

## 实际应用示例 {#practical-examples}

### 生物强化系统 {#mob-enhancement-system}

```js
EntityEvents.spawned(event => {
    const entity = event.entity;
    
    if (entity.type === 'minecraft:zombie') {
        // 增强僵尸移动速度
        const speedAttr = entity.getAttribute('minecraft:generic.movement_speed');
        speedAttr.baseValue = 0.4;
        
        // 增加生命值
        const healthAttr = entity.getAttribute('minecraft:generic.max_health');
        healthAttr.baseValue = 40.0;
        entity.health = 40.0;
        
        // 设置自定义名称
        entity.setCustomName('§c强化僵尸');
        entity.setCustomNameVisible(true);
        
        // 标记为精英怪物
        entity.persistentData.putBoolean("is_elite", true);
    }
});
```

### 驯服动物行为监控 {#tamed-animal-monitoring}

```js
EntityEvents.hurt(event => {
    const entity = event.entity;
    
    if (entity.type === 'minecraft:wolf' && entity.isTame()) {
        const owner = entity.getOwner();
        if (owner && owner.isPlayer()) {
            owner.tell(`§e你的狼 ${entity.customName || "无名"} 受到了攻击！`);
            
            // 给予狼再生效果
            entity.potionEffects.add('minecraft:regeneration', 100, 1);
        }
    }
});
```

### 群体行为模拟 {#group-behavior-simulation}

```js
EntityEvents.death(event => {
    const entity = event.entity;
    
    if (entity.type === 'minecraft:bee') {
        // 蜜蜂死亡时，附近的蜜蜂变得愤怒
        const nearbyBees = entity.level.getEntitiesWithinAABB(
            entity.x - 10, entity.y - 5, entity.z - 10,
            entity.x + 10, entity.y + 5, entity.z + 10
        ).filter(e => e.type === 'minecraft:bee');
        
        nearbyBees.forEach(bee => {
            if (event.source.actual) {
                bee.setTarget(event.source.actual);
                bee.persistentData.putBoolean("angry", true);
            }
        });
    }
});
```

## 性能优化建议 {#performance-optimization}

### AI 优化 {#ai-optimization}

- **限制目标搜索范围**：避免过大的 `follow_range` 属性
- **合理使用 AI 目标**：移除不必要的行为目标
- **减少事件频率**：避免在每 tick 都进行复杂的 AI 计算

### 实体管理 {#entity-management}

- **实体数量控制**：定期清理不必要的实体
- **区块加载优化**：合理管理实体所在区块
- **内存使用**：避免在实体上存储大量 persistentData

### 实体清理 {#entity-cleanup}

```js
// 定期清理特定类型的实体
ServerEvents.tick(event => {
    // 每5分钟执行一次清理
    if (event.server.tickCount % 6000 === 0) {
        event.server.getAllLevels().forEach(level => {
            const entities = level.entities.filter(entity => 
                entity.type === 'minecraft:item' && 
                entity.age > 1200 // 1分钟以上的物品
            );
            
            entities.forEach(entity => entity.discard());
        });
    }
});
```