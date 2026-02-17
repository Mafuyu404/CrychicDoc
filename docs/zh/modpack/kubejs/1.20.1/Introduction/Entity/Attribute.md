---
title: 实体属性
description: Attribute 在 KubeJS 中的完整操作指南
tags:
  - KubeJS
  - Attribute
  - LivingEntity
progress: 90
hidden: false
priority: 100
---
<llm-only>
This Chinese page (zh/modpack/kubejs/1.20.1/Introduction/Entity/Attribute.md) mirrors the corresponding English content; include it when generating LLM-friendly documentation and keep the original structure.
</llm-only>


# 实体属性 {#EntityAttribute}

## 概述 {#overview}

实体属性（Attribute）是 Minecraft 实体系统的核心组成部分，用于定义和动态控制实体的各项数值特征（如生命值、攻击力、移动速度等）。属性系统由 AttributeMap（属性映射表）、AttributeInstance（属性实例）、AttributeModifier（属性修饰符）等底层结构组成，支持高度可扩展和事件驱动。

## 适用范围 {#applicability}

::: alert {"type": "warning", "title": "注意"}
仅"有生命的实体"（LivingEntity）支持属性操作。非生命实体（如矿车）不适用。
:::

## 属性机制与生命周期 {#mechanism_lifecycle}

- 每个 LivingEntity 拥有 AttributeMap，管理所有 AttributeInstance。
- AttributeInstance 负责属性的基础值、修饰符集合、缓存值，属性变更时 setDirty()，自动刷新缓存并通过 onDirty 回调触发同步。
- 支持持久化（save/load NBT），装备/药水等变更时自动序列化。
- 仅 isClientSyncable() 的属性会同步到客户端。
- 属性变更流程：
  1. setBaseValue/setModifier → setDirty()
  2. setDirty() → 缓存失效，onDirty 回调
  3. onDirty → 属性同步、事件触发

## 属性修饰符机制 {#attribute_modifiers}

- AttributeModifier 用于动态调整属性值，字段包括：
  - `UUID`：唯一标识，决定叠加/覆盖行为
  - `Name`：修饰符名称
  - `Amount`：数值，支持正负
  - `Operation`：操作符（0=ADDITION，1=MULTIPLY_BASE，2=MULTIPLY_TOTAL）
- 三种 Operation 的叠加顺序与底层计算：

```mermaid
graph TD;
  A[Base Value] --> B[+ 所有 ADDITION]
  B --> C[* (1 + 所有 MULTIPLY_BASE)]
  C --> D[* (1 + 所有 MULTIPLY_TOTAL)]
  D --> E[最终属性值]
```

- 伪代码示例：

```js
let value = baseValue;
value += sum(additionModifiers);
value *= (1 + sum(multiplyBaseModifiers));
value *= (1 + sum(multiplyTotalModifiers));
value = attribute.sanitizeValue(value);
```

## 事件驱动与自动管理 {#event_driven}

- LivingEntity tick 时自动检测装备、药水、事件等变更，动态添加/移除修饰符。
- 装备切换时，旧装备的修饰符会被移除，新装备的修饰符通过 addTransientAttributeModifiers 自动应用。
- 药水生效/失效时，MobEffectInstance 会自动调用 add/removeAttributeModifiers。
- 属性变更会自动触发同步与缓存刷新，无需手动管理。

## 典型用法与脚本接口 {#usage_examples}

### 获取/设置属性基础值 {#get_set_base_value}

```js
const maxHealth = entity.getAttribute('generic.max_health').getBaseValue();
entity.getAttribute('generic.max_health').setBaseValue(30);
```

### 添加/移除属性修饰符 {#add_remove_modifiers}

```js
// 增加最大生命值（如装备特殊护甲时）
entity.modifyAttribute('generic.max_health', 'kubejs:armor_bonus', 8, 'addition');
// 增加移动速度（如获得速度药水时）
entity.modifyAttribute('generic.movement_speed', 'kubejs:speed_potion', 0.2, 'multiply_total');
// 移除修饰符（如脱下装备或药水失效）
entity.removeAttributeModifier('generic.max_health', 'kubejs:armor_bonus');
```

### 获取所有修饰符 {#get_all_modifiers}

