---
authors: ['Gu-meng', 'Qi-Month']
---
## Advanced Section Usage (recommended after learning the basics)

> ### showIndependentSection
>
> `scene.world.showIndependentSection(selection: Selection, fadeInDirection: Direction): ElementLink<WorldSectionElement>`
>
> Shows a section and returns an `ElementLink` pointing to that section (animation lasts 15 ticks).
>
> ```js
> const example_link = scene.world.showIndependentSection(
>   [2, 1, 2],
>   Direction.down
> );
> ```

> ### showIndependentSection
>
> `scene.world.showIndependentSection(selection: Selection, fadeInDirection: Direction, fadeInDuration: number): ElementLink<WorldSectionElement>`
>
> Compared with the method above, this one adds a `fadeInDuration` parameter.
>
> This parameter is an integer used to change animation duration.

> ### showIndependentSectionImmediately
>
> `scene.world.showIndependentSectionImmediately(selection: Selection): ElementLink<WorldSectionElement>`
>
> A simplified version of the two methods above. It shows a section immediately and returns its `ElementLink`.
>
> As a tradeoff, the default `fadeInDirection` is `Direction.down`.

> ### showSectionAndMerge
>
> `scene.world.showSectionAndMerge(selection: Selection, fadeInDirection: Direction, link: ElementLink<WorldSectionElement>): void`
>
> Remember the `example_link` created above?
>
> That is exactly the `link` parameter accepted here.
>
> This method shows a section and merges it into the section referenced by `link` (animation lasts 15 ticks).

> ### glueBlockOnto
>
> `scene.world.glueBlockOnto(blockPos: BlockPos, fadeInDirection: Direction, link: ElementLink<WorldSectionElement>): void`
>
> This method exists mainly for Java developer convenience.
>
> In JavaScript, the only difference from the previous method is the first parameter:
>
> - This method only accepts one block position.
>
> - The method above supports either one block position or a region.

> ### hideSection
>
> `scene.world.hideSection(selection: Selection, fadeOutDirection: Direction): void`
>
> Hides the selected section.
>
> Hide animation duration is 15 ticks.

> ### hideIndependentSection
>
> `scene.world.hideIndependentSection(link: ElementLink<WorldSectionElement>, fadeOutDirection: Direction, fadeOutDuration: number): void`
>
> Hides the section referenced by the provided `link`.

> ### hideIndependentSectionImmediately
>
> `scene.world.showIndependentSectionImmediately(link: ElementLink<WorldSectionElement>): void`
>
> Immediately hides the section referenced by `link`.
>
> Default hide direction is `Direction.down`.

> ### rotateSection
>
> `scene.world.rotateSection(link: ElementLink<WorldSectionElement>, xRotation: number, yRotation: number, zRotation: number, duration: number)`
>
> Accepts a `link` and rotates the section it references (for example, one merged via `showSectionAndMerge`).
>
> The last four parameters are `double`, `double`, `double`, and `int`.

> ### makeSectionIndependent
>
> `scene.world.makeSectionIndependent(selection: Selection): ElementLink<WorldSectionElement>`
>
> Splits the selected region out from `baseWorldSection` as an independent section.

> ### moveSection
>
> `scene.world.moveSection(link: ElementLink<WorldSectionElement>, offset: Vec3, duration: number): void`
>
> Moves the section referenced by `link`. The second parameter can be an array like `[x, y, z]`.
