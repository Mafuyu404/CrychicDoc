---
layout: home

hero:
    name: "CrychicDoc"
    text: "Minecraft Modpack & Development Knowledge Base"
    tagline: "Maintained by PickAID for modpack docs, KubeJS scripting, and Minecraft development collaboration."
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
              alt: "KubeJS documentation preview"
              width: "720px"
              borderRadius: 16px
              shadow: "0 20px 40px rgba(0,0,0,0.3)"
              x: "5%"
              y: "20%"
            - type: image
              src:
                  light: "/imgs/screenshots/develop.png"
                  dark: "/imgs/screenshots/developDark.png"
              alt: "Development documentation preview"
              width: "480px"
              borderRadius: 16px
              shadow: "0 20px 40px rgba(0,0,0,0.3)"
              x: "75%"
              y: "40%"
            - type: image
              src:
                  light: "/imgs/screenshots/rules.png"
                  dark: "/imgs/screenshots/rulesDark.png"
              alt: "Collaboration rules preview"
              width: "360px"
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
          text: "KubeJS Docs"
          link: /en/modpack/kubejs/Catalogue
        - theme: brand
          text: "Mods Index"
          link: /en/mods/Catalogue
        - theme: alt
          text: "Development"
          link: /en/develop/Catalogue
        - theme: alt
          text: "Developer Docs"
          link: /en/doc/Catalogue
        - theme: outline
          text: "Plugins Guide"
          link: /en/doc/pluginsGuide
        - theme: ghost
          text: "Collaboration Rules"
          link: /en/doc/rules

features:
    - icon: 🧪
      title: "KubeJS Path"
      details: "From introduction to advanced script patterns across versions."
      link: /en/modpack/kubejs/Catalogue
    - icon: 🧩
      title: "Mods Atlas"
      details: "Category-based mod references for server, tech, performance, and more."
      link: /en/mods/Catalogue
    - icon: 🛠️
      title: "Dev Guides"
      details: "Modding, plugins, datapacks, and resource pack workflows."
      link: /en/develop/Catalogue
    - icon: 📖
      title: "Plugins Guide"
      details: "Markdown/container/component capability guide used by this site."
      link: /en/doc/pluginsGuide
    - icon: 🧭
      title: "Developer Docs"
      details: "Framework workflow, extension architecture, and hero extension handbooks."
      link: /en/doc/Catalogue
    - icon: 📚
      title: "Info Index"
      details: "Community indexes, requests, and Q&A hubs."
      link: /en/info/Catalogue
    - icon: 🤝
      title: "Contribution Rules"
      details: "Collaboration conventions and contribution checklist."
      link: /en/doc/rules
    - icon: 👥
      title: "Site Catalogue"
      details: "Open the English root catalogue for mods, modpacks, development, and info sections."
      link: /en/Catalogue
    - icon: 📦
      title: "Modpack Docs"
      details: "Operational and recommendation documents for modpack maintenance."
      link: /en/modpack/Catalogue

gitChangelog: false
---

<Contributors
  owner="PickAID"
  repo="CrychicDoc"
  :max-count="100"
  :show-contributions="true"
  :enable-cache="true"
  title="Contributors"
  locale="en-US"
/>

<commitsCounter
  username="PickAID"
  repoName="CrychicDoc"
  :daysToFetch="14"
/>
