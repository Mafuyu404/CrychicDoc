---
title: 箱子
hidden: false
priority: 200
---
# 箱子类型战利品表

## 操作战利品表

-   事件：ServerEvents.chestLootTables(event => \{\});

::: code-group

```js [KubeJS修改原战利品表]
ServerEvents.chestLootTables((event) => {
    // 修改原战利品表
    event.modify("minecraft:end_city_treasure", (loot) => {
        loot.addPool((pool) => {
            // 添加战利品
            pool.addItem("minecraft:diamond"); // [!code ++]
        });
    });
});
```

```js [KubeJS覆盖原战利品表]
ServerEvents.chestLootTables((event) => {
    // 创建战利品表，但因为id(ResourceLocation)和原战利品表一模一样，因此覆盖了原战利品表
    event.addChest("minecraft:end_city_treasure", (loot) => {
        loot.addPool((pool) => {
            // 添加战利品
            pool.addItem("minecraft:diamond"); // [!code ++]
        });
    });
});
```

```js [KubeJS带有谓词与修饰器的]
ServerEvents.chestLootTables((event) => {
    event.addChest("minecraft:end_city_treasure", (loot) => {
        // [!code ++]
        loot.addPool((pool) => {
            // [!code ++]
            // 添加战利品
            pool.addItem("minecraft:diamond"); // [!code ++]
            // 为战利品添加有条件的物品修饰器
            pool.addItem("minecraft:diamond").addConditionalFunction((c) =>
                c.name(Component.aqua("测试钻石"))
            ); // [!code ++]
            // 为战利品池添加有条件的物品修饰器
            pool.addConditionalFunction((c) =>
                c.name(Component.aqua("测试钻石"))
            ); // [!code ++]
        });

        loot.addPool((pool) => {
            // [!code ++]
            pool.addItem("minecraft:diamond"); // [!code ++]
            // 为战利品添加谓词
            pool.addItem("minecraft:diamond").survivesExplosion(); // [!code ++]
            // 为战利品池添加谓词
            pool.survivesExplosion(); // [!code ++]
        });
        // 为战利品表添加有条件的物品修饰器
        loot.addConditionalFunction((c) => c.name(Component.aqua("测试钻石"))); // [!code ++]
    });
});
```

:::

## 可用谓词

-   箱子类型战利品表上下文可用的谓词。

