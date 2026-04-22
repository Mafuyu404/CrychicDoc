---
title: 测试与调试
description: 通过日志确认脚本是否被加载、事件是否触发，以及异常发生的位置
priority: 50
layout: doc
---

# console.info("[hello.js] loaded") {#Introduction}

KubeJS 调试的首要目标，是确认脚本究竟执行到了哪一层。

对起步阶段而言需要区分四件事：

- 文件是否已被加载：++对于KubeJS来说通常并不重要，而有关脚本是否加载的逻辑会在后文提及。++
- 事件是否确实触发：==你是否正确触发了事件，在你不了解事件本身的原理时很有用。==
- 关键对象是否已经拿到。
- 具体哪一段运行期逻辑发生了异常。

这四层若没有分清，后续判断很容易失准。

## 基础调试示例 {#MinimalTemplate}

下面这段脚本只保留最基础的检查点：

```js
console.info("[hello.js] script loaded"); // 只是为了确保文件被加载，更多是为了配合事件触发的日志

PlayerEvents.loggedIn((event) => {
    console.info("[hello.js] PlayerEvents.loggedIn fired"); // [!code focus]
    console.info(event.player); // [!code focus]
});
```

三条输出分别对应三层信息：

| 输出                                              | 用途                       |
| :------------------------------------------------ | :------------------------- |
| `"[hello.js] script loaded"`                      | 确认文件已被加载           |
| `"[hello.js] PlayerEvents.loggedIn fired"`        | 确认事件已经触发           |
| `event.player`                                    | 确认关键对象已经成功取得   |

如果其中任意一层没有得到确认，就不应继续向下推断。

## Debugging 步骤 {#Workflow}

::: steps
@tab 先确认文件已加载

顶层输出用于判断脚本文件本身是否被 KubeJS 读取：

```js
console.info("[hello.js] script loaded");
```

如果这一行没有出现，应优先检查：

- 文件是否放在正确的脚本阶段目录。
- 当前实例是否读取到了你正在编辑的这份文件。
- 脚本是否存在语法错误，导致文件在加载前就失败。

@tab 再确认事件确实触发

事件回调内的输出，用于确认事件是否被触发：

```js
PlayerEvents.loggedIn((event) => {
    console.info("[hello.js] PlayerEvents.loggedIn fired");
});
```

如果文件已加载而事件输出没有出现，问题就在于事件本身没有触发。例如，即使执行了 `/kubejs reload server_scripts` 或 `/reload`，也不能触发“登录”。

@tab 再确认关键数据已经拿到

对 `event.player`、`event.level`、`event.block` 这类关键对象，先确认是否已经拿到数据，再继续推进后续逻辑：

```js
PlayerEvents.loggedIn((event) => {
    console.info(event.player);
});
```

这里需要确认的是==当前对象确实存在，并且类型与预期一致，且获取到的数据对你的后续代码有帮助==。

@tab 最后缩小到具体失败位置

一旦脚本开始包含 `if`、`return` 或多段条件分支，就应在关键位置保留最少量的输出：

```js
console.info("[hello.js] before player check");

if (!event.player) {
    console.warn("[hello.js] event.player missing");
    return;
}

console.info("[hello.js] after player check");
```

这能帮助你判断执行路径究竟停在何处，而不是只看到“脚本没有效果”这种模糊的描述。确认失败位置后，再决定是否需要局部 `try / catch`。
:::

## 日志文件 {#LogFiles}

| 文件                      | 适用场景                                               |
| :------------------------ | :----------------------------------------------------- |
| `logs/kubejs/startup.log` | 排查 `startup_scripts`，或怀疑启动阶段脚本根本未加载   |
| `logs/kubejs/server.log`  | 排查 `server_scripts` 的事件、配方与服务端逻辑         |
| `logs/kubejs/client.log`  | 排查 `client_scripts` 的界面、本地提示与客户端表现     |
| `logs/latest.log`         | 需要查看更完整的报错日志，有时没有任何报错但是实际逻辑不支持会在 `lastest.log` 报错而不是在 KubeJS日志中报错         |

:::: alert {"type":"info","title":"如果示例放在 server_scripts","variant":"outlined","border":"start"}
优先查看 `logs/kubejs/server.log`。
::::

只改 `server_scripts` 脚本时，优先使用 `/kubejs reload server_scripts`。只有在需要连同服务端数据一起重载时，才使用 `/reload`。

同理、 `client_scripts` 只有涉及客户端资源时才需要使用 `F3 + T` 重载，一般使用 `/kubejs reload client_scripts` 即可。

`startup_scripts` 通常需要重启游戏，更具体无需重启游戏的方法会在涉及具体细节时再讲述。

## `console` 的常见用途 {#Console}

| 方法              | 适用场景                                   |
| :---------------- | :----------------------------------------- |
| `console.log()`   | 输出普通信息或对象内容                     |
| `console.info()`  | 输出明确的检查点                           |
| `console.warn()`  | 标记可疑状态，但当前还不属于硬错误         |
| `console.error()` | 记录已经确认发生的异常或错误               |

对于起步阶段，`info`、`warn` 和 `error` 通常已经足够。

## `try / catch` 的作用范围 {#TryCatch}

`try / catch` 一般用于 **捕获已经进入执行流程的一小段具体代码抛出的运行异常。**

:::: alert {"type":"warning","title":"它不负责处理","variant":"tonal"}

- 脚本语法错误。
- 文件是否被加载。
- 事件是否被触发。
::::

它通常适合处理下面这类问题：

- Java 调用过程中抛出的异常。
- 访问字段或方法时发生的运行期错误。
- 你已经确认事件触发、对象存在后，某一小段逻辑的失败点。

因此，`try / catch` 应放在已经确认会被执行、且确实存在风险的那一小段代码周围。

推荐写法如下：

```js
PlayerEvents.loggedIn((event) => {
    const player = event.player;

    try {
        console.info(player.name.string);
    } catch (error) {
        console.error("[hello.js] failed while reading player name");
        console.error(error);
    }
});
```

不推荐把整个文件，或整个事件注册过程，直接包进一个过大的 `try / catch` 中：

```js
try {
    PlayerEvents.loggedIn((event) => {
        console.info(event.player.name.string);
    });
} catch (error) {
    console.error(error);
}
```

原因有两点：

- 作用范围过大后，日志会变得含糊，很难判断真正出错的是哪一段。
- 事件是在之后触发的。外层 `try / catch` 包住的是“注册事件”这一步，不等于能捕获稍后在回调内部发生的异常。

如果运行期异常未被捕获，当前执行路径通常会在该处中断。在 `startup_scripts` 中，这一影响会被放大，大部分错误都会直接崩溃掉游戏。

因此，在实验阶段，不确定代码逻辑可以跑通前，通常建议在 `startup_scripts` 中使用 `try / catch` 来隔离异常。

## 调试输出应及时清理 {#Cleanup}

:::: alert {"type":"info","title":"调试完成后","variant":"outlined","border":"start"}
问题已定位、逻辑已稳定后，临时 `console` 输出通常应删除，或先注释掉，避免日志被无关信息淹没。
::::
