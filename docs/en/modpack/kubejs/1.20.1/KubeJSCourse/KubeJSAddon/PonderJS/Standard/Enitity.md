---
authors: ['Gu-meng', 'Qi-Month']
---
# About Entities
This page introduces additional entity-related functions.
 
## Create Entities

Example:

```js
// Create a minecraft:sheep entity at [2.5, 1, 2.5] and assign it to _sheep
var _sheep = scene.world.createEntity("minecraft:sheep", [2.5, 1, 2.5]);

// Create a minecraft:sheep entity at [2.5, 1, 2.5] and set its name to jeb_
scene.world.createEntity("minecraft:sheep", [2.5, 1, 2.5], event => {
    event.setCustomName("jeb_");
});
```

Return type: **Internal.ElementLink\<Internal.EntityElement\>**

> The following code is excerpted from [scene_world_function.md](../Internal/SceneWorldFunction)

```js
createEntity(arg0: Internal.EntityType_\<any\>, arg1: Vec3d_, arg2: Internal.Consumer_\<Internal.Entity\>): Internal.ElementLink\<Internal.EntityElement\>;
createEntity(arg0: Internal.EntityType_\<any\>, arg1: Vec3d_): Internal.ElementLink\<Internal.EntityElement\>;

createEntity(arg0: Internal.Function_\<Internal.Level, Internal.Entity\>): Internal.ElementLink\<Internal.EntityElement\>;
```

## Modify Entities

Example:

```js
// Create a minecraft:sheep entity at [2.5, 1, 2.5] and assign it to _sheep
var _sheep = scene.world.createEntity("minecraft:sheep", [2.5, 1, 2.5]);

// Modify entity _sheep
scene.world.modifyEntity(_sheep, event => {

    // Set its name to jeb_
    event.setCustomName("jeb_");

    // Show hurt animation
    event.animateHurt(3);

    // Move it by [1, 0, 1] using self movement
    event.move("self", [1, 0, 1]);
});
```

> The following code is excerpted from [scene_world_function.md](../Internal/SceneWorldFunction)

```js
modifyEntity(arg0: Internal.ElementLink_\<Internal.EntityElement\>, arg1: Internal.Consumer_\<Internal.Entity\>): void_;
```

## Create Item Entities

Example:

```js
// Create a minecraft:iron_ingot item entity at [2, 1, 2] with initial velocity [0.5, 0, 0]
scene.world.createItemEntity([2, 1, 2], [0.5, 0, 0], 'minecraft:iron_ingot');
```
Below are **([2, 1, 2], [0.5, 0, 0])** and **([3, 1, 2], [0, 0, 0])**, respectively.

![ItemEntity_move](/imgs/PonderJS/ItemEntity_move.gif)

Return type: **Internal.ElementLink\<Internal.EntityElement\>**

> The following code is excerpted from [scene_world_function.md](../Internal/SceneWorldFunction)

```js
createItemEntity(arg0: Vec3d_, arg1: Vec3d_, arg2: Internal.ItemStack_): Internal.ElementLink\<Internal.EntityElement\>;
```

## Remove Entities

Example:

```js
// Create a minecraft:sheep entity at [2.5, 1, 2.5] and assign it to _sheep
var _sheep = scene.world.createEntity("minecraft:sheep", [2.5, 1, 2.5]);

// Remove entity _sheep
scene.world.removeEntity(_sheep);
```

> The following code is excerpted from [scene_world_function.md](../Internal/SceneWorldFunction)

```js
removeEntity(arg0: Internal.ElementLink_\<Internal.EntityElement\>): void_;
```
