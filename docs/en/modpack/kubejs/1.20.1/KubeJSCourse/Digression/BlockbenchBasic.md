---
authors: ['Gu-meng']
---
# Basic Blockbench Usage
## 1. About Blockbench

* [**Blockbench**](https://www.blockbench.net) is one of the most popular modeling tools in Minecraft modding. Most custom-looking blocks you see are made with Blockbench (though some advanced models use [`obj`](../Digression/ForgeReadObjModel) format), for example this smithing table:\
  ![smithing\_table_1.png](/imgs/Blockbench/smithing_table_1.png)![smithing\_table_2.png](/imgs/Blockbench/smithing_table_2.png)
* **File paths**
  * Model files are usually stored in `assets/${modid}/models`.
  * Like the `textures` folder, `models` is usually split into `block` and `item`.
  * Put block models in `block` and item models in `item`.
  * **Make sure the exported model file is in `json` format.**
  * Textures go in `assets/${modid}/textures`; similarly, blocks in `block`, items in `item`. If possible, keep models and textures under the same `${modid}`.

## 2. Usage

(This tutorial only covers the basics. For advanced workflows, check other guides.)

### **1. Create a Project**

* When selecting a model preset, choose the provided Java Edition type. It exports `json`, which is exactly what Minecraft needs.\
  ![menu.png](/imgs/Blockbench/menu.png)
* After selecting the type, a popup appears. Set the filename to your item/block ID and leave the rest as default.\
  ![project.png](/imgs/Blockbench/project.png)

### **2. Create Your First Model**

* After entering the workspace, you will see the UI below. This image highlights the most commonly used buttons (a cube here means a model element).\
  ![ui.png](/imgs/Blockbench/ui.png)
* Create any quick test model first.\
  ![model\_1.png](/imgs/Blockbench/model_1.png)
* Then save it to `assets/${modid}/models/block` as mentioned above.\
  ![model\_2.png](/imgs/Blockbench/save.png)\
  Write code...
```js
StartupEvents.registry("block", (event) => {
	// The block ID should match the model filename
    event.create("test_block")
})
```

### **3. Set Textures for the Model**

* If you register it in-game now, you will likely get the classic purple-black missing texture, and the in-hand/inventory transform will also look wrong.\
  ![model\_1.png](/imgs/Blockbench/game/model_1.png)
* First, fix textures. Put texture files in `assets/${modid}/textures/block`, then in Blockbench click import texture at the lower-left (you can import multiple textures), or create one yourself (not covered here).\
  ![textures\_1.png](/imgs/Blockbench/textures/textures_1.png)![textures\_2.png](/imgs/Blockbench/textures/textures_2.png)
* After importing, apply textures to the model. There are two ways:
  * Drag a texture directly onto the model.\
    ![textures\_3.png](/imgs/Blockbench/textures/textures_3.png)
  * Right-click the model and choose `Select Texture`.\
    ![textures\_4.png](/imgs/Blockbench/textures/textures_4.png)
  * Sometimes you may get strange UV results after applying textures:\
    ![textures\_5.png](/imgs/Blockbench/textures/textures_5.png)
  * Use the UV editor in the top-left. Selecting a region there updates the mapped area on the model in real time.
  * As for the row of buttons below, try them out yourself and see what each does.\
    ![textures\_6.png](/imgs/Blockbench/textures/textures_6.png)
  * A model has 6 faces, each face can use different texture regions, and each model can use different textures. After finishing UV/textures, save and start the game (or use `F3+T` if the game is already running).\
    ![model\_2.png](/imgs/Blockbench/model_2.png)
	**Launching the game...**
  * If the texture loads correctly, you did it.
  * ![model\_2.png](/imgs/Blockbench/model_2.png)
**Inventory Model**
  * You will notice the inventory and in-hand model can still look odd. Open Display Settings (top-right in workspace), click the three-line menu at the top-left, choose `Apply Preset -> Default Block -> Apply to all slots`, then save (or tune it manually).\
    ![display\_1.png](/imgs/Blockbench/display_1.png) ![display\_2.png](/imgs/Blockbench/display_2.png)
  * Reload with `F3+T` like before. ![model\_3.png](/imgs/Blockbench/game/model_3.png)
  * Now the inventory and in-hand model should look like a normal block.
  * If block break particles are still purple-black, right-click the texture and choose `Use as Particle Texture`.
