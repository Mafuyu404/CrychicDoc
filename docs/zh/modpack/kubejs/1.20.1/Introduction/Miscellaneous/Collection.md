---
title: 集合
description: Java Collection 接口在 KubeJS 中的使用指南
tags:
  - KubeJS
  - Collection
  - Java
  - 数据结构
authors:
  - 文档贡献者
progress: 95
hidden: false
priority: 0
---

# {{ $frontmatter.title }}

## 概述

`Collection` 是Java集合框架的基础接口，在KubeJS中广泛用于处理实体列表、物品集合等数据结构。

:::: info **类型层次**
::: justify
```
Collection<E>
├── List<E> (有序，可重复)
│   ├── ArrayList<E>
│   └── LinkedList<E>
├── Set<E> (无序，不重复)
│   ├── HashSet<E>
│   └── TreeSet<E>
└── Queue<E> (队列)
    ├── LinkedList<E>
    └── PriorityQueue<E>
```
:::
::::

## 基础操作

### 常用方法

```js
/**
 * Collection基础操作
 * @param {Internal.Collection} collection
 */
function collectionBasicOperations(collection) {
    // 添加元素
    collection.add('minecraft:stone');
    collection.addAll(['minecraft:dirt', 'minecraft:grass']);
    
    // 查询操作
    const size = collection.size();
    const isEmpty = collection.isEmpty();
    const contains = collection.contains('minecraft:stone');
    const containsAll = collection.containsAll(['minecraft:stone', 'minecraft:dirt']);
    
    console.log(`集合大小: ${size}`);
    console.log(`是否为空: ${isEmpty}`);
    console.log(`包含石头: ${contains}`);
    
    // 移除操作
    collection.remove('minecraft:stone');
    collection.removeAll(['minecraft:dirt', 'minecraft:grass']);
    collection.clear(); // 清空所有元素
    
    // 转换为数组
    const array = collection.toArray();
    console.log(`转换为数组: ${array}`);
}
```

### 遍历操作

```js
/**
 * Collection遍历方法
 * @param {Internal.Collection} collection
 */
function collectionIteration(collection) {
    // 方法1: forEach遍历
    collection.forEach(item => {
        console.log(`物品: ${item}`);
    });
    
    // 方法2: 迭代器遍历
    const iterator = collection.iterator();
    while (iterator.hasNext()) {
        const item = iterator.next();
        console.log(`物品: ${item}`);
        
        // 安全删除
        if (item === 'minecraft:dirt') {
            iterator.remove();
        }
    }
    
    // 方法3: for-of遍历（如果支持）
    for (const item of collection) {
        console.log(`物品: ${item}`);
    }
}
```

## 实际应用

### 实体集合操作

```js
/**
 * 实体集合管理
 * @param {Internal.Level} level
 */
function entityCollectionManagement(level) {
    // 获取所有玩家
    const players = level.players;
    
    // 筛选在线玩家
    const onlinePlayers = [];
    players.forEach(player => {
        if (player.isAlive()) {
            onlinePlayers.add(player);
        }
    });
    
    // 按距离排序玩家集合
    const centerX = 0, centerZ = 0;
    const sortedPlayers = players.stream()
        .sorted((p1, p2) => {
            const dist1 = Math.sqrt(p1.x * p1.x + p1.z * p1.z);
            const dist2 = Math.sqrt(p2.x * p2.x + p2.z * p2.z);
            return dist1 - dist2;
        })
        .collect();
    
    console.log(`最近的玩家: ${sortedPlayers.get(0).username}`);
}

/**
 * 物品集合处理
 * @param {Internal.Player} player
 */
function itemCollectionProcessing(player) {
    const inventory = player.inventory;
    const items = [];
    
    // 收集所有非空物品
    for (let i = 0; i < inventory.containerSize; i++) {
        const item = inventory.getItem(i);
        if (!item.isEmpty()) {
            items.add(item);
        }
    }
    
    // 按稀有度分组
    const itemsByRarity = new Map();
    items.forEach(item => {
        const rarity = item.rarity || 'common';
        if (!itemsByRarity.has(rarity)) {
            itemsByRarity.set(rarity, []);
        }
        itemsByRarity.get(rarity).push(item);
    });
    
    // 输出分组结果
    itemsByRarity.forEach((itemList, rarity) => {
        console.log(`${rarity}级物品: ${itemList.length}个`);
    });
}
```

### 集合过滤和转换

