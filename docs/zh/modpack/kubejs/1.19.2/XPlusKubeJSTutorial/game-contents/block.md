---
authors: ['Wudji']
---


# 4 方块注册和属性修改

## 一、方块注册

### 1、事件监听

通过向`StartupEvents.registry`事件传入不同的参数，其可用于在启动脚本中注册游戏中的新元素，如方块、物品、声音、药效等，在这里，我们传入`"block"`来进行与方块有关的修改：

```js
StartupEvents.registry("block",event=>{
    // 脚本
})
```

显然，方块注册脚本应该放于StartupEvents脚本文件夹中。

### 2、事件方法

| 方法                               | 描述     | 返回值       |
| ---------------------------------- | -------- | ------------ |
| create(字符串 方块ID, 字符串 类型) | 注册方块 | BlockBuilder |
| create(字符串 方块ID)              | 注册方块 | BlockBuilder |

其中，“类型”参数支持以下字符串：

| 值                        | 描述           | 备注                                   |
| ------------------------- | -------------- | -------------------------------------- |
| "basic"                   | 基础方块类型   | -                                      |
| "detector"                | 检测方块       | 使用说明详见后文detectorId(id: string) |
| "slab"                    | 台阶方块       | 预设模型                               |
| "stairs"                  | 楼梯方块       | 预设模型                               |
| "fence"                   | 栅栏方块       | 预设模型                               |
| "fence\_gate"             | 栅栏门方块     | 预设模型                               |
| "wall"                    | 墙方块         | 预设模型                               |
| "wooden\_pressure\_plate" | 木质压力板     | 预设模型和红石特性                     |
| "stone\_pressure\_plate"  | 石质压力板     | 预设模型和红石特性                     |
| "wooden\_button"          | 木质按钮       | 预设模型，红石特性，其物品可用作燃料   |
| "stone\_button"           | 石质按钮       | 预设模型，红石特性，其物品不可用作燃料 |
| "falling"                 | 下坠方块       | -                                      |
| "crop"                    | 农作物方块     | 使用说明详见后文                       |
| "cardinal"                | 黛玉朝向的方块 | 例如讲桌、熔炉方块等                   |

通过设定自定义方块类型，你可以便捷地指定其模型或为其添加特殊功能。

### 3、BlockBuilder

| 方法                                                                             | 描述                     | 备注                                             | 对应属性默认值 |
| -------------------------------------------------------------------------------- | ------------------------ | ------------------------------------------------ | -------------- |
| **🧾 方块属性类**                                                                 | -                        | -                                                | -              |
| property(BlockProperties 属性)                                                   | 设置方块属性             | 🔎 形如`BlockProperties.WATERLOGGED`              | -              |
| redstoneConductor(布尔值 b)                                                      | 是否为红石信号导体       | -                                                | true           |
| displayName(字符串 名称)                                                         | 设置方块名称             | -                                                | -              |
| tagBoth(命名空间 标签)                                                           | 为物品和方块同时添加标签 | -                                                | -              |
| tagBlock(命名空间 标签)                                                          | 只为方块添加标签         | -                                                | -              |
| tagItem(命名空间 标签)                                                           | 只为物品添加标签         | -                                                | -              |
| unbreakable()                                                                    | 设置为无法被破坏         | 将hardness设为-1，resistance设为Float.MAX\_VALUE | -              |
| resistance(float 抗性)                                                           | 设置方块抗性             | -                                                | 3F             |
| hardness(float 硬度)                                                             |                          |                                                  |                |
| material(MaterialJS 材质)                                                        | 设置方块材质             | \[1]                                             | "wood"         |
| randomTick(RandomTickCallbackJS 随机刻回调函数)                                  | 设置随机刻事件           | \[2]                                             | -              |
| lightLevel(float 强度)                                                           | 设置方块光照等级         | -                                                | 0F             |
| noDrops()                                                                        | 设置为破坏时不掉落自身   | 默认为掉落自身                                   | -              |
| noItem()                                                                         | 设置为不注册方块对应物品 | -                                                | -              |
| waterlogged()                                                                    | 设置为含水方块           | -                                                | false          |
| jumpFactor(float 倍率)                                                           | 设置跳跃高度倍率         | -                                                | 1.0F           |
| speedFactor(float 倍率)                                                          | 设置速度倍率             | -                                                | 1.0F           |
| slipperiness(float f)                                                            | 设置滑动倍率(μ)          | -                                                | 0.6F           |
| requiresTool(布尔值 f)                                                           | 设置是否需要对应工具破坏 | -                                                | false          |
| noValidSpawns(布尔值 b)                                                          | 设置是否可以生成怪物     | -                                                | -              |
| **🎞 方块外形类**                                                                 | -                        | -                                                | -              |
| box(double x0,double y0,double z0, double x1,double y1,double z1,布尔值 scale16) | 设置方块碰撞箱           | \[3]                                             | -              |
| noCollision()                                                                    | 设置为无碰撞箱           | -                                                | -              |
| defaultTranslucent()                                                             | 默认半透明渲染方式       | 详见`4、方块渲染方式`                            | -              |
| defaultCutout()                                                                  | 默认镂刻渲染方式         | 详见`4、方块渲染方式`                            |                |
| model(命名空间 模型目录)                                                         | 设置模型                 | 形如"kubejs:blocks/wudji\_notfound"              | null           |
| viewBlocking(布尔值 b)                                                           | -                        | 详见`4、方块渲染方式`                            | true           |
| notSolid()                                                                       | 设置为非固体方块         | 详见`4、方块渲染方式`                            | false          |
| texture(字符串 id,字符串 材质)                                                   | 手动设置方块材质         | \[4]                                             | -              |
| renderType(字符串 渲染类型)                                                      | 设置方块渲染类型         | \[5]                                             | "basic"        |

