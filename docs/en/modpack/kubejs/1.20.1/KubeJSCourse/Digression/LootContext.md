---
authors: ['Gu-meng']
---
# Loot Context
## this
The entity that the loot is generated from.

## killer
The entity that indirectly caused death or damage.  
For example, if a player kills a mob using a bow, a thrown trident, or an Instant Damage potion, the player is the `killer`.

## direct_killer
The entity that directly caused death or damage.  
For example, if a player directly attacks a mob with a sword or trident, the player is `direct_killer`.

If the mob is killed by an arrow shot from a bow, the arrow is `direct_killer`.

If the mob is killed by a thrown trident, the trident is `direct_killer`.

If Instant Damage is applied by a thrown potion, that potion entity is `direct_killer`.

## killer_player
The direct killer is a player.
