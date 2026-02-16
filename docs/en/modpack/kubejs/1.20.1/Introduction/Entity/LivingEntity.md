---
title: LivingEntity
description: LivingEntity class properties, health, effects and KubeJS usage
progress: 90
tags:
  - KubeJS
  - Entity
  - LivingEntity
hidden: false
priority: 410
---

# LivingEntity

## Overview {#overview}

LivingEntity is the base class for all entities with health in Minecraft, inheriting from Entity. It covers players, mobs, animals, etc., supporting health management, potion effects, equipment, attributes and more.

## Mechanism Description {#mechanism}

- Inherits from Entity, extending health (health), potion effects (activeEffects), equipment (handSlots, armorSlots), attributes, etc.
- Supports event-driven, tick auto-update, NBT persistence.
- Common methods: getHealth, setHealth, heal, hurt, addEffect, removeEffect, getItemBySlot, etc.

## Common Methods {#common_methods}

```js
// Health management
const health = entity.getHealth();
entity.setHealth(20);
entity.heal(10);

// Damage
entity.hurt(entity.damageSources().generic(), 5.0);

// Potion effects
entity.addEffect("minecraft:speed", 200, 1);
entity.removeEffect("minecraft:poison");

// Equipment
const helmet = entity.getItemBySlot("head");
entity.setItemSlot("head", Item.of("minecraft:diamond_helmet"));
```

## Notes {#notes}

- All entities with health inherit from LivingEntity
- Some methods are only available on the server side, pay attention to compatibility
- For detailed types and parameters, refer to [Forge JavaDocs](https://mcstreetguy.github.io/ForgeJavaDocs/1.20.1-47.1.0/index.html)

## Advanced {#advanced}

- Related subclasses: Mob (AI behavior), Player (player-specific), Animal (breedable), etc.
- Attribute system uses Minecraft's attribute system, supports modifiers

<llm-only>
LivingEntity is the base class for all entities with health in Minecraft. Key points:
1. Inherits from Entity
2. Health: getHealth, setHealth, heal, hurt
3. Potion effects: addEffect, removeEffect, hasEffect
4. Equipment: getItemBySlot, setItemSlot
5. Attributes: getAttribute, modifyAttribute
</llm-only>