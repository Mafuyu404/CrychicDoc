---
authors: ['Qi Month']
---

# Custom Metal Materials Integration (CMMI)

## Introduction

This project is designed as a quick all-in-one workflow: by filling in `[id, color, mining level]`, you can register `ingots, plates, raw ores, stone/deepslate ores, metal blocks, and molten fluids` together.

The code itself is straightforward. The trickiest part is ore rendering.

Ore registration works by rendering a separate ore texture layer over a vanilla stone model. The extra layer is slightly larger than stone (same size causes render issues). To apply tinting correctly only to the ore texture, set tinting on the ore layer in the model.

<!-- ![1](/imgs/CustomMetal/1.png) -->

To ensure tinting works correctly (tint ore, not base stone), configure tinting on the ore texture in the model.

<!-- ![2](/imgs/CustomMetal/2.png) -->

Result Preview
<!-- ![3](/imgs/CustomMetal/3.png) -->
## Quick Explanation

In ore models, there is a `tintindex` key. The [**official Minecraft Wiki**](https://minecraft.wiki/w/Tutorial:Models) explains it as:

```
Use a hardcoded tint index to recolor the texture. If set to `-1`, recoloring is disabled (default is `-1`).
```

So adding `"tintindex": 0` to the model texture allows that layer to be tinted independently. See the screenshots above for reference.

## File Download

<!-- [Download Files](/Code/Projects/CMMI) -->

## References

[**Official Minecraft Wiki**](https://minecraft.wiki/w/Tutorial:Models)

[**Comprehensive Resource Pack Guide: tintindex**](http://sqwatermark.com/resguide/vanilla/model/tintindex.html)

## Source Code

<details open>

<summary>Project source code (click to expand/collapse)</summary>

```js
// Define namespace; change it if needed
let namespace  = "new_create:"

// Fill in values in order: [id, color, mining level]
let moltenRegisters = [
	// ["test", 0xabc098, "wooden"]
]
moltenRegisters.forEach(([name, color, level]) => {
	StartupEvents.registry("item", (event) => {
		// Ingot
		event.create(`${namespace + name}_ingot`)
			// Set texture path
			.texture(`${namespace}item/metal/ingot`)
			// Set color
			.color(color)
			// Add Forge tags for unified management
			.tag("forge:ingots")
			// Add Forge tags for unified management
			.tag(`forge:ingots/${name}`)

		// Plate
		event.create(`${namespace + name}_sheet`)
			// Set texture path
			.texture(`${namespace}item/metal/sheet`)
			// Set color
			.color(color)
			// Add Forge tags for unified management
			.tag("forge:plates")
			// Add Forge tags for unified management
			.tag(`forge:plates/${name}`)

		// Raw ore
		event.create(`${namespace}raw_${name}`)
			// Set texture path
			.texture(`${namespace}item/metal/raw_ore`)
			// Set color
			.color(color)
			// Add Forge tags for unified management
			.tag("forge:raw_materials")
			// Add Forge tags for unified management
			.tag(`forge:raw_materials/${name}`)
	})

	StartupEvents.registry("block", (event) => {
		let pickaxe = "minecraft:mineable/pickaxe"

		// Mining level mapping
		let miningLevel = {
			wooden: "minecraft:needs_wooden_tool",
			stone: "minecraft:needs_stone_tool",
			iron: "minecraft:needs_iron_tool",
			gold: "minecraft:needs_gold_tool",
			diamond: "minecraft:needs_diamond_tool",
			nether: "forge:needs_netherite_tool"
		}

		// Stone ore
		event.create(`${namespace + name}_ore`)
			// Set model path
			.model(`${namespace}block/ore/ore`)
			// Set color (see explanation above for `0`)
			.color(0, color)
			// Set render type to support transparency
			.renderType("cutout")
			// Set sound type
			.soundType(SoundType.STONE)
			// Set hardness
			.hardness(3)
			// Set blast resistance
			.resistance(3)
			// Add Forge tags for unified management
			.tag("forge:ores")
			// Add Forge tags for unified management
			.tag(`forge:ores/${name}`)
			// Add Forge tags for unified management
			.tag("forge:ore_rates/dense")
			// Mark as pickaxe-mineable
			.tagBlock(pickaxe)
			// Add mining level tag
			.tagBlock(miningLevel[level])
			// Require proper tool
			.requiresTool(true)
			// Set item model tint color
			.item((item) => {
				item.color(0, color)
			})

		// Deepslate ore
		event.create(`${namespace}deepslate_${name}_ore`)
			// Set model path
			.model(`${namespace}block/ore/deepslate_ore`)
			// Set color (see explanation above for `0`)
			.color(0, color)
			// Set render type to support transparency
			.renderType("cutout")
			// Set sound type
			.soundType(SoundType.DEEPSLATE)
			// Set hardness
			.hardness(4.5)
			// Set blast resistance
			.resistance(4.5)
			// Add Forge tags for unified management
			.tag("forge:ores")
			// Add Forge tags for unified management
			.tag(`forge:ores/${name}`)
			// Add Forge tags for unified management
			.tag("forge:ore_rates/deepslate")
			// Mark as pickaxe-mineable
			.tagBlock(pickaxe)
			// Add mining level tag
			.tagBlock(miningLevel[level])
			// Require proper tool
			.requiresTool(true)
			// Set item model tint color
			.item((item) => {
				item.color(0, color)
			})

		// Block
		event.create(`${namespace + name}_block`)
			// Set texture path
			.textureAll(`${namespace}block/metal/block`)
			// Set sound type
			.soundType(SoundType.METAL)
			// Set color
			.color(color)
			// Set hardness
			.hardness(5)
			// Set blast resistance
			.resistance(5)
			// Add Forge tags for unified management
			.tag("forge:storage_blocks")
			// Add Forge tags for unified management
			.tag(`forge:storage_blocks/${name}`)
			// Mark as pickaxe-mineable
			.tagBlock(pickaxe)
			// Add mining level tag
			.tagBlock(miningLevel[level])
			// Require proper tool
			.requiresTool(true)
			// Set item model tint color
			.item((item) => {
				item.color(color)
			})
	})

	StartupEvents.registry("fluid", (event) => {
		// Fluid textures should be at [modid/textures/block/fluid]
		const PATH = "block/fluid/"

		// Molten metal
		event.create(`${namespace}molten_${name}`)
			// Color
			.thinTexture(color)
			// Color
			.bucketColor(color)
			// Texture path
			.flowingTexture(`${namespace + PATH}flowing`)
			// Texture path
			.stillTexture(`${namespace + PATH}still`)
			// Add Forge tags for unified management
			.tag(`forge:molten_${name}`)
			// Add Forge tags for unified management
			.tag("forge:molten_materials")
	})
})
```
</details>
