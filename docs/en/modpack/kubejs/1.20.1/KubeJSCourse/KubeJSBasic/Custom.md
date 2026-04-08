---
authors: ['Gu-meng']
---
# Generic Recipe Editing
This chapter explains how to modify almost any recipe.
Whether or not a mod has direct KJS integration, you can still edit recipes as long as they are standard data-driven recipes.

## Find a Recipe
This example uses the Infusion table recipe from Advent of Ascension 3 (`AoA3`).

First, press **F3+H** in-game to enable advanced tooltips. Then open the relevant recipe screen and note the **Recipe ID** shown for the item.

Next, go to the `mods` folder and find the target mod jar (`AoA3` here). Open it with an archive tool.
You can also copy the jar and rename `.jar` to `.zip` to open it.

Inside the archive, open `data\modid\recipes`.
For this example, the path is `data\aoa3\recipes`.

Find any infusion recipe, check its recipe id, then open the matching JSON file in that folder.

Example file: `infusion_crystallis_helmet.json`

```json
{
	"type": "aoa3:infusion",
	"ingredients": [
		{
			"item": "aoa3:padded_cloth"
		},
		{
			"item": "aoa3:padded_cloth"
		},
		{
			"item": "aoa3:armour_plating"
		},
		{
			"item": "aoa3:rainbow_druse"
		},
		{
			"item": "aoa3:rainbow_druse"
		},
		{
			"item": "aoa3:green_druse"
		},
		{
			"item": "aoa3:blue_druse"
		}
	],
	"input": {
		"item": "aoa3:helmet_frame"
	},
	"result": {
		"item": "aoa3:crystallis_helmet"
	}
}
```

## Add a Recipe
Now that we know the target JSON format, we can create the same structure in KJS using `event.custom(...)`.

```js
ServerEvents.recipes(e => {
	e.custom({
		"type": "aoa3:infusion",
		"ingredients": [
			{
				"item": "minecraft:soul_lantern"
			},
			{
				"item": "minecraft:soul_lantern"
			},
			{
				"item": "minecraft:lantern"
			},
			{
				"item": "minecraft:lantern"
			}
		],
		"input": {
			"item": "minecraft:emerald"
		},
		"result": {
			"item": "minecraft:grass_block"
		}
	})
})
```

## Summary
Generic recipe editing in KJS is essentially the same as writing datapack recipe JSON by hand.

The main advantage is that KJS lets you wrap these patterns into reusable methods/functions (not covered in this chapter).
