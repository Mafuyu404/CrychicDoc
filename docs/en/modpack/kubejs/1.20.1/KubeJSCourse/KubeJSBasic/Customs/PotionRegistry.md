---
authors: ['Gu-meng']
---
# Register Potion Effects
If vanilla potion effects do not meet your needs, you can create custom effects. The scripts in this chapter are created under `startup_scripts`.
```js
StartupEvents.registry("mob_effect",event=>{
    event.create("meng:my_mob_effect")
})
```
The code above only registers the effect id. To make it useful, add effect properties with methods like these:
## Common Methods
|                               Method                                |      Purpose      |            Notes           |
| :-----------------------------------------------------------------: | :--------------: | :------------------------: |
|                            `harmful() `                             | Negative effect  | Display category            |
|                           `beneficial()`                           | Positive effect  | Display category            |
|                  `effectTick(EffectTickCallback)`                   | Tick behavior    | Main logic for custom effect|
|                           `color(Color)`                            | Particle color   | -                          |
|                    `category(MobEffectCategory)`                    | Effect category  | -                          |
| `modifyAttribute(ResourceLocation,string,double,AttributeModifier)` | Modify attributes| -                          |
