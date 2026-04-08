---
authors: ['Gu-meng']
---
# Basic Syntax: Remove And Modify Recipes
This chapter introduces **recipe filters**, and how to use them to remove or modify recipes.

## Recipe Filters
In KubeJS, you can use recipe filters when removing or modifying recipes to match recipes that meet certain conditions.

The most basic recipe filters are these five:
```js
{output : 'item_id'}
{input : 'item_id'}
{mod : 'mod_id'}
{id : 'recipe_id'}
{type : 'recipe_type'}
```
Explanation:
1. `output`: matches the output item. If A+B+C crafts into D, then D is the output. When removing recipes, any recipe whose output matches D will be removed.
2. `input`: matches an input ingredient. If A+B+C crafts into D, then A/B/C are inputs. If you set `input` to B, then any recipe that contains B as an ingredient can match.
3. `mod`: matches a mod id. For example, if you set `mod: "create"`, it can match recipes from Create.
4. `id`: matches a specific recipe id. If you have JEI installed and enable advanced tooltips with `F3+H`, you can see recipe ids. Using `id` lets you target the recipe precisely.
5. `type`: matches the recipe type (crafting table, furnace, etc.). Using `type` can remove an entire category of recipes.

In addition to these basics, KubeJS also supports combining filters. In real projects you often need stricter matching, so here are three more patterns:
```js
{basicFilter1, basicFilter2, basicFilter3, ...}
[{basicFilter1, basicFilter2, basicFilter3, ...}, {basicFilter1, basicFilter2, basicFilter3, ...}, ...]
{not:{basicFilter1, basicFilter2, basicFilter3, ...}}
```
Explanation and examples:
1. The first form means "match all conditions". If any condition is not satisfied, the recipe does not match. For example, for the `input` filter you might write:
```js
{{input : 'stone'}},{input : 'redstone'},{input : 'grass'}}
```
Then a recipe would need to satisfy all three conditions to match. If it only satisfies one or two, it would not match.

2. The second form is similar, but it is an array of "match all" groups. If the recipe matches any one group in the array, it matches.
3. The third form is the negation form. It matches recipes that do NOT match the inner filter.

## Removing Recipes
Now that we've introduced recipe filters, let's use them. It's easier to understand by trying them directly.

There is only one method to remove recipes: `event.remove(...)`. Everything depends on the recipe filters described above.
```js
ServerEvents.recipes(event => {
    // Remove all recipes whose output is stone bricks
    event.remove({output : 'stone_bricks'});
    // Remove all recipes that have stone bricks as an input
    event.remove({input : 'stone_bricks'});
    // Remove all recipes from the minecraft namespace
    event.remove({mod : 'minecraft'});
    // Remove the recipe with id minecraft:oak_wood (4 oak logs -> oak wood)
    event.remove({id : "minecraft:oak_wood"});
    // Remove all smelting recipes
    event.remove({type : "smelting"});
});
```
Aside from removing by exact recipe id, the other filters can match a very broad set of recipes. If an item has both crafting and smelting recipes and you only want to remove the smelting one, you should combine filters:
```js
ServerEvents.recipes(event => {
    event.remove({type:"crafting_shaped",output:"end_stone_bricks"});
});
```
In 1.20, end stone bricks can be crafted via crafting table and stonecutter. The filter above only removes the crafting-table recipe; the stonecutter recipe still exists.

**<font color=red>Note: currently, you cannot remove recipes that you added yourself via KubeJS, no matter what method you use.</font>**

## Replacing Ingredients/Outputs
The advanced filters become more useful when replacing inputs/outputs.

KubeJS provides two replacement helpers:
`replaceInput` (replace matching input ingredients) and `replaceOutput` (replace matching outputs).

Here are three examples:
```js
ServerEvents.recipes(event =>{
    // Only match recipes that include both stick and the planks tag, then replace stick with blaze rod
    event.replaceInput({input:'minecraft:stick',input:'#minecraft:planks'},'minecraft:stick','minecraft:blaze_rod');
    // Only match recipes whose output is dispenser/dropper/furnace, then replace cobblestone input with diamond
    event.replaceInput([{output:'minecraft:dispenser'},{output:'minecraft:dropper'},{output:'minecraft:furnace'}],"minecraft:cobblestone",'minecraft:diamond');
    // For recipes that do NOT match (stick + planks), replace outputs in the logs tag with stone bricks
    event.replaceOutput({not:{input:'minecraft:stick',input:'#minecraft:planks'}},'#minecraft:logs','minecraft:stone_bricks');
})
```

### A Simple Helper Snippet For Removing Recipes
```js
// Put the things you want to remove into the arrays below. Remember to separate entries with commas.
/*
 * let output = [
 *		"minecraft:crafting_table",
 *		"create:brass_ingot"
 *	]
 *	output.forEach((item) => {
 *		event.remove({ output: item })
 *	})
 */
ServerEvents.recipes((event) => {
	// Outputs
	let output = [
		
	]
	output.forEach((item) => {
		event.remove({ output: item })
	})

	// Inputs
	let input = [

	]
	input.forEach((item) => {
		event.remove({ input: item })
	})

	// Recipe types
	let recipeType = [
		
	]
	recipeType.forEach((type) => {
		event.remove({ type: type })
	})

	// Mod recipes
	let modRecipes = [
		
	]
	modRecipes.forEach((modid) => {
		event.remove({ mod: modid })
	})
})
```
