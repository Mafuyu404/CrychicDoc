<template>
    <div v-if="xyebbsId" class="xyebbs-container">
        <div class="xyebbs-card" :class="{ expanded: isExpanded }">
            <!-- Header -->
            <div class="xyebbs-header" @click="toggleExpanded">
                <div class="xyebbs-badge">
                    <div class="xyebbs-icon-wrapper">
                        <img
                            :src="data.logoUrl || 'https://pic.xyeidc.com/i/6a59d895-a4fe-4b8d-8450-cdbd814c7ede.png'"
                            :alt="data.name || 'XyeBBS'"
                            class="xyebbs-icon no-preview"
                            @error="handleImageError"
                        />
                    </div>
                    <div class="xyebbs-title-section">
                        <span class="xyebbs-title">{{
                            data.name || t.title
                        }}</span>
                        <div
                            v-if="!isExpanded && data.downloadCount > 0"
                            class="xyebbs-download-badge"
                        >
                            {{ formatNumber(data.downloadCount) }}
                            {{ t.downloads }}
                        </div>
                    </div>
                </div>
                <div class="xyebbs-toggle" :class="{ expanded: isExpanded }">
                    <svg width="20" height="20" viewBox="0 0 24 24">
                        <path
                            fill="currentColor"
                            d="M7.41 8.59L12 13.17l4.59-4.58L18 10l-6 6-6-6 1.41-1.41z"
                        />
                    </svg>
                </div>
            </div>

            <!-- Content -->
            <Transition name="xyebbs-slide">
                <div v-show="isExpanded" class="xyebbs-content">
                    <!-- Loading State -->
                    <div v-if="isLoading" class="xyebbs-state-card loading">
                        <div class="state-icon">
                            <div class="loading-spinner"></div>
                        </div>
                        <div class="state-text">{{ t.loading }}</div>
                    </div>

                    <!-- Error State -->
                    <div v-else-if="errorMsg" class="xyebbs-state-card error">
                        <div class="state-icon">
                            <svg width="24" height="24" viewBox="0 0 24 24">
                                <path
                                    fill="currentColor"
                                    d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm1 15h-2v-2h2v2zm0-4h-2V7h2v6z"
                                />
                            </svg>
                        </div>
                        <div class="state-text">
                            <div class="state-title">{{ t.errorTitle }}</div>
                            <div class="state-message">{{ errorMsg }}</div>
                        </div>
                    </div>

                    <!-- Data Content -->
                    <div v-else-if="data.downloadCount > 0" class="xyebbs-data">
                        <!-- Statistics Grid -->
                        <div class="stats-grid">
                            <div class="stat-card primary">
                                <div class="stat-icon">
                                    <svg
                                        width="20"
                                        height="20"
                                        viewBox="0 0 24 24"
                                    >
                                        <path
                                            fill="currentColor"
                                            d="M5,20H19V18H5M19,9H15V3H9V9H5L12,16L19,9Z"
                                        />
                                    </svg>
                                </div>
                                <div class="stat-content">
                                    <div class="stat-value">
                                        {{ formatNumber(data.downloadCount) }}
                                    </div>
                                    <div class="stat-label">
                                        {{ t.downloadCount }}
                                    </div>
                                </div>
                            </div>

                            <div v-if="data.author" class="stat-card">
                                <div class="stat-icon">
                                    <svg
                                        width="20"
                                        height="20"
                                        viewBox="0 0 24 24"
                                    >
                                        <path
                                            fill="currentColor"
                                            d="M12,4A4,4 0 0,1 16,8A4,4 0 0,1 12,12A4,4 0 0,1 8,8A4,4 0 0,1 12,4M12,14C16.42,14 20,15.79 20,18V20H4V18C4,15.79 7.58,14 12,14Z"
                                        />
                                    </svg>
                                </div>
                                <div class="stat-content">
                                    <div class="stat-value">
                                        {{ data.author }}
                                    </div>
                                    <div class="stat-label">{{ t.author }}</div>
                                </div>
                            </div>

                            <div v-if="data.dateCreated" class="stat-card">
                                <div class="stat-icon">
                                    <svg
                                        width="20"
                                        height="20"
                                        viewBox="0 0 24 24"
                                    >
                                        <path
                                            fill="currentColor"
                                            d="M19,3H18V1H16V3H8V1H6V3H5A2,2 0 0,0 3,5V19A2,2 0 0,0 5,21H19A2,2 0 0,0 21,19V5A2,2 0 0,0 19,3M19,19H5V8H19V19Z"
                                        />
                                    </svg>
                                </div>
                                <div class="stat-content">
                                    <div class="stat-value">
                                        {{ formatDate(data.dateCreated) }}
                                    </div>
                                    <div class="stat-label">
                                        {{ t.created }}
                                    </div>
                                </div>
                            </div>

                            <div v-if="data.dateModified" class="stat-card">
                                <div class="stat-icon">
                                    <svg
                                        width="20"
                                        height="20"
                                        viewBox="0 0 24 24"
                                    >
                                        <path
                                            fill="currentColor"
                                            d="M21,10.12H14.22L16.96,7.3C14.23,4.6 9.81,4.5 7.34,7.29C4.77,10.2 4.9,14.7 7.65,17.1C10.1,19.3 13.97,19.1 16.17,16.5C17.33,15.25 17.76,13.62 17.43,12.05H21C21.24,14.66 20.12,17.37 17.85,19.1C12.75,23 5.27,21.5 2.25,16.05C-0.68,10.8 0.7,4.1 5.6,1.05C10.73,-2.18 17.81,-0.21 20.83,5.32C21.94,7.5 22.18,9.91 21.1,12.1H21Z"
                                        />
                                    </svg>
                                </div>
                                <div class="stat-content">
                                    <div class="stat-value">
                                        {{ formatDate(data.dateModified) }}
                                    </div>
                                    <div class="stat-label">
                                        {{ t.updated }}
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Download Chart -->
                        <div v-if="chartData.length > 0" class="chart-section">
                            <div class="section-header">
                                <h4 class="section-title">
                                    {{ t.downloadTrend }}
                                </h4>
                            </div>
                            <div class="chart-container">
                                <v-chart
                                    class="chart"
                                    :option="chartOption"
                                    autoresize
                                />
                            </div>
                        </div>

                        <!-- Description -->
                        <div v-if="data.description || data.coverUrl" class="content-section">
                            <div class="section-header">
                                <h4 class="section-title">
                                    {{ t.description }}
                                </h4>
                            </div>
                            <div class="content-card description-card">
                                <div v-if="data.coverUrl" class="description-cover">
                                    <img 
                                        :src="data.coverUrl" 
                                        :alt="data.name || 'Cover'"
                                        class="cover-image"
                                        @error="handleCoverImageError"
                                    />
                                </div>
                                <div v-if="data.description" class="description-content">
                                    <p class="description-text">
                                        {{ data.description }}
                                    </p>
                                </div>
                            </div>
                        </div>

                        <!-- Tags and Versions -->
                        <div class="tags-grid">
                            <div
                                v-if="data.gameVersions.length > 0"
                                class="tag-section"
                            >
                                <h5 class="tag-section-title">
                                    {{ t.supportedVersions }}
                                </h5>
                                <div class="tag-list">
                                    <span
                                        v-for="version in data.gameVersions"
                                        :key="version"
                                        class="tag version-tag"
                                    >
                                        {{ version }}
                                    </span>
                                </div>
                            </div>

                            <div
                                v-if="data.cores.length > 0"
                                class="tag-section"
                            >
                                <h5 class="tag-section-title">
                                    {{ t.modLoaders }}
                                </h5>
                                <div class="tag-list">
                                    <span
                                        v-for="core in data.cores"
                                        :key="core"
                                        class="tag core-tag"
                                    >
                                        {{ core }}
                                    </span>
                                </div>
                            </div>

                            <div
                                v-if="data.tags.length > 0"
                                class="tag-section"
                            >
                                <h5 class="tag-section-title">{{ t.tags }}</h5>
                                <div class="tag-list">
                                    <span
                                        v-for="tag in data.tags"
                                        :key="tag"
                                        class="tag feature-tag"
                                    >
                                        {{ tag }}
                                    </span>
                                </div>
                            </div>
                        </div>

                        <!-- Detailed Documentation -->
                        <div v-if="data.text" class="content-section">
                            <div
                                class="section-header expandable"
                                @click="toggleTextExpanded"
                            >
                                <h4 class="section-title">
                                    {{ t.detailedDocs }}
                                </h4>
                                <div
                                    class="toggle-button"
                                    :class="{ expanded: isTextExpanded }"
                                >
                                    <svg
                                        width="16"
                                        height="16"
                                        viewBox="0 0 24 24"
                                    >
                                        <path
                                            fill="currentColor"
                                            d="M7.41 8.59L12 13.17l4.59-4.58L18 10l-6 6-6-6 1.41-1.41z"
                                        />
                                    </svg>
                                </div>
                            </div>
                            <Transition name="text-slide">
                                <div
                                    v-show="isTextExpanded"
                                    class="content-card documentation"
                                    v-html="processedText"
                                ></div>
                            </Transition>
                        </div>

                        <!-- Visit Link -->
                        <div class="action-section">
                            <a
                                :href="xyebbsUrl"
                                target="_blank"
                                rel="noopener noreferrer"
                                class="visit-link"
                            >
                                <div class="link-content">
                                    <span>{{ t.visitLink }}</span>
                                    <svg
                                        width="16"
                                        height="16"
                                        viewBox="0 0 24 24"
                                    >
                                        <path
                                            fill="currentColor"
                                            d="M14,3V5H17.59L7.76,14.83L9.17,16.24L19,6.41V10H21V3M19,19H5V5H12V3H5C3.89,3 3,3.9 3,5V19A2,2 0 0,0 5,21H19A2,2 0 0,0 21,19V12H19V19Z"
                                        />
                                    </svg>
                                </div>
                            </a>
                        </div>
                    </div>

                    <!-- No Data State -->
                    <div v-else class="xyebbs-state-card no-data">
                        <div class="state-icon">
                            <svg width="24" height="24" viewBox="0 0 24 24">
                                <path
                                    fill="currentColor"
                                    d="M12,2A10,10 0 0,0 2,12A10,10 0 0,0 12,22A10,10 0 0,0 22,12A10,10 0 0,0 12,2M12,17A1.5,1.5 0 0,1 10.5,15.5A1.5,1.5 0 0,1 12,14A1.5,1.5 0 0,1 13.5,15.5A1.5,1.5 0 0,1 12,17M12,10.5C10.07,10.5 8.5,8.93 8.5,7A3.5,3.5 0 0,1 12,3.5A3.5,3.5 0 0,1 15.5,7C15.5,8.93 13.93,10.5 12,10.5Z"
                                />
                            </svg>
                        </div>
                        <div class="state-text">{{ t.noData }}</div>
                    </div>
                </div>
            </Transition>
        </div>
    </div>
