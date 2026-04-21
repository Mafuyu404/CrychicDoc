---
title: 你的第一个脚本
description: 在 server_scripts 中编写并测试第一份 KubeJS 脚本
priority: 40
layout: doc
---

# createExplosion(...).explode() {#Introduction}

本页使用一个足够短、但能在游戏内直接看到结果的示例，帮助你完成第一份 KubeJS 脚本。示例放在 `server_scripts` 中，是因为这一层最适合初学者接触 KubeJS 的事件驱动方式：它能较快给出反馈，又不需要立刻进入客户端渲染或启动阶段注册这些门槛更高的内容。

## 脚本文件 {#FirstRun}

在实例目录下新建：

```text
kubejs/server_scripts/apple.js
```

文件名并不固定。这里使用 `apple.js`，只是为了让文件名和脚本内容保持一致。

## 示例代码 {#Example}

```js
ItemEvents.foodEaten((event) => {
    if (event.item.id !== "minecraft:apple") return;

    const player = event.player;
    player.level.createExplosion(player.x, player.y, player.z).explode();
});
```

这段脚本只做一件事：当玩家吃掉苹果后，在玩家当前位置生成一次爆炸。

## 触发方式 {#HowToTest}

保存文件后，按下面的顺序测试即可：

1. 进入当前实例所读取的世界或服务端。
2. 如果你刚修改过脚本，可以先执行一次 `/reload`。
3. 拿一颗苹果并将它吃掉。

需要注意的是，`/reload` 只负责重载脚本与数据，不会替代事件本身的触发条件。`ItemEvents.foodEaten` 是否执行，仍然取决于玩家是否真的完成了一次进食。

## 代码说明 {#ReadCode}

- `ItemEvents.foodEaten(...)` 是食物被吃下后触发的事件。
- `event.item.id` 用于确认本次吃掉的物品是否为 `minecraft:apple`。
- `event.player` 是触发事件的玩家对象。
- `player.level.createExplosion(...).explode()` 会在当前世界中生成一次爆炸。

起步阶段选择这种示例，是为了把变量控制在最少范围内。你需要先熟悉三件事：事件在什么时候触发、事件对象里有哪些数据、取得对象后能否继续调用方法。等这些关系稳定之后，再扩展到更复杂的逻辑会容易很多。

## 首次测试未生效时应检查的内容 {#FirstChecklist}

如果没有看到效果，通常优先回看下面几项：

- 脚本是否确实放在 `kubejs/server_scripts` 中。
- 当前实例是否正在读取你编辑的这份 `apple.js`。
- 玩家吃掉的是否确实是 `minecraft:apple`。
- 是否只执行了 `/reload`，但没有真正触发“进食”这一事件。

脚本确认生效后，可以再尝试修改触发物品、爆炸位置或效果强度，继续观察结果变化。入门阶段建议一次只改一处，这样更容易建立“脚本改动”和“游戏结果”之间的对应关系。
