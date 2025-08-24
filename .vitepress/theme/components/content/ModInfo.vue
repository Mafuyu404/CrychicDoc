<script setup>
    import { ref, computed, onMounted } from "vue";
    import { useSafeI18n } from "../../../utils/i18n/locale";

    /**
     * Internationalization
     */
    const { t } = useSafeI18n("ModInfo", {
        downloads: "downloads",
        loading: "Loading...",
        checkCurseForge: "View CurseForge page",
        supportedVersions: "Supported Minecraft Versions",
        modAuthors: "Mod Authors",
        dateCreated: "Created:",
        lastUpdated: "Last Updated:",
        unknown: "Unknown",
        usingCachedData: "Using cached data (API request failed)",
        failedToFetch: "Failed to fetch mod data:",
        failedToFetchModrinth: "Failed to fetch Modrinth mod data:",
    });

    const props = defineProps({
        curseForgeId: {
            type: String,
            default: "",
        },
        modName: {
            type: String,
            required: true,
        },
        iconUrl: {
            type: String,
            default: "",
        },
        projectId: {
            type: String,
            default: "",
        },
        // 添加Modrinth相关属性
        modrinthId: {
            type: String,
            default: "",
        },
        modrinthSlug: {
            type: String,
            default: "",
        },

        // 直接传入数据的props，用于测试
        testDownloads: {
            type: Number,
            default: 0,
        },
        testModrinthDownloads: {
            type: Number,
            default: 0,
        },
        testVersions: {
            type: Array,
            default: () => [],
        },
        testAuthors: {
            type: Array,
            default: () => [],
        },
        testDateCreated: {
            type: String,
            default: "",
        },
        testDateModified: {
            type: String,
            default: "",
        },
    });

    // 响应式状态
    const modData = ref({
        downloadCount: 0,
        dateCreated: null,
        dateModified: null,
        gameVersions: [],
        authors: [],
        logoUrl: null,
    });

    // Modrinth数据
    const modrinthData = ref({
        downloadCount: 0,
        dateCreated: null,
        dateModified: null,
        gameVersions: [],
        team: [],
        iconUrl: null,
    });

    const icon = ref(props.iconUrl);
    const isLoading = ref(false);
    const errorMsg = ref("");
    const modrinthLoading = ref(false);
    const modrinthErrorMsg = ref("");

    // API配置
    const CF_API_KEY = import.meta.env.VITE_CF_API_KEY || "";
    const CF_API_URL = "https://api.curseforge.com/v1/mods";
    const MODRINTH_API_URL = "https://api.modrinth.com/v2";

    // 格式化工具函数
    const formatNumber = (num) => {
        if (num < 1000) return String(num);
        if (num < 1000000) return (num / 1000).toFixed(1) + "K";
        return (num / 1000000).toFixed(1) + "M";
    };

    const formatDate = (dateString) => {
        if (!dateString) return t.unknown;
        const date = new Date(dateString);
        return date.toLocaleDateString(undefined, {
            year: "numeric",
            month: "long",
            day: "numeric",
        });
    };

    // 计算属性
    const formattedDownloads = computed(() =>
        formatNumber(modData.value.downloadCount)
    );
    const formattedDateCreated = computed(() =>
        formatDate(modData.value.dateCreated)
    );
    const formattedDateModified = computed(() =>
        formatDate(modData.value.dateModified)
    );
    const curseForgeUrl = computed(
        () =>
            `https://www.curseforge.com/minecraft/mc-mods/${props.curseForgeId}`
    );

    // Modrinth计算属性
    const formattedModrinthDownloads = computed(() =>
        formatNumber(modrinthData.value.downloadCount)
    );
    const modrinthUrl = computed(() => {
        if (props.modrinthSlug) {
            return `https://modrinth.com/mod/${props.modrinthSlug}`;
        } else if (props.modrinthId) {
            return `https://modrinth.com/mod/${props.modrinthId}`;
        }
        return null;
    });

    // 版本排序
    const compareVersions = (a, b) => {
        const aParts = a.split(".").map(Number);
        const bParts = b.split(".").map(Number);

        for (let i = 0; i < Math.max(aParts.length, bParts.length); i++) {
            const aVal = i < aParts.length ? aParts[i] : 0;
            const bVal = i < bParts.length ? bParts[i] : 0;

            if (aVal !== bVal) return bVal - aVal;
        }

        return 0;
    };

    const sortedVersions = computed(() => {
        if (!modData.value.gameVersions.length) return [];
        return [...modData.value.gameVersions].sort(compareVersions);
    });

    const fetchModData = async () => {
        if (!props.projectId) return;

        // Check if API key is available
        if (!CF_API_KEY) {
            errorMsg.value = "CurseForge API key is not configured";
            console.warn("CF_API_KEY environment variable is not set");
            return;
        }

        isLoading.value = true;
        errorMsg.value = "";

        try {
            const response = await fetch(`${CF_API_URL}/${props.projectId}`, {
                method: "GET",
                headers: {
                    "x-api-key": CF_API_KEY,
                    Accept: "application/json",
                    "Content-Type": "application/json",
                    "User-Agent": "ModInfo-Component/1.0",
                },
                mode: "cors",
            });

            if (!response.ok) {
                if (response.status === 403) {
                    throw new Error(
                        "CurseForge API访问受限，可能是CORS限制或API密钥问题"
                    );
                } else if (response.status === 429) {
                    throw new Error("请求频率过高，请稍后再试");
                } else {
                    throw new Error(
                        `CurseForge API请求失败: ${response.status} ${response.statusText}`
                    );
                }
            }

            const { data } = await response.json();

            if (data) {
                let gameVersions = [];
                if (data.latestFilesIndexes?.length > 0) {
                    const versions = data.latestFilesIndexes
                        .map((file) => file.gameVersion)
                        .filter(Boolean);
                    gameVersions = [...new Set(versions)];
                } else if (data.gameVersions) {
                    gameVersions = data.gameVersions;
                }

                modData.value = {
                    downloadCount: data.downloadCount || 0,
                    dateCreated: data.dateCreated || null,
                    dateModified: data.dateModified || null,
                    gameVersions,
                    authors: data.authors || [],
                    logoUrl: data.logo?.thumbnailUrl || null,
                };

                // 保存数据到本地存储以便离线使用
                try {
                    localStorage.setItem(
                        `mod_data_${props.projectId}`,
                        JSON.stringify(modData.value)
                    );
                } catch (storageErr) {
                    console.warn("无法保存模组数据到本地存储:", storageErr);
                }

                if (!props.iconUrl && modData.value.logoUrl) {
                    icon.value = modData.value.logoUrl;
                }
            }
        } catch (error) {
            // 尝试从本地存储加载缓存数据
            try {
                const cachedData = localStorage.getItem(
                    `mod_data_${props.projectId}`
                );
                if (cachedData) {
                    modData.value = JSON.parse(cachedData);
                    if (!props.iconUrl && modData.value.logoUrl) {
                        icon.value = modData.value.logoUrl;
                    }
                    errorMsg.value = t.usingCachedData;
                    console.warn("使用缓存的模组数据");
                    return;
                }
            } catch (cacheError) {
                console.error("读取缓存数据失败:", cacheError);
            }

            const errorMessage =
                error instanceof Error ? error.message : "未知错误";
            errorMsg.value = `${t.failedToFetch} ${errorMessage}`;
        } finally {
            isLoading.value = false;
        }
    };

    // 获取Modrinth数据
    const fetchModrinthData = async () => {
        // 如果没有提供id或slug，则不进行请求
        if (!props.modrinthId && !props.modrinthSlug) return;

        modrinthLoading.value = true;
        modrinthErrorMsg.value = "";

        try {
            // 确定要使用的标识符（id或slug）
            const identifier = props.modrinthId || props.modrinthSlug;
            const response = await fetch(
                `${MODRINTH_API_URL}/project/${identifier}`,
                {
                    method: "GET",
                    headers: {
                        "User-Agent": "Documentation-Website/1.0",
                        Accept: "application/json",
                    },
                }
            );

            if (!response.ok) {
                throw new Error(
                    `Modrinth API请求失败: ${response.status} ${response.statusText}`
                );
            }

            const data = await response.json();

            if (data) {
                modrinthData.value = {
                    downloadCount: data.downloads || 0,
                    dateCreated: data.published || null,
                    dateModified: data.updated || null,
                    gameVersions: data.game_versions || [],
                    team: data.team || "",
                    iconUrl: data.icon_url || null,
                };

                // 保存数据到本地存储以便离线使用
                try {
                    localStorage.setItem(
                        `modrinth_data_${identifier}`,
                        JSON.stringify(modrinthData.value)
                    );
                } catch (storageErr) {
                    console.warn(
                        "无法保存Modrinth模组数据到本地存储:",
                        storageErr
                    );
                }

                // 如果没有从CurseForge设置图标且有Modrinth图标，则使用Modrinth图标
                if (
                    !props.iconUrl &&
                    !modData.value.logoUrl &&
                    modrinthData.value.iconUrl
                ) {
                    icon.value = modrinthData.value.iconUrl;
                }
            }
        } catch (error) {
            // 尝试从本地存储加载缓存数据
            try {
                const identifier = props.modrinthId || props.modrinthSlug;
                const cachedData = localStorage.getItem(
                    `modrinth_data_${identifier}`
                );
                if (cachedData) {
                    modrinthData.value = JSON.parse(cachedData);
                    if (
                        !props.iconUrl &&
                        !modData.value.logoUrl &&
                        modrinthData.value.iconUrl
                    ) {
                        icon.value = modrinthData.value.iconUrl;
                    }
                    modrinthErrorMsg.value = t.usingCachedData + " (Modrinth)";
                    console.warn("使用缓存的Modrinth模组数据");
                    return;
                }
            } catch (cacheError) {
                console.error("读取Modrinth缓存数据失败:", cacheError);
            }

            const errorMessage =
                error instanceof Error ? error.message : "未知错误";
            modrinthErrorMsg.value = `${t.failedToFetchModrinth} ${errorMessage}`;
        } finally {
            modrinthLoading.value = false;
        }
    };

    // 生命周期钩子
    onMounted(async () => {
        // 如果有测试数据，直接使用测试数据
        if (
            props.testDownloads > 0 ||
            props.testVersions.length > 0 ||
            props.testAuthors.length > 0
        ) {
            modData.value = {
                downloadCount: props.testDownloads,
                dateCreated: props.testDateCreated || null,
                dateModified: props.testDateModified || null,
                gameVersions: props.testVersions,
                authors: props.testAuthors,
                logoUrl: null,
            };
        } else {
            await fetchModData();
        }

        // 如果有Modrinth测试数据，直接使用
        if (props.testModrinthDownloads > 0) {
            modrinthData.value = {
                downloadCount: props.testModrinthDownloads,
                dateCreated: props.testDateCreated || null,
                dateModified: props.testDateModified || null,
                gameVersions: props.testVersions,
                team: [],
                iconUrl: null,
            };
        } else {
            await fetchModrinthData();
        }
    });
