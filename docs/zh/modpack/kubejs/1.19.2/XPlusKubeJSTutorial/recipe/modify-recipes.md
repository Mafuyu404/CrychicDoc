---
authors: ['Wudji']
---


# 2.2 配方的添加，修改和移除

**关于ProbeJS提示**

_**带有🔎符号的段落代表该段内容ProbeJS中有语言文档，或使用ProbeJS编写较为简便。**_

***

## 一、配方事件

要修改配方，你需要在`ServerEvents.recipes`事件下注册一个“事件监听器”，并在该事件下完成所有修改，例如：

```js
ServerEvents.recipes(event => {
  // 该部分为回调函数
  // 本节中的示例脚本将只包括此部分内容
  console.info('ServerEvents.recipes触发')
})
```

以下为配方事件支持的部分方法：

| 方法                                                                               | 描述                                                         | 返回值 |
| ---------------------------------------------------------------------------------- | ------------------------------------------------------------ | ------ |
| forEachRecipe(RecipeFilter 过滤器, Consumer consumer)                              | 对所有满足过滤器的配方进行修改                               | void   |
| countRecipes(RecipeFilter 过滤器)                                                  | 返回满足过滤器的配方个数                                     | 整形   |
| containsRecipe(RecipeFilter 过滤器)                                                | 返回是否存在满足给定过滤器的配方                             | 布尔值 |
| remove(RecipeFilter 过滤器)                                                        | 移除满足给定过滤器的配方，并返回移除个数                     | 整形   |
| replaceInput(RecipeFilter 过滤器, IngredientMatch 被替换物品, Ingredient 替换物品) | 对所有满足过滤器的配方进行修改：替换输入物品，并返回操作个数 | 整形   |
| replaceOutput(RecipeFilter 过滤器, IngredientMatch 被替换物品, ItemStack 替换物品) | 对所有满足过滤器的配方进行修改：替换输出物品，并返回操作个数 | 整形   |

## 二、配方添加

🔎 `event.recipes`可以获取所有存在的配方类型(`DocumentedRecipes`)，其属性为命名空间。你可以通过这种情况修改所有支持的配方，例如：

```js
ServerEvents.recipes(event => {
	// 营火配方：红石烧烤得到红石火把
	event.recipes.minecraft.campfire_cooking('minecraft:redstone_torch','minecraft:redstone')
})
```

以下为一些简写方法：

### 1、有序配方添加

语句：`event.shaped(输出物品 , 形状 , 输入物品)`

例子：用 4个海绵 和 4个钻石 合成 3个石头

```js
event.shaped(Item.of('minecraft:stone', 3), [
		'LOL',
		'O O',
		'LOL'
	],
    {
		L: 'minecraft:sponge',
		O: 'minecraft:diamond'
})
```

### 2、无序配方添加

语句：`event.shapeless(输出物品 , 输入物品)`

例子：用 1个海绵 和 1个带有木板标签的物品 合成2个石头

```
event.shapeless(Item.of('minecraft:stone', 2), ['minecraft:stone', Ingredient.matchAny('#minecraft:planks')])
```

### 3、锻造台配方

语句：`event.smithing(输出物品, 目标物品, 消耗物品)`

例子：苹果 + 金锭 -> 金苹果

```js
event.smithing('minecraft:golden_apple', 'minecraft:apple', 'minecraft:gold_ingot')
```

### 4、熔炉配方

语句：`event.smelting(输出物品, 输入物品)`

例子：1个金苹果 经熔炉烧炼得到 32个胡萝卜

```js
event.smelting('32x minecraft:carrot', 'minecraft:golden_apple')
```

### 5、烟熏炉配方

语句：`event.smoking(输出物品, 输入物品)`

例子：玻璃经烟熏炉烧炼得到遮光玻璃

```
event.smoking('minecraft:tinted_glass', 'minecraft:glass')
```

注：燃料需要在烧炼事件中进行更改

### 6、营火配方

语句：`event.campfireCooking(输出物品, 输入物品)`

例子：木棍经过营火烧炼得到火把

```js
event.campfireCooking('minecraft:torch', 'minecraft:stick')
```

### 7、切石机

语句：`event.stonecutting(输出物品, 输入物品)`

例子：1个带有木板标签的物品合成3个木棍

```js
event.stonecutting('3x minecraft:stick', '#minecraft:planks')
```

### 8、高炉

语句：`event.blasting(输出物品, 输入物品)`

例子：金苹果经过高炉烧炼得到3个苹果

```js
event.blasting('3x minecraft:apple', 'minecraft:golden_apple')
```

## 三、配方过滤器及配方的修改和移除

