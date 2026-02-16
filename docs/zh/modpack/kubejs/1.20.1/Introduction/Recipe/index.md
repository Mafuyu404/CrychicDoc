---
title: Recipe
hidden: false
priority: 60
collapsed: true
---

<!--
<llm-only>
## KubeJS 配方系统

这是KubeJS配方系统的核心文档页面。配方系统用于添加、修改和删除Minecraft中的合成配方。

### 支持的配方类型

| 类型 | 命名空间 | 用途 |
|-----|---------|------|
| CraftingTable | kubejs:crafting_table | 工作台合成（3x3） |
| Furnace | kubejs:furnace | 熔炉烧制 |
| BlastFurnace | kubejs:blast_furnace | 高炉烧制 |
| Smoker | kubejs:smoker | 烟熏炉烹饪 |
| SmithingTable | kubejs:smithing_table | 锻造台合成 |
| Campfire | kubejs:campfire | 营火烹饪 |
| Stonecutter | kubejs:stonecutter | 切石机切割 |

### 核心API方法

```javascript
// 添加配方 - StartupScript
StartupEvents.init(event => {
    // 工作台配方
    event.recipes.craftingTable('modid:recipe_id', 'output', [
        'aaa',
        'bab',
        'ccc'
    ]);

    // 熔炉配方
    event.recipes.furnace('output', 'input', experience, cookingTime);

    // 删除配方
    event.recipes.craftingTable.remove('output_item');
});
```

### 配方ID命名规范

- 格式：`modid:recipe_name`
- modid：你的模组ID（建议使用小写字母和下划线）
- recipe_name：配方的唯一标识（建议使用下划线分隔）

### 重要约束

- 配方输入输出必须使用完整的资源定位符（如 `minecraft:stick`）
- 配方JSON中的占位符必须与输入网格对应
- 使用 `remove` 时可以按输出物品或配方ID删除
- 修改已有配方前建议先备份原版配方

### 相关文档

- 基础配方：Recipe/AddRecipe/index.md
- 原版配方：Recipe/AddRecipe/Vanilla/index.md
- 物品系统：Item/index.md
- 标签系统：Tag/index.md
</llm-only>
-->

