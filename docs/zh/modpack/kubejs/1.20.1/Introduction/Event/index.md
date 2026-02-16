---
title: Event
hidden: false
priority: 50
collapsed: true
---

<!--
<llm-only>
## KubeJS 事件系统

这是KubeJS事件系统的核心文档页面。事件是KubeJS脚本执行的核心触发器，所有游戏逻辑都通过事件驱动。

### 脚本类型总览

| 脚本类型 | 执行时机 | 典型用途 | 文档页面 |
|---------|---------|---------|---------|
| StartupScript | 服务器启动时执行一次 | 物品/方块注册、配方注册、标签创建 | StartupScript/index.md |
| ServerScript | 服务器端游戏事件 | 玩家操作、方块交互、实体行为、配方处理 | ServerScript/index.md |
| ClientScript | 客户端事件 | GUI渲染、按键监听、客户端特效 | ClientScript/index.md |

### 事件监听基础语法

```javascript
// ServerScript 事件监听
ServerEvents.on('block_place', event => {
    // 事件处理逻辑
    event.player.tell('You placed a block!');
});

// ClientScript 事件监听
ClientEvents.on('player_log_in', event => {
    // 客户端特定逻辑
});
```

### 重要约束

- StartupScript 中的代码只在服务器启动时执行一次，用于注册静态内容
- ServerScript 和 ClientScript 中的事件监听器会在每次相应事件触发时执行
- 事件对象（event）包含该事件的所有相关信息，不同事件有不同的属性
- 务必在事件回调中处理异常，避免导致服务器崩溃

### 相关文档

- 物品注册：Item/index.md
- 方块注册：Block/index.md
- 配方系统：Recipe/index.md
- 实体操作：Entity/index.md
</llm-only>
-->

