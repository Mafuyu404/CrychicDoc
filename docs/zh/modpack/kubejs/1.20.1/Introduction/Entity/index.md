---
title: Entity
hidden: false
priority: 300
collapsed: true
---

<llm-only>
## KubeJS 实体系统

这是KubeJS实体系统的核心文档页面。用于操作Minecraft中的实体，包括生成、属性修改、AI行为控制等。

### 实体类型层级

```
Entity（实体基类）
├── LivingEntity（生物基类）
│   ├── Mob（可移动实体）
│   │   ├── Animal（动物）
│   │   ├── Monster（怪物）
│   │   ├── Player（玩家）
│   │   └── ...
│   └── ...
├── Item（掉落物品）
├── Projectile（投射物）
└── ...
```

### 实体操作基础

```javascript
// ServerScript 中操作实体
ServerEvents.entitySpawn(event => {
    const entity = event.entity;

    // 检查实体类型
    if (entity instanceof Java.type('net.minecraft.world.entity.monster.Zombie')) {
        // 设置生命值
        entity.setHealth(50);

        // 设置自定义名称
        entity.setCustomName('§cElite Zombie§r');
        entity.setCustomNameVisible(true);

        // 添加效果
        entity.addEffect(
            Java.type('net.minecraft.world.effect.MobEffects').STRENGTH,
            100,
            2
        );
    }
});

// 玩家操作
ServerEvents.playerLogIn(event => {
    const player = event.player;

    // 发送消息
    player.tell('Welcome to the server!');

    // 设置游戏模式
    player.gameMode('creative');

    // 获取位置
    const pos = player.blockPosition();
});
```

### Brain（大脑系统 1.19+）

```javascript
// 操作实体大脑和AI行为
ServerEvents.entitySpawn(event => {
    const entity = event.entity;
    if (entity.brain) {
        const brain = entity.brain;

        // 添加目标
        brain.addMemory(
            Java.type('net.minecraft.world.entity.ai.memory.MemoryModuleType').NEAREST_PLAYER,
            true,
            200
        );

        // 设置活动
        brain.setActiveActivity(
            Java.type('net.minecraft.world.entity.schedule.Activity').FIGHT
        );
    }
});
```

### 重要约束

- 实体操作应在ServerScript中进行
- 使用Java.type访问Minecraft原版类
- 实体事件在实体生成时触发，可以修改实体属性
- 玩家相关操作需要检查玩家是否在线
- Brain系统仅在1.19及以上版本可用

### 相关文档

- 实体方法：Entity/Methods.md
- 玩家操作：Entity/Player.md
- 生物操作：Entity/Mob.md
- AI行为：Entity/Goal.md
- 大脑系统：Entity/Brain.md
</llm-only>

