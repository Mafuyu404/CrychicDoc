---
authors: ['Gu-meng']
---
# Block Registration
KubeJS can register blocks from the `startup_scripts` folder. Note: custom registrations cannot be hot-reloaded. After writing registration code, you must restart the game for it to take effect.

## Basic Syntax
Registering a block in KubeJS is straightforward. The simplest form looks like:
```js
StartupEvents.registry("block", (event) => {
    // event.create(blockId, blockType)
    event.create("meng:my_block", "basic")
})
```
In the code above, we register a block with id `"meng:my_block"`. The `meng` prefix is the [namespace](../../Digression/NameSpace). It matters for paths when you later add [textures](../../Resources/Texture) and [localization (lang files)](../../Resources/Lang.md).

## Block Types
Here we used the `basic` type (the default/basic block type). KubeJS provides multiple block types:
|      Type      |          Description          | Example |
| :--------------: | :------------------------: | :----: |
|     `basic`      | Basic block                 | TBD |
|     `carpet`     | Carpet                      | TBD |
|      `crop`      | Crop                        | [Crop registration](./BlockType/CropReg.md) |
|     `fence`      | Fence                       | TBD |
|   `fence_gate`   | Fence gate                  | TBD |
| `pressure_plate` | Pressure plate              | TBD |
|     `button`     | Button                      | TBD |
|      `slab`      | Slab                        | TBD |
|     `stairs`     | Stairs                      | TBD |
|      `wall`      | Wall                        | TBD |
|    `cardinal`    | Facing block (lectern, furnace, etc.) | TBD |
|    `detector`    | Detector block?             | TBD |
|    `falling`     | Falling block (sand-like)   | TBD |

In KubeJS, you do not need to register the corresponding block item separately. KubeJS registers the block item for you automatically, and its item id matches the block id. When preparing textures, you generally only need the block textures, not separate block-item textures.

## Common Builder Methods
These methods can be used for blocks of any type.

