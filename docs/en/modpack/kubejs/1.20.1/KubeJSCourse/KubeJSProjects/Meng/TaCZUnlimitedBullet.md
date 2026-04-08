# TaCZ Unlimited Ammo
Topics covered: key registration and usage, Forge events, `loadClass`, `paint`, and player data.
Mods and versions used:
1. rhino-forge-2001.2.3-build.6
2. tacz-1.20.1-1.0.3-all
3. architectury-9.2.14-forge
4. kubejs-forge-2001.6.5-build.16
5. probejs-6.0.1-forge

## Full Code (`startup_scripts`)
### Core Logic
```js
const $GunShootEvent = Java.loadClass("com.tacz.guns.api.event.common.GunShootEvent")
const $Integer = Java.loadClass("java.lang.Integer")
const $ModSyncedEntityData = Java.loadClass("com.tacz.guns.entity.sync.ModSyncedEntityData")
const $AbstractGunItem = Java.loadClass("com.tacz.guns.api.item.gun.AbstractGunItem")

ForgeEvents.onEvent($GunShootEvent,event=>{global.gse(event)})
global.gse = event =>{
    try{
        // Handle event only on server side
        if(event.logicalSide.isClient()) return;
        let item = event.getGunItemStack();
        // Check player data to determine whether unlimited-ammo mode is enabled
        if(event.getShooter().data.get("frenzyOpen"))
            item.nbt.merge({GunCurrentAmmoCount:$Integer.decode​(
                item.nbt.getInt("GunCurrentAmmoCount")+1+""
            )});
    }catch(err){
        console.warn(err);
    }
}
```
This code reads gun item NBT and prevents ammo consumption.

### Register Keybinding
```js
const $KeyMappingRegistry = Java.loadClass("dev.architectury.registry.client.keymappings.KeyMappingRegistry");
const $KeyMapping = Java.loadClass("net.minecraft.client.KeyMapping");
const $GLFWkey = Java.loadClass("org.lwjgl.glfw.GLFW");

ClientEvents.init(event=>{
    global.regKeyFrenzy = new $KeyMapping(
        "key.meng.frenzy",
        $GLFWkey.GLFW_KEY_GRAVE_ACCENT,
        "key.keybinding.meng.special_abilities"
    );
    $KeyMappingRegistry.register(global.regKeyFrenzy)
})
```
[About key registration](./RegKey.md)

### Key Translation and Handling (`client_scripts`)
```js
ClientEvents.lang("en_us",e=>{
    e.add("key.meng.frenzy","Frenzy Mode")
    e.add("key.keybinding.meng.special_abilities","Gun Skills")
})

ClientEvents.tick(event => {
    const key = global.regKeyFrenzy;
    if (key.isDown()) {
        if (!event.player.getPersistentData().getBoolean("frenzy")) {
            event.player.sendData("frenzy")
            event.player.getPersistentData().putBoolean("frenzy", true);
        }
    } else {
        if (event.player.getPersistentData().getBoolean("frenzy")) {
            event.player.getPersistentData().putBoolean("frenzy", false);
        }
    }
})
```

## Full Code (`server_scripts`)
```js
// Progress bar coordinates
const x = "$screenW/1.4";
const textX = "$screenW/1.288"
const y = "$screenH/1.05";
const textY = "$screenH/1.083";
// Progress bar height
const h = 10;
// Skill cooldown
const cd = 20*8;
// Skill multiplier
const cdMultiples = 2;
// Skill charge cooldown
const chargeCd = cd * cdMultiples;

PlayerEvents.tick(e=>{
    let cdn = 0;
    let player = e.player;
    let open = player.data.get("frenzyOpen");
    let n = player.data.get("frenzyCount");
    if (n <= chargeCd && !open){
        n++;
        cdn = (n / chargeCd) * 100;
    }else if(open && n > 0){
        n--;
        cdn = (n / cd) * 100;
    }else if(n <= 0){
        player.data.put("frenzyOpen",false)
    }

    if (cdn == 0 && n > 0){
        cdn = 100;
    }
    
    e.player.paint({
        // Background color
        back_show: {
            type: 'rectangle',
            x: x, y: y, w: 100, h: h,
            color: '#f20c00'
        },
        // Foreground color
        top_show: {
            type: 'rectangle',
            x: x, y: y, w: cdn, h: h,
            color: '#7ef218'
        },
        // Text
        text_show: {
            type: 'text',
            text: "Frenzy Mode",
            x: textX, y: textY,
            
        }
    })

    player.data.put("frenzyCount",n);
})

// Handle key input
NetworkEvents.dataReceived("frenzy",event=>{
    let open = event.player.data.get("frenzyOpen")
    let n = event.player.data.get("frenzyCount");
    if (open){
        if (n > 0){
            n*= cdMultiples;
        }
    }else{
        n /= cdMultiples;
    }
    event.player.data.put("frenzyCount",n);
    event.player.data.add("frenzyOpen",!open)
})

// Save cooldown data to persistent player data on logout
PlayerEvents.loggedOut(event=>{
    event.player.persistentData.putInt("frenzyCount",event.player.data.get("frenzyCount"));
})

// Load cooldown data from persistent player data on login
PlayerEvents.loggedIn(event=>{
    event.player.data.add("frenzyCount",event.player.persistentData.getInt("frenzyCount"));
})
```

## Notes
1. This project is only an example; many parts are not necessarily optimal and can be improved.
2. If you improve this project, you can upload your revised code to the [Gitee repository](https://gitee.com/gumengmengs/kubejs-course).
3. Do not include keybinding registration in server deployment (see the key registration notes).