</template>

<script setup>
    import { ref, computed } from "vue";
    import { use } from "echarts/core";
    import { CanvasRenderer } from "echarts/renderers";
    import { LineChart } from "echarts/charts";
    import {
        TitleComponent,
        TooltipComponent,
        LegendComponent,
        GridComponent,
    } from "echarts/components";
    import VChart from "vue-echarts";
    import MarkdownIt from "markdown-it";
    import hljs from "highlight.js";
    import "highlight.js/styles/github.css";
    import { useSafeI18n } from "../../../utils/i18n/locale";

    // Register ECharts components
    use([
        CanvasRenderer,
        LineChart,
        TitleComponent,
        TooltipComponent,
        LegendComponent,
        GridComponent,
    ]);

    /**
     * Component props definition
     */
    const props = defineProps({
        xyebbsId: {
            type: String,
            required: true,
        },
    });

    /**
     * Internationalization
     */
    const { t } = useSafeI18n("XyebbsInfo", {
        title: "XyeBBS",
        downloads: "Downloads",
        loading: "Loading XyeBBS information...",
        errorTitle: "Failed to load data",
        noData: "No XyeBBS data available",
        downloadCount: "Download Count",
        author: "Author",
        created: "Created",
        updated: "Updated",
        description: "Module Description",
        detailedDocs: "Detailed Documentation",
        supportedVersions: "Supported Versions",
        modLoaders: "Mod Loaders",
        tags: "Tags",
        visitLink: "Visit XyeBBS Page",
        downloadTrend: "Download Trend",
        statistics: "Statistics",
        copyCode: "Copy",
        codeCopied: "Copied!",
    });

    /**
     * Component state
     */
    const isExpanded = ref(false);
    const isTextExpanded = ref(false);
    const isLoading = ref(false);
    const errorMsg = ref("");
    const data = ref({
        name: null,
        downloadCount: 0,
        dateCreated: null,
        dateModified: null,
        gameVersions: [],
        cores: [],
        tags: [],
        author: null,
        logoUrl: null,
        coverUrl: null,
        description: null,
        text: null,
    });

    /**
     * API configuration
     */
    const XYEBBS_API_URL =
        "https://resource-api.xyeidc.com/client/resources/identify";

    /**
     * Computed properties
     */
    const xyebbsUrl = computed(() => {
        return `https://bbs.xyeidc.com/res-id/${props.xyebbsId}?tab=info`;
    });

    /**
     * Chart data - mock data for demonstration
     */
    const chartData = computed(() => {
        if (!data.value || data.value.downloadCount === 0) return [];

        // Generate mock trend data based on download count
        const count = data.value.downloadCount || 0;
        const days = 30;
        const mockData = [];

        for (let i = days; i >= 0; i--) {
            const date = new Date();
            date.setDate(date.getDate() - i);
            const variance = Math.random() * 0.3 + 0.85; // 85-115% variance
            const dailyDownloads = Math.max(
                1,
                Math.floor((count / days) * variance)
            );
            mockData.push({
                date: date.toISOString().split("T")[0],
                downloads: dailyDownloads,
            });
        }

        return mockData;
    });

    /**
     * Chart configuration
     */
    const chartOption = computed(() => {
        // Use CSS variables for theme compatibility
        const isDark = typeof window !== 'undefined' && 
                      document.documentElement.classList.contains('dark');
        
        return {
            grid: {
                top: "10%",
                left: "3%",
                right: "4%",
                bottom: "10%",
                containLabel: true,
            },
            xAxis: {
                type: "category",
                data: chartData.value?.map((item) => item.date) || [],
                boundaryGap: false,
                axisLine: {
                    lineStyle: {
                        color: isDark ? "#374151" : "#e5e7eb",
                    },
                },
                axisLabel: {
                    color: isDark ? "#9ca3af" : "#6b7280",
                    fontSize: 11,
                },
            },
            yAxis: {
                type: "value",
                axisLine: {
                    lineStyle: {
                        color: isDark ? "#374151" : "#e5e7eb",
                    },
                },
                axisLabel: {
                    color: isDark ? "#9ca3af" : "#6b7280",
                    fontSize: 11,
                },
                splitLine: {
                    lineStyle: {
                        color: isDark ? "#374151" : "#e5e7eb",
                        type: "dashed",
                    },
                },
            },
            series: [
                {
                    name: t.downloads,
                    type: "line",
                    data: chartData.value?.map((item) => item.downloads) || [],
                    smooth: true,
                    lineStyle: {
                        color: "#3b82f6",
                        width: 2,
                    },
                    itemStyle: {
                        color: "#3b82f6",
                    },
                    areaStyle: {
                        color: {
                            type: "linear",
                            x: 0,
                            y: 0,
                            x2: 0,
                            y2: 1,
                            colorStops: [
                                {
                                    offset: 0,
                                    color: "rgba(59, 130, 246, 0.3)",
                                },
                                {
                                    offset: 1,
                                    color: "transparent",
                                },
                            ],
                        },
                    },
                    symbol: "circle",
                    symbolSize: 4,
                },
            ],
            tooltip: {
                trigger: "axis",
                backgroundColor: isDark ? "#1f2937" : "#ffffff",
                borderColor: isDark ? "#374151" : "#e5e7eb",
                textStyle: {
                    color: isDark ? "#f9fafb" : "#1f2937",
                },
            },
        };
    });

    /**
     * Process markdown text
     */
    const processedText = computed(() => {
        if (!data.value.text) return null;

        try {
            const md = new MarkdownIt({
                html: true,
                breaks: true,
                linkify: true,
                highlight: function (str, lang) {
                    if (lang && hljs.getLanguage(lang)) {
                        try {
                            const highlighted = hljs.highlight(str, {
                                language: lang,
                            }).value;
                            return `<pre class="hljs-code-block"><div class="hljs-header"><span class="hljs-lang">${lang.toUpperCase()}</span><button class="hljs-copy" onclick="copyCode(this)">${
                                t.copyCode
                            }</button></div><code class="hljs language-${lang}">${highlighted}</code></pre>`;
                        } catch (__) {}
                    }

                    try {
                        const result = hljs.highlightAuto(str);
                        const detectedLang = result.language || "text";
                        return `<pre class="hljs-code-block"><div class="hljs-header"><span class="hljs-lang">${detectedLang.toUpperCase()}</span><button class="hljs-copy" onclick="copyCode(this)">${
                            t.copyCode
                        }</button></div><code class="hljs language-${detectedLang}">${
                            result.value
                        }</code></pre>`;
                    } catch (__) {
                        return `<pre class="hljs-code-block"><div class="hljs-header"><span class="hljs-lang">TEXT</span><button class="hljs-copy" onclick="copyCode(this)">${
                            t.copyCode
                        }</button></div><code class="hljs">${md.utils.escapeHtml(
                            str
                        )}</code></pre>`;
                    }
                },
            });

            let processedMarkdown = data.value.text.replace(
                /!\[([^\]]*)\]\(https:\/\/resource-api\.xyeidc\.com\/client\/pics\/([^)]+)\)/g,
                "![图片](https://resource-api.xyeidc.com/client/pics/$2)"
            );

            return md.render(processedMarkdown);
        } catch (error) {
            console.error("Markdown processing error:", error);
            return `<pre>${data.value.text}</pre>`;
        }
    });

    /**
     * Utility methods
     */
    const formatNumber = (num) => {
        if (num >= 1000000) {
            return (num / 1000000).toFixed(1) + "M";
        } else if (num >= 1000) {
            return (num / 1000).toFixed(1) + "K";
        }
        return num.toString();
    };

    const formatDate = (dateString) => {
        if (!dateString) return "";
        const date = new Date(dateString);
        return date.toLocaleDateString();
    };

    /**
     * Event handlers
     */
    const toggleExpanded = async () => {
        isExpanded.value = !isExpanded.value;

        if (
            isExpanded.value &&
            data.value.downloadCount === 0 &&
            !isLoading.value
        ) {
            await fetchData();
        }
    };

    const toggleTextExpanded = () => {
        isTextExpanded.value = !isTextExpanded.value;
    };

    const handleImageError = (event) => {
        event.target.src = 'https://pic.xyeidc.com/i/6a59d895-a4fe-4b8d-8450-cdbd814c7ede.png';
    };

    const handleCoverImageError = (event) => {
        const parent = event.target.parentElement;
        if (parent) {
            parent.style.display = 'none';
        }
    };

    /**
     * API data fetching
     */
    const fetchData = async () => {
        isLoading.value = true;
        errorMsg.value = "";

        try {
            const response = await fetch(
                `${XYEBBS_API_URL}/${props.xyebbsId}?includes=%2A`
            );

            if (!response.ok) {
                throw new Error(`API request failed: ${response.status}`);
            }

            const result = await response.json();

            if (result.code === 200 && result.data) {
                const apiData = result.data;

                data.value = {
                    name: apiData.name || apiData.title || null,
                    downloadCount: apiData.downloadCount || 0,
                    dateCreated: apiData.createDate || null,
                    dateModified: apiData.updateDate || null,
                    gameVersions: apiData.versions
                        ? apiData.versions.map((v) => v.name)
                        : [],
                    cores: apiData.cores
                        ? apiData.cores.map((c) => c.name)
                        : [],
                    tags: apiData.tags ? apiData.tags.map((t) => t.name) : [],
                    author: apiData.member ? apiData.member.username : null,
                    logoUrl: apiData.logoImgUuid
                        ? `https://resource-api.xyeidc.com/client/pics/${apiData.logoImgUuid}`
                        : null,
                    coverUrl: apiData.coverImgUuid
                        ? `https://resource-api.xyeidc.com/client/pics/${apiData.coverImgUuid}`
                        : (apiData.imgs && apiData.imgs.length > 0 
                            ? `https://resource-api.xyeidc.com/client/pics/${apiData.imgs[0]}`
                            : null),
                    description: apiData.description || null,
                    text: apiData.text || null,
                };
            } else {
                throw new Error("Invalid data format");
            }
        } catch (error) {
            errorMsg.value = `${t.errorTitle}: ${error.message}`;
        } finally {
            isLoading.value = false;
        }
    };

    /**
     * Global code copy function
     */
    if (typeof window !== "undefined") {
        window.copyCode = function (button) {
            const codeBlock = button.parentElement.nextElementSibling;
            const code = codeBlock.textContent || codeBlock.innerText;

            navigator.clipboard
                .writeText(code)
                .then(() => {
                    const originalText = button.textContent;
                    button.textContent = t.codeCopied;
                    button.style.background = "var(--vp-c-green)";

                    setTimeout(() => {
                        button.textContent = originalText;
                        button.style.background = "";
                    }, 2000);
                })
                .catch(() => {
                    const textArea = document.createElement("textarea");
                    textArea.value = code;
                    document.body.appendChild(textArea);
                    textArea.select();
                    document.execCommand("copy");
                    document.body.removeChild(textArea);

                    const originalText = button.textContent;
                    button.textContent = t.codeCopied;
                    setTimeout(() => {
                        button.textContent = originalText;
                    }, 2000);
                });
        };
    }
