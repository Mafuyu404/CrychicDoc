---
title: 列表接口
description: Java List 接口在 KubeJS 中的使用指南
tags:
  - KubeJS
  - List
  - Array
  - 数据结构
authors:
  - 文档贡献者
progress: 95
---

# {{ $frontmatter.title }}

## 概述

`List` 是有序的集合接口，允许重复元素。在KubeJS中常用于存储物品列表、实体序列等需要保持顺序的数据。

:::: info **List特性**
::: justify
- **有序性**: 元素按插入顺序排列
- **可重复**: 允许相同元素多次出现
- **索引访问**: 支持通过索引快速访问元素
- **动态大小**: 可动态增减元素
:::
::::

## 基础操作

### 创建和基本方法

```js
/**
 * List基础操作
 */
function listBasicOperations() {
    // 创建List
    const itemList = [];
    const playerList = new ArrayList();
    
    // 添加元素
    itemList.push('minecraft:stone');
    itemList.push('minecraft:dirt');
    playerList.add(player);
    
    // 插入元素
    itemList.splice(1, 0, 'minecraft:grass'); // 在索引1处插入
    
    // 获取元素
    const firstItem = itemList[0];
    const lastItem = itemList[itemList.length - 1];
    
    // 查找元素
    const stoneIndex = itemList.indexOf('minecraft:stone');
    const hasStone = itemList.includes('minecraft:stone');
    
    // 删除元素
    itemList.splice(1, 1); // 删除索引1的元素
    itemList.pop(); // 删除最后一个元素
    const removed = itemList.shift(); // 删除第一个元素
    
    console.log(`列表大小: ${itemList.length}`);
    console.log(`第一个物品: ${firstItem}`);
}
```

### 遍历方法

```js
/**
 * List遍历操作
 * @param {Array} list
 */
function listIteration(list) {
    // 方法1: for循环（最快）
    for (let i = 0; i < list.length; i++) {
        console.log(`索引${i}: ${list[i]}`);
    }
    
    // 方法2: forEach
    list.forEach((item, index) => {
        console.log(`索引${index}: ${item}`);
    });
    
    // 方法3: for-of循环
    for (const item of list) {
        console.log(`物品: ${item}`);
    }
    
    // 方法4: for-in循环（不推荐用于数组）
    for (const index in list) {
        console.log(`索引${index}: ${list[index]}`);
    }
    
    // 方法5: while循环
    let i = 0;
    while (i < list.length) {
        console.log(`索引${i}: ${list[i]}`);
        i++;
    }
}
```

## 高级操作

### 排序和搜索

```js
/**
 * List排序和搜索
 * @param {Array} list
 */
function listSortingAndSearching(list) {
    // 基础排序
    const numbers = [3, 1, 4, 1, 5, 9, 2, 6];
    numbers.sort((a, b) => a - b); // 升序
    numbers.sort((a, b) => b - a); // 降序
    
    // 字符串排序
    const items = ['stone', 'dirt', 'grass', 'wood'];
    items.sort(); // 字典序
    items.sort((a, b) => a.length - b.length); // 按长度排序
    
    // 对象排序
    const players = [
        { name: 'Alice', level: 10 },
        { name: 'Bob', level: 15 },
        { name: 'Charlie', level: 8 }
    ];
    
    // 按等级排序
    players.sort((a, b) => b.level - a.level);
    
    // 多条件排序
    players.sort((a, b) => {
        if (a.level === b.level) {
            return a.name.localeCompare(b.name);
        }
        return b.level - a.level;
    });
    
    // 二分查找（需要已排序）
    function binarySearch(sortedArray, target) {
        let left = 0;
        let right = sortedArray.length - 1;
        
        while (left <= right) {
            const mid = Math.floor((left + right) / 2);
            const midValue = sortedArray[mid];
            
            if (midValue === target) {
                return mid;
            } else if (midValue < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return -1; // 未找到
    }
    
    const sortedNumbers = [1, 3, 5, 7, 9, 11, 13];
    const index = binarySearch(sortedNumbers, 7);
    console.log(`7的索引: ${index}`);
}
```

### 函数式编程

```js
/**
 * List函数式编程方法
 * @param {Array} list
 */
function listFunctionalProgramming(list) {
    const numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
    
    // map: 转换元素
    const doubled = numbers.map(n => n * 2);
    const squares = numbers.map(n => n * n);
    
    // filter: 过滤元素
    const evenNumbers = numbers.filter(n => n % 2 === 0);
    const largeNumbers = numbers.filter(n => n > 5);
    
    // reduce: 归约操作
    const sum = numbers.reduce((acc, n) => acc + n, 0);
    const product = numbers.reduce((acc, n) => acc * n, 1);
    const max = numbers.reduce((acc, n) => Math.max(acc, n), -Infinity);
    
    // find: 查找第一个匹配
    const firstEven = numbers.find(n => n % 2 === 0);
    const firstLarge = numbers.find(n => n > 5);
    
    // findIndex: 查找索引
    const firstEvenIndex = numbers.findIndex(n => n % 2 === 0);
    
    // some: 检查是否有元素满足条件
    const hasEven = numbers.some(n => n % 2 === 0);
    const hasNegative = numbers.some(n => n < 0);
    
    // every: 检查是否所有元素满足条件
    const allPositive = numbers.every(n => n > 0);
    const allEven = numbers.every(n => n % 2 === 0);
    
    // 链式调用
    const result = numbers
        .filter(n => n % 2 === 0)
        .map(n => n * n)
        .reduce((acc, n) => acc + n, 0);
    
    console.log(`偶数平方和: ${result}`);
}
```

