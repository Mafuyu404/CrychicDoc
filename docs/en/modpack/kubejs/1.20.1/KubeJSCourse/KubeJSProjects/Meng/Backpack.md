# Register a Backpack
Topics covered: item registration, NBT operations, Curios handling, and keybinding registration.
Mods and versions used:
1. rhino-forge-2001.2.3-build.6
2. architectury-9.2.14-forge
3. kubejs-forge-2001.6.5-build.16
4. probejs-6.0.1-forge
5. curios-forge-5.10.0+1.20.1

## `startup_scripts` Code
### Register Item
```js
event.create("meng:backpack")
    .maxStackSize(1)
    .tag("curios:back"); // This line adds the Curios slot tag; remove it if not needed
```
### Register Keybinding
```js
ClientEvents.init(() => {
  global.regKeyB = new $KeyMapping(
    "key.meng.packsack",
    $GLFWkey.GLFW_KEY_B,
    "key.keybinding.packsack"
  );
  $KeyMappingRegistry.register(global.regKeyB);
}); 
```

### Key Handling in `client_scripts`
```js
ClientEvents.tick(event => {
    const key = global.regKeyB;
    if (key.isDown()) {
        if (!event.player.getPersistentData().getBoolean("openBackpack")) {
            event.player.sendData("openBackpack")
            event.player.getPersistentData().putBoolean("openBackpack", true);
        }
    } else {
        if (event.player.getPersistentData().getBoolean("openBackpack")) {
            event.player.getPersistentData().putBoolean("openBackpack", false);
        }
    }
})
```

## `server_scripts` Code
### Methods for Opening and Closing the Backpack
```js
// priority: 5

const backpack = "meng:backpack";
const dataBackpack = "backpack";
const dataBackpackItem = dataBackpack + "Item";
/**
 * On backpack close, write backpack inventory items into backpack NBT.
 * @param {*} inventoryContainer 
 * @param {Internal.ItemStack} backpackItem 
 */
function backpackFunc(inventoryContainer,backpackItem) {
    let n = 0;
    if (inventoryContainer.getItems().size() == 90) n = 54;
    let list = [];
    for (let i = 0; i < n; i++) {
        let item = inventoryContainer.getSlot(i).getItem();
        if (item.is("air")) continue;
        list.push({ item: item.id, count: item.count, slot: i, nbt: item.nbt })
    }
    backpackItem.nbt.merge({ "items": list })
}
/**
 * Open-backpack function
 *
 */
function openBackpackFunc(player, item) {
    if (!item.hasNBT()) item.setNbt({ items: [] })
    let nbt = item.getNbt();
    let itemList = nbt.get("items");
    if (itemList == undefined) item.nbt.merge({ items: [] });

    player.openMenu(new $SimpleMenuProvider((i, inv, p) => {
        let chest = $ChestMenu.sixRows(i, inv)
        for (let i = 0; i < itemList.size(); i++) {
            let value = itemList.get(i)
            chest.getSlot(value.slot).set(Item.of(value.item, value.count, value.nbt));
        }
        player.data.add(dataBackpack, chest.hashCode().toString())
        return chest
    }, Text.of(item.displayName).yellow()))
}
```
### Open Backpack Logic
```js
ItemEvents.firstRightClicked(backpack, event => {
    let { player, item } = event
    openBackpackFunc(player,item);
})


NetworkEvents.dataReceived("openBackpack", event => {
    const player = event.player
    if (player.data.get(dataBackpack) == undefined) {
        let opItem = $CuriosApi
            .getCuriosHelper()
        ["findFirstCurio(net.minecraft.world.entity.LivingEntity,net.minecraft.world.item.Item)"](player, backpack);
        try {
            let item = opItem.get().stack();
            openBackpackFunc(player,item);
        } catch (err) {
            console.warn(err);
            player.tell(Text.translate("tell.meng.openBackpack.curiosapi"))
        }
    }
})
```
### Close Backpack Logic
```js
PlayerEvents.chestClosed(event => {
    let { player, inventoryContainer } = event
    if (player.data.get(dataBackpack) == inventoryContainer.hashCode().toString()) {
        let handItem = player.getMainHandItem();
        player.data.remove(dataBackpack)
        // player.data.remove(dataBackpackItem)
        if (handItem.is(backpack)) {
            backpackFunc(inventoryContainer, handItem)
            return;
        }
        let opItem = $CuriosApi
            .getCuriosHelper()
            ["findFirstCurio(net.minecraft.world.entity.LivingEntity,net.minecraft.world.item.Item)"]
                (player, backpack);
        let curiosItem = opItem.get().stack();
        if (curiosItem.is(backpack)) {
            backpackFunc(inventoryContainer, curiosItem)
            return;
        }
    }
})
```

## Notes
1. This project is only an example; many parts are not necessarily optimal and can be improved.
2. If you improve this project, you can upload your revised code to the [Gitee repository](https://gitee.com/gumengmengs/kubejs-course).
3. Do not include client keybinding registration in server deployment (see the keybinding comments).
