---
title: 向量与坐标
description: Vector and Coordinates
tags:
    - KubeJS
    - Vector
progress: 95
---

# {{ $frontmatter.title }}

> [!TIP] 提示
> 此处详细介绍 KubeJS 中的向量概念、坐标系统和移动机制。

## 坐标系统 {#coordinate-system}

### Minecraft 坐标轴

Minecraft 使用右手坐标系：

```
Y轴 ↑ (高度)
   |
   |
   └───→ X轴 (东西)
  /
 /
Z轴 (南北)
```

| 轴   | 方向 | 正值方向 | 负值方向 |
| ---- | ---- | -------- | -------- |
| X 轴 | 东西 | 东       | 西       |
| Y 轴 | 高度 | 上       | 下       |
| Z 轴 | 南北 | 南       | 北       |

### 坐标类型

| 类型       | 精度   | 用途     | 示例             |
| ---------- | ------ | -------- | ---------------- |
| `number`   | 双精度 | 精确位置 | `x: 100.5`       |
| `BlockPos` | 整数   | 方块坐标 | `[100, 64, 200]` |
| `ChunkPos` | 整数   | 区块坐标 | `[6, 12]`        |
| `Vec3d`    | 双精度 | 三维向量 | `(100, 64, 200)` |

::: code-group

```js [获取坐标]
// 精确坐标 (双精度)
const x = entity.getX(); // 100.8234
const y = entity.getY(); // 64.0
const z = entity.getZ(); // 200.1567

// 方块坐标 (整数)
const blockPos = entity.blockPosition();
console.log(blockPos); // [100, 64, 200]

// 区块坐标
const chunkPos = entity.chunkPosition();
console.log(chunkPos); // [6, 12]
```

```js [坐标转换]
// 精确坐标转方块坐标
const blockX = Math.floor(entity.getX());
const blockY = Math.floor(entity.getY());
const blockZ = Math.floor(entity.getZ());

// 方块坐标转区块坐标
const chunkX = Math.floor(blockX / 16);
const chunkZ = Math.floor(blockZ / 16);

// 区块内相对坐标
const relativeX = blockX % 16;
const relativeZ = blockZ % 16;
```

:::

## Vec3d 向量 {#vec3d-vector}

### 基本概念

`Vec3d` 是三维向量类，表示三维空间中的点或方向：

| 属性 | 类型     | 描述   |
| ---- | -------- | ------ |
| `x`  | `number` | X 分量 |
| `y`  | `number` | Y 分量 |
| `z`  | `number` | Z 分量 |

### 创建向量

| 方法                      | 参数                     | 描述           |
| ------------------------- | ------------------------ | -------------- |
| `new Vec3d(x, y, z)`      | `number, number, number` | 创建新向量     |
| `Vec3d.ZERO`              | -                        | 零向量 (0,0,0) |
| `entity.position()`       | -                        | 实体位置向量   |
| `entity.getEyePosition()` | -                        | 眼部位置向量   |
| `entity.getLookAngle()`   | -                        | 朝向向量       |

### 向量运算

| 方法                 | 参数                     | 返回类型 | 描述     |
| -------------------- | ------------------------ | -------- | -------- |
| `add(x, y, z)`       | `number, number, number` | `Vec3d`  | 向量加法 |
| `add(vec)`           | `Vec3d`                  | `Vec3d`  | 向量加法 |
| `subtract(x, y, z)`  | `number, number, number` | `Vec3d`  | 向量减法 |
| `subtract(vec)`      | `Vec3d`                  | `Vec3d`  | 向量减法 |
| `multiply(scalar)`   | `number`                 | `Vec3d`  | 标量乘法 |
| `multiply(x, y, z)`  | `number, number, number` | `Vec3d`  | 分量乘法 |
| `normalize()`        | -                        | `Vec3d`  | 单位化   |
| `length()`           | -                        | `number` | 向量长度 |
| `lengthSqr()`        | -                        | `number` | 长度平方 |
| `distanceTo(vec)`    | `Vec3d`                  | `number` | 距离     |
| `distanceToSqr(vec)` | `Vec3d`                  | `number` | 距离平方 |
| `dot(vec)`           | `Vec3d`                  | `number` | 点积     |
| `cross(vec)`         | `Vec3d`                  | `Vec3d`  | 叉积     |

::: code-group