### 1、配方过滤器

配方过滤器可在配方修改或移除中用于匹配符合条件的配方，其包括以下类型

| 写法                           | 描述             | 示例                                                                       |
| ------------------------------ | ---------------- | -------------------------------------------------------------------------- |
| `{output:'物品'}`              | 匹配输出物品     | `{output: '#minecraft:wool'}`                                              |
| `{input:'物品'}`               | 匹配输入物品     | `{input: '#forge:dusts/redstone'}`                                         |
| `{mod:'模组ID'}`               | 匹配模组ID       | `{mod: 'kubejstutorial'}`                                                  |
| `{recipe:'配方ID'}`            | 匹配配方ID       | `{mod: 'xplusmodpack'}`                                                    |
| `{type: '配方类型'}`           | 匹配配方类型     | `{type: 'minecraft:campfire_cooking'}`                                     |
| `{条件1:"值",条件2:"值"}`      | 组合过滤器（与） | `{output: 'minecraft:cooked_chicken', type: 'minecraft:campfire_cooking'}` |
| `[{条件1:"值"}, {条件2:"值"}]` | 组合过滤器（或） | `[{type:'minecraft:smelting'}, {type:'minecraft:blasting'}]`               |
| `{not:{条件:"值"}}`            | 组合过滤器（非） | `{not:{type:"minecraft:smelting"}}`                                        |

上表中组合过滤器依旧可以多层叠加，从而实现更复杂的条件判断，详见下方示例。

### 2、配方修改

| 语句                                                  | 描述         |
| ----------------------------------------------------- | ------------ |
| event.replaceInput(配方过滤器, 被替换物品, 替换物品)  | 修改输入物品 |
| event.replaceOutput(配方过滤器, 被替换物品, 替换物品) | 修改输出物品 |

例子：在输出物品为minecraft:ladder的配方中，将输入物品中的木棍替换为树苗

```js
event.replaceInput(
    { output: 'minecraft:ladder' }, // 物品过滤器（匹配输出物品）
    'minecraft:stick', // 被替换物品
    Ingredient.of('#minecraft:saplings') // 替换物品
    // 注意：流体标签在Fabric端不可用。
)
```

例子：在所有配方中，将输出物品中的minecraft:stick替换为minecraft:oak\_sapling

```js
event.replaceOutput({}, 'minecraft:stick', 'minecraft:oak_sapling') // 物品过滤器留空
```

例子：在所有无序配方中，将minecraft:iron\_nugget替换为minecraft:gold\_nugget

```js
event.replaceInput({type: 'minecraft:crafting_shapeless'}, 'minecraft:iron_nugget', 'minecraft:gold_nugget')
```

### 3、配方移除

语句：`event.remove()`

```js
// 物品过滤器例子 + 配方移除例子
// 移除所有配方:
event.remove({}) 
// 移除输出物品为石斧的配方：
event.remove({output: 'minecraft:stone_pickaxe'}) 
// 移除输出物品带有羊毛tag的配方：
event.remove({output: '#minecraft:wool'})
// 移除输入物品带有红石tag的配方：
event.remove({input: '#forge:dusts/redstone'})
// 移除农夫乐事添加的配方：
event.remove({mod: 'farmersdelight'})
// 移除所有营火配方：
event.remove({type: 'minecraft:campfire_cooking'}) 
// 移除除熔炉以外所有输出物品为石头的配方:
event.remove({not:{type:"minecraft:smelting"},output:"stone"}) 
// 移除输出物品为熟鸡肉的营火配方：
event.remove({output: 'minecraft:cooked_chicken', type: 'minecraft:campfire_cooking'})
// 移除熔炉或高炉的输出物品为铁锭的配方：
event.remove([{type:'minecraft:smelting',output:'minecraft:iron_ingot'}, {type:'minecraft:blasting', output:'minecraft:iron_ingot'}])	  
// 通过ID移除配方（data/minecraft/recipes/glowstone.json）：
// 注：配方ID和输出物品是两个概念！
event.remove({id: 'minecraft:glowstone'})
```

[另请参阅：RecipesEventJS](https://github.com/KubeJS-Mods/KubeJS/blob/1.19/main/common/src/main/java/dev/latvian/mods/kubejs/recipe/RecipesEventJS.java)

## 四、配方ID

mc中所有的配方都有一个随机的ID，但以下配方被指定了一个唯一的静态ID，可用于编写Patchouli手册或覆盖已存在配方等。

你可以通过JEI/REI来便捷地查询配方的ID。

```js
event.smelting('minecraft:golden_apple', 'minecraft:carrot').id('wudjimodpack:wudji_first_recipe_id')
```