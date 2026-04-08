---
authors: ['Gu-meng']
---
# All Events
This chapter lists major events and example links, including events from KubeJS and from other mods.

## All Events Provided by KubeJS
Below are callable events provided by KubeJS. Pay attention to which folder each event belongs to.

Format: `MainEvent.SubEvent`
### `startup_scripts` (startup folder)
|   Main Event   |     Sub Event      |            Purpose            |                    Example                    |
| :------------: | :----------------: | :---------------------------: | :-------------------------------------------: |
| StartupEvents  |        init        |      Game initialization      |                       -                       |
| StartupEvents  |      registry      | Register game content         | [Link](../KubeJSBasic/Customs/README.md)      |
| WorldgenEvents |        add         | Add worldgen features         |                       -                       |
| WorldgenEvents |       remove       | Remove worldgen features      |                       -                       |
|   ItemEvents   |    modification    | Modify item properties        |                       -                       |
|   ItemEvents   |  toolTierRegistry  | -                             |                       -                       |
|   ItemEvents   | armorTierRegistry  | -                             |                       -                       |
|   ItemEvents   |  modelProperties   | -                             |                       -                       |
|  BlockEvents   |    modification    | Modify block properties       |                       -                       |

### `server_scripts` (server folder)
|   Main Event   |        Sub Event         |                 Purpose                 |                              Example                              |
| :------------: | :----------------------: | :-------------------------------------: | :---------------------------------------------------------------: |
|  ServerEvents  |     lowPriorityData      | -                                       |                                 -                                 |
|  ServerEvents  |     highPriorityData     | -                                       |                                 -                                 |
|  ServerEvents  |          loaded          | Server reload/load event                |                                 -                                 |
|  ServerEvents  |         unloaded         | -                                       |                                 -                                 |
|  ServerEvents  |           tick           | Game tick event                         |                                 -                                 |
|  ServerEvents  |           tags           | Tag event                               |                                 -                                 |
|  ServerEvents  |      commandRegistry     | Command registration event              |                                 -                                 |
|  ServerEvents  |         command          | Server command event                    |                                 -                                 |
|  ServerEvents  |      customCommand       | Custom commands                         | [Link](../KubeJSAdvanced/CustomCommandRegistry)                  |
|  ServerEvents  |         recipes          | Recipe event                            | [Link](./BasicSyntax-Add)                                         |
|  ServerEvents  |       afterRecipes       | Post-recipe processing (has known bugs) |                                 -                                 |
|  ServerEvents  | specialRecipeSerializers | -                                       |                                 -                                 |
|  ServerEvents  |    compostableRecipes    | -                                       |                                 -                                 |
|  ServerEvents  |    recipeTypeRegistry    | -                                       |                                 -                                 |
|  ServerEvents  |    genericLootTables     | Global loot event                       | [Link](./LootTables/GlobalLootTable)                              |
|  ServerEvents  |     blockLootTables      | Block loot event                        | [Link](./LootTables/BlockLootTable)                               |
|  ServerEvents  |     entityLootTables     | Entity loot event                       | [Link](./LootTables/EntityLootTable)                              |
|  ServerEvents  |      giftLootTables      | Villager gift loot event                | [Link](./LootTables/GiftLootTable)                                |
|  ServerEvents  |    fishingLootTables     | Fishing loot event                      | [Link](./LootTables/FishingLootTable)                             |
|  ServerEvents  |     chestLootTables      | Chest loot event                        | [Link](./LootTables/ChestLootTable)                               |
|   LevelEvents  |          loaded          | Level/world loaded event                |                                 -                                 |
|   LevelEvents  |         unloaded         | -                                       |                                 -                                 |
|   LevelEvents  |           tick           | Level/world tick event                  |                                 -                                 |
|   LevelEvents  |      beforeExplosion     | -                                       |                                 -                                 |
|   LevelEvents  |      afterExplosion      | -                                       |                                 -                                 |
| NetworkEvents  |        fromClient        | -                                       |                                 -                                 |
|   ItemEvents   |       rightClicked       | Item right-click event                  |                                 -                                 |
|   ItemEvents   |        canPickUp         | -                                       |                                 -                                 |
|   ItemEvents   |         pickedUp         | Item pickup event                       |                                 -                                 |
|   ItemEvents   |         dropped          | Item drop event                         |                                 -                                 |
|   ItemEvents   |     entityInteracted     | -                                       |                                 -                                 |
|   ItemEvents   |         crafted          | -                                       |                                 -                                 |
|   ItemEvents   |         smelted          | -                                       |                                 -                                 |
|   ItemEvents   |        foodEaten         | Food consumed event                     |                                 -                                 |
|   ItemEvents   |    firstRightClicked     | -                                       |                                 -                                 |
|   ItemEvents   |     firstLeftClicked     | -                                       |                                 -                                 |
|  BlockEvents   |       rightClicked       | Block right-click event                 | [Link](../KubeJSAdvanced/EventExamples/BlockRightClickedEvent.md) |
|  BlockEvents   |       leftClicked        | Block left-click event                  |                                 -                                 |
|  BlockEvents   |          placed          | Block placed event                      |                                 -                                 |
|  BlockEvents   |          broken          | Block broken event                      |                                 -                                 |
|  BlockEvents   |     detectorChanged      | -                                       |                                 -                                 |
|  BlockEvents   |     detectorPowered      | -                                       |                                 -                                 |
|  BlockEvents   |    detectorUnpowered     | -                                       |                                 -                                 |
|  BlockEvents   |     farmlandTrampled     | -                                       |                                 -                                 |
| EntityEvents   |          death           | Entity death event                      |                                 -                                 |
| EntityEvents   |           hurt           | Entity hurt event                       |                                 -                                 |
| EntityEvents   |        checkSpawn        | -                                       |                                 -                                 |
| EntityEvents   |         spawned          | Entity spawn event                      |                                 -                                 |
| PlayerEvents   |         loggedIn         | Player login event                      |                                 -                                 |
| PlayerEvents   |        loggedOut         | Player logout event                     |                                 -                                 |
| PlayerEvents   |        respawned         | Player respawn event                    |                                 -                                 |
| PlayerEvents   |           tick           | Player tick event                       |                                 -                                 |
| PlayerEvents   |           chat           | Player chat event                       |                                 -                                 |
| PlayerEvents   |       decorateChat       | -                                       |                                 -                                 |
| PlayerEvents   |       advancement        | -                                       |                                 -                                 |
| PlayerEvents   |      inventoryOpened     | Player inventory open event             |                                 -                                 |
| PlayerEvents   |      inventoryClosed     | Player inventory close event            |                                 -                                 |
| PlayerEvents   |     inventoryChanged     | Player inventory changed event          | [Link](../KubeJSAdvanced/EventExamples/PlayerInventoryChangedEvents.md) |
| PlayerEvents   |       chestOpened        | Player chest open event                 |                                 -                                 |
| PlayerEvents   |       chestClosed        | Player chest close event                |                                 -                                 |

### `client_scripts` (client folder)
|   Main Event   |     Sub Event      |         Purpose         |      Example       |
| :------------: | :----------------: | :---------------------: | :----------------: |
|  ClientEvents  | highPriorityAssets | -                       |         -          |
|  ClientEvents  |        init        | -                       |         -          |
|  ClientEvents  |      loggedIn      | -                       |         -          |
|  ClientEvents  |      loggedOut     | -                       |         -          |
|  ClientEvents  |        tick        | Client tick event       |         -          |
|  ClientEvents  |   painterUpdated   | -                       |         -          |
|  ClientEvents  |   leftDebugInfo    | -                       |         -          |
|  ClientEvents  |   rightDebugInfo   | -                       |         -          |
|  ClientEvents  |    paintScreen     | -                       |         -          |
| NetworkEvents  |     fromServer     | -                       |         -          |
|   ItemEvents   |      tooltip       | Item tooltip event      | [Link](./Tooltip)  |
|   ItemEvents   | clientRightClicked | -                       |         -          |
|   ItemEvents   | clientLeftClicked  | -                       |         -          |

## Events Provided by Other Mods
