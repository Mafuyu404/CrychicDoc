---
authors: ['Gu-meng']
---
# Slab-to-Plank Crafting
Main topic in this chapter: `findRecipes` in recipe events. All code in this chapter belongs in `server_scripts`.

## Full Code
```js
ServerEvents.recipes(event => {
    // Iterate over all items in the `minecraft:slabs` tag
    Ingredient.of('#minecraft:slabs').getItemIds().forEach(slab => {
        // Find crafting table recipes whose output is this slab, then iterate them
        event.findRecipes({ type: "minecraft:crafting_shaped", output: slab }).forEach(value => {
            // Check whether the input count is exactly 3 (normal slab recipes use 3; avoids exploits)
            if (value.getOriginalRecipe().getIngredients().size() != 3) return;
            // Get the first ingredient into a variable
            let item = value.getOriginalRecipe().getIngredients().get(0).getFirst();
            // Iterate over recipe ingredients
            value.getOriginalRecipe().getIngredients().forEach(inputItem=>{
                // Check whether item is null
                if (item == null) return;
                // Check whether all ingredients are the same; otherwise set item to null
                if (item != inputItem) item = null;
            })
            // If item is null after checks, skip this recipe
            if (item == null) return;
            event.shaped(item, [slab,slab]);
        })
    })
})
```
Note: This is a small example. The code is explained directly in comments for readability.

## Notes
1. This project is only an example; many parts are not necessarily optimal and can be improved.
2. Potential issue: the `item` variable may be a tag in some cases. This has not been fully tested.
3. If you improve this project, you can upload your revised code to the [Gitee repository](https://gitee.com/gumengmengs/kubejs-course).
