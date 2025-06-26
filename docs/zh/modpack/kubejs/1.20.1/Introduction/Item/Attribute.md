---
title: 物品属性修饰符
progress: 90
description: 物品（ItemStack）属性修饰符机制
state: unfinished
---

# 物品属性修饰符

## 概述 {#overview}

物品（ItemStack）属性修饰符（AttributeModifier）允许通过装备、持有等方式动态影响实体的属性（如攻击力、最大生命、移动速度等）。常用于武器、护甲、饰品等装备加成，也可实现特殊能力、事件触发等高级玩法。物品修饰符与实体属性系统（AttributeInstance）深度集成，是 Minecraft 属性机制的重要组成部分。

## 机制说明 {#mechanism}

- 物品修饰符可通过 NBT（AttributeModifiers）或 getAttributeModifiers 方法注入。
- 每个修饰符包含：
  - `UUID`：唯一标识，决定叠加/覆盖行为。
  - `Name`：修饰符名称，仅用于显示和调试。
  - `Amount`：数值，支持正负。
  - `Operation`：操作符（0=ADDITION，1=MULTIPLY_BASE，2=MULTIPLY_TOTAL）。
  - `AttributeName`：目标属性注册名（如 `generic.attack_damage`）。
  - `Slot`：适用装备位（如 `mainhand`、`offhand`、`head`、`chest` 等，可选）。
- NBT 结构示例：

```json
{
  "AttributeModifiers": [
    {
      "AttributeName": "generic.attack_damage",
      "Name": "Weapon bonus",
      "Amount": 5.0,
      "Operation": 0,
      "UUID": [123456, 654321, 111111, 222222],
      "Slot": "mainhand"
    }
  ]
}
```

- getAttributeModifiers 方法允许物品类自定义返回修饰符集合，支持动态逻辑。

## 底层流程 {#internal-flow}

- LivingEntity.detectEquipmentUpdates() 检测装备变更，自动识别每个装备位的 ItemStack 变化。
- 旧装备的修饰符通过 removeAttributeModifiers 移除，新装备的修饰符通过 addTransientAttributeModifiers 应用到 AttributeMap。
- handleEquipmentChanges() 负责批量同步所有装备修饰符，保证属性状态与装备一致。
- 物品修饰符的生效与移除完全自动，卸下即移除，穿戴即生效，无需手动管理。
- 属性变更会自动触发 setDirty()，刷新缓存并同步到客户端（仅 isClientSyncable 属性）。

## 修饰符叠加与覆盖机制 {#modifier-stack}

- 每个 AttributeModifier 以 UUID 唯一标识，若多件装备/物品使用相同 UUID，则后添加的会覆盖前者，属性面板和实际效果仅保留一个。
- 不同 UUID 的修饰符可叠加，支持多件装备/来源共同影响同一属性。
- Operation 决定叠加方式，详见实体属性文档。
- 属性面板仅显示当前生效的修饰符，UUID 冲突时可能导致显示与实际不符。

## 典型用法与脚本接口 {#usage-examples}

### NBT 添加修饰符

```json
{
  "AttributeModifiers": [
    {
      "AttributeName": "generic.max_health",
      "Name": "Bonus Health",
      "Amount": 10.0,
      "Operation": 0,
      "UUID": [123,456,789,1011],
      "Slot": "chest"
    }
  ]
}
```

### KubeJS 添加/移除物品修饰符

```js
// 添加修饰符
item.addAttributeModifier('generic.attack_damage', 'kubejs:weapon_bonus', 5, 'addition', 'mainhand');
// 移除修饰符
item.removeAttributeModifier('generic.attack_damage', 'kubejs:weapon_bonus', 'mainhand');
```

- KubeJS 的物品修饰符接口会自动映射到 AttributeModifiers NBT，装备时自动同步到实体。
- 与实体属性系统（AttributeInstance）无缝集成，支持事件驱动、动态变更。

## 推荐实践与模组兼容 {#recommend}

- 为每个自定义 AttributeModifier 分配唯一 UUID，避免与原版/其他模组冲突。
- 推荐使用 [Apothic Attributes](https://www.curseforge.com/minecraft/mc-mods/apothic-attributes) 前置模组，支持多修饰符叠加与属性面板优化，彻底解决 UUID 冲突导致的属性覆盖与显示问题。
- 封装统一的修饰符管理工具，便于批量增删、调试和兼容性维护。