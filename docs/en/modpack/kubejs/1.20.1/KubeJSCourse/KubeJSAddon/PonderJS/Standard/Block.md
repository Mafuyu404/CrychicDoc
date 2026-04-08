---
authors: ['Gu-meng', 'Qi-Month']
---
# About Blocks
This page introduces additional block-related functions.

## Place Blocks

According to [scene_world_function.md](../Internal/SceneWorldFunction), there are two placement APIs:

```js
setBlocks(arg0: Internal.Selection_, arg1: boolean_, arg2: Internal.BlockState_): void_;
setBlocks(arg0: Internal.Selection_, arg1: Internal.BlockState_): void_;
setBlocks(arg0: Internal.Selection_, arg1: Internal.BlockState_, arg2: boolean_): void_;

setBlock(arg0: BlockPos_, arg1: Internal.BlockState_, arg2: boolean_): void_;
```

**setBlocks** was already introduced in [Ponder](../Ponder.md), so this section focuses on **setBlock**.

The first parameter type for **setBlocks** and **setBlock** is different.

**setBlocks** can place many blocks at once using selections like `[3, 1, 1, 1, 1, 3]`, while **setBlock** places only one block at a time.

```js
// Place an iron block at [2, 1, 2]. If a block already exists there, destroy it first.
scene.world.setBlock([2, 1, 2], "minecraft:iron_block", true);
```

## Destroy Blocks

Example:

```js
// Destroy the block at [2, 1, 2]
scene.world.destroyBlock([2, 1, 2]);
```
If there is no block at that position, no destroy particles will appear.

> The following code is excerpted from [scene_world_function.md](../Internal/SceneWorldFunction)

```js
destroyBlock(arg0: BlockPos_): void_;
```

The first parameter type is the same as `setBlock`, meaning `single position only; no range destroy`.

## Replace Blocks

Example:

```js
// Replace the block at [2, 1, 2] with an iron block and show destroy particles
scene.world.replaceBlocks([2, 1, 2], "minecraft:iron_block", true);

// Replace all blocks in the rectangular area defined by [2, 1, 3] and [3, 1, 3] with iron blocks, without destroy particles
scene.world.replaceBlocks([2, 1, 3, 3, 1, 3], "minecraft:iron_block", false);
```
If there is no block in a target position, it will not be replaced and no destroy particles will appear.

> The following code is excerpted from [scene_world_function.md](../Internal/SceneWorldFunction)

```js
replaceBlocks(arg0: Internal.Selection_, arg1: Internal.BlockState_, arg2: boolean_): void_;
```

The first parameter type is the same as `setBlocks`, so it `supports range replacement`.

## Set Block State

Example:

```js
scene.world.setBlocks([2, 1, 2], 'create:cogwheel', true);

// Set the block at [2, 1, 2] with axis=x and waterlogged=true, without destroy particles
scene.world.modifyBlock([2, 1, 2], state => state.with("axis", "x").with("waterlogged", "true"), false);

// Set the block at [2, 1, 2] to an oak trapdoor (open=true style), with destroy particles
scene.world.modifyBlock([2, 1, 2], () => Block.id("minecraft:oak_trapdoor").with("type", "top"), true);

// Set all blocks in the rectangular area defined by [2, 1, 3] and [3, 1, 3] to oak trapdoors, with destroy particles
scene.world.modifyBlocks([2, 1, 3, 3, 1, 3], () => Block.id("minecraft:oak_trapdoor").with("type", "top"), true);
```

> The following code is excerpted from [scene_world_function.md](../Internal/SceneWorldFunction)

```js
modifyBlocks(arg0: Internal.Selection_, arg1: Internal.BlockStateFunction_, arg2: boolean_): void_;
modifyBlocks(arg0: Internal.Selection_, arg1: boolean_, arg2: Internal.BlockStateFunction_): void_;
modifyBlocks(arg0: Internal.Selection_, arg1: Internal.BlockStateFunction_): void_;

modifyBlock(arg0: BlockPos_, arg1: Internal.BlockStateFunction_, arg2: boolean_): void_;
```

## Set Block NBT

Example:

```js
scene.world.setBlocks([2, 1, 2], 'create:cogwheel', true);

// Set Speed=16 in the block NBT at [2, 1, 2]
scene.world.modifyBlockEntityNBT([2, 1, 2], nbt => { nbt.Speed = 16 });

// Same as above; the purpose of the second parameter is currently unclear
scene.world.modifyBlockEntityNBT([2, 1, 2], true, nbt => { nbt.Speed = 16 });
```

> The following code is excerpted from [scene_world_function.md](../Internal/SceneWorldFunction)

```js
modifyBlockEntityNBT(arg0: Internal.Selection_, arg1: boolean_, arg2: Internal.Consumer_<Internal.CompoundTag>): void_;
modifyBlockEntityNBT(arg0: Internal.Selection_, arg1: Internal.Consumer_<Internal.CompoundTag>): void_;
```

## Other Functions (To Be Documented)

Waiting for future documentation updates.

> The following code is excerpted from [scene_world_function.md](../Internal/SceneWorldFunction)

```js
modifyBlockEntity<T extends Internal.BlockEntity>(arg0: BlockPos_, arg1: T, arg2: Internal.Consumer_<T>): void_;
restoreBlocks(arg0: Internal.Selection_): void_;
glueBlockOnto(arg0: BlockPos_, arg1: Internal.Direction_, arg2: Internal.ElementLink_<Internal.WorldSectionElement>): void_;
cycleBlockProperty(arg0: BlockPos_, arg1: Internal.Property_<any>): void_;
incrementBlockBreakingProgress(arg0: BlockPos_): void_;
```
