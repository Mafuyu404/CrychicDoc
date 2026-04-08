---
authors: ['Gu-meng']
---
# Model Overview

Models apply to both blocks and items. This page does not teach you how to create models in a modeling tool. If you want to learn model creation, see [**Blockbench basics**](../Digression/BlockbenchBasic).

This page is a simple overview of models: where model files go, and which registration methods relate to models.

# Main

## Where Model Files Go
Model paths are simple:

Item models live under `assets/${modid}/models/item`.

Block models live under `assets/${modid}/models/block`.

The model filename must match the block/item id, and the file extension **must be** `.json`.

## Registration Methods

For items, you can use `.model(...)` and pass the model path directly. For example:

```js
StartupEvents.registry("item", (event) => {
	let MODID = "test_mod"
	event.create(`${MODID}:test_item`)
		.model(`${MODID}:item/test_item`)
})
```

Here `let MODID = "test_mod"` is your custom mod id (namespace). When specifying the model path, it must match this namespace. If you do not set your own namespace, the default is `kubejs`.

For blocks:

```js
StartupEvents.registry("block", (event) => {
	let MODID = "test_mod"
	event.create("test_mod:test_block")
		.model(`${MODID}:block/test_block`)
})
```

Same idea: the namespace in the model path must match.

## Simple Model JSON Examples

### Multi-face Block

This is a simple block model JSON example that does not require Blockbench.

We'll use a furnace-like model (different textures on different faces) as the reference.

First, prepare textures for the `front`, `side`, and `top`.

Create a JSON file named after your block id under `models/block`. Start with this (do not copy the comments into a real JSON file; real JSON does not allow comments):

```json
{
	// You can omit the "minecraft:" namespace, but we include it for clarity
	"parent": "minecraft:block/orientable"
}
```

This sets the parent model. Your model is based on that parent.

Next, define textures:

```json
{
	// You can omit the "minecraft:" namespace, but we include it for clarity
	"parent": "minecraft:block/orientable",
	"textures": {
		// Front face
		"front": "test_mod:block/front",
		// Side faces
		"side": "test_mod:block/side",
		// Top face
		"top": "test_mod:block/top"
	}
}
```

That is enough for a basic block model. Put the textures under `assets/test_mod/textures/block`.

### Item

Item models are even simpler:

```json
{
	"parent": "minecraft:item/generated",
	"textures": {
		"layer0": "test_mod:item/test_item"
	}
}
```
