---
authors: ['Gu-meng']
---
# Block Loot
In Minecraft, block drops are also loot tables. A mined block does not always drop itself, and some drops can be affected by Fortune. What drops depends on what is rolled from the loot pool.
## Override Block Loot
The following example creates a Lucky-Block-like random item drop behavior:
```js
ServerEvents.blockLootTables(e=>{
    Block.getTypeList().forEach(block=>{
        e.addBlock(block,l=>{
            l.addPool(p=>{
                Ingredient.of("@minecraft").getItemIds().forEach(itemId=>{
                    p.addItem(Item.of(itemId))
                })
            })
        })
    })
})
```
In this example, all mined blocks are changed to drop random Minecraft items.

## Add to Existing Loot
The following adds entries to dirt's original loot so it can drop dirt plus the added item:
```js
ServerEvents.blockLootTables(e=>{
    e.modifyBlock('minecraft:dirt',loot=>{
        let pool = [{
            "type":"minecraft:item",
            "name":"minecraft:diamond"
        }]
        let arr = loot.pools.get(0).asJsonObject.get("entries").asJsonArray
        arr.addAll(pool)
    })
})
```
In `kubejs-forge-2001.6.5-build.14`, using `JsonArray.add()` directly may throw an error. This example uses `addAll` instead. Since `addAll` needs an array, the entry JSON is wrapped in `[]`.
