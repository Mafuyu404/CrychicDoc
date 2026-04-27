---
progress: 50
state: unfinished
title: 方块注册
description: KubeJS 中方块注册的常用方法，涵盖碰撞箱、方块状态、硬度、抗性、摩擦、亮度、挖掘属性等内容
authors:
  - sdjge
hidden: false
priority: 0
layout: doc
---

# 方块注册 {#Register}

## 概述 {#Overview}

本页汇总 KubeJS 中方块注册的常用方法，涵盖方块的物理属性、方块状态、渲染类型、交互回调，以及额外方块实体与自定义属性的注册方式。

方块注册在 `startup_scripts` 中进行，更改后通常需要重启游戏才能生效。

## 基础创建 {#Basic}

在 `startup_scripts` 中新建一个脚本文件，写入以下内容：

```js
StartupEvents.registry("block", event => {
    // 后续所有注册代码均在此回调内编写
})
```

使用 `event.create()` 注册一个方块：

```js
event.create("example_block")
```

`create()` 的第一个参数为方块 ID。若需要指定命名空间，在 ID 中直接注明：

```js
event.create("baka9:example_block")
```

第二个参数可选，用于指定预设方块类型：

```js
event.create("example_block", "slab")
```

`create()` 返回的是一个 Builder 对象，你可以继续链式调用其方法来定制方块的各项属性。

## 常用属性 {#Properties}

```js
event.create("example_block")
    .lightLevel(15)
    .noCollision()
    .hardness(3.0)
    .resistance(6.0)
```

这会注册一个 `kubejs:example_block`，发光等级 15，无碰撞箱，硬度 3.0，防爆 6.0。

下面是所有可用的 Builder 方法汇总：

| 方法 | 说明 |
| :--- | :--- |
| `afterFallenOn` | 实体落在方块上**之后**的回调 |
| `blockEntity` | 为方块注册方块实体 |
| `bounciness` | 弹性系数，不可为负 |
| `box` | 碰撞箱形状，可多次调用以构建复杂碰撞箱 |
| `canBeReplaced` | 是否可被替换，类似高草丛 |
| `canBeWaterlogged` | 是否可以含水，类似台阶 |
| `color` | 方块颜色染色。单层时传入 16 进制颜色值；多层时第一个参数为层索引，第二个参数为颜色 |
| `createProperties` | 直接传入一个 `BlockBehaviour.Properties` 对象（进阶用法） |
| `createShape` | 自定义 `VoxelShape`（进阶用法） |
| `cropSoundType` | 给予方块农作物音效 |
| `defaultCutout` | 设为默认 cutout 渲染 |
| `defaultState` | 设置方块默认方块状态 |
| `defaultTranslucent` | 设为默认 translucent 渲染 |
| `displayName` | 设置英文显示名称，优先级低于语言文件 |
| `dynamicMapColor` | 传入一个函数以动态决定方块在地图上的颜色（进阶用法） |
| `exploded` | 方块被爆炸破坏**后**的回调 |
| `fallenOn` | 实体落在方块上时的回调（更早触发） |
| `fullBlock` | 底面是否为完整方块 |
| `glassSoundType` | 给予方块玻璃音效 |
| `grassSoundType` | 给予方块草音效 |
| `gravelSoundType` | 给予方块沙砾音效 |
| `hardness` | 硬度，默认 1.5；设为 -1 等同于基岩，不可破坏 |
| `instrument` | 设为音符盒乐器方块，参数为乐器 ID |
| `item` | 修改方块的物品形态 |
| `jumpFactor` | 跳跃加成系数 |
| `lightLevel` | 发光亮度等级 |
| `mapColor` | 设置方块在地图上的颜色，接受 `DyeColor` 或 `MaterialColor` |
| `material` | 已弃用（1.20.1），改用 `mapColor` 等独立属性 |
| `mirrorState` | 设置方块镜像时如何变换其方块状态 |
| `model` | 指定方块模型 |
| `noCollision` | 方块无碰撞箱 |
| `noDrops` | 方块无掉落物 |
| `noItem` | 方块无物品形态 |
| `noSoundType` | 方块无音效 |
| `noValidSpawns` | 方块上是否允许生物自然生成 |
| `notSolid` | 方块非固体方块（可穿过） |
| `opaque` | 方块不透明 |
| `placementState` | 方块被放置时的回调，可在此设置初始方块状态 |
| `property` | 添加方块状态属性（见下文「注册额外 blockstate」） |
| `randomTick` | 方块的随机刻回调 |
| `redstoneConductor` | 是否可被红石充能（决定是否会切断红石信号） |
| `renderType` | 渲染类型：`"cutout"`、`"cutout_mipped"`、`"translucent"` 或 `"basic"` |
| `requiresTool` | 是否需要特定工具才能掉落 |
| `resistance` | 防爆系数，默认 3 |
| `rightClick` | 方块被右键点击时的回调 |
| `rotateState` | 方块被旋转时如何变换其方块状态 |
| `sandSoundType` | 给予方块沙子音效 |
| `slipperiness` | 光滑系数，影响实体在方块上的滑动距离 |
| `soundType` | 音效类型，默认 `"wood"` |
| `speedFactor` | 移动速度系数：0.1–1 产生灵魂沙效果，超过 1 每 tick 加速 |
| `steppedOn` | 实体踩在方块上的回调 |
| `stoneSoundType` | 给予方块石头音效 |
| `suffocating` | 是否造成窒息伤害 |
| `tagBlock` | 给方块添加标签 |
| `tagBoth` | 给方块及对应物品同时添加标签 |
| `tagItem` | 给方块的物品形态添加标签 |
| `texture` | 按面指定纹理 |
| `textureAll` | 指定所有面的统一纹理 |
| `textureSide` | 指定某一面的纹理 |
| `transformObject` | 传入一个 `TransformType` 以设定模型变换（进阶用法） |
| `transparent` | 方块是否透明（非光学透明，指渲染层面的透明度设定） |
| `unbreakable` | 是否完全不可被破坏 |
| `viewBlocking` | 方块是否阻挡视线（影响渲染面剔除判断，进阶用法） |
| `waterlogged` | 方块是否可以含水 |
| `woodSoundType` | 给予方块木质音效 |

