---
title: 向量
description: 向量与坐标
tags:
    - KubeJS
    - Vector
progress: 95
---

# 向量与坐标

> [!TIP] 提示
> 本文将详细解释 KubeJS 与 Minecraft 中的向量（Vector）与坐标（Coordinate）概念，帮助你理解空间计算的原理、常见用法与开发注意事项。

## 什么是向量与坐标？ {#intro}

在 Minecraft 脚本开发中，理解"向量"和"坐标"是空间操作的基础。

-   **坐标（Coordinate）**：用于描述世界中某个"点"的具体位置。例如玩家的当前位置、方块的中心点等。
-   **向量（Vector）**：不仅可以表示一个点，也可以描述"方向"和"距离"。向量常用于描述实体的移动方向、速度、两点之间的相对关系等。

**区别与联系**：

-   坐标是"点"，向量是"箭头"。
-   坐标常用于定位，向量常用于计算方向、距离、速度等。
-   在代码中，很多 API 会混用这两个概念，开发时要注意区分。

**实际意义举例**：

-   获取玩家位置：`player.position()` 返回的是一个三维向量（Vec3d），但它也代表玩家的坐标。
-   计算玩家到目标的方向：`target.position().subtract(player.position())` 得到一个"从玩家指向目标"的向量。
-   让实体朝某方向移动：需要用"单位向量"描述方向，再乘以速度。

::: alert {"type": "info", "title": "常见误区"}

-   不要把"坐标数组"当作向量直接做加减运算，推荐统一用 Vec3d 类型。
-   坐标和向量的单位通常都是"方块"，但有些 API（如区块坐标）单位不同，注意换算。
    :::

## 坐标系统详解 {#coordinate_system}

### Minecraft 坐标轴 {#axes}

Minecraft 世界采用"右手坐标系"：

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

### 坐标类型对比 {#coordinate_types}

| 类型       | 精度   | 用途     | 示例             |
| ---------- | ------ | -------- | ---------------- |
| `number`   | 双精度 | 精确位置 | `x: 100.5`       |
| `BlockPos` | 整数   | 方块坐标 | `[100, 64, 200]` |
| `ChunkPos` | 整数   | 区块坐标 | `[6, 12]`        |
| `Vec3d`    | 双精度 | 三维向量 | `(100, 64, 200)` |

-   **number**：最常见的坐标类型，表示精确到小数点的世界位置。
-   **BlockPos**：整数坐标，表示方块格子的中心点，常用于放置/获取方块。
-   **ChunkPos**：区块坐标，单位为 16 格，常用于区块相关操作。
-   **Vec3d**：三维向量，既可表示点，也可做向量运算。

**坐标转换常见写法**：

```js
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

::: alert {"type": "warning", "title": "常见误区"}

-   区分"精确坐标"（number/Vec3d）和"方块坐标"（BlockPos），不要混用。
-   区块坐标不是世界坐标，单位是 16 格。
-   取整时建议用 `Math.floor`，避免负数坐标出错。
    :::

## Vec3d 向量详解 {#vec3d}

向量（Vector）是描述空间中"方向"和"距离"的数学工具。在 Minecraft/KubeJS 中，Vec3d 是最常用的三维向量类型。

-   **向量的本质**：可以看作是从原点出发的一支"箭头"，既有长度（magnitude），也有方向（direction）。
-   **用途**：描述实体移动、两点间距离、朝向、速度等。

### Vec3d 的属性与创建 {#vec3d_properties}

| 属性 | 类型     | 说明   |
| ---- | -------- | ------ |
| `x`  | `number` | X 分量 |
| `y`  | `number` | Y 分量 |
| `z`  | `number` | Z 分量 |

| 创建方式                  | 说明           |
| ------------------------- | -------------- |
| `new Vec3d(x, y, z)`      | 创建新向量     |
| `Vec3d.ZERO`              | 零向量 (0,0,0) |
| `entity.position()`       | 实体位置向量   |
| `entity.getEyePosition()` | 眼部位置向量   |
| `entity.getLookAngle()`   | 朝向向量       |

::: code-group

```js [创建与访问]
const pos = new Vec3d(10, 64, 20);
console.log(pos.x, pos.y, pos.z); // 10 64 20

