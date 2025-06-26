---
title: 映射接口
description: Java Map 接口在 KubeJS 中的使用指南
tags:
  - KubeJS
  - Map
  - HashMap
  - 键值对
authors:
  - 文档贡献者
progress: 95
---

# {{ $frontmatter.title }}

## 概述

`Map` 是键值对集合，提供高效的查找、插入和删除操作。在KubeJS中常用于缓存数据、配置管理、索引构建等场景。

:::: info **Map特性**
::: justify
- **键值对存储**: 每个键关联一个值
- **快速查找**: O(1)平均时间复杂度
- **无重复键**: 键必须唯一
- **任意类型**: 键和值可以是任意类型
:::
::::

## 基础操作

### 创建和基本方法

```js
/**
 * Map基础操作
 */
function mapBasicOperations() {
    // 创建Map
    const itemMap = new Map();
    const configMap = new Map([
        ['spawn_protection', 16],
        ['max_players', 20],
        ['difficulty', 'normal']
    ]);
    
    // 设置键值对
    itemMap.set('minecraft:stone', { count: 64, durability: 100 });
    itemMap.set('minecraft:diamond', { count: 16, durability: 1561 });
    
    // 获取值
    const stoneData = itemMap.get('minecraft:stone');
    const maxPlayers = configMap.get('max_players');
    
    // 检查键是否存在
    const hasStone = itemMap.has('minecraft:stone');
    const hasGold = itemMap.has('minecraft:gold_ingot');
    
    // 删除键值对
    itemMap.delete('minecraft:stone');
    
    // 获取大小
    const size = itemMap.size;
    
    // 清空Map
    itemMap.clear();
    
    console.log(`石头数据: ${JSON.stringify(stoneData)}`);
    console.log(`最大玩家数: ${maxPlayers}`);
    console.log(`Map大小: ${size}`);
}
```

### 遍历操作

```js
/**
 * Map遍历方法
 * @param {Map} map
 */
function mapIteration(map) {
    // 方法1: forEach遍历
    map.forEach((value, key) => {
        console.log(`${key}: ${value}`);
    });
    
    // 方法2: for-of遍历条目
    for (const [key, value] of map) {
        console.log(`${key}: ${value}`);
    }
    
    // 方法3: 遍历键
    for (const key of map.keys()) {
        console.log(`键: ${key}`);
    }
    
    // 方法4: 遍历值
    for (const value of map.values()) {
        console.log(`值: ${value}`);
    }
    
    // 方法5: 遍历条目
    for (const entry of map.entries()) {
        console.log(`条目: ${entry[0]} = ${entry[1]}`);
    }
}
```

## 高级操作

### Map转换和操作

```js
/**
 * Map转换操作
 * @param {Map} map
 */
function mapTransformations(map) {
    // Map转换为数组
    const keysArray = Array.from(map.keys());
    const valuesArray = Array.from(map.values());
    const entriesArray = Array.from(map.entries());
    
    // Map转换为对象
    const mapToObject = Object.fromEntries(map);
    
    // 对象转换为Map
    const objectToMap = new Map(Object.entries({
        a: 1,
        b: 2,
        c: 3
    }));
    
    // 过滤Map
    function filterMap(map, predicate) {
        const filtered = new Map();
        for (const [key, value] of map) {
            if (predicate(key, value)) {
                filtered.set(key, value);
            }
        }
        return filtered;
    }
    
    // 映射Map值
    function mapValues(map, mapper) {
        const mapped = new Map();
        for (const [key, value] of map) {
            mapped.set(key, mapper(value, key));
        }
        return mapped;
    }
    
    // 映射Map键
    function mapKeys(map, mapper) {
        const mapped = new Map();
        for (const [key, value] of map) {
            mapped.set(mapper(key, value), value);
        }
        return mapped;
    }
    
    // 使用示例
    const numbers = new Map([
        ['one', 1],
        ['two', 2],
        ['three', 3],
        ['four', 4]
    ]);
    
    // 过滤偶数
    const evenNumbers = filterMap(numbers, (key, value) => value % 2 === 0);
    
    // 值乘以10
    const multiplied = mapValues(numbers, value => value * 10);
    
    console.log('偶数:', evenNumbers);
    console.log('乘以10:', multiplied);
}
```

## 实际应用

### 缓存系统