## 实际应用

### 实体列表管理

```js
/**
 * 实体列表管理
 * @param {Internal.Level} level
 */
function entityListManagement(level) {
    // 获取区域内的实体
    const entities = level.getEntitiesWithinAABB(
        -100, 0, -100,
        100, 256, 100
    );
    
    // 按类型分组
    const entityGroups = new Map();
    entities.forEach(entity => {
        const type = entity.type;
        if (!entityGroups.has(type)) {
            entityGroups.set(type, []);
        }
        entityGroups.get(type).push(entity);
    });
    
    // 查找最近的玩家
    const centerX = 0, centerZ = 0;
    const players = entities.filter(e => e.type === 'minecraft:player');
    
    if (players.length > 0) {
        const nearestPlayer = players.reduce((nearest, player) => {
            const nearestDist = Math.sqrt(nearest.x * nearest.x + nearest.z * nearest.z);
            const playerDist = Math.sqrt(player.x * player.x + player.z * player.z);
            return playerDist < nearestDist ? player : nearest;
        });
        
        console.log(`最近的玩家: ${nearestPlayer.username}`);
    }
    
    // 按距离排序实体
    const sortedByDistance = entities
        .map(entity => ({
            entity: entity,
            distance: Math.sqrt(entity.x * entity.x + entity.z * entity.z)
        }))
        .sort((a, b) => a.distance - b.distance)
        .map(item => item.entity);
    
    console.log(`最近的实体: ${sortedByDistance[0]?.type}`);
}

/**
 * 物品库存管理
 * @param {Internal.Player} player
 */
function inventoryListManagement(player) {
    const inventory = player.inventory;
    const items = [];
    
    // 收集所有物品
    for (let i = 0; i < inventory.containerSize; i++) {
        const item = inventory.getItem(i);
        if (!item.isEmpty()) {
            items.push({
                item: item,
                slot: i,
                count: item.count,
                id: item.id
            });
        }
    }
    
    // 按数量排序
    items.sort((a, b) => b.count - a.count);
    
    // 查找特定物品
    const stones = items.filter(item => item.id === 'minecraft:stone');
    const totalStones = stones.reduce((sum, item) => sum + item.count, 0);
    
    // 查找稀有物品
    const rareItems = items.filter(item => {
        const rarity = item.item.rarity;
        return rarity === 'rare' || rarity === 'epic';
    });
    
    // 分组统计
    const itemCounts = new Map();
    items.forEach(item => {
        const id = item.id;
        itemCounts.set(id, (itemCounts.get(id) || 0) + item.count);
    });
    
    console.log(`物品种类: ${itemCounts.size}`);
    console.log(`石头总数: ${totalStones}`);
    console.log(`稀有物品数: ${rareItems.length}`);
}
```

### 任务队列管理

```js
/**
 * 任务队列系统
 */
class TaskQueue {
    constructor() {
        this.tasks = [];
        this.running = false;
    }
    
    // 添加任务
    addTask(task, priority = 0) {
        this.tasks.push({ task, priority, id: Date.now() });
        this.tasks.sort((a, b) => b.priority - a.priority);
    }
    
    // 批量添加任务
    addTasks(tasks) {
        tasks.forEach(task => this.addTask(task));
    }
    
    // 插入高优先级任务
    insertUrgentTask(task) {
        this.tasks.unshift({ task, priority: Infinity, id: Date.now() });
    }
    
    // 移除任务
    removeTask(taskId) {
        this.tasks = this.tasks.filter(item => item.id !== taskId);
    }
    
    // 获取下一个任务
    getNextTask() {
        return this.tasks.shift()?.task;
    }
    
    // 查看队列状态
    getStatus() {
        return {
            totalTasks: this.tasks.length,
            highPriorityTasks: this.tasks.filter(t => t.priority > 5).length,
            isRunning: this.running
        };
    }
    
    // 执行所有任务
    async executeAll() {
        this.running = true;
        
        while (this.tasks.length > 0) {
            const task = this.getNextTask();
            if (task) {
                try {
                    await this.executeTask(task);
                } catch (error) {
                    console.error('任务执行失败:', error);
                }
            }
        }
        
        this.running = false;
    }
    
    async executeTask(task) {
        if (typeof task === 'function') {
            return await task();
        } else if (task && typeof task.execute === 'function') {
            return await task.execute();
        }
    }
    
    // 清空队列
    clear() {
        this.tasks = [];
    }
    
    // 暂停执行
    pause() {
        this.running = false;
    }
}

// 使用示例
const taskQueue = new TaskQueue();

taskQueue.addTask(() => console.log('普通任务1'), 1);
taskQueue.addTask(() => console.log('高优先级任务'), 10);
taskQueue.addTask(() => console.log('普通任务2'), 1);

taskQueue.executeAll();
```