</script>

<template>
    <div class="mod-info">
        <div class="mod-header">
            <div class="mod-icon">
                <img :src="icon" :alt="modName" class="no-preview" />
            </div>
            <div class="mod-stats">
                <h1 class="mod-title">{{ modName }}</h1>

                <div class="mod-badges">
                    <!-- CurseForge下载量 -->
                    <a
                        v-if="props.curseForgeId"
                        :href="curseForgeUrl"
                        target="_blank"
                        rel="noopener noreferrer"
                        class="badge-link no-external-icon"
                    >
                        <div
                            v-if="
                                projectId &&
                                !isLoading &&
                                !errorMsg &&
                                modData.downloadCount > 0
                            "
                            class="download-stats"
                        >
                            <div class="download-count">
                                <svg
                                    xmlns="http://www.w3.org/2000/svg"
                                    width="20"
                                    height="20"
                                    viewBox="0 0 24 24"
                                    class="download-icon"
                                >
                                    <path
                                        fill="currentColor"
                                        d="M12 2L2 7v10l10 5l10-5V7l-10-5zm0 2.18L18.82 8L12 11.82L5.18 8L12 4.18zM4 9.18l7 3.5v6.64l-7-3.5V9.18zm16 0v6.64l-7 3.5v-6.64l7-3.5z"
                                    />
                                </svg>
                                <span
                                    >{{ formattedDownloads }}
                                    {{ t.downloads }}</span
                                >
                            </div>
                        </div>

                        <div v-else-if="isLoading" class="loading-state">
                            <span>{{ t.loading }}</span>
                        </div>

                        <div v-else-if="errorMsg" class="error-state">
                            <div>{{ errorMsg }}</div>
                            <div
                                style="
                                    font-size: 0.8rem;
                                    margin-top: 4px;
                                    opacity: 0.8;
                                "
                            >
                                注：CurseForge API可能因浏览器CORS限制而无法访问
                            </div>
                        </div>

                        <div v-if="!projectId" class="curseforge-link">
                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                width="20"
                                height="20"
                                viewBox="0 0 24 24"
                                class="download-icon"
                            >
                                <path
                                    fill="currentColor"
                                    d="m6.307 5.581.391 1.675H0s.112.502.167.558c.168.279.335.614.559.837 1.06 1.228 2.902 1.73 4.409 2.009 1.06.224 2.121.28 3.181.335l1.228 3.293h.67l.391 1.061h-.558l-.949 3.07h9.321l-.949-3.07h-.558l.39-1.061h.67s.558-3.404 2.288-4.967C21.935 7.758 24 7.535 24 7.535V5.581H6.307zm9.377 8.428c-.447.279-.949.279-1.284.503-.223.111-.335.446-.335.446-.223-.502-.502-.67-.837-.781-.335-.112-.949-.056-1.786-.782-.558-.502-.614-1.172-.558-1.507v-.167c0-.056 0-.112.056-.168.111-.334.39-.669.948-.893 0 0-.39.559 0 1.117.224.335.67.502 1.061.279.167-.112.279-.335.335-.503.111-.39.111-.781-.224-1.06-.502-.446-.613-1.06-.279-1.451 0 0 .112.502.614.446.335 0 .335-.111.224-.223-.056-.167-.782-1.228.279-2.009 0 0 .669-.447 1.451-.391-.447.056-.949.335-1.116.782v.055c-.168.447-.056.949.279 1.396.223.335.502.614.614 1.06-.168-.056-.279 0-.391.112a.533.533 0 0 0-.112.502c.056.112.168.223.279.223h.168c.167-.055.279-.279.223-.446.112.111.167.391.112.558 0 .167-.112.335-.168.446-.056.112-.167.224-.223.335-.056.112-.112.224-.112.335 0 .112 0 .279.056.391.223.335.67 0 .782-.279.167-.335.111-.726-.112-1.061 0 0 .391.224.67 1.005.223.67-.168 1.451-.614 1.73z"
                                />
                            </svg>
                            <span>{{ t.checkCurseForge }}</span>
                        </div>
                    </a>

                    <!-- Modrinth下载量 -->
                    <a
                        v-if="modrinthUrl"
                        :href="modrinthUrl"
                        target="_blank"
                        rel="noopener noreferrer"
                        class="badge-link no-external-icon"
                    >
                        <div
                            v-if="
                                !modrinthLoading &&
                                !modrinthErrorMsg &&
                                modrinthData.downloadCount > 0
                            "
                            class="download-stats modrinth-stats"
                        >
                            <div class="download-count modrinth-download-count">
                                <svg
                                    xmlns="http://www.w3.org/2000/svg"
                                    width="20"
                                    height="20"
                                    viewBox="0 0 24 24"
                                    class="download-icon"
                                >
                                    <path
                                        fill="currentColor"
                                        d="M12.252.004a11.78 11.768 0 0 0-8.92 3.73a11 10.999 0 0 0-2.17 3.11a11.37 11.359 0 0 0-1.16 5.169c0 1.42.17 2.5.6 3.77c.24.759.77 1.899 1.17 2.529a12.3 12.298 0 0 0 8.85 5.639c.44.05 2.54.07 2.76.02c.2-.04.22.1-.26-1.7l-.36-1.37l-1.01-.06a8.5 8.489 0 0 1-5.18-1.8a5.34 5.34 0 0 1-1.3-1.26c0-.05.34-.28.74-.5a37.572 37.545 0 0 1 2.88-1.629c.03 0 .5.45 1.06.98l1 .97l2.07-.43l2.06-.43l1.47-1.47c.8-.8 1.48-1.5 1.48-1.52c0-.09-.42-1.63-.46-1.7c-.04-.06-.2-.03-1.02.18c-.53.13-1.2.3-1.45.4l-.48.15l-.53.53l-.53.53l-.93.1l-.93.07l-.52-.5a2.7 2.7 0 0 1-.96-1.7l-.13-.6l.43-.57c.68-.9.68-.9 1.46-1.1c.4-.1.65-.2.83-.33c.13-.099.65-.579 1.14-1.069l.9-.9l-.7-.7l-.7-.7l-1.95.54c-1.07.3-1.96.53-1.97.53c-.03 0-2.23 2.48-2.63 2.97l-.29.35l.28 1.03c.16.56.3 1.16.31 1.34l.03.3l-.34.23c-.37.23-2.22 1.3-2.84 1.63c-.36.2-.37.2-.44.1c-.08-.1-.23-.6-.32-1.03c-.18-.86-.17-2.75.02-3.73a8.84 8.839 0 0 1 7.9-6.93c.43-.03.77-.08.78-.1c.06-.17.5-2.999.47-3.039c-.01-.02-.1-.02-.2-.03Zm3.68.67c-.2 0-.3.1-.37.38c-.06.23-.46 2.42-.46 2.52c0 .04.1.11.22.16a8.51 8.499 0 0 1 2.99 2a8.38 8.379 0 0 1 2.16 3.449a6.9 6.9 0 0 1 .4 2.8c0 1.07 0 1.27-.1 1.73a9.37 9.369 0 0 1-1.76 3.769c-.32.4-.98 1.06-1.37 1.38c-.38.32-1.54 1.1-1.7 1.14c-.1.03-.1.06-.07.26c.03.18.64 2.56.7 2.78l.06.06a12.07 12.058 0 0 0 7.27-9.4c.13-.77.13-2.58 0-3.4a11.96 11.948 0 0 0-5.73-8.578c-.7-.42-2.05-1.06-2.25-1.06Z"
                                    />
                                </svg>
                                <span
                                    >{{ formattedModrinthDownloads }}
                                    {{ t.downloads }}</span
                                >
                            </div>
                        </div>

                        <div v-else-if="modrinthLoading" class="loading-state">
                            <span>{{ t.loading }}</span>
                        </div>

                        <div v-else-if="modrinthErrorMsg" class="error-state">
                            <span>{{ modrinthErrorMsg }}</span>
                        </div>
                    </a>
                </div>
            </div>
        </div>

        <!-- CurseForge/Modrinth 详细信息 -->
        <div
            v-if="
                (projectId && !isLoading && !errorMsg) ||
                (modrinthUrl && !modrinthLoading && !modrinthErrorMsg)
            "
            class="mod-details"
        >
            <div v-if="modData.gameVersions.length > 0" class="detail-section">
                <h3 class="detail-title">{{ t.supportedVersions }}</h3>
                <div class="version-tags">
                    <span
                        v-for="(version, index) in sortedVersions"
                        :key="index"
                        class="version-tag"
                    >
                        {{ version }}
                    </span>
                </div>
            </div>

            <div v-if="modData.authors.length > 0" class="detail-section">
                <h3 class="detail-title">{{ t.modAuthors }}</h3>
                <div class="authors-list">
                    <a
                        v-for="(author, index) in modData.authors"
                        :key="index"
                        :href="author.url"
                        target="_blank"
                        rel="noopener noreferrer"
                        class="author-tag no-external-icon"
                    >
                        {{ author.name }}
                    </a>
                </div>
            </div>

            <div class="detail-section">
                <div class="dates-row">
                    <div v-if="modData.dateCreated" class="date-item">
                        <span class="date-label">{{ t.dateCreated }}</span>
                        <span class="date-value">{{
                            formattedDateCreated
                        }}</span>
                    </div>

                    <div v-if="modData.dateModified" class="date-item">
                        <span class="date-label">{{ t.lastUpdated }}</span>
                        <span class="date-value">{{
                            formattedDateModified
                        }}</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
    .mod-info {
        margin-bottom: 32px;
        background-color: var(--vp-c-bg-alt);
        border-radius: 20px;
        padding: 28px;
        border: 2px solid var(--vp-c-divider);
        transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
        position: relative;
        overflow: hidden;
    }

    .mod-info::before {
        content: "";
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background: linear-gradient(
            135deg,
            rgba(var(--vp-c-brand-rgb), 0.03) 0%,
            transparent 100%
        );
        z-index: 0;
        border-radius: 20px;
    }

    .dark .mod-info {
        background-color: var(--vp-c-bg-alt);
        border-color: var(--vp-c-divider);
    }

    .dark .mod-info::before {
        background: linear-gradient(
            135deg,
            rgba(var(--vp-c-brand-rgb), 0.05) 0%,
            transparent 100%
        );
    }

    .mod-header {
        display: flex;
        align-items: center;
        gap: 18px;
        position: relative;
        z-index: 1;
    }

    .mod-icon {
        width: 80px;
        height: 80px;
        border-radius: 16px;
        overflow: hidden;
        flex-shrink: 0;
        border: 2px solid var(--vp-c-divider);
        transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
    }

    .mod-icon img {
        width: 100%;
        height: 100%;
        object-fit: cover;
    }

    .mod-stats {
        flex: 1;
    }

    .mod-title {
        margin: 0 0 18px 0;
        font-size: 2rem;
        line-height: 1.2;
        font-weight: 700;
        color: var(--vp-c-text-1);
        transition: all 0.3s ease;
    }

    .mod-badges {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
        gap: 20px;
    }

    .badge-link {
        text-decoration: none;
        display: inline-block;
        transition: all 0.3s ease;
    }

    .no-external-icon::after {
        display: none !important;
    }

    /* 禁用ModInfo组件内所有链接的图标 */
    .mod-info a::before {
        display: none !important;
    }

    .mod-info a::after {
        display: none !important;
    }

    .download-stats,
    .curseforge-link,
    .loading-state,
    .error-state {
        border-radius: 12px;
        overflow: hidden;
        transition: all 0.3s ease;
    }

    .download-count {
        display: flex;
        align-items: center;
        gap: 12px;
        padding: 12px 20px;
        background: var(--vp-c-brand);
        color: white;
        border-radius: 12px;
        font-size: 1rem;
        font-weight: 600;
        transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
        border: 2px solid var(--vp-c-brand);
    }

    /* Modrinth样式 */
    .modrinth-stats {
        --modrinth-color-rgb: 30, 215, 96;
    }

    .modrinth-download-count {
        background: rgb(var(--modrinth-color-rgb));
        border-color: rgb(var(--modrinth-color-rgb));
    }

    .download-icon,
    .curseforge-icon {
        flex-shrink: 0;
    }

    .curseforge-link {
        display: flex;
        align-items: center;
        gap: 12px;
        padding: 12px 20px;
        background: var(--vp-c-brand);
        color: white;
        border-radius: 12px;
        font-size: 1rem;
        font-weight: 600;
        transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
        border: 2px solid var(--vp-c-brand);
    }

    .loading-state {
        padding: 12px 20px;
        background: var(--vp-c-brand-soft);
        color: var(--vp-c-brand);
        border-radius: 12px;
        font-size: 1rem;
        font-weight: 500;
        border: 2px solid var(--vp-c-brand-light);
        animation: pulse 1.5s infinite;
    }

    @keyframes pulse {
        0% {
            opacity: 0.8;
        }
        50% {
            opacity: 1;
        }
        100% {
            opacity: 0.8;
        }
    }

    .error-state {
        padding: 12px 20px;
        background: var(--vp-c-danger-soft);
        color: var(--vp-c-danger);
        border-radius: 12px;
        font-size: 1rem;
        font-weight: 500;
        border: 2px solid var(--vp-c-danger-light);
    }

    .mod-details {
        margin-top: 24px;
        border-top: 2px solid var(--vp-c-divider);
        padding-top: 24px;
        display: flex;
        flex-direction: column;
        gap: 20px;
        position: relative;
        z-index: 1;
    }

    .detail-section {
        display: flex;
        flex-direction: column;
        gap: 8px;
    }

    .detail-title {
        font-size: 0.94rem;
        color: var(--vp-c-text-2);
        margin: 0;
        font-weight: 600;
        display: flex;
        align-items: center;
        gap: 6px;
    }

    .detail-title::before {
        content: "";
        display: inline-block;
        width: 4px;
        height: 18px;
        background: linear-gradient(
            to bottom,
            var(--vp-c-brand),
            var(--vp-c-brand-light)
        );
        border-radius: 2px;
    }

    .version-tags {
        display: flex;
        flex-wrap: wrap;
        gap: 8px;
        max-width: 100%;
    }

    .version-tag {
        padding: 8px 14px;
        background-color: var(--vp-c-brand-soft);
        color: var(--vp-c-brand);
        border-radius: 24px;
        font-size: 0.9rem;
        font-weight: 600;
        transition: all 0.3s ease;
        white-space: nowrap;
        border: 2px solid var(--vp-c-brand-light);
    }

    .authors-list {
        display: flex;
        flex-wrap: wrap;
        gap: 8px;
        max-width: 100%;
    }

    .author-tag {
        padding: 8px 16px;
        background-color: var(--vp-c-brand-soft);
        color: var(--vp-c-brand);
        border-radius: 24px;
        font-size: 0.95rem;
        font-weight: 600;
        transition: all 0.3s ease;
        text-decoration: none;
        border: 2px solid var(--vp-c-brand-light);
        display: flex;
        align-items: center;
        gap: 8px;
    }

    .author-tag::before {
        content: "";
        display: inline-block;
        width: 6px;
        height: 6px;
        border-radius: 50%;
        background-color: var(--vp-c-brand);
    }

    .dates-row {
        display: flex;
        flex-wrap: wrap;
        gap: 20px;
        align-items: center;
    }

    .date-item {
        display: flex;
        align-items: center;
        gap: 12px;
        padding: 10px 16px;
        background-color: var(--vp-c-bg-soft);
        border-radius: 12px;
        border: 2px solid var(--vp-c-divider);
        transition: all 0.3s ease;
    }

    .date-label {
        font-size: 0.9rem;
        font-weight: 600;
        color: var(--vp-c-text-2);
    }

    .date-value {
        font-size: 0.9rem;
        color: var(--vp-c-brand);
        font-weight: 500;
    }

    @media (max-width: 768px) {
        .mod-badges {
            grid-template-columns: 1fr;
            gap: 16px;
        }

        .mod-icon {
            width: 60px;
            height: 60px;
        }

        .mod-title {
            font-size: 1.5rem;
            margin-bottom: 12px;
        }

        .mod-info {
            padding: 18px;
        }

        .dates-row {
            flex-direction: column;
            gap: 12px;
            align-items: flex-start;
        }
    }

    @media (max-width: 480px) {
        .mod-header {
            gap: 12px;
        }

        .mod-icon {
            width: 50px;
            height: 50px;
        }

        .mod-title {
            font-size: 1.3rem;
            margin-bottom: 10px;
        }

        .download-count,
        .curseforge-link,
        .loading-state,
        .error-state {
            padding: 8px 14px;
            font-size: 0.9rem;
        }

        .version-tag,
        .author-tag {
            font-size: 0.8rem;
            padding: 4px 8px;
        }
    }
</style>
