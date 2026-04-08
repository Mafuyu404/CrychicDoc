---
authors: ['Gu-meng']
---
# Modify Create Pipe Fluid Collision Results
Main topics in this chapter: `ForgeEvents` and Create's `PipeCollisionEvent.Spill`. All code in this chapter belongs in `startup_scripts`.

Reference for `PipeCollisionEvent`: [https://github.com/Creators-of-Create/Create/blob/mc1.20.1/dev/src/main/java/com/simibubi/create/api/event/PipeCollisionEvent.java](https://github.com/Creators-of-Create/Create/blob/mc1.20.1/dev/src/main/java/com/simibubi/create/api/event/PipeCollisionEvent.java)
## Full Code
```js
const $PipeCollisionEvent = Java.loadClass("com.simibubi.create.api.event.PipeCollisionEvent")

ForgeEvents.onEvent($PipeCollisionEvent.Spill, event => {
    let block = event.getState().block;
    if (block.id == "minecraft:stone"){
        event.setState(Block.getBlock('minecraft:netherrack').defaultBlockState())
    }
})
```
The code above checks the block generated when fluid spills from a pipe. If the result is stone, it replaces it with netherrack.
