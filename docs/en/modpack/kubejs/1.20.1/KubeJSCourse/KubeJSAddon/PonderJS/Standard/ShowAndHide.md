---
authors: ['Gu-meng', 'Qi-Month']
---
# About Showing and Hiding
This page introduces additional functions for showing and hiding blocks.
 
## Show Blocks

```js
// Show blocks in the rectangular area defined by [2, 1, 1] and [1, 1, 2],
// using a top-down falling animation, and mark that area as shown
scene.world.showSection([2, 1, 1, 1, 1, 2], Direction.down);

// Show the block at [2, 1, 1] with a top-down falling animation,
// and mark it as shown
scene.world.showSection([2, 1, 1], Direction.down);

// Same as above
scene.world.showSection([2, 1, 1], Facing.down);

// Same as above
scene.world.showSection([2, 1, 1], 'down');
```
About directions: [Direction & Facing](../Internal/Facing.md)

> The following code is excerpted from [scene_world_function.md](../Internal/SceneWorldFunction)

```js
showSection(arg0: Internal.Selection_, arg1: Internal.Direction_): void_;
```

## Hide Blocks

```js
// Hide the block at [2, 1, 1] with a top-down falling animation
// and mark it as hidden
scene.world.hideSection([2, 1, 1], 'down');
```

> The following code is excerpted from [scene_world_function.md](../Internal/SceneWorldFunction)

```js
hideSection(arg0: Internal.Selection_, arg1: Internal.Direction_): void_;
```

## Other Functions (To Be Documented)

Waiting for future documentation updates.

> The following code is excerpted from [scene_world_function.md](../Internal/SceneWorldFunction)

```js
hideIndependentSection(arg0: Internal.ElementLink_<Internal.WorldSectionElement>, arg1: Internal.Direction_, arg2: number): void_;
configureCenterOfRotation(arg0: Internal.ElementLink_<Internal.WorldSectionElement>, arg1: Vec3d_): void_;
showIndependentSectionImmediately(arg0: Internal.Selection_): Internal.ElementLink<Internal.WorldSectionElement>;
showIndependentSection(arg0: Internal.Selection_, arg1: Internal.Direction_): Internal.ElementLink<Internal.WorldSectionElement>;
hideIndependentSectionImmediately(arg0: Internal.ElementLink_<Internal.WorldSectionElement>): void_;
glueBlockOnto(arg0: BlockPos_, arg1: Internal.Direction_, arg2: Internal.ElementLink_<Internal.WorldSectionElement>): void_;
showSectionAndMerge(arg0: Internal.Selection_, arg1: Internal.Direction_, arg2: Internal.ElementLink_<Internal.WorldSectionElement>): void_;
makeSectionIndependent(arg0: Internal.Selection_): Internal.ElementLink<Internal.WorldSectionElement>;
showIndependentSection(arg0: Internal.Selection_, arg1: Internal.Direction_, arg2: number): Internal.ElementLink<Internal.WorldSectionElement>;
moveSection(arg0: Internal.ElementLink_<Internal.WorldSectionElement>, arg1: Vec3d_, arg2: number): void_;
hideIndependentSection(arg0: Internal.ElementLink_<Internal.WorldSectionElement>, arg1: Internal.Direction_): void_;
rotateSection(arg0: Internal.ElementLink_<Internal.WorldSectionElement>, arg1: number, arg2: number, arg3: number, arg4: number): void_;
```
