---
title: 扩展架构说明
description: CrychicDoc 中配置、运行时、组件、插件与样式的职责边界与落点规则。
---

# 扩展架构说明

本页说明 CrychicDoc 的框架代码应该放在哪里，以及如何保持契约、运行时与渲染层职责清晰。

## 架构分层规则

- `api` 负责契约事实来源
- `runtime` 负责共享状态与生命周期
- `theme/components` 负责渲染
- `config` 负责项目默认值与注册接线
- `docs/**` 负责示例、用户文档与开发手册

## 创建普通页面

新增普通文档页时：

1. 在两个语言目录中创建相同相对路径的 markdown 文件。
2. 从最基础的 frontmatter 开始：

```yaml
---
title: 示例页面
layout: doc
description: 这页讲什么。
---
```

3. 只有在页面需要从导航、文档入口页或首页被发现时，才补这些入口。
4. 如果页面引入了新的契约或工作流，也要同步更新对应开发文档。

## 创建首页或 Hero 页面

落地页或开发入口页使用这一套：

1. 使用 `layout: home`。
2. 先定义 Hero frontmatter：

```yaml
---
layout: home
hero:
  name: Developer Docs
  text: Extend CrychicDoc Safely
  tagline: Runtime、frontmatter、plugin 与内容注册
  actions:
    - theme: brand
      text: Development Workflow
      link: /en/doc/developmentWorkflow
---
```

3. Hero 主体稳定后，再补 `features` 或 `featureCards`。
4. 如果页面成为主要入口，同步更新 `docs/en/index.md`、`docs/zh/index.md` 与 locale nav 文件。

## 组件扩展

1. 新组件放入 `.vitepress/theme/components/<category>/`。
2. 需复用的组件导出到 `.vitepress/utils/vitepress/componentRegistry/**`。
3. 面向 Markdown 的组件在 `.vitepress/utils/vitepress/components.ts` 注册。
4. 组件有 UI 文案时补充 locale 资源。
5. 保持组件 ID 与 i18n 映射一致。

## 深入说明：注册新的内容组件

当组件属于内容层，并会出现在 markdown 页面或文档壳层时，按这条链路处理：

1. 在 `.vitepress/theme/components/content/` 下创建组件。
2. 如果该目录使用 barrel，先导出到 `.vitepress/theme/components/content/index.ts`。
3. 如果主题内部其他模块要通过 registry 复用它，再导出到 `.vitepress/utils/vitepress/componentRegistry/contentRegistry.ts`。
4. 如果 markdown 页面需要直接通过标签名调用它，再注册到 `.vitepress/utils/vitepress/components.ts`。
5. 如果组件使用了 `useSafeI18n`，补全 locale JSON，并保持 `component-id-mapping.json` 对齐。
6. 如果它由 markdown 插件输出，确保插件输出的是已经注册过的标签。
7. 至少补一页真实 markdown 示例，确保构建过程会验证它。

可以这样理解：

- `componentRegistry/contentRegistry.ts`
  给主题内部复用。
- `components.ts`
  给 markdown / 全局标签注册。

## 函数与运行时扩展

1. 纯数据规范化逻辑放入 `.vitepress/utils/vitepress/api/**`。
2. 共享状态型能力放入 `.vitepress/utils/vitepress/runtime/**`。
3. 从最近的 `index.ts` barrel 暴露公共入口。
4. 优先建设一个共享运行时，而不是在多个组件里各写一套 observer。

已有优秀样例：

- 主题同步：`.vitepress/utils/vitepress/runtime/theme/**`
- Hero 导航自适应：`.vitepress/utils/vitepress/runtime/hero/navAdaptiveState.ts`
- Frontmatter 契约规范化：`.vitepress/utils/vitepress/api/frontmatter/hero/HeroFrontmatterApi.ts`

## 配置扩展

主要文件：

- `.vitepress/config/project-config.ts`
- `.vitepress/config/lang/**`
- `.vitepress/config/markdown-plugins.ts`
- `.vitepress/config/shaders/**`

## 样式扩展

请遵循 `.vitepress/theme/styles/index.css` 的导入层次。

- 局部视觉：scoped `<style>`
- 跨组件共享 Token 与全局选择器：`.vitepress/theme/styles/**`
- 主题敏感或运行时敏感的值：frontmatter 或配置驱动 CSS 变量

## 创建新的 Markdown 插件

1. 在 `.vitepress/plugins/**` 实现。
2. 在 `.vitepress/config/markdown-plugins.ts` 注册。
3. 若渲染依赖 Vue 组件，在 `.vitepress/utils/vitepress/components.ts` 一并注册。
4. 在中英文文档树中增加示例页面。

如果插件会成为一个可复用的作者入口，也要同时补上：

- markdown 语法
- 对应渲染组件名
- 是否依赖 frontmatter
- 两个语言都能直接复制使用的示例

## 文件职责速查表

- 新的 hero 字段或嵌套 frontmatter 键：`.vitepress/utils/vitepress/api/frontmatter/**`
- 主题稳定、观察器或共享生命周期：`.vitepress/utils/vitepress/runtime/**`
- 新的 Vue 视觉块或结构组件：`.vitepress/theme/components/**`
- 新的 Markdown 语法：`.vitepress/plugins/**` 与 `.vitepress/config/markdown-plugins.ts`
- 新的全局 Token 或共享皮肤：`.vitepress/theme/styles/**`
- 新的开发者手册页面：`docs/en/doc/**` 与 `docs/zh/doc/**`
