---
authors: ['Gu-meng']
---
# Adding Enchantments
In KubeJS you can add enchantments very easily. Important: write these scripts in the `startup_scripts` folder.
```js
StartupEvents.registry("enchantment",event =>{
    event.create("meng:my_enchantment")
})
```
After running the code above, an enchanted book will exist in-game. However, it cannot be obtained directly from the enchanting table by default; you typically need help from other mods if you want it to appear there.

Next, you can configure properties on the enchantment.

# Adding Properties
## Setting What Items Can Be Enchanted
|    Method     |   Args   |                    Meaning                     |
| :-----------: | :------: | :--------------------------------------------: |
|    armor()    |   none   |                Enchant armor                   |
|  armorHead()  |   none   |                Enchant helmets                 |
| armorChest()  |   none   |              Enchant chestplates               |
|  armorFeet()  |   none   |                 Enchant boots                  |
|  armorLegs()  |   none   |                Enchant leggings                |
| fishingRod()  |   none   |              Enchant fishing rods              |
|     bow()     |   none   |                  Enchant bows                  |
|  crossbow()   |   none   |                Enchant crossbows               |
|   trident()   |   none   |                Enchant tridents                |
|  breakable()  |   none   |                    Unknown                     |
| vanishable()  |   none   |                    Unknown                     |
|  wearable()   |   none   |        Enchant wearable items in general        |
|   weapon()    |   none   |                 Enchant weapons                |
| category(str) | category | [Set a custom category](#enchantable-item-categories) |

If you call `armor()` and then call `bow()`, it does not mean "armor AND bow". The later call overrides the earlier one. Be careful not to call multiple category setters and end up with only the last one taking effect.

You may notice there is no explicit "digger" (tools) method. That's because if you do not call any category setter, the default is `digger`. In most cases, you can just use these helper methods and you do not need to call `category(...)`.

### Enchantable Item Categories
|   Category   | Description |
| :----------: | :---------- |
| fishing_rod  | Fishing rod |
| wearable     | Wearable    |
| trident      | Trident     |
| crossbow     | Crossbow    |
| armor_chest  | Chestplate  |
| vanishable   | Unknown     |
| bow          | Bow         |
| digger       | Digger/tools |
| weapon       | Weapon      |
| armor        | Armor       |
| armor_legs   | Leggings    |
| armor_head   | Helmet      |
| armor_feet   | Boots       |
| breakable    | Unknown     |

## Setting Rarity
[About rarity](../../Digression/Rarity)
|     Method     |   Args   |                       Meaning                       |
| :------------: | :------: | :-------------------------------------------------: |
| rarity(rarity) | rarity   | [Set rarity](../../Digression/Rarity#%E7%A8%80%E6%9C%89%E5%BA%A6%E7%9A%84%E7%AD%89%E7%BA%A7) |
|  uncommon()    |  none    | Set rarity to uncommon                              |
|    rare()      |  none    | Set rarity to rare                                  |
|  veryRare()    |  none    | Set rarity to very rare (epic)                      |

## Common Methods
|     Method     |   Args    |                    Meaning                    |
| :------------: | :-------: | :-------------------------------------------: |
| minLevel(int)  | integer   | Set the minimum enchantment level             |
| maxLevel(int)  | integer   | Set the maximum enchantment level             |
| untradeable()  | none      | Prevent villagers from selling this enchantment |
| curse()        | none      | Mark as a curse (displayed in red)            |

## Checking Whether An Item Can Be Enchanted (`canEnchant`)
If you want to allow enchantments on items that do not fit any category (for example a stick), or you want the enchantment to apply to all items, you can use `canEnchant`. Examples:
```js
StartupEvents.registry("enchantment",event =>{
    // Returning true means the enchantment can apply to any item
    event.create("meng:all_item_enchantment").canEnchant((item)=>{return true})
    // Only allow enchanting a specific item
    event.create("meng:item_stick_enchantment")
            .canEnchant((item)=>{
                // Return a boolean to indicate whether this item can be enchanted
                return item.id == "minecraft:stick"
            })
    // Allow multiple items
    event.create("meng:more_item_enchantment")
            .canEnchant((item)=>{
                if (item.id == "minecraft:stick"){
                    return true
                }else if(item.id == "minecraft:diamond"){
                    return true
                }
                return false
            })
})
```
You can also reject items based on what enchantments they already have, to implement conflicts (like Sharpness vs Smite vs Bane of Arthropods). A simple example:
```js
StartupEvents.registry("enchantment",event =>{
    event.create("meng:clash_test1")
            .canEnchant((item)=>{
                // Get all enchantments on the item
                let enchantments = item.getEnchantments()
                // Check whether the conflicting enchantment is present
                if (enchantments.get("meng:clash_test2") == null){
                    return true
                }else{
                    return false
                }
            })
        
        event.create("meng:clash_test2")
            .canEnchant((item)=>{
                // Get all enchantments on the item
                let enchantments = item.getEnchantments()
                // Check whether the conflicting enchantment is present
                if (enchantments.get("meng:clash_test1") == null){
                    return true
                }else{
                    return false
                }
            })
})
```
This makes `clash_test1` incompatible with `clash_test2`, and vice versa.

## Damage Bonus And Protection
You can also define damage bonuses or damage protection directly on the enchantment during registration.

### Damage Bonus (`damageBonus`)
This example deals extra damage to undead mobs equal to `level * 10`:
```js
StartupEvents.registry("enchantment",event =>{
    event.create("meng:undead_killer_pro")
            .weapon()
            .damageBonus((level,entityType)=>{
                    if (entityType == "undead"){
                        return level * 10
                    }  
            })
})
```
`level` is the enchantment level.

`entityType` is the entity category/type.

### Damage Protection (`damageProtection`)
This is similar to Protection / Fire Protection: reduce incoming damage.

Before writing this, you should understand how vanilla Protection works: [Armor mechanics (Minecraft Wiki, zh)](https://zh.minecraft.wiki/w/%E7%9B%94%E7%94%B2%E6%9C%BA%E5%88%B6#%E4%BF%9D%E6%8A%A4%E9%AD%94%E5%92%92%E6%9C%BA%E5%88%B6).

In short: if the returned value were 25, the player would fully negate the damage. But due to Minecraft's mechanics, the effective cap is 20, and anything above 20 behaves like 20.

For more details, see the wiki link above, or the in-doc page [Enchantment protection mechanics](../../Digression/ProtectionEnchantMechanism).

```js
StartupEvents.registry("enchantment",event =>{
    event.create("meng:undead_protect")
            .armor()
            .damageProtection((level,damageSource)=>{
                try{
                    /**
                    * @type {$LivingEntity}
                    */
                    let damageSourceLiving = damageSource.getActual() // Entity that actually caused the damage
                    let LivingEntityType = damageSourceLiving.getEntityType() // Entity type of the attacker
                    // Check whether the attacker is a Warden
                    if(LivingEntityType.toShortString() == "warden"){
                        return level * 5
                    }
                    let damageSourceEntity = damageSource.getImmediate() // Immediate source (e.g. arrow)
                }catch(e){
                    // If the damage was not caused by an entity (burning, fall, drowning, potion effects, etc.),
                    // getActual()/getImmediate() may fail and we handle it here.
                    console.log(damageSource.getType());
                    if (damageSource.getType() == "lava"){
                        return level * 2
                    }
                }
                
            })
})
```
Here is the difference between `getActual()` and `getImmediate()`:

Example: if a skeleton shoots an arrow and the arrow hits the player, the skeleton is `getActual()`, and the arrow is `getImmediate()`.

So you need to decide which part you want to use for your logic.

We use try/catch because not all damage comes from entities. Falling, burning, drowning, potion effects, etc. are not "entity damage", so calling `getActual()`/`getImmediate()` can throw errors. Wrapping it with [try/catch](https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Statements/try...catch) lets us handle those cases.

If it is not entity damage, you can check the damage type via `damageSource.getType()`. It returns a string, so you can do `if (damageSource.getType() == "lava")` to check for lava damage.

Using `console.log(damageSource.getType())` will write the damage type to `logs\\kubejs\\startup.log`. If you are not sure what damage type something uses, enchant armor with this and take that type of damage in-game to observe the value.


### Entity Type Categories
Unfortunately, damage bonus/protection does not target a single mob; it targets a category. The categories are:
1. `unknown` - no specific category
2. `undead` - undead mobs
3. `arthropod` - arthropods
4. `illager` - illagers (ravagers do not count)
5. `water` - water creatures

## Hurt And Attack Hooks
When registering an enchantment, you can hook into attack and hurt behaviors directly on the enchantment, without writing a separate event handler.

### Hurt (`postHurt`)
This "hurt" hook refers to damage received after an entity attack.

The example below: when wearing armor enchanted with this enchantment, after being attacked it steals health from the attacker and heals the wearer.
```js
StartupEvents.registry("enchantment",event =>{
    event.create("meng:health_theft")
            .armor()
            .postHurt((living,entity,level)=>{
                living.heal(level * 2)
                if (entity.isLiving()) {
                    entity.attack(level)
                }
            })
})
```
After registering the enchantment, do not forget to add a [lang file](../../Resources/Lang):
```json
{
    "enchantment.meng.health_theft": "Health Theft"
}
```

In `postHurt`, the first argument `living` is the entity that got hurt, the second argument `entity` is the attacker, and the third argument `level` is the enchantment level.

In this example, `living.heal(level * 2)` heals the wearer, and `entity.attack(level)` damages the attacker (simulating life drain).

### Attack (`postAttack`)
The example below: when attacking with a weapon enchanted with this enchantment, steal health and add it to the attacker.
```js
StartupEvents.registry("enchantment",event =>{
    event.create("meng:health_steal")
            .weapon()
            .minLevel(1)
            .maxLevel(5)
            .postAttack((living,entity,level)=>{
                if (entity.isLiving()) {
                    living.health += level
                }
            })
})
```
After registering the enchantment, do not forget to add a [lang file](../../Resources/Lang):
```json
{
    "enchantment.meng.health_steal": "Health Steal"
}
```
`if (entity.isLiving())` checks whether the target is a living entity. If so, it heals the attacker by the enchantment level.
