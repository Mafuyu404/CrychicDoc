---
title: Entity Goal
description: Goal system, goalSelector, targetSelector and KubeJS usage
progress: 90
tags:
  - KubeJS
  - Entity
  - Goal
hidden: false
priority: 340
---

# Entity Goal System

## Overview {#overview}

The Goal System is the core AI behavior framework in Minecraft, controlling how entities make decisions and execute actions. It is divided into two main components: goalSelector (daily behavior goals) and targetSelector (attack/target goals).

## Core Concepts {#core_concepts}

### goalSelector {#goal-selector}

Manages daily behaviors like wandering, attacking, breeding, etc. Lower priority numbers execute first.

| Priority | Typical Use |
| -------- | ----------- |
| 0 | Critical (e.g., float when in water) |
| 1-2 | Combat (e.g., melee attack) |
| 3-4 | Movement (e.g., random wander) |
| 5+ | Idle behaviors |

### targetSelector {#target-selector}

Manages target selection and tracking, used for hostile mobs to find and track attack targets.

## Lifecycle Methods {#lifecycle}

Goals have a clear lifecycle:

| Stage | Method | Description |
| ----- | ------ | ----------- |
| Can Use | canUse() | Check if goal can be activated |
| Start | start() | Called when goal starts executing |
| Tick | tick() | Called each tick while goal is active |
| Can Continue | canContinueToUse() | Check if goal should continue |
| Stop | stop() | Called when goal stops |

## Common Goal Classes {#common_goals}

### Movement Goals {#movement}

| Goal Class | Description |
| ---------- | ----------- |
| `FloatGoal` | Float in water |
| `RandomStrollGoal` | Random wandering |
| `LookAtPlayerGoal` | Look at player |
| `MoveTowardsTargetGoal` | Move toward target |
| `BreedGoal` | Find mate and breed |

### Combat Goals {#combat}

| Goal Class | Description |
| ---------- | ----------- |
| `MeleeAttackGoal` | Melee attack target |
| `RangedAttackGoal` | Ranged attack |
| `HurtByTargetGoal` | Attack what hurt it |

## Usage Examples {#examples}

```js
// Add goal
const FloatGoal = Java.loadClass("net.minecraft.world.entity.ai.goal.FloatGoal");
mob.goalSelector.addGoal(0, new FloatGoal(mob));

// Add target goal
const NearestAttackableTargetGoal = Java.loadClass("net.minecraft.world.entity.ai.targeting.NearestAttackableTargetGoal");
mob.targetSelector.addGoal(0, new NearestAttackableTargetGoal(mob, Player.class, true));
```

## Notes {#notes}

- Goal priority: Lower number = higher priority
- Only one goal per priority level in each selector
- Use `removeGoal` to clear goals
- For 1.19+, consider using Brain system instead

<llm-only>
The Goal System is Minecraft's AI behavior framework. Key points:
1. goalSelector: Daily behaviors (wander, breed), priority 0 = highest
2. targetSelector: Attack/target selection for hostile mobs
3. Lifecycle: canUse → start → tick → canContinueToUse → stop
4. Common goals: FloatGoal, RandomStrollGoal, MeleeAttackGoal
5. For 1.19+, Brain system is preferred over Goal system
</llm-only>