const playerPos = player.position();
const look = player.getLookAngle();
```

```js [常见用法]
// 获取玩家眼睛位置
const eye = player.getEyePosition();
// 获取实体朝向向量
const dir = entity.getLookAngle();
```

:::

> **数学说明**：
> 向量 (x, y, z) 可以表示空间中的一个点，也可以表示从原点 (0,0,0) 指向 (x, y, z) 的箭头。

---

## 向量运算与空间计算 {#vector_math}

向量运算是空间计算的基础。每种运算不仅有数学意义，也有实际开发用途。下列内容将详细讲解常用运算、典型用法、边界情况和常见误区。

### 向量加法与减法 {#vector_add_subtract}

-   **加法**：两个向量首尾相连，结果是"合力"或"总位移"。
-   **减法**：A-B 得到"从 B 指向 A"的方向和距离。

::: code-group

```js [链式加减]
const a = new Vec3d(1, 2, 3);
const b = new Vec3d(4, 5, 6);
const c = new Vec3d(-1, 0, 2);
const result = a.add(b).subtract(c); // (6, 7, 7)
```

```js [零向量运算]
const zero = Vec3d.ZERO;
const a = new Vec3d(2, 3, 4);
const sum = a.add(zero); // (2, 3, 4)
const diff = a.subtract(zero); // (2, 3, 4)
```

```js [误用示例]
// 错误：不能直接对数组做加法
const arr = [1, 2, 3];
// const result = a.add(arr); // 会报错，需先转为Vec3d
```

```js [实际场景]
// 计算玩家到目标的方向向量
const direction = target.position().subtract(player.position());
// 计算两点中点
const midpoint = a.add(b).multiply(0.5);
```

:::

> **数学说明**：
> 加法：(x1, y1, z1) + (x2, y2, z2) = (x1+x2, y1+y2, z1+z2)
> 减法：(x1, y1, z1) - (x2, y2, z2) = (x1-x2, y1-y2, z1-z2)

::: alert {"type": "info", "title": "常见误区"}

-   加法/减法只能用于 Vec3d 类型，不能直接对 BlockPos/数组做加减。
-   减法结果的方向是"后减前"，即 a.subtract(b) 是"从 b 指向 a"。
    :::

---

### 标量乘法与分量乘法 {#vector_multiply}

-   **标量乘法**：向量整体缩放，常用于"调整速度/距离"。
-   **分量乘法**：每个轴分别缩放，常用于"非等比缩放"或"遮罩"。

::: code-group

```js [标量乘法]
const v = new Vec3d(1, 2, 3);
const scaled = v.multiply(2); // (2, 4, 6)
const reversed = v.multiply(-1); // (-1, -2, -3)
```

```js [分量乘法]
const v = new Vec3d(5, 8, 2);
const mask = new Vec3d(1, 0, 1);
const xzOnly = v.multiply(mask); // (5, 0, 2)
```

```js [实际场景]
// 沿方向移动指定距离
const from = player.position();
const to = target.position();
const dir = to.subtract(from).normalize();
const newPos = from.add(dir.multiply(5)); // 沿朝向前进5格
```

:::

> **数学说明**：
> 标量乘法：(x, y, z) * s = (x*s, y*s, z*s)
> 分量乘法：(x1, y1, z1) * (x2, y2, z2) = (x1*x2, y1*y2, z1*z2)

---

### 单位化与长度 {#vector_normalize_length}

-   **单位化**：将向量缩放为长度 1，仅保留方向。
-   **长度**：向量的"模"，即箭头的实际长度。

::: code-group

```js [单位化]
const v = new Vec3d(3, 4, 0);
const unit = v.normalize(); // (0.6, 0.8, 0)
```

```js [单位化后缩放]
const dir = v.normalize();
const length10 = dir.multiply(10); // (6, 8, 0)
```

```js [长度]
const len = v.length(); // 5
```

```js [零向量单位化]
const zero = Vec3d.ZERO;
const unitZero = zero.normalize(); // (0, 0, 0)
```

:::

> **数学说明**：
> 长度：|v| = sqrt(x² + y² + z²)
> 单位化：v/|v|，零向量单位化结果仍为零

---

### 距离计算 {#vector_distance}

-   **distanceTo**：两点间直线距离。
-   **distanceToSqr**：距离的平方，适合比较远近时避免开方运算。

::: code-group

```js [三维距离]
const a = new Vec3d(1, 2, 3);
const b = new Vec3d(4, 6, 3);
const dist = a.distanceTo(b); // 5
```

```js [二维距离]
function distance2D(a, b) {
    return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.z - b.z, 2));
}
```

```js [距离平方优化]
const sqr = a.distanceToSqr(b); // 25
if (sqr < 100) {
    /* 更快判断是否在10格内 */
}
```

```js [距离为零]
const distZero = a.distanceTo(a); // 0
```

:::

---

### 点积与夹角 {#vector_dot_angle}

-   **点积**：判断两个向量是否同向、反向、垂直，计算夹角。
-   **夹角**：通过点积公式求得。

::: code-group

```js [同向/反向/垂直]
const a = new Vec3d(1, 0, 0);
const b = new Vec3d(2, 0, 0);
const c = new Vec3d(-1, 0, 0);
const d = new Vec3d(0, 1, 0);
a.dot(b); // 2，同向
a.dot(c); // -1，反向
a.dot(d); // 0，垂直
```

```js [夹角计算]
const v1 = new Vec3d(1, 0, 0);
const v2 = new Vec3d(1, 1, 0);
const angle = Math.acos(v1.dot(v2) / (v1.length() * v2.length())); // ≈0.785（弧度）
const deg = (angle * 180) / Math.PI; // ≈45°
```

```js [夹角阈值]
const look = player.getLookAngle();
const toTarget = target.position().subtract(player.position()).normalize();
const cosThreshold = Math.cos(Math.PI / 6); // 30°
if (look.dot(toTarget) > cosThreshold) {
    // 夹角小于30°
}
```

```js [判断视线是否正对目标]
// 判断玩家是否正对目标（如触发交互、攻击判定等）
const look = player.getLookAngle();
const toTarget = target.position().subtract(player.position()).normalize();
const dot = look.dot(toTarget);
if (dot > 0.98) {
    // 玩家正对目标，允许触发特殊行为
}
```

```js [判断是否背对目标]
const look = player.getLookAngle();
const toTarget = target.position().subtract(player.position()).normalize();
if (look.dot(toTarget) < -0.98) {
    // 玩家背对目标
}
```

```js [判断是否在锥形区域]
// 判断实体是否在玩家前方60°锥形区域内
const look = player.getLookAngle();
const toEntity = entity.position().subtract(player.position()).normalize();
const cosCone = Math.cos(Math.PI / 6); // 30°
if (look.dot(toEntity) > cosCone) {
    // entity 在玩家前方锥形区域内
}
```

:::

> **实际场景说明**：
>
> -   判断玩家是否正对/背对某目标（如交互、攻击、NPC 对话等）。
> -   判断实体是否在玩家视野锥体内（如 AOE 技能、视线检测）。
> -   判断两个方向是否基本一致（如自动对准、导航）。

---

### 叉积与法向量 {#vector_cross}

-   **叉积**：得到垂直于两个向量的向量，常用于求平面法线、旋转等。

::: code-group

```js [右手定则]
const x = new Vec3d(1, 0, 0);
const y = new Vec3d(0, 1, 0);
const z = x.cross(y); // (0, 0, 1)
```

```js [共线边界]
const a = new Vec3d(1, 2, 3);
const b = new Vec3d(2, 4, 6);
const n = a.cross(b); // (0, 0, 0)，共线向量叉积为零向量
```

```js [平面法线]
// 求平面法线
function getNormal(a, b, c) {
    const ab = b.subtract(a);
    const ac = c.subtract(a);
    return ab.cross(ac).normalize();
}
```

```js [三角形面积]
// 求三角形面积（向量叉积模长的一半）
function triangleArea(a, b, c) {
    const ab = b.subtract(a);
    const ac = c.subtract(a);
    return ab.cross(ac).length() / 2;
}
```

```js [法线方向判断]
// 判断法线朝向
const a = new Vec3d(0, 0, 0);
const b = new Vec3d(1, 0, 0);
const c = new Vec3d(0, 1, 0);
const normal = getNormal(a, b, c); // (0, 0, 1)，朝上
```
:::

- 实例：

::: code-group

```js [判断玩家是否站在斜坡上]
// 通过地形三个点计算法线，判断是否为斜坡
const p1 = player.position();
const p2 = p1.add(1, world.getBlock(p1.add(1, 0, 0)).y - p1.y, 0);
const p3 = p1.add(0, world.getBlock(p1.add(0, 0, 1)).y - p1.y, 1);
const normal = getNormal(p1, p2, p3);
if (Math.abs(normal.y) < 0.95) {
    // 法线y分量小于0.95，说明不是平地，可能是斜坡
}
```

```js [实体与地面夹角判定]
// 判断实体是否"贴地"或"攀爬"
const entityPos = entity.position();
const below = entityPos.add(0, -1, 0);
const blockNormal = getNormal(
    below,
    below.add(1, world.getBlock(below.add(1, 0, 0)).y - below.y, 0),
    below.add(0, world.getBlock(below.add(0, 0, 1)).y - below.y, 1)
);
const up = new Vec3d(0, 1, 0);
const angle = Math.acos(blockNormal.dot(up));
if (angle > Math.PI / 6) {
    // 地面与竖直夹角大于30°，判定为斜坡或可攀爬表面
}
```

```js [玩家视线与方块表面法线夹角]
// 判断玩家是否正对斜面（如自定义交互）
const look = player.getLookAngle();
const blockNormal = getNormal(blockPos1, blockPos2, blockPos3); // 三点确定表面
const dot = look.dot(blockNormal);
if (dot > 0.95) {
    // 玩家正对表面
}
```

```js [法线生成粒子特效]
// 沿法线方向生成粒子
const center = new Vec3d(10, 64, 10);
const normal = new Vec3d(0, 1, 0); // 假设为上表面
for (let i = 0; i < 10; i++) {
    const pos = center.add(normal.multiply(i * 0.2));
    level.spawnParticles("minecraft:cloud", pos.x, pos.y, pos.z, 1, 0, 0, 0, 0);
}
```


:::

> **实际场景说明**：
>
> -   判断玩家是否正对/背对某目标（如交互、攻击、NPC 对话等）。
> -   判断实体是否在玩家视野锥体内（如 AOE 技能、视线检测）。
> -   判断两个方向是否基本一致（如自动对准、导航）。

---

### 投影与反射 {#vector_projection_reflection}

-   **投影**：将一个向量投影到另一个向量上，常用于"沿某方向分量"。
-   **反射**：模拟"弹射"或"镜像"效果。

::: code-group

```js [向量投影]
function project(a, b) {
    // 投影a到b
    const unitB = b.normalize();
    const projLen = a.dot(unitB);
    return unitB.multiply(projLen);
}

