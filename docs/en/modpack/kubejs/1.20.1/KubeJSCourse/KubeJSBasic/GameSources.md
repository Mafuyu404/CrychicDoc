---
authors: ['Gu-meng']
---
# Global Resource Access

Game resources in KubeJS that can be used globally.

| Keyword | Name | Summary | Related Link |
|:-----:|:----:|:---:|:-------:|
| Item | Item | Precisely represent one item | [✔](#item) |
| Ingredient | Item Tag | Represent all items in one tag | [✔](#ingredient) |
| Fluid | Fluid | Precisely represent one fluid/type | - |

## Item

| Call | Return Value |
|:-------:|:-----:|
| Item.of(`itemType(string)`) | `ItemStack` for that item |
| Item.of(`itemType(string)`, `count(integer)`) | `ItemStack` with that count |
| Item.of(`itemType(string)`, `itemNBT(string)`) | `ItemStack` with that NBT |
| Item.of(`itemType(string)`, `count(integer)`, `itemNBT(string)`) | `ItemStack` with count + NBT |
| Item.playerHead(`playerName(string)`) | Player head `ItemStack` for that name |

## Ingredient

| Call | Return Value |
|:-------:|:-----:|
| Ingredient.of(`tagNamespace`) | Matching `Ingredient` |

**Note** tag namespace syntax:
* `#` `tagName` means a tag, containing all items with that tag.
* `@` `modName` means a mod, containing all items from that mod.
* `%` `creativeTab` means a creative inventory tab category, containing items in that tab.