```js [基础运算]
const pos1 = new Vec3d(10, 64, 20);
const pos2 = new Vec3d(5, 60, 15);

// 向量加法
const sum = pos1.add(pos2); // (15, 124, 35)

// 向量减法
const diff = pos1.subtract(pos2); // (5, 4, 5)

// 标量乘法
const scaled = pos1.multiply(2); // (20, 128, 40)

// 向量长度
const length = diff.length(); // √(5² + 4² + 5²) = √66

// 距离计算
const distance = pos1.distanceTo(pos2); // √66
```

```js [实用计算]
// 两点中点
function midPoint(pos1, pos2) {
    return pos1.add(pos2).multiply(0.5);
}

// 方向向量
function direction(from, to) {
    return to.subtract(from).normalize();
}

// 在方向上移动指定距离
function moveInDirection(pos, direction, distance) {
    return pos.add(direction.multiply(distance));
}

const playerPos = player.position();
const targetPos = target.position();
const dir = direction(playerPos, targetPos);
const newPos = moveInDirection(playerPos, dir, 10);
```

:::

## Motion 移动系统 {#motion-system}

### Motion vs DeltaMovement

在 KubeJS 中，实体移动有两套 API：

| KubeJS API                  | Minecraft API          | 描述          |
| --------------------------- | ---------------------- | ------------- |
| `entity.getMotionX()`       | `getDeltaMovement().x` | 获取 X 轴速度 |
| `entity.getMotionY()`       | `getDeltaMovement().y` | 获取 Y 轴速度 |
| `entity.getMotionZ()`       | `getDeltaMovement().z` | 获取 Z 轴速度 |
| `entity.setMotionX(x)`      | `setDeltaMovement()`   | 设置 X 轴速度 |
| `entity.setMotionY(y)`      | `setDeltaMovement()`   | 设置 Y 轴速度 |
| `entity.setMotionZ(z)`      | `setDeltaMovement()`   | 设置 Z 轴速度 |
| `entity.setMotion(x, y, z)` | `setDeltaMovement()`   | 设置三轴速度  |
| `entity.addMotion(x, y, z)` | `addDeltaMovement()`   | 增加速度      |

### Motion 详解

Motion 表示实体每刻的位移量（单位：方块/刻）：

| 速度值 | 含义                | 每秒移动距离 |
| ------ | ------------------- | ------------ |
| `0.1`  | 每刻移动 0.1 方块   | 2 方块/秒    |
| `0.5`  | 每刻移动 0.5 方块   | 10 方块/秒   |
| `1.0`  | 每刻移动 1 方块     | 20 方块/秒   |
| `-0.1` | 反方向每刻 0.1 方块 | -2 方块/秒   |

### Motion 操作方法

| 方法                    | 参数                     | 返回类型 | 描述          |
| ----------------------- | ------------------------ | -------- | ------------- |
| `getMotionX()`          | -                        | `number` | 获取 X 轴速度 |
| `getMotionY()`          | -                        | `number` | 获取 Y 轴速度 |
| `getMotionZ()`          | -                        | `number` | 获取 Z 轴速度 |
| `setMotionX(x)`         | `number`                 | `void`   | 设置 X 轴速度 |
| `setMotionY(y)`         | `number`                 | `void`   | 设置 Y 轴速度 |
| `setMotionZ(z)`         | `number`                 | `void`   | 设置 Z 轴速度 |
| `setMotion(x, y, z)`    | `number, number, number` | `void`   | 设置三轴速度  |
| `addMotion(x, y, z)`    | `number, number, number` | `void`   | 增加速度      |
| `getDeltaMovement()`    | -                        | `Vec3d`  | 获取速度向量  |
| `setDeltaMovement(vec)` | `Vec3d`                  | `void`   | 设置速度向量  |
| `addDeltaMovement(vec)` | `Vec3d`                  | `void`   | 增加速度向量  |

::: code-group

```js [基础移动]
// 获取当前速度
const motionX = entity.getMotionX();
const motionY = entity.getMotionY();
const motionZ = entity.getMotionZ();

// 设置速度
entity.setMotionX(0.5); // 向东移动
entity.setMotionY(0.2); // 向上移动
entity.setMotionZ(-0.3); // 向北移动

// 一次性设置三轴速度
entity.setMotion(0.5, 0.2, -0.3);

// 增加速度 (不覆盖现有速度)
entity.addMotion(0.1, 0, 0);
```