### 列表工具类

```js
/**
 * 列表工具类
 */
class ListUtils {
    /**
     * 分页
     */
    static paginate(list, pageSize, pageNumber) {
        const startIndex = (pageNumber - 1) * pageSize;
        const endIndex = startIndex + pageSize;
        
        return {
            items: list.slice(startIndex, endIndex),
            totalPages: Math.ceil(list.length / pageSize),
            currentPage: pageNumber,
            totalItems: list.length,
            hasNext: endIndex < list.length,
            hasPrev: pageNumber > 1
        };
    }
    
    /**
     * 随机采样
     */
    static sample(list, count) {
        const shuffled = [...list].sort(() => 0.5 - Math.random());
        return shuffled.slice(0, count);
    }
    
    /**
     * 打乱顺序
     */
    static shuffle(list) {
        const result = [...list];
        for (let i = result.length - 1; i > 0; i--) {
            const j = Math.floor(Math.random() * (i + 1));
            [result[i], result[j]] = [result[j], result[i]];
        }
        return result;
    }
    
    /**
     * 去重
     */
    static unique(list, keyFn = null) {
        if (keyFn) {
            const seen = new Set();
            return list.filter(item => {
                const key = keyFn(item);
                if (seen.has(key)) {
                    return false;
                }
                seen.add(key);
                return true;
            });
        } else {
            return [...new Set(list)];
        }
    }
    
    /**
     * 分组
     */
    static groupBy(list, keyFn) {
        const groups = new Map();
        list.forEach(item => {
            const key = keyFn(item);
            if (!groups.has(key)) {
                groups.set(key, []);
            }
            groups.get(key).push(item);
        });
        return groups;
    }
    
    /**
     * 扁平化
     */
    static flatten(nestedList, depth = 1) {
        if (depth === 1) {
            return nestedList.flat();
        }
        return nestedList.flat(depth);
    }
    
    /**
     * 窗口滑动
     */
    static window(list, size, step = 1) {
        const windows = [];
        for (let i = 0; i <= list.length - size; i += step) {
            windows.push(list.slice(i, i + size));
        }
        return windows;
    }
    
    /**
     * 最大值和最小值
     */
    static minMax(list, keyFn = null) {
        if (list.length === 0) return { min: null, max: null };
        
        let min = list[0];
        let max = list[0];
        
        for (const item of list) {
            const value = keyFn ? keyFn(item) : item;
            const minValue = keyFn ? keyFn(min) : min;
            const maxValue = keyFn ? keyFn(max) : max;
            
            if (value < minValue) min = item;
            if (value > maxValue) max = item;
        }
        
        return { min, max };
    }
}

// 使用示例
const numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

// 分页
const page1 = ListUtils.paginate(numbers, 3, 1);
console.log('第一页:', page1.items); // [1, 2, 3]

// 随机采样
const sample = ListUtils.sample(numbers, 3);
console.log('随机3个:', sample);

// 分组
const grouped = ListUtils.groupBy(numbers, n => n % 2 === 0 ? 'even' : 'odd');
console.log('按奇偶分组:', grouped);
```

## 性能优化

### 性能对比和优化建议

```js
/**
 * 列表性能优化
 */
function listPerformanceOptimization() {
    console.log(`
    List性能优化建议:
    
    1. 访问操作 (O(1)):
       - 使用索引访问: list[i]
       - 避免频繁的length计算
    
    2. 插入删除:
       - 末尾操作最快: push(), pop()
       - 开头操作较慢: unshift(), shift()
       - 中间插入最慢: splice()
    
    3. 查找操作:
       - 有序数组用二分查找
       - 频繁查找考虑用Set或Map
    
    4. 遍历优化:
       - for循环最快
       - forEach次之
       - for-of和for-in较慢
    
    5. 内存优化:
       - 预分配容量避免重新分配
       - 及时清理不用的引用
       - 大数组考虑分批处理
    `);
    
    // 性能测试示例
    function performanceTest() {
        const size = 100000;
        const arr = new Array(size);
        
        // 填充数组
        console.time('填充数组');
        for (let i = 0; i < size; i++) {
            arr[i] = i;
        }
        console.timeEnd('填充数组');
        
        // for循环遍历
        console.time('for循环');
        let sum1 = 0;
        for (let i = 0; i < arr.length; i++) {
            sum1 += arr[i];
        }
        console.timeEnd('for循环');
        
        // forEach遍历
        console.time('forEach');
        let sum2 = 0;
        arr.forEach(n => sum2 += n);
        console.timeEnd('forEach');
    }
    
    performanceTest();
}
```

## 相关文档

- [Collection 集合](./Collection.md)
- [Map 映射](./Map.md)
- [Supplier 供应者](./Supplier.md)
