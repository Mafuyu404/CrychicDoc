---
progress: 95
state: preliminary
title: Description
hidden: false
priority: 0
---

<llm-only>
This file is in Chinese and needs translation to English. It covers:
1. Tags: Used to group game content and assign functionality (e.g., fox food items in minecraft:fox_food tag)
2. Ore Dictionary: Historical context comparing modern tag system to Forge's Ore Dictionary
3. Vanilla Tag Applications: Links to Minecraft Wiki for vanilla tag functionality
4. Common Issues: Tag loading issues when non-existent items are added to tags

Translation needed: Convert all Chinese content to English while preserving technical terms and links.
</llm-only>

# 标签

## 概述

- **`作用`** 标签可用于将游戏事物分组，且可以赋予功能，例如minecraft:fox_food标签下的食物均能繁殖狐狸。

- **`矿物辞典`** 这不是必要的知识，如果你想要知道了解如今的标签系统与昔日forge的矿物词典的关系可查阅：[宝宝早教-矿物词典已经"死"了！！](https://www.mcmod.cn/post/1793.html)

- **`原版标签的应用`** 关于原版已有的物品标签和其被赋予的功能：[minecraft-wiki/标签](https://zh.minecraft.wiki/w/%E6%A0%87%E7%AD%BE#%E7%89%A9%E5%93%81)

- **`常见问题`** 在添加xx模组后某个标签消失，或你已经不知道某条本该存在的标签已经"消失"了多久，事实上这是Minecraft原版标签加载机制与模组作者的失误共同导致的，当不存在的游戏事物被加入标签，例如现将一个游戏中不存在的物品加入minecraft:fox_food标签，游戏会因为找不到这个物品从而导致minecraft:fox_food失效（使用kubejs添加标签不需担心此问题），为了避免问题，可以安装该模组[Load-My-F***ing-Tags](https://www.mcmod.cn/class/11473.html)
