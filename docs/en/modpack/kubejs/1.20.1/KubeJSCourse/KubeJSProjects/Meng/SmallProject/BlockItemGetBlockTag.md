---
authors: ['Gu-meng']
---
# Get Block Tags by Right-Clicking an Item
Main topics in this chapter: item right-click events and block items. All code in this chapter belongs in `server_scripts`.

Mods and versions used:
1. jei-1.20.1-forge-15.3.0.4
2. rhino-forge-2001.2.2-build.18
3. architectury-9.2.14-forge
4. kubejs-forge-2001.6.5-build.14
5. probejs-6.0.1-forge

## Code
```js
ItemEvents.firstRightClicked(event=>{
    if (event.getItem().isBlock()){
        /**
         * @type {Internal.BlockItem}
         */
        let item = event.getItem().item
        let tagList = item.getBlock().defaultBlockState().getTags().toList();
        for (const key of tagList) {
            // This logs the content to /logs/kubejs/server.log
            console.log(key.location());
        }
    }
})
```
When the player right-clicks an item, the script checks whether it is a block item. If it is, the tags are printed.