// 投影为零
const a = new Vec3d(1, 0, 0);
const b = new Vec3d(0, 1, 0);
const proj = project(a, b); // (0, 0, 0)
```

```js [沿方向分量]
// 计算玩家速度在前进方向上的分量
const velocity = player.getDeltaMovement();
const forward = player.getLookAngle();
const forwardSpeed = velocity.dot(forward); // 前进分量
```

```js [向量反射]
function reflect(v, normal) {
    // v沿normal反射
    const n = normal.normalize();
    return v.subtract(n.multiply(2 * v.dot(n)));
}

// 反射夹角
const v = new Vec3d(1, -1, 0);
const normal = new Vec3d(0, 1, 0);
const reflected = reflect(v, normal); // (1, 1, 0)
```

```js [弹射/镜像实际场景]
// 计算实体碰撞后的反弹方向
const velocity = entity.getDeltaMovement();
const surfaceNormal = new Vec3d(0, 1, 0); // 假设地面
const bounce = reflect(velocity, surfaceNormal);
```

:::

> **实际场景说明**：
>
> -   计算速度/力在某方向上的分量（如滑动、摩擦、投射）。
> -   计算实体/粒子碰撞后的反弹方向。
> -   镜像变换、弹射模拟。

---

::: alert {"type": "info", "title": "开发建议与常见误区"}

-   零向量不能单位化，结果仍为零。
-   比较距离时优先用距离平方，避免不必要的开方。
-   方向判断建议用单位向量，避免长度影响。
-   叉积结果方向与输入顺序有关（右手定则）。
    :::

## 位置操作详解 {#position_operations}

### 位置设置方法 {#position_set}

| 方法                                         | 参数                     | 特点         |
| -------------------------------------------- | ------------------------ | ------------ |
| `setPosition(x, y, z)`                       | `number, number, number` | 立即设置位置 |
| `setPos(x, y, z)`                            | `number, number, number` | 立即设置位置 |
| `setPos(vec)`                                | `Vec3d`                  | 向量方式设置 |
| `teleportTo(x, y, z)`                        | `number, number, number` | 传送，有特效 |
| `teleportTo(dimension, x, y, z, yaw, pitch)` | 跨维度参数               | 跨维度传送   |
| `moveTo(x, y, z)`                            | `number, number, number` | 平滑移动     |
| `absMoveTo(x, y, z)`                         | `number, number, number` | 绝对移动     |

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

:::

### 位置获取方法 {#position_get}

| 方法                         | 返回类型   | 描述              |
| ---------------------------- | ---------- | ----------------- |
| `getX()`, `getY()`, `getZ()` | `number`   | 当前精确坐标      |
| `getX(tick)` 等              | `number`   | 插值坐标 (渲染用) |
| `position()`                 | `Vec3d`    | 位置向量          |
| `blockPosition()`            | `BlockPos` | 方块位置          |
| `getEyePosition()`           | `Vec3d`    | 眼部位置          |
| `getEyePosition(tick)`       | `Vec3d`    | 插值眼部位置      |

### 相对位置计算 {#relative_position}

| 方法                  | 参数     | 返回类型 | 描述       |
| --------------------- | -------- | -------- | ---------- |
| `getUpVector(tick)`   | `number` | `Vec3d`  | 上方向向量 |
| `getForward()`        | -        | `Vec3d`  | 前方向向量 |
| `getLookAngle()`      | -        | `Vec3d`  | 朝向向量   |
| `getViewVector(tick)` | `number` | `Vec3d`  | 视线向量   |

::: code-group

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

## 实用工具函数 {#utility_functions}

::: code-group
```js [距离与最近点]
// 计算实体到方块表面最近点
function closestPointOnBlock(entity, blockPos) {
    const e = entity.position();
    const min = blockPos;
    const max = blockPos.add(1, 1, 1);
    return new Vec3d(
        Math.max(min.x, Math.min(e.x, max.x)),
        Math.max(min.y, Math.min(e.y, max.y)),
        Math.max(min.z, Math.min(e.z, max.z))
    );
}

