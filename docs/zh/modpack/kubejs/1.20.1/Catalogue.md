---
title: 须知
description: KubeJS-1.20.1 须知与目录页，说明阅读方式、使用边界与主要分区入口。
hidden: false
priority: 10
---

# KubeJS-1.20.1 {#Introduction}

当前文档主要维护中的版本。当前目录默认围绕 `Minecraft 1.20.1`、`Forge` 与 `KubeJS 6` 展开。

## 在开始之前，需要先接受什么 {#BeforeYouStart}

如果你想真正接触 KubeJS，而不是只停留在复制几段脚本的阶段，那么至少应先接受下面几件事：

- 这不是一套完全现代、完全稳定、完全统一的脚本环境。`Rhino`、KubeJS 本体、Forge 与各类附属模组的历史遗留问题，会直接影响脚本表现。
- 你需要频繁进入世界测试、查看日志、确认脚本所在侧别，并通过类型与运行结果不断修正认识；==很多实现不是因为写法高级,而是Debug流程做得很清晰==。
- 社区示例往往来自不同版本、不同作者与不同历史阶段。能参考，但不能默认它们可以直接照抄。
- 你会不断遇到 Java 类名、事件签名、方法重载、脚本侧别与版本差异；这不是附带成本，而是使用 KubeJS 开发方式正常的一环。
- 你需要具备基础的 JavaScript 理解，或至少做好逐步学习的准备。KubeJS 并不会替你屏蔽语言本身的复杂性，对函数、对象、作用域等概念的理解，往往直接影响脚本能否正确运行。

这些是使用 KubeJS 进行开发时不可避免的一部分。

## 关于提问、求助与 AI {#AIAndChat}

KubeJS 的很多问题，更适合先整理清楚再去社区提问，而不是直接把问题丢给 AI。真正有价值的问题，重点不在“会不会问”，而在“有没有把上下文讲明白”。

如果你准备去社区求助，至少先整理这些信息：

- `Minecraft / Loader / KubeJS / ProbeJS` 版本。
- 脚本所在位置，例如 `startup_scripts`、`server_scripts` 或 `client_scripts`。
- 你想实现什么，实际发生了什么。
- 最小可复现的代码片段，而不是整个脚本目录。
- 报错、日志、`console` 输出，或你已经确认过的关键类型信息。
- 你已经试过哪些排查步骤，例如重新 `/probejs dump`、切换脚本侧别、打印对象内容。

:::: chat title="社区提问示例"
::: message nickname="提问者" avatar-type="icon"
大佬们，我这个 KubeJS 脚本为什么不生效？
:::

::: message nickname="社区成员" avatar-type="icon" location="right"
你什么都不说我们怎么知道你的代码哪里错了。版本、js文件的位置、你想干嘛、具体代码和日志，这些能提供的提供一下吧，最好用[这个](https://mclo.gs/)发代码和日志。没信息很难判断是事件没触发还是脚本放错地方了，也可能是类型没对上或者Rhino和KJS本体就不支持。
:::

::: message nickname="提问者" avatar-type="icon"
Minecraft `1.20.1`，Forge，KubeJS `6`，ProbeJS Legacy。脚本在 `server_scripts`。我想在 `PlayerEvents.loggedIn` 时给玩家显示动作栏消息。当前代码是 `PlayerEvents.loggedIn(event => { event.player.showActionba(Text.green('hi')) })`。进入世界后没有报错，也没有显示消息。我已经重新 `/probejs dump`，并确认脚本会重载。接下来我应该先查事件、js文件的位置还是 `player` 的类型？
:::

::: message nickname="社区成员" avatar-type="icon" location="right"
你函数`showActionbar`拼错了，是不是没弄好ProbeJS啊。
:::

::: message nickname="社区成员" avatar-type="icon" location="right"
安装好ProbeJS然后生成一下类型吧，如果补全提供的是父类，需要补一下 `JSDoc` 来使用正确的类型；问题是其他情况的时候，先确认事件有没有实际执行，再看动作栏调用、日志输出和类型补全。
:::

::: message nickname="社区成员" avatar-type="icon" location="right"
还有下次发代码用[codesnap](https://marketplace.visualstudio.com/items?itemName=ArthurLobo.easy-codesnap&ssr=false#overview)截图或者[mclogs](https://mclo.gs/)。
:::
::::

==提问收到回答后记得说谢谢。==!!比了解一万个函数更有用的进步秘诀!!

 **当前的 AI 在 KubeJS 开发上幻觉和误差都很大，不推荐依赖。** 但可以适当使用。
它可以帮你解释术语、整理问题、列排查顺序，但不能代替日志、类型文件、源码、实际测试，也不能代替社区里基于经验给出的修正。

如果一段回答对不上具体版本、具体事件并不能判断可能的运行结果或者可能的报错，那它最多只是线索，不是答案。