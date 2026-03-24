<template>
    <div class="tags-page">
        <header class="tags-page__hero">
            <h1 class="tags-page__title">
                {{ frontmatter.value?.title || t.pageTitle }}
            </h1>
            <p class="tags-page__lead">
                {{ t.searchPlaceholder }}
            </p>
        </header>

        <div v-if="isLoading" class="tags-page__status">
            <v-progress-circular indeterminate color="primary" size="28" />
            <p>{{ t.loadingTagData }}</p>
        </div>

        <div v-else-if="loadError" class="tags-page__status">
            <p>{{ t.loadingError }}</p>
            <v-btn variant="outlined" rounded @click="loadTagData">{{ t.retry }}</v-btn>
        </div>

        <div v-else-if="totalTags === 0" class="tags-page__status">
            <p>{{ t.noTagsFound }}</p>
        </div>

        <template v-else>
            <div class="toolbar">
                <v-text-field
                    v-model="searchQuery"
                    :placeholder="t.searchPlaceholder"
                    prepend-inner-icon="mdi-magnify"
                    variant="outlined"
                    density="comfortable"
                    rounded
                    hide-details
                    class="tags-page__search"
                />

                <v-btn-toggle
                    v-model="viewMode"
                    mandatory
                    density="comfortable"
                    rounded
                    variant="outlined"
                    divided
                    color="primary"
                >
                    <v-btn value="cloud" size="small">{{ t.tagCloud }}</v-btn>
                    <v-btn value="list" size="small">{{ t.list }}</v-btn>
                </v-btn-toggle>
            </div>

            <div v-if="viewMode === 'cloud'" class="cloud">
                <TagBadge
                    v-for="tag in filteredTags"
                    :key="tag.name"
                    :tag="tag.name"
                    :count="tag.count"
                    :clickable="true"
                    :style="{ fontSize: tagSize(tag.count) }"
                    @click="toggleTag(tag.name)"
                />
            </div>

            <div v-else class="list">
                <v-card
                    v-for="tag in filteredTags"
                    :key="tag.name"
                    variant="flat"
                    :ripple="false"
                    hover
                    class="list__item"
                    @click="toggleTag(tag.name)"
                >
                    <div class="list__item-inner">
                        <div class="list__item-head">
                            <v-chip
                                :color="getTagColor(tag.name)"
                                variant="tonal"
                                size="small"
                                label
                            >{{ tag.name }}</v-chip>
                            <span class="muted small">{{ tag.count }} {{ t.pages }}</span>
                        </div>
                        <p class="list__item-preview muted small">
                            {{ tag.pages.slice(0, 3).map((p) => p?.title || p?.path || 'Untitled').join(', ') }}
                            <span v-if="tag.pages.length > 3" class="dimmed">
                                {{ t.morePages.replace('{count}', String(tag.pages.length - 3)) }}
                            </span>
                        </p>
                    </div>
                </v-card>
            </div>

            <section v-if="selectedTags.length > 0" class="results">
                <div class="results__header">
                    <h2 class="results__heading">
                        {{ t.selectedTags }}
                        <v-chip
                            v-for="tag in selectedTags"
                            :key="tag"
                            closable
                            size="small"
                            variant="tonal"
                            color="primary"
                            @click:close="toggleTag(tag)"
                        >{{ tag }}</v-chip>
                    </h2>
                    <div class="results__meta muted small">
                        {{ selectedTagPages.length }} {{ t.pages }}
                    </div>
                    <v-btn variant="text" size="small" @click="clearSelection">{{ t.clearSelection }}</v-btn>
                </div>

                <div v-if="selectedTagPages.length > 0" class="results__list">
                    <v-card
                        v-for="pg in selectedTagPages"
                        :key="pg.path"
                        tag="a"
                        :href="pg.path"
                        variant="flat"
                        :ripple="false"
                        hover
                        class="result-row"
                    >
                        <div class="result-row__inner">
                            <div class="result-row__head">
                                <h3 class="result-row__title">{{ pg.title || pg.path || 'Untitled' }}</h3>
                                <div v-if="pg.progress != null" class="result-row__progress">
                                    <v-progress-linear
                                        :model-value="pg.progress"
                                        color="primary"
                                        rounded
                                        height="4"
                                        style="width: 56px;"
                                    />
                                    <span class="dimmed small">{{ pg.progress }}%</span>
                                </div>
                            </div>
                            <p v-if="pg.description" class="result-row__desc muted">{{ pg.description }}</p>
                            <div class="result-row__tags">
                                <v-chip
                                    v-for="tg in (pg.tags || []).slice(0, 4)"
                                    :key="tg"
                                    size="x-small"
                                    variant="tonal"
                                    label
                                >{{ tg }}</v-chip>
                                <span v-if="(pg.tags || []).length > 4" class="dimmed small">+{{ (pg.tags || []).length - 4 }}</span>
                            </div>
                        </div>
                    </v-card>
                </div>

                <div v-else class="tags-page__status tags-page__status--compact">
                    <p>{{ t.noMatchingPages }}</p>
                    <p class="dimmed small">{{ t.noMatchingPagesHint }}</p>
                </div>
            </section>

            <footer class="stats">
                <v-card v-for="stat in statsItems" :key="stat.label" variant="flat" class="stats__item">
                    <div class="stats__item-inner">
                        <span class="stats__number">{{ stat.value }}</span>
                        <span class="muted small">{{ stat.label }}</span>
                    </div>
                </v-card>
            </footer>
        </template>
    </div>
