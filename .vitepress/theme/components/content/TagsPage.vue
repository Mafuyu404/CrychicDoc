<template>
    <div class="tags-page">
        <h1 class="tags-page__title">
            {{ frontmatter.value?.title || t.pageTitle }}
        </h1>

        <!-- Loading / Error / Empty -->
        <div v-if="isLoading" class="tags-page__status">
            <div class="spinner" />
            <p>{{ t.loadingTagData }}</p>
        </div>

        <div v-else-if="loadError" class="tags-page__status">
            <p>{{ t.loadingError }}</p>
            <button class="btn btn--outline" @click="loadTagData">
                {{ t.retry }}
            </button>
        </div>

        <div v-else-if="totalTags === 0" class="tags-page__status">
            <p>{{ t.noTagsFound }}</p>
        </div>

        <template v-else>
            <!-- Toolbar -->
            <div class="toolbar">
                <div class="toolbar__search">
                    <svg
                        class="toolbar__search-icon"
                        viewBox="0 0 24 24"
                        fill="none"
                        stroke="currentColor"
                        stroke-width="2"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                    >
                        <circle cx="11" cy="11" r="8" />
                        <path d="m21 21-4.35-4.35" />
                    </svg>
                    <input
                        v-model="searchQuery"
                        type="text"
                        :placeholder="t.searchPlaceholder"
                    />
                </div>
                <div class="toolbar__views">
                    <button
                        :class="[
                            'toolbar__view-btn',
                            { active: viewMode === 'cloud' },
                        ]"
                        @click="viewMode = 'cloud'"
                    >
                        {{ t.tagCloud }}
                    </button>
                    <button
                        :class="[
                            'toolbar__view-btn',
                            { active: viewMode === 'list' },
                        ]"
                        @click="viewMode = 'list'"
                    >
                        {{ t.list }}
                    </button>
                </div>
            </div>

            <!-- Cloud view -->
            <div v-if="viewMode === 'cloud'" class="cloud">
                <TagBadge
                    v-for="tag in filteredTags"
                    :key="tag.name"
                    :tag="tag.name"
                    :count="tag.count"
                    :clickable="true"
                    :style="{ fontSize: tagSize(tag.count) }"
                    @click="toggleTag"
                />
            </div>

            <!-- List view -->
            <div v-else class="list">
                <div
                    v-for="tag in filteredTags"
                    :key="tag.name"
                    class="list__item"
                    @click="toggleTag(tag.name)"
                >
                    <div class="list__item-head">
                        <TagBadge
                            :tag="tag.name"
                            :count="tag.count"
                            :clickable="true"
                        />
                        <span class="muted small"
                            >{{ tag.count }} {{ t.pages }}</span
                        >
                    </div>
                    <p class="list__item-preview muted small">
                        {{
                            tag.pages
                                .slice(0, 3)
                                .map((p) => p?.title || p?.path || "Untitled")
                                .join(", ")
                        }}
                        <span v-if="tag.pages.length > 3" class="dimmed">
                            {{
                                t.morePages.replace(
                                    "{count}",
                                    String(tag.pages.length - 3),
                                )
                            }}
                        </span>
                    </p>
                </div>
            </div>

            <!-- Selected tags results -->
            <section v-if="selectedTags.length > 0" class="results">
                <div class="results__header">
                    <h2 class="results__heading">
                        {{ t.selectedTags }}
                        <TagBadge
                            v-for="tag in selectedTags"
                            :key="tag"
                            :tag="tag"
                            :clickable="true"
                            @click="toggleTag"
                        />
                        <span class="muted small"
                            >({{ selectedTagPages.length }} {{ t.pages }})</span
                        >
                    </h2>
                    <button class="btn btn--ghost" @click="clearSelection">
                        {{ t.clearSelection }}
                    </button>
                </div>

                <div v-if="selectedTagPages.length > 0" class="results__list">
                    <a
                        v-for="pg in selectedTagPages"
                        :key="pg.path"
                        :href="pg.path"
                        class="result-row"
                    >
                        <div class="result-row__head">
                            <h3 class="result-row__title">
                                {{ pg.title || pg.path || "Untitled" }}
                            </h3>
                            <div
                                v-if="pg.progress != null"
                                class="result-row__progress"
                            >
                                <div class="progress-track">
                                    <div
                                        class="progress-fill"
                                        :style="{ width: `${pg.progress}%` }"
                                    />
                                </div>
                                <span class="dimmed small"
                                    >{{ pg.progress }}%</span
                                >
                            </div>
                        </div>
                        <p v-if="pg.description" class="result-row__desc muted">
                            {{ pg.description }}
                        </p>
                        <div class="result-row__tags">
                            <TagBadge
                                v-for="tg in (pg.tags || []).slice(0, 3)"
                                :key="tg"
                                :tag="tg"
                            />
                            <span
                                v-if="(pg.tags || []).length > 3"
                                class="dimmed small"
                                >+{{ (pg.tags || []).length - 3 }}</span
                            >
                        </div>
                    </a>
                </div>

                <div v-else class="tags-page__status" style="padding: 3rem 0">
                    <p>{{ t.noMatchingPages }}</p>
                    <p class="dimmed small">{{ t.noMatchingPagesHint }}</p>
                </div>
            </section>

            <!-- Stats -->
            <footer class="stats">
                <div class="stats__item">
                    <span class="stats__number">{{ totalTags }}</span>
                    <span class="muted small">{{ t.totalTags }}</span>
                </div>
                <div class="stats__item">
                    <span class="stats__number">{{ totalPages }}</span>
                    <span class="muted small">{{ t.totalPages }}</span>
                </div>
                <div class="stats__item">
                    <span class="stats__number">{{ filteredTags.length }}</span>
                    <span class="muted small">{{ t.matchingTags }}</span>
                </div>
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

    const { page, frontmatter, lang } = useData();

    const tagData = ref<TagData>({});
    const isLoading = ref(false);
    const loadError = ref<string | null>(null);
    const searchQuery = ref("");
    const viewMode = ref<"cloud" | "list">("cloud");
    const selectedTags = ref<string[]>([]);

    // ── Derived state ────────────────────────────────────────────────────

    const filteredTags = computed(() => {
        const tags = Object.values(tagData.value);
        const sorted = tags.sort((a, b) => b.count - a.count);
        if (!searchQuery.value) return sorted;
        const q = searchQuery.value.toLowerCase();
        return sorted.filter((tag) => tag.name.toLowerCase().includes(q));
    });

    const selectedTagPages = computed<PageInfo[]>(() => {
        if (selectedTags.value.length === 0) return [];
        if (selectedTags.value.length === 1) {
            return (tagData.value[selectedTags.value[0]]?.pages ?? []).filter(
                (pg): pg is PageInfo => Boolean(pg?.path),
            );
        }
        // Intersection of pages across all selected tags
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

    const maxCount = computed(() =>
        Math.max(1, ...Object.values(tagData.value).map((tag) => tag.count)),
    );

    function tagSize(count: number): string {
        const ratio = count / maxCount.value;
        return `${0.75 + 0.75 * ratio}rem`;
    }

    // ── Tag selection with URL sync ──────────────────────────────────────

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

    // ── Data loading ─────────────────────────────────────────────────────

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
        } catch (err) {
            loadError.value = t.loadingError;
            tagData.value = {};
        } finally {
            isLoading.value = false;
        }
    }

    // ── Lifecycle ────────────────────────────────────────────────────────

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
    /* ── Layout ─────────────────────────────────────────────────────── */
    .tags-page {
        max-width: 1024px;
        margin: 0 auto;
        padding: 4rem 2rem;
    }
    .tags-page__title {
        margin: 0 0 3.5rem;
        font-size: 2.75rem;
        font-weight: 700;
        letter-spacing: -0.015em;
        color: var(--vp-c-text-1);
        line-height: 1.1;
    }

    /* ── Shared helpers ─────────────────────────────────────────────── */
    .muted {
        color: var(--vp-c-text-2);
    }
    .dimmed {
        color: var(--vp-c-text-3);
    }
    .small {
        font-size: 0.85rem;
    }

    .btn {
        display: inline-flex;
        align-items: center;
        gap: 0.4rem;
        padding: 0.45rem 0.85rem;
        border-radius: 6px;
        font-size: 0.85rem;
        cursor: pointer;
        border: none;
        transition:
            background-color 0.2s,
            color 0.2s;
    }
    .btn--outline {
        background: transparent;
        color: var(--vp-c-text-1);
        border: 1px solid var(--vp-c-divider);
    }
    .btn--ghost {
        background: transparent;
        color: var(--vp-c-text-3);
    }

    /* ── Status screens ─────────────────────────────────────────────── */
    .tags-page__status {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        padding: 6rem 0;
        text-align: center;
        color: var(--vp-c-text-3);
    }
    .tags-page__status p {
        font-size: 0.95rem;
        color: var(--vp-c-text-2);
        margin: 0.5rem 0;
    }

    .spinner {
        width: 1.5rem;
        height: 1.5rem;
        margin-bottom: 1rem;
        border: 2px solid var(--vp-c-divider);
        border-top-color: var(--vp-c-text-3);
        border-radius: 50%;
        animation: spin 1s linear infinite;
    }
    @keyframes spin {
        to {
            transform: rotate(360deg);
        }
    }

    /* ── Toolbar ─────────────────────────────────────────────────────── */
    .toolbar {
        display: flex;
        gap: 1rem;
        align-items: center;
        margin-bottom: 2.5rem;
        padding-bottom: 1.25rem;
        border-bottom: 1px solid var(--vp-c-divider);
    }
    .toolbar__search {
        position: relative;
        flex: 1;
        max-width: 320px;
    }
    .toolbar__search-icon {
        position: absolute;
        left: 0.85rem;
        top: 50%;
        transform: translateY(-50%);
        width: 1rem;
        height: 1rem;
        color: var(--vp-c-text-3);
        pointer-events: none;
    }
    .toolbar__search input {
        width: 100%;
        padding: 0.6rem 1rem 0.6rem 2.5rem;
        border: 1px solid var(--vp-c-divider);
        border-radius: 8px;
        background: var(--vp-c-bg);
        color: var(--vp-c-text-1);
        font-size: 0.9rem;
        transition: border-color 0.2s;
    }
    .toolbar__search input:focus {
        outline: none;
        border-color: var(--vp-c-text-3);
    }

    .toolbar__views {
        display: flex;
        gap: 0.25rem;
        margin-left: auto;
        background: var(--vp-c-bg-soft);
        padding: 0.25rem;
        border-radius: 8px;
    }
    .toolbar__view-btn {
        padding: 0.4rem 0.8rem;
        border-radius: 6px;
        border: none;
        background: transparent;
        color: var(--vp-c-text-2);
        font-size: 0.85rem;
        font-weight: 500;
        cursor: pointer;
        transition:
            background-color 0.2s,
            color 0.2s;
    }
    .toolbar__view-btn.active {
        background: var(--vp-c-bg);
        color: var(--vp-c-text-1);
    }

    /* ── Cloud ───────────────────────────────────────────────────────── */
    .cloud {
        display: flex;
        flex-wrap: wrap;
        gap: 0.75rem;
        margin-bottom: 3.5rem;
        align-items: center;
    }

    /* ── List ────────────────────────────────────────────────────────── */
    .list {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
        gap: 1rem;
        margin-bottom: 3.5rem;
    }
    .list__item {
        padding: 1.25rem;
        border: 1px solid var(--vp-c-divider);
        border-radius: 12px;
        cursor: pointer;
    }
    .list__item-head {
        display: flex;
        align-items: center;
        justify-content: space-between;
        margin-bottom: 0.5rem;
    }
    .list__item-preview {
        margin: 0;
        line-height: 1.5;
    }

    /* ── Results ─────────────────────────────────────────────────────── */
    .results {
        margin: 3.5rem 0;
    }
    .results__header {
        display: flex;
        align-items: center;
        justify-content: space-between;
        margin-bottom: 1.5rem;
        padding-bottom: 1rem;
        border-bottom: 1px solid var(--vp-c-divider);
    }
    .results__heading {
        margin: 0;
        font-size: 1.4rem;
        font-weight: 600;
        display: flex;
        align-items: center;
        gap: 0.75rem;
        flex-wrap: wrap;
        letter-spacing: -0.01em;
    }

    .results__list {
        border-top: 1px solid var(--vp-c-divider);
    }
    .result-row {
        display: block;
        padding: 1.25rem 0;
        text-decoration: none;
        color: inherit;
        border-bottom: 1px solid var(--vp-c-divider);
        transition: opacity 0.2s;
    }
    .result-row:hover {
        opacity: 0.8;
    }
    .result-row__head {
        display: flex;
        align-items: baseline;
        justify-content: space-between;
        gap: 1rem;
        margin-bottom: 0.35rem;
    }
    .result-row__title {
        margin: 0;
        font-size: 1.1rem;
        font-weight: 600;
        color: var(--vp-c-text-1);
        letter-spacing: -0.01em;
    }
    .result-row__progress {
        display: flex;
        align-items: center;
        gap: 0.5rem;
        flex-shrink: 0;
    }
    .progress-track {
        width: 48px;
        height: 4px;
        background: var(--vp-c-divider);
        border-radius: 2px;
        overflow: hidden;
    }
    .progress-fill {
        height: 100%;
        background: var(--vp-c-text-2);
    }
    .result-row__desc {
        margin: 0 0 0.75rem;
        font-size: 0.9rem;
        line-height: 1.5;
        max-width: 800px;
    }
    .result-row__tags {
        display: flex;
        flex-wrap: wrap;
        gap: 0.5rem;
        align-items: center;
    }

    /* ── Stats ───────────────────────────────────────────────────────── */
    .stats {
        display: flex;
        gap: 4rem;
        padding: 2.5rem 0;
        margin-top: 3.5rem;
        border-top: 1px solid var(--vp-c-divider);
    }
    .stats__number {
        display: block;
        font-size: 2rem;
        font-weight: 600;
        color: var(--vp-c-text-1);
        line-height: 1;
        margin-bottom: 0.4rem;
        letter-spacing: -0.02em;
    }

    /* ── Responsive ──────────────────────────────────────────────────── */
    @media (max-width: 768px) {
        .tags-page {
            padding: 2rem 1.5rem;
        }
        .tags-page__title {
            font-size: 2rem;
            margin-bottom: 2rem;
        }
        .toolbar {
            flex-direction: column;
            align-items: stretch;
            border-bottom: none;
        }
        .toolbar__search {
            max-width: none;
        }
        .toolbar__views {
            margin-left: 0;
            justify-content: center;
        }
        .results__header {
            flex-direction: column;
            gap: 0.75rem;
            align-items: flex-start;
        }
        .stats {
            flex-direction: column;
            gap: 1.5rem;
        }
        .result-row__head {
            flex-direction: column;
            align-items: flex-start;
            gap: 0.25rem;
        }
    }
</style>
