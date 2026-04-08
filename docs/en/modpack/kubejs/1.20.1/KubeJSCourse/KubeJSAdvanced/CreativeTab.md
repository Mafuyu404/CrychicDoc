---
authors: ['Gu-meng']
---
# Creative Tabs
In Creative mode, players can directly pick items from creative tabs in the inventory. Some mods also put items into their own tabs. For example, in vanilla Minecraft, pistons, buttons, and levers are in the Redstone tab.

This chapter explains how to register your own creative tab and move/add items into it. All examples below are written in `startup_scripts`.

## Register a Creative Tab
```js
StartupEvents.registry("creative_mode_tab", (event) => {
	// Register a creative tab and assign its ID
	let tab = event.create("meng:items")
	// Set the creative tab icon (the item must exist)
	tab.icon(() => Item.of("meng:hello_item"))
	// Set the displayed name using a localization key
	tab.displayName = Text.translatable("item_group.meng.items")
	// Add items to the tab
	tab.content(() => [
		"meng:hello_item"
	])
})
```

## Modify a Creative Tab
Besides registering your own tab and adding items, you can also modify an existing tab directly.
```js
// The first argument is the creative tab ID (KubeJS tab ID is kubejs:tab)
StartupEvents.modifyCreativeTab("meng:items", (event) => {
	// code
});
```
### Methods Available for Tab Modification
|              Method              |        Description        |
| :------------------------------: | :----------------------: |
|     removeSearch(Ingredient)     | Remove matching items from search |
|        setIcon(ItemStack)        | Set the creative tab icon |
| addBefore(ItemStack,ItemStack[]) | Add before a specific item |
|    removeDisplay(Ingredient)     | - |
| addAfter(ItemStack,ItemStack[])  | Add after a specific item |
|    setDisplayName(Component)     | Set the creative tab display name |
|          add(ItemStack)          | Add items into this creative tab |
|        remove(Ingredient)        | Remove items from the creative tab |
