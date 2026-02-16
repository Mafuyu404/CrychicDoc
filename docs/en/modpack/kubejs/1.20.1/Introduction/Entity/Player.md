---
title: Player
description: Player class properties, methods, events and KubeJS usage
progress: 80
tags:
  - KubeJS
  - Entity
  - Player
hidden: false
priority: 500
---

# Player

## Overview {#overview}

Player is the entity class representing the player in Minecraft, inheriting from LivingEntity. It has unique ability systems, inventory, experience, game modes, etc., supporting rich scripting operations.

## Mechanism Description {#mechanism}

- Inherits from LivingEntity, extending abilities, inventory, experience, gameMode, advancements and other systems.
- Supports tick auto-update, event-driven, NBT persistence.
- Common methods: getName, getUuid, getAbilities, getInventory, addExperience, setGameMode, sendMessage, isCreative, isSpectator, isSleeping, etc.

## Common Methods {#common_methods}

```js
// Get player name and UUID
const name = player.getName();
// To get a name that can be used directly
const name = player.username;
const uuid = player.getUuid();

// Send message
player.sendMessage('Hello, world!');

// Check creative/spectator mode
if (player.isCreative()) { /* ... */ }
if (player.isSpectator()) { /* ... */ }

// Add experience
player.addExperience(100);

// Get inventory contents
const inventory = player.getInventory();
```

## Notes {#notes}

- Only Player entities support abilities, inventory, experience, game mode and other operations.
- Some methods are only available on the server side or in specific events, pay attention to compatibility.
- For detailed types and parameters, refer to [Forge JavaDocs](https://mcstreetguy.github.io/ForgeJavaDocs/1.20.1-47.1.0/index.html).

## Advanced {#advanced}

- Player is the core of KubeJS player scripting development. It is recommended to combine vanilla source code with Forge documentation for in-depth study.
- Related subclasses: ServerPlayer, AbstractClientPlayer, etc.

<llm-only>
This page documents the Player class in KubeJS for entity scripting. Key points for LLM understanding:

1. Player inherits from LivingEntity, adding player-specific systems
2. Common operations: getName, getUuid, sendMessage, isCreative, isSpectator
3. Inventory access via getInventory()
4. Experience management via addExperience()
5. Game mode checking: isCreative(), isSpectator()
6. Related classes: ServerPlayer, AbstractClientPlayer

For scripting, use player.username for direct name access, player.getName() for name component.
</llm-only>