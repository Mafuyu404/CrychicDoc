---
authors: ['Gu-meng']
---
# Item Rarity
**Rarity mainly affects the in-game color of item names (if the item name color is set explicitly, rarity color will not override it).**
## Rarity Levels
When using the API, pay attention to your `ProbeJS` version.

If version is **7.0 or above**, import like this: `const { $Rarity } = require("packages/net/minecraft/world/item/$Rarity")`

If version is **below 7.0**, import like this: `const $Rarity = Java.loadClass("net.minecraft.world.item.Rarity")`
|  Level   | Meaning | Name Color |      API Value      | String Value |
| :------: | :---: | :------: | :--------------: | :--------: |
|  Common  | Common  |   White   |  $Rarity.COMMON  |   common   |
| Uncommon | Uncommon  |   Yellow   | $Rarity.UNCOMMON |  uncommon  |
|   Rare   | Rare  |   Aqua   |   $Rarity.RARE   |    rare    |
|   Epic   | Epic  |  Magenta  |   $Rarity.EPIC   |    epic    |

## Notes
[Reference from mcwiki](https://zh.minecraft.wiki/w/%E7%A8%80%E6%9C%89%E5%BA%A6?variant=zh-cn)
Rarity is shown in these cases:
1. Item name in tooltip when selected in inventory
2. Item name shown above the hotbar when selected
3. Item names shown inside JSON text messages, including death screen messages, chat death messages, and `/tellraw`/`give` text output

Rarity is not shown in these cases:
1. Renamed item names displayed in item frames
2. Item names shown in a shulker box tooltip preview
3. Item names on the statistics screen
