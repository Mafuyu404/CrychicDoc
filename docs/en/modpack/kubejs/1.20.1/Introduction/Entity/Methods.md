---
title: Entity Methods
description: KubeJS Entity API common methods classification and usage reference
progress: 80
tags:
  - KubeJS
  - Entity
  - Methods
hidden: false
priority: 20
---

# Entity Common Methods

## Overview {#overview}

This page summarizes common methods in the KubeJS Entity API, covering operations such as attributes, AI, status, equipment, and data, facilitating reference and scripting development.

## Method Categories and Descriptions {#categories}

### Attribute Related {#attribute}
- `getAttribute(name)`: Get attribute instance
- `modifyAttribute(name, id, amount, operation)`: Add attribute modifier
- `removeAttributeModifier(name Remove attribute modifier

, id)`:### AI Related {#ai}
- `addGoal(priority, goal)`: Add AI goal
- `setTarget(entity)`: Set attack target
- `getTarget()`: Get current target

### Status Related {#status}
- `getHealth()` / `setHealth(value)`: Get/set health
- `isAlive()` / `isDeadOrDying()`: Survival status check
- `addEffect(effect, duration, amplifier)`: Add potion effect
- `removeEffect(effect)`: Remove potion effect
- `hasEffect(effect)`: Check potion effect

### Equipment Related {#equipment}
- `getItemBySlot(slot)`: Get item in specified equipment slot
- `setItemSlot(slot, item)`: Set item in equipment slot
- `mainHandItem` / `offHandItem`: Main hand/off hand item

### Data and Persistence {#data}
- `persistentData`: Custom data storage
- `addTag(tag)` / `removeTag(tag)`: Add/remove tag

## Usage Examples {#examples}

```js
// Get and modify attribute
const attr = entity.getAttribute('generic.max_health');
attr.setBaseValue(40);
entity.modifyAttribute('generic.max_health', 'kubejs:bonus', 8, 'addition');

// Add AI goal
const FloatGoal = Java.loadClass('net.minecraft.world.entity.ai.goal.FloatGoal');
entity.goalSelector.addGoal(0, new FloatGoal(entity));

// Status and potions
entity.setHealth(10);
entity.addEffect('minecraft:regeneration', 200, 1);

// Equipment operations
entity.setItemSlot('head', Item.of('minecraft:diamond_helmet'));

// Data storage
entity.persistentData.putString('role', 'boss');
```

## Notes {#notes}

