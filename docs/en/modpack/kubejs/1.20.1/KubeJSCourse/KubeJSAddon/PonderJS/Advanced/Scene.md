---
authors: ['Gu-meng', 'Qi-Month']
---
> ### encapsulateBounds
>
> `scene.encapsulateBounds(blockPos: BlockPos): void`
>
> This is a utility method added by PonderJS. It is equivalent to:
>
> ```js
> scene.addInstruction((ponderScene) => {
>   let world = ponderScene.world;
>   if (world != null) {
>     world.bounds.encapsulate(blockPos);
>   }
> });
> ```
>
> It reshapes the `PonderWorld` `BoundingBox` to include the provided block position.
