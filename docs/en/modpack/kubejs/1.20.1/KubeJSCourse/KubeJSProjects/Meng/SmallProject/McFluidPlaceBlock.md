---
authors: ['Gu-meng']
---
# Change Vanilla Fluid Collision Block Result
Main topics in this chapter: `ForgeEvents` and Forge's `BlockEvent.FluidPlaceBlockEvent`. All code in this chapter belongs in `startup_scripts`.

## Full Code
```js
let $BlockEvent = Java.loadClass("net.minecraftforge.event.level.BlockEvent")

ForgeEvents.onEvent($BlockEvent.FluidPlaceBlockEvent,event=>{
    let block = event.getNewState().getBlock();
    if (block.id == "minecraft:stone"){
        event.setNewState(Block.getBlock('minecraft:netherrack').defaultBlockState());
    }
})
```
The code above changes the result of lava-water collision from stone to netherrack.
