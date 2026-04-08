---
authors: ['Gu-meng', 'LitterWolf-fufu']
---
# Enchant Items on the Crafting Table

```js
ServerEvents.recipes((event) => {
    // Allow iron swords to be enchanted on the crafting table
    enchantCrafting('iron_sword', event)
})
/**  
 * Register a recipe that applies enchanted-book enchantments to an item.
 * @param {$ItemStack_} enchanted_item - ItemStack of the item to enchant
 * @param {$RecipesEventJS_} event - Recipe event
 */  
const enchantCrafting = (enchanted_item, event) => { 
    event.shapeless(enchanted_item, [enchanted_item, 'minecraft:enchanted_book'])  
        .modifyResult((/**@type {$ModifyRecipeCraftingGrid_}*/grid, /**@type {$ItemStack_} */item) => {
            item = grid.find(enchanted_item)    

            let addition = grid.find('minecraft:enchanted_book').enchantments
            if (addition.isEmpty()) { // If the book has no enchantments, return the original item
                return item.withCount(1)
            }

            let origin = item.enchantments
            if (origin.isEmpty()) {  // If the original item has no enchantments, apply all from the book
                return item.enchant(addition).withCount(1)
            }

            // Neither side is empty
            let combinedOrgin = origin   // Copy original item enchantments as the base
            item.enchantments.clear()
            addition.forEach((id, lvl) => { // Iterate over enchanted-book enchantments
                // Check whether both have the same enchantment ID
                if (origin.containsKey(id)) {
                    if (lvl > origin.get(id)) {
                        // If the book level is higher, replace the original level
                        combinedOrgin.replace(id, lvl)
                    }
                } else {
                    // If the item does not have this enchantment, merge it in
                    combinedOrgin.put(id, lvl)
                }
            })
            item.nbt.remove('Enchantments')
            return item.enchant(combinedOrgin).withCount(1)
        })
}
```
