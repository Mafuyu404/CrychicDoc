---
title: 已知限制与兼容性问题
description: 基于文档与源码整理的 KubeJS 1.20.1 使用边界
priority: 70
layout: doc
---

# KubeJS 1.20.1 的边界 {#Introduction}

`1.20.1` 的 KubeJS 能处理大量整合包逻辑，但它既不是完整的现代 JavaScript 运行时，也不是无条件开放的 Java 反射环境。很多看起来像“脚本写错了”的问题，实际是运行时边界、版本限制或 Java 互操作规则所致。

本页将这些边界分成两类：

- 前半部分可以直接对应到仓库文档、KubeJS 源码或 Rhino 实现。
- 后半部分更适合作为兼容性提醒，使用时应结合具体环境再确认。

## 常见表现 {#QuickIndex}

| 表现                                                                | 优先检查的方向                              |
| :------------------------------------------------------------------ | :------------------------------------------ |
| `Java.loadClass(...)` 报错，或 `Java.tryLoadClass(...)` 返回 `null` | 类不存在，或被 `class filter` 拦截          |
| `class Foo {}`、默认参数、`...args` 等语法不能正常使用              | 当前 Rhino 语法边界                         |
| 某个参数明明传了 `number`，结果行为仍然异常                         | `TypeWrapper` 转换                          |
| 日志里出现 `candidate methods` 或 `candidate constructors`          | Java 方法或构造函数的重载歧义               |
| 传一个 JS 函数给 Java 接口时直接报错                                | 接口适配限制                                |
| `persistentData` 的行为与 Forge / NeoForge 的认知不一致             | KubeJS 自己的数据入口                       |
| `EntityEvents.hurt` 里能读到伤害值，但改动无效                      | `1.20.1` 中这一事件只提供只读伤害值         |
| 世界生成脚本完全没有效果                                            | `WorldgenEvents.add()` 在 `1.20.1` 尚未支持 |

## 文档与源码可直接对应的限制 {#SourceBackedLimits}

### `Java.loadClass(...)` 会受到 `class filter` 限制 {#ClassFilter}

这里的 `class filter`，可以理解为一套“哪些 Java 类允许暴露给脚本”的过滤规则。`Java.loadClass(...)` 失败，并不只表示类名写错。

`ScriptManager.loadJavaClass(...)` 在加载类时会先检查该类是否允许暴露给脚本：

```java
if (!isClassAllowed(name)) {
    either = Either.right(true);
}

throw Context.reportRuntimeError(
    (found ? "Failed to load Java class '%s': Class is not allowed by class filter!"
           : "Failed to load Java class '%s': Class could not be found!")
        .formatted(name),
    context
);
```

过滤器本身也有独立实现：

```java
if (denyStrong.contains(s)) {
    return V_DENY;
}

if (allowStrong.contains(s)) {
    return V_ALLOW;
}
```

因此，`Java.tryLoadClass(...)` 返回 `null` 时，至少有两种不同原因：

- 该类确实不存在。
- 该类存在，但被 `class filter` 拒绝。

排查时不应只反复修改包名。

### 当前语法环境不能按完整现代 JavaScript 理解 {#RhinoBoundary}

原生 KubeJS 使用的是定制 Rhino。它支持一部分较新的语法特性，但不能按浏览器或 Node.js 的完整现代 JavaScript 能力来预设行为。

例如，Rhino 的 token 常量里把 `class` 归到了 `RESERVED`：

```text
public static final int RESERVED;
ConstantValue: int 128
```

`TokenStream` 对 `class` 关键字也会落到这一分支：

```text
839: aload_2
840: ldc           #139                // String class
...
1396: sipush        128
```

这意味着下面这类写法不应当默认可用：

```js
class Foo {}
function x(arg = 1) {}
function y(...args) {}
```

其中有三件事需要分开理解：

- `class` 是语法层面的限制。
- 默认参数与 `...args` 属于另一组语法能力。
- Java 类能否调用，则属于后面的 Java 互操作问题。

但这并不影响 ES5 风格的构造函数写法。例如：

```js
function Foo(name) {
    this.name = name;
}

const foo = new Foo("example");
```

这类 `function + new` 的构造方式，与 `class Foo {}` 并不是同一套语法。

### `class`、ES5 构造函数、继承与 Java 类不是同一件事 {#ClassAndExtends}

仓库内的 [Java 文档](../Introduction/GlobalScope/Classes/Java) 已明确说明：

```md
KubeJS内无法继承（extends）类，无法实现（implements）接口。
```

这条限制说的是“不能在脚本里按 Java 的继承 / 实现模型继续写下去”，并不等于“不能使用已经拿到的 Java 类”。

