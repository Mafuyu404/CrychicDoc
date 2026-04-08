---
authors: ['Gu-meng']
---
# Protection Enchantment Mechanism
For full details, see [mcwiki](https://zh.minecraft.wiki/w/%E7%9B%94%E7%94%B2%E6%9C%BA%E5%88%B6#%E4%BF%9D%E6%8A%A4%E9%AD%94%E5%92%92%E6%9C%BA%E5%88%B6). This page is mainly a summary.

## Definition
Armor enchantments are a second defensive layer after armor points. These include Protection, Projectile Protection, Fire Protection, Blast Protection, and Feather Falling. Damage reduced by enchantments does not consume armor durability.

In Survival, the highest level for protection-type enchantments is IV (4). Except for Feather Falling, these protection enchantments are mutually exclusive on the same armor piece.

Each protection enchantment covers specific damage types, and reduction depends on its Enchantment Protection Factor (EPF).

**EPF from protection enchantments on multiple equipped items stacks together.**

## EPF by Enchantment
|   Enchantment   | Level I | Level II | Level III | Level IV |                        Damage Types Reduced                        |
| :--------: | :--------: | :---------: | :----------: | :---------: | :---------------------------------------------------: |
|    Protection    |     1      |      2      |      3       |      4      | All damage types except hunger, void, Warden sonic attack, and `/kill` |
|  Fire Protection  |     2      |      4      |      6       |      8      |               Fire, lava, magma blocks, and small fireballs               |
|  Blast Protection  |     2      |      4      |      6       |      8      |                          Explosions                          |
| Projectile Protection |     2      |      4      |      6       |      8      |                        Projectiles                         |
|  Feather Falling  |     3      |      6      |      9       |     12      |               Fall damage and ender pearl teleport damage                |

## Calculation Rules
When calculating protection effects, all relevant EPF values are added together into total EPF. Damage reduction ratio is `total EPF / 25`. EPF has a minimum of 0 and maximum of 20.

With **four Protection IV** pieces and **one Feather Falling IV**, EPF against fall damage can reach 28, but it is capped to 20.

**Negative-level protection enchantments reduce EPF from other protection enchantments, but not below 0, so final damage never exceeds pre-reduction damage.**

Because final EPF is capped, **protection enchantments can reduce at most 80% damage**, meaning **damage cannot be reduced below 20% of its original value**.

Enchantment protection is applied after armor-point reduction, and reduces the already armor-reduced damage.

You can reach the cap for a specific damage type with only 3 armor pieces. For example: `two Blast Protection IV pieces (EPF 8 each) plus one Protection IV piece (EPF 4) gives 20 EPF vs explosion damage, which is the maximum effective value`.
