---
authors: ['Gu-meng']
---
# Fishing Loot
This chapter provides simple native KubeJS examples for fishing loot.
## Basic Syntax
In the code below, `addFishing` overrides the original loot table (use `modify` if you only want to add to the existing table):
```js
ServerEvents.fishingLootTables(event=>{
    event.addFishing("minecraft:fish",loot=>{
        loot.addPool(pool=>{
            pool.addItem("arrow")
            pool.addItem("glass")
            pool.addItem("bamboo_planks")
        })
    })
})
```

## Remove Loot Entries
Sometimes you do not want to add or modify loot, but remove entries from a pool:
```js
ServerEvents.fishingLootTables(event=>{
    event.modify("treasure",loot=>{
        loot.pools.forEach(pool =>{
            let pArr = pool.get("entries").asJsonArray
            for (let index = 0; index < pArr.size(); index++) {
                let vName = pArr.get(index).asJsonObject.get("name").asString
                if (vName == "minecraft:bow"){
                    pArr.remove(pArr.get(index))
                }   
            }
        })
    })
})
```
In the example above, the bow is removed from the fishing treasure pool.
