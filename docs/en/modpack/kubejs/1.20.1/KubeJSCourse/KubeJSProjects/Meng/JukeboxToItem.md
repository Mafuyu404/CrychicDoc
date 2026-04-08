---
authors: ['Gu-meng']
---
# Convert a Music Disc into an Item
Topics covered: jukebox block entities, block entity data, and popping items from a block.
Mods and versions used:
1. rhino-forge-2001.2.2-build.18
2. architectury-9.2.14-forge
3. kubejs-forge-2001.6.5-build.14
4. probejs-6.0.1-forge

## Project Code
```js
BlockEvents.rightClicked("jukebox", event => {
    let block = event.getBlock()
    // Check whether the jukebox is playing after right-click
    if (block.entityData.getBoolean("IsPlating")){
        // Schedule a check 3 seconds later
        event.server.scheduleInTicks(20 * 3, () => {
            let newBlock = event.level.getBlock(block.pos)
            if (newBlock.id == 'minecraft:air') return
            let blockEntity = newBlock.getEntity()
            let blockEntityData = newBlock.getEntityData()
            // Check whether the item is air to determine whether it is still playing
            // You can also check blockEntityData.getBoolean("IsPlating")
            if (!blockEntity.getFirstItem().is("air")) {
                // Check whether playback time is greater than 2.8 (close to target, but avoid exact equality)
                if ((blockEntityData.getInt("TickCount") - blockEntityData.getInt("RecordStartTick")) / 20 >= 2.8) {
                    // Clear the item inside the jukebox
                    blockEntity.clearContent()
                    // Pop a new item above the block
                    newBlock.popItemFromFace("stone", "up")
                }
            }
        })
    }
})
```

## Notes
1. This project is only an example; many parts are not necessarily optimal and can be improved.
2. If you improve this project, you can upload your revised code to the [Gitee repository](https://gitee.com/gumengmengs/kubejs-course).
3. Many values in the code are adjustable parameters and can be modified as needed.