```js
/**
 * 集合过滤操作
 * @param {Internal.Collection} source
 */
function collectionFiltering(source) {
    /**
     * 过滤集合元素
     */
    function filterCollection(collection, predicate) {
        const filtered = [];
        collection.forEach(item => {
            if (predicate(item)) {
                filtered.push(item);
            }
        });
        return filtered;
    }
    
    /**
     * 映射集合元素
     */
    function mapCollection(collection, mapper) {
        const mapped = [];
        collection.forEach(item => {
            mapped.push(mapper(item));
        });
        return mapped;
    }
    
    /**
     * 归约集合元素
     */
    function reduceCollection(collection, reducer, initialValue) {
        let result = initialValue;
        collection.forEach(item => {
            result = reducer(result, item);
        });
        return result;
    }
    
    // 使用示例
    const numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
    
    // 过滤偶数
    const evenNumbers = filterCollection(numbers, n => n % 2 === 0);
    console.log(`偶数: ${evenNumbers}`);
    
    // 映射为平方
    const squares = mapCollection(numbers, n => n * n);
    console.log(`平方: ${squares}`);
    
    // 求和
    const sum = reduceCollection(numbers, (acc, n) => acc + n, 0);
    console.log(`总和: ${sum}`);
}
```

### 集合工具类

```js
/**
 * 集合工具类
 */
class CollectionUtils {
    /**
     * 创建不同类型的集合
     */
    static createList() {
        return [];
    }
    
    static createSet() {
        return new Set();
    }
    
    static createMap() {
        return new Map();
    }
    
    /**
     * 集合去重
     */
    static unique(collection) {
        const set = new Set();
        collection.forEach(item => set.add(item));
        return Array.from(set);
    }
    
    /**
     * 集合交集
     */
    static intersection(collection1, collection2) {
        const result = [];
        collection1.forEach(item => {
            if (collection2.includes(item)) {
                result.push(item);
            }
        });
        return result;
    }
    
    /**
     * 集合并集
     */
    static union(collection1, collection2) {
        const result = [...collection1];
        collection2.forEach(item => {
            if (!result.includes(item)) {
                result.push(item);
            }
        });
        return result;
    }
    
    /**
     * 集合差集
     */
    static difference(collection1, collection2) {
        const result = [];
        collection1.forEach(item => {
            if (!collection2.includes(item)) {
                result.push(item);
            }
        });
        return result;
    }
    
    /**
     * 分批处理
     */
    static chunk(collection, size) {
        const chunks = [];
        const array = Array.from(collection);
        
        for (let i = 0; i < array.length; i += size) {
            chunks.push(array.slice(i, i + size));
        }
        
        return chunks;
    }
    
    /**
     * 查找元素
     */
    static find(collection, predicate) {
        for (const item of collection) {
            if (predicate(item)) {
                return item;
            }
        }
        return null;
    }
    
    /**
     * 检查所有元素
     */
    static every(collection, predicate) {
        for (const item of collection) {
            if (!predicate(item)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 检查任意元素
     */
    static some(collection, predicate) {
        for (const item of collection) {
            if (predicate(item)) {
                return true;
            }
        }
        return false;
    }
}

// 使用示例
const items = ['stone', 'dirt', 'grass', 'stone', 'wood'];
const uniqueItems = CollectionUtils.unique(items);
console.log(`去重后: ${uniqueItems}`);

const chunks = CollectionUtils.chunk(items, 2);
console.log(`分批: ${chunks}`);
```

## 性能考虑

### 集合选择指南

```js
/**
 * 集合性能对比
 */
function collectionPerformanceGuide() {
    console.log(`
    集合类型选择指南:
    
    ArrayList:
    - 优点: 随机访问快O(1)，遍历快
    - 缺点: 插入删除慢O(n)
    - 适用: 读多写少，需要索引访问
    
    LinkedList:
    - 优点: 插入删除快O(1)
    - 缺点: 随机访问慢O(n)
    - 适用: 频繁插入删除，不需要随机访问
    
    HashSet:
    - 优点: 查找添加删除都是O(1)
    - 缺点: 无序，不能重复
    - 适用: 需要快速查找，不关心顺序
    
    TreeSet:
    - 优点: 有序，查找添加删除O(log n)
    - 缺点: 比HashSet慢
    - 适用: 需要排序的唯一元素集合
    `);
}

/**
 * 性能优化技巧
 */
function collectionOptimization() {
    // 1. 预分配容量
    const list = new Array(1000); // 如果知道大概大小
    
    // 2. 批量操作
    const batch = ['item1', 'item2', 'item3'];
    list.push(...batch); // 比逐个添加快
    
    // 3. 使用合适的数据结构
    const frequentLookup = new Set(['common1', 'common2']); // 快速查找
    
    // 4. 避免在循环中创建新集合
    function processItems(items) {
        const result = []; // 在外部创建
        items.forEach(item => {
            result.push(processItem(item));
        });
        return result;
    }
    
    function processItem(item) {
        return item.toUpperCase();
    }
}
```

## 相关文档

- [List 列表](./List.md)
- [Map 映射](./Map.md)
- [Supplier 供应者](./Supplier.md)