```js
/**
 * 缓存管理系统
 */
class CacheManager {
    constructor(maxSize = 100, ttl = 300000) { // 5分钟TTL
        this.cache = new Map();
        this.timestamps = new Map();
        this.maxSize = maxSize;
        this.ttl = ttl;
    }
    
    // 设置缓存
    set(key, value) {
        // 清理过期缓存
        this.cleanup();
        
        // 如果超过最大大小，删除最旧的
        if (this.cache.size >= this.maxSize) {
            this.evictOldest();
        }
        
        this.cache.set(key, value);
        this.timestamps.set(key, Date.now());
    }
    
    // 获取缓存
    get(key) {
        if (!this.cache.has(key)) {
            return null;
        }
        
        // 检查是否过期
        const timestamp = this.timestamps.get(key);
        if (Date.now() - timestamp > this.ttl) {
            this.cache.delete(key);
            this.timestamps.delete(key);
            return null;
        }
        
        return this.cache.get(key);
    }
    
    // 检查缓存存在
    has(key) {
        return this.get(key) !== null;
    }
    
    // 删除缓存
    delete(key) {
        this.cache.delete(key);
        this.timestamps.delete(key);
    }
    
    // 清空缓存
    clear() {
        this.cache.clear();
        this.timestamps.clear();
    }
    
    // 清理过期缓存
    cleanup() {
        const now = Date.now();
        const expiredKeys = [];
        
        for (const [key, timestamp] of this.timestamps) {
            if (now - timestamp > this.ttl) {
                expiredKeys.push(key);
            }
        }
        
        expiredKeys.forEach(key => {
            this.cache.delete(key);
            this.timestamps.delete(key);
        });
    }
    
    // 驱逐最旧的缓存
    evictOldest() {
        let oldestKey = null;
        let oldestTime = Infinity;
        
        for (const [key, timestamp] of this.timestamps) {
            if (timestamp < oldestTime) {
                oldestTime = timestamp;
                oldestKey = key;
            }
        }
        
        if (oldestKey) {
            this.delete(oldestKey);
        }
    }
    
    // 获取缓存统计
    getStats() {
        return {
            size: this.cache.size,
            maxSize: this.maxSize,
            hitRate: this.hitCount / (this.hitCount + this.missCount) || 0
        };
    }
}

// 使用示例
const cache = new CacheManager(50, 60000); // 50个条目，1分钟TTL

cache.set('player_data_123', { name: 'Player', level: 50 });
const playerData = cache.get('player_data_123');
console.log('玩家数据:', playerData);
```

### 配置管理

```js
/**
 * 配置管理系统
 */
class ConfigManager {
    constructor() {
        this.configs = new Map();
        this.defaults = new Map();
        this.listeners = new Map();
    }
    
    // 设置默认值
    setDefault(key, value) {
        this.defaults.set(key, value);
        
        // 如果没有设置值，使用默认值
        if (!this.configs.has(key)) {
            this.configs.set(key, value);
        }
    }
    
    // 设置配置
    set(key, value) {
        const oldValue = this.configs.get(key);
        this.configs.set(key, value);
        
        // 触发监听器
        this.notifyListeners(key, value, oldValue);
    }
    
    // 获取配置
    get(key, defaultValue = null) {
        if (this.configs.has(key)) {
            return this.configs.get(key);
        }
        
        if (this.defaults.has(key)) {
            return this.defaults.get(key);
        }
        
        return defaultValue;
    }
    
    // 获取数字配置
    getNumber(key, defaultValue = 0) {
        const value = this.get(key, defaultValue);
        return typeof value === 'number' ? value : Number(value) || defaultValue;
    }
    
    // 获取布尔配置
    getBoolean(key, defaultValue = false) {
        const value = this.get(key, defaultValue);
        return typeof value === 'boolean' ? value : Boolean(value);
    }
    
    // 获取字符串配置
    getString(key, defaultValue = '') {
        const value = this.get(key, defaultValue);
        return String(value);
    }
    
    // 添加配置监听器
    addListener(key, listener) {
        if (!this.listeners.has(key)) {
            this.listeners.set(key, []);
        }
        this.listeners.get(key).push(listener);
    }
    
    // 移除监听器
    removeListener(key, listener) {
        if (this.listeners.has(key)) {
            const listeners = this.listeners.get(key);
            const index = listeners.indexOf(listener);
            if (index > -1) {
                listeners.splice(index, 1);
            }
        }
    }
    
    // 通知监听器
    notifyListeners(key, newValue, oldValue) {
        if (this.listeners.has(key)) {
            this.listeners.get(key).forEach(listener => {
                try {
                    listener(newValue, oldValue, key);
                } catch (error) {
                    console.error('配置监听器错误:', error);
                }
            });
        }
    }
    
    // 批量设置
    setAll(configObject) {
        Object.entries(configObject).forEach(([key, value]) => {
            this.set(key, value);
        });
    }
    
    // 获取所有配置
    getAll() {
        return Object.fromEntries(this.configs);
    }
    
    // 重置为默认值
    reset(key = null) {
        if (key) {
            if (this.defaults.has(key)) {
                this.set(key, this.defaults.get(key));
            } else {
                this.configs.delete(key);
            }
        } else {
            this.configs.clear();
            this.defaults.forEach((value, key) => {
                this.configs.set(key, value);
            });
        }
    }
}

// 使用示例
const config = new ConfigManager();

// 设置默认配置
config.setDefault('max_players', 20);
config.setDefault('spawn_protection', 16);
config.setDefault('enable_pvp', true);

// 添加监听器
config.addListener('max_players', (newValue, oldValue) => {
    console.log(`最大玩家数从 ${oldValue} 变更为 ${newValue}`);
});

// 设置配置
config.set('max_players', 30);
console.log('最大玩家数:', config.getNumber('max_players'));
```

