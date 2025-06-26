---
title: 实体常用方法
description: KubeJS 实体 API 常用方法分类与用法参考
progress: 80
tags:
  - KubeJS
  - Entity
  - Methods
---

# 实体常用方法

## 概述 {#overview}

本页汇总 KubeJS 实体 API 的常用方法，涵盖属性、AI、状态、装备、数据等操作，便于查阅和脚本开发。

## 方法分类与说明 {#categories}

### 属性相关 {#attribute}
- `getAttribute(name)`：获取属性实例
- `modifyAttribute(name, id, amount, operation)`：添加属性修饰符
- `removeAttributeModifier(name, id)`：移除属性修饰符

### AI 相关 {#ai}
- `addGoal(priority, goal)`：添加 AI 目标
- `setTarget(entity)`：设置攻击目标
- `getTarget()`：获取当前目标

### 状态相关 {#status}
- `getHealth()` / `setHealth(value)`：获取/设置生命值
- `isAlive()` / `isDeadOrDying()`：生存状态判断
- `addEffect(effect, duration, amplifier)`：添加药水效果
- `removeEffect(effect)`：移除药水效果
- `hasEffect(effect)`：检测药水效果

### 装备相关 {#equipment}
- `getItemBySlot(slot)`：获取指定装备位物品
- `setItemSlot(slot, item)`：设置装备位物品
- `mainHandItem` / `offHandItem`：主手/副手物品

### 数据与持久化 {#data}
- `persistentData`：自定义数据存储
- `addTag(tag)` / `removeTag(tag)`：添加/移除标签

## 用法示例 {#examples}

```js
// 获取并修改属性
const attr = entity.getAttribute('generic.max_health');
attr.setBaseValue(40);
entity.modifyAttribute('generic.max_health', 'kubejs:bonus', 8, 'addition');

// 添加 AI 目标
const FloatGoal = Java.loadClass('net.minecraft.world.entity.ai.goal.FloatGoal');
entity.goalSelector.addGoal(0, new FloatGoal(entity));

// 状态与药水
entity.setHealth(10);
entity.addEffect('minecraft:regeneration', 200, 1);

// 装备操作
entity.setItemSlot('head', Item.of('minecraft:diamond_helmet'));

// 数据存储
entity.persistentData.putString('role', 'boss');
```

## 注意事项 {#notes}

