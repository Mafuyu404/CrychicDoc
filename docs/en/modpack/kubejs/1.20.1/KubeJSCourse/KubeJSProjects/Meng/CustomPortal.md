---
authors: ['Gu-meng']
---
# Custom Portals
This project depends on [Custom Portal API [Forge]](https://www.mcmod.cn/class/10354.html), so install that mod before use.

Mods and versions used:
1. rhino-forge-2001.2.2-build.18
2. architectury-9.2.14-forge
3. kubejs-forge-2001.6.5-build.14
4. probejs-6.0.1-forge
5. customportalapi-0.0.1-forge-1.20

## Project Code
This example code should be placed in the `startup_scripts` folder.

```js
const $CustomPortalBuilder = Java.loadClass("net.kyrptonaught.customportalapi.api.CustomPortalBuilder")
const $BuiltinDimensionTypes = Java.loadClass("net.minecraft.world.level.dimension.BuiltinDimensionTypes")

StartupEvents.postInit(e=>{
    $CustomPortalBuilder
        .beginPortal() // Begin building a custom portal
        ["frameBlock(net.minecraft.world.level.block.Block)"](Blocks.STONE) // Portal frame block
        .destDimID($BuiltinDimensionTypes.NETHER_EFFECTS) // Destination dimension
        .tintColor(131, 66, 184) // Portal RGB color
        .registerPortal(); // Register custom portal

    $CustomPortalBuilder
        .beginPortal() // Begin building a custom portal
        ["frameBlock(net.minecraft.world.level.block.Block)"](Blocks.DIAMOND_BLOCK) // Portal frame block
        .destDimID($BuiltinDimensionTypes.NETHER_EFFECTS) // Destination dimension
        .lightWithItem(Items.DIAMOND) // Item used to ignite portal
        .flatPortal() // Use flat portal style
        .tintColor(131, 133, 184) // Portal RGB color
        .registerPortal(); // Register custom portal
})
```

## `CustomPortalBuilder` Callable Methods
Open-source reference: [https://github.com/kyrptonaught/customportalapi/blob/1.20/src/main/java/net/kyrptonaught/customportalapi/api/CustomPortalBuilder.java](https://github.com/kyrptonaught/customportalapi/blob/1.20/src/main/java/net/kyrptonaught/customportalapi/api/CustomPortalBuilder.java)
|                               Method Name                                |     Parameters      |                        Description                        |
| :-----------------------------------------------------------------------: | :-----------------: | :------------------------------------------------------: |
|                               beginPortal()                               |          -          |                     Create a new portal                  |
|                             registerPortal()                              |          -          |      Register the portal after all properties are set    |
|                       frameBlock(ResourceLocation)                        |       Block ID      |                  Set portal frame block                  |
|                             frameBlock(Block)                             |      Block type     |                  Set portal frame block                  |
|                        destDimID(ResourceLocation)                        |    Dimension ID     |                  Set destination dimension               |
|                              tintColor(int)                               |       Hex color     |                     Set portal color                     |
|                       tintColor(int r,int g,int b)                        |       RGB ints      |                     Set portal color                     |
|                             lightWithWater()                              |          -          |                 Make portal ignite by water             |
|                            lightWithItem(Item)                            |      Item type      |                 Make portal ignite by item              |
|                           lightWithFluid(Fluid)                           |      Fluid type     |                Make portal ignite by fluid              |
|                  customIgnitionSource(ResourceLocation)                   |          ~          |                            a1                            |
|                customIgnitionSource(PortalIgnitionSource)                 |          ~          |                            a1                            |
|                          forcedSize(int w,int h)                          |    w width, h height|                 Force portal dimensions                  |
|                   customPortalBlock(CustomPortalBlock)                    | CustomPortalBlock   |                  Use custom portal block                |
|                    returnDim(ResourceLocation,boolean)                    |   dimension ID, b1  |                  Set return dimension                   |
|                          onlyLightInOverworld()                           |          -          |                            a2                            |
|                               flatPortal()                                |          -          |     Use flat portal style (like End portal visuals)     |
|                    customFrameTester(ResourceLocation)                    |          ~          |                            a3                            |
|            registerBeforeTPEvent(Function\<Entity,SHOULDTP\>)             |          ~          | Register a pre-teleport event (can cancel via SHOULDTP) |
| registerInPortalAmbienceSound(Function\<PlayerEntity,CPASoundEventData\>) |          ~          |          Register sound while player is in portal        |
| registerPostTPPortalAmbience(Function\<PlayerEntity,CPASoundEventData\>)  |          ~          |          Register sound during/after teleport            |
|                  registerPostTPEvent(Consumer\<Entity\>)                  |          ~          |               Register post-teleport event               |

### Fabric-Only (Likely)
Based on callable API differences, the following methods appear to be available only in the [Fabric version](https://www.mcmod.cn/class/15391.html).
|                     Method Name                  |  Parameters  |                           Description                           |
| :-----------------------------------------------: | :----------: | :-------------------------------------------------------------: |
|              beginPortal(PortalLink)              |  PortalLink  |        Create a new portal using a custom `PortalLink`         |
|              registerPortalForced()               |      -       |                    Force-register a portal                      |
|   registerPreIgniteEvent(PortalPreIgniteEvent)    |      ~       | Pre-ignite event; activation fails if event returns `false`    |
|      registerIgniteEvent(PortalIgniteEvent)       |      ~       |                     Post-ignite event                           |
|    setPortalSearchYRange(int bottomY,int topY)    |      -       |      Set Y-range used for portal search/generation             |
| setReturnPortalSearchYRange(int bottomY,int topY) |      -       |      Set Y-range used for return portal search/generation      |

### Official Notes
a1: Specify a Custom Ignition Source to be used to ignite the portal. You must manually trigger the ignition yourself.

a2: Specify that this portal can only be ignited in the Overworld.Attempting to light it in other dimensions will fail.

a3: Specify a custom portal frame tester to be used.

b1: Should this portal only be ignitable in returnDimID.

## About Registering Portal Blocks
```js
const $CustomPortalsMod = Java.loadClass("net.kyrptonaught.customportalapi.CustomPortalsMod")
const $CustomPortalBlock = Java.loadClass("net.kyrptonaught.customportalapi.CustomPortalBlock")
const $BlockBehaviourProperties = Java.loadClass("net.minecraft.world.level.block.state.BlockBehaviour$Properties")
const $SoundType = Java.loadClass("net.minecraft.world.level.block.SoundType")
const $EventBuses = Java.loadClass("dev.architectury.platform.forge.EventBuses");

let test;

StartupEvents.init(e => {
    test = $CustomPortalsMod.registerOnlyBlock("test",()=>{
        return new $CustomPortalBlock(
            $BlockBehaviourProperties.copy(Blocks.NETHER_PORTAL)
            .lightLevel(() => 11)
            .noCollission()
            .strength(-1)
            .sound($SoundType.GLASS)
        )
    })

    $CustomPortalsMod.BLOCKS.register($EventBuses.getModEventBus("kubejs").get());
})

StartupEvents.postInit(e=>{
    $CustomPortalBuilder
        .beginPortal()
        ["frameBlock(net.minecraft.world.level.block.Block)"](Blocks.STONE)
        .customPortalBlock(test)
        .destDimID($BuiltinDimensionTypes.NETHER_EFFECTS) 
        .tintColor(131, 66, 184)
        .registerPortal();
})
```

## Notes
1. This project was mainly tested on `1.20.1` with [Custom Portal API [Forge]](https://www.mcmod.cn/class/10354.html).
2. If errors occur because your mod version differs from this example, adjust the code accordingly.
3. This project focuses on the core implementation pattern. For more features, check the open-source project and extend from there.