如果某个类能够被 `Java.loadClass(...)` 成功加载，通常仍然可以：

- 访问它的静态字段与静态方法。
- 使用 `new` 调用它的构造函数。

例如：

```js
new $AABB(0, 0, 0, 1, 1, 1);

new (Java.loadClass("net.minecraft.world.entity.ExperienceOrb"))(
    level,
    x,
    y,
    z,
    10,
);
```

因此，“不能 `class`”不应被误解为下面任意一种情况：

- 不能使用 ES5 风格的构造函数。
- 不能 `new` 已成功加载的 Java 类。

真正受限的是脚本侧的 `class` 语法，以及 Java 风格的 `extends / implements`。

### 把 JS 函数直接当成 Java 接口实现并不总是可行 {#InterfaceAdapter}

这里先解释一下“函数转 Java 接口”是什么意思。

某些 Java API 需要的参数并不是普通值，而是一个接口实例。脚本里有时会直接写一个函数，Rhino 再尝试把这个函数临时转换成 Java 接口。示意写法大致如下：

```js
someJavaApi(function (value) {
    return value > 0;
});
```

这并不是把“任何 JS 函数”都变成“任何 Java 接口”。Rhino 允许部分函数式接口适配，但这条路径有明确边界。`InterfaceAdapter.create(...)` 中直接给出了两种失败条件：

```java
if (methods.length == 0) {
    throw Context.reportRuntimeError1("msg.no.empty.interface.conversion", ...);
}

if (!firstName.equals(method.getName())) {
    throw Context.reportRuntimeError1("msg.no.function.interface.conversion", ...);
}
```

对应的消息文本是：

```properties
msg.no.function.interface.conversion=
    Cannot convert function to interface {0} since it contains methods with different names
```

这说明“传一个 JS 函数给 Java 接口”并不是通用方案。空接口不行，多个抽象方法名称不一致的接口也不行。

更直接地说：

- 如果 Java 侧期望的是“只有一个明确抽象方法”的接口，自动转换才有成立的可能。
- 如果接口里有多个抽象方法，或这些方法名称彼此不同，Rhino 就无法判断这个函数到底要实现哪一个方法。

这类报错出现后，问题通常不在函数体本身，而在“目标参数其实不是一个能被单个函数安全表示的接口”。

这一点在 `ItemEvents.modification` 里尤其容易被误判。

很多人在看到 `item` 是一个 Java 物品对象后，会自然地写出类似下面这种代码，试图直接覆写某个 Java 方法：

```js
ItemEvents.modification((event) => {
    event.modify("minecraft:apple", (item) => {
        item.someMethod = () => {
            return 1;
        };
    });
});
```

这类写法通常不可行。原因不是箭头函数本身特殊，而是这里真正想做的事情，其实是“在脚本里直接覆写一个 Java 方法”。`ItemEvents.modification` 给你的，是一个已经存在的 Java 对象；KubeJS 不会因为你写了 `item.someMethod = ...`，就替你生成新的 Java 子类，也不会把这段函数自动变成一次合法的方法覆写。这也是为什么如果想定制注册项的功能制作 KubeJS Addon 是必要的。!!该文档应该不会提供真的能实现这一套逻辑的巨魔科技!!

因此，这里需要分清两种完全不同的操作：

- 修改 KubeJS 已经暴露出来、允许改动的字段或属性。
- 直接覆写原有 Java 方法。

前者在 `ItemEvents.modification` 中经常可以做到；后者通常做不到。即使把箭头函数换成 `function () {}`，结论也不会改变。

### `number` 的问题首先来自 `TypeWrapper` {#NumberAndTypeWrappers}

这里的 `TypeWrapper`，可以理解为 KubeJS 在“脚本值”与“Java 参数类型”之间加的一层转换器。某些 API 看上去接收的是 Java 类型，但脚本里传入的 `number` 会先经过这层转换，而不是直接按 `int / long / double` 处理。

Rhino 在做 Java 转换时，会先检查目标类型是否存在可用的 wrapper：

```java
if (context.hasTypeWrappers()) {
    if (context.getTypeWrappers().getWrapperFactory(target, value) != null) {
        return 0;
    }
}
```

而 KubeJS 本体确实注册了多种 wrapper：

```java
typeWrappers.registerSimple(IntProvider.class, UtilsJS::intProviderOf);
typeWrappers.registerSimple(NumberProvider.class, UtilsJS::numberProviderOf);
typeWrappers.registerSimple(TemporalAmount.class, UtilsJS::getTemporalAmount);
```

因此，`number` 相关问题首先要考虑的是：

- 当前 API 的目标类型是什么。
- 这个目标类型是否被 `TypeWrapper` 接管。