// 计算实体到多实体的最近距离
function minDistanceToEntities(entity, entities) {
    const pos = entity.position();
    return Math.min(...entities.map(e => pos.distanceTo(e.position())));
}
```

```js [轨迹与碰撞检测]
// 预测实体下一tick位置
function predictNextPosition(entity) {
    return entity.position().add(entity.getDeltaMovement());
}

// 判断射线是否命中AABB盒
function rayIntersectsAABB(origin, direction, min, max) {
    let tmin = (min.x - origin.x) / direction.x;
    let tmax = (max.x - origin.x) / direction.x;
    if (tmin > tmax) [tmin, tmax] = [tmax, tmin];
    let tymin = (min.y - origin.y) / direction.y;
    let tymax = (max.y - origin.y) / direction.y;
    if (tymin > tymax) [tymin, tymax] = [tymax, tymin];
    if ((tmin > tymax) || (tymin > tmax)) return false;
    tmin = Math.max(tmin, tymin);
    tmax = Math.min(tmax, tymax);
    let tzmin = (min.z - origin.z) / direction.z;
    let tzmax = (max.z - origin.z) / direction.z;
    if (tzmin > tzmax) [tzmin, tzmax] = [tzmax, tzmin];
    if ((tmin > tzmax) || (tzmin > tmax)) return false;
    return true;
}
```

```js [空间分布与采样]
// 均匀分布圆周上的点
function pointsOnCircle(center, radius, count, y = 0) {
    return Array.from({ length: count }, (_, i) => {
        const angle = (2 * Math.PI * i) / count;
        return center.add(Math.cos(angle) * radius, y, Math.sin(angle) * radius);
    });
}

