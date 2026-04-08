---
authors: ['Gu-meng']
---
# Add Custom Items
KubeJS can register items in `startup_scripts`.
Note: all custom registration changes are not hot-reloadable. Restart the game after editing.

## Basic Syntax
```js
StartupEvents.registry("item",event=>{
    event.create("meng:my_item","basic")
})
```
Creating an item is simple and can be done in one line.

In `create`, the first argument is the item id.
If you write `"namespace:id"`, the part before `:` is your namespace (similar to mod id), and the part after `:` is the item id.
If you only write `"id"` without a namespace, KubeJS defaults to `kubejs:id`.

The second argument in `create` is the item type.

## Item Types
|      Type Arg       |     Use     |              Description              |                  Example                  |
| :-----------------: | :---------: | :-----------------------------------: | :---------------------------------------: |
|       `basic`       | Basic item  | -                                     |                    -                      |
|       `basic`       | Basic item  | -                                     |      [Add food](./ItemType/food)          |
|    `music_disc`     | Music disc  | Playable in jukebox                   | [Add music disc](./ItemType/MusicDisc)    |
| `smithing_template` | Smithing template | -                                |                    -                      |
|      `helmet`       | Helmet      | -                                     |               [Add armor]()               |
|    `chestplate`     | Chestplate  | -                                     |                 Same as above             |
|     `leggings`      | Leggings    | -                                     |                 Same as above             |
|       `boots`       | Boots       | -                                     |                 Same as above             |
|        `axe`        | Axe         | -                                     |      [Add tools](./ItemType/tools)        |
|        `hoe`        | Hoe         | -                                     |                 Same as above             |
|      `pickaxe`      | Pickaxe     | -                                     |                 Same as above             |
|      `shovel`       | Shovel      | -                                     |                 Same as above             |
|       `sword`       | Sword       | -                                     |                 Same as above             |
|      `shears`       | Shears      | -                                     |              [Add shears]()               |


## Common Methods
The methods below are available to all item types.
Armor/tools and other special categories may have extra methods in their own chapters.
### Frequently Used Methods
|                             Method Call                              |   Args   |                         Purpose                         | Return Type |
| :------------------------------------------------------------------: | :------: | :-----------------------------------------------------: | :---------: |
|                          maxDamage(int)                              |    ->    | Set max durability                                      |    this     |
|                   food(Consumer\<FoodBuilder\>)                      |    ->    | [Set item as food](./ItemType/food)                     |    this     |
|                         maxStackSize(int)                            |    ->    | Set max stack size (can exceed 64, usually not advised) |    this     |
|                        fireResistant(bool)                           |    ->    | Set whether item is fire resistant                      |    this     |
|                        displayName(string)                           |    ->    | Set display name when no lang entry exists              |    this     |
|                       tag(ResourceLocation)                          |    ->    | Add item tag                                            |    this     |
|                          texture(string)                             |    ->    | Set item texture path                                   |    this     |
|                           unstackable()                              |    -     | Make item unstackable                                   |    this     |
|                           burnTime(int)                              |    -     | Set burn time in ticks (`0` means not burnable)         |    this     |
| modifyAttribute(ResourceLocation,string,double,AttributeModifier)    |    ~     | Apply attribute modifiers                               |    this     |
|                      createItemProperties()                          |    -     | Create item properties object                           |    Item     |


### Methods Triggered While Using the Item
|                    Method Call                    |   Args   |                         Purpose                          | Return Type |
| :-----------------------------------------------: | :------: | :------------------------------------------------------: | :---------: |
|          use(ItemBuilder$UseCallback)             |    ->    | Called when player starts using the item                |    this     |
| releaseUsing(ItemBuilder$ReleaseUsingCallback)    |    ->    | Called when player releases right-click before finish   |    this     |
|  finishUsing(ItemBuilder$FinishUsingCallback)     |    ->    | Called when item use completes                          |    this     |
|     useDuration(ToIntFunction\<ItemStack\>)       |    ~     | Set/use item use duration                               |    this     |
|             useAnimation(UseAnim)                 |    ~     | Set use animation                                       |    this     |

### Rendering-Related Methods
|               Method Call               |   Args   |                   Purpose                    | Return Type |
| :-------------------------------------: | :------: | :------------------------------------------: | :---------: |
|        modelJson(JsonObject)            |    ->    | Set model JSON (possible, but not recommended) |   this    |
|         parentModel(string)             |    ~     | Set parent model                             |    this     |
|       textureJson(JsonObject)           |    ->    | Set texture JSON                             |    this     |
| barWidth(ToIntFunction\<ItemStack\>)    |    ~     | Durability bar width logic                   |    this     |
| barColor(Function\<ItemStack\>,Color)   |    ~     | Durability bar color logic                   |    this     |
|              glow(bool)                 |    ->    | Set enchanted glint effect                   |    this     |
|          tooltip(Component)             |    ->    | Set item tooltip content                     |    this     |

### Other Methods
|                       Method Call                         |                Args                 |           Purpose           |   Return Type    |
| :-------------------------------------------------------: | :---------------------------------: | :-------------------------: | :--------------: |
|        hurtEnemy(Predicate\<HurtEnemyContext\>)           |                 ->                  | Trigger when item hits mob  |       this       |
|                    rarity(Rarity)                         | [Rarity](../../Digression/Rarity)  | Set item rarity             |       this       |
|                   getRegistryType()                       |                  -                  | Get registry type           | RegistryInfo<T\> |
|        generateAssetJsons(AssetJsonGenerator)             |                  ?                  | ?                           |        -         |
|         generateDataJsons(DataJsonGenerator)              |                  ?                  | ?                           |        -         |
|                  name(NameCallback)                       |                  ~                  | Dynamic item name           |       this       |
|              color(int,ItemTintFunction)                  |                  ?                  | ?                           |       this       |
|                color(ItemTintFunction)                    |                  ?                  | ?                           |       this       |
|                 transformObject(Item)                     |                  ~                  | ~                           |       this       |
| subtypes(Function\<ItemStack,Collection\<ItemStack\>\>)    |                  ~                  | ?                           |       this       |
|            containerItem(ResourceLocation)                |                  ~                  | Set container item          |       this       |

## Simple Item Registration Helper Pattern

**Note: adapt this pattern based on your own style.**

```js
StartupEvents.registry("item", (event) => {
	// ModID declaration. If you want default "kubejs", remove this variable.
	const MODID = "meng:"

	/*
	* Define items
	* Add a comma after each [] entry when adding more items
	* Keep the format strict:
	* [itemId, rarityType, hasGlow]
	*/
	let itemRegisters = [
		["example_item", "common", false],
	]
	itemRegisters.forEach(([name, rarity, glow]) => {
		event.create(MODID + name) // declare id
			.rarity(rarity) // rarity
			.glow(glow) // enchanted glint
			.tag(MODID + "items") // add item tag (optional)
	})
})
```
