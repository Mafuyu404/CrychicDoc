---
authors: ['Gu-meng']
---
# Loot Tables
In this chapter, loot table changes are implemented with native KubeJS APIs, not LootJS. LootJS usage is covered in the mod chapter.

Native KubeJS provides the following 6 ways to modify or add loot tables.

Although there are six event types, their loot editing patterns are very similar.

Before writing loot scripts, it helps to review [Loot table](https://zh.minecraft.wiki/w/%E6%88%98%E5%88%A9%E5%93%81%E8%A1%A8?variant=zh-cn), [Loot predicate](https://zh.minecraft.wiki/w/%E6%88%98%E5%88%A9%E5%93%81%E8%A1%A8?variant=zh-cn), and optionally [Item modifier](https://zh.minecraft.wiki/w/%E7%89%A9%E5%93%81%E4%BF%AE%E9%A5%B0%E5%99%A8).

|         Loot Event Call         |    Purpose  |  Modify Method | Override Method |  Usage |
| :----------------------------: | :-------: | :----------: | :--------: | :-----------------------------------: |
| ServerEvents.genericLootTables | Global loot    | modify       | addGeneric | [Global Loot](./GlobalLootTable)   |
| ServerEvents.blockLootTables   | Block loot     | modifyBlock  | addBlock   | [Block Loot](./BlockLootTable)    |
| ServerEvents.entityLootTables  | Entity loot    | modifyEntity | addEntity  | [Entity Loot](./EntityLootTable)     |
| ServerEvents.giftLootTables    | Gift loot      | modify       | addGift    | [Gift Loot](./GiftLootTable)     |
| ServerEvents.fishingLootTables | Fishing loot   | modify       | addFishing | [Fishing Loot](./FishingLootTable)   |
| ServerEvents.chestLootTables   | Chest loot     | modify       | addChest   | [Chest Loot](./ChestLootTable) |

## Common `LootBuilderPool` Methods
|                         Method                          |                Parameters               |           Usage          |    Return Type    |
| :-----------------------------------------------------: | :-------------------------------------: | :----------------------: | :----------------: |
|              `setUniformRolls(int1,int2)`               | int1 -> min rolls, int2 -> max rolls   | Random roll count        |        void        |
|               `addCondition(JsonObject)`                |                    ~                    |            ~             | ConditionContainer |
| `addConditionalFunction(Consumer<ConditionalFunction>)` |                    ~                    |            ~             | FunctionContainer  |
|                     `addEmpty(int)`                     | int -> empty entry weight               | Set empty result weight  |   LootTableEntry   |
|                 `addEntry(JsonObject)`                  |                    ~                    |            ~             |   LootTableEntry   |
|                  `addItem(ItemStack)`                   |                   ->                    | Add an item              |   LootTableEntry   |
|                `addItem(ItemStack,int)`                 |          int -> weight ratio            | Add an item              |   LootTableEntry   |
|         `addItem(ItemStack,int,NumberProvider)`         | NumberProvider -> count range           | Add an item              |   LootTableEntry   |
|            `addLootTable(ResourceLocation)`             |                   ->                    | Add another loot table   |   LootTableEntry   |
|                  `addTag(string,bool)`                  | string -> tagId, bool -> pick one       | Add tag as loot entries  |   LootTableEntry   |
|          `randomChanceWithLooting(int1,int2)`           |      int1->chance int2->multiplier      |            ~             | ConditionContainer |
|                 `count(NumberProvider)`                 |                   ->                    | Set count range          | FunctionContainer  |
|                `damage(NumberProvider)`                 |                   ->                    | Set damage range         | FunctionContainer  |
|          `enchantRandomly(ResourceLocation[])`          |                   ->                    | Apply random enchantment | FunctionContainer  |
|        `enchantWithLevels(NumberProvider,bool)`         |                    ~                    |            ~             | FunctionContainer  |
|       `entityProperties(EntityTarget,JsonObject)`       |                    ~                    |            ~             | ConditionContainer |
|      `entityScores(EntityTarget,Map<string, any>)`      |                    ~                    |            ~             | ConditionContainer |
|                       `entries()`                       |                    -                    | Get entries list         |     JsonArray      |
|                   `killedByPlayer()`                    |                    -                    | Require player kill      | ConditionContainer |
|                   `randomChance(int)`                   |                   ->                    |            ~             | ConditionContainer |
|          `randomChanceWithLooting(int1,int2)`           |     int1-> chance int2-> multiplier     |            ~             | ConditionContainer |
|              `setBinomialRolls(int1,int2)`              |                    ~                    |            ~             |        void        |
|                  `survivesExplosion()`                  |                    -                    |            ~             | ConditionContainer |
|                    `furnaceSmelt()`                     |                    -                    | Apply smelting behavior  | FunctionContainer  |
