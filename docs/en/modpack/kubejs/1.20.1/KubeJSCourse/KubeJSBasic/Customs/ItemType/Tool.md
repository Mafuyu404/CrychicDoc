---
authors: ['Gu-meng']
---
# Tool Types
This section provides basic tool item syntax and commonly used methods.

## Basic Syntax
```js
StartupEvents.registry("item", event => {
    // Axe
    event.create("meng:my_axe", "axe")
    // Hoe
    event.create("meng:my_hoe", "hoe")
    // Pickaxe
    event.create("meng:my_pickaxe", "pickaxe")
    // Shovel
    event.create("meng:my_shovel", "shovel")
    // Sword
    event.create("meng:my_sword", "sword")
})
```
These are the tool item types provided by KubeJS.

## Available Methods
|               Method Name              |                          Parameter                       |                                 Usage                                | Return |
| :------------------------------------: | :------------------------------------------------------: | :------------------------------------------------------------------: | :------: |
|          speedBaseline(float)          |                            ->                            | Set base attack speed (offset from 4, sword default -2.4, axe -3.1) |   this   |
|              speed(float)              |                            ->                            | Set attack speed                                                      |   this   |
|      attackDamageBaseline(float)       |                            ->                            | Set base attack damage (offset from 3, sword default 3, axe default 6)|  this   |
|        attackDamageBonus(float)        |                            ->                            | Set bonus attack damage                                               |   this   |
| modifyTier(Consumer\<MutableToolTier>) |                            ~                             |                                  ~                                   |   this   |
|               tier(Tier)               | [mcwiki](https://zh.minecraft.wiki/w/%E5%93%81%E8%B4%A8) | Set tool tier                                                         |   this    |
