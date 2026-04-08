# Advanced Recipe Crafting
Everything in this chapter applies to KubeJS `shapeless` and `shaped` recipes only. It does not apply to other recipe types, even if you can technically call similar methods.

KubeJS only adapts its own recipe APIs and does not adapt other crafting systems, including vanilla `recipes.minecraft.xxx`.

So make sure you are using `shapeless` and `shaped`, or `recipes.kubejs.shaped` and `recipes.kubejs.shapeless`.

For why vanilla water buckets return empty buckets in crafting, see --> [Set Item Crafting Return Item](./ItemRecipeReturnItem.md)

## NBT on Crafting Result: `modifyResult`
The code below demonstrates crafting with enchanted inputs and custom output logic.

This is only an example of how to use `modifyResult`. Adjust the scenario to your own needs.
```js
ServerEvents.recipes(event=>{
    event.recipes.kubejs.shaped('minecraft:soul_torch',[
        ['minecraft:torch','minecraft:torch','minecraft:torch'],
        ['minecraft:torch','minecraft:stone','minecraft:torch'],
        ['minecraft:torch','minecraft:torch','minecraft:torch']
    ]).modifyResult((inputItemGrid,outputItem)=>{
        let stone = inputItemGrid.find("stone")
        let items = inputItemGrid.findAll("torch");
        for (let i = 0; i < items.length; i++) {
            if (!items[i].hasEnchantment("looting",2)){
                return "air"
            }
        }
        if (Math.random() < 0.5){
           let ci =  items[0].copy();
           ci.count = 1;
           return ci
        } 
        return outputItem;
    })
})
```
In `modifyResult`, the first parameter `inputItemGrid` is the crafting-grid input, and the second parameter `outputItem` is the output item. You must return an item as the final output (`return ItemStack`).

`inputItemGrid.find(itemId)` searches for an item in the crafting grid and returns an **`ItemStack`**.

`ItemStack.hasEnchantment(enchantmentId, level)` checks enchantments on the item and returns `bool`.

`ItemStack.enchant(enchantmentId, level)` enchants the item. Since it does not directly mutate the original item in this context, ***store the returned value in a variable***.

## Crafting with Durability Cost: `damageIngredient`
Example: use any axe with logs in a crafting table to make 8 planks, consuming axe durability.
```js
ServerEvents.recipes((event) => {
	event.shapeless(Item.of('minecraft:oak_planks', 8), [
		'minecraft:oak_log', "#minecraft:axes"
	]).damageIngredient({ tag: "#minecraft:axes" }, 5)
})
```
In `damageIngredient`, the first parameter is a matcher, and the second is the durability amount consumed.

In the matcher, `item` matches an item, and `tag` matches a tag.

## Crafting with Return Item: `replaceIngredient`
Example: use Strong Healing potion + Dragon's Breath to craft a Lingering Strong Healing potion, and return a glass bottle.
```js
ServerEvents.recipes(event => {
    event.shapeless(
        Item.of('minecraft:lingering_potion', '{Potion:"minecraft:strong_healing"}').strongNBT(),
        ['minecraft:dragon_breath', Item.of('minecraft:potion', '{Potion:"minecraft:strong_healing"}').strongNBT()]
    ).replaceIngredient({ item: "minecraft:dragon_breath" }, Item.of('minecraft:glass_bottle'))
})
```

In `replaceIngredient`, the first parameter is the matcher for the ingredient to replace, and the second is the replacement item.

Matcher rules are the same as above.

## Crafting While Keeping Ingredient: `keepIngredient`
In the example below, crafting with stick + honeycomb into blaze rod does not consume the honeycomb.
```js
ServerEvents.recipes(event => {
    event.shapeless(
        'minecraft:blaze_rod',
        ['minecraft:stick', 'minecraft:honeycomb']
    ).keepIngredient({ item: "minecraft:honeycomb" })
})
```

`keepIngredient` takes a matcher parameter, with the same rules as above.
