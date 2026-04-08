---
authors: ['Gu-meng']
---
# Global Loot
KubeJS does not provide dedicated event hooks for every loot source (for example cat gifts, piglin bartering, sniffer loot, etc.). In those cases, use global loot tables directly. If you can find the in-game loot table id, you can modify it with global loot.
## Override
```js
ServerEvents.genericLootTables(e=>{
    e.addGeneric("minecraft:gameplay/cat_morning_gift",loot=>{
        loot.addPool(pool=>{
            pool.addItem("diamond")
            pool.addItem("iron_ingot")
            pool.addItem("apple")
        })
    })
})
```
This overrides cat morning gifts so cats can bring diamonds, apples, or iron ingots.

## Add Entries
```js
ServerEvents.genericLootTables(e=>{
    e.modify("minecraft:gameplay/piglin_bartering",loot=>{
        let json = [{
            "type":"minecraft:item",
            "name":"minecraft:diamond",
            "weight":80
        }]
        let poolArr = loot.pools.get(0).asJsonObject.get("entries").asJsonArray
        poolArr.addAll(json)
    })
})
```
This adds a high-weight diamond entry to piglin bartering loot.
