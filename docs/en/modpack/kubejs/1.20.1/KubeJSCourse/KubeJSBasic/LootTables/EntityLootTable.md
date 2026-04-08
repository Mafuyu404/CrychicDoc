---
authors: ['Gu-meng']
---
# Entity Loot
This section introduces basic KubeJS syntax and common parameters for entity loot.
## Basic Syntax
```js
ServerEvents.entityLootTables(event => {
    event.addEntity("minecraft:pig", loot => {
        loot.addPool(pool => {
            pool.addItem("diamond").weight(1).count([3, 6])
            pool.addItem("iron_ingot").weight(2)
        })
    })
})
```
In the code above, pig drops are changed to either 3-6 diamonds or 1 iron ingot. Iron has weight 2 and diamonds have weight 1 ([weight concept](../../Digression/Weight)).

`pool.addItem("diamond").weight(1).count([3, 6])` sets weight and count separately. You can also combine them as `pool.addItem("diamond",1,[3,6])`.

If you want to add extra drops to existing entity loot, you can do this:
```js
ServerEvents.entityLootTables(event => {
    event.modifyEntity("minecraft:pig", loot => {
        loot.addPool(pool => {
            pool.addItem("pig_spawn_egg")
        })
})
```

Other than the event method (`addEntity` vs `modifyEntity`), the parameters inside are the same.
## Common Methods and Examples
The examples below all use this top-level structure:
```js
ServerEvents.entityLootTables(event => {
    event.modifyEntity("minecraft:pig", loot => {
        loot.addPool(pool => {
            //code
        }
    }
})
```
`code` below means the content inside the pool callback.
### setUniformRolls
Random number of rolls from the pool.
```js
pool.setUniformRolls(1,3)
```
Parameter 1: minimum roll count

Parameter 2: maximum roll count
### enchantRandomly
Apply random enchantments to dropped items.
```js
pool.enchantRandomly("minecraft:smite")
```
Parameter: enchantment id
### lootingEnchant
Make drop count affected by Looting and set values.
```js
pool.lootingEnchant(2,10)
```
Parameter 1: extra drop amount per Looting level

Parameter 2: max drop count
### killedByPlayer
Require the entity to be killed by a player.
```js
pool.killedByPlayer()
```
### addEmpty
Set the weight ratio for an empty result.
```js
pool.addEmpty(2)
```
Parameter: weight ratio
### furnaceSmelt
Apply furnace-smelting behavior to drops (similar to furnace smelting).
```js
pool.furnaceSmelt()
```
### randomChanceWithLooting
Random chance affected by Looting.
```js
pool.randomChanceWithLooting(0.1,0.2)
```
Parameter 1: chance without Looting

Parameter 2: additional chance per Looting level
### entityProperties
Match entity properties; if matched, pool items can drop.
```js
pool.entityProperties("killer", {
    type:"minecraft:player",
    equipment: {
            mainhand: {
                items: [
                    "minecraft:diamond_sword"
                ],
            predicates:{
                enchantments:{
                    enchantments:["minecraft:silk_touch"]
                }
            } 
        }
    }
})
```
Checks whether the pig killer is a player using a diamond sword with Silk Touch.

Parameter 1: entity target (optional: `"[this](../../Digression/LootContext#this)"`, `"[killer_player](../../Digression/LootContext#killer_player)"`, `"[killer](../../Digression/LootContext#killer)"`, `"[direct_killer](../../Digression/LootContext#direct_killer)"`)

Parameter 2: JSON predicate under `entity_properties` in loot-table predicates
### addTag
Add tag-based loot entries.
```js
pool.addTag("minecraft:beds",true)
```
Parameter 1: tag id

Parameter 2: whether to randomly pick one item from the tag
### rolls
Number of rolls for this pool.
```js
pool.rolls = 3
```