### 索引和查找

```js
/**
 * 多索引数据管理
 */
class MultiIndexMap {
    constructor() {
        this.data = new Map();
        this.indexes = new Map();
    }
    
    // 添加索引
    addIndex(indexName, keyExtractor) {
        this.indexes.set(indexName, {
            keyExtractor: keyExtractor,
            index: new Map()
        });
        
        // 为现有数据建立索引
        for (const [id, item] of this.data) {
            this.updateIndex(indexName, id, item);
        }
    }
    
    // 设置数据
    set(id, item) {
        // 如果是更新，先清理旧索引
        if (this.data.has(id)) {
            this.removeFromAllIndexes(id);
        }
        
        this.data.set(id, item);
        
        // 更新所有索引
        for (const indexName of this.indexes.keys()) {
            this.updateIndex(indexName, id, item);
        }
    }
    
    // 获取数据
    get(id) {
        return this.data.get(id);
    }
    
    // 删除数据
    delete(id) {
        if (this.data.has(id)) {
            this.removeFromAllIndexes(id);
            this.data.delete(id);
        }
    }
    
    // 通过索引查找
    findByIndex(indexName, key) {
        const indexData = this.indexes.get(indexName);
        if (!indexData) return [];
        
        const ids = indexData.index.get(key) || [];
        return ids.map(id => this.data.get(id)).filter(item => item !== undefined);
    }
    
    // 更新索引
    updateIndex(indexName, id, item) {
        const indexData = this.indexes.get(indexName);
        if (!indexData) return;
        
        const key = indexData.keyExtractor(item);
        if (!indexData.index.has(key)) {
            indexData.index.set(key, []);
        }
        
        const ids = indexData.index.get(key);
        if (!ids.includes(id)) {
            ids.push(id);
        }
    }
    
    // 从所有索引中移除
    removeFromAllIndexes(id) {
        const item = this.data.get(id);
        if (!item) return;
        
        for (const [indexName, indexData] of this.indexes) {
            const key = indexData.keyExtractor(item);
            const ids = indexData.index.get(key);
            if (ids) {
                const index = ids.indexOf(id);
                if (index > -1) {
                    ids.splice(index, 1);
                    if (ids.length === 0) {
                        indexData.index.delete(key);
                    }
                }
            }
        }
    }
    
    // 获取统计信息
    getStats() {
        const indexStats = {};
        for (const [indexName, indexData] of this.indexes) {
            indexStats[indexName] = {
                keyCount: indexData.index.size,
                averageItemsPerKey: this.data.size / indexData.index.size || 0
            };
        }
        
        return {
            totalItems: this.data.size,
            indexes: indexStats
        };
    }
}

// 使用示例：玩家数据管理
const playerManager = new MultiIndexMap();

// 添加索引
playerManager.addIndex('byLevel', player => Math.floor(player.level / 10)); // 按等级段
playerManager.addIndex('byClass', player => player.class); // 按职业
playerManager.addIndex('byGuild', player => player.guild); // 按公会

// 添加玩家数据
playerManager.set('player1', {
    name: 'Alice',
    level: 25,
    class: 'warrior',
    guild: 'Dragons'
});

playerManager.set('player2', {
    name: 'Bob',
    level: 32,
    class: 'mage',
    guild: 'Dragons'
});

// 查找数据
const level20Players = playerManager.findByIndex('byLevel', 2); // 20-29级
const warriors = playerManager.findByIndex('byClass', 'warrior');
const dragonMembers = playerManager.findByIndex('byGuild', 'Dragons');

console.log('20-29级玩家:', level20Players);
console.log('战士:', warriors);
console.log('龙族公会成员:', dragonMembers);
```

### Map工具类

