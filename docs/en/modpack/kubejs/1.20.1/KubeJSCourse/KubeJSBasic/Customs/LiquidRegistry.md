---
authors: ['Gu-meng']
---
# Register Fluids
Use the following code in KubeJS to register a fluid:
```js
StartupEvents.registry("fluid",event =>{
    event.create("meng:my_fluid")
})
```
When creating a fluid with KubeJS, a fluid bucket is registered by default.

After creating a fluid, you can set the following properties.
## Property Methods
|            Method Name           | Parameter  |                    Parameter Description                    |                     Usage                    |         Return Type         | Tested |
| :------------------------------: | :--------: | :----------------------------------------------------------: | :------------------------------------------: | :-------------------------: | :----------: |
|            gaseous()             |     -      |                              -                               | Set fluid to gaseous                          |            this             |     ???      |
|        rarity(arityType)         |   Rarity   |                              -                               | Set [fluid rarity](../../Digression/Rarity.md)|            this             |     ???      |
|        getRegistryType()         |     -      |                              -                               | Get registry type                             |        RegistryInfo         |      -       |
|           color(Color)           |    Color   | Use color string or hex code (`0Xffffff` = white)           | Set fluid color                               |            this             |      -       |
|          createObject()          |     -      |                              -                               | ?                                             |            this             |     ???      |
|           density(int)           |   Integer  |                              -                               | Set fluid thickness/density                   |            this             |     Failed   |
|          translucent()           |     -      |                              -                               | Set fluid translucent                         |            this             |     Failed   |
|         luminosity(int)          |   Integer  |                           Brightness                         | Set fluid luminosity                          |            this             |     Failed   |
|        bucketColor(Color)        |    Color   |                          Same as above                       | Set bucket color                              |            this             |     Success  |
|        builtinTextures()         |     -      |                              -                               | ?                                             |            this             |     ???      |
|        createAttributes()        |     -      |                              -                               | Create attributes ???                         | ArchitecturyFluidAttributes |     ???      |
|            noBucket()            |     -      |                              -                               | Do not generate a bucket                      |            this             |     Success  |
|            noBlock()             |     -      |                              -                               | Do not generate a fluid block                 |            this             |     Success  |
|        thinTexture(Color)        |    Color   |                          Same as above                       | Set global color for bucket+fluid (water type)|           this             |     Success  |
|       thickTexture(Color)        |    Color   |                          Same as above                       | Set global color for bucket+fluid (lava type) |           this             |     Success  |
|  stillTexture(ResourceLocation)  | Texture id |                              -                               | Set still fluid texture                       |            this             |      -       |
| flowingTexture(ResourceLocation) | Texture id |                              -                               | Set flowing fluid texture                     |            this             |      -       |
|          viscosity(int)          |   Integer  |                              -                               | Set fluid viscosity (similar to lava)         |            this             |     Failed   |
|         renderType(str)          |   String   |                              ?                               | ?                                             |            this             |     ???      |
|    createAdditionalObjects()     |     -      |                              -                               | ?                                             |            this             |     ???      |
|         temperature(int)         |   Integer  |                              -                               | Set fluid temperature                         |            this             |     Failed   |
|         displayName(str)         |   String   |                              -                               | Display name without lang file                |            Success          |
|           flowingFluid           |     -      |                       Direct-access field                    | ?                                             |     FlowingFluidBuilder     |      -       |
|            attributes            |     -      |                       Direct-access field                    | ?                                             | ArchitecturyFluidAttributes |      -       |
|              block               |     -      |                       Direct-access field                    | Fluid block                                   |      FluidBlockBuilder      |      -       |
|            bucketItem            |     -      |                       Direct-access field                    | Fluid bucket item                             |   FluidBucketItemBuilder    |      -       |

These properties may not be complete. Refer to ProbeJS exports for the full list.

# Registration Examples
```js
StartupEvents.registry("fluid", (event) =>{
    // Set lava-style texture color to pink
    event.create("meng:my_fluid")
		.thickTexture(0Xff82e0)
    // Set water-style texture color to pink
    event.create("meng:my_fluid2")
		.thinTexture(0Xff82e0)
    // Set fluid color to green and do not generate a fluid block
    event.create("meng:my_fluid4")
		.thickTexture("GREEN")
		.noBlock()
    // Set fluid color to blue and do not generate a bucket
    event.create("meng:my_fluid5")
		.thinTexture("BLUE")
		.noBucket()
    // Set fluid to pink but bucket color to yellow
    event.create("meng:my_fluid6")
		.thickTexture(0Xff82e0)
		.bucketColor("yellow")
})
```
## Lang File
```json
{
    "fluid.meng.my_fluid": "GuMeng's Fluid",
    "item.meng.my_fluid_bucket": "GuMeng's Fluid Bucket"
}
```

### Simple Fluid Registration Helper

**Note: The following is optional and can be adjusted to your own style.**

```js
StartupEvents.registry("fluid", (event) => {
	// ModID declaration. If you keep the default ("kubejs"), remove this variable.
	const MODID = "meng:"
	// Path constant
	const PATH = "block/fluid/"
	
	/* 
	* Define fluids
	* Add a comma after [] when adding the next fluid
	* Keep the exact format:
	* [fluid_id, color]
	*/
	let fluidRegisters = [
		["example_fluid", 0x808080],
	]
	fluidRegisters.forEach(([name, color]) => {
		event.create(MODID + name) // Declare id
			.thickTexture(color) // Fluid color
			.bucketColor(color) // Color of fluid inside bucket
			.flowingTexture(MODID + PATH + "flowing") // Use local fluid texture (.mcmeta is also required)
			.stillTexture(MODID + PATH + "still") // Use local fluid texture (.mcmeta is also required)
			.tag(MODID + "fluid") // Add fluid tag (optional)
	})
})
```
