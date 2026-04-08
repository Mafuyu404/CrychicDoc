# Create (Mechanical Power) and KubeJS
This chapter introduces how to use KubeJS to modify recipes from the Create mod.

Below are the Forge and mod versions used when writing this chapter. If you use different versions and get errors, the mod authors may have changed code or APIs:
1. forge-47.2.32
2. JEI-15.3.0.4
3. rhino-2001.2.2-build.18
4. architectury-9.2.14
5. kubejs-2001.6.5-build.7
6. probejs-6.0.1
7. create-0.5.1f
8. kubejs create-2001.2.5.bulid.2

### Recipe Type Table
(This is not exhaustive. It lists common ones. `[]` means you can pass multiple inputs/outputs or an array.)

Baking/smelting use vanilla recipe types, so they do not have a dedicated Create recipe type entry here.

The `.heatLevel` method requires the extra mod [**CreateHeatJS**](../KubeJSAddon/CreateHeatJS).
|                                         Recipe Call                                       |      Recipe Type     |                     Notes                          |
| :---------------------------------------------------------------------------------------: | :-----------------: | :-----------------------------------------------: |
|                          `create.conversion(output[], input[])`                           | Conversion          | See examples such as gearboxes / info panels       |
|                           `create.crushing(output[], input[])`                            | Crushing            | -                                                   |
|                            `create.milling(output[], input[])`                            | Milling             | -                                                   |
|                            `create.cutting(output[], input[])`                            | Cutting             | -                                                   |
|                            `create.mixing(output[], input[])`                             | Mixing              | Can append `.heated()` and `.superheated()`         |
|                          `create.compacting(output[], input[])`                           | Compacting          | Can append `.heated()` and `.superheated()`         |
|                      `create.sandpaperPolishing(output[], input[])`                       | Sandpaper polishing | -                                                   |
|                           `create.splashing(output[], input[])`                           | Splashing (fan+water) | -                                                |
|                           `create.haunting(output[], input[])`                            | Haunting (fan+soul fire) | -                                            |
|                           `create.deploying(output[], input[])`                           | Deploying           | -                                                   |
|                         `create.item_application(output, input)`                          | Item application    | Similar to casing recipes (1.18+; auto adapts to deploying) |
|                            `create.filling(output[], input[])`                            | Filling             | -                                                   |
|                           `create.emptying(output[], input[])`                            | Emptying            | -                                                   |
|                  `create.mechanicalCrafting(output[], pattern[], {key})`                  | Mechanical crafting | See examples below                                  |
| `create.sequencedAssembly(output[], input, sequence[]).transitionalItem(item).loops(int)` | Sequenced assembly  | See examples below                                  |

### Notes
This is the common outer structure for all the code below (only partial code is shown in the examples):
```js
ServerEvents.recipes((event) => {
	/* 
	 * This is a basic destructuring pattern for the recipes event.
	 * It is not limited to Create.
	 * If other mods are supported, you can destructure them here as well, for example:
	 * const { create, thermal, kubejs, minecraft } = event.recipes
	 * If you are not sure which mods are supported, try using ProbeJS.
	*/
    const { create } = event.recipes
})
```
If a recipe supports **heated**, it also supports **superheated**.

Just append `.heated()` or `.superheated()` to the recipe. The former is normal heating and the latter is superheating.
You can also use `.heatRequirement(string)` with `"heated"`, `"superheated"`, or other heat levels.

If a recipe supports fluid output, you can use `Fluid.of(...)` like this:
```js
Fluid.of("fluid_id", amount)
Fluid.of("minecraft:lava", 810)
```

If a recipe supports chance outputs, use:
```js
Item.of("item_id", amount).withChance(0.5)
```
This means the item has a 50% chance to output. Here, `1` is 100%.

If a recipe supports multiple outputs, you can write:
```js
[Item.of("item_id", amount), Item.of("item_id", amount)]
```
If multiple outputs and chance are supported, you can also add `.withChance()` to `Item.of(...)`.

If a recipe supports processing time, you can append `processingTime(ticks)`.

