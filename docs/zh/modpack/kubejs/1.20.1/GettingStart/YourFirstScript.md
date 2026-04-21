---
title: 你的第一个脚本
description: 在 server_scripts 中编写并测试第一份 KubeJS 脚本
priority: 40
layout: doc
---

# createExplosion(...).explode() {#Introduction}

本页使用一个足够短、但能在游戏内直接看到结果的示例，帮助你完成第一份 KubeJS 脚本。示例放在 `server_scripts` 中，是因为这一层最适合初学者接触 KubeJS 的事件驱动方式：它能较快给出反馈，又不需要立刻进入客户端渲染或启动阶段注册这些门槛更高的内容。

## 操作步骤 {#FirstRun}

::: steps
@tab 新建脚本文件

在实例目录下新建：

```text
kubejs/server_scripts/apple.js
```

文件名并不固定。这里使用 `apple.js`，只是为了让文件名和脚本内容保持一致。

@tab 写入示例代码

```js
ItemEvents.foodEaten((event) => {
    if (event.item.id !== "minecraft:apple") return;

    const player = event.player;
    player.level.createExplosion(player.x, player.y, player.z).explode();
});
```

这段脚本只做一件事：当玩家吃掉苹果后，在玩家当前位置生成一次爆炸。

@tab 进入游戏测试

保存文件后，按下面的顺序测试：

1. 进入当前实例所读取的世界或服务端。
2. 如果你刚修改的是 `server_scripts` 脚本，可以先执行一次 `/kubejs reload server_scripts`。
3. 拿一颗苹果并将它吃掉。
:::

:::: alert {"type":"info","title":"关于 `/kubejs reload server_scripts` 与 `/reload`","variant":"outlined","border":"start"}
如果你改动的只是 `server_scripts` 里的脚本，优先使用 `/kubejs reload server_scripts`。只有在数据与资源也需要一并重载时，才使用 `/reload`。无论使用哪一个命令，`ItemEvents.foodEaten` 是否执行，仍然取决于玩家是否真的完成了一次进食。
::::

## 代码在做什么 {#ReadCode}

| 代码 | 作用 |
| :--- | :--- |
| `ItemEvents.foodEaten(...)` | 食物被吃下后触发的事件 |
| `event.item.id !== "minecraft:apple"` | 确认本次吃掉的物品是否为苹果 |
| `event.player` | 取得触发事件的玩家对象 |
| `player.level.createExplosion(...).explode()` | 在当前世界中生成一次爆炸 |

起步阶段选择这种示例，是为了把变量控制在最少范围内。你需要先熟悉三件事：事件在什么时候触发、事件对象里有哪些数据、取得对象后能否继续调用方法。等这些关系稳定之后，再扩展到更复杂的逻辑会容易很多。

## 首次测试未生效时应检查的内容 {#FirstChecklist}

:::: alert {"type":"warning","title":"优先检查这些地方","variant":"tonal"}

- 脚本是否确实放在 `kubejs/server_scripts` 中。
- 当前实例是否正在读取你编辑的这份 `apple.js`。
- 玩家吃掉的是否确实是 `minecraft:apple`。
- 修改脚本后，是否执行了 `/kubejs reload server_scripts`，或重新进入了世界。
- 是否把脚本重载与事件触发混为一谈；命令不会替代真正的“进食”动作。
::::

脚本确认生效后，可以再尝试修改触发物品、爆炸位置或效果强度，继续观察结果变化。入门阶段建议一次只改一处，这样更容易建立“脚本改动”和“游戏结果”之间的对应关系。
