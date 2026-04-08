---
authors: ['Gu-meng']
---
# Player Inventory Changed Event
This code belongs in server scripts.

There are two ways to use inventory-changed events:
1. `PlayerEvents.inventoryChanged(itemId, event => {})` captures when a specific item enters player inventory.
2. `PlayerEvents.inventoryChanged(event => {})` captures all inventory-change events.

## Directly Accessible Methods
|         Method          |             Purpose             |   Return Type   | Direct Value |
| :---------------------: | :----------------------------: | :-------------: | :--------: |
|       getEntity()       | Get the entity whose inventory changed (usually a player) |     Entity      |   entity   |
|        getItem()        | Get the item that caused the inventory change |    ItemStack    |    item    |
|       getLevel()        | Get the world where the inventory changed |      Level      |   level    |
|       getPlayer()       | Get the player whose inventory changed |     Player      |   player   |
|       getServer()       | Get the relevant server instance | MinecraftServer |   server   |
|        getSlot()        | Get the slot index that changed |     number      |    slot    |
|  hasGameStage(String)   | Check whether a game stage exists |     boolean     |     -      |
| removeGameStage(String) | Remove a game stage |      void       |     -      |
|  addGameStage(String)   | Add a game stage |      void       |     -      |

## Example
The example below checks whether the changed item is in a banned list. If it is, the item is removed.
```js
const banItems = [
    'minecraft:nether_star',
    'minecraft:lapis_lazuli',
    'minecraft:emerald',
    'minecraft:copper_ingot'
];

PlayerEvents.inventoryChanged(event=>{
    let item = event.getItem()
    if (undefined != banItems.find(value=> value==item.id)){
        event.getPlayer().getInventory().clear(item)
        event.player.tell("Contraband found: " + item.displayName.getString() + " removed!")
    }  
})
```
`const banItems = []` defines an array of controlled/banned item IDs.

`banItems.find(value=> value==item.id)` checks whether the item is in that list. If not found, it returns `undefined`.

So you can determine whether the item is banned by checking whether the result is `undefined`.

`event.getPlayer().getInventory().clear(item)` clears that item.

`event.player.tell()` sends a message to the player telling them the item was removed.
