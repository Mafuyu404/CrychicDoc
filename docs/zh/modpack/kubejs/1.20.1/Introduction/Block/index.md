---
title: Block
hidden: false
priority: 200
collapsed: true
---

<!--
<llm-only>
## KubeJS 方块系统

这是KubeJS方块系统的核心文档页面。用于注册和管理Minecraft中的自定义方块。

### 方块注册基础

```javascript
// StartupScript 中注册方块
StartupEvents.init(event => {
    // 简单方块注册
    event.create('my_mod:custom_block')
        .displayName('Custom Block')
        .hardness(2.0)
        .resistance(6.0)
        .material('stone')
        .requiresTool(true);

    // 带方块实体的方块
    event.create('my_mod:custom_block_entity')
        .displayName('Custom Block with Entity')
        .blockEntity('normal')
        .defaultBlockState(blockState => {
            // 设置默认方块状态
        });

    // 液体方块
    event.create('my_mod:custom_fluid')
        .displayName('Custom Fluid')
        .liquid();
});
```

### 方块属性说明

| 属性 | 类型 | 说明 |
|-----|------|------|
| displayName | string | 游戏内显示名称 |
| hardness | number | 硬度（影响挖掘时间） |
| resistance | number | 抗性（影响爆炸抗性） |
| material | string | 材质（影响交互行为） |
| requiresTool | boolean | 是否需要工具挖掘 |
| lightLevel | number | 发光等级（0-15） |
| jumpFactor | number | 跳跃因子 |
| speedFactor | number | 移动速度因子 |

### 方块状态（BlockState）

```javascript
// 设置方块状态
event.create('my_mod:variant_block')
    .defaultBlockState({
        variant: 'default'
    })
    .blockState((block, definition) => {
        definition.addState('variant', ['default', 'active', 'broken']);
    });
```

### 重要约束

- 方块ID必须使用小写字母和下划线
- 方块材质影响工具挖掘效率和交互行为
- 带方块实体的方块需要额外的注册代码
- 液体方块需要定义流体属性
- 方块状态名称必须唯一且不能与原版冲突

### 相关文档

- 方块实体：查看BlockEntity相关文档
- 方块掉落：LootTable/index.md
- 物品系统：Item/index.md
- 配方系统：Recipe/index.md
</llm-only>
-->

