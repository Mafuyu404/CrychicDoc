---
title: 测试与调试
description: 通过日志确认脚本是否被加载、事件是否触发，以及异常发生的位置
priority: 50
layout: doc
---

# console.info("[hello.js] loaded") {#Introduction}

KubeJS 调试的第一目标，是确认脚本究竟执行到了哪一层，而不是立即解释全部业务逻辑。对起步阶段而言，至少要区分四件事：

- 文件是否已被加载。
- 事件是否确实触发。
- 关键对象是否已经拿到。
- 具体哪一段运行期逻辑发生了异常。

这四层若没有分清，后续判断很容易失准。

## 基础调试示例 {#MinimalTemplate}

下面这段脚本只保留最基础的检查点：

```js
console.info("[hello.js] script loaded");

PlayerEvents.loggedIn((event) => {
    console.info("[hello.js] PlayerEvents.loggedIn fired");
    console.info(event.player);
});
```

三条输出分别对应三层信息：

| 输出                                              | 用途                       |
| :------------------------------------------------ | :------------------------- |
| `"[hello.js] script loaded"`                      | 确认文件已被加载           |
| `"[hello.js] PlayerEvents.loggedIn fired"`        | 确认事件已经触发           |
| `event.player`                                    | 确认关键对象已经成功取得   |

如果其中任意一层没有得到确认，就不应继续向下推断。

## 日志文件 {#LogFiles}

| 文件                      | 适用场景                                               |
| :------------------------ | :----------------------------------------------------- |
| `logs/kubejs/startup.log` | 排查 `startup_scripts`，或怀疑启动阶段脚本根本未加载   |
| `logs/kubejs/server.log`  | 排查 `server_scripts` 的事件、配方与服务端逻辑         |
| `logs/kubejs/client.log`  | 排查 `client_scripts` 的界面、本地提示与客户端表现     |
| `logs/latest.log`         | 需要查看更完整的报错栈，或交叉确认整个游戏日志         |

如果本页示例放在 `server_scripts` 中，优先查看：

```text
logs/kubejs/server.log
```

## 基础排查顺序 {#Workflow}

### 1. 先确认文件已加载

顶层输出用于判断脚本文件本身是否被 KubeJS 读取：

```js
console.info("[hello.js] script loaded");
```

如果这一行没有出现，应优先检查：

- 文件是否放在正确的脚本阶段目录。
- 当前实例是否读取到了你正在编辑的这份文件。
- 脚本是否存在语法错误，导致文件在加载前就失败。

### 2. 再确认事件确实触发

事件回调内的输出，用于确认触发条件已经成立：

```js
PlayerEvents.loggedIn((event) => {
    console.info("[hello.js] PlayerEvents.loggedIn fired");
});
```

如果文件已加载而事件输出没有出现，问题通常不在后续 API，而在触发条件本身。例如，只执行 `/reload` 并不会替代“重新登录世界”这一触发过程。

### 3. 在使用对象前先确认对象本身

对 `event.player`、`event.level`、`event.block` 这类关键对象，先确认是否已经拿到，再继续编写后续逻辑：

```js
PlayerEvents.loggedIn((event) => {
    console.info(event.player);
});
```

这里需要确认的不是“没有报错”，而是“当前对象确实存在，并且类型与预期一致”。

### 4. 分支与返回语句前后需要标记

一旦脚本开始包含 `if`、`return` 或多段条件分支，就应在关键位置保留最少量的输出：

```js
console.info("[hello.js] before player check");

if (!event.player) {
    console.warn("[hello.js] event.player missing");
    return;
}

console.info("[hello.js] after player check");
```

这能帮助你判断执行路径究竟停在何处，而不是只看到“脚本没有效果”这一笼统现象。

## `console` 的常见用途 {#Console}

| 方法              | 适用场景                                   |
| :---------------- | :----------------------------------------- |
| `console.log()`   | 输出普通信息或对象内容                     |
| `console.info()`  | 输出明确的检查点                           |
| `console.warn()`  | 标记可疑状态，但当前还不属于硬错误         |
| `console.error()` | 记录已经确认发生的异常或错误               |

对于起步阶段，`info`、`warn` 和 `error` 通常已经足够。

## `try / catch` 的作用范围 {#TryCatch}

`try / catch` 只处理一件事：**捕获已经进入执行流程的那一小段代码中抛出的运行期异常。**

它通常适合处理下面这类问题：

- Java 调用过程中抛出的异常。
- 访问字段或方法时发生的运行期错误。
- 你已经确认事件触发、对象存在后，某一小段逻辑的失败点。

它不能解决下面这些问题：

- 脚本语法错误。
- 文件根本没有被加载。
- 事件根本没有触发。

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

如果运行期异常没有被捕获，当前执行路径通常会在该处中断。放在 `startup_scripts` 中的未捕获异常，影响会更明显，严重时可能直接中断启动流程。

## 调试输出应及时清理 {#Cleanup}

本页示例刻意保留了较多 `console` 输出，因为它的目标是帮助你先确认执行路径。但在问题已经定位、逻辑已经稳定之后，这些输出通常就没有继续保留的必要了。

更常见的做法是：

- 删除已经完成任务的临时输出。
- 或先将其注释掉，等再次排查时再恢复。

这样可以避免日志被无关信息淹没，也更容易在下一次出错时看见真正需要关注的内容。
