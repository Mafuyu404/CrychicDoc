---
title: 持久化数据
description: persistentData 在 KubeJS 中的使用指南
tags:
  - KubeJS
  - persistentData
  - Data
  - Storage
progress: 90
---

# {{ $frontmatter.title }}

## 概述 {#overview}

`persistentData` 是 KubeJS 提供的自定义数据存储系统，**不是 NBT 数据**。

### 适用范围 {#scope}

`persistentData` 可用于以下对象：
- **实体**（Entity）- 存储实体相关数据
- **玩家**（Player）- 存储玩家个人数据  
- **世界**（Level）- 存储世界级别数据
- **服务器**（Server）- 存储全局数据

## 基础操作 {#basic-operations}

### 数据类型 {#data-types}

| 方法 | 数据类型 | 描述 |
|------|----------|------|
| `putString(key, value)` | 字符串 | 存储文本数据 |
| `getString(key)` | 字符串 | 读取文本数据 |
| `putInt(key, value)` | 整数 | 存储整数 |
| `getInt(key)` | 整数 | 读取整数 |
| `putDouble(key, value)` | 浮点数 | 存储小数 |
| `getDouble(key)` | 浮点数 | 读取小数 |
| `putBoolean(key, value)` | 布尔值 | 存储真/假值 |
| `getBoolean(key)` | 布尔值 | 读取真/假值 |

### 基础示例 {#basic-examples}

```js
// 实体持久化数据存储
entity.persistentData.putString('type', 'boss')
entity.persistentData.putInt('level', 5)
entity.persistentData.putBoolean('defeated', false)

// 数据读取
const type = entity.persistentData.getString('type')
const level = entity.persistentData.getInt('level')
const defeated = entity.persistentData.getBoolean('defeated')
```

## 数据检查与管理 {#data-management}

### 检查数据存在 {#check-existence}

```js
// 检查键是否存在
if (player.persistentData.contains('first_join')) {
    // 键存在，执行相应逻辑
} else {
    // 首次使用，设置默认值
    player.persistentData.putBoolean('first_join', true)
}
```

### 移除数据 {#remove-data}

```js
// 移除指定键
player.persistentData.remove('temp_data')

// 获取所有键
const allKeys = player.persistentData.getAllKeys()
console.log('存储的键:', allKeys)
```

## 复合数据 {#compound-data}

### 存储对象数据 {#storing-objects}

```js
// 创建复合数据
const playerData = player.persistentData.getCompound('stats')
playerData.putInt('deaths', 0)
playerData.putInt('kills', 0)
playerData.putDouble('playtime', 0.0)

// 保存复合数据
player.persistentData.put('stats', playerData)
```

### 读取复合数据 {#reading-compounds}

```js
// 读取复合数据
const stats = player.persistentData.getCompound('stats')
const deaths = stats.getInt('deaths')
const kills = stats.getInt('kills')
const playtime = stats.getDouble('playtime')
```

## 列表数据 {#list-data}

### NBT 标签类型表 {#nbt-tag-types}

在使用 `getList()` 方法时需要指定 NBT 标签类型 ID：

| ID  | 标签类型        | Java 常量名      | 描述                           | 示例用法                           |
| --- | --------------- | ---------------- | ------------------------------ | ---------------------------------- |
| 0   | END_TAG         | `TAG_End`        | 结束标签（内部使用）           | -                                  |
| 1   | BYTE_TAG        | `TAG_Byte`       | 字节（-128 到 127）            | `getList('bytes', 1)`              |
| 2   | SHORT_TAG       | `TAG_Short`      | 短整数（-32768 到 32767）      | `getList('shorts', 2)`             |
| 3   | INT_TAG         | `TAG_Int`        | 整数（-2³¹ 到 2³¹-1）          | `getList('scores', 3)`             |
| 4   | LONG_TAG        | `TAG_Long`       | 长整数（-2⁶³ 到 2⁶³-1）        | `getList('timestamps', 4)`         |
| 5   | FLOAT_TAG       | `TAG_Float`      | 单精度浮点数                   | `getList('floats', 5)`             |
| 6   | DOUBLE_TAG      | `TAG_Double`     | 双精度浮点数                   | `getList('coordinates', 6)`        |
| 7   | BYTE_ARRAY_TAG  | `TAG_ByteArray`  | 字节数组                       | `getList('byteArrays', 7)`         |
| 8   | STRING_TAG      | `TAG_String`     | 字符串                         | `getList('friends', 8)`            |
| 9   | LIST_TAG        | `TAG_List`       | 列表（相同类型元素）           | `getList('nested_lists', 9)`       |
| 10  | COMPOUND_TAG    | `TAG_Compound`   | 复合标签（键值对集合）         | `getList('compounds', 10)`         |
| 11  | INT_ARRAY_TAG   | `TAG_IntArray`   | 整数数组                       | `getList('intArrays', 11)`         |
| 12  | LONG_ARRAY_TAG  | `TAG_LongArray`  | 长整数数组                     | `getList('longArrays', 12)`        |

