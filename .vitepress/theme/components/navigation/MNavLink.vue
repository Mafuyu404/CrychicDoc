<script setup lang="ts">
    import { computed } from "vue";
    import { withBase, useData } from "vitepress";
    import { slugify } from "@mdit-vue/shared";
    import MarkdownIt from "markdown-it";
    import {
        resolveThemeValueByMode,
        getThemeRuntime,
    } from "@utils/vitepress/runtime/theme";

    import type {
        NavBadge,
        NavIcon,
        NavLink,
        NavThemeIcon,
    } from "@utils/content/navLinkType";

    const { isDark } = useData();
    const { effectiveDark } = getThemeRuntime(isDark);
    const md = new MarkdownIt({ html: true, linkify: true });

    const props = defineProps<{
        noIcon?: boolean;
        icon?: NavLink["icon"];
        logo?: NavLink["logo"];
        badge?: NavLink["badge"];
        badges?: NavLink["badges"];
        title?: NavLink["title"];
        desc?: NavLink["desc"];
        link: NavLink["link"];
        tag?: NavLink["tag"];
        color?: NavLink["color"];
        target?: NavLink["target"];
        eyebrow?: NavLink["eyebrow"];
        note?: NavLink["note"];
        featured?: NavLink["featured"];
        style?: NavLink["style"];
        iconBackground?: NavLink["iconBackground"];
    }>();

    const themeIcon = (icon: NavIcon | NavThemeIcon): NavIcon => {
        if (typeof icon === "object" && !Array.isArray(icon)) {
            const record = icon as {
                dark?: NavIcon;
                light?: NavIcon;
                value?: NavIcon;
            };
            if ("dark" in record || "light" in record || "value" in record) {
                return resolveThemeValueByMode(record, effectiveDark.value) ?? icon;
            }
        }
        return icon;
    };

    const isRawSvg = (value: string) => /^\s*<svg[\s>]/i.test(value);
    const isExternalUrl = (value: string) =>
        /^(?:https?:)?\/\//.test(value) ||
        value.startsWith("data:") ||
        value.startsWith("blob:");
    const resolveAssetUrl = (value: string) =>
        isExternalUrl(value) ? value : withBase(value);

    const normalizeIconValue = (icon?: NavIcon | NavThemeIcon): string => {
        if (!icon) return "";
        const resolved = themeIcon(icon);
        if (typeof resolved === "object" && "svg" in resolved) {
            return resolved.svg || "";
        }
        return typeof resolved === "string" ? resolved : "";
    };

    const iconSource = computed(() => props.logo ?? props.icon);

    const formatTitle = computed(() => {
        if (!props.title) {
            return "";
        }
        return slugify(props.title);
    });

    const renderedEyebrow = computed(() =>
        props.eyebrow ? md.renderInline(props.eyebrow).trim() : "",
    );
    const renderedTitle = computed(() =>
        props.title ? md.renderInline(props.title).trim() : "",
    );
    const renderedDesc = computed(() =>
        props.desc ? md.renderInline(props.desc).trim() : "",
    );
    const renderedTag = computed(() =>
        props.tag ? md.renderInline(props.tag).trim() : "",
    );
    const renderedNote = computed(() =>
        props.note ? md.renderInline(props.note).trim() : "",
    );

    const rawIcon = computed(() => normalizeIconValue(iconSource.value));
    const svg = computed(() =>
        rawIcon.value && isRawSvg(rawIcon.value) ? rawIcon.value : "",
    );
    const url = computed(() =>
        rawIcon.value && !isRawSvg(rawIcon.value)
            ? resolveAssetUrl(rawIcon.value)
            : "",
    );

    const badgeList = computed<NavBadge[]>(() => {
        const list: NavBadge[] = [];

        if (props.badge) {
            const badge = props.badge;
            list.push(
                typeof badge === "string"
                    ? { text: badge, type: "info" }
                    : { text: badge.text ?? "", type: badge.type ?? "info" },
            );
        }

        if (props.badges) {
            for (const badge of props.badges) {
                list.push(
                    typeof badge === "string"
                        ? { text: badge, type: "info" }
                        : {
                              text: badge.text ?? "",
                              type: badge.type ?? "info",
                          },
                );
            }
        }

        return list.filter((badge) => badge.text);
    });

    const isExternalLink = computed(() =>
        /^(?:https?:)?\/\//.test(props.link) ||
        props.link.startsWith("mailto:") ||
        props.link.startsWith("tel:"),
    );
    const resolvedHref = computed(() =>
        isExternalLink.value ? props.link : withBase(props.link),
    );
    const linkTarget = computed(() =>
        props.target ?? (isExternalLink.value ? "_blank" : "_self"),
    );
    const linkRel = computed(() =>
        linkTarget.value === "_blank" ? "noreferrer" : undefined,
    );
    const linkStyle = computed(() => props.style ?? "default");
    const cardStyle = computed(() => ({
        ...(props.color
            ? { "--nav-link-accent": props.color }
            : undefined),
        ...(props.iconBackground
            ? { "--nav-link-icon-bg": props.iconBackground }
            : undefined),
    }));
