---
title: 编程与脚本环境
description: IDE的选择与脚本运行环境
priority: 20
layout: doc
---

# 编程与脚本环境 {#Environment}

KubeJS 对开发所使用的工具、工作区目录的选择，以及脚本运行环境的区分都有较为明确的要求。这不仅关系到脚本能否被正确识别，也直接影响其可访问的对象、作用范围以及实际执行结果。

若对这些基础规则缺乏了解，往往会出现调用对象不符合预期、逻辑运行结果异常，或在不同环境下行为不一致等问题。因此，在正式开始编写脚本之前，先理解这些内容是十分必要的。

## VSCode {#Vscode}

想要编写 KubeJS 脚本，你需要一款能够编辑文本的工具，例如记事本、Notepad++、Atom、Sublime 或 VS Code 等。其中，==推荐使用 VS Code 进行 KubeJS 开发==，以获得更完善的功能支持与开发体验。!!孩子们不要使用记事本写代码了好么。!!

[VSCode](https://code.visualstudio.com/download) 是一款轻量且扩展性极强的 IDE（集成开发环境），在当前的开发环境中被广泛使用。对于 KubeJS 脚本编写而言，VSCode 提供了良好的编辑体验与插件生态，是推荐使用的开发工具。由于ProbeJS模组导出的`Special.Objects`和`Snippets`强依赖Vscode的相关模块，因此不建议在学习KubeJS或使用其开发时选择其他的IDE。

请参考[该教程](https://zhuanlan.zhihu.com/p/698865320)完成基础的下载与安装。对于初次接触 VSCode 的用户，建议了解其基本界面结构与常用操作（如文件管理、插件安装、终端使用等），但无需在前期投入过多精力进行复杂配置。++但建议检索一款自己心仪的主题包，以便于长期开发。++

:::: flat
::: flat
需要注意的是，VSCode 本身只是一个工具，其核心价值在于提供良好的开发环境，而非决定脚本逻辑的实现方式。因此，在初期阶段，应以“能够正常编写与运行脚本”为目标，而非追求过度复杂或个性化的编辑器配置。
:::
::::

::: alert {"type":"warning","title":"须知","variant":"outlined","density":"comfortable","border":"bottom","textColor":"rgb(207, 216, 82)"}
为了加载{中文:Chinese}语言包，请至少先阅读扩展相关内容。
:::

你可以通过[该页面](https://open-vsx.org/extension/MS-CEINTL/vscode-language-pack-zh-hans)，或直接在扩展商店搜索 `Chinese Language Pack` 来安装中文语言包。

安装完成后，按下 `Ctrl + Shift + P` 打开`命令面板`，输入 `display`，并选择 `Configure Display Language`。随后，VSCode 会列出当前已安装的语言，并标识当前使用的语言。选择目标`语言`后，即可完成界面语言的切换。

## 脚本运行环境 {#FileStructure}

为了确保这一部分内容能够顺利进行，请先确保你已经熟悉VSCode的基本使用方式，并能够理解诸如`工作区`等基础概念。

KubeJS相较于其他魔改模组，在开发体验上的一大优势在于**其通过ProbeJS提供了完善的类型补全支持**。这一能力对于理解==实例可用的函数、参数结构以及整体调用方式==具有关键作用。相关内容将在后续章节中进行更系统的讲解。

需要注意的是，要使类型补全功能正常生效，你需要将**实例文件夹作为VSCode的工作区根目录**进行打开。该目录通常位于 kubejs 文件夹的上一层（即包含 kubejs 目录的游戏实例根目录）。

通常，一个版本实例的文件结构是这样的：

<LiteTree>
log=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0Ij48ZyBmaWxsPSJub25lIj48cGF0aCBkPSJtMTIuNTkzIDIzLjI1OGwtLjAxMS4wMDJsLS4wNzEuMDM1bC0uMDIuMDA0bC0uMDE0LS4wMDRsLS4wNzEtLjAzNXEtLjAxNi0uMDA1LS4wMjQuMDA1bC0uMDA0LjAxbC0uMDE3LjQyOGwuMDA1LjAybC4wMS4wMTNsLjEwNC4wNzRsLjAxNS4wMDRsLjAxMi0uMDA0bC4xMDQtLjA3NGwuMDEyLS4wMTZsLjAwNC0uMDE3bC0uMDE3LS40MjdxLS4wMDQtLjAxNi0uMDE3LS4wMThtLjI2NS0uMTEzbC0uMDEzLjAwMmwtLjE4NS4wOTNsLS4wMS4wMWwtLjAwMy4wMTFsLjAxOC40M2wuMDA1LjAxMmwuMDA4LjAwN2wuMjAxLjA5M3EuMDE5LjAwNS4wMjktLjAwOGwuMDA0LS4wMTRsLS4wMzQtLjYxNHEtLjAwNS0uMDE4LS4wMi0uMDIybS0uNzE1LjAwMmEuMDIuMDIgMCAwIDAtLjAyNy4wMDZsLS4wMDYuMDE0bC0uMDM0LjYxNHEuMDAxLjAxOC4wMTcuMDI0bC4wMTUtLjAwMmwuMjAxLS4wOTNsLjAxLS4wMDhsLjAwNC0uMDExbC4wMTctLjQzbC0uMDAzLS4wMTJsLS4wMS0uMDF6Ii8+PHBhdGggZmlsbD0iY3VycmVudENvbG9yIiBkPSJNMTMuNTg2IDJBMiAyIDAgMCAxIDE1IDIuNTg2TDE5LjQxNCA3QTIgMiAwIDAgMSAyMCA4LjQxNFYyMGEyIDIgMCAwIDEtMiAySDZhMiAyIDAgMCAxLTItMlY0YTIgMiAwIDAgMSAyLTJaTTEyIDRINnYxNmgxMlYxMGgtNC41QTEuNSAxLjUgMCAwIDEgMTIgOC41em0tLjAxIDEwYy41NTggMCAxLjAxLjQ1MiAxLjAxIDEuMDF2MS4xMjRBMSAxIDAgMCAxIDEyLjUgMThoLS40OUExLjAxIDEuMDEgMCAwIDEgMTEgMTYuOTlWMTZhMSAxIDAgMSAxIDAtMnptLjAxLTNhMSAxIDAgMSAxIDAgMmExIDEgMCAwIDEgMC0ybTItNi41ODZWOGgzLjU4NnoiLz48L2c+PC9zdmc+
---
{.folder}.minecraft
    [folder] config // 存放配置文件的文件夹
    [folder] kubejs //*    KubeJS根目录
        [folder] assets // KubeJS的[资源包](https://zh.minecraft.wiki/w/%E8%B5%84%E6%BA%90%E5%8C%85)目录
        [folder] client_scripts //! 客户端脚本
        [folder] config // KubeJS本地配置
        [folder] data // KubeJS[数据包](https://zh.minecraft.wiki/w/%E6%95%B0%E6%8D%AE%E5%8C%85)目录
        [folder] server_scripts //! 服务端脚本
        [folder] startup_scripts //! 在游戏加载时运行的代码，比如添加物品、方块流体等
    + [folder] logs // 包含了游戏与KubeJS在运行时产生的日志
        [folder] kubejs //! KubeJS日志
            [log] client.log //! 客户端日志
            [log] server.log //! 服务端日志
            [log] startup.log //! 游戏加载时所运行脚本的日志
        [log] latest.log //! 游戏日志
    [folder] mods // 模组文件
</LiteTree>

### 非脚本资源 {#NonScriptResources}

该部分包含三类非脚本的{资源文件:Resource}，主要对应资源包与数据包相关内容。在注册物品并引用游戏资源，以及对地形生成、群系、地物与结构生成等内容进行修改时，这些目录尤为重要。

::::tabs key:ab
== 资源包/assets

**`assets`** ：其基本结构如下：`assets/<namespace>/...`

其中 `namespace` 表示命名空间，且可以存在多个。例如，原版的命名空间为 **`minecraft`**。该目录主要用于存放各类资源文件，例如：**语言（lang）**、**纹理（textures）** 等。

::: plain
详情参见 [Minecraft Wiki（资源包 - 文件结构）](https://zh.minecraft.wiki/w/%E8%B5%84%E6%BA%90%E5%8C%85#%E6%96%87%E4%BB%B6%E7%BB%93%E6%9E%84)。
:::
== 数据包/data

**`data`** ：其基本结构如下：`data/<namespace>/...`

其中 `namespace` 同样表示命名空间，且可以存在多个。例如，原版的命名空间为 **`minecraft`**。该目录主要用于存放各类数据文件，例如：**标签（tags）**、**进度（advancements）**、**战利品表（loot_tables）** 等。

::: plain
详情参见 [Minecraft Wiki（数据包 - 文件结构）](https://zh.minecraft.wiki/w/%E6%95%B0%E6%8D%AE%E5%8C%85#%E6%96%87%E4%BB%B6%E5%A4%B9%E7%BB%93%E6%9E%84)。
:::

::::

:::: alert {"type":"warning","title":"请注意！","variant":"tonal","icon":"mdi-alert-decagram"}
资源文件与数据文件遵循 `命名空间 + 路径` 的组织方式；当不同资源包或数据包中存在相同路径内容时，最终生效结果将由优先级决定，后加载的内容会覆盖先加载的内容。
::::

::: text
在数据包与资源包中，“优先级”指的是**生效顺序而非加载顺序**：优先级低的内容会先加载，优先级高的内容后加载，并可以覆盖前者在相同命名空间与路径下的资源。在游戏的资源包/数据包界面中，位置越靠上表示优先级越高（加载更晚），位置越靠下则优先级越低（加载更早）。因此，可以简单理解为：**后加载的内容拥有更高优先级，并能够覆盖先加载的内容**。
:::

简单来说，通常不建议通过 KubeJS 的 `assets` 或 `data` 直接覆盖已有资源包或数据包的内容；但在有明确需求时，可以先按预期进行覆盖实现，无需在一开始过多考虑优先级问题。若最终效果与预期不符，再回过头检查优先级关系是否存在冲突即可。
### 脚本资源 {#ScriptResources}

与 `assets` 和 `data` 不同，脚本资源用于编写并执行实际的逻辑代码，是 KubeJS 最核心的组成部分。所有脚本均基于 JavaScript 编写，并运行在特定的执行环境中；不同目录下的脚本，其作用范围、可访问对象以及可响应的事件均存在差异。

KubeJS 根据运行环境将脚本划分为三类：

- **`startup_scripts`**  
  在游戏加载阶段执行，主要用于注册内容（如物品、方块、流体等）。该阶段更偏向“定义”，而非运行时逻辑处理。

- **`server_scripts`**  
  在服务端运行，负责处理主要的游戏逻辑，例如事件监听、配方修改、掉落控制、实体行为等。绝大多数魔改逻辑集中于此。

- **`client_scripts`**  
  在客户端运行，主要影响界面与表现层，例如 HUD、粒子效果、提示信息及部分交互反馈。

需要注意的是，这三类脚本并不是简单的功能分类，而是严格对应不同的执行环境。每个环境中可用的对象、事件以及可实现的功能均有所不同，因此在编写脚本时，必须明确当前代码的运行位置。

#### 运行环境 {#Platform}

如果你有过基础的模组开发经验，可能会接触到一些未明确区分运行侧的事件（如 `PlayerInteractEvent$RightClickItem`），通常需要通过 `level.isClientSide()` 来手动判断执行环境。而在 KubeJS 中，这种“运行环境的区分”被前置到了脚本层面，可以理解为：

:::magic-move

```java
// Forge（同一事件中手动区分运行侧）
if (level.isClientSide()) {
    // client logic
} else {
    // server logic
}
```

```js [KubeJS]
// KubeJS（通过脚本目录直接区分运行环境）
// client_scripts
// → 客户端环境

// server_scripts
// → 服务端环境
```
:::

这种基于目录划分运行环境的设计，避免了在同一逻辑中频繁进行客户端/服务端判断，使代码结构更加清晰，同时也降低了因运行侧混用而引发问题的可能性。但与此同时，**这种`分离式执行`也容易在理解上带来一些偏差**。

::: text
KubeJS 的事件大多基于底层事件体系进行封装，而这些事件本身并不会自动同步或覆盖不同运行环境的行为。如果对机制缺乏足够了解，便容易**产生逻辑已经处理，但结果不符合预期**的情况。
:::

一个常见的例子是尝试阻止玩家使用某个物品：

```js
// server_scripts
ItemEvents.rightClicked(event => {
  if (event.item.id === 'minecraft:ender_pearl') {
    event.cancel()
  }
})
```

从逻辑上看，这段代码已经取消了物品使用，但在实际游戏中，你可能会发现物品的部分行为仍然被触发。这是因为：

- 该取消逻辑仅作用于服务端
- 客户端仍然执行了对应的使用行为（`Item.use()`）
- 因此表现为“未完全取消”

换句话说，这类问题并不是逻辑错误，而是运行环境不一致导致的行为差异。

要解决这一问题，需要在客户端与服务端同时处理对应逻辑，例如：

:::tabs key:ba
== server_scripts
```js
// server_scripts
ItemEvents.rightClicked(event => {
  if (event.item.id === 'minecraft:ender_pearl') {
    event.cancel()
  }
})
```
通过在两个运行环境中同时取消事件，才能确保行为被完整阻止。
== client_scripts
```js
// client_scripts
ItemEvents.rightClicked(event => {
  if (event.item.id === 'minecraft:ender_pearl') {
    event.cancel()
  }
})
```
通过在两个运行环境中同时取消事件，才能确保行为被完整阻止。
:::

---

与 `server_scripts` / `client_scripts` 不同，`startup_scripts` 并不天然绑定某一运行侧。在该阶段执行的代码更接近“启动阶段”，很多操作会同时作用于客户端与服务端，甚至在不同阶段被多次触发。因此，**不能简单地假设代码只运行在某一侧**。

这意味着，在 `startup_scripts` 中：

- 需要明确当前代码是否应在客户端或服务端执行  
- 对于仅限单侧的逻辑（如渲染、客户端对象），必须进行环境判断  
- 对注册行为，应避免引入依赖运行时状态的逻辑  

例如，一些仅客户端存在的对象（如渲染、UI）如果直接在 `startup_scripts` 中调用，**很可能会在服务端环境中导致错误**：

```js
// 错误示例（未区分运行环境）
StartupEvents.registry('item', event => {
  // 假设这里调用了仅客户端存在的类或方法
  someClientOnlyFunction()
})
```

可以通过 `Platform` 来区分当前的运行环境，这在后续使用 `ForgeEvents` 时尤为重要。

```js
StartupEvents.registry('item', event => {
  if (Platform.isClientEnvironment()) {
    // client-only logic
  }
})

```

此外，在各类事件的回调函数（如上文的 `event => { ... }`）中，通常也可以通过上下文对象获取到当前的 `Level`（例如 `entity.level()`）。在这种情况下，可以使用 `isClientSide()` 方法来判断当前代码的执行环境，从而区分客户端与服务端逻辑。

本章节主要涵盖必要的基础内容，关于更具体的开发细则与实践技巧（如 Global 的使用），将于后续章节中进行详细说明。

*[IDE]: Integrated Development Environment，集成开发环境。它是一种辅助程序开发人员开发软件的应用程序，将代码编写、编译、调试、测试等多种工具整合在一个图形用户界面（GUI）中，旨在通过自动化和整合工具提高开发效率。