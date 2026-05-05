---
layout: home

hero:
    name: "CrychicDoc"
    text: "我的世界整合包与开发知识库"
    tagline: "由 PickAID 维护，聚合 KubeJS、模组资料、开发文档与协作规范。"
    typography:
        type: grouped-float
        motion:
            intensity: 0.84
            title: { x: 10, y: -6, scale: 2.08 }
            text: { x: 12, y: -4, scale: 0.9 }
            tagline: { x: 10, y: 1, scale: 0.85 }
            image: { x: 14, y: -7, scale: 1.08 }
            transitionDuration: 700
            transitionDelayStep: 60
            transitionEasing: "cubic-bezier(0.16, 1, 0.3, 1)"
    background:
        type: "shader"
        shader:
            type: "silk"
            speed: 0.72
            uniforms:
                uColor1:
                    type: vec3
                    value:
                        light: [0.96, 0.97, 0.98]
                        dark: [0.06, 0.08, 0.14]
                uColor2:
                    type: vec3
                    value:
                        light: [0.98, 0.99, 1.0]
                        dark: [0.10, 0.13, 0.20]
                uColor3:
                    type: vec3
                    value:
                        light: [0.92, 0.94, 0.97]
                        dark: [0.18, 0.24, 0.36]
    colors:
        title:
            light: "rgba(17, 24, 39, 0.95)"
            dark: "rgba(243, 244, 246, 0.95)"
        text:
            light: "rgba(31, 41, 55, 0.85)"
            dark: "rgba(229, 231, 235, 0.85)"
        tagline:
            light: "rgba(75, 85, 99, 0.75)"
            dark: "rgba(156, 163, 175, 0.75)"
        searchBackground:
            light: "rgba(255, 255, 255, 0.42)"
            dark: "rgba(15, 23, 42, 0.24)"
        searchBackgroundScrolled:
            light: "rgba(255, 255, 255, 0.62)"
            dark: "rgba(15, 23, 42, 0.38)"
        searchHoverBackground:
            light: "rgba(255, 255, 255, 0.56)"
            dark: "rgba(15, 23, 42, 0.34)"
        searchHoverBackgroundScrolled:
            light: "rgba(255, 255, 255, 0.74)"
            dark: "rgba(15, 23, 42, 0.46)"
        searchText:
            light: "rgba(15, 23, 42, 0.82)"
            dark: "rgba(255, 255, 255, 0.94)"
        searchTextScrolled:
            light: "rgba(15, 23, 42, 0.88)"
            dark: "rgba(255, 255, 255, 0.96)"
        searchTextMuted:
            light: "rgba(15, 23, 42, 0.56)"
            dark: "rgba(255, 255, 255, 0.72)"
        searchTextMutedScrolled:
            light: "rgba(15, 23, 42, 0.62)"
            dark: "rgba(255, 255, 255, 0.76)"
        searchBorder:
            light: "rgba(15, 23, 42, 0.10)"
            dark: "rgba(255, 255, 255, 0.14)"
        searchBorderScrolled:
            light: "rgba(15, 23, 42, 0.12)"
            dark: "rgba(255, 255, 255, 0.18)"
        searchKeyBackground:
            light: "rgba(15, 23, 42, 0.06)"
            dark: "rgba(255, 255, 255, 0.12)"
        searchKeyBackgroundScrolled:
            light: "rgba(15, 23, 42, 0.08)"
            dark: "rgba(255, 255, 255, 0.16)"
        searchKeyText:
            light: "rgba(15, 23, 42, 0.60)"
            dark: "rgba(255, 255, 255, 0.76)"
        searchKeyTextScrolled:
            light: "rgba(15, 23, 42, 0.66)"
            dark: "rgba(255, 255, 255, 0.82)"
    waves:
        enabled: true
        animated: true
        height: 140
        speed: 1.5
        layers:
            - amplitude: 50
              wavelength: 440
              opacity: 0.22
            - amplitude: 40
              wavelength: 240
              opacity: 0.22
            - amplitude: 34
              wavelength: 200
              opacity: 0.5
            - amplitude: 28
              wavelength: 170
              opacity: 0.92
    floating:
        enabled: true
        opacity: 0.95
        density: 8
        motion:
            enabled: true
            style: drift
            durationMin: 14
            durationMax: 24
            drift: 24
        items:
            - type: image
              src:
                  light: "/imgs/screenshots/kubejs.png"
                  dark: "/imgs/screenshots/kubejsDark.png"
              alt: "KubeJS 文档预览"
              width: "clamp(520px, 50vw, 720px)"
              borderRadius: 16px
              shadow: "0 20px 40px rgba(0,0,0,0.3)"
              x: "5%"
              y: "20%"
            - type: image
              src:
                  light: "/imgs/screenshots/develop.png"
                  dark: "/imgs/screenshots/developDark.png"
              alt: "开发文档预览"
              width: "clamp(360px, 33.333vw, 480px)"
              borderRadius: 16px
              shadow: "0 20px 40px rgba(0,0,0,0.3)"
              x: "75%"
              y: "40%"
            - type: image
              src:
                  light: "/imgs/screenshots/rules.png"
                  dark: "/imgs/screenshots/rulesDark.png"
              alt: "协作规范预览"
              width: "clamp(280px, 25vw, 360px)"
              borderRadius: 12px
              shadow: "0 10px 20px rgba(0,0,0,0.2)"
              x: "82%"
              y: "12%"
            - type: card
              title: "Docs + Mods + Dev"
              description: "One portal for writing, scripting, and modpack maintenance."
              x: "8%"
              y: "62%"
            - type: code
              code: "npm run docs:build"
              x: "35%"
              y: "80%"
    image:
        light: /logodark.png
        dark: /logo.png
        alt: CrychicDoc
        width: "min(900px, 130%)"
    actions:
        - theme: brand
          text: "KubeJS 文档"
          link: /zh/modpack/kubejs/Catalogue
        - theme: brand
          text: "模组索引"
          link: /zh/mods/Catalogue
        - theme: alt
          text: "开发文档"
          link: /zh/develop/Catalogue
        - theme: alt
          text: "开发手册"
          link: /zh/doc/Catalogue
        - theme: outline
          text: "插件指南"
          link: /zh/doc/pluginsGuide
        - theme: ghost
          text: "协作规范"
          link: /zh/doc/Catalogue