</script>

<template>
    <v-card
        v-if="link"
        tag="a"
        :href="resolvedHref"
        :target="linkTarget"
        :rel="linkRel"
        class="m-nav-link"
        variant="flat"
        :ripple="false"
        hover
        :style="cardStyle"
        :data-featured="featured ? 'true' : undefined"
        :data-style="linkStyle"
    >
        <div class="m-nav-link-box">
            <template v-if="!noIcon && (svg || url)">
                <v-avatar
                    v-if="svg"
                    class="m-nav-link-icon"
                    rounded="lg"
                    size="44"
                    color="surface-variant"
                >
                    <span v-html="svg"></span>
                </v-avatar>
                <v-avatar
                    v-else-if="url"
                    class="m-nav-link-icon"
                    rounded="lg"
                    size="44"
                    color="surface-variant"
                >
                    <v-img :src="url" :alt="title" width="26" height="26" />
                </v-avatar>
            </template>
            <div class="m-nav-link-content">
                <div class="m-nav-link-header">
                    <div v-if="badgeList.length" class="m-nav-link-badges">
                        <v-chip
                            v-for="(b, i) in badgeList"
                            :key="i"
                            size="x-small"
                            :color="
                                b.type === 'danger'
                                    ? 'error'
                                    : b.type === 'warning'
                                      ? 'warning'
                                      : b.type === 'tip'
                                        ? 'success'
                                        : 'primary'
                            "
                            variant="tonal"
                            label
                        >
                            {{ b.text }}
                        </v-chip>
                    </div>
                    <span
                        v-if="renderedEyebrow"
                        class="m-nav-link-eyebrow"
                        v-html="renderedEyebrow"
                    ></span>
                    <h5 v-if="title" :id="formatTitle" class="m-nav-link-title">
                        <span v-if="renderedTitle" v-html="renderedTitle"></span>
                        <span v-else>{{ title }}</span>
                    </h5>
                </div>
                <p
                    v-if="renderedDesc"
                    class="m-nav-link-desc"
                    v-html="renderedDesc"
                ></p>
                <div
                    v-if="renderedTag || renderedNote"
                    class="m-nav-link-meta"
                >
                    <v-chip v-if="renderedTag" size="small" variant="outlined" label
                        ><span v-html="renderedTag"></span
                    ></v-chip>
                    <span
                        v-if="renderedNote"
                        class="m-nav-link-note"
                        v-html="renderedNote"
                    ></span>
                </div>
            </div>
        </div>
    </v-card>
</template>

<style scoped>
    .m-nav-link {
        text-decoration: none !important;
        color: inherit !important;
        border: 1px solid var(--vp-c-divider) !important;
        background: var(--vp-c-bg-soft) !important;
        border-radius: 14px !important;
        transition:
            border-color 0.2s ease,
            box-shadow 0.2s ease,
            transform 0.2s ease !important;
    }

    .m-nav-link:hover {
        border-color: color-mix(in srgb, var(--vp-c-brand-1) 32%, var(--vp-c-divider)) !important;
        box-shadow: 0 4px 16px rgba(15, 23, 42, 0.06) !important;
        transform: translateY(-1px);
    }

    .dark .m-nav-link {
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2) !important;
    }

    .dark .m-nav-link:hover {
        box-shadow: 0 6px 20px rgba(0, 0, 0, 0.3) !important;
    }

    .m-nav-link :deep(.v-card__overlay) {
        display: none;
    }

    .m-nav-link-icon :deep(svg) {
        width: 26px;
        height: 26px;
        fill: currentColor;
    }

    .m-nav-link-icon :deep(img) {
        object-fit: contain;
    }
</style>
