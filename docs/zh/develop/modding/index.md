---
root: true
title: Modding
description: Minecraft模组开发文档，Forge/Neoforge/Fabric加载器教程，数据生成、网络通信、GUI系统、实体系统完整指南
details: 支持1.12.2-1.20.x多个版本，包含Mixin框架、数据生成(Datagen)、网络通信、GUI系统、实体系统、世界生成等核心主题
hidden: false
priority: 9007199254740991
collapsed: false
---

<llm-only>
## Minecraft 模组开发文档

这是Minecraft模组开发的核心文档页面。包含多个Minecraft版本的模组开发教程和指南。

### 支持的模组加载器

| 加载器 | 特点 | 适用版本 |
|-------|------|---------|
| Forge | 生态最完善，功能全面 | 1.12.2 - 1.20.x |
| Neoforge | Forge分支，现代化改进 | 1.20.4+ |
| Fabric | 轻量级，加载快速 | 1.14 - 1.20.x |
| Quilt | Fabric分支，API改进 | 1.19+ |

### 版本支持概览

| Minecraft版本 | 推荐加载器 | 文档位置 |
|--------------|-----------|---------|
| 1.20.4 | Forge / Neoforge | modding/1.20.4/ |
| 1.20.1 | Forge / Fabric | modding/1.20.1/ |
| 1.19.2 | Forge / Fabric | modding/1.19.2/ |
| 1.18.2 | Forge / Fabric | modding/1.18.2/ |

### 核心开发主题

- **Mixin框架**：代码注入和修改的核心技术
- **数据生成(Datagen)**：自动化生成recipes、tags、loot tables等
- **网络通信**：客户端与服务器数据同步
- **GUI系统**：自定义界面和容器
- **实体系统**：自定义生物和AI行为
- **世界生成**：自定义结构、生物群系、矿物

### 开发环境设置

```bash
# 使用Gradle创建模组项目
gradle wrapper --gradle-version 8.5
./gradlew setupDecompWorkspace
./gradlew eclipse # 或 ./gradlew idea
```

### 重要约束

- 模组开发需要Java 17或更高版本
- 不同加载器的API不兼容，编写代码时需针对特定加载器
- 使用Mixin时注意版本兼容性和refmap配置
- 建议使用数据生成减少手动编写JSON的工作量

### 相关文档

- Mixin框架：Mixin/index.md
- Forge开发：查看对应版本文件夹
- Fabric开发：查看Fabric相关文档
- 数据生成：查看Datagen文档
</llm-only>