features:
    - icon: 🧪
      title: "KubeJS 路线"
      details: "从入门到进阶，覆盖多个版本的脚本实践。"
      link: /zh/modpack/kubejs/Catalogue
    - icon: 🧩
      title: "模组图谱"
      details: "按分类管理服务器、科技、性能与玩法相关模组。"
      link: /zh/mods/Catalogue
    - icon: 🛠️
      title: "开发指南"
      details: "模组开发、插件、数据包与资源包工作流。"
      link: /zh/develop/Catalogue
    - icon: 📖
      title: "插件指南"
      details: "站点所用 Markdown 容器与组件能力总览。"
      link: /zh/doc/pluginsGuide
    - icon: 🧭
      title: "开发手册"
      details: "框架工作流、扩展架构与 Hero 扩展文档入口。"
      link: /zh/doc/Catalogue
    - icon: 📚
      title: "信息索引"
      details: "问题反馈、建议与社区资料入口。"
      link: /zh/info/Catalogue
    - icon: 🤝
      title: "协作规范"
      details: "贡献流程、命名约定与文档协作规则。"
      link: /zh/doc/Catalogue
    - icon: 👥
      title: "总站目录"
      details: "进入中文总站目录，按模块浏览模组、整合包、开发与信息索引。"
      link: /zh/Catalogue
    - icon: 📦
      title: "整合包文档"
      details: "整合包说明、推荐与维护相关文档。"
      link: /zh/modpack/Catalogue

gitChangelog: false
---

<Contributors
  owner="PickAID"
  repo="CrychicDoc"
  :max-count="100"
  :show-contributions="true"
  :enable-cache="true"
  title="贡献者"
  locale="zh-CN"
/>

<commitsCounter
  username="PickAID"
  repoName="CrychicDoc"
  :daysToFetch="14"
/>
