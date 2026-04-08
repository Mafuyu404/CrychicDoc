---
authors: ['Gu-meng']
---
# Item Recipe Return Items
In theory this topic belongs in item attributes, but item attributes are broad, so it is separated into its own section.

This was mentioned in [Advanced Recipe Crafting](./AdvancedRecipe.md): recipe return items.

Vanilla already has similar behavior. For example, a milk bucket returns an empty bucket in crafting, and lava buckets also return buckets after fuel use.

This behavior is not `replaceIngredient` from *Advanced Recipe Crafting*. It is implemented by the item's `craftingRemainder` attribute.

## Example
```js
Item.of('minecraft:bedrock').item.craftingRemainder = Item.of('minecraft:water_bucket').item;
```
(This is written in `server` here, but it is not the recommended approach.)

The code above sets the item's `craftingRemainder` attribute.

So when **bedrock** is used in crafting, it returns a **water bucket**.

This return behavior is not only for crafting. If a fuel item has `craftingRemainder`, the remainder item is returned after fuel is consumed, similar to lava buckets in vanilla.

```js
Item.of('minecraft:bedrock').item.burnTime = 20000
```
Here we set the item as fuel with a burn time of 20000 ticks.

When this item is used as furnace fuel, a water bucket will be returned in place of bedrock after consumption.

If you want to **keep the ingredient in crafting**, set the remainder item to the same item itself.

### Standard Example (this one belongs in `startup`)
```js
ItemEvents.modification(event=>{
    event.modify('minecraft:bedrock',item=>{
        item.craftingRemainder = Item.of('minecraft:water_bucket').item;
        item.burnTime = 20000;
    });
})
```
This is the cleaner/recommended pattern.

## Notes
Pros:
* This is very generic and works in most recipe systems. For example, Create's mechanical crafting also returns this crafting remainder.

Cons:
* Because it is so generic, you cannot precisely control when it returns in specific contexts.
