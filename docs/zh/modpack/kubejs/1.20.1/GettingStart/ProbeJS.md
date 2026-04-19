---
title: 类型补全
description: ProbeJS Legacy 与普通 ProbeJS 的取舍、基础使用、类型补全与迁移说明
priority: 30
layout: doc
---

# ProbeJS 与类型补全 {#Introduction}

在KubeJS开发的任何阶段，`ProbeJS` 都是必要的辅助工具。

它做的事情很简单：把当前实例里一部分运行时信息生成编辑器的TypeScript服务器可以理解的类型补全文件，让 `VSCode` 能补全事件、对象、方法和常见片段。这样你写脚本时就不必一直靠记忆或盲猜。

`ProbeJS` 并不能解决所有 KubeJS 的基础问题。由于底层基于 {`Rhino`:JS → Java} 的编译机制，回调函数中提供的类型补全往往停留在父类层级，这种情况十分常见。相较之下，Java 在运行时对对象的实际类型（子类）通常更为明确，但即便在模组开发中，许多场景下仍需要通过 `instanceof` 等方式进行类型判断；而在 KubeJS 中，这类不确定性会更加明显。

因此，在 JS 环境下，类型信息往往不够直观，类型补全也不一定能够反映对象的真实形态。在实际编写脚本时，仍需结合日志输出、`JSDoc`、源码阅读以及实际测试，对行为与数据结构进行验证，而不能完全依赖类型补全。

## 它主要能做些什么 {#WhatProbeJSDoes}

- 给事件、对象与方法提供补全。
- 让你看到参数、返回值与部分重载信息。
- 允许你通过 `Ctrl / Cmd + 左键` 跳到类型文件继续看结构。
- 提供 `@item`、`@block`、`@entity` 这类常见{片段:Snippets}与补全。

对 KubeJS 来说，这个价值不只是“少打几个字”，而是让你先看清再下笔。很多脚本问题不是逻辑太难，而是对象结构、脚本侧别或方法边界一开始就没确认清楚。

## 在 1.20.1 中应当如何选择 ProbeJS {#Version}

