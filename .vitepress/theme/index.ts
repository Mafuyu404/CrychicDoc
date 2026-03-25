import type { Theme } from "vitepress";
import { inBrowser, useData, useRoute, useRouter } from "vitepress";
import DefaultTheme from "vitepress/theme-without-fonts";
import { h, type Ref, watch } from "vue";
import "./styles/index.css";
import "virtual:group-icons.css";
import "markdown-it-multiple-choice/style.css";
import {
	NolebaseEnhancedReadabilitiesMenu,
	NolebaseEnhancedReadabilitiesScreenMenu,
} from "@nolebase/vitepress-plugin-enhanced-readabilities/client";
import vuetify from "./vuetify";
import "@nolebase/vitepress-plugin-highlight-targeted-heading/client/style.css";

import { getProjectInfo, isFeatureEnabled } from "@config/project-config";
import { useDirectoryLandingRouteSync } from "@utils/sidebar/runtime";
import {
	comment,
	PageTags,
	ResponsibleEditor,
} from "@utils/vitepress/componentRegistry/contentRegistry";
import {
	Animation,
	Buttons,
	NotFound,
	Preview,
} from "@utils/vitepress/componentRegistry/uiRegistry";
import {
	applyThemePageStyles,
	getThemeRuntime,
	installThemeSiteBootstraps,
} from "@utils/vitepress/runtime/theme";
import utils from "../utils";
import VPBreadcrumb from "./components/navigation/Breadcrumb/VPBreadcrumb.vue";
import Footer from "./components/navigation/Footer.vue";
import { enhanceThemeApp } from "./enhanceThemeApp";

type VuetifyThemeController = typeof vuetify.theme & {
	change?: (themeName: string) => void;
	global?: {
		name: {
			value: string;
		};
	};
};

function syncVuetifyTheme(isDark: boolean) {
	const themeName = isDark ? "vitepressDark" : "vitepressLight";
	const themeController = vuetify.theme as VuetifyThemeController;

	if (typeof themeController.change === "function") {
		themeController.change(themeName);
		return;
	}

	if (themeController.global) {
		themeController.global.name.value = themeName;
	}
}

export default {
	extends: DefaultTheme,
	Layout: () => {
		const props: Record<string, unknown> = {};
		const { frontmatter } = useData();

		if (frontmatter.value?.layoutClass) {
			props.class = frontmatter.value.layoutClass;
		}

		return h(Animation, null, {
			slot: () =>
				h(DefaultTheme.Layout, props, {
					"aside-outline-after": () => null,
					"doc-after": () => [h(Buttons), h(comment)],
					"doc-footer-before": () => h(ResponsibleEditor),
					"layout-bottom": () => h(Footer),
					"not-found": () => [h(NotFound)],
					"nav-bar-content-after": () => h(NolebaseEnhancedReadabilitiesMenu),
					"nav-screen-content-after": () =>
						h(NolebaseEnhancedReadabilitiesScreenMenu),
					"doc-before": () => [h(VPBreadcrumb), h(Preview), h(PageTags)],
				}),
		});
	},

	enhanceApp(ctx) {
		enhanceThemeApp(ctx);
	},

	setup() {
		const route = useRoute();
		const router = useRouter();
		const { isDark, frontmatter } = useData();
		const { effectiveDark } = getThemeRuntime(isDark);
		const projectInfo = getProjectInfo();

		watch(
			effectiveDark,
			(dark) => {
				if (inBrowser) {
					syncVuetifyTheme(dark);
				}
			},
			{ immediate: true },
		);

		applyThemePageStyles(
			route,
			frontmatter as unknown as Ref<Record<string, unknown>>,
			effectiveDark,
		);
		useDirectoryLandingRouteSync(route, router);
		installThemeSiteBootstraps({
			route,
			projectInfo,
			mermaidEnabled: isFeatureEnabled("mermaid"),
			initSiteStats: () => {
				utils.vitepress.initBusuanzi();
			},
		});
	},
} satisfies Theme;
