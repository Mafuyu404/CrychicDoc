---
authors: ['Gu-meng']
---
# Create: Sifting
This chapter introduces how to use KubeJS to modify Create: Sifting.

The tutorial below was tested with these mod/Forge versions.  
If you get errors on different versions, the API may have changed:
1. forge-47.3.7
2. JEI-15.3.0.4
3. rhino-2001.2.2-build.18
4. architectury-9.2.14
5. kubejs-2001.6.5-build.7
6. probejs-6.0.1
7. create-0.5.1f
8. createsifter-1.20.1-1.8.1.e-22

## Register Mesh Items
Create: Sifting supports mesh item registration directly.  
You only need to register the item, assign textures/model data, and add localization.

```js
StartupEvents.registry("item", (event) => {
	// Basic mesh
	event.create("meng:mesh", "createsifter:mesh")
	// Advanced mesh
	event.create("meng:advanced_mesh", "createsifter:advanced_mesh")
})
```

## Recipe Changes
Create: Sifting provides a recipe type for sieve processing:
```js
ServerEvents.recipes((event) => {
	const { createsifter } = event.recipes

	createsifter.sifting(output[], input[], processingTime, isWater, minimumSpeed)
})
```
`output` : output item(s) -- ***required***

`input` : input item(s) -- ***required***

`processingTime` : processing time (in ticks) -- **optional** -- default `100`

`isWater` : whether the process is waterlogged -- **optional** -- default `false`

`minimumSpeed` : minimum processing speed -- **optional** -- default `1.0`

The last three can also be configured via chained methods.  
For waterlogged processing, use `.waterlogged()` (default `true` for that method).

Question 1:

Where do I specify the mesh?

Answer:

`output` is an array-like structure in examples, and one of its parameters is the mesh item id.

Question 2:

There are two sieve types but only one recipe format. How do I separate them?

Answer:

The addon includes an advanced brass mesh.  
If the mesh is `advanced_brass_mesh` (or your own item registered as `advanced_mesh`), it will be recognized by the brass/advanced sieve type. Other meshes are treated as normal.

## Simple Helper Wrappers for Recipe Editing
```js
// String mesh
function stringMesh(output, input, time, isWater) {
	sifting(output, [input, "createsifter:string_mesh"], time, isWater)
}

// Andesite mesh
function andesiteMesh(output, input, time, isWater) {
	sifting(output, [input, "createsifter:andesite_mesh"], time, isWater)
}
// Zinc mesh
function zincMesh(output, input, time, isWater) {
	sifting(output, [input, "createsifter:zinc_mesh"], time, isWater)
}
// Brass mesh
function brassMesh(output, input, time, isWater) {
	sifting(output, [input, "createsifter:brass_mesh"], time, isWater)
}
// Advanced brass mesh
function advancedBrassMesh(output, input, time, isWater) {
	sifting(output, [input, "createsifter:advanced_brass_mesh"], time, isWater)
}

/**
 * 
 * @param {*} output output
 * @param {*} input input
 * @param {*} time defaults to 5 seconds when omitted
 * @param {*} isWater defaults to false when omitted
 */
function sifting(output, input, time, isWater) {
	if (time == undefined) time = 5
	if (isWater == undefined) isWater = false
	ServerEvents.recipes((event) => {
		const createsifter = event.recipes.createsifter
		createsifter.sifting(output, input, time * 20, isWater)
	})
}
```
When using this, you can just call `stringMesh()`

so you don’t need to pass the mesh id every time.

## Mesh Model/Texture Notes
Here is a simple model example (you can also reuse the official model).

Place the model at `assets/modid/models/item/itemId.json`.

`modid` is your mod id.

`itemId` is your item id.

