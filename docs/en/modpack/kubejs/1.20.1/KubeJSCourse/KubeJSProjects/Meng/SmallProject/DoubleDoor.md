# Double Door Sync
Main topics in this chapter: block right-click events and changing block states. All code in this chapter belongs in `server_scripts`.

Mods and versions used:
1. jei-1.20.1-forge-15.3.0.4
2. rhino-forge-2001.2.2-build.18
3. architectury-9.2.14-forge
4. kubejs-forge-2001.6.5-build.14
5. probejs-6.0.1-forge

## Full Code
```js
const $DoorBlock = Java.loadClass("net.minecraft.world.level.block.DoorBlock");
const $Boolean = Java.loadClass("java.lang.Boolean");

BlockEvents.rightClicked(event => {
    if (event.getHand() != "MAIN_HAND") return;
    if (event.player.isShiftKeyDown()) return;
    let block = event.block;
    let bs = block.blockState;
    if (!(bs.block instanceof $DoorBlock)) return;
    if (block.id == 'minecraft:iron_door' || bs.block.idLocation.namespace == "create") return;
    let open = $DoorBlock.OPEN,
        facing = $DoorBlock.FACING,
        hinge = $DoorBlock.HINGE, neighborBlock;
    if (bs.getValue(hinge) == $DoorHingeSide.LEFT) {
        if (bs.getValue(facing) == $Direction.SOUTH) neighborBlock = block.west;
        else if (bs.getValue(facing) == $Direction.WEST) neighborBlock = block.north;
        else if (bs.getValue(facing) == $Direction.NORTH) neighborBlock = block.east;
        else if (bs.getValue(facing) == $Direction.EAST) neighborBlock = block.south;

    } else {
        if (bs.getValue(facing) == $Direction.SOUTH) neighborBlock = block.east;
        else if (bs.getValue(facing) == $Direction.WEST) neighborBlock = block.south;
        else if (bs.getValue(facing) == $Direction.NORTH) neighborBlock = block.west;
        else if (bs.getValue(facing) == $Direction.EAST) neighborBlock = block.north;
    }
    if (neighborBlock.id == block.id && neighborBlock.blockState.getValue(hinge) != bs.getValue(hinge)) {
        neighborBlock.setBlockState(neighborBlock.blockState.setValue(open, !bs.getValue(open) ? $Boolean.TRUE : $Boolean.FALSE), 3)
    }
})
```
This script makes paired doors open and close together when the player interacts with one door.

If the player is sneaking while opening or closing a door, the double-door sync will not trigger.
