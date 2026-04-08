---
authors: ['Gu-meng']
---
# CreateHeatJS (CreateJS Addon Mod)
### CreateHeatJS lets you define custom heat sources for some CreateJS processing recipes.

**Examples**\
![Example](/imgs/createheadjs/Example_1.png)![Example](/imgs/createheadjs/Example_2.png)

Use `.heatLevel()` after a recipe to set a custom heat tier.
**JEI shows localization keys by default, so you need to add your own language file.**

## Code Example
`server_scripts`
```js
ServerEvents.recipes((event) => {
    const { create } = event.recipes
     
    create.mixing('minecrafrt:diamond', [
        'minecrafrt:coal_block'
    ]).heatLevel('melt')
     
    create.compacting('minecrafrt:diamond', [
        'minecrafrt:coal_block'
    ]).heatLevel('frozen')
})
```

`starup_scripts`
```js
CreateHeatJS.registerHeatEvent((event) => {
	// Melt
    event.registerHeat('melt', 1, 0xFF8C00)
		.addHeatSource('minecraft:fire')
		.register()

	// Frozen
	event.registerHeat('frozen', -1, 0x87CEFA)
		.addHeatSource('minecraft:blue_ice')
		.register()
})
```

## Advanced
`starup_scripts`
```js
// Load the furnace class
const $AbstractFurnaceBlock = Java.loadClass("net.minecraft.world.level.block.AbstractFurnaceBlock")
/*
 * This style also works,
 * but it requires ProbeJS v7.0 or newer.
*/
const { $AbstractFurnaceBlock } = require("package/net/minecraft/world/level/block/AbstractFurnaceBlock")

CreateHeatJS.registerHeatEvent((event) => {
	// Lit furnace
	event.registerHeat("BLAZE", 3, 0xed9c33)
		.addHeatSource("minecraft:furnace", "minecraft:furnace[lit=true]", (level, pos, blockStack) => {
			if (blockStack.hasProperty($AbstractFurnaceBlock.LIT)) {
				return blockStack.getValue($AbstractFurnaceBlock.LIT).booleanValue()
			}
			return false
		})
		.register()
	
	// Frozen (requires the Ice Spikes biome)
	event.registerHeat("CRYOTHEUM", -1, 0x8BAAFF)
		.addHeatSource("minecraft:blue_ice", (level, pos, blockStack) => {
			return level.getBiome(pos)
				.is(new ResourceLocation("minecraft:ice_spikes"))
		})
		.jeiTip()
		.register()
})
```
