---
authors: ['Gu-meng', 'Qi-Month']
---
# Translation

> While writing scenes, you may worry about translating content into other languages.  
> Don’t worry: Ponder automatically generates translation files.
>
> Once you know how keys map to your code, you can edit the files directly.

# Before You Start

After you finish your code and launch the game, Ponder will auto-generate an [en_us.json](https://gitee.com/gumengmengs/kubejs-course/tree/main/Code/Ponder/kubejs/assets/ponderjs_generated/lang/en_us.json) translation file **(even if your text is not English)**.

> Note: Every game launch regenerates `en_us.json` from your code.

Only two simple examples are shown here:

> `en_us.json` is English
>
> `zh_cn.json` is Simplified Chinese

For more language file naming conventions: [link](../..../Digression/LangFileNamingChart.md)

# Start

Using part of [iron_golem.js](https://gitee.com/gumengmengs/kubejs-course/tree/main/Code/Ponder/kubejs/client_scripts/Ponder/iron_golem.js) as an example:

# PonderTag Section

```js
Ponder.tags((event) => {
  event.createTag(
    "kubejs:iron_golem",
    "minecraft:iron_block",
    "Iron Golem",
    "Iron golems are large, strong friendly mobs that protect players and villagers."
  );
});
```

This generates the following entries in `en_us.json`:

```json
{
  "kubejs.ponder.tag.iron_golem": "Iron Golem",
  "kubejs.ponder.tag.iron_golem.description": "Iron golems are large, strong friendly mobs that protect players and villagers."
}
```

> `kubejs` maps to the namespace in the PonderTag id
>
> `ponder.tag` indicates this is a tag entry
>
> `iron_golem` maps to the path part of the PonderTag id

# Ponder Section

```js
Ponder.registry(event => {
    event.create("minecraft:iron_block")
        .tag("kubejs:iron_golem")
        .scene("kubejs:iron_golem_1", "Summon an Iron Golem", (scene, util) => {
            scene.text(25, 'To summon an Iron Golem,\nfirst place four iron blocks in a §bT§r shape');
            scene.text(20, 'Note: §4these four positions must be air blocks§r\nAny non-air block (including snow, tall grass, and water) in these positions will prevent spawning');
       });
});
```

This generates the following entries in `en_us.json`:

```json
{
  "kubejs.ponder.iron_golem_1.header": "Summon an Iron Golem",
  "kubejs.ponder.iron_golem_1.text_1": "To summon an Iron Golem,\nfirst place four iron blocks in a §bT§r shape",
  "kubejs.ponder.iron_golem_1.text_2": "Note: §4these four positions must be air blocks§r\nAny non-air block (including snow, tall grass, and water) in these positions will prevent spawning"
}
```

> `kubejs.ponder.iron_golem_1` maps to the Ponder id
>
> `header` maps to the Ponder title
>
> `text_1` ... `text_n` are generated in display order
