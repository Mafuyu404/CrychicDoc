---
authors: ['Gu-meng']
---
# Recipe ID

* **About Recipe ID**
  * A recipe ID usually corresponds to the recipe file path. It is very useful when checking recipe type and format during development.
  * Common use cases include removing recipes, overriding recipes, and using `custom`.
  * After installing `JEI`, enable advanced tooltips with `F3+H` and you can view recipe IDs in-game.\
    ![debug.png](/imgs/RecipeId/debug.png)
  * In JEI, open any recipe and hover the output to view the recipe ID.\
    ![id-view.png](/imgs/RecipeId/id-view.png)
  * From the iron casting example above, the recipe ID tells us the file is `ModFile.jar/data/tconstruct/recipes/smeltery/casting/metal/iron/ingot_gold_cast.json`.
  * JEI provides a keybind for copying recipe IDs. Configure it yourself (no default key is bound).\
    ![key-binding.png](/imgs/RecipeId/key-binding.png)
  * `REI` and `EMI` can do similar things, but this tutorial only covers `JEI`. If you use `REI`/`EMI`, check mod settings for their keybinds.

* **Example**
    * When writing a recipe, if you append `.id()` with an existing recipe ID, it will directly override the original recipe.
    ```js
    ServerEvents.recipes((event) => {
		const { kubejs } = event.recipes

		kubejs.shaped('tconstruct:queens_slime_block', [
			'RRR',
			'RRR',
			'RRR'
		], {
			R: 'minecraft:rotten_flesh'
		}).id('tconstruct:common/materials/queens_slime_block_from_ingots')
	})
    ```
	Before\
	![contrast-1](/imgs/RecipeId/contrast-1.png)
	After\
	![contrast-2](/imgs/RecipeId/contrast-2.png)

    * Writing every recipe as a separate JSON can be tedious and hard to manage. You can also find the recipe file by ID, then copy its JSON content into `ServerEvents.recipes` with `custom`, for example:

    ```js
    ServerEvents.recipes((event) => {
        event.custom({
      	    "type": "createmetallurgy:casting_in_table",
          	"ingredients": [
      		    { "fluid": "createmetallurgy:molten_gold", "amount": 90 },
      		    { "tag": "forge:plates" },
      	    ],
      	    "results": [{ "item": "createmetallurgy:graphite_plate_mold" }],
    	    "processingTime": 90
        })
    })
    ```
	* **When copying a recipe JSON, make sure to include the outer `{}` as well.**
    * Also, `custom` can override by recipe ID too, same as above, by appending `.id()`.
