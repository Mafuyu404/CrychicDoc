---
authors: ['Wudji']
---


# 2.3 非标准配方修改

**关于ProbeJS提示**

_**带有🔎符号的段落代表该段内容ProbeJS中有语言文档，或使用ProbeJS编写较为简便。**_

***

本节将介绍非标准配方的修改，包括含的NBT配方、自定义配方格式和修改输入物品状态等（如原版中蛋糕的合成方式）。

## 一、含NBT配方

```js
event.shaped('minecraft:book', [
    'CCC',
    'WGL',
    'CCC'
  ], {
    C: '#forge:cobblestone',
    L: Item.of('minecraft:enchanted_book', '{StoredEnchantments:[{lvl:1,id:"minecraft:sweeping"}]}').weakNBT(),
    // 尽管格式是相同的，但是对于附魔来说，你还可以将其简写成如下形式：
    W: Item.of('minecraft:enchanted_book').enchant('minecraft:respiration', 2).weakNBT(),
    G: '#forge:glass'
  })
```

## 二、自定义配方格式

语句：`event.custom({json})`，`{json}`中必须包括`"type": "mod:recipe_id"`。

该修改方式适用于所有使用原版配方系统的Recipe handler，具体的JSON格式可以参考模组内配方文件（多见于`mod.jar/data/modid/recipes/`目录下）。

对于这种类型的配方，KubeJS提供了一些简写方法：

* 例如，你可以使用`Item.of('x', 4).toJson()` 来替代`{item: 'x', count: 4}` ，
* 使用`Ingredient.of('x').toJson()` 或`Ingredient.of('#x').toJson()`来替代`{item: 'x'}` 或`{tag: 'x'}`。

### 1、修改机械动力中粉碎轮的合成配方

注意！KubeJS现已有机械动力的拓展mod，无需使用该方法修改！

```js
event.custom({
    type: 'create:crushing',//指定合成方式为粉碎轮
    ingredients: [
      Ingredient.of('minecraft:oak_sapling').toJson()//输入内容
    ],
    results: [
      Item.of('minecraft:apple').toResultJson(),//100%输出苹果
      Item.of('minecraft:carrot').withChance(0.5).toResultJson()//50%输出苹果
    ],
    processingTime: 100 //所用时间
  })
//若上述配方使用Json格式添加（即原版数据包格式）
{
  "type": "create:crushing",
  "ingredients": [
    {
      "tag": "minecraft:oak_sapling"
    }
  ],
  "results": [
    {
      "item": "minecraft:apple",
      "count": 1
    },
    {
      "item": "minecraft:carrot",
      "chance": 0.5
    }
  ],
  "processingTime": 100
}
```

### 2：为Extended Crafting添加配方

```js
event.custom({
    type: 'extendedcrafting:shaped_table',
    tier: 4,
    pattern: [
        "XXXXXXXXX",
        "X       X",
        "X       X",
        "X       X",
        "X       X",
        "X       X",
        "X       X",
        "X       X",
        "XXXXXXXXX"
  ],
  key: {
      X: [Ingredient.of('#forge:ingots/gold').toJson()],//标签的使用
  }
      result: [Ingredient.of('minecraft:apple').toJson()]
  })
//上述配方使用数据包修改：
{
  "type": "extendedcrafting:shaped_table",
  "pattern": [
    "XXXXXXXXX",
    "X       X",
    "X       X",
    "X       X",
    "X       X",
    "X       X",
    "X       X",
    "X       X",
    "XXXXXXXXX"
  ],
  "key": {
    "X": {
      "tag": "forge:ingots/gold"
    }
  },
  "result": {
    "item": "minecraft:apple"
  }
}
```

## 三、修改输入物品状态

| **功能**                           | **函数格式**                                                                |
| ---------------------------------- | --------------------------------------------------------------------------- |
| 为输入物品减去耐久                 | .damageIngredient(IngredientFilter 要修改的物品, 整形减去的耐久值)          |
| 替换输入物品(比如桶)               | .replaceIngredient(IngredientFilter 要替换的物品, ItemStack 替换的物品)     |
| 保持输入物品不变                   | .keepIngredient(IngredientFilter 要保留的物品)                              |
| 设定部分烧炼配方消耗时间           | .cookingTime(int 时间)                                                      |
| 自定义事件(Server StartUp脚本注册) | .customIngredientAction(IngredientFilter 要操作的物品, 字符串 自定义事件ID) |

其中，IngredientFilter可以为：

| **内容**     | **示例**                                                             |
| ------------ | -------------------------------------------------------------------- |
| ItemStack    | 'minecraft:dirt', Item.of('minecraft:diamond\_sword').ignoreNBT() 等 |
| 合成输入索引 | 整形，如0，1，2......                                                |
| 对象         | {item: 'something', index: 0}                                        |

