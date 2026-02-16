---
title: Mob
description: Mob class properties, AI, goal selection and KubeJS usage
progress: 90
tags:
  - KubeJS
  - Entity
  - Mob
hidden: false
priority: 400
---

# Mob

## Overview {#overview}

Mob is the base class for all entities with AI behavior in Minecraft, inheriting from LivingEntity. It covers monsters, animals, neutral creatures, etc., supporting advanced mechanisms like AI goals, target selectors, behavior trees, and more.

## Mechanism Description {#mechanism}

- Inherits from LivingEntity, extending AI goals (goalSelector), target selectors (targetSelector), Brain, etc.
- Supports AI behavior trees, goal priorities, attack/defense/movement and other behaviors.
- Common methods: addGoal, addTargetGoal, setTarget, getTarget, isAggressive, canAttack, wander, lookAt, etc.
- Supports event-driven, tick auto-update, NBT persistence.

## Common Methods {#common_methods}

```js
// Add AI goal
const MeleeAttackGoal = Java.loadClass("net.minecraft.world.entity.ai.goal.MeleeAttackGoal");
mob.goalSelector.addGoal(0, new MeleeAttackGoal(mob, 1.0, true));

// Set attack target
mob.setTarget(targetEntity);
const currentTarget = mob.getTarget();

// Check if aggressive
if (mob.isAggressive()) {
    /* ... */
}
```

## Common Subclasses and Extensions {#subclasses}

### PathfinderMob {#PathfinderMob}

- Direct parent class for most entities, with basic pathfinding and leash logic.
- Common methods/properties:

| Method/Property | Description |
|-------------------------|----------------------------|
| getNavigation() | Get navigation controller |
| moveTo(x, y, z, speed) | Move to specified position |
| isPathFinding() | Is pathfinding |

::: code-group

```js [KubeJS]
mob.getNavigation().moveTo(100, 64, 100, 1.0)
```

:::

---

### Animal {#Animal}

- Represents breedable animals like cows, sheep, pigs, chickens, etc.
- Common methods/properties:

| Method/Property | Description |
| ----------- | ------------------ |
| isBaby() | Is juvenile |
| setAge(age) | Set age |
| canMate(other) | Can breed with specified animal |
| setInLove(player) | Enter breeding state |

::: code-group

```js [KubeJS]
if (mob.isBaby()) mob.setAge(0)
if (mob.canMate(other)) mob.setInLove(player)
```

:::

---

### TamableMob {#TamableMob}

- Represents creatures that can be tamed by players like wolves, cats, parrots, foxes, etc.
- Common methods/properties:

| Method/Property | Description |
| -------------- | -------------------- |
| isTame() | Is tamed |
| setTame(bool) | Set tame state |
| getOwner() | Get owner entity |
| setOwner(uuid) | Set owner |
| isInSittingPose() | Is sitting |
| setSitting(bool) | Set sitting state |

::: code-group

```js [KubeJS]
if (mob.isTame()) mob.setSitting(true)
```

:::

---

### Monster {#Monster}

- Represents hostile entities like zombies, skeletons, creepers, etc.
- Common methods/properties:

| Method/Property | Description |
| ------------ | ------------------ |
| isAggressive() | Is aggressive |
| setTarget(entity) | Set attack target |
| getTarget() | Get current target |
| canAttack(entity) | Can attack target |

Note: These methods are not unique to Monster

::: code-group

```js [KubeJS]
if (mob.isAggressive()) mob.setTarget(player)
```

:::

---

### WaterAnimal {#WaterAnimal}

- Represents aquatic creatures like fish, dolphins, squid, etc.
- **No unique API, common methods are all Mob general.**
- Its "aquatic" characteristic is reflected in overrides of mobType, breathing mechanism, leash, etc.

| Method/Property | Description |
|-------------------------|----------------------------|
| mobType | Type identifier, 'WATER' for aquatic |
| isInWaterOrBubble() | Is in water or bubble column |
| setAirSupply(value) | Set air supply (Mob general) |
| getAirSupply() | Get air supply (Mob general) |

Note: These methods are not unique to WaterAnimal

::: code-group

```js [KubeJS]
if (mob.mobType == 'WATER' && !mob.isInWaterOrBubble()) {
  mob.setAirSupply(0)
}
```

:::

#### Notes {#tips}

- Recommended to use `mobType` for checking aquatic creatures, e.g., `mob.mobType == 'WATER'`.
- `setAirSupply`, `getAirSupply`, `isInWaterOrBubble` etc. are general for all Mobs, not aquatic-specific.

---

### AmbientCreature {#AmbientCreature}

- Represents harmless ambient creatures like bats.
- **No special extensions, inherits from Mob.**

| Method/Property | Description |
| --------- | -------------- |
| - | No special extensions |

---

## Notes {#notes}

- Only Mob and its subclasses support AI goals, target selection, and other operations.
- Some methods are only available for specific subclasses. It is recommended to use JSDoc type annotations before use to improve completion experience.
- For further API confirmation, refer to type definitions in the typefiles/1.20.1 directory and Forge JavaDocs.

<!--
<llm-only>
Mob is the base class for all AI entities in Minecraft. Key points:

1. Inherits from LivingEntity
2. Supports AI goals (goalSelector), target selectors (targetSelector)
3. Common subclasses: PathfinderMob, Animal, TamableMob, Monster, WaterAnimal, AmbientCreature
4. Common methods: addGoal, setTarget, getTarget, isAggressive

This page documents Mob class and its subclasses for KubeJS entity scripting.
</llm-only>
-->