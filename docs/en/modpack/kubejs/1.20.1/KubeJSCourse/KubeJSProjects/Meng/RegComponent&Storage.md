---
authors: ['Gu-meng']
---
# Register AE Storage Components and Their Cells
Topics covered: using `Java.loadClass` and `createCustom` in `StartupEvents.registry`.

Mods and versions used:
1. jei-1.20.1-forge-15.3.0.4
2. rhino-forge-2001.2.2-build.18
3. architectury-9.2.14-forge
4. kubejs-forge-2001.6.5-build.14
5. probejs-6.0.1-forge
6. appliedenergistics2-forge-15.2.13

## Code
All code below belongs in the `startup` folder.

```js
const $BasicStorageCell = Java.loadClass("appeng.items.storage.BasicStorageCell")
const $StorageComponentItem = Java.loadClass("appeng.items.materials.StorageComponentItem")
const $AEItems = Java.loadClass("appeng.core.definitions.AEItems")
const $AEKeyType = Java.loadClass("appeng.api.stacks.AEKeyType")
const $Item = Java.loadClass("net.minecraft.world.item.Item");

const namespace = "meng:";

let AECellComponentItems = {
}

/**
 * Register storage components and matching storage cells.
 * @param {Number} byte max capacity in KB
 * @param {Number} maxItemTypeCount max item type count, 1~63
 * @param {Number} AE2Energy AE energy use
 * @param {Number} bytesPer bytes used per new item type
 */
function regCellComponent(byte,maxItemTypeCount,AE2Energy,bytesPer){
    let cellComponentId = `${namespace}cell_component_${byte}k`
    let storageCellId = `${namespace}item_storage_cell_${byte}k`
    AECellComponentItems[byte] = {
        byte:byte,
        cellComponent: cellComponentId,
        storageCell:storageCellId,
        maxItemTypeCount:maxItemTypeCount,
        AE2Energy:AE2Energy,
        bytesPer:bytesPer
    }
}

regCellComponent(512,63,3.0,4096)
regCellComponent(1024,63,4.0,4096)
regCellComponent(2048,63,5.0,4096)
regCellComponent(4096,63,6.0,4096)
regCellComponent(8192,63,7.0,4096)

StartupEvents.registry("item", event => {
    for (const key in AECellComponentItems) {
        let aeValue = AECellComponentItems[key];
        event.createCustom(aeValue.cellComponent,
            ()=>new $StorageComponentItem($Item.Properties().stacksTo(1), aeValue.byte))
        event.createCustom(aeValue.storageCell,
            ()=>new $BasicStorageCell(
                $Item.Properties().stacksTo(1), 
                Item.of(aeValue.cellComponent), 
                $AEItems.ITEM_CELL_HOUSING, 
                aeValue.AE2Energy, 
                aeValue.byte, 
                aeValue.bytesPer, 
                aeValue.maxItemTypeCount, 
                $AEKeyType.items()
            )
        )
    }
})
```
The code above registers storage components and matching cells from 512k to 8192k.

In game, you can also use `Shift + Right Click` like vanilla to separate the component from its housing.

Localization and textures follow the normal item registration workflow.

## How to Use the Code Above
The helper is already prepared for you. Call `regCellComponent` with your parameters, then restart the game.
```js
regCellComponent(512,63,3.0,4096)
```
Just call it like shown above, restart the game, and it will load.

## Notes
1. This project is only an example. Some parts may not be explained in detail, so check related references when needed.
2. If you use this code, please add a comment with a link to this document.