\[1]

```
以下为 MaterialJS 支持的值（Minecraft 1.19.4）：
"grass" | "spore_blossom" | "dripstone" | "slime" | "berry_bush" | "ice" | "gilded_blackstone" | "small_amethyst_bud" | "amethyst_cluster" | "mud" | "amethyst" | "dragon_egg" | "packed_mud" | "crop" | "anvil" | "dirt" | "nether_sprouts" | "powder_snow" | "air" | "pointed_dripstone" | "muddy_mangrove_roots" | "lava" | "chain" | "sculk_sensor" | "leaves" | "clay" | "netherrack" | "medium_amethyst_bud" | "basalt" | "portal" | "mud_bricks" | "soul_soil" | "mangrove_roots" | "big_dripleaf" | "sculk_catalyst" | "bone" | "vine" | "web" | "polished_deepslate" | "coral" | "weeping_vines" | "plant" | "sculk_shrieker" | "large_amethyst_bud" | "explosive" | "copper" | "roots" | "ancient_debris" | "netherite" | "snow" | "moss_carpet" | "sculk_vein" | "stone" | "sculk" | "glow_lichen" | "hanging_roots" | "cake" | "nether_wart" | MaterialJS | "froglight" | "honey" | "small_dripleaf" | "kelp" | "nether_ore" | "sand" | "frogspawn" | "water" | "glass" | "azalea_leaves" | "tuff" | "metal" | "rooted_dirt" | "soul_sand" | "moss" | "deepslate" | "cave_vines" | "twisting_vines" | "deepslate_bricks" | "nylium" | "vegetable" | "azalea" | "scaffolding" | "flowering_azalea" | "sponge" | "lodestone" | "nether_bricks" | "lantern" | "candle" | "sea_grass" | "calcite" | "wart_block" | "nether_gold_ore" | "bamboo_sapling" | "wool" | "deepslate_tiles" | "bamboo" | "shroomlight" | "wood" | "hard_crop"
```

\[2]RandomTickCallbackJS 支持以下方法：

| 方法        | 描述         | 返回值          |
| ----------- | ------------ | --------------- |
| getLevel()  | 获取当前世界 | Level           |
| getServer() | 获取服务器   | MinecraftServer |

具体使用方法见后文示例。

\[3]

```
box(x0, y0, z0, x1, y1, z1, true) // 设置方块碰撞箱(0~16)，默认值为0,0,0,16,16,16,true

box(x0, y0, z0, x1, y1, z1, false) // 设置方块碰撞箱(0~1)，默认值为0,0,0,1,1,1,false
```

\[4]

```
常用ID有"particle"（粒子）和"all"（全部）。材质字符串 形如 "minecraft:block/tnt_bottom"
```

\[5]

```
其支持值为：solid（实心）, cutout（镂刻）, translucent（半透明）
```