|   谓词类型   |                                                作用                                                 |           语句           | KubeJS 原生支持 |                        示例                         |
| :----------: | :-------------------------------------------------------------------------------------------------: | :----------------------: | :-------------: | :-------------------------------------------------: |
|     全部     |             评估一系列战利品表谓词，若它们都通过检查，则评估通过。可从任何上下文调用。              |            -             |        ☐        |     [示例](../BasicKnowledge/Predicate.md#全部)     |
|     任何     |          评估一系列战利品表谓词，若其中任意一个通过检查，则评估通过。可从任何上下文调用。           |            -             |        ☐        |     [示例](../BasicKnowledge/Predicate.md#任何)     |
|   实体属性   |                          检查战利品表上下文中的实体。可从任何上下文调用。                           | entityProperties(..args) |        ☑        |   [示例](../BasicKnowledge/Predicate.md#实体属性)   |
|   实体分数   |                                       检查实体的记分板分数。                                        |   entityScores(..args)   |        ☑        |   [示例](../BasicKnowledge/Predicate.md#实体分数)   |
|  取反（非）  |                          定义一个谓词列表，当内含谓词不通过时该谓词通过。                           |            -             |        ☐        |     [示例](../BasicKnowledge/Predicate.md#取反)     |
|   检查位置   |              检查当前位置。需要战利品上下文提供的来源进行检测，若未提供则总是不通过。               |            -             |        ☐        |   [示例](../BasicKnowledge/Predicate.md#检查位置)   |
|   随机概率   |        生成一个取值范围为 0.0–1.0 之间的随机数，并检查其是否小于指定值。可从任何上下文调用。        |   randomChance(..args)   |        ☑        |   [示例](../BasicKnowledge/Predicate.md#随机概率)   |
| 引用谓词文件 |                           调用谓词文件并返回其结果。可从任何上下文调用。                            |            -             |        ☐        | [示例](../BasicKnowledge/Predicate.md#引用谓词文件) |
|   检查时间   | 将当前的游戏时间（更确切地来说，为 24000 \* 天数 + 当天时间）和给定值进行比较。可从任何上下文调用。 |            -             |        ☐        |   [示例](../BasicKnowledge/Predicate.md#检查时间)   |
|    检查值    |                       将一个数与另一个数或范围进行比较。可从任何上下文调用。                        |            -             |        ☐        |    [示例](../BasicKnowledge/Predicate.md#检查值)    |
|   检查天气   |                            检查当前游戏的天气状态。可从任何上下文调用。                             |            -             |        ☐        |   [示例](../BasicKnowledge/Predicate.md#检查天气)   |

## 可用物品修饰器

-   箱子类型战利品表上下文可用的物品修饰器。

|        物品修饰器类型        |                                                                        作用                                                                         |           语句            | KubeJS 原生支持 |                                  示例                                  |
| :--------------------------: | :-------------------------------------------------------------------------------------------------------------------------------------------------: | :-----------------------: | :-------------: | :--------------------------------------------------------------------: |
|           复制 NBT           |                                              将 NBT 从指定源复制到项目上。唯一允许的值是"block_entity"                                              |             -             |        ☐        |           [示例](../BasicKnowledge/ItemModifier.md#复制nbt)            |
|           随机附魔           |                                                  为物品附上一个随机的魔咒。魔咒的等级也是随机的。                                                   |  enchantRandomly(..args)  |        ☑        |           [示例](../BasicKnowledge/ItemModifier.md#随机附魔)           |
| 给予等价于经验等级的随机魔咒 |                                       使用指定的魔咒等级附魔物品（大约等效于使用这个等级的附魔台附魔物品）。                                        | enchantWithLevels(..args) |        ☑        | [示例](../BasicKnowledge/ItemModifier.md#给予等价于经验等级的随机魔咒) |
|        设置探险家地图        |                             将普通的地图物品变为一个指引到某个结构标签的探险家地图。如果物品不是地图，则不做任何处理。                              |             -             |        ☐        |        [示例](../BasicKnowledge/ItemModifier.md#设置探险家地图)        |
|           爆炸损耗           | 如果物品栈是因为方块被爆炸破坏而产生，执行该函数的每个物品有 1/爆炸半径的概率消失，物品栈会被分为多个单独的物品计算；否则此物品修饰器不做任何处理。 |             -             |        ☐        |           [示例](../BasicKnowledge/ItemModifier.md#爆炸损耗)           |
|         填充玩家头颅         |                                        将玩家头颅设置为指定玩家的头颅。如果物品不是玩家头颅则不做任何处理。                                         |             -             |        ☐        |         [示例](../BasicKnowledge/ItemModifier.md#填充玩家头颅)         |
|           熔炉熔炼           |                                       将物品转变为用熔炉烧炼后的对应物品。如果物品不可烧炼，则不做任何处理。                                        |      furnaceSmelt()       |        ☑        |           [示例](../BasicKnowledge/ItemModifier.md#熔炉熔炼)           |
|        限制物品栈数量        |                                                                   限制物品数量。                                                                    |             -             |        ☐        |        [示例](../BasicKnowledge/ItemModifier.md#限制物品栈数量)        |
|      引用物品修饰器文件      |                                                               引用另一个物品修饰器。                                                                |             -             |        ☐        |        [示例](../BasicKnowledge/ItemModifier.md#引用物品修饰器)        |
|           设置属性           |                                                               为物品加上属性修饰符。                                                                |             -             |        ☐        |           [示例](../BasicKnowledge/ItemModifier.md#设置属性)           |
|         设置旗帜图案         |                                           设置旗帜物品的图案。如果物品不是旗帜，则此修饰器不做任何处理。                                            |             -             |        ☐        |         [示例](../BasicKnowledge/ItemModifier.md#设置旗帜图案)         |
|          设置内容物          |                                                                 设置物品的内容物。                                                                  |             -             |        ☐        |          [示例](../BasicKnowledge/ItemModifier.md#设置内容物)          |
|         设置物品数量         |                                                                 设置该物品的数量。                                                                  |       count(..args)       |        ☑        |         [示例](../BasicKnowledge/ItemModifier.md#设置物品数量)         |
|          设置损伤值          |                                                                 设置工具的损坏值。                                                                  |      damage(..args)       |        ☑        |          [示例](../BasicKnowledge/ItemModifier.md#设置损伤值)          |
|           设置魔咒           |                                                                  设置物品的魔咒。                                                                   |             -             |        ☐        |           [示例](../BasicKnowledge/ItemModifier.md#设置魔咒)           |
|           设置乐器           |                                                设置山羊角的种类。如果物品不是山羊角则不做任何处理。                                                 |             -             |        ☐        |           [示例](../BasicKnowledge/ItemModifier.md#设置乐器)           |
|         设置战利品表         |                                                          为一个容器方块物品设定战利品表。                                                           |             -             |        ☐        |         [示例](../BasicKnowledge/ItemModifier.md#设置战利品表)         |
|         设置物品描述         |                                                                为物品添加描述信息。                                                                 |             -             |        ☐        |         [示例](../BasicKnowledge/ItemModifier.md#设置物品描述)         |
|          设置物品名          |                                                            添加或修改物品的自定义名称。                                                             |       name(..args)        |        ☑        |          [示例](../BasicKnowledge/ItemModifier.md#设置物品名)          |
|           设置 NBT           |                                                                设置物品栈 NBT 数据。                                                                |        nbt(..args)        |        ☐        |           [示例](../BasicKnowledge/ItemModifier.md#设置nbt)            |
|           设置药水           |                                                            设置物品包含的药水效果标签。                                                             |             -             |        ☐        |           [示例](../BasicKnowledge/ItemModifier.md#设置药水)           |
|       设置迷之炖菜效果       |                                                              为谜之炖菜添加状态效果。                                                               |             -             |        ☐        |     [示例](../BasicKnowledge/ItemModifier.md#设置迷之炖菜状态效果)     |
