---
authors: ['Gu-meng']
---
# Add Food
This chapter introduces how to add food items using the item registry event. All JS code in this chapter is under the `startup_scripts` folder.

## Basic Syntax
```js
StartupEvents.registry("item", event => {
    event.create("meng:my_food")
        .food(foodBuilder=>{})
})
```
This is the most basic syntax. It marks the item as food, then you can configure food-related parameters.

## Food Builder Methods
|               Method Name              |       Parameter       |              Usage             |            Note          |
| :------------------------------------: | :-------------------: | :----------------------------: | :----------------------: |
|           saturation(float)            |      Saturation       | Set food saturation multiplier | `value * hunger = sat`   |
|            hunger(integer)             |        Hunger         | Set hunger restored            | Hunger bar ("drumsticks")|
|                 meat()                 |           -           | Mark as meat                   | Can be eaten by wolves   |
|             alwaysEdible()             |           -           | Always edible                  | Eat even at full hunger  |
|              fastToEat()               |           -           | Faster eating speed            | Eat quickly              |
|        removeEffect(MobEffect)         |     Potion effect     | Remove one effect after eating | -                        |
|      eaten(Consumer\<FoodEatenEvent\>)    |   After-eat event     | Trigger callback               | -                        |
| effect(ResourceLocation,int,int,float) | [effect args](#effect)| Add effect when eaten          | -                        |
|                build()                 |           -           | Return `FoodProperties`        | -                        |

[Difference between saturation and hunger](../../../Digression/SaturationHunger)

### effect
```
effect(
    ResourceLocation, potion effect id
    int, duration in ticks
    int, effect amplifier (actual level is n+1)
    float, chance to apply effect (1 = 100%)
)
```

## Potion Effect Settings
### Add a Potion Effect
```js
StartupEvents.registry("item", event => {
    event.create("meng:my_food")
        .food(foodBuilder=>{
            foodBuilder.effect("minecraft:speed",20*20,0,0.5)
        })
})
```
When the player eats this item, it gives Speed I for 20 seconds with a 50% chance.

### Remove a Potion Effect
```js
StartupEvents.registry("item", event => {
    event.create("meng:my_food2")
        .food(foodBuilder=>{
            foodBuilder.removeEffect("speed")
        })
})
```
This seems to fail in practice (it may not work as expected).

## After-Eat Event
```js
const { $Player } = require("packages/net/minecraft/world/entity/player/$Player")

StartupEvents.registry("item", event => {
    event.create("meng:my_food3")
        .food(foodBuilder=>{
            foodBuilder.eaten(foodEatenEvent=>{
                /**
                 * @type {$Player}
                 */
                let player = foodEatenEvent.getPlayer()
                if (foodEatenEvent.getPlayer() != null){
                    player.give("bowl")
                }
            })
        })
})
```
If the eater is a player, return a bowl to them (the check is to avoid non-player entities).

### Methods Available in the Event
| Method Name |   Return Type   |
| :---------: | :-------------: |
|  getItem()  |    ItemStack    |
| getEntity() |     Entity      |
| getPlayer() |     Player      |
| getLevel()  |      Level      |
|  getServer  | MinecraftServer |

## Simple Example
```js
event.create("meng:my_food4")
        .food(foodBuilder=>{
            foodBuilder.hunger(10) // Restores 5 hunger icons ("drumsticks")
            foodBuilder.saturation(0.5) // Saturation is 10 * 0.5 = 5
            foodBuilder.meat() // Mark as meat, can be eaten by wolves
            foodBuilder.alwaysEdible() // Can be eaten even when not hungry
            foodBuilder.fastToEat() // Make it quick to eat
        })
```