- Some methods are only available on the server side or in specific events, pay attention to compatibility.
- For detailed types and parameters, refer to [Forge JavaDocs](https://mcstreetguy.github.io/ForgeJavaDocs/1.20.1-47.1.0/index.html).

## Basic Entity Methods {#basic_entity_methods}

### Entity Information Retrieval {#entity_info}

| Method | Parameters | Return Type | Description |
| ------ | ---------- | ----------- | ------------ |
| `getType()` | - | `string` | Get entity type ID |
| `getId()` | - | `number` | Get entity numeric ID |
| `getStringUuid()` | - | `string` | Get entity UUID string |
| `getUuid()` | - | `Internal.UUID` | Get entity UUID object |
| `getName()` | - | `Component` | Get entity name component |
| `getDisplayName()` | - | `Component` | Get display name |
| `getCustomName()` | - | `Component` | Get custom name |
| `hasCustomName()` | - | `boolean` | Has custom name |

### Position and Coordinates {#position}

> [!NOTE] Detailed Information
> For detailed information about vectors, coordinates, and movement mechanisms, please refer to [Vector and Coordinates documentation](./Vector)

| Method | Parameters | Return Type | Description |
| ------ | ---------- | ----------- | ------------ |
| `getX()` | - | `number` | Get X coordinate |
| `getY()` | - | `number` | Get Y coordinate |
| `getZ()` | - | `number` | Get Z coordinate |
| `blockPosition()` | - | `BlockPos` | Get block position |
| `position()` | - | `Vec3d` | Get precise position vector |
| `getEyePosition()` | - | `Vec3d` | Get eye position |

### Rotation and Orientation {#rotation}

| Method | Parameters | Return Type | Description |
| ------ | ---------- | ----------- | ------------ |
| `getYaw()` | - | `number` | Get yaw angle |
| `getPitch()` | - | `number` | Get pitch angle |
| `getYHeadRot()` | - | `number` | Get head rotation |
| `setYaw(arg0: number)` | `number` | `void` | Set yaw angle |
| `setPitch(arg0: number)` | `number` | `void` | Set pitch angle |
| `setYHeadRot(arg0: number)` | `number` | `void` | Set head rotation |
| `getLookAngle()` | - | `Vec3d` | Get look direction vector |

### Movement and Teleportation

| Method | Parameters | Return Type | Description |
| ------ | ---------- | ----------- | ------------ |
| `setPosition(x, y, z)` | `number, number, number` | `void` | Set position |
| `setPos(x, y, z)` | `number, number, number` | `void` | Set position |
| `setPos(vec)` | `Vec3d` | `void` | Set position |
| `teleportTo(x, y, z)` | `number, number, number` | `void` | Teleport to coordinates |
| `teleportTo(dimension, x, y, z, yaw, pitch)` | `ResourceLocation, number, number, number, number, number` | `void` | Cross-dimension teleport |
| `moveTo(x, y, z)` | `number, number, number` | `void` | Move to position |

### Motion and Velocity

> [!NOTE] Detailed Information
> For detailed information about Motion, please refer to [Vector and Coordinates documentation](./Vector#motion-system)

| Method | Parameters | Return Type | Description |
| ------ | ---------- | ----------- | ------------ |
| `getMotionX()` | - | `number` | Get X axis velocity |
| `getMotionY()` | - | `number` | Get Y axis velocity |
| `getMotionZ()` | - | `number` | Get Z axis velocity |
| `setMotionX(x)` | `number` | `void` | Set X axis velocity |
| `setMotionY(y)` | `number` | `void` | Set Y axis velocity |
| `setMotionZ(z)` | `number` | `void` | Set Z axis velocity |
| `setMotion(x, y, z)` | `number, number, number` | `void` | Set three-axis velocity |
| `addMotion(x, y, z)` | `number, number, number` | `void` | Add velocity |
| `getDeltaMovement()` | - | `Vec3d` | Get velocity vector |
| `setDeltaMovement(vec)` | `Vec3d` | `void` | Set velocity vector |

### Type Checking

| Method | Parameters | Return Type | Description |
| ------ | ---------- | ----------- | ------------ |
| `isPlayer()` | - | `boolean` | Is player |
| `isLiving()` | - | `boolean` | Is living entity |
| `isAnimal()` | - | `boolean` | Is animal |
| `isMonster()` | - | `boolean` | Is monster |
| `isVehicle()` | - | `boolean` | Is vehicle |
| `isSpectator()` | - | `boolean` | Is spectator |
| `isAttackable()` | - | `boolean` | Is attackable |
| `isAlive()` | - | `boolean` | Is alive |
| `isRemoved()` | - | `boolean` | Is removed |

::: code-group

```js [Basic Information]
// Get entity basic information
const entityType = entity.getType();
const entityId = entity.getId();
const uuid = entity.getStringUuid();
const name = entity.getName();

// Type checking
if (entity.isPlayer()) {
    console.log("This is a player");
} else if (entity.isLiving()) {
    console.log("This is a living entity");
}
```

```js [Position Operations]
// Get coordinates
const x = entity.getX();
const y = entity.getY();
const z = entity.getZ();
const blockPos = entity.blockPosition();

// Set position
entity.setPosition(100, 64, 200);
entity.setYaw(90);
entity.setPitch(0);

// Teleportation
entity.teleportTo(100, 64, 200);
entity.teleportTo("minecraft:the_nether", 0, 64, 0, 0, 0);
```

:::

## LivingEntity Methods {#living_entity_methods}

All methods inherited from Entity, plus the following entity-specific methods:

### Health {#health}

| Method | Parameters | Return Type | Description |
| ------ | ---------- | ----------- | ------------ |
| `getHealth()` | - | `number` | Get current health |
| `getMaxHealth()` | - | `number` | Get max health |
| `setHealth(arg0: number)` | `number` | `void` | Set health |
| `heal(arg0: number)` | `number` | `void` | Heal entity |
| `hurt(arg0: DamageSource, arg1: number)` | `DamageSource, number` | `boolean` | Deal damage |
| `kill()` | - | `void` | Kill entity |
| `isDeadOrDying()` | - | `boolean` | Is dead or dying |

### Equipment Management {#equipment_manage}

| Method | Parameters | Return Type | Description |
| ------ | ---------- | ----------- | ------------ |
| `getMainHandItem()` | - | `ItemStack` | Get main hand item |
| `getOffhandItem()` | - | `ItemStack` | Get off hand item |
| `setMainHandItem(item: ItemStack)` | `ItemStack` | `void` | Set main hand item |
| `getItemBySlot(arg0: EquipmentSlot)` | `EquipmentSlot` | `ItemStack` | Get item in specified slot |
| `setItemSlot(arg0: EquipmentSlot, arg1: ItemStack)` | `EquipmentSlot, ItemStack` | `void` | Set item in slot |
| `getArmorSlots()` | - | `Iterable<ItemStack>` | Get armor slots |
| `getHandSlots()` | - | `Iterable<ItemStack>` | Get hand slots |

### Potion Effects {#potion_effects}

| Method | Parameters | Return Type | Description |
| ------ | ---------- | ----------- | ------------ |
| `addEffect(arg0: MobEffectInstance)` | `MobEffectInstance` | `boolean` | Add potion effect |
| `addEffect(arg0: MobEffectInstance, arg1: Entity)` | `MobEffectInstance, Entity` | `boolean` | Add potion effect with source |
| `removeEffect(arg0: MobEffect)` | `MobEffect` | `boolean` | Remove specified effect |
| `removeAllEffects()` | - | `boolean` | Remove all effects |
| `hasEffect(arg0: MobEffect)` | `MobEffect` | `boolean` | Check if has effect |
| `getEffect(arg0: MobEffect)` | `MobEffect` | `MobEffectInstance` | Get effect instance |
| `getActiveEffects()` | - | `Collection<MobEffectInstance>` | Get all active effects |

### Attributes {#attribute_system}

| Method | Parameters | Return Type | Description |
| ------ | ---------- | ----------- | ------------ |
| `getAttribute(arg0: Attribute)` | `Attribute` | `AttributeInstance` | Get attribute instance |
| `getAttributeValue(arg0: Attribute)` | `Attribute` | `number` | Get attribute value |
| `getAttributeBaseValue(arg0: Attribute)` | `Attribute` | `number` | Get base attribute value |
| `getAttributes()` | - | `AttributeMap` | Get attribute map |

::: code-group

```js [Health Operations]
// Health management
const currentHealth = entity.getHealth();
const maxHealth = entity.getMaxHealth();

// Heal entity
entity.heal(10);

// Deal damage
entity.hurt(entity.damageSources().magic(), 5.0);

// Set health
entity.setHealth(20);
```

```js [Equipment Management]
// Get equipment
const mainHand = entity.getMainHandItem();
const helmet = entity.getItemBySlot("head");

// Set equipment
entity.setMainHandItem(Item.of("minecraft:diamond_sword"));
entity.setItemSlot("head", Item.of("minecraft:diamond_helmet"));
```

```js [Potion Effects]
// Add effect (need to use PotionEffects)
entity.potionEffects.add("minecraft:speed", 200, 1);
entity.potionEffects.add("minecraft:strength", 400, 0);

// Check effect
const hasSpeed = entity.hasEffect("minecraft:speed");

// Remove effect
entity.removeEffect("minecraft:poison");
entity.removeAllEffects();
```

:::

## Player Methods {#player_methods}

All methods inherited from LivingEntity, plus the following player-specific methods:

### Inventory Management {#inventory}

| Method | Parameters | Return Type | Description |
| ------ | ---------- | ----------- | ------------ |
| `getInventory()` | - | `InventoryKJS` | Get inventory object |
| `give(item: ItemStack)` | `ItemStack` | `void` | Give item |
| `sendData(channel: string, data: CompoundTag)` | `string, CompoundTag` | `void` | Send data packet |

### Experience

| Method | Parameters | Return Type | Description |
| ------ | ---------- | ----------- | ------------ |
| `getStats()` | - | `PlayerStatsJS` | Get player statistics |
| `awardStat(arg0: ResourceLocation, arg1: number)` | `ResourceLocation, number` | `void` | Award stat point |
| `addXPLevels(l: number)` | `number` | `void` | Add XP levels |

### Player Abilities {#abilities}

| Method | Parameters | Return Type | Description |
| ------ | ---------- | ----------- | ------------ |
| `getAbilities()` | - | `Abilities` | Get abilities object |
| `onUpdateAbilities()` | - | `void` | Sync abilities to client |

### GUI Interaction

| Method | Parameters | Return Type | Description |
| ------ | ---------- | ----------- | ------------ |
| `notify(title: Component, text: Component)` | `Component, Component` | `void` | Send notification |
| `closeMenu()` | - | `void` | Close menu |
| `openCommandBlock(arg0: CommandBlockEntity)` | `CommandBlockEntity` | `void` | Open command block |

::: code-group

```js [Inventory Operations]
// Get inventory
const inventory = player.getInventory();

// Give items
player.give(Item.of("minecraft:diamond", 64));

// Check items
const hasDiamond = inventory.contains("minecraft:diamond");
const diamondCount = inventory.count("minecraft:diamond");
```

```js [Experience and Statistics]
// Add experience
player.addXPLevels(5);

// Get statistics
const stats = player.getStats();

// Award stat point
player.awardStat("minecraft:play_time", 100);
```

```js [Abilities Management]
// Get abilities
const abilities = player.getAbilities();

// Set flying
abilities.mayfly = true;
abilities.flying = true;

// Sync to client
player.onUpdateAbilities();
```

:::

## Data Storage Methods {#data_storage_methods}

### persistentData Operations {#persistentdata}

persistentData is KubeJS's custom data storage, **NOT NBT data**.

| Method | Parameters | Return Type | Description |
| ------ | ---------- | ----------- | ------------ |
| `getPersistentData()` | - | `CompoundTag` | Get persistent data |
| `putString(key, value)` | `string, string` | `void` | Store string |
| `getString(key)` | `string` | `string` | Read string |
| `putInt(key, value)` | `string, number` | `void` | Store integer |
| `getInt(key)` | `string` | `number` | Read integer |
| `putBoolean(key, value)` | `string, boolean` | `void` | Store boolean |
| `getBoolean(key)` | `string` | `boolean` | Read boolean |
| `contains(key)` | `string` | `boolean` | Check if key exists |

### NBT Operations {#nbt}

| Method | Parameters | Return Type | Description |
| ------ | ---------- | ----------- | ------------ |
| `getNbt()` | - | `CompoundTag` | Get NBT data |
| `setNbt(nbt: CompoundTag)` | `CompoundTag` | `void` | Set NBT data |
| `mergeNbt(tag: CompoundTag)` | `CompoundTag` | `Entity` | Merge NBT data |
| `save(arg0: CompoundTag)` | `CompoundTag` | `boolean` | Save to NBT |
| `load(arg0: CompoundTag)` | `CompoundTag` | `void` | Load from NBT |

::: code-group

```js [persistentData]
// Store data
entity.persistentData.putString("player_role", "admin");
entity.persistentData.putInt("death_count", 5);
entity.persistentData.putBoolean("is_vip", true);

// Read data
const role = entity.persistentData.getString("player_role");
const deaths = entity.persistentData.getInt("death_count");
const isVip = entity.persistentData.getBoolean("is_vip");

// Check data
if (entity.persistentData.contains("first_join")) {
    console.log("Data exists");
}
```

```js [NBT Operations]
// Get and set NBT
const nbt = entity.getNbt();
entity.setNbt(nbt);

// Merge NBT
entity.mergeNbt({
    CustomName: "§aSpecial Entity",
    Health: 100,
});
```

:::

## World Interaction Methods {#world_interaction_methods}

### Distance Calculation {#distance}

| Method | Parameters | Return Type | Description |
| ------ | ---------- | ----------- | ------------ |
| `distanceTo(arg0: Entity)` | `Entity` | `number` | Calculate distance to entity |
| `distanceToSqr(arg0: Vec3d)` | `Vec3d` | `number` | Calculate squared distance to vector |
| `distanceToEntitySqr(arg0: Entity)` | `Entity` | `number` | Calculate squared distance to entity |
| `closerThan(arg0: Entity, arg1: number)` | `Entity, number` | `boolean` | Is closer than specified distance |

### Ray Tracing {#raytrace}

| Method | Parameters | Return Type | Description |
| ------ | ---------- | ----------- | ------------ |
| `rayTrace(distance: number)` | `number` | `RayTraceResultJS` | Ray trace |
| `rayTrace(distance: number, fluids: boolean)` | `number, boolean` | `RayTraceResultJS` | Ray trace (including fluids) |
| `pick(arg0: number, arg1: number, arg2: boolean)` | `number, number, boolean` | `HitResult` | Pick detection |

### Collision Detection {#collision}

| Method | Parameters | Return Type | Description |
| ------ | ---------- | ----------- | ------------ |
| `getBoundingBox()` | - | `AABB` | Get collision box |
| `setBoundingBox(arg0: AABB)` | `AABB` | `void` | Set collision box |
| `isColliding(arg0: BlockPos, arg1: BlockState)` | `BlockPos, BlockState` | `boolean` | Is colliding with block |
| `canCollideWith(arg0: Entity)` | `Entity` | `boolean` | Can collide with entity |

::: code-group

```js [Distance Calculation]
// Calculate distance
const distance = player.distanceTo(targetEntity);
const distanceSq = player.distanceToSqr(targetPos);

// Distance check
if (player.closerThan(targetEntity, 10)) {
    console.log("Target is within 10 blocks");
}
```

```js [Ray Tracing]
// Basic ray tracing
const rayTrace = player.rayTrace(10.0);
if (rayTrace.block) {
    console.log("Looking at block:", rayTrace.block);
}

// Ray trace including fluids
const rayTraceFluid = player.rayTrace(10.0, true);
if (rayTraceFluid.entity) {
    console.log("Looking at entity:", rayTraceFluid.entity);
}
```

:::

<llm-only>
This page provides comprehensive documentation on Entity methods in KubeJS. Key points for LLM understanding:

1. Entity Methods Overview:
   - Attribute operations: getAttribute, modifyAttribute, removeAttributeModifier
   - AI operations: addGoal, setTarget, getTarget
   - Health operations: getHealth, setHealth, heal, hurt
   - Equipment: getItemBySlot, setItemSlot, getMainHandItem
   - Potion effects: addEffect, removeEffect, hasEffect
   - Data storage: persistentData (KubeJS custom), NBT (vanilla)

2. Method Inheritance:
   - Entity → LivingEntity → Mob → Player
   - Each class adds more methods

3. Key Differences:
   - persistentData is KubeJS-specific, NOT NBT
   - Some methods only available on server side
   - Reference Forge JavaDocs for detailed parameters

4. Common Usage Patterns:
   - entity.getAttribute('generic.max_health').setBaseValue(40)
   - entity.goalSelector.addGoal(0, new FloatGoal(entity))
   - entity.setItemSlot('head', Item.of('minecraft:diamond_helmet'))
   - entity.persistentData.putString('key', 'value')

This is a comprehensive API reference for KubeJS entity scripting.
</llm-only>