```js [向量方式]
// 使用向量操作
const currentMotion = entity.getDeltaMovement();
const newMotion = new Vec3d(0.5, 0.2, -0.3);

// 设置新速度
entity.setDeltaMovement(newMotion);

// 增加速度
entity.addDeltaMovement(new Vec3d(0.1, 0, 0));

// 速度修改
const boostedMotion = currentMotion.multiply(1.5);
entity.setDeltaMovement(boostedMotion);
```

```js [实用示例]
// 向特定方向推动实体
function pushTowards(entity, targetPos, force) {
    const entityPos = entity.position();
    const direction = targetPos.subtract(entityPos).normalize();
    const pushVec = direction.multiply(force);
    entity.addDeltaMovement(pushVec);
}

// 垂直弹射
function launchUp(entity, force) {
    entity.setMotionY(force);
}

// 水平击退
function knockback(entity, direction, force) {
    const knockbackVec = direction.normalize().multiply(force);
    entity.addMotion(knockbackVec.x, 0, knockbackVec.z);
}
```

:::

::: v-warning 注意！
如果你为玩家`setMotion`，必须使用`player.hurtMarked = true`来同步以实现效果。
:::

## 位置操作详解 {#position-operations}

### 位置设置方法

| 方法                                         | 参数                     | 特点         |
| -------------------------------------------- | ------------------------ | ------------ |
| `setPosition(x, y, z)`                       | `number, number, number` | 立即设置位置 |
| `setPos(x, y, z)`                            | `number, number, number` | 立即设置位置 |
| `setPos(vec)`                                | `Vec3d`                  | 向量方式设置 |
| `teleportTo(x, y, z)`                        | `number, number, number` | 传送，有特效 |
| `teleportTo(dimension, x, y, z, yaw, pitch)` | 跨维度参数               | 跨维度传送   |
| `moveTo(x, y, z)`                            | `number, number, number` | 平滑移动     |
| `absMoveTo(x, y, z)`                         | `number, number, number` | 绝对移动     |

### 位置获取方法

| 方法                         | 返回类型   | 描述              |
| ---------------------------- | ---------- | ----------------- |
| `getX()`, `getY()`, `getZ()` | `number`   | 当前精确坐标      |
| `getX(tick)` 等              | `number`   | 插值坐标 (渲染用) |
| `position()`                 | `Vec3d`    | 位置向量          |
| `blockPosition()`            | `BlockPos` | 方块位置          |
| `getEyePosition()`           | `Vec3d`    | 眼部位置          |
| `getEyePosition(tick)`       | `Vec3d`    | 插值眼部位置      |

### 相对位置计算

| 方法                  | 参数     | 返回类型 | 描述       |
| --------------------- | -------- | -------- | ---------- |
| `getUpVector(tick)`   | `number` | `Vec3d`  | 上方向向量 |
| `getForward()`        | -        | `Vec3d`  | 前方向向量 |
| `getLookAngle()`      | -        | `Vec3d`  | 朝向向量   |
| `getViewVector(tick)` | `number` | `Vec3d`  | 视线向量   |

::: code-group

```js [位置设置]
// 立即设置位置
entity.setPosition(100, 64, 200);
entity.setPos(new Vec3d(100, 64, 200));

// 传送 (有粒子特效)
entity.teleportTo(100, 64, 200);

// 跨维度传送
entity.teleportTo("minecraft:the_nether", 50, 64, 100, 0, 0);

// 相对移动
const currentPos = entity.position();
const newPos = currentPos.add(10, 0, 0); // 向东移动10格
entity.setPos(newPos);
```

```js [位置计算]
// 计算实体前方位置
function getFrontPosition(entity, distance) {
    const pos = entity.position();
    const lookAngle = entity.getLookAngle();
    return pos.add(lookAngle.multiply(distance));
}

// 计算实体上方位置
function getAbovePosition(entity, height) {
    const pos = entity.position();
    return pos.add(0, height, 0);
}

// 计算两实体中点
function getMidpoint(entity1, entity2) {
    const pos1 = entity1.position();
    const pos2 = entity2.position();
    return pos1.add(pos2).multiply(0.5);
}
```

```js [圆形排列]
// 在实体周围圆形排列其他实体
function arrangeInCircle(centerEntity, entities, radius) {
    const centerPos = centerEntity.position();
    const angleStep = (Math.PI * 2) / entities.length;

    entities.forEach((entity, index) => {
        const angle = angleStep * index;
        const x = centerPos.x + Math.cos(angle) * radius;
        const z = centerPos.z + Math.sin(angle) * radius;
        entity.setPosition(x, centerPos.y, z);
    });
}
```

