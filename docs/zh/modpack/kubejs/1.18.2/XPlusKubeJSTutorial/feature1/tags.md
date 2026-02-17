---
authors: ['Wudji']
---
<llm-only>
This Chinese page (zh/modpack/kubejs/1.18.2/XPlusKubeJSTutorial/feature1/tags.md) mirrors the corresponding English content; include it when generating LLM-friendly documentation and keep the original structure.
</llm-only>


# 3 标签

***

你应将以下内容放在`.minecraft\kubejs\server_scripts`下

```
onEvent('item.tags', event => {
        // 获取 #forge:cobblestone，然后将minecraft:diamond_ore添加进去
        event.add('forge:cobblestone', 'minecraft:diamond_ore')
  
        // 获取#forge:cobblestone，然后移除minecraft:mossy_cobblestone
        event.remove('forge:cobblestone', 'minecraft:mossy_cobblestone')
  
        // 移除#forge:ingots/copper
        event.removeAll('forge:ingots/copper')
})

```