<script setup lang="ts">
    import { computed } from "vue";
    import { withBase, useData } from "vitepress";
    import { slugify } from "@mdit-vue/shared";
    import MarkdownIt from "markdown-it";
    import {
        resolveThemeValueByMode,
        getThemeRuntime,
    } from "@utils/vitepress/runtime/theme";

    import MNavLink from "./MNavLink.vue";
    import type { NavData, NavIcon, NavLink, NavThemeIcon } from "@utils/content/navLinkType";

    const { isDark } = useData();
    const { effectiveDark } = getThemeRuntime(isDark);
    const md = new MarkdownIt({ html: true, linkify: true });

    const props = defineProps<{
        groups?: NavData[];
        title?: string;
        noIcon?: boolean;
        items?: NavLink[];
        description?: string;
        columns?: number;
        icon?: NavLink["icon"];
        eyebrow?: string;
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

    const normalizedGroups = computed<NavData[]>(() => {
        if (Array.isArray(props.groups) && props.groups.length > 0) {
            return props.groups;
        }
        if (props.title && Array.isArray(props.items)) {
            return [
                {
                    title: props.title,
                    description: props.description,
                    columns: props.columns,
                    icon: props.icon,
                    eyebrow: props.eyebrow,
                    items: props.items,
                },
            ];
        }
        return [];
    });
    const hasPageHeader = computed(
        () => Array.isArray(props.groups) && props.groups.length > 0 && Boolean(props.title),
    );

    const renderInline = (value?: string) =>
        value ? md.renderInline(value).trim() : "";

    const getHeadingId = (title: string) => slugify(title || "");
    const getRenderedTitle = (title: string) => renderInline(title);
    const getRenderedDescription = (description?: string) => renderInline(description);
    const getRenderedEyebrow = (eyebrow?: string) => renderInline(eyebrow);
    const getGroupSvg = (group: NavData) => {
        const value = normalizeIconValue(group.icon);
        return value && isRawSvg(value) ? value : "";
    };
    const getGroupUrl = (group: NavData) => {
        const value = normalizeIconValue(group.icon);
        return value && !isRawSvg(value) ? resolveAssetUrl(value) : "";
    };
    const getGridStyle = (group: NavData) => {
        if (!group.columns) return undefined;
        return {
            "grid-template-columns": `repeat(${group.columns}, minmax(0, 1fr))`,
        };
    };
</script>

<template>
    <div class="m-nav-links-page">
        <header v-if="hasPageHeader" class="m-nav-page-heading">
            <div class="m-nav-page-copy">
                <span
                    v-if="getRenderedEyebrow(eyebrow)"
                    class="m-nav-page-eyebrow"
                    v-html="getRenderedEyebrow(eyebrow)"
                ></span>
                <h1 v-if="title" class="m-nav-page-title">
                    <span v-if="getRenderedTitle(title)" v-html="getRenderedTitle(title)"></span>
                    <span v-else>{{ title }}</span>
                </h1>
                <p
                    v-if="getRenderedDescription(description)"
                    class="m-nav-page-desc"
                    v-html="getRenderedDescription(description)"
                ></p>
            </div>
        </header>
        <section v-for="(group, index) in normalizedGroups" :key="index" class="m-nav-group">
            <div class="m-nav-group-heading">
                <span v-if="getGroupSvg(group) || getGroupUrl(group)" class="m-nav-group-icon">
                    <span v-if="getGroupSvg(group)" v-html="getGroupSvg(group)"></span>
                    <img
                        v-else-if="getGroupUrl(group)"
                        :src="getGroupUrl(group)"
                        :alt="group.title"
                        onerror="this.parentElement.style.display='none'"
                    />
                </span>
                <div class="m-nav-group-copy">
                    <span
                        v-if="getRenderedEyebrow(group.eyebrow)"
                        class="m-nav-group-eyebrow"
                        v-html="getRenderedEyebrow(group.eyebrow)"
                    ></span>
                    <h2 v-if="group.title" :id="getHeadingId(group.title)" tabindex="-1">
                        <span v-if="getRenderedTitle(group.title)" v-html="getRenderedTitle(group.title)"></span>
                        <span v-else>{{ group.title }}</span>
                        <a
                            class="header-anchor"
                            :href="`#${getHeadingId(group.title)}`"
                            aria-hidden="true"
                        ></a>
                    </h2>
                    <p
                        v-if="getRenderedDescription(group.description)"
                        class="m-nav-group-desc"
                        v-html="getRenderedDescription(group.description)"
                    ></p>
                </div>
            </div>
            <div class="m-nav-links" :style="getGridStyle(group)">
                <MNavLink
                    v-for="(item, itemIndex) in group.items"
                    :key="itemIndex"
                    :noIcon="noIcon"
                    v-bind="item"
                />
            </div>
        </section>
    </div>
</template>
