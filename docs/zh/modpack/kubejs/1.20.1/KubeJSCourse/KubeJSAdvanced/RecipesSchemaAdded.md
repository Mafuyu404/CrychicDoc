---
authors:
  - Gu-meng
editor: Gu-meng
---

# 配方概要添加(RecipesSchemaAdded)

> 本文档基于1.20.1编写, 嗯...1.21就没了, 1.21用的其他方式
>
> ![wolf](/imgs/Schema/wolf.png)

在1.20的KubeJS添加了一个`recipeSchemaRegistry`启动事件`startup`, 这个事件可以让你给某些没有适配KubeJS的配方类型添加简单的适配, 例如`createmetallurgy(机械动力: 冶金学)`, 这个Mod没有添加KubeJS适配

> 我找作者问过了, 他说真的没时间学科比吉斯(KubeJS)了
	
这个Mod内有个类似于[**匠魂**](https://www.mcmod.cn/class/3725.html)的铸造台配方, 在Json格式下是这样

```json
{
	"type": "createmetallurgy:casting_in_table",
	"ingredients": [
		{
			"item": "createmetallurgy:graphite_plate_mold"
		},
		{
			"fluid": "createmetallurgy:molten_brass",
			"amount": 90
		}
	],
	"processingTime": 80,
	"mold_consumed": false,
	"result": {
		"item": "create:brass_sheet"
	}
}
```

同样的配方, 在添加了配方概述(下文称**Schema**)后可以变成这样

```js
ServerEvents.recipes((event) => {
	const { createmetallurgy } = event.recipe

	createmetallurgy.casting_in_table("create:brass_sheet", [
		"createmetallurgy:graphite_plate_mold",
		Fluid.of("createmetallurgy:molten_brass", 90)
	]).processingTime(80).mold_consumed(false)
})
```

## 正文开始

介绍和示例就演示到这里, 下面开始正文(完整的代码在下面👇)

首先请先下载这个Schema配方文件[**RecipesSchema**](https://cloud.mihono.cn/s/FPCmZY3ibF5JyDD/download/RecipesSchema.java), 这个文件内有着各种类型的组件, 在编写的时候需要很频繁的查阅这些东西

然后去[**迺逸夫老师的GitHub**](https://github.com/Prunoideae/-recipes/blob/1.20.1/src)仓库下载[**prelude.js**](https://github.com/Prunoideae/-recipes/blob/1.20.1/src/prelude.js), 这个文件是逸夫老师事先写好的轮子, 可以非常大程度的提升编写效率

随后将这个文件放入`kubejs/starup_scripts/@recipes`内

随后就可以开始编写自己的Schema了, 创建一个需要适配的Mod的`modid`一样的js文件

> 例如`createmetallurgy.js`

这里就拿`createmetallurgy`演示, 创建了文件后, 我们在文件内先写入

```js
new Schema()
```

这里第一个括号内填入配方类型, 配方类型可以在配方文件的第一行`"type"`看到, 例如上文的铸造台配方类型就是`"createmetallurgy:casting_in_table"`

```js
new Schema("createmetallurgy:casting_in_table")
```

下面开始添加`.simpleKey()`方法, 第一个参数内填入键名, 也就是配方文件中对应的各种`一级键名`, 例如`ingredients` `processingTime` `result`等

第二个就是需要填入的类型, 这个需要在上面提到的[**Java文件**](/Files/RecipesSchema.java)进行查看

第三个就是填入可选的内容, 其中字符串需要列举出来, 使用`||`进行分隔, 例如`"superheated" || "heated"`

接下来我们拿第一个类型`createmetallurgy:casting_in_basin`进行讲解

首先各种方法的排序代表着在`ServerEvent.recipes`中的排序

例如我是`ingredients`在前那`createmetallurgy.casting_in_basin()`排在第一个的就是`ingredients`而不是`result`或`results`**<font color=red>(这里我提到了两种, 要留意一下, 下面会着重讲解(问就是我就被这个搞过🤡))</font>**

![1](/imgs/Schema/1.png) 

![2](/imgs/Schema/2.png) 

![3](/imgs/Schema/3.png) 

好我们继续, 上面提到想要和别的配方写法一样那首先就得确保`results`或`result`再最前面, `ingredients`或`input`一类的关键词在第二个

我们先说第一个`results`和`result`, 他们之间的区别就是一个`s`, 在英语中`s`代表复数, 因此多一个`s`就代表着可以 **`多输出`**

写入的第一个之后我们看第二个, 第二个代表着类型, 打开游戏, 随便看一个配方

![4](/imgs/Schema/4.png)

根据**猜测**, 这种配方类型**似乎**只能输出一种物品, 因此我们在[Java文件](/Files/RecipesSchema.java)内找到带关键词`output` `item`的单词`outputItem` `输出物品`, 然后我们先把这个写进去, 后面如果不行我们继续慢慢改

```js
new Schema("createmetallurgy:casting_in_basin")
	.simpleKey("result", "outputItem")
```

接下来是第二个`ingredients`, 和上面的`results`一样, 后面带个s, 并且根据上图的配方来看, 这是一个`多输出`的配方(熔融铁和板子模具), 接下来我们去看`Json`文件

![5](/imgs/Schema/5.png)

不难看出在`ingredients`这个键后所带的是一个`[]`, 也就是`数组`, 数组的英文是`Array`, 你可能会像上面一样继续去找关键词. 这是对的, 但是这个配方不仅仅可以输入`item`, 同样可以输入`fluid`, 这是一个很关键的点, 需要注意, 根据上面的几个关键词`Array` `item` `fluid`找到`inputFluidOrItemArray`将他写在第二个参数上, 这样一个最简单的`Schema`就添加好了, 我们打开游戏输入`/probe dump`并且重启**VS Code**后就可以看到解构的补全了

![6](/imgs/Schema/6.png)

我们写一个简单的配方

```js
ServerEvents.recipes((event) => {
	const { createmetallurgy } = event.recipes

	createmetallurgy.casting_in_basin("minecraft:brick", [
		Fluid.of("minecraft:lava", 90),
		"#forge:ingots/iron"
	])
})
```

理论上这个配方其实已经可以用了, 但是配方内还有`processingTime`和`mold_consumed`两个键, 对应着`加工时间`和`是否消耗模具`

因此我们需要将这两项也写上, 这两个都比较简单

首先是`加工时间`, 加工时间用的是数字(单位[**Tick**](https://zh.minecraft.wiki/w/刻#游戏刻与计算速率)), 因此其实随便找一个和数字(**Number**)有关的就好, 第三个参数表现的时间可写可不写, 但最好随便写个数字

我这里用的是`doubleNumber`, 这种加工时间的一般在配方后用`方法`表示

![7](/imgs/Schema/7.png)

接下来是`模具消耗(mold_consumed)`, 这个在原文件中使用的是`布尔值(true/false)`, 因此和上面一样, 随便找一个可以表示布尔的即可, 第三个参数把可选的`true`和`false`加上

然后进游戏`dump`一下, 写一个完整的配方

```js
ServerEvents.recipes((event) => {
	const { createmetallurgy } = event.recipes

	createmetallurgy.casting_in_basin("minecraft:brick", [
		Fluid.of("minecraft:lava", 90),
		"#forge:ingots/iron"
	]).processingTime(114).mold_consumed(true)
})
```

**Game Be Staring...**

![8](/imgs/Schema/8.png)

配方一切正常, 来看看🐴

```js
new Schema("createmetallurgy:casting_in_basin")
	.simpleKey("result", "outputItem")
	.simpleKey("ingredients", "inputFluidOrItemArray")
	.simpleKey("processingTime", "doubleNumber", 100)
	.simpleKey("mold_consumed", "bool", false || true)
```

剩下的配方都差不多, 自己学着写一下

## 提示

这里简单讲一下机械动力的`热量等级`, 在配方中他们是一个键和字符串`heatRequirement`, 在Schema中创建了`key`后选择使用`非空字符串(nonEmptyString)`, 然后在后面把热源等级都加上即可, 使用`||`进行分隔

随后在配方后使用`.heatRequirement(String)`方法进行热量配置

## 完整代码示例
```js
new Schema("createmetallurgy:casting_in_basin")
	.simpleKey("result", "outputItem")
	.simpleKey("ingredients", "inputFluidOrItemArray")
	.simpleKey("processingTime", "doubleNumber", 100)
	.simpleKey("mold_consumed", "bool", false || true)

new Schema("createmetallurgy:casting_in_table")
	.simpleKey("result", "outputItem")
	.simpleKey("ingredients", "inputFluidOrItemArray")
	.simpleKey("processingTime", "doubleNumber", 100)
	.simpleKey("mold_consumed", "bool", false || true)

new Schema("createmetallurgy:grinding")
	.simpleKey("results", "outputItemArray")
	.simpleKey("ingredients", "inputItemArray")
	.simpleKey("processingTime", "doubleNumber", 100)

new Schema("createmetallurgy:alloying")
	.simpleKey("results", "outputFluidOrItemArray")
	.simpleKey("ingredients", "inputFluidOrItemArray")
	.simpleKey("heatRequirement", "nonEmptyString", "superheated" || "heated")
	.simpleKey("processingTime", "doubleNumber", 100)

new Schema("createmetallurgy:melting")
	.simpleKey("results", "outputFluidArray")
	.simpleKey("ingredients", "inputItemArray")
	.simpleKey("heatRequirement", "nonEmptyString", "superheated" || "heated")
	.simpleKey("processingTime", "doubleNumber", 100)
```