⚠1.19.2的KubeJS将原版的合成和它自己引入的，支持各种操作的合成逻辑分开了。比如，使用`event.recipes.minecraft.crafting_shaped`添加的配方将**无法使用**`.keepIngredient`、`.damageIngredient`来进行特殊合成的设置。要使其正常工作，你需要使用形如`event.recipes.kubejs.shaped`的语句添加修改。

### 1、修改输入物品状态

```js
ServerEvents.recipes(event => {
    //用钻石剑切西瓜
      event.shapeless('9x minecraft:melon_slice', [ //无序合成，合成输出: 9个西瓜片
        Item.of('minecraft:diamond_sword').ignoreNBT(), //输入一个忽略NBT的钻石剑
        'minecraft:minecraft:melon' // 其他输入内容
    ]).damageItem(Item.of('minecraft:diamond_sword').ignoreNBT()) // 降低钻石剑耐久1点(必须忽略NBT)

    // 使用两个钻石剑合成kubejs:example_block. 合成后索引为1的钻石剑掉一点耐久并保留第二个钻石剑.
    event.shaped('kubejs:example_block', [
        'SD ',
        'D S'
    ], {
        S: Item.of('minecraft:diamond_sword').ignoreNBT(),
        D: 'minecraft:dirt'
    }).damageIngredient(0).keepIngredient('minecraft:diamond_sword')//叠加使用多个函数

    // 使用两个钻石剑合成kubejs:example_block. 合成后钻石剑被替换为石剑
    event.shapeless('kubejs:example_block', [
        Item.of('minecraft:diamond_sword').ignoreNBT(),
        'minecraft:stone',
        Item.of('minecraft:diamond_sword').ignoreNBT(),
        'minecraft:stone'
    ]).replaceIngredient('minecraft:diamond_sword', 'minecraft:stone_sword')

    // 使用沙子，骨粉，土方块和水瓶合成陶土. 合成后，水瓶被玻璃瓶所替代
    event.shapeless('minecraft:clay', [
        'minecraft:sand',
        'minecraft:bone_meal',
        'minecraft:dirt',
        Item.of('minecraft:potion', {Potion: "minecraft:water"})
    ]).replaceIngredient({item: Item.of('minecraft:potion', {Potion: "minecraft:water"})}, 'minecraft:glass_bottle')
})
```

### 2、自定义输入物品配方事件

实例：在工作台中使用附魔书为工具附魔，附魔后清除附魔书带有的附魔。

`kubejs\startup_scripts\CustomIngredientAction.js`

```js
Ingredient.registerCustomIngredientAction("apply_enchantment", (itemstack, index, inventory) => {
      let enchantment = inventory.get(inventory.find(Item.of("minecraft:enchanted_book"))).nbt;
      if (itemstack.nbt == null)
          itemstack.nbt = {}
      itemstack.nbt = itemstack.nbt.merge({ Enchantments: enchantment.get("StoredEnchantments") })
      return itemstack;
  })
```

`kubejs\server_scripts\Recipe.js`

```
ServerEvents.recipes(event => {
      event.shapeless("minecraft:book", ["#forge:tools", Item.of("minecraft:enchanted_book")])
            .customIngredientAction("#forge:tools", "apply_enchantment")
})
```

## 四、修改输出物品状态

相较于输入物品修改，输出物品的修改较为简单。

| 功能             | 表述                                                           |
| ---------------- | -------------------------------------------------------------- |
| 修改输出物品状态 | .modifyResult(ModifyRecipeResultCallback 输出物品修改回调函数) |

🔎其中，`ModifyRecipeResultCallback`具有两个参数`ModifyRecipeCraftingGrid` 和 `ItemStack`，其中前者支持以下方法：

| 表述                                       | 功能               | 返回值              |
| ------------------------------------------ | ------------------ | ------------------- |
| .player()                                  | 获取合成物品的玩家 | Player              |
| .find(ingredient 寻找物品, int 跳过的数字) | 获取符合条件的物品 | ItemStack           |
| ...                                        | ...                | 详见ProbeJS生成文档 |

需要注意的是，`ModifyRecipeResultCallback`需要一个返回值，即经过修改的输出物品

下面给出一个基础例子：使用红石粉无序合成红石火把，如果用户名是`Wudji_NotFound`，就为这个火把附魔击退 X。

```js
ServerEvents.recipes(event => {
	event.shapeless('minecraft:redstone_torch','minecraft:redstone').modifyResult(function(inventory,itemStack){
		if(inventory.getPlayer().getUsername() == "Wudji_NotFound"){ // 判断用户名
			return itemStack.enchant('minecraft:knockback', 10).withName(Component.blue("击退棒"))// 为物品附魔及重命名
		}
		return itemStack;// 判断未通过则直接返回
	})
})
```