```js
/**
 * Map工具类
 */
class MapUtils {
    /**
     * 合并多个Map
     */
    static merge(...maps) {
        const result = new Map();
        maps.forEach(map => {
            for (const [key, value] of map) {
                result.set(key, value);
            }
        });
        return result;
    }
    
    /**
     * Map交集
     */
    static intersection(map1, map2) {
        const result = new Map();
        for (const [key, value] of map1) {
            if (map2.has(key)) {
                result.set(key, value);
            }
        }
        return result;
    }
    
    /**
     * Map差集
     */
    static difference(map1, map2) {
        const result = new Map();
        for (const [key, value] of map1) {
            if (!map2.has(key)) {
                result.set(key, value);
            }
        }
        return result;
    }
    
    /**
     * 反转Map（值变键，键变值）
     */
    static invert(map) {
        const result = new Map();
        for (const [key, value] of map) {
            result.set(value, key);
        }
        return result;
    }
    
    /**
     * 按值排序
     */
    static sortByValue(map, compareFn = null) {
        const entries = Array.from(map.entries());
        entries.sort((a, b) => {
            if (compareFn) {
                return compareFn(a[1], b[1]);
            }
            return a[1] < b[1] ? -1 : a[1] > b[1] ? 1 : 0;
        });
        return new Map(entries);
    }
    
    /**
     * 按键排序
     */
    static sortByKey(map, compareFn = null) {
        const entries = Array.from(map.entries());
        entries.sort((a, b) => {
            if (compareFn) {
                return compareFn(a[0], b[0]);
            }
            return a[0] < b[0] ? -1 : a[0] > b[0] ? 1 : 0;
        });
        return new Map(entries);
    }
    
    /**
     * 获取Map的子集
     */
    static subset(map, keys) {
        const result = new Map();
        keys.forEach(key => {
            if (map.has(key)) {
                result.set(key, map.get(key));
            }
        });
        return result;
    }
    
    /**
     * 计数器Map（用于统计）
     */
    static createCounter(items, keyExtractor = item => item) {
        const counter = new Map();
        items.forEach(item => {
            const key = keyExtractor(item);
            counter.set(key, (counter.get(key) || 0) + 1);
        });
        return counter;
    }
    
    /**
     * 分组Map
     */
    static groupBy(items, keyExtractor) {
        const groups = new Map();
        items.forEach(item => {
            const key = keyExtractor(item);
            if (!groups.has(key)) {
                groups.set(key, []);
            }
            groups.get(key).push(item);
        });
        return groups;
    }
}

// 使用示例
const map1 = new Map([['a', 1], ['b', 2]]);
const map2 = new Map([['b', 3], ['c', 4]]);

const merged = MapUtils.merge(map1, map2);
console.log('合并后:', merged);

const items = ['apple', 'banana', 'apple', 'orange', 'banana', 'apple'];
const counter = MapUtils.createCounter(items);
console.log('计数器:', counter);
```

## 性能和最佳实践

### 性能对比

```js
/**
 * Map vs Object 性能对比
 */
function mapVsObjectPerformance() {
    console.log(`
    Map vs Object 性能对比:
    
    插入性能:
    - Map: O(1) 平均
    - Object: O(1) 平均
    
    查找性能:
    - Map: O(1) 平均
    - Object: O(1) 平均
    
    删除性能:
    - Map: O(1) 平均
    - Object: O(1) 但可能触发属性重排
    
    遍历性能:
    - Map: for-of 最快
    - Object: Object.keys() + for循环
    
    内存使用:
    - Map: 略高，但更可预测
    - Object: 略低，但存在隐藏类优化
    
    使用建议:
    - 频繁增删：优选Map
    - 简单配置：可用Object
    - 非字符串键：必须用Map
    - 需要遍历顺序：优选Map
    `);
}

/**
 * Map性能优化技巧
 */
function mapOptimization() {
    // 1. 预设容量（JavaScript Map没有直接方法，但可以批量初始化）
    const largeMap = new Map();
    const initialData = Array.from({ length: 1000 }, (_, i) => [`key${i}`, i]);
    initialData.forEach(([key, value]) => largeMap.set(key, value));
    
    // 2. 使用合适的键类型
    const stringKeyMap = new Map(); // 字符串键
    const numberKeyMap = new Map(); // 数字键
    const objectKeyMap = new Map(); // 对象键（注意内存泄漏）
    
    // 3. 及时清理不需要的键
    function cleanupMap(map, isExpired) {
        const keysToDelete = [];
        for (const [key, value] of map) {
            if (isExpired(key, value)) {
                keysToDelete.push(key);
            }
        }
        keysToDelete.forEach(key => map.delete(key));
    }
    
    // 4. 批量操作比单个操作效率高
    function batchSet(map, entries) {
        entries.forEach(([key, value]) => map.set(key, value));
    }
    
    // 5. 使用WeakMap避免内存泄漏（适用于对象键）
    const weakMap = new WeakMap();
    
    console.log('Map性能优化完成');
}
```

## 相关文档

- [Collection 集合](./Collection.md)
- [List 列表](./List.md)
- [Supplier 供应者](./Supplier.md)
