---
title: 什么是KubeJS
description: KubeJS的介绍、优劣与学习路线
hidden: false
priority: 10
---

# 什么是KubeJS {#Introduction}

**KubeJS**是一款基于[Rhino](https://github.com/KubeJS-Mods/Rhino)引擎，允许玩家使用 JavaScript 编写脚本的老牌 魔改 模组。它易于上手且支持修改的内容广泛，拥有良好的附属模组生态。通过事件驱动，它能轻松实现以往需要复杂数据包才能达成的效果，并且能够**直接导入原版和其他模组的Java类**来进行深度开发。

::: flat 
由于该模组基于定制版 Rhino，而非现代化的 JS-to-Java 引擎如Graal，KubeJS 在运行时会受到一定的语法限制并已发现存在方法重载的参数问题。此外，模组内部也存在一些历史遗留问题，短期内基本不会得到官方优化或修复。
:::

截止该文档开始编写的`2026/4/18`，该模组在[CurseForge](https://www.curseforge.com/minecraft/mc-mods/kubejs)已斩获138,759,626下载量，其中当前的文档分支所维护的1.20.1版本有着31.5M的下载量，足见其热门程度。

不严谨地说，KubeJS 的脚本基于++事件驱动++，但在实际使用中并不局限于通过事件实现所有功能，应根据具体 API 灵活选择实现方式。通常，一个由 KubeJS 提供的事件大致如下所示：

```js
PlayerEvents.loggedIn((event) => {
    /**
     * @type {Internal.ServerPlayer}
     */
    let player = event.player;
    player.showActionbar(Text.green('Hello, World! (已登录)'))
})
```

使用该脚本，玩家会成功进入世界后，在[动作栏](https://zh.minecraft.wiki/w/%E5%8A%A8%E4%BD%9C%E6%A0%8F)显示`Hello, World! (已登录)`。通过这一简单示例，你应该已经能够初步理解 KubeJS 的运行机制及其基本的编写方式。

如果在阅读上述示例后仍无法理解，也无需担心。本文档将系统列出 KubeJS 各类事件的参数与相关信息，并结合示例进行说明，帮助你逐步建立清晰的认知。

## 你可以使用KubeJS做些什么 {#WhatYouCouldDo}

在 `1.20.1` 中，KubeJS 已经覆盖了启动/初始化注册、服务端逻辑与客户端表现这 三个层面。

在常见的使用场景里，它通常被拿来处理下面这些内容：

- 在启动阶段注册物品、方块、药水效果等内容。
- 在服务端修改配方、标签、掉落、玩家行为、实体行为与世界逻辑。
- 在客户端处理物品的:::dialog#tooltip 工具提示:::、部分表现层交互，以及 KubeJS 自带的 Paint API绘制功能。
- 在需要时直接调用 Java 类，继续访问原版、Forge 以及其他模组暴露出的类型与接口。

@@@ dialog-def#tooltip {"title": "工具提示", "width": 1200}

使用客户端的`ItemEvents.tooltip`例如下方代码：

```js
ItemEvents.tooltip(event => {
    event.addAdvanced('create: goggles', (item, addvance, text) => {
        if (item.nbt.get('alchemist')) {
            let lvl = item.nbt.alchemist
            if (lvl === 0.0) {
                text[0] = Text.translatable('item.kubejs.apprentice_goggles')
                if (event.shift) {
                    text.add('能够查看炼金材料的大致属性')
                }
            } else if (lvl === 1.0) {
                text[0] = Text.translatable('item.kubejs.alchemist_goggles')
                if (event.shift) {
                    text.add('能够查看炼金材料的具体属性')
                }
            }
        }
    })
})
```

你将可以轻松创建这样的工具提示：

![Tooltip](/imgs/zh/kubejs/1.20.1/GettingStart/tooltip1.png)

![Tooltip](/imgs/zh/kubejs/1.20.1/GettingStart/tooltip2.png)

@@@


::: warning 警告
进阶内容主要基于 Forge 端的 KubeJS。若你使用 Fabric，请谨慎对待后续文档中的实现方式与结论。
:::

::: elevated
因此，KubeJS 更适合处理那些单靠配置难以完成、但又未必需要直接编写 Java 模组的整合包逻辑。!!虽然也有不少使用 `unsafe` 和 `adapter` 的巨魔科技。!!
:::

## 为什么很多整合包会使用它 {#WhyKubeJS}

如果仅需调整模组的既定行为，配置文件或数据包通常已经足够；而当需求涉及更底层能力，或原版/模组未提供对应接口时，往往仍需要进行模组开发。KubeJS 则处于两者之间。

它并非简单的“轻量版模组开发”，而更像是一种面向整合包的“运行时脚本”方案：既可以联动多个模组内容，又便于快速验证机制，同时还能在不脱离现有模组生态的前提下，补足配置层难以实现的功能。

也正因如此，当需求同时涉及{注册:Registry}、{交互:Interaction}、{配方:Recipe}、{掉落物:Loottable}、{实体:Entity}或{跨模组联动:LoadClass}等多个方面时，KubeJS 往往可以作为实现这些需求的主要手段，用于完成魔改、整合以及整合包内容的组织与扩展。

例如：

```js
// 注册一个自定义物品 kubejs:ritual_core
StartupEvents.registry('item', event => {
    event.create('ritual_core').displayName('仪式核心')
})

// 让其可通过合成获取
ServerEvents.recipes(event => {
    event.remove({ output: 'minecraft:beacon' })
    event.shaped('kubejs:ritual_core', [
        'ABA',
        'CDC',
        'AEA'
    ], {
        A: 'minecraft:amethyst_shard',
        B: 'minecraft:nether_star',
        C: 'minecraft:gold_ingot',
        D: 'minecraft:diamond',
        E: 'minecraft:ender_eye'
    })
})

// 监听交互并执行逻辑
BlockEvents.rightClicked('minecraft:lodestone', event => {
    const player = event.player
    const item = player.mainHandItem

    if (event.level.isClientSide()) return
    if (event.hand != "MAIN_HAND") return
    if (item.id !== 'kubejs:ritual_core') return

    item.count--
    event.block.popItem('minecraft:echo_shard')

    const entity = event.level.createEntity('minecraft:warden')
    entity.setPosition(event.block.pos.x, event.block.pos.y + 1, event.block.pos.z)
    entity.spawn()
})
```

这一段真实的KubeJS代码假设了整合包希望新增一种“仪式核心”：玩家右键特定方块后消耗物品，召唤实体，并根据条件掉落自定义奖励。这类需求通常会同时牵涉物品注册、交互监听、配方移除或新增、实体生成，以及战利品或奖励逻辑的改写，而这正是 KubeJS 擅长处理的范围。

而如果将这一需求抽象成更完整的实现流程，它通常会更接近下面这样的结构：

```md
移除原有配方并添加新的获取方式

当玩家右键特定方块时：
    检查手中物品是否为仪式物品
    检查维度、时间、周围方块或队伍条件
    消耗物品并播放粒子/音效
    生成指定实体
    记录一次特殊状态或进度

当该实体被击败时：
    根据玩家状态、阶段或概率
    掉落额外战利品
    解锁下一阶段内容
```

## 如何开始学习 {#HowToGetStart}

不论你是否已经对 KubeJS 的功能与局限有了一定了解，该文档设计的学习路线都将围绕**通过 KubeJS 修改行为逻辑并进行深度魔改**这一目的展开教学。

**在实际编写脚本的过程中，核心通常集中在两点：==理解 与 测试==。**

其中，“理解”意味着你需要逐步建立对 Minecraft 运行机制乃至模组开发方式的认知，这本身并不是一件可以一蹴而就的事情；而“测试”则是整个学习过程中最直接、也最有效的手段。因此，在文档的前期，尤其是起步阶段，会更多聚焦于`测试 / Debugging`。

::: plain
通过合理使用 Debug，你可以确认事件中实际传递的数据结构，观察函数调用前后的状态变化，验证逻辑是否按预期执行，并及时发现潜在的问题，从而避免在复杂逻辑中反复试错或产生难以定位的错误。
:::

所以，在`起步`章节中，文档将内容划分为六个模块：

- 介绍（即本页面）
- 脚本{编写:Vscode}环境与脚本运行环境
- ProbeJS 与类型补全
- 你的第一个脚本文件
- 如何测试与调试
- 进阶内容粗览
- KubeJS与Rhino的限制

其中，前三部分主要聚焦于正式编写脚本前需要了解的基础概念与使用规则，解决工具配置与类型补全等最基础的问题。随后，文档将逐步引入事件结构，结合实例讲解常见函数与参数，并通过类型补全相关内容，引导你实际应用这些信息，完成你的第一个脚本编写。

在此基础上，后续内容将详细介绍如何使用`console`进行日志输出与调试，帮助你掌握分析函数行为与数据流的基本方法，从而在后续开发中逐步摆脱对社区经验的完全依赖。最后，作为起步章节的收束部分，将带你初步接触一些更进阶的功能!!巨魔科技!!，以及通过深入理解与实验（包括部分非官方、甚至被官方限制的模块）所实现的高级效果。

*[Debug]: 通过观察运行时数据与执行结果，验证脚本逻辑并定位问题的过程。

*[三个层面]: `ScriptType` 明确区分 `startup_scripts`、`server_scripts` 与 `client_scripts`，而 API 参考也给出了这些脚本阶段分别对应的事件组与职责范围。

*[魔改]: 使用配置文件、数据包、脚本甚至开发新的模组来对原版与其他模组的行为进行定制与修改。