### 存储列表 {#storing-lists}

```js
// 字符串列表
const friends = player.persistentData.getList('friends', 8) // 8 = STRING_TAG
friends.add('player1')
friends.add('player2')

// 整数列表  
const scores = player.persistentData.getList('scores', 3) // 3 = INT_TAG
scores.add(100)
scores.add(250)

// 双精度浮点数列表
const coordinates = player.persistentData.getList('visited_coords', 6) // 6 = DOUBLE_TAG
coordinates.add(100.5)
coordinates.add(64.0)
coordinates.add(200.75)

// 长整数列表（时间戳）
const timestamps = player.persistentData.getList('event_times', 4) // 4 = LONG_TAG
timestamps.add(Date.now())
timestamps.add(1234567890123)
```

### 读取列表 {#reading-lists}

```js
// 读取字符串列表
const friendsList = player.persistentData.getList('friends', 8) // STRING_TAG
for (let i = 0; i < friendsList.size(); i++) {
    const friendName = friendsList.getString(i)
    console.log('朋友:', friendName)
}

// 读取整数列表
const scoresList = player.persistentData.getList('scores', 3) // INT_TAG
for (let i = 0; i < scoresList.size(); i++) {
    const score = scoresList.getInt(i)
    console.log('分数:', score)
}

// 读取双精度浮点数列表
const coordsList = player.persistentData.getList('visited_coords', 6) // DOUBLE_TAG
for (let i = 0; i < coordsList.size(); i++) {
    const coord = coordsList.getDouble(i)
    console.log('坐标:', coord)
}

// 读取长整数列表
const timestampsList = player.persistentData.getList('event_times', 4) // LONG_TAG
for (let i = 0; i < timestampsList.size(); i++) {
    const timestamp = timestampsList.getLong(i)
    console.log('时间戳:', new Date(timestamp))
}

// 读取字节列表
const bytesList = player.persistentData.getList('flags', 1) // BYTE_TAG
for (let i = 0; i < bytesList.size(); i++) {
    const flag = bytesList.getByte(i)
    console.log('标志:', flag === 1 ? '开启' : '关闭')
}
```

## 实际应用示例 {#examples}

### 玩家数据 {#player}

```js
PlayerEvents.loggedIn(event => {
    const player = event.player
    
    // 检查是否首次加入
    if (!player.persistentData.contains('first_join')) {
        player.persistentData.putBoolean('first_join', true)
        player.persistentData.putLong('join_time', Date.now())
        player.tell('§a欢迎首次加入服务器!')
        
        // 给予新手物品
        player.give('minecraft:bread', 10)
    } else {
        // 更新最后登录时间
        player.persistentData.putLong('last_login', Date.now())
    }
    
    // 增加登录次数
    const loginCount = player.persistentData.getInt('login_count') + 1
    player.persistentData.putInt('login_count', loginCount)
    player.tell(`§7这是你第 ${loginCount} 次登录`)
})
```

### 实体 {#entity}

```js
EntityEvents.spawned(event => {
    const entity = event.entity
    
    if (entity.type === 'minecraft:zombie') {
        // 随机设置僵尸等级
        const level = Math.floor(Math.random() * 5) + 1
        entity.persistentData.putInt('zombie_level', level)
        
        // 根据等级调整属性
        const health = 20 + (level * 5)
        entity.getAttribute('minecraft:generic.max_health').baseValue = health
        entity.health = health
        
        // 设置名称显示等级
        entity.customName = `§c等级 ${level} 僵尸`
        entity.customNameVisible = true
    }
})
```

### 世界数据 {#world}

```js
ServerEvents.tick(event => {
    const level = event.server.overworld()
    
    // 每天更新一次世界数据
    if (event.server.tickCount % 24000 === 0) {
        const currentDay = Math.floor(level.dayTime / 24000)
        level.persistentData.putInt('current_day', currentDay)
        
        // 记录特殊事件
        if (currentDay % 7 === 0) {
            level.persistentData.putBoolean('special_week', true)
            event.server.tell('§6今天是特殊的一周!')
        }
    }
})
```

## 标签类型选择指南 {#tag-selection-guide}

### 常用数据类型推荐 {#common-data-recommendations}

