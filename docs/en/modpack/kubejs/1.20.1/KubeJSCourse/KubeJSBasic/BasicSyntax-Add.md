---
authors: ['Gu-meng']
---
# Basic Syntax (Adding Recipes)
This chapter shows how to add or tweak basic recipes, such as crafting table and furnace recipes. For more advanced topics, see [Advanced Recipes](../KubeJSAdvanced/AdvancedRecipe).

## Crafting Table
Crafting supports both shaped and shapeless recipes. In KubeJS you can write them using the "simple" style or the JSON-style builder. Choose whichever you prefer.

### Shaped Recipes
#### JSON-style
First, let's look at the JSON-style syntax:
```js
ServerEvents.recipes(event => {
    event.shaped("3x minecraft:stone", [
		'DDD',
		'   ',
		'W W'
	], {
		D: 'minecraft:diamond',
		W: 'minecraft:white_wool'
    });
});
```
In the code above:
The first argument is the output item and output count. Here it is written as a string. If you prefer, you can also write it as `Item.of(itemId, count)`.

The second argument is an array representing the 3x3 crafting grid. Each letter is a key that stands for an ingredient. Empty slots can be spaces.

The third argument is a JSON object mapping those keys to actual ingredients. In this example, `D` is diamond and `W` is white wool. You can use any letters you like, but you must define what each placeholder means.

#### Simple-style
If you have used CraftTweaker before, this may feel familiar. It is a simpler, more direct syntax:
```js
ServerEvents.recipes(event => {
    event.shaped(Item.of('minecraft:white_wool', 3), [
        ['minecraft:white_wool','minecraft:beacon'],[],
        ['minecraft:beacon','','minecraft:beacon']]);
});
```
In the code above, the first argument is the output item and count.

The second argument is a 2D array. Each inner array represents a row in the crafting grid. Use item ids for ingredients. Use an empty string (`\"\"`) for an empty slot (like the second entry in the third row above). If an entire row is empty you can leave the row as an empty array (like the second row above).

### Shapeless Recipes
```js
ServerEvents.recipes(event => {
    event.shapeless(Item.of('minecraft:redstone',2),[
        'minecraft:stone','minecraft:beacon','minecraft:white_wool','minecraft:enchanting_table'
    ]);
});
```
For shapeless crafting, the second argument is just a list of input ingredients (item ids).

## Furnace-Type Recipes
This section covers the "cooking" family: furnace, smoker, blast furnace, and campfire.

The syntax is almost the same for all of them, so they are shown together.

```js
ServerEvents.recipes(event => {
    // Furnace
    event.smelting('minecraft:bell','minecraft:gold_ingot',1000,2000);
    // Smoker
    event.smoking('minecraft:bell','minecraft:gold_ingot',1000,2000);
    // Campfire
    event.campfireCooking('minecraft:bell','minecraft:gold_ingot',0,2000);
    // Blast furnace
    event.blasting('minecraft:bell','minecraft:gold_ingot',1000,2000);
});
```
In the calls above:
The first argument is the output item, the second is the input ingredient, the third is the XP produced by the recipe, and the fourth is the cook time in ticks. (20 ticks = 1 second, so you can write `20 * seconds`.)

### Setting An Item As Fuel
Yes, you can also make an item act as fuel in KubeJS:
```js
Item.getItem('stone').burnTime = 20*10;
```
Put the item id in `getItem(...)`, then assign `.burnTime` to the fuel time in ticks. Here we set it to 10 seconds, so `20 * 10`.

## Smithing Table
Smithing is also simple. Conceptually it is: **item1 + item2 => item3**.
```js
ServerEvents.recipes(event => {
    event.smithing('minecraft:golden_apple','stone','minecraft:apple', 'minecraft:gold_ingot');
});
```
In the code above, the first argument is the output. The second argument is the smithing template (the first slot). The third and fourth arguments are the other two inputs.

If you do not want to specify a custom smithing template, you can omit it and shift the other parameters forward. The default is the netherite upgrade template.

## Stonecutter
```js
ServerEvents.recipes(event => {
    event.stonecutting('minecraft:golden_apple', '#minecraft:planks');
});
```
The first argument is the output and the second is the input. Note the `#`: this is not an item id but a tag. Any item in the `minecraft:planks` tag can be used as input.

## About Recipe IDs
In the recipes above we did not set a recipe id, and unlike CraftTweaker it does not error. That is because KubeJS automatically generates an id. If you want to set your own id, add `.id(\"your_id\")` at the end:
```js
ServerEvents.recipes(event => {
    event.shapeless(Item.of('minecraft:sand', 2), [
        'minecraft:stone',
		'minecraft:beacon',
		'minecraft:white_wool',
		'minecraft:enchanting_table'
    ]).id("meng_sand");
});
```
[**Recipe ID deep dive**](../Digression/RecipeId)

Important: if you write an id like `\"meng_sand\"` (no namespace), it defaults to the `minecraft` namespace.

If you write it like `meng:sand`, the recipe id is in the `meng` namespace, similar to `modid:recipe_name`.

If this is unclear, see [Namespace](../Digression/NameSpace).
