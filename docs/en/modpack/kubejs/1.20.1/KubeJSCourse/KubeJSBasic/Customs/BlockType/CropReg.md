---
authors: ['Gu-meng']
---
# Crop Registration
In KubeJS, you can register crops directly. Many methods are available.
Below is a method list and an example.

## Available Methods
|                     Method Name                      | Args  |                       Purpose                        |     Return Type      |
| :--------------------------------------------------: | :---: | :--------------------------------------------------: | :------------------: |
|                     age(number)                      |  ->   | Set crop growth stages                               |         this         |
| age(number,Consumer\<CropBlockBuilder$ShapeBuilder\>) |  ->   | Set collision shape for each growth stage            |         this         |
|                      crop(any)                       |   ?   | Add crop drop with 100% chance                       |         this         |
|                  crop(any,number)                    |  ->   | Add crop drop with custom chance                     |         this         |
|    bonemeal(ToIntFunction\<RandomTickCallbackJS\>)    |   ?   | Bonemeal growth callback                             |         this         |
|   growTick(ToDoubleFunction\<RandomTickCallbackJS\>)   |  ->   | Random-tick growth callback                          |         this         |
|      survive(CropBlockBuilder$SurviveCallback_)      |  ->   | Growth/survival condition callback                   |         this         |
|                    dropSeed(bool)                    |  ->   | Whether harvested crop drops seeds                   |         this         |
|                translationKey(string)                |  ->   | Set translation key                                  | BuilderBase\<Block\> |
|               formattedDisplayName()                 |   -   | Make displayName override lang file                  | BuilderBase\<Block\> |
|            formattedDisplayName(Component)           |   -   | Set direct text to override lang file                | BuilderBase\<Block\> |
|       dynamicMapColor(Function\<BlockState,any\>)      |   ~   | Set map color behavior per block state               |     BlockBuilder     |
|                  toString(string)                    |  ->   | String conversion (rarely needed)                    |        string        |
|                    createObject()                    |   -   | ?                                                    |          ?           |
|               createAdditionalObjects()              |   -   | Create additional objects                            |         void         |
|                     notifyAll()                      |   -   | ?                                                    |         void         |
|                     wait(number)                     |   -   | ?                                                    |         void         |
|               getTranslationKeyGroup()               |   -   | ~                                                    |         void         |
|              getBuilderTranslationKey()              |   -   | ~                                                    |         void         |
|          【static】 createShape(List\<AABB\>)          |   ~   | ?                                                    |      VoxelShape      |
|                         get()                        |   -   | ?                                                    |         Block        |
|                      getClass()                      |   -   | Get class                                            |      typeof any      |
|                 setWaterlogged(bool)                 |  ->   | **Deprecated**                                       |          -           |
|                  getWaterlogged()                    |   -   | **Deprecated**                                       |          -           |

## Example
```js
StartupEvents.registry("block", event => {
    event.create("test_crop", "crop")
        .age(3,cbb=>{
            cbb.shape(0,0,0,0,16,2,16)
            cbb.shape(1,0,0,0,16,8,16)
            cbb.shape(2,0,0,0,16,12,16)
            cbb.shape(3,0,0,0,16,16,16)
        })
        .growTick((blockstate, random) => {
            return 25;
        })
        .bonemeal(rtc => {
            return rtc.random.nextInt(2)
        })
        .crop(Item.of("stone"))
        .survive((blockstate, level, pos) => {
            // Check whether the area is loaded
            if (level.isAreaLoaded(pos,1)){
                // Check whether the crop can see the sky
                if(level.canSeeSky(pos)){
                    // Check sky light level
                    if (level.getBrightness("sky",pos) <= 8){
                        // Check block below
                        if (level.getBlockState(pos.below()).is(Blocks.NETHERITE_BLOCK)){
                            return true
                        }
                    }
                }else{
                    // Check block light level
                    if (level.getBrightness("block",pos) <= 8){
                        if (level.getBlockState(pos.below()).is(Blocks.NETHERITE_BLOCK)){
                            return true
                        }
                    }
                }
            }
            return false
        })
        .texture("0", "minecraft:block/wheat_stage0")
        .texture("1", "minecraft:block/wheat_stage3")
        .texture("2", "minecraft:block/wheat_stage5")
        .texture("3", "minecraft:block/wheat_stage7")
        .item(seed => seed.texture("minecraft:wheat_seeds"))
})
```
This example reuses vanilla wheat textures (including the item texture).

`age(3,cbb=>{})` mainly uses `shape` to define collision size per age.
For `cbb.shape(2,0,0,0,16,12,16)`:

`2` is the age stage.
The first `0,0,0` is min `x,y,z`.
`16,12,16` is max `x,y,z`.
This is crop-stage collision/display shape logic, which is different from regular block `box` setup.

`growTick` decides whether the crop grows when chosen by random tick.
The formula is `random.nextInt((25 / returnValue) + 1)`.
If you return `25`, random values become `0` or `1`; under vanilla logic, growth only succeeds on the `0` case.

`bonemeal` handles bonemeal growth.
Return value is how many growth stages to increase.
Returning `1` increases age by 1; returning `0` does nothing.
This example uses `rtc.random.nextInt(2)` to return either `0` or `1`.

`crop` sets crop drops, similar to how wheat drops seeds and wheat.

`survive` handles environment rules:
* which block can support planting
* required brightness
* whether it can survive in different dimensions/conditions
* return `true` to allow survival
* return `false` to disallow survival (crop breaks if unsupported)
* this callback might not run every `growTick` (possibly a behavior quirk)
* it is reliably checked when neighboring block updates notify this crop
* if check fails, crop breaks
* similar to vanilla behavior where crops can break when light conditions are invalid

`texture` sets textures for each age stage; this example uses vanilla textures.
