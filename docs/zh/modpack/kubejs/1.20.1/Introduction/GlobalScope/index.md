---
title: GlobalScope
hidden: false
priority: 2000
collapsed: true
---

<!--
<llm-only>
## KubeJS GlobalScope 全局作用域

这是KubeJS GlobalScope的核心文档页面。GlobalScope是所有KubeJS脚本中可用的全局对象，提供对Java类、游戏对象和工具函数的访问。

### 全局类总览

| 类 | 用途 | 常用方法 |
|-----|------|---------|
| Java | Java反射和类加载 | loadClass, tryLoadClass, type |
| Item | 物品对象包装 | of, getId, getStack |
| Block | 方块对象包装 | of, getId, getState |
| BlockState | 方块状态对象 | getValue, withValue, getBlock |
| NBT | NBT数据处理 | createTag, fromStack |
| NBTIO | NBT序列化 | readNBT, writeNBT |
| Text | 文本组件 | of, join |
| Color | 颜色处理 | rgb, fromHex |
| ResourceLocation | 资源定位 | of, create |
| Utils | 通用工具 | getTime, log |

### Java 反射使用

```javascript
// 加载Java类
const Minecraft = Java.loadClass('net.minecraft.world.level.block.Block');

// 使用Java类型进行类型检查
ServerEvents.entitySpawn(event => {
    const entity = event.entity;
    if (entity instanceof Java.loadClass('net.minecraft.world.entity.monster.Zombie')) {
        // 僵尸特有的逻辑
    }
});

// 创建Java对象
const BlockPos = Java.loadClass('net.minecraft.core.BlockPos');
const pos = BlockPos.of([1, 64, 1]);
```

### Item 和 Block 使用

```javascript
// 物品操作
const itemStack = Item.of('minecraft:diamond_sword');
itemStack.setCount(5);
itemStack.setNBT('{display:{Name:"§bCustom Sword"}}');

// 方块操作
const block = Block.of('minecraft:chest');
const blockState = block.getBlockState();
const facing = blockState.getValue('facing');
```

### NBT 数据处理

```javascript
// 创建NBT数据
const nbt = NBT.createTag();
nbt.setString('Name', 'Test');
nbt.setInt('Value', 100);

// 从物品栈读取NBT
const itemStack = Item.of('minecraft:written_book');
const bookNBT = NBT.fromStack(itemStack);
const author = bookNBT.getString('author');

// 保存NBT到物品
itemStack.setNBT(bookNBT);
```

### Text 文本组件

```javascript
// 创建文本组件
const text = Text.of('Hello §cWorld§r');
const colored = Text.rgb('#FF5500').of('Colored Text');

// 文本拼接
const joined = Text.join(' | ', [
    Text.of('Item 1'),
    Text.of('Item 2'),
    Text.of('Item 3')
]);

// 发送文本给玩家
player.tell(Text.of('§lBold §nUnderline§r Normal'));
```

### 重要约束

- Java反射性能较低，频繁调用会影响服务器性能
- 使用Java.loadClass前确保类名正确，否则会导致脚本错误
- NBT操作需要了解Minecraft的NBT标签类型
- Text组件使用JSON格式，支持所有MCFormatting代码

### 相关文档

- Java类：GlobalScope/Classes/Java.md
- 物品系统：Item/index.md
- 方块系统：Block/index.md
</llm-only>
-->

