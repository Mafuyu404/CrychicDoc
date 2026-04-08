---
authors: ['Gu-meng']
---
# Registering Usable Items

```js
StartupEvents.registry('item', event => {
    event.create('use_item')
        /**
         * Item use animation (plays matching sound by animation type)
         * |'spear' (trident)|'crossbow' (crossbow)|'eat' (eat)|
         * |'spyglass' (spyglass)|'block' (block)|'none' (none)|
         * |'bow' (bow)|'drink' (drink)|
         */
        .useAnimation('drink')
        /**
         * Maximum use ticks (if right-click duration reaches this value,
         * the function in .finishUsing() is executed)
         * If return value is 0, the item is marked as unusable with use
         * - Related:
         * > Player ($Player) property "useItemRemainingTicks" is the remaining
         *   use time for the currently held item
         */
        .useDuration(itemstack => 64)
        /**
         * Whether the item can be used
         * When true and useDuration > 0, the item is used and plays animation/sound
         */
        .use((level, player, hand) => true)
        /**
         * Behavior after successful use
         * (right-click held for a full useDuration)
         */
        .finishUsing((itemstack, level, entity) => {
            entity.potionEffects.add('minecraft:haste', 120 * 20)
            itemstack.shrink(1)
            if (entity.player) {
                /**
                 * @type {$Player_} - Confirms player type for entity
                 */
                let player = entity
                player.addItem(Item.of('minecraft:glass_bottle').itemStack)
            }
            return itemstack
        })
        /**
         * Behavior when released before reaching full useDuration
         * tick = remaining ticks before full use would complete
         */
        .releaseUsing((itemstack, level, entity, tick) => {
            itemstack.shrink(1)
            level.createExplosion(entity.x, entity.y, entity.z).explode()
        })
})
```
