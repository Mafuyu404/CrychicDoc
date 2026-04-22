---
title: 你的第一个脚本
description: 在 server_scripts 中编写并测试第一份 KubeJS 脚本
priority: 40
layout: doc
---

# createExplosion(...).explode() {#Introduction}

本页使用一个足够短、但能在游戏内直接看到结果的示例，引导你完成第一份 KubeJS 脚本。示例放在 `server_scripts` 中，是因为这是最适合初学者接触 KubeJS 的环境：它能较快给出反馈，又不需要立刻接触客户端渲染或启动阶段注册这些门槛更高的内容。

## 操作步骤 {#FirstRun}

:::: steps
@tab 新建脚本文件

在 实例 目录下新建：

*[实例]: kubejs之外的版本文件夹，通常为.minecraft，取决于你使用的启动器。

:::flat kubejs/server_scripts/apple.js
文件名并不固定。这里使用 `apple.js`。
:::

@tab 写入示例代码

```js
ItemEvents.foodEaten((event) => {
    if (event.item.id !== "minecraft:apple") return;

    const player = event.player;
    player.level.createExplosion(player.x, player.y, player.z).explode();
});
```

这段代码的逻辑是当玩家吃掉苹果后，在玩家当前位置生成一次爆炸。

@tab 进入游戏测试

保存文件后，按下面的顺序测试：

1. 进入当前实例所读取的世界或服务端。
2. 如果你刚修改的是 `server_scripts` 脚本，可以先执行一次 `/kubejs reload server_scripts`。
3. 拿一颗苹果并将它吃掉。
4. boom💥
::::

:::: alert {"type":"info","title":"关于 `/kubejs reload server_scripts` 与 `/reload`","variant":"outlined","border":"start"}
如果你改动的只是 `server_scripts` 里的脚本，优先使用 `/kubejs reload server_scripts`。只有在数据与资源也需要一并重载时，才使用 `/reload`。无论使用哪一个命令，`ItemEvents.foodEaten` 是否执行，仍然取决于玩家是否真的完成了一次进食。
::::

## 代码在做什么 {#ReadCode}

| 代码 | 作用 |
| :--- | :--- |
| `ItemEvents.foodEaten(...)` | 食物被吃下后触发的事件 |
| `event.item.id !== "minecraft:apple"` | 确认本次吃掉的物品是否为苹果 |
| `event.player` | 取得触发事件的玩家对象 |
| `player.level.createExplosion(...).explode()` | 在当前世界、玩家所处的位置上生成一次爆炸 |

如果你已经装好 `ProbeJS legacy / ProbeJS`，也可以同时查看其类型文件，能够帮助你理解其他函数与方法的使用方式。

::: code-group
```js [apple.js]
ItemEvents.foodEaten((event) => {
    if (event.item.id !== "minecraft:apple") return;

    const player = event.player;
    player.level.createExplosion(player.x, player.y, player.z).explode();
});
```

```ts [.probe/server/global/events.d.ts]
export namespace ItemEvents {
    function foodEaten(handler: ((event: $FoodEatenEventJS) => void)): void
}
```

```ts [.probe/server/packages/net.minecraft.world.level.d.ts]
public "createExplosion"(x: double, y: double, z: double): $ExplosionJS
```
:::

把脚本和类型文件放在一起看时，先关注以下两点：

- `event.item.id` 和 `event.player` 都是从 `event` 这个事件里读取出来的{字段:field}。对起步阶段来说，先知道这次吃掉的是什么、是谁吃的，就已经足够了。若想进一步了解该事件还提供了哪些字段，可以在编辑器中悬停 `event` 查看类型信息，或使用 `Ctrl + 点击` 跳转到对应的类型定义。
- `player.level.createExplosion(...)` 会先创建并返回一个爆炸对象，而后面的 `.explode()` 才是真正触发爆炸的执行步骤。可以将这一类写法理解为：**先获取（或构造）一个对象，再调用它的方法完成具体行为**。

在阅读类似代码时，可以优先拆成两步来看：

1. 前半部分在“得到什么对象”；
2. 后半部分在“对这个对象做什么操作”。

