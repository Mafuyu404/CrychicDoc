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

    /** Shown in tooltip on hover. Uses `note` prop (opt-in hover-only info). */
    const tooltipContent = computed(() => renderedNote.value || "");
</script>

<template>
    <v-tooltip
        v-if="link"
        :text="tooltipContent"
        :disabled="!tooltipContent"
        location="top"
        :max-width="300"
        transition="fade-transition"
        content-class="m-nav-tooltip__content"
    >
        <template #activator="{ props: tp }">
            <a
                class="m-nav-link"
                :class="[
                    linkStyle !== 'default' ? `m-nav-link--${linkStyle}` : '',
                    { 'm-nav-link--featured': featured },
                ]"
                :href="resolvedHref"
                :target="linkTarget"
                :rel="linkRel"
                :style="cardStyle"
                v-bind="tp"
            >
                <div class="m-nav-link-box">
                    <!-- Icon: plain div, no forced clipping -->
                    <template v-if="!noIcon && (svg || url)">
                        <div
                            v-if="svg"
                            class="m-nav-link-icon"
                            v-html="svg"
                        ></div>
                        <div v-else-if="url" class="m-nav-link-icon">
                            <img
                                :src="url"
                                :alt="title"
                                onerror="this.parentElement.style.display='none'"
                            />
                        </div>
                    </template>

                    <!-- Content -->
                    <div class="m-nav-link-content">
                        <div class="m-nav-link-header">
                            <span
                                v-if="renderedEyebrow"
                                class="m-nav-link-eyebrow"
                                v-html="renderedEyebrow"
                            ></span>
                            <h5
                                v-if="title"
                                :id="formatTitle"
                                class="m-nav-link-title"
                            >
                                <span
                                    v-if="renderedTitle"
                                    v-html="renderedTitle"
                                ></span>
                                <span v-else>{{ title }}</span>
                            </h5>
                            <!-- Multi-badge row -->
                            <div v-if="badgeList.length" class="m-nav-link-badges">
                                <span
                                    v-for="(b, i) in badgeList"
                                    :key="i"
                                    class="m-nav-badge"
                                    :data-type="b.type || 'info'"
                                >{{ b.text }}</span>
                            </div>
                        </div>

                        <p
                            v-if="renderedDesc"
                            class="m-nav-link-desc"
                            v-html="renderedDesc"
                        ></p>

                        <!-- Tag footer (note goes to tooltip) -->
                        <div v-if="renderedTag" class="m-nav-link-meta">
                            <span class="m-nav-tag" v-html="renderedTag"></span>
                        </div>
                    </div>
                </div>
            </a>
        </template>
    </v-tooltip>
</template>

<style scoped>
    /* Tooltip surface */
    :deep(.m-nav-tooltip__content) {
        background: rgba(10, 16, 30, 0.93) !important;
        color: rgba(255, 255, 255, 0.88) !important;
        border-radius: 8px !important;
        padding: 8px 13px !important;
        font-size: 12.5px !important;
        font-weight: 450 !important;
        line-height: 1.65 !important;
        letter-spacing: 0.1px !important;
        box-shadow: 0 6px 24px rgba(0, 0, 0, 0.32) !important;
        backdrop-filter: blur(12px) !important;
        border: 1px solid rgba(255, 255, 255, 0.07) !important;
    }

    /* Accent border on hover (CSS var override) */
    .m-nav-link:hover {
        border-color: var(
            --nav-link-accent,
            color-mix(in srgb, var(--vp-c-brand-1) 35%, var(--vp-c-divider))
        ) !important;
    }
</style>
