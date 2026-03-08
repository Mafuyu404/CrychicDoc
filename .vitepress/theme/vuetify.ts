import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";

import "vuetify/styles";
import "@mdi/font/css/materialdesignicons.css";

export default createVuetify({
    ssr: true,
    components,
    directives,
    theme: {
        defaultTheme: "vitepressLight",
        themes: {
            vitepressLight: {
                dark: false,
                colors: {
                    background: "#ffffff",
                    surface: "#ffffff",
                    "surface-variant": "#f6f6f7",
                    primary: "#3451b2",
                    "on-background": "#213547",
                    "on-surface": "#213547",
                    "on-surface-variant": "#213547",
                    "on-primary": "#ffffff",
                },
            },
            vitepressDark: {
                dark: true,
                colors: {
                    background: "#1b1b1f",
                    surface: "#1b1b1f",
                    "surface-variant": "#2e2e32",
                    primary: "#a8b1ff",
                    "on-background": "rgba(255, 255, 245, 0.86)",
                    "on-surface": "rgba(255, 255, 245, 0.86)",
                    "on-surface-variant": "rgba(255, 255, 245, 0.86)",
                    "on-primary": "#1b1b1f",
                },
            },
        },
    },
});
