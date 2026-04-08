---
authors: ['Gu-meng']
---
# Block Right-Click Event
This code belongs in server scripts.

There are two ways to use block right-click events:
1. `BlockEvents.rightClicked(blockId, event => {})` captures right-click events for a specific block.
2. `BlockEvents.rightClicked(event => {})` captures right-click events for all blocks.

In most cases, use the first form with a specific block. Use the second form only when needed.

## Directly Accessible Methods
|   Method    |                 Purpose                 |   Return Type    | Direct Value |
| :---------: | :--------------------------------------: | :--------------: | :--------: |
| getBlock()  | Get the clicked block info | BlockContainerJS |   block    |
| getEntity() | Get the entity clicking the block (typically the player here) |      Player      |   entity   |
| getFacing() | Get the face/direction that was clicked |    Direction     |   facing   |
|  getHand()  | Get which hand performed the click | InteractionHand  |    hand    |
|  getItem()  | Get the item used to right-click |    ItemStack     |    item    |
| getLevel()  | Get the world of the clicked block |      Level       |   level    |
| getPlayer() | Get the player who right-clicked |      Player      |   player   |
| getServer() | Get the server instance | MinecraftServer  |   server   |

## Example
The example below uses a block right-click event. When a player right-clicks oak planks with an item tagged as an axe, the planks are destroyed and 8 sticks are dropped.
```js
BlockEvents.rightClicked('minecraft:oak_planks', event => {
    if (event.hand == "OFF_HAND") return
    let player = event.getPlayer()
    if (player == null) return
    let isBreak = false;
    if (event.getItem().hasTag("minecraft:axes")){
        let spawnItem = event.getLevel().createEntity("item")
        spawnItem.pos = event.block.pos
        spawnItem.item = Item.of('minecraft:stick', 8);
        event.level.destroyBlock(event.block.pos,false)
        spawnItem.spawn();
        event.getItem().setDamageValue(event.getItem().getDamageValue() + 2)
    }
})
```
The block right-click event can fire twice for a player: main hand first, then off hand.

So check `if (event.hand == "OFF_HAND")` to filter the hand.

`event.getItem()` returns the item in `event.hand`.

`event.getItem().hasTag("minecraft:axes")` checks whether the item has that tag.

`event.level.destroyBlock(event.block.pos,false)` destroys the block at that position, and `false` means no default drops.
