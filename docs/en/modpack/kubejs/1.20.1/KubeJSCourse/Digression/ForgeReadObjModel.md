---
authors: ['Gu-meng']
---
# `obj` Model
* Compared with vanilla `Minecraft` JSON models, `obj` models are much more flexible for authoring. For example, [**Immersive Engineering**](https://www.mcmod.cn/class/463.html) uses them for blocks like the [**Arc Furnace**](https://www.mcmod.cn/item/36383.html) and [**Bucket-Wheel Excavator**](https://www.mcmod.cn/item/36550.html).
  * ### Build an `obj` Model
    * First create an `obj` model. You can use many tools, such as [**`3DS Max`**](https://www.autodesk.com.cn/products/3ds-max), [**`Blender`**](https://www.blender.org/), and [**`BlockBench`**](https://www.blockbench.net).
    Example:
	  ```
	  # Made in Blockbench 4.8.3
	  mtllib qqqqoo.mtl

	  o pyramid
	  v 1 -2.220446049250313e-16 1
	  v 1 0.9999999999999998 1.0000000000000002
	  v 0 0.9999999999999998 1.0000000000000002
	  v 0 -2.220446049250313e-16 1
	  v 0.5 0.4999999999999999 0.5000000000000001
	  vt 0.25 0.75
	  vt 0.25 1
	  vt 0 1
	  vt 0 0.75
	  vt 0 0.479471875
	  vt 0.25 0.479471875
	  vt 0.125 0.65625
	  vt 0.1875 0.620096875
	  vt 0.4375 0.620096875
	  vt 0.3125 0.796875
	  vt 0.265625 0.823221875
	  vt 0.515625 0.823221875
	  vt 0.390625 1
	  vt 0.375 0.479471875
	  vt 0.625 0.479471875
	  vt 0.5 0.65625
	  vn 0 -2.220446049250313e-16 1
	  vn 0.7071067811865476 1.5700924586837752e-16 -0.7071067811865476
	  vn 0 0.7071067811865477 -0.7071067811865475
	  vn 0 -0.7071067811865475 -0.7071067811865477
	  vn -0.7071067811865476 1.5700924586837752e-16 -0.7071067811865476
	  usemtl m_c5b90dae-3a75-90c1-2280-8a6b9dc0fba2
	  f 1/1/1 2/2/1 3/3/1 4/4/1
	  f 2/5/2 1/6/2 5/7/2
	  f 3/8/3 2/9/3 5/10/3
	  f 1/11/4 4/12/4 5/13/4
	  f 4/14/5 3/15/5 5/16/5
	  ```
	  Exporting also generates an `mtl` file and a `png` texture file:
	  ```
	  # Made in Blockbench 4.8.3
	  newmtl m_c5b90dae-3a75-90c1-2280-8a6b9dc0fba2
	  map_Kd kubejs:block/qqqqoo
	  newmtl none
	  ```

	  Make sure `usemtl m_c5b90dae-3a75-90c1-2280-8a6b9dc0fba2` in `obj` matches `newmtl m_c5b90dae-3a75-90c1-2280-8a6b9dc0fba2` in `mtl`.
	  `mtllib qqqqoo.mtl` in `obj` points to the mtl file; keeping them at the same level is fine by default.\
	  `map_Kd` in `mtl` specifies the texture path.\
	  Like vanilla models, place textures in `assets/${modid}/textures/block` (this tutorial uses a block example, so it is under `block`; for items, use `item`).
  * ### Point to the `obj` File
    * Vanilla `Minecraft` cannot load `obj` files directly, so you need a `json` model file that points to the `obj` file.
      ```json
	  {
	      "loader": "forge:obj",
	      "model": "kubejs:models/block/qqqqoo.obj",
	      "flip_v": true,
	      "textures": {
		      "particle": "kubejs:block/qqqqoo"
	      }
      }
	  ```
      `loader` specifies the model loader type; here it is set to `obj`.\
	  `model` specifies the path to the `obj` model.\
	  `flip_v` controls texture flipping. Since `obj` UVs are vertically inverted relative to MC, set it to `true`.\
	  `textures` specifies textures to load; `particle` is used here for particle texture.

  * ### Define `blockstate`
    * When loading an `obj` model, you must provide a `blockstate`. It is not auto-generated for `obj`, so without it the block cannot resolve its model.
      ```json
	  {
	      "variants": {
	  	      "facing=down": {
	  	   	      "model": "kubejs:block/qqqqoo",
	  		      "x": 90
	  	      },
	  	      "facing=east": {
	  		      "model": "kubejs:block/qqqqoo",
	  		      "y": 90
	  	      },
	  	      "facing=north": {
	  		      "model": "kubejs:block/qqqqoo"
	  	      },
	  	      "facing=south": {
	  	          "model": "kubejs:block/qqqqoo",
	  		      "y": 180
	  	      },
	  	      "facing=up": {
	  		      "model": "kubejs:block/qqqqoo",
	  		      "x": 270
	  	      },
	  	      "facing=west": {
	  		      "model": "kubejs:block/qqqqoo",
	  		      "y": 270
	  	      }
	      }
      }
	  ```
	* ### Check Paths
      Place the `mtl`, `obj`, and the `json` model that points to the `obj` in `assets/${modid}/models/block`.\
	  Place the `png` texture in `assets/${modid}/textures/block`.\
	  The `blockstate` `json` file should be in `assets/${modid}/blockstate`.\
	  These files should be named after the block registry name.\
	  If the exported model appears offset in-game, adjust it in the modeling tool.

	# References
	[[Minecraft 1.20.4 NeoForge Modding Tutorial] 18 Load OBJ Models](https://www.bilibili.com/video/BV1jm421J7UR)\
	[[Boson 1.16 Modding Tutorial] - Obj](https://boson.v2mcdev.com/specialmodel/obj.html)