就本仓库当前的 `1.20.1` 文档和示例而言，默认优先参考的是 [`ProbeJS Legacy`](https://www.curseforge.com/minecraft/mc-mods/probejs-legacy)。

普通 [`ProbeJS`](https://www.curseforge.com/minecraft/mc-mods/probejs) 在旧资料、旧截图和一部分旧示例里仍然很常见，所以你阅读历史内容时仍会碰到它。但如果你现在是新建 `1.20.1` 工作区，建议先按本仓库当前的默认参考来。

可以把两者的关系简单理解成：

- `ProbeJS Legacy`：本仓库当前 `1.20.1` 内容的默认参考。
- 普通 `ProbeJS`：旧资料与迁移场景里经常会遇到的另一条版本线。

::: flat
这里说的是本仓库当前文档的参考口径，不代表所有整合包、所有旧教程都完全一致。读资料时，先看版本再看写法。
:::

## 第一次生成类型文件 {#FirstDump}

1. 在实例中安装 `KubeJS`、其前置以及你准备实际使用的 `ProbeJS`。
2. 为 `VSCode` 安装 `ProbeJS` 扩展：`Prunoideae.probejs`。
3. 启动游戏，并进入任意一个世界。
4. 在游戏内执行 `/probejs dump`。
5. 使用 `VSCode` 打开实例根目录，也就是 `kubejs` 的上级目录，而不是只打开某一个脚本文件。

`/probejs dump` 读取的是当前已经加载完成的实例状态。模组列表变了、版本换了、补全明显不对了，都应该重新导出一次。

::: flat
若导出后补全仍未刷新，可以在 `VSCode` 中按 `F1`，执行 `TypeScript: Restart TS server`。这一步不会修改脚本，只是强制编辑器重新索引类型文件。
:::

## 日常怎么用 {#HowToUse}

起步阶段，先把下面三件事用熟就够了。

### 1. 先看 `event` 里到底有什么 {#ObserveTypes}

当你编写如下代码时：

```js
ItemEvents.firstLeftClicked(event => {
    event.player.
             // ^ 会冒出很多个函数候选
})
```

先别急着把逻辑写完。先看补全结果，再对类型或方法使用 `Ctrl / Cmd + 左键` 继续追进去。对 `event.player`、`event.level`、`event.block` 这类对象，先确认它们到底是什么，再决定后面的写法。

### 2. 善用类型补全 {#Completion}

对于 `ServerEvents.recipes`、`BlockEvents.rightClicked`、`StartupEvents.registry` 这类 API，最常见的问题往往不是逻辑本身复杂，而是名称写错、参数传错、重载没分清。ProbeJS 会把这部分低级错误尽量提前暴露给编辑器：

- 在空白区域输入部分前缀后，编辑器会列出对应的事件。
- 在一些上下文例如`event.player`后输入 `.` 后，将能够查看该对象下的全部可用函数与字段。
- 在需要传入物品、方块、实体、维度等标识符时，编辑器能够基于类型信息和ProbeJS提供的`Special.Objects`类型文件提供更合适的补全候选。

这样做能把精力留给真正的逻辑问题，但是存在着许多问题，将会在后续章节中详述。

### 3. 使用 Snippet 快速插入常见内容 {#Snippets}

ProbeJS 还会为编辑器准备一组常用的片段补全。以常见的使用方式来说，输入 `@item`、`@block`、`@entity`、`@dimension` 等前缀后，编辑器通常会提供相应的候选内容，用来快速插入具体的 ID 或片段结构。

甚至对于资源文件和语言键都提供了`textures`和`langKey`，让使用者的开发更加方便。

## 为什么有了 ProbeJS，仍然需要 JSDoc {#WhyStillNeedJSDoc}

ProbeJS 很有用，但它给出的类型并不总是够窄。

最常见的情况是：运行时对象其实更具体，但编辑器只能先把它当成一个父类。例如某个位置实际是 `ServerPlayer`，补全里却只先显示成 `Player`。这时就需要 `JSDoc` 主动把类型收紧。

例如：

```js
// server_scripts/test.js
ItemEvents.firstLeftClicked(event => {
    /**
     * @type {Internal.ServerPlayer}
     */
    let player = event.player

    player.showActionbar(Text.green('Hello World'))
})
```

这段注释不会改变运行时对象，它只是告诉编辑器：这里按 `Internal.ServerPlayer` 来理解。这样原本补不出来的方法才会重新出现。如果你发现代码中的某个实体没有`药水效果`和`属性相关`的函数与字段，试试给它`Internal.LivingEntity`的@type后再尝试补全函数。

如果你遇到“运行时确实有这个方法，但编辑器补不出来”的情况，先怀疑类型是否过宽，而不是立即认定 ProbeJS 完全失效。更深入的写法与使用场景，可以继续参阅 [JSDoc 文档](../Addon/ProbeJS/JSDoc)。

## 补全不对时先查什么 {#Migration}

很多补全异常不是脚本写错，而是旧类型文件没清干净。尤其是同一个实例切过不同 `ProbeJS` 版本时，这个问题很常见。

出现下面这些现象时，应优先考虑清理旧文件并重新导出：

- 编辑器能够补全部分 API，但结果明显不符合当前模组环境。
- 某些类型跳转进入了旧版本文件，或名称与当前文档不一致。
- 已重新执行 `/probejs dump`，但补全结果几乎没有变化。
- 同一个实例在阅读旧教程时换过不同 ProbeJS 版本，之后补全开始出现互相混杂的痕迹。

处理方式通常是：

1. 关闭 `VSCode` 或至少关闭当前工作区。
2. 删除实例目录中旧的类型文件与 `jsconfig.json`。
3. 若你曾使用过 `ProbeJS 7.x`，一并检查并清理 `/.probe/` 下的旧输出。
4. 若你此前换过不同的 `ProbeJS` 结构或版本，也一并清理对应的旧类型文件，避免与当前输出并存。
5. 重新进入世界，执行 `/probejs dump`。
6. 重新打开实例根目录，并在必要时执行 `TypeScript: Restart TS server`。

这一步可以让当前工作区只保留与**当前所选 ProbeJS 版本**对应的一套类型输出，避免不同代际的结构混杂在同一个实例中。
