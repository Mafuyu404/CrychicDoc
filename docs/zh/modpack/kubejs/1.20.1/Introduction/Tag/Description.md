---
progress: 95
state: preliminary
title: 描述
hidden: false
priority: 0
---
<llm-only>
本文件为中文内容，需要翻译成英文。它覆盖：
1. 将标签用于分组与赋予功能（如 minecraft:fox_food 下的食物）；
2. 说明现代标签系统与 Forge 矿物词典的关系；
3. 提供原版标签应用的链接；
4. 探讨向标签中加入不存在事物导致加载失败的常见问题；
请在翻译时保留术语与链接。
</llm-only>

# 标签

## 概述

- **`作用`** 标签可用于将游戏事物分组，且可以赋予功能，例如minecraft:fox_food标签下的食物均能繁殖狐狸。

- **`矿物辞典`** 这不是必要的知识，如果你想要知道了解如今的标签系统与昔日forge的矿物词典的关系可查阅：[宝宝早教-矿物词典已经“死”了！！](https://www.mcmod.cn/post/1793.html)

- **`原版标签的应用`** 关于原版已有的物品标签和其被赋予的功能：[minecraft-wiki/标签](https://zh.minecraft.wiki/w/%E6%A0%87%E7%AD%BE#%E7%89%A9%E5%93%81)

- **`常见问题`** 在添加xx模组后某个标签消失，或你已经不知道某条本该存在的标签已经“消失”了多久，事实上这是Minecraft原版标签加载机制与模组作者的失误共同导致的，当不存在的游戏事物被加入标签，例如现将一个游戏中不存在的物品加入minecraft:fox_food标签，游戏会因为找不到这个物品从而导致minecraft:fox_food失效（使用kubejs添加标签不需担心此问题），为了避免问题，可以安装该模组[Load-My-F***ing-Tags](https://www.mcmod.cn/class/11473.html)
