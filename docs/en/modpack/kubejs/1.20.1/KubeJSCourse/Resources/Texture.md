---
authors: ['Gu-meng']
---
# Textures

This page is a simple overview of textures: where texture files go, and which registration methods relate to textures.

# Main

## Where Texture Files Go

Texture files live under `assets/${modid}/textures`. Common subfolders include `block`, `entity`, `item`, and `models`, each corresponding to different texture types:

* `block` for block textures
* `item` for item textures
* `entity` for entity textures (mobs, tridents, arrows, and other special model textures)
* `models/armor` for armor textures

The folders above are the most commonly used for KubeJS registrations. More advanced UI (`gui`) work typically cannot be done by KubeJS alone.

When registering content, it is not enough to place textures in the right folder. You also need to reference them from models. See [**Intro to models**](./Model).

## Registration Methods

For item registration there is a single `texture(...)` method. You pass the texture path directly. (This is often used when the texture is not in the default location, or when you use a white base texture and then tint it using the `color(...)` method.)

For blocks there are `textureAll`, `textureSide`, and `texture` to control textures on different faces.

`textureAll` applies one texture to every face. `textureSide` is similar but targets the side faces.

`texture(id, path)` applies a texture to a specific slot. According to ProbeJS hints, it takes an `id` argument before the texture path. This `id` selects which texture slot you are overriding.

For blocks like a furnace (different textures per face), the model JSON has a `textures` object. Each key is a texture slot name and each value is a texture path. The `id` you pass to `texture(...)` is one of these keys.

```json
{
	"parent": "minecraft:block/orientable",
	"textures": {
		"front": "minecraft:block/furnace_front",
		"side": "minecraft:block/furnace_side",
		"top": "minecraft:block/furnace_top"
	}
}
```

For the vanilla furnace model:

`front` is the front face texture.
`side` is the side texture.
`top` is the top texture.

So the first argument of `texture(...)` is the slot key (a **String**), and the second argument is the texture path.