// 球面均匀分布点
function pointsOnSphere(center, radius, count) {
    const points = [];
    for (let i = 0; i < count; i++) {
        const phi = Math.acos(1 - 2 * (i + 0.5) / count);
        const theta = Math.PI * (1 + Math.sqrt(5)) * i;
        const x = center.x + radius * Math.cos(theta) * Math.sin(phi);
        const y = center.y + radius * Math.cos(phi);
        const z = center.z + radius * Math.sin(theta) * Math.sin(phi);
        points.push(new Vec3d(x, y, z));
    }
    return points;
}

// 生成圆环上的点
function pointsOnRing(center, innerR, outerR, count, y = 0) {
    return Array.from({ length: count }, (_, i) => {
        const angle = (2 * Math.PI * i) / count;
        const r = (innerR + outerR) / 2;
        return center.add(Math.cos(angle) * r, y, Math.sin(angle) * r);
    });
}
```

```js [复杂区域判定]
// 判断点是否在二维多边形内（射线法）
function inPolygon(point, polygon) {
    let inside = false;
    for (let i = 0, j = polygon.length - 1; i < polygon.length; j = i++) {
        const xi = polygon[i].x, zi = polygon[i].z;
        const xj = polygon[j].x, zj = polygon[j].z;
        const intersect = ((zi > point.z) !== (zj > point.z)) &&
            (point.x < (xj - xi) * (point.z - zi) / (zj - zi + 1e-10) + xi);
        if (intersect) inside = !inside;
    }
    return inside;
}

