---
title: Chest
hidden: false
priority: 200
---
# Chest Type Loot Tables

## Operating Loot Tables

-   Event: ServerEvents.chestLootTables(event => {});

::: code-group

```js [KubeJS Modify Original Loot Table]
ServerEvents.chestLootTables((event) => {
    // Modify original loot table
    event.modify("minecraft:end_city_treasure", (loot) => {
        loot.addPool((pool) => {
            // Add loot
            pool.addItem("minecraft:diamond"); // [!code ++]
        });
    });
});
```

```js [KubeJS Override Original Loot Table]
ServerEvents.chestLootTables((event) => {
    // Create loot table, but since id (ResourceLocation) is the same as original, it overrides
    event.addChest("minecraft:end_city_treasure", (loot) => {
        loot.addPool((pool) => {
            // Add loot
            pool.addItem("minecraft:diamond"); // [!code ++]
        });
    });
});
```

```js [KubeJS With Predicates and Modifiers]
ServerEvents.chestLootTables((event) => {
    event.addChest("minecraft:end_city_treasure", (loot) => {
        // [!code ++]
        loot.addPool((pool) => {
            // [!code ++]
            // Add loot
            pool.addItem("minecraft:diamond"); // [!code ++]
            // Add conditional item modifier to loot
            pool.addItem("minecraft:diamond").addConditionalFunction((c) =>
                c.name(Component.aqua("Test Diamond"))
            ); // [!code ++]
            // Add conditional item modifier to loot pool
            pool.addConditionalFunction((c) =>
                c.name(Component.aqua("Test Diamond"))
            ); // [!code ++]
        });

        loot.addPool((pool) => {
            // [!code ++]
            pool.addItem("minecraft:diamond"); // [!code ++]
            // Add predicate to loot
            pool.addItem("minecraft:diamond").survivesExplosion(); // [!code ++]
            // Add predicate to loot pool
            pool.survivesExplosion(); // [!code ++]
        });
        // Add conditional item modifier to loot table
        loot.addConditionalFunction((c) => c.name(Component.aqua("Test Diamond"))); // [!code ++]
    });
});
```

:::

## Available Predicates

-   Predicates available in chest type loot table context.