</script>

<style scoped>
    /* Container */
    .xyebbs-container {
        margin: 24px 0;
    }

    .xyebbs-card {
        background: var(--vp-c-bg-alt);
        border: 2px solid var(--vp-c-divider);
        border-radius: 16px;
        overflow: hidden;
        transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
    }

    .xyebbs-card.expanded {
        border-color: var(--vp-c-brand);
    }

    /* Header */
    .xyebbs-header {
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 16px 20px;
        cursor: pointer;
        user-select: none;
        background: var(--vp-c-bg-soft);
        border-bottom: 1px solid var(--vp-c-divider-light);
    }

    .xyebbs-badge {
        display: flex;
        align-items: center;
        gap: 16px;
        flex: 1;
    }

    .xyebbs-icon-wrapper {
        width: 40px;
        height: 40px;
        border-radius: 12px;
        background: var(--vp-c-bg);
        display: flex;
        align-items: center;
        justify-content: center;
        border: 2px solid var(--vp-c-divider);
    }

    .xyebbs-icon {
        height: 24px;
        width: auto;
        object-fit: contain;
    }

    .xyebbs-title-section {
        display: flex;
        flex-direction: column;
        gap: 4px;
        flex: 1;
    }

    .xyebbs-title {
        font-weight: 600;
        font-size: 1.1rem;
        color: var(--vp-c-text-1);
    }

    .xyebbs-download-badge {
        background: var(--vp-c-brand-soft);
        color: var(--vp-c-brand);
        padding: 6px 14px;
        border-radius: 24px;
        font-size: 0.85rem;
        font-weight: 600;
        display: inline-block;
        width: fit-content;
        border: 2px solid var(--vp-c-brand-light);
    }

    .xyebbs-toggle {
        width: 36px;
        height: 36px;
        border-radius: 12px;
        background: var(--vp-c-bg);
        border: 2px solid var(--vp-c-divider);
        display: flex;
        align-items: center;
        justify-content: center;
        transition: all 0.25s cubic-bezier(0.4, 0, 0.2, 1);
        color: var(--vp-c-text-2);
    }

    .xyebbs-toggle.expanded {
        transform: rotate(180deg);
        background: var(--vp-c-brand-soft);
        border-color: var(--vp-c-brand);
        color: var(--vp-c-brand);
    }

    /* Content */
    .xyebbs-content {
        padding: 24px;
        transform-origin: top;
    }

    /* State Cards */
    .xyebbs-state-card {
        display: flex;
        align-items: center;
        justify-content: center;
        gap: 16px;
        padding: 48px 24px;
        text-align: center;
        border-radius: 8px;
    }

    .xyebbs-state-card.loading {
        background: var(--vp-c-bg-soft);
        color: var(--vp-c-text-2);
    }

    .xyebbs-state-card.error {
        background: var(--vp-c-danger-soft);
        color: var(--vp-c-danger-dark);
    }

    .xyebbs-state-card.no-data {
        background: var(--vp-c-bg-soft);
        color: var(--vp-c-text-3);
    }

    .state-icon svg {
        color: currentColor;
    }

    .state-text {
        display: flex;
        flex-direction: column;
        gap: 4px;
    }

    .state-title {
        font-weight: 600;
        font-size: 1rem;
    }

    .state-message {
        font-size: 0.9rem;
        opacity: 0.8;
    }

    .loading-spinner {
        width: 24px;
        height: 24px;
        border: 3px solid var(--vp-c-divider);
        border-top: 3px solid var(--vp-c-brand);
        border-radius: 50%;
        animation: spin 1s linear infinite;
    }

    @keyframes spin {
        0% {
            transform: rotate(0deg);
        }
        100% {
            transform: rotate(360deg);
        }
    }

    /* Statistics Grid */
    .stats-grid {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
        gap: 16px;
        margin-bottom: 32px;
    }

    .stat-card {
        background: var(--vp-c-bg-soft);
        border: 2px solid var(--vp-c-divider);
        border-radius: 16px;
        padding: 20px;
        display: flex;
        align-items: center;
        gap: 16px;
        transition: all 0.3s ease;
    }

    .stat-card.primary {
        background: var(--vp-c-brand-soft);
        border-color: var(--vp-c-brand);
    }

    .stat-icon {
        width: 44px;
        height: 44px;
        border-radius: 14px;
        background: var(--vp-c-bg);
        display: flex;
        align-items: center;
        justify-content: center;
        color: var(--vp-c-brand);
        border: 2px solid var(--vp-c-divider);
    }

    .stat-card.primary .stat-icon {
        background: var(--vp-c-brand);
        color: white;
        border-color: var(--vp-c-brand);
    }

    .stat-content {
        flex: 1;
    }

    .stat-value {
        font-size: 1.2rem;
        font-weight: 700;
        color: var(--vp-c-text-1);
        line-height: 1.2;
        word-break: break-all;
    }

    .stat-label {
        font-size: 0.85rem;
        color: var(--vp-c-text-2);
        margin-top: 2px;
    }

    /* Sections */
    .chart-section,
    .content-section {
        margin: 32px 0;
    }

    .section-header {
        display: flex;
        align-items: center;
        justify-content: space-between;
        margin-bottom: 16px;
    }

    .section-header.expandable {
        cursor: pointer;
        user-select: none;
        padding: 4px 0;
    }

    .section-title {
        font-size: 1.1rem;
        font-weight: 600;
        color: var(--vp-c-text-1);
        margin: 0;
        display: flex;
        align-items: center;
        gap: 8px;
    }

    .section-title::before {
        content: "";
        width: 3px;
        height: 16px;
        background: var(--vp-c-brand);
        border-radius: 2px;
    }

    .toggle-button {
        width: 24px;
        height: 24px;
        border-radius: 6px;
        background: var(--vp-c-bg-soft);
        border: 1px solid var(--vp-c-divider);
        display: flex;
        align-items: center;
        justify-content: center;
        transition: all 0.2s cubic-bezier(0.4, 0, 0.2, 1);
        color: var(--vp-c-text-3);
    }

    .toggle-button.expanded {
        transform: rotate(180deg);
        background: var(--vp-c-brand-soft);
        border-color: var(--vp-c-brand-light);
        color: var(--vp-c-brand);
    }

    /* Chart */
    .chart-container {
        background: var(--vp-c-bg-soft);
        border: 2px solid var(--vp-c-divider);
        border-radius: 16px;
        padding: 24px;
        height: 320px;
    }

    .chart {
        width: 100%;
        height: 100%;
    }

    /* Content Cards */
    .content-card {
        background: var(--vp-c-bg-soft);
        border: 2px solid var(--vp-c-divider);
        border-radius: 16px;
        padding: 24px;
    }

    .content-card.documentation {
        max-height: 400px;
        overflow-y: auto;
    }

    .description-card {
        display: flex;
        gap: 24px;
        align-items: flex-start;
    }

    .description-cover {
        flex-shrink: 0;
        width: 200px;
        height: 120px;
        border-radius: 12px;
        overflow: hidden;
        border: 2px solid var(--vp-c-divider);
        background: var(--vp-c-bg);
    }

    .cover-image {
        width: 100%;
        height: 100%;
        object-fit: cover;
        transition: transform 0.3s ease;
    }

    .description-content {
        flex: 1;
    }

    .description-text {
        color: var(--vp-c-text-2);
        line-height: 1.6;
        margin: 0;
        font-size: 0.95rem;
    }

    /* Tags */
    .tags-grid {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
        gap: 24px;
        margin: 32px 0;
    }

    .tag-section {
        background: var(--vp-c-bg-soft);
        border: 2px solid var(--vp-c-divider);
        border-radius: 16px;
        padding: 24px;
    }

    .tag-section-title {
        font-size: 0.95rem;
        font-weight: 600;
        color: var(--vp-c-text-1);
        margin: 0 0 12px 0;
    }

    .tag-list {
        display: flex;
        flex-wrap: wrap;
        gap: 8px;
    }

    .tag {
        padding: 8px 16px;
        border-radius: 24px;
        font-size: 0.85rem;
        font-weight: 600;
        transition: all 0.3s ease;
    }

    .version-tag {
        background: var(--vp-c-brand-soft);
        color: var(--vp-c-brand);
        border: 2px solid var(--vp-c-brand-light);
    }

    .core-tag {
        background: var(--vp-c-green-soft);
        color: var(--vp-c-green);
        border: 2px solid var(--vp-c-green-light);
    }

    .feature-tag {
        background: var(--vp-c-bg);
        color: var(--vp-c-text-2);
        border: 2px solid var(--vp-c-divider);
    }

    /* Action Section */
    .action-section {
        margin-top: 32px;
        padding-top: 24px;
        border-top: 1px solid var(--vp-c-divider);
    }

    .visit-link {
        display: inline-flex;
        align-items: center;
        padding: 14px 24px;
        background: var(--vp-c-brand);
        color: white;
        text-decoration: none;
        border-radius: 12px;
        font-size: 0.95rem;
        font-weight: 600;
        transition: all 0.3s ease;
        border: 2px solid var(--vp-c-brand);
    }

    .link-content {
        display: flex;
        align-items: center;
        gap: 8px;
    }

    .visit-link .vp-external-link-icon {
        display: none;
    }

    /* Documentation Content Styles */
    .content-card :deep(h1),
    .content-card :deep(h2),
    .content-card :deep(h3),
    .content-card :deep(h4),
    .content-card :deep(h5),
    .content-card :deep(h6) {
        color: var(--vp-c-text-1);
        margin: 1.5em 0 0.5em 0;
    }

    .content-card :deep(p) {
        margin: 0.8em 0;
        line-height: 1.6;
    }

    .content-card :deep(code:not(.hljs)) {
        background: var(--vp-c-bg-mute);
        padding: 2px 6px;
        border-radius: 4px;
        font-size: 0.9em;
        color: var(--vp-c-text-code);
        font-family: var(--vp-font-family-mono);
    }

    .content-card :deep(.hljs-code-block) {
        margin: 1em 0;
        border-radius: 8px;
        overflow: hidden;
        border: 1px solid var(--vp-c-divider);
        background: var(--vp-c-bg);
    }

    .content-card :deep(.hljs-header) {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 8px 16px;
        background: var(--vp-c-bg-mute);
        border-bottom: 1px solid var(--vp-c-divider);
        font-size: 0.8rem;
    }

    .content-card :deep(.hljs-lang) {
        font-weight: 600;
        color: var(--vp-c-brand);
        font-family: var(--vp-font-family-mono);
    }

    .content-card :deep(.hljs-copy) {
        background: var(--vp-c-brand-soft);
        color: var(--vp-c-brand);
        border: none;
        padding: 4px 8px;
        border-radius: 4px;
        font-size: 0.75rem;
        cursor: pointer;
        transition: all 0.2s ease;
        font-family: var(--vp-font-family-base);
    }

    .content-card :deep(.hljs-code-block pre) {
        margin: 0;
        padding: 16px;
        background: var(--vp-c-bg);
        overflow-x: auto;
        font-family: var(--vp-font-family-mono);
        font-size: 0.9em;
        line-height: 1.5;
    }

    .content-card :deep(.hljs) {
        background: var(--vp-c-bg) !important;
        color: var(--vp-c-text-1) !important;
    }

    .content-card :deep(ul),
    .content-card :deep(ol) {
        padding-left: 1.5em;
        margin: 0.8em 0;
    }

    .content-card :deep(blockquote) {
        border-left: 3px solid var(--vp-c-brand);
        padding: 12px 16px;
        margin: 1em 0;
        background: var(--vp-c-brand-soft);
        border-radius: 4px;
    }

    .content-card :deep(a) {
        color: var(--vp-c-brand);
        text-decoration: none;
        border-bottom: 1px solid transparent;
        transition: border-color 0.3s ease;
    }

    .content-card :deep(img) {
        max-width: 100%;
        height: auto;
        border-radius: 12px;
        margin: 1em 0;
        border: 2px solid var(--vp-c-divider);
    }

    /* Animations */
    .xyebbs-slide-enter-active {
        transition: all 0.3s cubic-bezier(0.25, 0.46, 0.45, 0.94);
        overflow: hidden;
    }

    .xyebbs-slide-leave-active {
        transition: all 0.25s cubic-bezier(0.55, 0.055, 0.675, 0.19);
        overflow: hidden;
    }

    .xyebbs-slide-enter-from {
        opacity: 0;
        transform: translateY(-8px) scale(0.98);
    }

    .xyebbs-slide-leave-to {
        opacity: 0;
        transform: translateY(-5px) scale(0.99);
    }

    .text-slide-enter-active {
        transition: all 0.3s cubic-bezier(0.25, 0.46, 0.45, 0.94);
        overflow: hidden;
    }

    .text-slide-leave-active {
        transition: all 0.2s cubic-bezier(0.55, 0.055, 0.675, 0.19);
        overflow: hidden;
    }

    .text-slide-enter-from {
        opacity: 0;
        transform: translateY(-3px) scale(0.99);
    }

    .text-slide-leave-to {
        opacity: 0;
        transform: translateY(-2px) scale(0.995);
    }

    /* Responsive Design */
    @media (max-width: 768px) {
        .xyebbs-container {
            margin: 16px 0;
        }

        .xyebbs-header {
            padding: 12px 16px;
        }

        .xyebbs-content {
            padding: 20px 16px;
        }

        .stats-grid {
            grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
            gap: 12px;
        }

        .stat-card {
            padding: 16px;
        }

        .stat-icon {
            width: 32px;
            height: 32px;
        }

        .stat-value {
            font-size: 1.1rem;
        }

        .tags-grid {
            grid-template-columns: 1fr;
            gap: 16px;
        }

        .tag-section {
            padding: 16px;
        }

        .chart-container {
            height: 250px;
            padding: 16px;
        }

        .content-card {
            padding: 16px;
        }

        .content-card :deep(.hljs-header) {
            padding: 6px 12px;
            font-size: 0.75rem;
        }

        .content-card :deep(.hljs-copy) {
            padding: 3px 6px;
            font-size: 0.7rem;
        }

        .content-card :deep(.hljs-code-block pre) {
            padding: 12px;
            font-size: 0.85em;
        }

        .description-card {
            flex-direction: column;
            gap: 16px;
        }

        .description-cover {
            width: 100%;
            height: 200px;
            align-self: center;
        }
    }
</style>
