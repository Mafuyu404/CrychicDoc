---
authors: ['Gu-meng', 'Qi-Month']
---
# Practical Tools

> You may need some basic concepts to write PonderJS comfortably, and a few practical tools to speed up scene creation.



## What Is a Ponder Scene?

A Ponder scene is not a mini-world/dimension. It is only a scene renderer with specific behaviors.

Block entities and normal entities inside it are not ticked, and multi-state texture animations do not auto-update.

That means basin auto-output, block state visual updates, entity interaction animations, and similar effects must be scripted manually.



The following snippet is from the official Forge Create source:  
`Create/src/main/java/com/simibubi/create/infrastructure/ponder/scenes/ProcessingScenes.java` (Ponder scene for compacting with a Mechanical Press):

```java
BlockPos basin = util.grid.at(1, 2, 2);
        BlockPos pressPos = util.grid.at(1, 4, 2);                        // Mechanical press position
        Vec3 basinSide = util.vector.blockSurface(basin, Direction.WEST); // Basin side

        ItemStack copper = new ItemStack(Items.COPPER_INGOT);             // Copper item
        ItemStack copperBlock = new ItemStack(Items.COPPER_BLOCK);        // Copper block item

        scene.overlay.showText(60)
            .pointAt(basinSide)
            .placeNearTarget()
            .attachKeyFrame()
            .text("Pressing items held in a Basin will cause them to be Compacted");
        scene.idle(40);

        scene.overlay.showControls(new InputWindowElement(util.vector.topOf(basin), Pointing.DOWN).withItem(copper),
            30);
        scene.idle(30);
        Class<MechanicalPressBlockEntity> type = MechanicalPressBlockEntity.class;
        scene.world.modifyBlockEntity(pressPos, type, pte -> pte.getPressingBehaviour()
            .start(Mode.BASIN));     // Start pressing
        scene.idle(30);    // Wait for pressing animation (yes, this is the way)
        scene.world.modifyBlockEntity(pressPos, type, pte -> pte.getPressingBehaviour()
            .makeCompactingParticleEffect(util.vector.centerOf(basin), copper));    // Generate pressing particles
        scene.world.modifyBlockEntityNBT(util.select.position(basin), BasinBlockEntity.class, nbt -> {
            nbt.put("VisualizedItems",
                NBTHelper.writeCompoundList(ImmutableList.of(IntAttached.with(1, copperBlock)), ia -> ia.getValue()
                    .serializeNBT()));
        });    // Add VisualizedItems NBT to basin to produce output animation
        scene.idle(4);    // Wait for item drop animation
        scene.world.createItemOnBelt(util.grid.at(1, 1, 1), Direction.UP, copperBlock);// Spawn the basin output item "falling" onto the belt
        scene.idle(30);
```

As shown above, in Ponder you do not place machines, supply power, and get automatic processing like normal gameplay.  
All animations/events are manually driven.

So in PonderJS, these animations are also implemented manually:

```javascript
onEvent("ponder.registry", (event) => {
    event.create("minecraft:raw_iron_block") // Raw iron block
        .scene(
            "potato_raw_block_scene",
            "Potatoes and raw iron block...",
            "kubejs:potato_raw_block_scene",
            (scene, util) => {
                const $BasinBlockEntity = java('com.simibubi.create.content.processing.basin.BasinBlockEntity');
                const $MechanicalPressBlockEntity = java('com.simibubi.create.content.kinetics.press.MechanicalPressBlockEntity');
                const $PressingBehaviour = java('com.simibubi.create.content.kinetics.press.PressingBehaviour');
                var depot = util.grid.at(1, 0, 0);
                var basin = util.grid.at(1, 1, 1);
                var press = util.grid.at(1, 3, 1);
                scene.world.setFilterData(basin, $BasinBlockEntity, Item.of("minecraft:air"));
                scene.world.modifyBlock(basin, state = state.with("facing", "down"), false);// Basin output direction corresponds to the block state's facing
                scene.world.showSection(basin, Direction.UP);
                scene.idle(20);
                scene.overlay.showoutline(PonderPalette.GREEN, new Object(), basin, 30);
                scene.overlay.showFilterslotInput(util.vector.of(1.5, 1.75, 1), Direction.NORTH, 30);
                scene.idle(20);
                scene.world.setFilterData(basin, $BasinBlockEntity, Item.of("minecraft:raw_iron_block"));
                scene.idle(40);
                scene.addKeyframe();
                let potato = scene.world.createItemEntity([1.5, 2.5, 1.5], [0, 0, 0], "9x potato");
                scene.idle(7);
                scene.world.modifyEntity(potato, e => { e.kill(); });// Remove dropped item after it falls into the basin
                scene.idle(40);
                scene.world.setkineticSpeed(press, 64);
                scene.world.showSection(press, Direction.DOWN);
                scene.world.showSection(depot, Direction.SOUTH);
                scene.idle(15);
                scene.world.modifyBlock(basin, state = state.with("facing", "north"), false);
                scene.addKeyframe();
                scene.world.modifyBlockEntity(press, $MechanicalPressBlockEntity, pte => { pte.getPressingBehaviour().start($PressingBehaviour.Mode.BASIN); })
                scene.idle(13);
                // In 1.18.2, appending NBT failed here, so a dropped-item workaround is used
                let raw_iron_block = scene.world.createItemEntity(util.vector.centerof(basin), [0, -0.2, -0.25], "raw_iron_block");
                scene.idle(5);
                scene.world.modifyEntity(raw_iron_block, e => { e.kill(); });// Remove dropped item after it falls onto the depot
                scene.world.createItemOnBeltLike(depot, Direction.UP, "raw_iron_block");// Spawn the basin output item "falling" onto the depot
                scene.idle(10);
            }
        );
});
```

## How to Implement a Specific Effect

Ponder is designed by Create to explain its own systems, so effects not shown in official Ponder scenes are usually hard or impossible to reproduce.

But this suggests a good workflow:

> If you want a specific effect, first find an official Ponder scene that already does it.
> 
> Then locate its implementation in the [official source code](https://github.com/Creators-of-Create/Create/blob/mc1.18/dev/src/main/java/com/simibubi/create/infrastructure/ponder/scenes/).
> 
> Finally, translate that logic into JS.

Do not forget ProbeJS. When confused, reading Create’s source is often the fastest way forward.



## scene utils

For easier positioning/selection in scenes, Ponder provides a `util` object in addition to `scene`:

![utils screenshot](/imgs/PonderJS/utils.png)

Methods in `util.grid` select a single position.

`util.select` selects an area.

`util.vector` selects a specific vector (for example, the facing of one block side).

The rest is already covered clearly by ProbeJS hints and official usage.