</template>

<script setup lang="ts">
    import { ref, computed, onMounted, onBeforeUnmount, watch } from "vue";
    import { useData, withBase } from "vitepress";
    import TagBadge from "../ui/TagBadge.vue";
    import { useSafeI18n } from "@utils/i18n/locale";
    import {
        resolveLanguageCode,
        getDefaultLanguage,
    } from "@config/project-api";

    interface PageInfo {
        title: string;
        path: string;
        tags: string[];
        description?: string;
        progress?: number;
    }

    interface TagInfo {
        name: string;
        count: number;
        pages: PageInfo[];
    }

    type TagData = Record<string, TagInfo>;

    const { t } = useSafeI18n("tags-page", {
        pageTitle: "Tags",
        loadingTagData: "Loading tag data...",
        loadingError: "Failed to load tag data. Please try again.",
        retry: "Retry",
        noTagsFound: "No tags found.",
        searchPlaceholder: "Search tags...",
        tagCloud: "Cloud",
        list: "List",
        pages: "pages",
        morePages: "and {count} more.",
        selectedTags: "Pages with tags:",
        clearSelection: "Clear",
        noMatchingPages: "No pages match all selected tags.",
        noMatchingPagesHint: "Try removing some tags to see more results.",
        totalTags: "Total Tags",
        totalPages: "Total Pages",
        matchingTags: "Matching Tags",
    });

    const { frontmatter, lang } = useData();

    const tagData = ref<TagData>({});
    const isLoading = ref(false);
    const loadError = ref<string | null>(null);
    const searchQuery = ref("");
    const viewMode = ref<"cloud" | "list">("cloud");
    const selectedTags = ref<string[]>([]);

    const filteredTags = computed(() => {
        const tags = Object.values(tagData.value);
        const sorted = tags.sort((a, b) => b.count - a.count);
        if (!searchQuery.value) return sorted;
        const q = searchQuery.value.toLowerCase();
        return sorted.filter((tag) => tag.name.toLowerCase().includes(q));
    });

    function tagSize(count: number): string {
        const tags = Object.values(tagData.value);
        if (!tags.length) return "1rem";
        const max = Math.max(...tags.map((t) => t.count));
        const min = Math.min(...tags.map((t) => t.count));
        const range = max - min || 1;
        const t = (count - min) / range;
        return `${0.82 + t * 0.72}rem`;
    }

    const selectedTagPages = computed<PageInfo[]>(() => {
        if (selectedTags.value.length === 0) return [];
        if (selectedTags.value.length === 1) {
            return (tagData.value[selectedTags.value[0]]?.pages ?? []).filter(
                (pg): pg is PageInfo => Boolean(pg?.path),
            );
        }

        const sets = selectedTags.value.map(
            (tag) =>
                new Set(
                    (tagData.value[tag]?.pages ?? [])
                        .filter((pg): pg is PageInfo => Boolean(pg?.path))
                        .map((p) => p.path),
                ),
        );
        const first = (tagData.value[selectedTags.value[0]]?.pages ?? []).filter(
            (pg): pg is PageInfo => Boolean(pg?.path),
        );
        return first.filter((pg) => sets.every((s) => s.has(pg.path)));
    });

    const totalTags = computed(() => Object.keys(tagData.value).length);
    const totalPages = computed(() =>
        Object.values(tagData.value).reduce((sum, tag) => sum + tag.count, 0),
    );

    const statsItems = computed(() => [
        { value: totalTags.value, label: t.totalTags },
        { value: totalPages.value, label: t.totalPages },
        { value: filteredTags.value.length, label: t.matchingTags },
    ]);

    function getTagColor(tagName: string): string {
        const colorMap: Record<string, string> = {
            minecraft: "#62c462",
            neoforge: "#ff6b35",
            forge: "#ff6b35",
            fabric: "#dba213",
            tutorial: "#3b82f6",
            api: "#8b5cf6",
            guide: "#10b981",
            kubejs: "#5518fe",
        };
        const lower = tagName.toLowerCase();
        for (const [key, color] of Object.entries(colorMap)) {
            if (lower.includes(key)) return color;
        }
        // Hash-based fallback
        const fallbacks = ["#22c55e", "#eab308", "#6b7280", "#dc2626", "#2563eb", "#059669", "#7c3aed", "#f43f5e", "#a855f7", "#14b8a6"];
        let hash = 0;
        for (let i = 0; i < tagName.length; i++) hash = tagName.charCodeAt(i) + ((hash << 5) - hash);
        return fallbacks[Math.abs(hash) % fallbacks.length];
    }

    function toggleTag(tagName: string) {
        const next = selectedTags.value.includes(tagName)
            ? selectedTags.value.filter((t) => t !== tagName)
            : [...selectedTags.value, tagName];
        selectedTags.value = next;
        syncTagsToUrl(next);
    }

    function clearSelection() {
        selectedTags.value = [];
        syncTagsToUrl([]);
    }

    function syncTagsToUrl(tags: string[]) {
        const url = new URL(window.location.href);
        if (tags.length > 0) {
            url.searchParams.set("tags", tags.join(","));
        } else {
            url.searchParams.delete("tags");
        }
        window.history.pushState({}, "", url.toString());
    }

    function readTagsFromUrl(): string[] {
        const param = new URLSearchParams(window.location.search).get("tags");
        return param ? param.split(",").filter(Boolean) : [];
    }

    function onPopState() {
        selectedTags.value = readTagsFromUrl();
    }

    async function loadTagData() {
        isLoading.value = true;
        loadError.value = null;

        try {
            const langCode = resolveLanguageCode(lang.value);
            const defaultCode = getDefaultLanguage().code;
            const urls = Array.from(
                new Set([
                    withBase(`/data/${langCode}/tags.json`),
                    withBase(`/data/${defaultCode}/tags.json`),
                ]),
            );

            let loaded: { tags?: TagData } | null = null;
            for (const url of urls) {
                const res = await fetch(url);
                if (!res.ok) continue;
                loaded = await res.json();
                break;
            }

            tagData.value = loaded?.tags ?? {};
            if (!loaded) loadError.value = t.loadingError;
        } catch {
            loadError.value = t.loadingError;
            tagData.value = {};
        } finally {
            isLoading.value = false;
        }
    }

    watch(lang, (next, prev) => {
        if (next !== prev) loadTagData();
    });

    onMounted(() => {
        loadTagData();
        selectedTags.value = readTagsFromUrl();
        window.addEventListener("popstate", onPopState);
    });

    onBeforeUnmount(() => {
        window.removeEventListener("popstate", onPopState);
    });