## 注册额外 blockstate {#CustomBlockstate}

当你需要非原版或其它模组定义的方块状态时，可以自行注册。

KubeJS 本体无法直接使用 `EnumProperty`（受限于 Rhino 对枚举的支持），如需类似原版台阶上下朝向的状态，建议直接复用已有的 `EnumProperty`。本章仅介绍 `BooleanProperty` 与 `IntegerProperty` 的使用。

```js
// 通过 Java.loadClass 获取两个对应的类
let $BooleanProperty = Java.loadClass("net.minecraft.world.level.block.state.properties.BooleanProperty");
let $IntegerProperty = Java.loadClass("net.minecraft.world.level.block.state.properties.IntegerProperty");

// 创建方块状态属性并存入变量以便复用
let TestBool = $BooleanProperty.create("test_boolean");
let TestInt = $IntegerProperty.create("test_integer", 0, 10); // 参数：名称, 下限, 上限

StartupEvents.registry("block", event => {
    event.create("example_block")
        .property(BlockProperties.HORIZONTAL_FACING) // 复用原版水平朝向属性
        .property(TestBool)                           // 自定义布尔属性
        .property(TestInt)                            // 自定义整数属性

        .placementState(place => {
            place.set(BlockProperties.HORIZONTAL_FACING, place.getHorizontalDirection().getOpposite());
            place.set(TestBool, place.getPlayer().isShiftKeyDown());
            place.set(TestInt, 1);
        })
})
```

其中：
- `$BooleanProperty.create(...)` 创建一个取值为 `true` / `false` 的方块状态。
- `$IntegerProperty.create(...)` 创建一个取值在 `[下限, 上限]` 间取整的方块状态。
- `.placementState()` 回调在方块被放入世界时执行，可用于设置初始方块状态。`place.getPlayer()` 获取放置者，`place.getHorizontalDirection()` 获取玩家水平朝向。

## 注册额外方块实体 {#BlockEntity}

当你需要方块持有数据（如 NBT 存储、容器逻辑）时，需要为它注册一个方块实体。

```js
StartupEvents.registry("block", event => {
    event.create("example_block_entity").blockEntity(e => {
        // 此时已为该方块创建了方块实体

        // 每个 server tick 执行一次（参数：延迟 tick 数, 间隔 tick 数, 回调）
        e.serverTick(1, 0, entity => {
            entity.getLevel().tell("zako")
        })
    })
})
```

`serverTick(initialDelay, period, callback)` 的参数含义：
- `initialDelay`：首次触发的延迟（tick）。
- `period`：后续触发的间隔（tick），设为 `0` 表示每 tick 执行一次。
- `callback`：回调函数，参数为当前的方块实体对象。

## rightClick：在注册阶段定义右键行为 {#RightClick}

::: alert {"type":"info","title":"阅读前提","variant":"outlined","border":"start"}
本节涉及客户端-服务端两侧的预测与同步机制。如果你尚未理解客户端与服务端的职责差异，建议先阅读[编程与脚本环境](../../GettingStart/Environment.md#PredictionMismatch)中「行为已取消，但表现仍然存在」一节。
:::

当你在 `server_scripts` 中通过事件监听方块的右键行为时：

```js
BlockEvents.rightClicked("kubejs:example_block", event => {
    event.player.tell("right click")
})
```

这种写法在逻辑上没有问题，但持有该方块的玩家右键时会先看到短暂的“方块被放置”的闪现，随后才恢复正常。这是因为：

1. 客户端默认认为手持方块物品右键 = 尝试放置方块，立即在本地执行了放置预测。
2. 服务端随后判断此方块有特殊交互，否决预测并发回正确的方块状态。
3. 客户端收到服务端数据后回退放置表现 —— 视觉上表现为“闪了一下”。

要避免这一问题，最干净的方式是在方块注册阶段直接声明右键行为：

```js
StartupEvents.registry("block", event => {
    event.create("example_right_click")
        .rightClick(ctx => {
            let { player, level, hand, block, item } = ctx

            // 仅处理主手、非潜行状态
            if (hand !== "MAIN_HAND" || player.isShiftKeyDown()) return

            player.tell("right click")
            player.swing()
        })
})
```

将 `.rightClick()` 写在 `startup_scripts` 的注册逻辑中，本质上是把这个行为作为方块定义的一部分告知游戏。这样一来，客户端在玩家手持该方块右键时，已经—知道—此方块存在自定义交互，就不会先执行放置预测再回退，根源上消除了视觉闪烁。

::: alert {"type":"warning","title":"注意","variant":"tonal"}
- 此处的 `ctx` 参数结构与 `BlockEvents.rightClicked` 的回调参数一致，但**不支持**调用 `cancel()`。该行为已在注册阶段声明，无需通过取消事件来阻止默认行为。
- `hand` 检查是为了确保只有主手（`"MAIN_HAND"`）点击时才响应，避免副手放置方块时重复触发。
- `player.swing()` 负责播放挥臂动画，不包含在右键回调的默认行为中，需要显式调用。
- 如果 `rightClick` 逻辑需要客户端与服务端两侧都运行，可结合 `level.isClientSide()` 进行分支判断。
:::