### Mechanical Press / Compacting
```js
// Compacting
create.compacting("minecraft:golden_apple", [
	"minecraft:apple"
])

// Compacting (heated)
create.compacting("minecraft:iron_nugget", [
	"minecraft:iron_ore"
]).heated()

// Compacting (superheated)
create.compacting("minecraft:diamond", [
	"minecraft:deepslate_diamond_ore"
]).superheated()

// Compacting (fluid)
create.compacting(Fluid.of("minecraft:water", 1000), [
	Fluid.of("minecraft:lava", 1000)
])

// Compacting (chance output: 1% chance to output diamond)
create.compacting([
	"minecraft:stone", 
	Item.of("minecraft:diamond").withChance(0.01)
], "minecraft:magma_block")

// Pressing
create.pressing("minecraft:enchanted_golden_apple", [
	"minecraft:golden_apple"
])

// Pressing (chance output)
create.pressing([
	"minecraft:stone",
	Item.of("diamond").withChance(0.5)
], "minecraft:crying_obsidian")
```
Quick explanation:

For **compacting** and **pressing**, the first argument is the <font color=red>output items and/or fluids</font>. It can be an array, and you can use <font color=blue>.withChance()</font> on items to add output probability (remember: <font color=red>1 is 100%</font>). The second argument is the <font color=red>input</font>.

Appending <font color=red>.heated()</font> requires heating.

Appending <font color=red>.superheated()</font> requires superheating.

### Mixer
```js
// Mixing - multiple inputs
create.mixing("minecraft:grass_block", [
	Fluid.of("minecraft:water", 500),
	"minecraft:dirt"
])

// Mixing - heated
create.mixing("minecraft:cooked_cod", [
	"minecraft:cod"
]).heated()

// Mixing - superheated
create.mixing("minecraft:golden_carrot", [
	"minecraft:carrot"
]).superheated()

// Mixing - fluid output + chance output
create.mixing([
	Fluid.of("minecraft:lava", 100),
	Item.of("diamond").withChance(0.3)
], Item.of("minecraft:fire_charge", 64)).superheated()
```

### Fan
```js
// Splashing (supports multiple outputs and chance outputs)
create.splashing("minecraft:golden_apple", [
	"minecraft:enchanted_golden_apple"
])

// Haunting (soul fire) (supports multiple outputs and chance outputs)
create.haunting(Item.of("minecraft:diamond").withChance(0.1),[
	"minecraft:stone"
])
```

### Millstone
```js
// Milling (supports multiple outputs and chance outputs)
create.milling(Item.of("minecraft:oak_planks", 6), [
	"minecraft:chest"
])

```
### Crushing Wheels
```js
// Crushing (supports multiple outputs and chance) - with processing time
create.crushing("minecraft:netherite_scrap", [
	"minecraft:ancient_debris"
]).processingTime(20 * 200)
```
### Spout (Filling)
```js
// Filling
create.filling("minecraft:soul_torch", [
	"minecraft:torch",
	Fluid.of("minecraft:milk", 500)
])
```
The first argument is the <font color=red>output item</font>.

The second argument is the <font color=red>input item and fluid</font>.

Note: the inputs must be an item + a fluid.

### Basin (Emptying)
```js
// Emptying
create.emptying([
	Fluid.of("minecraft:lava", 50),
	"minecraft:slime_ball"
], "minecraft:magma_cream")
```
The first argument is the <font color=red>output items and fluids</font>.

The second argument is the <font color=red>input item</font>.

Note: the outputs must be items + fluids.

### Mechanical Saw
```js
// Cutting (supports multiple outputs and chance; can also add processingTime)
create.cutting("minecraft:glowstone", [
	"minecraft:shroomlight"
])
```
### Deployer
```js
// Deploying (supports multiple outputs and chance)
create.deploying("minecraft:chipped_anvil", [
	"minecraft:damaged_anvil",
	"minecraft:iron_ingot"
])
// Deploying - do not consume held item
create.deploying("minecraft:anvil", [
	"minecraft:chipped_anvil",
	"minecraft:iron_ingot"
]).keepHeldItem()
```
The first argument is the <font color=red>output item</font>.

The second argument is the <font color=red>input item and the deployer's held item</font>.

The first input is the item being worked on; the second input is the item held by the deployer. So <font color=red>do not swap them</font>.