| 数据类型           | 推荐标签 | 标签ID | 说明                               |
| ------------------ | -------- | ------ | ---------------------------------- |
| 玩家名称/ID        | STRING   | 8      | 存储文本信息                       |
| 分数/等级/数量     | INT      | 3      | 普通整数范围足够                   |
| 时间戳/大数值      | LONG     | 4      | 防止数值溢出                       |
| 坐标/小数值        | DOUBLE   | 6      | 支持小数精度                       |
| 开关/标志状态      | BYTE     | 1      | 0/1 表示布尔值，节省存储空间       |
| 复杂数据结构       | COMPOUND | 10     | 包含多种数据类型的对象             |
| 嵌套列表          | LIST     | 9      | 存储其他列表的列表                 |

### 实际应用示例 {#practical-examples}

```js
// 玩家好友系统
function addFriend(player, friendName) {
    const friends = player.persistentData.getList('friends', 8) // STRING_TAG
    if (!friends.contains(friendName)) {
        friends.add(friendName)
        player.tell(`§a已添加 ${friendName} 为好友`)
    }
}

// 玩家访问坐标记录
function recordVisitedLocation(player, x, y, z) {
    const locations = player.persistentData.getList('visited_locations', 10) // COMPOUND_TAG
    
    const locationData = {}
    locationData.putDouble('x', x)
    locationData.putDouble('y', y) 
    locationData.putDouble('z', z)
    locationData.putLong('timestamp', Date.now())
    locationData.putString('dimension', player.level.dimension.toString())
    
    locations.add(locationData)
    
    // 限制记录数量
    if (locations.size() > 50) {
        locations.remove(0)
    }
}

// 玩家成就/标志系统
function setPlayerFlag(player, flagName, value) {
    const flags = player.persistentData.getList('achievement_flags', 1) // BYTE_TAG
    const flagNames = player.persistentData.getList('flag_names', 8) // STRING_TAG
    
    let index = -1
    for (let i = 0; i < flagNames.size(); i++) {
        if (flagNames.getString(i) === flagName) {
            index = i
            break
        }
    }
    
    if (index === -1) {
        // 新标志
        flagNames.add(flagName)
        flags.add(value ? 1 : 0)
    } else {
        // 更新现有标志
        flags.set(index, value ? 1 : 0)
    }
}

// 服务器统计数据
function updateServerStats(server) {
    const stats = server.persistentData.getList('daily_stats', 10) // COMPOUND_TAG
    
    const todayStats = {}
    todayStats.putLong('date', Date.now())
    todayStats.putInt('player_count', server.playerList.players.length)
    todayStats.putInt('entity_count', server.overworld().entities.size())
    todayStats.putDouble('tps', server.getAverageTickTime())
    
    stats.add(todayStats)
    
    // 只保留最近30天的数据
    if (stats.size() > 30) {
        stats.remove(0)
    }
}
```

## 性能与最佳实践 {#performance-best-practices}

### 数据量控制 {#data-size-control}

```js
// ✅ 良好实践：限制列表大小
function addToHistory(player, data) {
    const history = player.persistentData.getList('action_history', 8)
    history.add(data)
    
    // 限制历史记录数量，避免数据过大
    const maxSize = 100
    if (history.size() > maxSize) {
        history.remove(0) // 移除最早的记录
    }
}

// ❌ 不良实践：无限制添加数据
function badAddToHistory(player, data) {
    const history = player.persistentData.getList('action_history', 8)
    history.add(data) // 可能导致数据无限增长
}
```

### 数据类型选择 {#data-type-selection}

```js
// ✅ 根据数据范围选择合适的类型
const smallNumbers = player.persistentData.getList('levels', 1)    // BYTE_TAG: -128 到 127
const normalNumbers = player.persistentData.getList('scores', 3)   // INT_TAG: 普通整数
const bigNumbers = player.persistentData.getList('timestamps', 4)  // LONG_TAG: 大整数
const preciseNumbers = player.persistentData.getList('coords', 6)  // DOUBLE_TAG: 小数

// ❌ 过度使用大类型浪费空间
const wasteful = player.persistentData.getList('small_flags', 4)   // LONG_TAG 用于存储 0-10 的值
```

### 检查与错误处理 {#error-handling}

```js
// ✅ 安全的列表操作
function safeGetListItem(persistentData, listName, tagType, index) {
    try {
        const list = persistentData.getList(listName, tagType)
        if (index >= 0 && index < list.size()) {
            switch (tagType) {
                case 1: return list.getByte(index)
                case 3: return list.getInt(index) 
                case 4: return list.getLong(index)
                case 6: return list.getDouble(index)
                case 8: return list.getString(index)
                case 10: return list.getCompound(index)
                default: return null
            }
        }
    } catch (error) {
        console.error(`获取列表项失败: ${error}`)
    }
    return null
}
```