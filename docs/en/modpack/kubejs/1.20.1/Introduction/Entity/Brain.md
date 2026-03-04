---
title: Entity Brain
description: Brain system, memory, sensory locations and KubeJS usage
progress: 90
tags:
  - KubeJS
  - Entity
  - Brain
hidden: false
priority: 350
---

# Brain

## Overview {#overview}

Brain is the core AI controller for mobs in Minecraft 1.19+, replacing the older Goal system. It provides more advanced behavior management through Memory, Sensors, and Tasks.

## Mechanism Description {#mechanism}

- Core component of mob AI, managing memory (Memory), sensory (Sensors), and behavior tasks (Tasks)
- Memory: Stores information about the entity, such as nearest player, home position, mood, etc.
- Sensors: Collect information from the environment and update memory
- Tasks: Execute behaviors based on memory and current state

## Common Methods {#common_methods}

```js
// Get Brain
const brain = mob.getBrain();

// Memory Operations
brain.setMemory("minecraft:last_hurt_by", player);
const lastHurtBy = brain.getMemory("minecraft:last_hurt_by");

// Check memory exists
if (brain.hasMemory("minecraft:visible_targets")) {
    /* ... */
}
```

## Notes {#notes}

- Brain is available in Minecraft 1.19+
- For 1.18 and earlier, use Goal system instead
- Memory types vary by mob type