这种拆分方式不仅适用于这里的爆炸逻辑，在==处理其他方法返回对象并继续调用的写法时同样适用==。

### 怎样理解 `(event) => { ... }` 这类写法 {#ArrowFunction}

在 JavaScript 里，`(event) => { ... }` 是一种函数写法，通常叫箭头函数。

- `(event)` 是参数列表，表示这段代码会接收一个参数。
- `=>` 可以先理解成“拿到这个参数后，执行右边这段代码”。
- `{ ... }` 是函数体，也就是参数传进来之后真正执行的内容。

因此，这段写法：

```js
(event) => {
    if (event.item.id !== "minecraft:apple") return;
}
```

可以先按下面这种更传统的写法去理解：

```js
ItemEvents.foodEaten(function (event) {
    if (event.item.id !== "minecraft:apple") return;
});
```

这里的 `event` 不是固定关键字，只是参数名。写成 `event`、`e`、`context` 都可以；JavaScript 只关心“这里有一个参数”，并不关心你给它起什么名字。

文档里经常写 `context`，通常只是为了提醒“这个参数更像上下文对象”；它和写成 `event`、`e` 一样，首先都只是变量名，不会改变这段语法本身的含义。

真正决定这个参数里有什么内容的，不是名字，而是外层 API。

放到 KubeJS 里，`ItemEvents.foodEaten(...)` 的意思就是：把一段函数交给 KubeJS 保存起来；等到“有实体吃下食物”这件事真的发生时，KubeJS 再主动调用这段函数，并把这次事件对应的对象作为第一个参数传进来。于是，函数里的 `event.item`、`event.player` 这些成员访问才会成立。

ProbeJS 类型文件里的这一行：

```ts
function foodEaten(handler: ((event: $FoodEatenEventJS) => void)): void
```

可以按下面的顺序读：

- `foodEaten` 需要一个叫 `handler` 的参数。
- 这个 `handler` 本身又是一个函数。
- 这个函数会收到一个参数，类型是 `FoodEatenEventJS`。
- `=> void` 表示这个函数不要求返回值。

所以，文档里看到 `(context) => { ... }`、`(event) => { ... }`、`(player) => { ... }` 时，最先看的不应是名字本身，而是外层 API 想传给你什么对象。ProbeJS 给出的那行签名，正是用来说明“这个参数实际会是什么类型”的。

多参数时也是同样的逻辑。并不是所有 KubeJS API 都会把数据包成一个 `event` 对象；有些 API 会直接把几个对象按顺序传进来。

例如 `ItemBuilder` 里常见的几类回调，可以写成：

```js
.use((level, player, hand) => true)
.releaseUsing((itemstack, level, entity, tick) => {
    // ...
})
```

ProbeJS 类型文件里对应的是：

```ts
export type $ItemBuilder$UseCallback$$Type =
    ($ItemBuilder$UseCallback | ((arg0: $Level, arg1: $Player, arg2: $InteractionHand) => boolean));

export type $ItemBuilder$ReleaseUsingCallback$$Type =
    ($ItemBuilder$ReleaseUsingCallback | ((arg0: $ItemStack, arg1: $Level, arg2: $LivingEntity, arg3: integer) => void));
```

这里可以这样对应着读：

- `(level, player, hand)` 对应 `arg0`、`arg1`、`arg2`。
- `(itemstack, level, entity, tick)` 对应 `arg0`、`arg1`、`arg2`、`arg3`。
- `arg0` 这类名字通常只是 ProbeJS 生成时给出的占位名；你自己写脚本时可以改成更好懂的名字。
- 可以改名字，但不能随意改顺序。第三个参数若本来是 `hand`，写脚本时把它当成 `player` 去用，逻辑就会错。
- `=> boolean` 表示这个回调通常要返回一个布尔值；`=> void` 则表示这里只执行逻辑，不要求返回值。
- 所有参数并不一定全都需要写出，例如只关心 `player` 时，可以省略其后的参数，仅保留前面的顺序，例如：`use((level, player) => { player.tell("Hello!") })`。同样的，上下文中的参数也不是强制都必须使用。

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
