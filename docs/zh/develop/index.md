---
root: true
title: Develop
description: Minecraft开发文档，包含原版数据包开发、模组开发(Forge/Neoforge/Fabric)、插件开发完整教程和指南
details: 涵盖Minecraft游戏开发的所有方面，包括数据包制作、资源包设计、Mixin框架、模组加载器开发、环境配置等核心主题
hidden: false
collapsed: true
---

<llm-only>
这是开发文档的根页面。包含Minecraft开发相关的文档：
- 原版(vanilla)：数据包和资源包开发
- 模组开发(modding)：Forge、Neoforge、Fabric等平台的模组开发
- 插件开发(plugin)：插件开发教程
</llm-only>

---
root:
  title: 模组开发
  collapsed: false
  children:
      - title: '原版'
        path: 'vanilla'
        noScan: true
        collapsed: false
        file: index
      - title: '模组开发'
        path: 'modding'
        noScan: true
        collapsed: false
        file: index
      - title: '插件开发'
        path: 'plugin'
        noScan: true
        collapsed: false
        file: index
noguide: true
---
