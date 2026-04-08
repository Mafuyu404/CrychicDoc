---
authors: ['Gu-meng']
---
# What Is a Namespace
Explanation adapted from [McWiki](https://zh.minecraft.wiki/w/%E5%91%BD%E5%90%8D%E7%A9%BA%E9%97%B4ID?variant=zh-cn).

Namespaced identifier (also called resource location/resource identifier/namespaced string) is a way to identify specific objects in-game while avoiding ambiguity and conflicts.
# What Namespaces Are Used For
In-game, a namespaced ID is a resource location and has two main parts:

Namespace: a string used to identify resource ownership. The default namespace is `minecraft`.

Path: a string. In datapacks it usually reflects the file path of the resource, but it can also be just an identifier.

In modding, namespace is often called `modid`. So `modid` also refers to the namespace and is used to point to the matching resource folder name.

## Why It Matters in Item Registration
```js
StartupEvents.registry("item", (event) => {
    event.create("meng:my_item", "basic")
})
```
The example above is a basic item registration. The first argument is the item ID.

The item ID is split by `:`. The part before `:` is the namespace (used by the game to find resources), and the part after it is the item ID.

The namespace determines where assets are stored, and the item ID determines the asset naming.

# Namespace and ID Rules
Namespaces and IDs can only use the following characters:
- Numbers `0123456789`
- Lowercase letters `abcdefghijklmnopqrstuvwxyz`
- Underscore `_`
- Hyphen `-`
- Dot `.`

If you get an error like `Non [a-z0-9/._-] xxxxx`, your ID or namespace contains invalid characters.

There is one special character: `/`. It cannot be used in the namespace, but it can be used in the ID path. In that case it acts as a resource path separator for directories.

# Where to Check ModID
`Fabric` `modid` is in `ModFile.jar/fabric.mod.json`\
`Forge` or `NeoForge` `modid` is in `ModFile.jar/META-INF/mods.toml`
