---
authors: ['Gu-meng']
---
# Prevent Entity Travel to a Specific Dimension
Main topics in this chapter: `ForgeEvents` and Forge's `EntityTravelToDimensionEvent`. All code in this chapter belongs in `startup_scripts`.

## Full Code
```js
const $EntityTravelToDimensionEvent = Java.loadClass("net.minecraftforge.event.entity.EntityTravelToDimensionEvent")

ForgeEvents.onEvent($EntityTravelToDimensionEvent, event => {
    let resourceKey = event.dimension;
    if (resourceKey.getPath() == "the_nether") {
        event.setCanceled(true)
    }
})
```
The code above checks whether the target dimension is the Nether. If so, it cancels the event and blocks dimension travel.

## Stage Restriction
Stage restriction is mainly for players, because only players have stages.
```js
const $EntityTravelToDimensionEvent = Java.loadClass("net.minecraftforge.event.entity.EntityTravelToDimensionEvent")
const $ServerPlayer = Java.loadClass("net.minecraft.server.level.ServerPlayer")

ForgeEvents.onEvent($EntityTravelToDimensionEvent, event => {
    let resourceKey = event.dimension;
    /**
     * @type {Internal.ServerPlayer}
     */
    let player = event.entity;
    if (resourceKey.getPath() == "the_nether") {
        if (player instanceof $ServerPlayer){
            if (!player.stages.has("nether")){
                event.setCanceled(true)
            }
        }
    }
})
```
