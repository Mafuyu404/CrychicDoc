---
authors: ['Gu-meng']
---
# World Generation (WorldGen) (Strongly not recommended on 1.20; data packs are recommended for 1.20)

> Gu-meng note: This document references wudji's worldgen guide. It may be rewritten later.

* ## **Event Declaration**
`WorldgenEvents` is the event group for world-generation changes. It includes `WorldgenEvents.add` and `WorldgenEvents.remove`.
  ```js
  WorldgenEvents.add((event) => {})
  WorldgenEvents.remove((event) => {})
  ```
* ## Event Methods
| Method                                                                         | Description                        |
| ---------------------------------------------------------------------------- | ---------------------------- |
| addLake(Consumer lake)                                                         | Add a lake in world generation |
| addOre(Consumer ore)                                                        | Add ore generation in worldgen |
| getAnchors()                                                                 | Get height anchors (`VerticalAnchor`) |
| addSpawn(BiomeFilter biomeFilter, MobCategory spawnType, String mob)          | Add mob spawns |
| addSpawn(MobCategory spawnType, String mob)                                  | Add mob spawns |
| addSpawn(Consumer props)                                                      | Add mob spawns |
| addFeatureJson(BiomeFilter biomeFilter, JsonObject feature)                      | Add features/structures |
| addFeatureJson(BiomeFilter biomeFilter, ResourceLocation ID, JsonObject feature) | Add features/structures |

`WorldgenEvents.remove`
| Method                                                                                                     | Description                      |
| -------------------------------------------------------------------------------------------------------- | -------------------------- |
| removeSpawns(Consumer removeSpawn)                                                                          | Remove mob spawns |
| removeAllFeatures(BiomeFilter biomeFilter)                                                                | Remove all matching features |
| printSpawns(MobCategory spawnType)                                                                        | Print matching mob spawns |
| printFeatures(DecorationGenerationStep generationStep)                                                         | Print matching features |
| printSpawns()                                                                                            | Print all mob spawns |
| removeFeatureById(BiomeFilter biomeFilter, DecorationGenerationStep generationStep, ResourceLocation\[] featureIDs) | Remove features by ID |
| printFiltered(DecorationGenerationStep generationStep)                                                         | Print filtered matching features |
| printFiltered(DecorationGenerationStep generationStep, BiomeFilter biomeFilter)                                 | Print filtered matching features |
| printFeaturesForType(DecorationGenerationStep generationStep, BiomeFilter biomeFilter, Boolean afterRemoval)     | Print features for specific type |
| removeAllFeatures()                                                                                      | Remove all features |
| removeAllFeatures(BiomeFilter biomeFilter, DecorationGenerationStep generationStep)                             | Remove all matching features |
| removeAllSpawns()                                                                                        | Remove all mob spawns |
| removeFeatureById(DecorationGenerationStep generationStep, ResourceLocation[] featureIDs)                          | Remove features by ID |
| removeOres(Consumer removeOre)                                                                            | Remove matching ores |
| printFeatures()                                                                                          | Print all features |
| printFeatures(DecorationGenerationStep generationStep, BiomeFilter biomeFilter)                                 | Print all matching features |

* ## Common Components
  * ### Biome Filter
    You can use biome categories (Biome Categories) as biome filters.
	The ore example below demonstrates biome filter usage.
	Code source: `wudji` `Example Code`.
    ```js
    WorldgenEvents.add((event) => {
    	// Get vertical anchors
    	const { anchors } = event
    
    	event.addOre((ore) => {
    		ore.id = 'kubejs:glowstone_test_lmao' // Set ID for this feature (optional, recommended)
    		ore.biomes = {
    			not: 'minecraft:savanna' // Biome filter (optional)
    		}
    
    		// Add target examples
    		ore.addTarget('#minecraft:stone_ore_replaceables', 'minecraft:glowstone') // Replace target blocks in #minecraft:stone_ore_replaceables with minecraft:glowstone
    		ore.addTarget('minecraft:deepslate', 'minecraft:nether_wart_block')       // Replace target (minecraft:deepslate) with minecraft:nether_wart_block
    		ore.addTarget([
    			'minecraft:gravel',
    			/minecraft:(.*)_dirt/
    		], 'minecraft:tnt')       // Replace targets (minecraft:gravel and all dirt variants) with minecraft:tnt
    
    		ore.count([15, 50])                      // Set vein size (random from 15 to 50). You can also use a single number for fixed count.
    			.squared()                       // Enable cross-chunk style placement distribution
    			.triangleHeight(				 // Height provider using triangular distribution
    				anchors.aboveBottom(32), // Lower anchor: 32 blocks above world bottom (Y=-64), i.e. Y=-32
    				anchors.absolute(96)	 // Upper anchor: fixed Y=96
    			)								 // Overall range is Y=-32 to Y=96, with peak probability around midpoint Y=32
    
    		// More optional params (shown with defaults)
    		ore.size = 9                            // Maximum vein size
    		ore.noSurface = 0.5                     // Probability of being exposed to air
    		ore.worldgenLayer = 'underground_ores'  // Worldgen generation step for this ore
    		ore.chance = 0							// If non-zero and ore.count is not set, ore has 1/n chance per chunk
    	})
    
    	// Add lake
    	// This feature is planned for deprecation in vanilla
    	event.addLake((lake) => {
    		lake.id = 'kubejs:funny_lake' // BlockStatePredicate
    		lake.chance = 4
    		lake.fluid = 'minecraft:lava'
    		lake.barrier = 'minecraft:diamond_block'
    	})
    })
    
    WorldgenEvents.remove(event => {
    	//console.debugEnabled = true;
    
    	// Print all features in a given biome filter
    	event.printFeatures('', 'minecraft:plains')
    
    	event.removeOres((props) => {
    		// Like adding ores, ore removal also supports worldgen layer
    		props.worldgenLayer = 'underground_ores'
    		// Use biome filters
    		props.biomes = [{
    			category: 'icy',
    		}, {
    			category: 'savanna',
    		}, {
    			category: 'mesa',
    		}];
    
    		// Remove iron ore and copper ore in the above biomes
    		// Note: tags cannot be used here
    		props.blocks = ['minecraft:iron_ore', 'minecraft:copper_ore']
    	})
    
    	// Remove features by ID (first parameter is generation step)
    	event.removeFeatureById('underground_ores', ['minecraft:ore_coal_upper', 'minecraft:ore_coal_lower'])
    })
    ```
	![Example](/imgs/worlgen/Example_1.png)
	![Example](/imgs/worlgen/Example_2.png)