这一步之后，才谈得上 Java 重载最终选中了哪一个方法。

### 出现 `candidate methods` 时，应先检查重载歧义 {#OverloadAndCandidates}

Java 的“重载”，是指同一个方法名对应多组不同参数。Rhino 无法判断该选哪一组时，会直接抛出歧义错误。

源码里的报错文本如下：

```properties
msg.method.ambiguous=
    The choice of Java method {0}.{1} matching JavaScript argument types ({2}) is ambiguous; candidate methods are: {3}

msg.constructor.ambiguous=
    The choice of Java constructor {0} matching JavaScript argument types ({1}) is ambiguous; candidate constructors are: {2}
```

因此，日志里出现 `candidate methods` 或 `candidate constructors` 时，优先怀疑的是“同名方法或构造函数太多，Rhino 不知道该选哪一个”。

这时应先尝试显式签名调用：

```text
object["方法名(完整参数类型)"](args)
```

ProbeJS 文档里给过一个直接例子：

```text
event.hand["compareTo(net.minecraft.world.InteractionHand)"](arg)
```

这类写法的作用，是把完整签名直接告诉 Rhino，避免它继续猜测。

需要注意两点：

- 这一步主要解决“重载歧义”。
- 它不是万金油。如果真正的问题是 `TypeWrapper`、参数值本身不对，或 API 本来就不接受当前参数，它仍然可能失败。

构造函数也适用同样的判断逻辑。若日志里出现 `candidate constructors`，应把它视为与方法重载同类的问题。

### `persistentData` 不是 Forge / NeoForge 的持久化附件 {#PersistentData}

仓库文档已经说明，`persistentData` 是 KubeJS 提供的自定义数据存储系统，**不是 NBT 数据，也不是 Forge / NeoForge 的附件体系**。

源码中也可以看到，KubeJS 自己维护了独立字段。以实体为例：

```java
private CompoundTag kjs$persistentData;

if (kjs$persistentData != null && !kjs$persistentData.m_128456_()) {
    tag.m_128365_("KubeJSPersistentData", kjs$persistentData);
}
```

服务器对象上也有独立字段：

```java
@Unique
private final CompoundTag kjs$persistentData = new CompoundTag();
```

因此，更准确的理解是：`persistentData` 是 KubeJS 提供的一套持久化入口，它会写入存档，但其语义不应直接按 Forge / NeoForge 的持久化机制去套。

### `EntityEvents.hurt` 在 `1.20.1` 中只能读不能改 {#HurtReadonly}

`LivingEntityHurtEventJS` 提供的伤害值是只读字段：

```java
private final float amount;

@Info("The amount of damage.")
public float getDamage() {
    return amount;
}
```

这里只有 `getDamage()`，没有对应的 setter。因此，在 `EntityEvents.hurt` 中能读到伤害值，并不表示这一层也能直接改写伤害。

### `WorldgenEvents.add()` 在 `1.20.1` 中本身就未完成 {#WorldgenUnsupported}

这一条可以直接在 `AddWorldgenEventJS` 中看到：

```java
ConsoleJS.STARTUP.error(
    "WorldgenEvents.add() is currently not supported in 1.20 yet! This will be fixed soon, but for now we recommend you comment out worldgen code. Sorry for inconvenience!"
);
```

而且 `addOre(...)`、`addLake(...)` 等入口在这一版本里会直接 `return`。如果世界生成脚本在 `1.20.1` 完全没有效果，应先排除这一限制。

## 兼容性提醒 {#ExperienceNotes}

下面几项更适合作为兼容性提醒。它们在实际编写中很常见，但不像前文那样都有单一、直接的源码入口可供引用。

### `Proxy` 不可用 {#Proxy}

在原生 KubeJS 1.20.1 中，不应使用 `Proxy`。如果你的设计依赖代理拦截、动态转发或类似能力，应直接视为超出本文档默认前提，再去确认附属模组与额外运行时是否真的提供了这部分支持。

### ProbeJS 补全不等于运行时对象本身 {#ProbeJsBoundary}

ProbeJS 的价值很高，但它给出的补全常常停留在父类层级。补全里没有某个方法，不等于运行时一定没有；补全里能看到某个签名，也不等于当前参数一定能通过 Rhino 的转换与重载解析。

因此，补全只能帮助你缩小范围，不能替代日志、JSDoc、源码阅读和实际测试。

### 附属模组会改变本页的前提 {#ExtensionBoundary}

如果整合包额外引入了 `ClassJS`、`Kubeloader` 或其他会扩展 Rhino / KubeJS 能力的附属模组，那么本页的很多结论都需要重新判断。它们改变的是运行时边界，而不是原生 KubeJS 1.20.1 本体的默认能力。
