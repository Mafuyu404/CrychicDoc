---
authors: ['Gu-meng']
---
# Localization (lang files)
This chapter explains how to add and modify localization (language) files, including overriding other mods' translations.

* ### File path
  `lang` files are stored under `assets/${modid}/lang`. Different locales use different filenames. See the [Minecraft Wiki: Language](https://minecraft.wiki/w/Language) or the in-doc page [Lang file naming chart](../Digression/LangFileNamingChart.md). Files use the `.json` extension, for example `en_us.json`, `zh_cn.json`, `zh_tw.json`, etc.

  For `minecraft:sand`, `minecraft` is the `modid`.

* ### Format
  * A `lang` file is a JSON object that maps localization keys to text, for example:
```json
{
    "item.kubejs.test_item": "Test Item",
    "block.kubejs.test_block": "Test Block",
    "fluid.kubejs.test_fluid": "Test Fluid"
}
```
  When editing `lang` JSON, the last line **must not end with a trailing comma**. You also **cannot omit commas between entries**, and you **cannot add comments**.
  * Localization keys are typically `type.modid.id`.
  `type` is the category, for example:
  Fluids use `fluid`
  Items use `item`
  Blocks use `block`
  * In scripts you can also use your own custom keys. For example, add a tooltip translation for stone:

```js
ItemEvents.tooltip((event) => {
	event.add('minecraft:stone', [Text.translate("tip.mc.stone")])
})
```

```json
{
    "tip.mc.stone": "I am a stone."
}
```
  You can also use Minecraft formatting codes (colors) or escape sequences in lang values.

* ### Overriding Other Mods
  * To modify another mod's `lang` file, you generally need to create a resource pack. See [Minecraft Wiki: Creating a resource pack](https://minecraft.wiki/w/Tutorial:Creating_a_resource_pack).
  Then create a folder under `assets` with the same name as the target `modid`.
  
  * It is recommended to ship this localization resource pack as part of the modpack. As mentioned earlier, you may need a mod that can load built-in packs, such as [Json Things](https://www.mcmod.cn/class/7734.html).

  * You can also look inside `ModFile.jar/assets/` to find the correct folder names, then recreate the structure yourself. Under `${modid}`, create a `lang` folder and put the `lang` JSON you want to override inside it.
  * For Chinese translations, extract the original `lang` file and rename it to `zh_cn.json`. **Make sure the filename is exactly `zh_cn.json` (not `zh_cn.json.txt` or `zh_cn.txt.json`).**

  **All mod ids should consist only of `lowercase letters, numbers, and underscores (a-z, 0-9, _)`.**

* ### Faster Lang Authoring (for your own registrations only)
  * In KubeJS 6 there is a `ClientEvents.lang` event. A basic example:
```js
ClientEvents.lang("zh_cn", (event) => {
	event.add("item.kubejs.test_item", "KubeJS Test Item")
	event.add("block.kubejs.test_block", "KubeJS Test Block")
	event.add("fluid.kubejs.test_fluid", "KubeJS Test Fluid")
})
```
  * The first argument to `ClientEvents.lang` is the locale code. The `event.add(...)` calls are similar to JSON entries.
  * You might ask: **"So what is the difference from JSON?"**
  * JSON is data, not a programming language. JavaScript is a programming language, so you can use loops to generate many keys quickly. Example:
```js
ClientEvents.lang("zh_cn", (event) => {
	const MODID = "kubejs"

	// Item translations
	let itemResourceLang = [
		["test_item_1", "KubeJS Test Item 1"],
		["test_item_2", "KubeJS Test Item 2"]
	]
	itemResourceLang.forEach(([key, text]) => {
		event.add(`item.${MODID}.${key}`, text)
	})

	// Block translations
	let blockResourceLang = [
		["test_block_1", "KubeJS Test Block 1"],
		["test_block_2", "KubeJS Test Block 2"]
	]
	blockResourceLang.forEach(([key, text]) => {
		event.add(`block.${MODID}.${key}`, text)
	})

	// Fluid translations
	let fluidResourceLang = [
		["test_fluid_1", "KubeJS Test Fluid 1"],
		["test_fluid_2", "KubeJS Test Fluid 2"]
	]
	fluidResourceLang.forEach(([key, text]) => {
		/*
		 * Fluids appear under different key types depending on where they are shown:
		 * JEI uses "fluid"
		 * In-world blocks use "block"
		 * Buckets use "item". Since the id ends with "_bucket", we add that suffix.
		 */
		event.add(`fluid.${MODID}.${key}`, text)
		event.add(`block.${MODID}.${key}`, text)
		event.add(`item.${MODID}.${key}_bucket`, `${text} Bucket`)
	})
})
```
