---
authors: ['Gu-meng']
---
# Chest Loot
The following examples use native KubeJS to modify chest loot generation.
## Basic Syntax
### Override Vanilla Chest Loot
In this example, the original End City chest loot is overridden to generate only glass and glass bottles.
```js
ServerEvents.chestLootTables(event=>{
    event.addChest("minecraft:end_city_treasure",loot=>{
        loot.addPool(pool=>{
            pool.addItem("glass")
            pool.addItem("glass_bottle")
            pool.rolls = 3
            console.log(pool.conditions);
        })
    })
})
```
The first parameter of `addChest()` is the chest loot table id to override ([vanilla chest loot table list](../../Digression/LootTableId#chest-loot-table-ids)).

If you do not want to override and instead want to **add on top of existing loot**, use `modify()` with the same parameters as `addChest()`.

To override existing loot, define items in the pool and their [weight ratio](../../Digression/Weight).

If you want items to generate only under certain conditions, use [loot predicates](https://zh.minecraft.wiki/w/%E6%88%98%E5%88%A9%E5%93%81%E8%A1%A8?variant=zh-cn) and [item modifiers](https://zh.minecraft.wiki/w/%E7%89%A9%E5%93%81%E4%BF%AE%E9%A5%B0%E5%99%A8).

### Add a New Chest Loot Table
Adding a new loot table uses the same pattern as overriding a vanilla one.
```js
ServerEvents.chestLootTables(event=>{
    event.addChest("meng:chest_loot",loot=>{
        loot.addPool(pool=>{
            pool.addItem("glass")
            pool.addItem("glass_bottle")
            pool.rolls = 3
            console.log(pool.conditions);
        })
    })
})
```
Here we created a custom loot table with id `meng:chest_loot`.

You can use this command in-game to generate a chest that uses this loot table:
```
/setblock ~ ~ ~ chest{LootTable:"meng:chests/chest_loot"}
```
If you want your custom loot table to appear naturally in world generation, you need to place it via [world generation events]().
