<template>
    <!-- 使用 v-if 根据 showComment 是否为 true 来控制评论组件的显示 -->
    <div v-if="showComment" class="giscus-wrapper" ref="giscusContainer"></div>
</template>

<script lang="ts" setup>
    import { ref, watch, onMounted, computed } from "vue";
    import { useData, useRoute } from "vitepress";

    // 从 useData 中获取 frontmatter、isDark 和 lang
    const { isDark, lang, frontmatter } = useData();
    const route = useRoute();

    // 根据 frontmatter.showComment 控制显示，默认为 true
    const showComment = computed(() => frontmatter.value.showComment !== false);

    const translations = {
        "en-US": {
            langCode: "en",
        },
        "zh-CN": {
            langCode: "zh-CN",
        },
    };

    const currentLangConfig = computed(() => {
        return translations[lang.value] || translations["en-US"];
    });

    const extractTerm = (path: string) => {
        const cleanedPath = path.replace(/^\/[a-z]{2}\//, "");
        return cleanedPath.length > 0 ? cleanedPath : "none";
    };

    const giscusContainer = ref<HTMLElement | null>(null);

    const loadGiscus = () => {
        if (!giscusContainer.value) return;

        giscusContainer.value.innerHTML = "";

        const script = document.createElement("script");
        script.src = "https://giscus.app/client.js";
        script.async = true;
        script.setAttribute("data-repo", "PickAID/CrychicDoc");
        script.setAttribute("data-repo-id", "R_kgDOMnN0IQ");
        script.setAttribute("data-category", "Announcements");
        script.setAttribute("data-category-id", "DIC_kwDOMnN0Ic4Ch3qm");
        script.setAttribute("data-mapping", "specific");
        script.setAttribute("data-term", extractTerm(route.path)); // 动态提取 term
        script.setAttribute("data-strict", "1");
        script.setAttribute("data-reactions-enabled", "1");
        script.setAttribute("data-emit-metadata", "0");
        script.setAttribute("data-input-position", "top");
        script.setAttribute("data-lang", currentLangConfig.value.langCode);
        script.setAttribute(
            "data-theme",
            isDark.value ? "noborder_dark" : "noborder_light"
        );
        script.setAttribute("crossorigin", "anonymous");
        giscusContainer.value.appendChild(script);
    };

    onMounted(() => {
        if (showComment.value) {
            loadGiscus();
        }
    });

    watch(
        () => route.path,
        () => {
            if (showComment.value) {
                loadGiscus();
            }
        }
    );

    watch(
        () => isDark.value,
        () => {
            if (showComment.value) {
                const iframe = document.querySelector(
                    "iframe.giscus-frame"
                ) as HTMLIFrameElement;
                if (iframe) {
                    iframe.contentWindow?.postMessage(
                        {
                            giscus: {
                                setConfig: {
                                    theme: isDark.value
                                        ? "noborder_dark"
                                        : "noborder_light",
                                },
                            },
                        },
                        "https://giscus.app"
                    );
                }
            }
        }
    );

    watch(
        () => lang.value,
        () => {
            if (showComment.value) {
                const iframe = document.querySelector(
                    "iframe.giscus-frame"
                ) as HTMLIFrameElement;
                if (iframe) {
                    iframe.contentWindow?.postMessage(
                        {
                            giscus: {
                                setConfig: {
                                    lang: currentLangConfig.value.langCode,
                                },
                            },
                        },
                        "https://giscus.app"
                    );
                } else {
                    loadGiscus();
                }
            }
        }
    );
</script>

<style>
    .giscus-wrapper {
        margin-top: 2rem;
    }

    main .giscus,
    main .giscus-frame {
        width: 100%;
    }

    main .giscus-frame {
        border: none;
    }
</style>