// 判断点是否在圆环区域
function inAnnulus(point, center, innerR, outerR) {
    const d = Math.sqrt((point.x - center.x) ** 2 + (point.z - center.z) ** 2);
    return d >= innerR && d <= outerR;
}

// 判断点是否在扇形区域
function inSector2D(point, center, forward, angle, radius) {
    const dir = point.subtract(center).normalize();
    const dist = point.distanceTo(center);
    return dist <= radius && forward.dot(dir) > Math.cos(angle / 2);
}
```
:::

## 常见应用场景 {#common_use_cases}

::: code-group
```js [AI行为与空间交互]
// 让实体A追逐最近的玩家
function chaseNearestPlayer(entity, players, speed) {
    let minDist = Infinity;
    let nearest = null;
    for (const p of players) {
        const d = entity.position().distanceTo(p.position());
        if (d < minDist) {
            minDist = d;
            nearest = p;
        }
    }
    if (nearest) {
        const dir = nearest.position().subtract(entity.position()).normalize();
        entity.setDeltaMovement(dir.multiply(speed));
    }
}

// 让实体在圆环区域内巡逻
function patrolInRing(entity, center, innerR, outerR, speed, tick) {
    const angle = ((tick % 360) / 360) * 2 * Math.PI;
    const r = (innerR + outerR) / 2;
    const target = center.add(Math.cos(angle) * r, 0, Math.sin(angle) * r);
    const dir = target.subtract(entity.position()).normalize();
    entity.setDeltaMovement(dir.multiply(speed));
}
```

```js [粒子动画与空间特效]
// 生成球面粒子
function spawnSphereParticles(level, center, radius, count, particleType) {
    const points = pointsOnSphere(center, radius, count);
    for (const p of points) {
        level.spawnParticles(particleType, p.x, p.y, p.z, 1, 0, 0, 0, 0);
    }
}

// 生成多边形边界粒子
function spawnPolygonParticles(level, polygon, y, particleType) {
    for (let i = 0; i < polygon.length; i++) {
        const a = polygon[i];
        const b = polygon[(i + 1) % polygon.length];
        for (let t = 0; t <= 1; t += 0.1) {
            const x = a.x + (b.x - a.x) * t;
            const z = a.z + (b.z - a.z) * t;
            level.spawnParticles(particleType, x, y, z, 1, 0, 0, 0, 0);
        }
    }
}

// 生成螺旋粒子动画
function spawnSpiralParticles(level, center, radius, height, turns, particleType, count) {
    for (let i = 0; i < count; i++) {
        const t = (i / count) * turns * Math.PI * 2;
        const x = center.x + Math.cos(t) * radius;
        const y = center.y + (height * i) / count;
        const z = center.z + Math.sin(t) * radius;
        level.spawnParticles(particleType, x, y, z, 1, 0, 0, 0, 0);
    }
}
```

```js [视线追踪与命中判定]
// 判断玩家视线是否命中实体AABB
function isLookingAtEntity(player, entity, maxDistance = 10) {
    const origin = player.getEyePosition();
    const direction = player.getLookAngle();
    const min = entity.position().subtract(0.5, 0, 0.5);
    const max = entity.position().add(0.5, entity.getHeight(), 0.5);
    return rayIntersectsAABB(origin, direction, min, max);
}

// 判断玩家是否能"看到"某点（无障碍物）
function canSeePoint(player, point, level) {
    // 伪代码：实际应用需用 rayTrace API
    // 这里只演示向量用法
    const origin = player.getEyePosition();
    const dir = point.subtract(origin).normalize();
    // ...遍历路径判断障碍物
    return true;
}

// 预测实体是否会撞到墙
function willCollide(entity, level) {
    const next = predictNextPosition(entity);
    const block = level.getBlock(next);
    return !block.isAir();
}
```
:::
