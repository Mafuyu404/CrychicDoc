import { onMounted, onUnmounted } from "vue";
import { getThemeLifecycleManager } from "./ThemeLifecycleManager";
import { ThemeStateStore } from "./ThemeStateStore";
import type { RefLike } from "@utils/vitepress/runtime/refLike";

export function getThemeRuntime(isDark: RefLike<boolean>) {
    if (typeof window === "undefined") {
        const store = new ThemeStateStore(isDark);
        return {
            effectiveDark: store.effectiveDark,
            themeReady: store.themeReady,
            version: store.version,
        };
    }

    const lifecycleManager = getThemeLifecycleManager();
    const state = lifecycleManager.getState(isDark);

    onMounted(() => {
        lifecycleManager.mount(isDark);
    });

    onUnmounted(() => {
        lifecycleManager.unmount();
    });

    return state;
}