| Predicate Type | Effect | Statement | KubeJS Native Support | Example |
| :------------: | ------------------------------------------------------------------------------------------------------------- | :----------------------: | :------------------: | :-------------------------------------------------: |
| All | Evaluates a series of loot table predicates, passes if all pass. Can be called from any context. | - | ☐ | [Example](../BasicKnowledge/Predicate.md#all) |
| Any | Evaluates a series of loot table predicates, passes if any pass. Can be called from any context. | - | ☐ | [Example](../BasicKnowledge/Predicate.md#any) |
| Entity Properties | Checks entity in loot table context. Can be called from any context. | entityProperties(..args) | ☑ | [Example](../BasicKnowledge/Predicate.md#entity-properties) |
| Entity Scores | Checks entity's scoreboard scores. | entityScores(..args) | ☑ | [Example](../BasicKnowledge/Predicate.md#entity-scores) |
| Inverted (NOT) | Defines a list of predicates, passes when contained predicates don't pass. | - | ☐ | [Example](../BasicKnowledge/Predicate.md#inverted) |
| Check Location | Checks current position. Requires source from loot table context to detect, always fails if not provided. | - | ☐ | [Example](../BasicKnowledge/Predicate.md#check-location) |
| Random Chance | Generates random number between 0.0-1.0 and checks if less than specified value. Can be called from any context. | randomChance(..args) | ☑ | [Example](../BasicKnowledge/Predicate.md#random-chance) |
| Reference Predicate File | Calls predicate file and returns result. Can be called from any context. | - | ☐ | [Example](../BasicKnowledge/Predicate.md#reference-predicate-file) |
| Check Time | Compares current game time (more precisely, 24000 * days + time of day) with given value. Can be called from any context. | - | ☐ | [Example](../BasicKnowledge/Predicate.md#check-time) |
| Check Value | Compares a number with another number or range. Can be called from any context. | - | ☐ | [Example](../BasicKnowledge/Predicate.md#check-value) |
| Check Weather | Checks current game weather state. Can be called from any context. | - | ☐ | [Example](../BasicKnowledge/Predicate.md#check-weather) |

## Available Item Modifiers

-   Item modifiers available in chest type loot table context.

| Item Modifier Type | Effect | Statement | KubeJS Native Support | Example |
| :----------------: | ------------------------------------------------------------------------------------------------------------------------------------------------- | :-----------------------: | :------------------: | :--------------------------------------------------------------------: |
| Copy NBT | Copies NBT from specified source to item. Only allowed value is "block_entity" | - | ☐ | [Example](../BasicKnowledge/ItemModifier.md#copy-nbt) |
| Random Enchant | Adds random enchantment to item. Enchantment level is also random. | enchantRandomly(..args) | ☑ | [Example](../BasicKnowledge/ItemModifier.md#random-enchant) |
| Enchant With Level | Enchants item with specified enchantment level (approximately equivalent to enchanting at an enchantment table with this level). | enchantWithLevels(..args) | ☑ | [Example](../BasicKnowledge/ItemModifier.md#enchant-with-level) |
| Set Explorer Map | Turns normal map into explorer map pointing to a structure tag. Does nothing if item is not a map. | - | ☐ | [Example](../BasicKnowledge/ItemModifier.md#set-explorer-map) |
| Explosion Chance | If item stack is generated from block explosion, each item has 1/explosion radius probability to disappear; item stacks are divided into individual items for calculation; otherwise does nothing. | - | ☐ | [Example](../BasicKnowledge/ItemModifier.md#explosion-chance) |
| Fill Player Head | Sets player head to specified player's head. Does nothing if item is not player head. | - | ☐ | [Example](../BasicKnowledge/ItemModifier.md#fill-player-head) |
| Furnace Smelt | Converts item to its furnace-smelted counterpart. Does nothing if item is not smeltable. | furnaceSmelt() | ☑ | [Example](../BasicKnowledge/ItemModifier.md#furnace-smelt) |
| Limit Stack Count | Limits item count. | - | ☐ | [Example](../BasicKnowledge/ItemModifier.md#limit-stack-count) |
| Reference Item Modifier | References another item modifier. | - | ☐ | [Example](../BasicKnowledge/ItemModifier.md#reference-item-modifier) |
| Set Attributes | Adds attribute modifiers to item. | - | ☐ | [Example](../BasicKnowledge/ItemModifier.md#set-attributes) |
| Set Banner Patterns | Sets pattern on banner item. Does nothing if item is not a banner. | - | ☐ | [Example](../BasicKnowledge/ItemModifier.md#set-banner-patterns) |
| Set Contents | Sets item contents. | - | ☐ | [Example](../BasicKnowledge/ItemModifier.md#set-contents) |
| Set Item Count | Sets item quantity. | count(..args) | ☑ | [Example](../BasicKnowledge/ItemModifier.md#set-item-count) |
| Set Damage | Sets tool damage value. | damage(..args) | ☑ | [Example](../BasicKnowledge/ItemModifier.md#set-damage) |
| Set Enchantments | Sets item enchantments. | - | ☐ | [Example](../BasicKnowledge/ItemModifier.md#set-enchantments) |
| Set Instrument | Sets goat horn type. Does nothing if item is not goat horn. | - | ☐ | [Example](../BasicKnowledge/ItemModifier.md#set-instrument) |
| Set Loot Table | Sets loot table for container block item. | - | ☐ | [Example](../BasicKnowledge/ItemModifier.md#set-loot-table) |
| Set Lore | Adds description info to item. | - | ☐ | [Example](../BasicKnowledge/ItemModifier.md#set-lore) |
| Set Name | Adds or modifies item custom name. | name(..args) | ☑ | [Example](../BasicKnowledge/ItemModifier.md#set-name) |
| Set NBT | Sets item stack NBT data. | nbt(..args) | ☐ | [Example](../BasicKnowledge/ItemModifier.md#set-nbt) |
| Set Potion | Sets potion effect tag on item. | - | ☐ | [Example](../BasicKnowledge/ItemModifier.md#set-potion) |
| Set Suspicious Stew Effect | Adds status effect to suspicious stew. | - | ☐ | [Example](../BasicKnowledge/ItemModifier.md#set-suspicious-stew-effect) |