</script>

<style scoped>
.tags-page {
    width: min(100%, 1560px);
    margin: 0 auto;
    padding: 3rem 0 4rem;
    display: grid;
    gap: 2.5rem;
}

.tags-page__hero {
    display: grid;
    gap: 0.85rem;
    max-width: 68ch;
    padding-bottom: 1.25rem;
    border-bottom: 1px solid var(--vp-c-divider);
}

.tags-page__title {
    margin: 0;
    font-size: clamp(2rem, 4vw, 2.7rem);
    font-weight: 700;
    letter-spacing: -0.03em;
    color: var(--vp-c-text-1);
    line-height: 1.04;
}

.tags-page__lead {
    margin: 0;
    font-size: 0.98rem;
    line-height: 1.7;
    color: var(--vp-c-text-2);
}

.muted { color: var(--vp-c-text-2); }
.dimmed { color: var(--vp-c-text-3); }
.small { font-size: 0.85rem; }

.tags-page__status {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 5rem 0;
    gap: 1rem;
    text-align: center;
    color: var(--vp-c-text-3);
}
.tags-page__status p { margin: 0; font-size: 0.95rem; color: var(--vp-c-text-2); }
.tags-page__status--compact { padding: 2rem 0 1rem; }

/* Toolbar */
.toolbar {
    display: flex;
    gap: 1rem;
    align-items: center;
    flex-wrap: wrap;
}
.tags-page__search { flex: 1; min-width: 240px; max-width: 420px; }
.toolbar > :last-child { margin-left: auto; }