```js
const modifiers = entity.getAttribute('generic.max_health').getModifiers();
modifiers.forEach(mod => {
  console.log(mod.id, mod.name, mod.amount, mod.operation);
});
```

## 小贴士 {#tips}

- **唯一标识符**：每个修饰符应使用唯一字符串（如 `kubejs:xxx`），便于后续精确移除和管理。
- **叠加与覆盖**：同一属性的不同修饰符可叠加，标识符重复会覆盖。
- **事件驱动**：推荐在装备穿戴、药水生效、事件触发等时动态添加/移除修饰符。
- **常见误区**：
  - 忽略 UUID 唯一性，导致修饰符覆盖或属性异常。
  - 直接修改基础值而非用修饰符，导致属性动态性丧失。
  - 忽略属性同步，导致客户端显示与实际不符。

---

如需更深入的底层机制或脚本批量管理示例，可参考 [Forge JavaDocs](https://mcstreetguy.github.io/ForgeJavaDocs/1.20.1-47.1.0/index.html) 及 KubeJS 官方文档。

## 修饰符类型与生命周期 {#modifier_types}

在 Minecraft 属性系统中，AttributeModifier（属性修饰符）根据生命周期和持久化方式分为三类：

| 类型         | 说明                                 | 生命周期         | 典型场景         |
|--------------|--------------------------------------|------------------|------------------|
| permanent    | 永久修饰符，随实体存档持久化         | 装备、物品等     | 装备加成         |
| transient    | 临时修饰符，仅内存存在，不持久化     | 事件、Buff等     | 临时增益         |
| 普通         | 取决于调用方式，既可持久化也可临时   | 混合             | 通用             |

### 底层机制 {#mechanism}

- `permanentModifiers` 集合：存储所有永久修饰符，随实体存档持久化（如装备、物品 NBT 修饰符）。
- `transientModifiers` 集合：存储所有临时修饰符，仅在内存中存在，适合事件驱动、临时增益。
- `modifierById`：所有当前生效的修饰符（无论类型），以 UUID 为索引，便于查找和覆盖。
- 修饰符的添加/移除会自动触发 setDirty()，刷新属性缓存并同步到客户端。

#### 源码伪代码示例 {#samples}

```js
// 添加永久修饰符（如装备）
attributeInstance.addPermanentModifier(modifier);
// 添加临时修饰符（如事件Buff）
attributeInstance.addTransientModifier(modifier);
// 获取所有修饰符
attributeInstance.getModifiers(); // 包含所有类型
```

### KubeJS 脚本接口与用法 {#kubejs_usage}

- `modifyAttribute`（KubeJS）：默认等价于 transient 修饰符，适合事件、临时增益。
- `removeAttributeModifier`：可移除任意类型的修饰符（需指定 UUID/标识符）。
- 若需实现永久修饰符，推荐通过装备/物品 AttributeModifiers NBT 或自定义事件持久化管理。
- 典型用法：

```js
// 临时增益（事件触发，随事件结束自动移除）
entity.modifyAttribute('generic.attack_damage', 'kubejs:event_buff', 2, 'addition');

// 永久加成（通过装备 NBT 或物品 AttributeModifiers 实现）
const attribute = entity.getAttribute("minecraft:generic.max_health")
attribute.addPermanentModifier(new AttributeModifier('generic.attack_damage', 'kubejs:event_buff', 2, 'addition'))

// 移除修饰符
entity.removeAttributeModifier('generic.attack_damage', 'kubejs:event_buff');
```

### 建议 {#suggestion}

- **唯一标识符**：每个修饰符应使用唯一字符串（如 `kubejs:xxx`），便于后续精确移除和管理。
- **生命周期选择**：临时增益用 transient，永久加成用装备/物品 NBT。
- **批量管理**：可封装统一的修饰符管理工具，便于事件驱动和装备切换。
- **常见误区**：忽略 UUID 唯一性、混用生命周期、未同步属性等。

::: alert {"type": "info", "title": "进阶说明"}
如需实现装备穿戴时永久属性加成，推荐通过物品 AttributeModifiers NBT 实现（详见 Item/Attribute.md）；如需事件临时增益，直接用 `modifyAttribute` 即可。
:::