### Sandpaper
```js
// Sandpaper polishing (supports chance output)
create.sandpaper_polishing("minecraft:glow_item_frame", [
	"minecraft:item_frame"
])
```
### Mechanical Crafter
```js
// Mechanical crafting supports up to 9x9
create.mechanical_crafting("minecraft:cow_spawn_egg", [
    "BBBBB",
    "B B B",
    "BBEBB",
    "BMMMB",
    "MMMMM"
], {
    B: "minecraft:beef",
    E: "minecraft:egg",
    M: "minecraft:milk_bucket"
})
```
This uses a JSON-like pattern format and supports up to 9x9.

The first argument is the <font color=red>output item</font>.

The second argument is the <font color=red>pattern</font>.

The third argument is the <font color=red>key map</font> that explains pattern placeholders.

<font color=green>Recommendation: treat each string line as a row in the grid. If you have a completely empty row, <font color=red>fill it with spaces</font> rather than omitting it.</font>

### Sequenced Assembly
```js
/*
 * You can store repeated item ids in a constant.
 * This is optional; some people prefer not to.
 * If you reuse the same name, only the last one is effective.
 * To avoid issues, avoid reusing constant/variable names.
*/
const incomplete = "create:incomplete_precision_mechanism"

// Sequenced assembly
    create.sequenced_assembly(
        // Outputs + chance (this chance behaves more like "weight/share"):
        // Higher chance means higher probability.
        // The first item is the primary output; the rest are "scrap" outputs.
        [
            Item.of("diamond").withChance(0.02),
            Item.of("cobblestone").withChance(0.5),
            Item.of("stone").withChance(0.8)
        ],
        // Input item
        "minecraft:deepslate",
        // Processing sequence (machines in order)
        [
            // Deployer
            create.deploying(incomplete, [incomplete, "minecraft:tnt"]).keepHeldItem(),
            // Cutting
            create.cutting(incomplete, incomplete),
            // Filling
            create.filling(incomplete, [incomplete, Fluid.of("minecraft:lava", 100)]),
            // Pressing
            create.pressing(incomplete,incomplete)
        ]
	)
    // Transitional item (incomplete product)
    .transitionalItem(incomplete)
    // Loop count (default is 5 if omitted)
    .loops(3)

```
```js
// Same example without comments
const incomplete = "create:incomplete_precision_mechanism"

create.sequenced_assembly([
	Item.of("diamond").withChance(0.02),
	Item.of("cobblestone").withChance(0.5),
	Item.of("stone").withChance(0.8)
], "minecraft:deepslate", [
	create.deploying(incomplete, [incomplete, "minecraft:tnt"]).keepHeldItem(),
	create.cutting(incomplete, incomplete),
	create.filling(incomplete, [incomplete, Fluid.of("minecraft:lava", 100)]),
	create.pressing(incomplete, incomplete)
]).transitionalItem(incomplete).loops(3)
```
Note: at the moment, Create officially provides only the machine steps shown above in this example.

Note: the transitional item can be a vanilla item as well.

### Registering A Custom Transitional Item
You can register a custom transitional item for sequenced assembly. This must be written in `startup_scripts` (runs at game startup).
```js
StartupEvents.registry("item", (event) => {
    event.create("meng:diamond", "create:sequenced_assembly")
})
```
In `event.create`, the first argument is the **item id** and the second argument is the **item type**.

### Mysterious Recipes (JEI Display Only)
Mysterious recipes are only a JEI display. They are not actually craftable recipes, so you do not need to add any server recipe events.
Write them in `client_scripts` (client-side), like this:

```js
const $MysteriousItemConversionCategory = Java.loadClass("com.simibubi.create.compat.jei.category.MysteriousItemConversionCategory")
const $ConversionRecipe = Java.loadClass("com.simibubi.create.compat.jei.ConversionRecipe")

$MysteriousItemConversionCategory.RECIPES.add($ConversionRecipe.create("apple", "minecraft:diamond"))
```
This shows that "apple converts to diamond" as a mysterious recipe in JEI. What that recipe "means" and how players should obtain it is up to you. This only provides a JEI hint; it does not implement any gameplay logic by itself.
