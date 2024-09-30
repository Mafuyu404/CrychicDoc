---
progress: 10
state: unfinished
---
# 注册

## 事件监听

::: code-group

```js [KubeJS]
StartupEvents.registry('minecraft:item', event => {

})
```

:::

## 事件方法

- create('物品注册id');

- create('物品注册id', 物品类型);

::: details 物品类型

|   类型    |   描述    |   类型参考    |
|:---------:|:---------:|:---------:|
|   'basic'    |    基础物品    |   [Internal.BasicItemJS$Builder](../Addon/ProbeJS/ProbeJSClassFlie.md#basicitemjsbuilder)   |
|   'sword'    |    剑类物品    |   -   |
|   'pickaxe'    |    镐类物品    |   -   |
|   'axe'    |    斧类物品    |   -   |
|   'shovel'    |    锹类物品    |   -   |
|   'hoe'    |    锄类物品    |   -   |
|   'helmet'    |    头盔类物品    |   -   |
|   'chestplate'    |    胸甲类物品    |   -   |
|   'leggings'    |    护腿类物品    |   -   |
|   'boots'    |    靴子类物品    |   -   |
|   'music_disc'    |    唱片类物品    |   -   |

:::

## 纹理资源

- 你需要将物品贴图放入kubejs/assets/\<namespace\>/textures/item/\<id\>.png。

- 如果你的物品注册名是kubejs:demo，那么namespace是kubejs，id是demo。

- 更多详细信息请参阅[Minecraft-wiki/资源包#资源包内容](https://zh.minecraft.wiki/w/%E8%B5%84%E6%BA%90%E5%8C%85#%E8%B5%84%E6%BA%90%E5%8C%85%E5%86%85%E5%AE%B9)。

## 常用函数

::: details 基础物品

|   函数    |   描述    |   默认    |
|:---------:|:---------:|:--------:|
|   displayName(Component 物品显示名)   |   设置物品显示名。  |   -   |
|   fireResistant(boolean)    |    是否抗火。    |   false   |
|   maxDamage(number)    |    设置最大损伤值（耐久度）。    |   0   |
|   unstackable()    |    设置物品无法堆叠（堆叠上限为1）。    |   64   |
|   maxStackSize(number)    |    设置最大堆叠数。    |   64   |
|   modifyAttribute(ResourceLocation_ 属性id, string 属性标识符, number 属性值, Internal.AttributeModifier$Operation_ 属性操作符)    |    设置物品属性修饰符。    |   -   |
|   tooltip(Component 工具栏提示)    |    设置物品工具栏提示。    |   0   |
|   glow(boolean)    |    设置物品是否具有附魔光效。    |   0   |
|   food(Internal.Consumer_\<Internal.FoodBuilder\>)    |    设置食物属性。    |   -   |
|   rarity(Internal.Rarity_)    |    设置物品稀有度。    |   0   |
|   useAnimation(Internal.UseAnim_)    |    设置物品使用动画。    |   -   |
|   containerItem(ResourceLocation 物品id)    |    设置物品的容器，例如奶桶的容器是桶。    |   0   |
|   rarity(Internal.Rarity_)    |    设置物品稀有度。    |   "common"   |
|   burnTime(number 游戏刻)    |    设置物品在熔炉作为燃料的燃烧时间    |   0   |

:::

## 示例

::: code-group

```js [KubeJS]
StartupEvents.registry('minecraft:item', event => {
    event.create('kubejs:item', 'basic')
        .burnTime(200)
})
```

:::