- 某些方法仅在服务端/特定事件中可用，注意兼容性。
- 详细类型与参数建议参考 [Forge JavaDocs](https://mcstreetguy.github.io/ForgeJavaDocs/1.20.1-47.1.0/index.html)。

## 基础 Entity 方法 {#basic_entity_methods}

### 实体信息获取 {#entity_info}

| 方法               | 参数 | 返回类型        | 描述                 |
| ------------------ | ---- | --------------- | -------------------- |
| `getType()`        | -    | `string`        | 获取实体类型 ID      |
| `getId()`          | -    | `number`        | 获取实体数字 ID      |
| `getStringUuid()`  | -    | `string`        | 获取实体 UUID 字符串 |
| `getUuid()`        | -    | `Internal.UUID` | 获取实体 UUID 对象   |
| `getName()`        | -    | `Component`     | 获取实体名称组件     |
| `getDisplayName()` | -    | `Component`     | 获取显示名称         |
| `getCustomName()`  | -    | `Component`     | 获取自定义名称       |
| `hasCustomName()`  | -    | `boolean`       | 是否有自定义名称     |

### 位置与坐标 {#position}

> [!NOTE] 详细信息
> 关于向量、坐标系统和移动机制的详细说明，请参阅 [向量与坐标文档](./Vector)

| 方法                           | 参数     | 返回类型   | 描述             |
| ------------------------------ | -------- | ---------- | ---------------- |
| `getX()`                       | -        | `number`   | 获取 X 坐标      |
| `getY()`                       | -        | `number`   | 获取 Y 坐标      |
| `getZ()`                       | -        | `number`   | 获取 Z 坐标      |
| `blockPosition()`              | -        | `BlockPos` | 获取方块位置     |
| `position()`                   | -        | `Vec3d`    | 获取精确位置向量 |
| `getEyePosition()`             | -        | `Vec3d`    | 获取眼部位置     |

### 朝向与旋转 {#rotation}

| 方法                          | 参数     | 返回类型 | 描述         |
| ----------------------------- | -------- | -------- | ------------ |
| `getYaw()`                    | -        | `number` | 获取偏航角   |
| `getPitch()`                  | -        | `number` | 获取俯仰角   |
| `getYHeadRot()`               | -        | `number` | 获取头部旋转 |
| `setYaw(arg0: number)`        | `number` | `void`   | 设置偏航角   |
| `setPitch(arg0: number)`      | `number` | `void`   | 设置俯仰角   |
| `setYHeadRot(arg0: number)`   | `number` | `void`   | 设置头部旋转 |
| `getLookAngle()`              | -        | `Vec3d`  | 获取朝向向量 |

### 移动与传送

| 方法                                     | 参数                                                       | 返回类型 | 描述       |
| ---------------------------------------- | ---------------------------------------------------------- | -------- | ---------- |
| `setPosition(x, y, z)`                   | `number, number, number`                                   | `void`   | 设置位置   |
| `setPos(x, y, z)`                        | `number, number, number`                                   | `void`   | 设置位置   |
| `setPos(vec)`                            | `Vec3d`                                                    | `void`   | 设置位置   |
| `teleportTo(x, y, z)`                    | `number, number, number`                                   | `void`   | 传送到坐标 |
| `teleportTo(dimension, x, y, z, yaw, pitch)` | `ResourceLocation, number, number, number, number, number` | `void`   | 跨维度传送 |
| `moveTo(x, y, z)`                        | `number, number, number`                                   | `void`   | 移动到位置 |

### Motion 速度系统

> [!NOTE] 详细信息  
> Motion 详细说明请参阅 [向量与坐标文档](./Vector#motion-system)

| 方法                     | 参数                     | 返回类型 | 描述         |
| ------------------------ | ------------------------ | -------- | ------------ |
| `getMotionX()`           | -                        | `number` | 获取 X 轴速度 |
| `getMotionY()`           | -                        | `number` | 获取 Y 轴速度 |
| `getMotionZ()`           | -                        | `number` | 获取 Z 轴速度 |
| `setMotionX(x)`          | `number`                 | `void`   | 设置 X 轴速度 |
| `setMotionY(y)`          | `number`                 | `void`   | 设置 Y 轴速度 |
| `setMotionZ(z)`          | `number`                 | `void`   | 设置 Z 轴速度 |
| `setMotion(x, y, z)`     | `number, number, number` | `void`   | 设置三轴速度 |
| `addMotion(x, y, z)`     | `number, number, number` | `void`   | 增加速度     |
| `getDeltaMovement()`     | -                        | `Vec3d`  | 获取速度向量 |
| `setDeltaMovement(vec)`  | `Vec3d`                  | `void`   | 设置速度向量 |

### 类型判断

| 方法             | 参数 | 返回类型  | 描述           |
| ---------------- | ---- | --------- | -------------- |
| `isPlayer()`     | -    | `boolean` | 是否为玩家     |
| `isLiving()`     | -    | `boolean` | 是否为生物实体 |
| `isAnimal()`     | -    | `boolean` | 是否为动物     |
| `isMonster()`    | -    | `boolean` | 是否为怪物     |
| `isVehicle()`    | -    | `boolean` | 是否为载具     |
| `isSpectator()`  | -    | `boolean` | 是否为观察者   |
| `isAttackable()` | -    | `boolean` | 是否可攻击     |
| `isAlive()`      | -    | `boolean` | 是否存活       |
| `isRemoved()`    | -    | `boolean` | 是否已移除     |

::: code-group

```js [基础信息]
// 获取实体基本信息
const entityType = entity.getType();
const entityId = entity.getId();
const uuid = entity.getStringUuid();
const name = entity.getName();

// 类型检查
if (entity.isPlayer()) {
    console.log("这是一个玩家");
} else if (entity.isLiving()) {
    console.log("这是一个生物实体");
}
```

```js [位置操作]
// 获取坐标
const x = entity.getX();
const y = entity.getY();
const z = entity.getZ();
const blockPos = entity.blockPosition();

// 设置位置
entity.setPosition(100, 64, 200);
entity.setYaw(90);
entity.setPitch(0);

// 传送
entity.teleportTo(100, 64, 200);
entity.teleportTo("minecraft:the_nether", 0, 64, 0, 0, 0);
```

:::

## LivingEntity 方法 {#living_entity_methods}

继承自 Entity 的所有方法，并添加以下生物特有方法：

### 生命值系统 {#health}

| 方法                                     | 参数                   | 返回类型  | 描述           |
| ---------------------------------------- | ---------------------- | --------- | -------------- |
| `getHealth()`                            | -                      | `number`  | 获取当前生命值 |
| `getMaxHealth()`                         | -                      | `number`  | 获取最大生命值 |
| `setHealth(arg0: number)`                | `number`               | `void`    | 设置生命值     |
| `heal(arg0: number)`                     | `number`               | `void`    | 恢复生命值     |
| `hurt(arg0: DamageSource, arg1: number)` | `DamageSource, number` | `boolean` | 造成伤害       |
| `kill()`                                 | -                      | `void`    | 杀死实体       |
| `isDeadOrDying()`                        | -                      | `boolean` | 是否死亡或垂死 |

### 装备管理 {#equipment_manage}

| 方法                                                | 参数                       | 返回类型              | 描述             |
| --------------------------------------------------- | -------------------------- | --------------------- | ---------------- |
| `getMainHandItem()`                                 | -                          | `ItemStack`           | 获取主手物品     |
| `getOffhandItem()`                                  | -                          | `ItemStack`           | 获取副手物品     |
| `setMainHandItem(item: ItemStack)`                  | `ItemStack`                | `void`                | 设置主手物品     |
| `getItemBySlot(arg0: EquipmentSlot)`                | `EquipmentSlot`            | `ItemStack`           | 获取指定槽位物品 |
| `setItemSlot(arg0: EquipmentSlot, arg1: ItemStack)` | `EquipmentSlot, ItemStack` | `void`                | 设置槽位物品     |
| `getArmorSlots()`                                   | -                          | `Iterable<ItemStack>` | 获取护甲槽位     |
| `getHandSlots()`                                    | -                          | `Iterable<ItemStack>` | 获取手部槽位     |

### 药水效果 {#potion_effects}

| 方法                                               | 参数                        | 返回类型                        | 描述                 |
| -------------------------------------------------- | --------------------------- | ------------------------------- | -------------------- |
| `addEffect(arg0: MobEffectInstance)`               | `MobEffectInstance`         | `boolean`                       | 添加药水效果         |
| `addEffect(arg0: MobEffectInstance, arg1: Entity)` | `MobEffectInstance, Entity` | `boolean`                       | 添加药水效果(带来源) |
| `removeEffect(arg0: MobEffect)`                    | `MobEffect`                 | `boolean`                       | 移除指定效果         |
| `removeAllEffects()`                               | -                           | `boolean`                       | 移除所有效果         |
| `hasEffect(arg0: MobEffect)`                       | `MobEffect`                 | `boolean`                       | 检查是否有效果       |
| `getEffect(arg0: MobEffect)`                       | `MobEffect`                 | `MobEffectInstance`             | 获取效果实例         |
| `getActiveEffects()`                               | -                           | `Collection<MobEffectInstance>` | 获取所有激活效果     |

### 属性系统 {#attribute_system}

| 方法                                     | 参数        | 返回类型            | 描述           |
| ---------------------------------------- | ----------- | ------------------- | -------------- |
| `getAttribute(arg0: Attribute)`          | `Attribute` | `AttributeInstance` | 获取属性实例   |
| `getAttributeValue(arg0: Attribute)`     | `Attribute` | `number`            | 获取属性值     |
| `getAttributeBaseValue(arg0: Attribute)` | `Attribute` | `number`            | 获取基础属性值 |
| `getAttributes()`                        | -           | `AttributeMap`      | 获取属性映射   |

::: code-group

```js [生命值操作]
// 生命值管理
const currentHealth = entity.getHealth();
const maxHealth = entity.getMaxHealth();

// 恢复生命值
entity.heal(10);

// 造成伤害
entity.hurt(entity.damageSources().magic(), 5.0);

// 设置生命值
entity.setHealth(20);
```

```js [装备管理]
// 获取装备
const mainHand = entity.getMainHandItem();
const helmet = entity.getItemBySlot("head");

// 设置装备
entity.setMainHandItem(Item.of("minecraft:diamond_sword"));
entity.setItemSlot("head", Item.of("minecraft:diamond_helmet"));
```

```js [药水效果]
// 添加效果 (需要通过PotionEffects)
entity.potionEffects.add("minecraft:speed", 200, 1);
entity.potionEffects.add("minecraft:strength", 400, 0);

// 检查效果
const hasSpeed = entity.hasEffect("minecraft:speed");

// 移除效果
entity.removeEffect("minecraft:poison");
entity.removeAllEffects();
```

:::

## Player 方法 {#player_methods}

继承自 LivingEntity 的所有方法，并添加以下玩家特有方法：

### 背包管理 {#inventory}

| 方法                                           | 参数                  | 返回类型       | 描述         |
| ---------------------------------------------- | --------------------- | -------------- | ------------ |
| `getInventory()`                               | -                     | `InventoryKJS` | 获取背包对象 |
| `give(item: ItemStack)`                        | `ItemStack`           | `void`         | 给予物品     |
| `sendData(channel: string, data: CompoundTag)` | `string, CompoundTag` | `void`         | 发送数据包   |

### 经验系统

| 方法                                              | 参数                       | 返回类型        | 描述         |
| ------------------------------------------------- | -------------------------- | --------------- | ------------ |
| `getStats()`                                      | -                          | `PlayerStatsJS` | 获取统计数据 |
| `awardStat(arg0: ResourceLocation, arg1: number)` | `ResourceLocation, number` | `void`          | 给予统计点   |
| `addXPLevels(l: number)`                          | `number`                   | `void`          | 给予经验等级 |

### 玩家能力 {#abilities}

| 方法                  | 参数 | 返回类型    | 描述             |
| --------------------- | ---- | ----------- | ---------------- |
| `getAbilities()`      | -    | `Abilities` | 获取能力对象     |
| `onUpdateAbilities()` | -    | `void`      | 同步能力到客户端 |

### 界面交互

| 方法                                         | 参数                   | 返回类型 | 描述         |
| -------------------------------------------- | ---------------------- | -------- | ------------ |
| `notify(title: Component, text: Component)`  | `Component, Component` | `void`   | 发送通知     |
| `closeMenu()`                                | -                      | `void`   | 关闭菜单     |
| `openCommandBlock(arg0: CommandBlockEntity)` | `CommandBlockEntity`   | `void`   | 打开命令方块 |

::: code-group

```js [背包操作]
// 获取背包
const inventory = player.getInventory();

// 给予物品
player.give(Item.of("minecraft:diamond", 64));

// 检查物品
const hasDiamond = inventory.contains("minecraft:diamond");
const diamondCount = inventory.count("minecraft:diamond");
```

```js [经验和统计]
// 给予经验
player.addXPLevels(5);

// 统计数据
const stats = player.getStats();

// 给予统计点
player.awardStat("minecraft:play_time", 100);
```

```js [能力管理]
// 获取能力
const abilities = player.getAbilities();

// 设置飞行
abilities.mayfly = true;
abilities.flying = true;

// 同步到客户端
player.onUpdateAbilities();
```

:::

## 数据存储方法 {#data_storage_methods}

### persistentData 操作 {#persistentdata}

persistentData 是 KubeJS 的自定义数据存储系统，**不是 NBT 数据**。

| 方法                     | 参数              | 返回类型      | 描述           |
| ------------------------ | ----------------- | ------------- | -------------- |
| `getPersistentData()`    | -                 | `CompoundTag` | 获取持久化数据 |
| `putString(key, value)`  | `string, string`  | `void`        | 存储字符串     |
| `getString(key)`         | `string`          | `string`      | 读取字符串     |
| `putInt(key, value)`     | `string, number`  | `void`        | 存储整数       |
| `getInt(key)`            | `string`          | `number`      | 读取整数       |
| `putBoolean(key, value)` | `string, boolean` | `void`        | 存储布尔值     |
| `getBoolean(key)`        | `string`          | `boolean`     | 读取布尔值     |
| `contains(key)`          | `string`          | `boolean`     | 检查键是否存在 |

### NBT 操作 {#nbt}

| 方法                         | 参数          | 返回类型      | 描述          |
| ---------------------------- | ------------- | ------------- | ------------- |
| `getNbt()`                   | -             | `CompoundTag` | 获取 NBT 数据 |
| `setNbt(nbt: CompoundTag)`   | `CompoundTag` | `void`        | 设置 NBT 数据 |
| `mergeNbt(tag: CompoundTag)` | `CompoundTag` | `Entity`      | 合并 NBT 数据 |
| `save(arg0: CompoundTag)`    | `CompoundTag` | `boolean`     | 保存到 NBT    |
| `load(arg0: CompoundTag)`    | `CompoundTag` | `void`        | 从 NBT 加载   |

::: code-group

```js [persistentData]
// 存储数据
entity.persistentData.putString("player_role", "admin");
entity.persistentData.putInt("death_count", 5);
entity.persistentData.putBoolean("is_vip", true);

// 读取数据
const role = entity.persistentData.getString("player_role");
const deaths = entity.persistentData.getInt("death_count");
const isVip = entity.persistentData.getBoolean("is_vip");

// 检查数据
if (entity.persistentData.contains("first_join")) {
    console.log("数据存在");
}
```

```js [NBT操作]
// 获取和设置NBT
const nbt = entity.getNbt();
entity.setNbt(nbt);

// 合并NBT
entity.mergeNbt({
    CustomName: "§aSpecial Entity",
    Health: 100,
});
```

:::

## 世界交互方法 {#world_interaction_methods}

### 距离计算 {#distance}

| 方法                                     | 参数             | 返回类型  | 描述                 |
| ---------------------------------------- | ---------------- | --------- | -------------------- |
| `distanceTo(arg0: Entity)`               | `Entity`         | `number`  | 计算到实体的距离     |
| `distanceToSqr(arg0: Vec3d)`             | `Vec3d`          | `number`  | 计算到向量的距离平方 |
| `distanceToEntitySqr(arg0: Entity)`      | `Entity`         | `number`  | 计算到实体的距离平方 |
| `closerThan(arg0: Entity, arg1: number)` | `Entity, number` | `boolean` | 是否比指定距离更近   |

### 射线检测 {#raytrace}

| 方法                                              | 参数                      | 返回类型           | 描述             |
| ------------------------------------------------- | ------------------------- | ------------------ | ---------------- |
| `rayTrace(distance: number)`                      | `number`                  | `RayTraceResultJS` | 射线检测         |
| `rayTrace(distance: number, fluids: boolean)`     | `number, boolean`         | `RayTraceResultJS` | 射线检测(含流体) |
| `pick(arg0: number, arg1: number, arg2: boolean)` | `number, number, boolean` | `HitResult`        | 拾取检测         |

### 碰撞检测 {#collision}

| 方法                                            | 参数                   | 返回类型  | 描述             |
| ----------------------------------------------- | ---------------------- | --------- | ---------------- |
| `getBoundingBox()`                              | -                      | `AABB`    | 获取碰撞箱       |
| `setBoundingBox(arg0: AABB)`                    | `AABB`                 | `void`    | 设置碰撞箱       |
| `isColliding(arg0: BlockPos, arg1: BlockState)` | `BlockPos, BlockState` | `boolean` | 是否与方块碰撞   |
| `canCollideWith(arg0: Entity)`                  | `Entity`               | `boolean` | 是否可与实体碰撞 |

::: code-group

```js [距离计算]
// 计算距离
const distance = player.distanceTo(targetEntity);
const distanceSq = player.distanceToSqr(targetPos);

// 距离判断
if (player.closerThan(targetEntity, 10)) {
    console.log("目标在10格范围内");
}
```

```js [射线检测]
// 基础射线检测
const rayTrace = player.rayTrace(10.0);
if (rayTrace.block) {
    console.log("看着方块:", rayTrace.block);
}

// 包含流体的射线检测
const rayTraceFluid = player.rayTrace(10.0, true);
if (rayTraceFluid.entity) {
    console.log("看着实体:", rayTraceFluid.entity);
}
```

:::

## 相关链接 {#related_links}

- [向量与坐标文档](./Vector) - 详细的坐标系统和移动机制说明
- [LivingEntity 文档](./LivingEntity) - 生物实体特有方法
- [Player 文档](./Player) - 玩家对象操作方法
- [persistentData 文档](../Miscellaneous/persistentData) - 数据存储系统
- [Forge Entity JavaDocs](https://mcstreetguy.github.io/ForgeJavaDocs/1.20.1-47.1.0/net/minecraft/world/entity/Entity.html) - 官方文档参考
 