:::

## 实用工具函数 {#utility-functions}

### 距离计算

```js
// 二维距离 (忽略Y轴)
function distance2D(pos1, pos2) {
    const dx = pos1.x - pos2.x;
    const dz = pos1.z - pos2.z;
    return Math.sqrt(dx * dx + dz * dz);
}

// 三维距离
function distance3D(pos1, pos2) {
    return pos1.distanceTo(pos2);
}

// 曼哈顿距离
function manhattanDistance(pos1, pos2) {
    return (
        Math.abs(pos1.x - pos2.x) +
        Math.abs(pos1.y - pos2.y) +
        Math.abs(pos1.z - pos2.z)
    );
}
```

### 方向计算

```js
// 计算从实体A到实体B的方向
function getDirection(fromEntity, toEntity) {
    const from = fromEntity.position();
    const to = toEntity.position();
    return to.subtract(from).normalize();
}

// 计算角度 (弧度)
function getAngleBetween(vec1, vec2) {
    const dot = vec1.dot(vec2);
    const lengths = vec1.length() * vec2.length();
    return Math.acos(dot / lengths);
}

// 计算偏航角 (度)
function getYawFromDirection(direction) {
    return (Math.atan2(-direction.x, direction.z) * 180) / Math.PI;
}
```

### 轨迹计算

```js
// 抛物线轨迹
function calculateProjectilePath(startPos, velocity, gravity, time) {
    const x = startPos.x + velocity.x * time;
    const y = startPos.y + velocity.y * time - 0.5 * gravity * time * time;
    const z = startPos.z + velocity.z * time;
    return new Vec3d(x, y, z);
}

// 圆形轨迹
function calculateCircularPath(center, radius, angle, height) {
    const x = center.x + Math.cos(angle) * radius;
    const y = center.y + height;
    const z = center.z + Math.sin(angle) * radius;
    return new Vec3d(x, y, z);
}
```

## 常见应用场景 {#common-use-cases}

### 实体跟随

```js
// 让实体A跟随实体B
function makeFollowTarget(follower, target, distance, speed) {
    const followerPos = follower.position();
    const targetPos = target.position();
    const distanceToTarget = followerPos.distanceTo(targetPos);

    if (distanceToTarget > distance) {
        const direction = targetPos.subtract(followerPos).normalize();
        const moveVector = direction.multiply(speed);
        follower.setDeltaMovement(moveVector);
    } else {
        follower.setDeltaMovement(Vec3d.ZERO);
    }
}
```

### 粒子特效

```js
// 在两点间创建粒子线
function createParticleLine(level, startPos, endPos, particleType, count) {
    for (let i = 0; i <= count; i++) {
        const progress = i / count;
        const pos = startPos.add(endPos.subtract(startPos).multiply(progress));
        level.spawnParticles(particleType, pos.x, pos.y, pos.z, 1, 0, 0, 0, 0);
    }
}

// 圆形粒子效果
function createCircleParticles(level, center, radius, particleType, count) {
    for (let i = 0; i < count; i++) {
        const angle = (Math.PI * 2 * i) / count;
        const x = center.x + Math.cos(angle) * radius;
        const z = center.z + Math.sin(angle) * radius;
        level.spawnParticles(particleType, x, center.y, z, 1, 0, 0, 0, 0);
    }
}
```

### 区域检测

```js
// 检查实体是否在指定范围内
function isInRange(entity, center, radius) {
    const entityPos = entity.position();
    return entityPos.distanceTo(center) <= radius;
}

// 检查实体是否在立方体区域内
// 其实就是AABB
function isInCube(entity, corner1, corner2) {
    const pos = entity.position();
    const minX = Math.min(corner1.x, corner2.x);
    const maxX = Math.max(corner1.x, corner2.x);
    const minY = Math.min(corner1.y, corner2.y);
    const maxY = Math.max(corner1.y, corner2.y);
    const minZ = Math.min(corner1.z, corner2.z);
    const maxZ = Math.max(corner1.z, corner2.z);

    return (
        pos.x >= minX &&
        pos.x <= maxX &&
        pos.y >= minY &&
        pos.y <= maxY &&
        pos.z >= minZ &&
        pos.z <= maxZ
    );
}
```