Briefly, if you edit textures directly:
1. `0` is the center mesh texture.
2. `1` is the sieve frame texture.
```json
{
	"textures": {
		"0": "minecraft:block/iron_block",
		"1": "minecraft:block/oak_planks",
		"particle": "minecraft:block/iron_block"
	},
	"elements": [
		{
			"from": [15, 0, 0],
			"to": [16, 1, 16],
			"rotation": {"angle": 0, "axis": "y", "origin": [15, 0, 16]},
			"faces": {
				"north": {"uv": [0, 0, 1, 1], "texture": "#1"},
				"east": {"uv": [0, 0, 16, 1], "texture": "#1"},
				"south": {"uv": [0, 0, 1, 1], "texture": "#1"},
				"west": {"uv": [0, 0, 16, 1], "texture": "#1"},
				"up": {"uv": [0, 0, 16, 1], "rotation": 270, "texture": "#1"},
				"down": {"uv": [0, 0, 16, 1], "rotation": 90, "texture": "#1"}
			}
		},
		{
			"from": [0, 0, 0],
			"to": [1, 1, 16],
			"rotation": {"angle": 0, "axis": "y", "origin": [1, 0, 16]},
			"faces": {
				"north": {"uv": [0, 0, 1, 1], "rotation": 90, "texture": "#1"},
				"east": {"uv": [0, 0, 16, 1], "texture": "#1"},
				"south": {"uv": [0, 0, 1, 1], "rotation": 270, "texture": "#1"},
				"west": {"uv": [0, 0, 16, 1], "rotation": 180, "texture": "#1"},
				"up": {"uv": [0, 0, 16, 1], "rotation": 270, "texture": "#1"},
				"down": {"uv": [0, 0, 16, 1], "rotation": 270, "texture": "#1"}
			}
		},
		{
			"from": [1, 0, 0],
			"to": [15, 1, 1],
			"rotation": {"angle": 0, "axis": "y", "origin": [1, 0, 0]},
			"faces": {
				"north": {"uv": [0, 0, 14, 1], "texture": "#1"},
				"east": {"uv": [0, 0, 1, 1], "texture": "#1"},
				"south": {"uv": [0, 0, 14, 1], "texture": "#1"},
				"west": {"uv": [0, 0, 1, 1], "texture": "#1"},
				"up": {"uv": [0, 0, 14, 1], "texture": "#1"},
				"down": {"uv": [0, 0, 14, 1], "texture": "#1"}
			}
		},
		{
			"from": [1, 0, 15],
			"to": [15, 1, 16],
			"rotation": {"angle": 0, "axis": "y", "origin": [1, 0, 15]},
			"faces": {
				"north": {"uv": [0, 0, 14, 1], "texture": "#1"},
				"east": {"uv": [0, 0, 1, 1], "texture": "#1"},
				"south": {"uv": [0, 0, 14, 1], "texture": "#1"},
				"west": {"uv": [0, 0, 1, 1], "texture": "#1"},
				"up": {"uv": [0, 0, 14, 1], "texture": "#1"},
				"down": {"uv": [0, 0, 14, 1], "texture": "#1"}
			}
		},
		{
			"from": [6, 0, 1],
			"to": [7, 1, 15],
			"rotation": {"angle": 0, "axis": "y", "origin": [6, 0, 17]},
			"faces": {
				"north": {"uv": [0, 0, 1, 1], "texture": "#0"},
				"east": {"uv": [0, 0, 14, 1], "texture": "#0"},
				"south": {"uv": [0, 0, 1, 1], "texture": "#0"},
				"west": {"uv": [0, 0, 14, 1], "texture": "#0"},
				"up": {"uv": [0, 0, 14, 1], "rotation": 270, "texture": "#0"},
				"down": {"uv": [0, 0, 14, 1], "rotation": 90, "texture": "#0"}
			}
		},
		{
			"from": [9, 0, 1],
			"to": [10, 1, 15],
			"rotation": {"angle": 0, "axis": "y", "origin": [9, 0, 17]},
			"faces": {
				"north": {"uv": [0, 0, 1, 1], "texture": "#0"},
				"east": {"uv": [0, 0, 14, 1], "texture": "#0"},
				"south": {"uv": [0, 0, 1, 1], "texture": "#0"},
				"west": {"uv": [0, 0, 14, 1], "texture": "#0"},
				"up": {"uv": [0, 0, 14, 1], "rotation": 270, "texture": "#0"},
				"down": {"uv": [0, 0, 14, 1], "rotation": 90, "texture": "#0"}
			}
		},
		{
			"from": [13, 0, 1],
			"to": [14, 1, 15],
			"rotation": {"angle": 0, "axis": "y", "origin": [13, 0, 17]},
			"faces": {
				"north": {"uv": [0, 0, 1, 1], "texture": "#0"},
				"east": {"uv": [0, 0, 14, 1], "texture": "#0"},
				"south": {"uv": [0, 0, 1, 1], "texture": "#0"},
				"west": {"uv": [0, 0, 14, 1], "texture": "#0"},
				"up": {"uv": [0, 0, 14, 1], "rotation": 270, "texture": "#0"},
				"down": {"uv": [0, 0, 14, 1], "rotation": 90, "texture": "#0"}
			}
		},
		{
			"from": [1, 0, 3],
			"to": [15, 1, 4],
			"rotation": {"angle": 0, "axis": "y", "origin": [1, 0, 3]},
			"faces": {
				"north": {"uv": [0, 0, 14, 1], "texture": "#0"},
				"east": {"uv": [0, 0, 1, 1], "texture": "#0"},
				"south": {"uv": [0, 0, 14, 1], "texture": "#0"},
				"west": {"uv": [0, 0, 1, 1], "texture": "#0"},
				"up": {"uv": [0, 0, 14, 1], "texture": "#0"},
				"down": {"uv": [0, 0, 14, 1], "texture": "#0"}
			}
		},
		{
			"from": [1, 0, 6],
			"to": [15, 1, 7],
			"rotation": {"angle": 0, "axis": "y", "origin": [1, 0, 6]},
			"faces": {
				"north": {"uv": [0, 0, 14, 1], "texture": "#0"},
				"east": {"uv": [0, 0, 1, 1], "texture": "#0"},
				"south": {"uv": [0, 0, 14, 1], "texture": "#0"},
				"west": {"uv": [0, 0, 1, 1], "texture": "#0"},
				"up": {"uv": [0, 0, 14, 1], "texture": "#0"},
				"down": {"uv": [0, 0, 14, 1], "texture": "#0"}
			}
		},
		{
			"from": [1, 0, 12],
			"to": [15, 1, 13],
			"rotation": {"angle": 0, "axis": "y", "origin": [1, 0, 12]},
			"faces": {
				"north": {"uv": [0, 0, 14, 1], "texture": "#0"},
				"east": {"uv": [0, 0, 1, 1], "texture": "#0"},
				"south": {"uv": [0, 0, 14, 1], "texture": "#0"},
				"west": {"uv": [0, 0, 1, 1], "texture": "#0"},
				"up": {"uv": [0, 0, 14, 1], "texture": "#0"},
				"down": {"uv": [0, 0, 14, 1], "texture": "#0"}
			}
		},
		{
			"from": [1, 0, 9],
			"to": [15, 1, 10],
			"rotation": {"angle": 0, "axis": "y", "origin": [1, 0, 9]},
			"faces": {
				"north": {"uv": [0, 0, 14, 1], "texture": "#0"},
				"east": {"uv": [0, 0, 1, 1], "texture": "#0"},
				"south": {"uv": [0, 0, 14, 1], "texture": "#0"},
				"west": {"uv": [0, 0, 1, 1], "texture": "#0"},
				"up": {"uv": [0, 0, 14, 1], "texture": "#0"},
				"down": {"uv": [0, 0, 14, 1], "texture": "#0"}
			}
		},
		{
			"from": [2, 0, 1],
			"to": [3, 1, 15],
			"rotation": {"angle": 0, "axis": "y", "origin": [2, 0, 17]},
			"faces": {
				"north": {"uv": [0, 0, 1, 1], "texture": "#0"},
				"east": {"uv": [0, 0, 14, 1], "texture": "#0"},
				"south": {"uv": [0, 0, 1, 1], "texture": "#0"},
				"west": {"uv": [0, 0, 14, 1], "texture": "#0"},
				"up": {"uv": [0, 0, 14, 1], "rotation": 270, "texture": "#0"},
				"down": {"uv": [0, 0, 14, 1], "rotation": 90, "texture": "#0"}
			}
		}
	],
	"display": {
		"thirdperson_righthand": {
			"translation": [-5.5, 3, -3.5],
			"scale": [0.84, 0.92, 0.99]
		},
		"firstperson_righthand": {
			"rotation": [13, 0, 0],
			"translation": [8.25, 1.75, -12.75],
			"scale": [1.25, 1.25, 1.25]
		},
		"ground": {
			"translation": [0, 4.75, 0]
		},
		"gui": {
			"rotation": [97, 0, 0],
			"translation": [0, -0.5, 0],
            "scale": [0.8, 0.8, 0.8]
		},
		"fixed": {
			"rotation": [90, 0, 0],
			"scale": [1, 0.04, 1]
		}
	},
	"groups": [
		{
			"name": "group",
			"origin": [1, 0, 11],
			"color": 0,
			"children": [0, 1, 2, 3]
		},
		{
			"name": "group",
			"origin": [10, 0, 17],
			"color": 0,
			"children": [4, 5, 6, 7, 8, 9, 10]
		},
		11
	]
}
```
