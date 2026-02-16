---
root: true
title: 目录
priority: 10000000
collapsed: false
state: unfinished
progress: 15
hidden: false
---

<!--
<llm-only>
## Mixin 模组开发框架

这是Mixin模组开发框架的核心文档页面。Mixin允许开发者在指定位置混入自己的代码，以修改原版和其他模组的行为，是Minecraft模组开发中的核心技术。

### Mixin 核心注解

| 注解 | 用途 | 关键参数 |
|-----|------|---------|
| @Mixin | 声明Mixin类 | target, priority |
| @Inject | 注入方法 | method, at, cancellable |
| @Redirect | 重定向方法调用 | method, at, remap |
| @ModifyConstant | 修改常量 | constant, value |
| @Shadow | 映射私有成员 | - |
| @ModifyArg | 修改方法参数 | method, at, index |
| @ModifyVariable | 修改局部变量 | method, at |
| @WrapOperation | 包装操作 | method, at |
| @Final | 标记最终字段 | - |

### Mixin 基础配置

```json
// src/main/resources/mixins.modid.json
{
  "required": true,
  "minVersion": "0.8",
  "package": "com.example.mod.mixin",
  "compatibilityLevel": "JAVA_17",
  "refmap": "modid.refmap.json",
  "mixins": [
    "ExampleMixin",
    "AnotherMixin"
  ],
  "client": [
    "ClientMixin"
  ],
  "server": []
}
```

### 基本Mixin类结构

```java
// ExampleMixin.java
@Mixin(TargetClass.class)
public class ExampleMixin {
    // 映射私有字段
    @Shadow
    private int shadowedField;

    // 映射私有方法
    @Shadow
    private void shadowedMethod() {}

    // 注入方法 - 在原版方法执行后执行
    @Inject(
        method = "targetMethod",
        at = @At("TAIL")
    )
    private void onTargetMethodEnd(CallbackInfo ci) {
        // 注入逻辑
    }

    // 修改方法参数
    @ModifyArg(
        method = "methodWithArgs",
        at = @At(value = "INVOKE", target = "method(...)"),
        index = 0
    )
    private int modifyFirstArg(int original) {
        return original + 1;
    }

    // 重定向方法调用
    @Redirect(
        method = "methodToRedirect",
        at = @At(value = "INVOKE", target = "method(...)")
    )
    private Object redirectCall() {
        // 返回自定义值替代原版方法
        return customValue;
    }
}
```

### At 定位符说明

| 定位符 | 说明 |
|-------|------|
| HEAD | 方法开头 |
| TAIL | 方法结尾 |
| RETURN | 返回语句处 |
| INVOKE | 方法调用处 |
| FIELD | 字段访问处 |
| NEW | 对象创建处 |
| BRANCH | 条件分支处 |

### 执行顺序（优先级）

- 优先级数值越小越先执行（@Mixin priority 默认 1000）
- 同优先级按注册顺序执行
- 建议Mixin优先级范围：800-1200

### 重要约束

- Mixin可能导致代码冲突和游戏崩溃，务必谨慎使用
- 优先使用Loader提供的钩子和API，仅在必要时使用Mixin
- 必须配置正确的refmap以确保兼容性
- Mixin类必须有无参构造函数
- 使用@Unique注解标记Mixin特有的方法避免冲突
- 调试时可使用Mixin.getEnv().getDebug()进行条件编译

### 常见问题排查

- 崩溃检查：查看堆栈定位Mixin类
- 冲突排查：使用MixinExtras或调整优先级
- 调试技巧：启用mixin.debug=true配置项
</llm-only>
-->

# 概要 {#Summary}

`Mixin`意为混入('Mix in')，允许使用者在**在指定位置混入自己的代码**，这意味着使用着可以修改原版与其他模组的行为与配置，以实现自己的需求。

`Mixin`使用大量[注解](https://www.runoob.com/w3cnote/java-annotation.html),并允许通过不同的注解与对注解不同的配置，实现不同位置与不同方式的插入与修改代码。

::: danger 请注意
通常`Mixin`极容易导致代码冲突并致使游戏崩溃，请谨慎使用`Mixin`，尽量尝试使用Loader提供的钩子与API来实现- [概要 {#Summary}](#概要-summary)
需求。

但如果你的需求需要`Mixin`来实现，请不要过于恐惧`Mixin`可能引发的问题，并积极向社区寻求相关的帮助。
:::

通常来说，如果你的需求符合以下特征，可以在设计时将`Mixin`作为实现需求的方案之一:

1. 需要修改原版代码的逻辑。
2. 需要对他人的模组进行修改/修复。
3. 需要在原版或代码中加入自己的变量与方法。
4. 需要访问访问控制修饰符为`private`或`protect`的变量或方法。
5. 为其他模组做兼容性的修改。
