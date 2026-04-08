---
authors: ['Gu-meng']
---
# Falling-Item Crafting
Topics covered: recipe checks, item entities, entity spawn events, and level tick events.
Mods and versions used:
1. rhino-forge-2001.2.2-build.18
2. architectury-9.2.14-forge
3. kubejs-forge-2001.6.5-build.14
4. probejs-6.0.1-forge

## Full Code
Below is the full code with partial comment explanations. For full walkthrough details, check Gu-meng's Bilibili video.
```js
// Record data for falling item entities
let itemFallList = {};
// Recipe list
let fallItem = [
    {
        inputItem: "minecraft:cobblestone",
        outputItem: 'minecraft:gravel',
        spaceBetween: 10
    }
]

EntityEvents.spawned("item", event => {
    /**
     * @type {Internal.ItemEntity}
     */
    let itemEntity = event.getEntity();
    fallItem.forEach(value => {
        // Check the input item
        if (itemEntity.getItem().getId() != value.inputItem) return;
        // Set pickup delay to 32767 so it cannot merge (side effect: cannot be picked up)
        itemEntity.pickUpDelay = 32767;
        // Record item count
        let count = itemEntity.getNbt().get("Item").getInt("Count")
         // Save values
        itemFallList[itemEntity.getUuid()] = {
            dimension: event.getLevel().getDimension(),
            y: itemEntity.getY(),
            output: value.outputItem,
            count: count,
            spaceBetween: value.spaceBetween
        }
    })
})

LevelEvents.tick(event => {
    if (event.server.tickCount % 5 != 0) return
    if (Object.keys(itemFallList).length == 0) return
    for (let key in itemFallList) {
        let fallValue = itemFallList[key];
        if (fallValue.dimension == event.getLevel().getDimension()) {
            try {
                let entity = event.getLevel().getEntity(key)
                if (entity.onGround()) {
                    if (fallValue.y - entity.getY() >= fallValue.spaceBetween) {
                        entity.setItem(Item.of(fallValue.output, fallValue.count))
                    }
                    entity.pickUpDelay = 20;
                    delete itemFallList[key]
                }
            } catch (e) {
                console.warn(e);
                delete itemFallList[key]
            }
        }
    }
})
```

## Notes
1. Recipe checks are string-based, not `ItemStack`, so NBT cannot be checked directly. Modify this if needed.
2. This project is only an example; many parts are not necessarily optimal and can be improved.
3. If you improve this project, you can upload your revised code to the [Gitee repository](https://gitee.com/gumengmengs/kubejs-course).
4. You can copy and use this code directly. To add recipes, edit the `fallItem` array.
5. For cases like water pushing items upward before they fall, you can track each item's highest `y` value in the level tick handler.