### Common
|                      Method                       |              Args               |                   Description                    | Return |
| :-----------------------------------------------: | :-----------------------------: | :--------------------------------------------: | :------: |
|   `randomTick(Consumer\<RandomTickCallbackJS>)`   |                -                | Random tick callback                            |   this   |
|        `lootTable(Consumer\<LootBuilder>)`        |                -                | Build a loot table for the block                |    ~     |
|           `tagBlock(ResourceLocation)`            | [Common args](#common-tagblock-parameters) | Add block tags (mineable tool type, mining level, etc.) | this |
|            `tagItem(ResourceLocation)`            |               ->                | Add tags to the block item                      |   this   |
|                    `noItem()`                     |                -                | Do not generate the corresponding item          |   this   |
|             `displayName(Component)`              |               ->                | Set display name                                |   this   |
|                `lightLevel(float)`                |               ->                | Set light level                                 |   this   |
|     `blockEntity(Consumer\<BlockEntityInfo>)`     |                -                | Create a block entity                           |   this   |
| `rightClick(Consumer\<BlockRightClickedEventJS>)` |                -                | Right-click handler                             |   this   |
|                    `noDrops()`                    |                -                | No drops when broken                            |   this   |
|                 `hardness(float)`                 |               ->                | Set hardness (default 1.5)                      |   this   |
|               `speedFactor(float)`                |               ->                | Set speed factor (greater than 1 is faster)     |   this   |
|                `jumpFactor(float)`                |               ->                | Set jump factor                                 |   this   |
|               `noValidSpawns(bool)`               |               ->                | Whether mobs can spawn on this block            |   this   |
|                   `notSolid()`                    |                -                | Make the block non-solid (?)                    |   this   |
|                  `unbreakable()`                  |                -                | Make the block unbreakable                      |   this   |
|                `resistance(float)`                |               ->                | Explosion resistance (default 3)                |   this   |
|                 `requiresTool()`                  |                -                | Require a proper tool for drops                 |   this   |

#### Common `tagBlock()` Parameters
* Required tool tag

> If a block does not use `requiresTool()`, using the right tool only speeds up mining.
> 
> If a block uses `requiresTool()`, you must use the right tool to get drops.

|             Tag               | Tool |
| :----------------------------: | :----------: |
|  `"minecraft:mineable/sword"`  | Sword |
| `"minecraft:mineable/pickaxe"` | Pickaxe |
|   `"minecraft:mineable/axe"`   | Axe |
| `"minecraft:mineable/shovel"`  | Shovel |
|   `"minecraft:mineable/hoe"`   | Hoe |

* Required tool tier tag

|              Tag               | Required tier |
| :------------------------------: | :------------------------------------------: |
| `"minecraft:needs_wooden_tool"`  | Wooden |
|  `"minecraft:needs_stone_tool"`  | Stone |
|  `"minecraft:needs_iron_tool"`   | Iron |
| `"minecraft:needs_golden_tool"`  | Golden |
| `"minecraft:needs_diamond_tool"` | Diamond |
|  `"forge:needs_netherite_tool"`  | Netherite (tag provided by `Forge`) |

### Rendering / Collision
|                           Method                            |                      Args                      |             Description            | Return |
| :---------------------------------------------------------: | :--------------------------------------------: | :--------------------------------: | :------: |
|    `box(double, double, double, double, double, double)`    |                       ~                        |                 ~                  |   this   |
| `box(double, double, double, double, double, double, bool)` |                       ~                        |                 ~                  |   this   |
|                      `defaultCutout()`                      |                       -                        |                 ~                  |   this   |
|                   `defaultTranslucent()`                    |                       -                        |                 ~                  |   this   |
|                     `transparent(bool)`                     |                       ->                       | Whether the block is transparent   |   this   |
|                       `noCollision()`                       |                       -                        | No collision box                   |   this   |
|                    `renderType(string)`                     | "cutout"/"cutout_mipped"/"translucent"/"basic" | Choose render type (these four)    |   this   |
|                       `model(string)`                       |                       ->                       | Model path                         |   this   |
|                    `viewBlocking(bool)`                     |                       ~                        |                 ~                  |   this   |
|                      `fullBlock(bool)`                      |                       ->                       | Whether this is a full cube        |   this   |
|                       `opaque(bool)`                        |                       ->                       | Whether light can pass through     |   this   |
|                     `material(string)`                      |                       ->                       |                 ?                  |   this   |

### Sound Types
|          Method        | Args  | Description | Return |
| :--------------------: | :---: | :--------: | :------: |
|   `glassSoundType()`   |   -   | Glass sound |   this   |
|   `grassSoundType()`   |   -   | Grass sound |   this   |
|   `sandSoundType()`    |   -   | Sand sound  |   this   |
|   `stoneSoundType()`   |   -   | Stone sound |   this   |
|  `gravelSoundType()`   |   -   | Gravel sound |  this   |
|   `cropSoundType()`    |   -   | Crop sound  |   this   |
|   `woodSoundType()`    |   -   | Wood sound  |   this   |
|    `noSoundType()`     |   -   | No sound    |   this   |
| `soundType(SoundType)` |  ->   | Custom sound |  this   |

### Other
|                               Method                              | Args  |              Description              | Return |
| :--------------------------------------------------------------: | :---: | :----------------------------------: | :-----------------------: |
|       `mirrorState(Consumer\<BlockStateMirrorCallbackJS>)`       |   ~   |                  ~                   |           this            |
|       `rotateState(Consumer\<BlockStateRotateCallbackJS>)`       |   ~   |                  ~                   |           this            |
|                       `bounciness(float)`                        |   ~   | Bounciness / elasticity              |           this            |
|       `canBeReplaced(Predicate\<CanBeReplacedCallbackJS>)`       |   ~   | Whether this block can be replaced   |           this            |
| `placementState(Consumer\<BlockStateModifyPlacementCallbackJS>)` |   ~   | Placement callback                   |           this            |
|      `steppedOn(Consumer\<EntitySteppedOnBlockCallbackJS>)`      |   ~   | Entity stepped-on callback           |           this            |
|  `afterFallenOn(Consumer\<AfterEntityFallenOnBlockCallbackJS>)`  |   -   |                  ~                   |           this            |
|       `fallenOn(Consumer\<EntityFallenOnBlockCallbackJS>)`       |   -   | Entity fell on block callback        |           this            |
|                   `tagBoth(ResourceLocation)`                    |   ~   |                  ~                   |           this            |
|      `defaultState(Consumer\<BlockStateModifyCallbackJS>)`       |   -   | Default block state                  |           this            |
|          `exploded(Consumer\<BlockExplodedCallbackJS>)`          |   ~   | Called after explosion (block already destroyed) | this |
|                       `canBeWaterlogged()`                       |   -   | Whether this block can be waterlogged |          bool            |
|                 `textureSide(Direction,string)`                  |   ~   | Set texture for a specific side      |           this            |
|                       `mapColor(MapColor)`                       |  ->   | Map color                            |           this            |
|                    `redstoneConductor(bool)`                     |  ->   | Whether this block is a redstone conductor | this |
|                       `textureAll(string)`                       |  ->   | Use the same texture for all sides   |           this            |
|                       `suffocating(bool)`                        |  ->   | Whether entities suffocate inside    |           this            |
|                      `slipperiness(float)`                       |   ~   | How slippery the block is            |           this            |
|                     `transformObject(Block)`                     |   ~   |                  ~                   |           Block           |
|                     `texture(string,string)`                     |   ~   |                  ~                   |           this            |
|                   `property(BlockProperties)`                    |  ->   | Set block properties                 |           this            |
|               `item(Consumer\<BlockItemBuilder>)`                |   -   |            Block item builder            |           this            |
|                     `tag(ResourceLocation)`                      |  ->   | Set block tags                       |           this            |
|                  `color(int,BlockTintFunction)`                  |   ~   | Set tint color for a specific layer  |           this            |
|                    `color(BlockTintFunction)`                    |   ~   |                  ~                   |           this            |
|                       `createProperties()`                       |   -   |                  ?                   | BlockBehaviour$Properties |
|              `generateDataJsons(DataJsonGenerator)`              |   ~   |                  ~                   |             -             |
|             `generateAssetJsons(AssetJsonGenerator)`             |   ~   |                  ~                   |             -             |
|                       `getRegistryType()`                        |   -   |                  ~                   |      RegistryInfo<>       |
|                         `waterlogged()`                          |   -   | Enable waterlogging                  |           this            |
|                `instrument(NoteBlockInstrument)`                 |   ~   | Note block instrument?               |           this            |
|                   `createAdditionalObjects()`                    |   -   |                  ~                   |             -             |


### Simple Block Registration Helper

**Note: customize the snippet below to match your own preferences.**

```js
StartupEvents.registry("block", (event) => {
	// ModID: if you do not want to change it (default is "kubejs"), remove this variable
	const MODID = "meng:"

	// Tool tags
	const toolType = {
		sword: "minecraft:mineable/sword",
		pickaxe: "minecraft:mineable/pickaxe",
		axe: "minecraft:mineable/axe",
		shovel: "minecraft:mineable/shovel",
		hoe: "minecraft:mineable/hoe"
	}

	// Mining tier tags
	const miningLevel = {
		wooden: "minecraft:needs_wooden_tool",
		stone: "minecraft:needs_stone_tool",
		iron: "minecraft:needs_iron_tool",
		gold: "minecraft:needs_gold_tool",
		diamond: "minecraft:needs_diamond_tool",
		nether: "forge:needs_netherite_tool"
	}

	/* 
	* Block definitions
	* When adding the next block, remember to add a comma after each array entry.
	* Follow the format strictly:
	* [blockName, soundType, hardnessAndResistance, toolTypeKey, miningTierKey]
	*/
	let blockRegisters = [
		// Example
		["example_block", "stone", 3, "pickaxe", "wooden"],
	]
	blockRegisters.forEach(([name, soundType, hardness, tool, level]) => {
		event.create(MODID + name) // Block id
			.soundType(soundType) // Sound type
			.hardness(hardness) // Hardness
			.resistance(hardness) // Explosion resistance
			.tagBlock(toolType[tool]) // Tool type tag
			.tagBlock(miningLevel[level])  // Mining tier tag
			// .tagItem(MODID + "items") // Optional item tag
			// .tagItem(MODID + "blocks") // Optional item tag
			.requiresTool(true) // Require tool for drops
	})
})
```