### 4、方块渲染方式

在KubeJS中，常用`defaultTranslucent()`和`defaultCutout()`来设定半透明和镂刻方块渲染。如下图，从左到右设置的选项分别为`defaultTranslucent()`、`defaultCutout()`、`defaultCutout()`和`未设置渲染方式`设置：

![](https://m1.miaomc.cn/uploads/20230417\_643cda7740387.png)

给出常见情况：**对于玻璃等半透明方块，应使用`defaultTranslucent()`进行设置；对于讲台等方块，应使用`defaultCutout()`进行设置**

从KubeJS源代码可以看出，[`defaultTranslucent()`](https://github.com/KubeJS-Mods/KubeJS/blob/a021e99f79084e4ad7f8be38ae12c3ef915f20ca/common/src/main/java/dev/latvian/mods/kubejs/block/BlockBuilder.java#L536)设置了以下选项：

```
notSolid()
noValidSpawns(true)
suffocating(false)
viewBlocking(false)
redstoneConductor(false)
transparent(true)
renderType("translucent")
```

[`defaultCutout()`](https://github.com/KubeJS-Mods/KubeJS/blob/a021e99f79084e4ad7f8be38ae12c3ef915f20ca/common/src/main/java/dev/latvian/mods/kubejs/block/BlockBuilder.java#L532)设置了以下选项

```
notSolid()
noValidSpawns(true)
suffocating(false)
viewBlocking(false)
redstoneConductor(false)
transparent(true)
renderType("cutout")
```

### 5、资源目录

对于方块注册来说，你需要将方块材质置于`assets\kubejs\textures\block\<方块ID>.png`，自定义模型（可选）放于`kubejs\assets\kubejs\models\block\<方块ID>.json`下。（即文件结构同资源包）

### 6、示例

```js
StartupEvents.registry("block",event => {
	// 使用栅栏门预设
    event.create("kubejs_custom_block_fence_gate","fence_gate").displayName("Wudji的栅栏门")
    
    // 1/8 方块
    event.create("kubejs_custom_block_box","cardinal").box(0,0,0,8,8,8).defaultCutout().displayName("0.125个方块")
    
    // 随机刻事件使用示例
    // 效果为当kubejs_custom_block_random_tick方块被随机刻选中后再原地生成一个向下砸的火球
    event.create("kubejs_custom_block_random_tick","basic").randomTick(cb =>{// RandomTickCallbackJS
        // 以下内容详见后续实体章节。
        // 创建实体
        let entity = cb.getLevel().createEntity("minecraft:fireball");

        // 设置实体NBT, 坐标, 动量
        entity.mergeFullNBT('{ExplosionPower:2}');
        entity.setPosition(cb.block.getX(),cb.block.getY(),cb.block.getZ())
        entity.setMotion(0, -20, 0);

        // 生成实体
        entity.spawn()

    }).displayName("随机刻火球方块")
})
```

## 二、方块属性修改

### 1、事件监听

你需要监听`BlockEvents.modification`来对方块属性进行修改。

```js
BlockEvents.modification(event => {
    // 在此编写代码
})
```

显然，方块属性修改脚本应该放于StartupEvents脚本文件夹中。

### 2、事件方法

| 方法                                                              | 描述         | 返回值 |
| ----------------------------------------------------------------- | ------------ | ------ |
| modify(BlockStatePredicate 方块谓词, Consumer\<Block\_> 修改事件) | 修改方块属性 | -      |

### 3、谓词

`2、事件方法`中所述第一个参数`方块谓词`可以为以下值：

| 类型     | 描述     | 示例                                               |
| -------- | -------- | -------------------------------------------------- |
| BlockTag | 方块标签 | `"forge:ores/gold"` `"minecraft:wooden_doors"`     |
| BlockID  | 方块ID   | `"minecraft:blue_shulker_box"` `"minecraft:glass"` |

### 4、修改方法

🔎 绝大多数的修改方法与方块注册中的方法类似。例如`speedFactor(float 倍率)`在方块修改中为`setSpeedFactor(float 倍率)`（详见ProbeJS）。

### 5、修改示例

```js
BlockEvents.modification(event => {
	// 滑~
    event.modify("minecraft:grass_block", event => {
        event.setSpeedFactor(5.0);
    })
})
```