/* Cloud: flex-wrap word cloud */
.cloud {
    display: flex;
    flex-wrap: wrap;
    gap: 0.75rem;
    margin-bottom: 0.5rem;
    align-items: center;
}

/* List grid */
.list {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    gap: 0.875rem;
}
.list__item {
    cursor: pointer;
    border: 1px solid var(--vp-c-divider) !important;
    background: var(--vp-c-bg-soft) !important;
    border-radius: 14px !important;
    transition: border-color 0.2s ease, box-shadow 0.2s ease !important;
}
.list__item:hover {
    border-color: color-mix(in srgb, var(--vp-c-brand-1) 22%, var(--vp-c-divider)) !important;
    box-shadow: 0 4px 16px rgba(15, 23, 42, 0.05) !important;
}
.list__item :deep(.v-card__overlay) { display: none; }
.list__item-inner { padding: 1.1rem; text-align: left; }
.list__item-head { display: flex; align-items: center; justify-content: space-between; gap: 0.75rem; margin-bottom: 0.7rem; }
.list__item-preview { margin: 0; line-height: 1.6; }

/* Results */
.results { display: grid; gap: 1.25rem; }
.results__header {
    display: flex; align-items: center; justify-content: space-between;
    gap: 1rem; flex-wrap: wrap;
    padding-bottom: 1rem; border-bottom: 1px solid var(--vp-c-divider);
}
.results__heading {
    margin: 0; font-size: 1.2rem; font-weight: 700;
    display: flex; align-items: center; gap: 0.6rem; flex-wrap: wrap;
}
.results__meta { margin-left: auto; }
.results__list { display: grid; gap: 0.75rem; }

.result-row {
    text-decoration: none !important;
    color: inherit !important;
    border: 1px solid var(--vp-c-divider) !important;
    background: var(--vp-c-bg-soft) !important;
    border-radius: 14px !important;
    transition: border-color 0.2s ease, box-shadow 0.2s ease !important;
}
.result-row:hover {
    border-color: color-mix(in srgb, var(--vp-c-brand-1) 22%, var(--vp-c-divider)) !important;
    box-shadow: 0 6px 20px rgba(15, 23, 42, 0.06) !important;
}
.result-row :deep(.v-card__overlay) { display: none; }
.result-row__inner { display: grid; gap: 0.65rem; padding: 1.15rem 1.25rem; }
.result-row__head { display: flex; align-items: baseline; justify-content: space-between; gap: 1rem; }
.result-row__title { margin: 0; font-size: 1.02rem; font-weight: 700; color: var(--vp-c-text-1); }
.result-row__progress { display: flex; align-items: center; gap: 0.5rem; flex-shrink: 0; }
.result-row__desc { margin: 0; font-size: 0.92rem; line-height: 1.65; max-width: 90ch; }
.result-row__tags { display: flex; flex-wrap: wrap; gap: 0.4rem; align-items: center; }

/* Stats */
.stats {
    display: grid;
    grid-template-columns: repeat(3, minmax(0, 1fr));
    gap: 0.875rem;
    padding-top: 1rem;
    border-top: 1px solid var(--vp-c-divider);
}
.stats__item {
    border: 1px solid var(--vp-c-divider-light) !important;
    background: var(--vp-c-bg-soft) !important;
    border-radius: 14px !important;
}
.stats__item :deep(.v-card__overlay) { display: none; }
.stats__item-inner { padding: 1rem 1.1rem; }
.stats__number {
    display: block; font-size: 1.7rem; font-weight: 700;
    color: var(--vp-c-text-1); line-height: 1; margin-bottom: 0.45rem; letter-spacing: -0.02em;
}

@media (max-width: 960px) {
    .stats { grid-template-columns: 1fr; }
}
@media (max-width: 768px) {
    .tags-page { padding: 2rem 0 3rem; }
    .toolbar { flex-direction: column; align-items: stretch; }
    .tags-page__search { max-width: none; }
    .toolbar > :last-child { margin-left: 0; }
    .results__header { align-items: flex-start; }
    .result-row__head { flex-direction: column; align-items: flex-start; }
}
</style>
