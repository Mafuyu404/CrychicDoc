<script setup lang="ts">
// @i18n
import { ref, computed, watch, onMounted, nextTick } from "vue";
import { useData } from "vitepress";
import { defineAsyncComponent } from "vue";
import { useSafeI18n } from "../../../utils/i18n/locale";

// Async import for vue-echarts to avoid SSR issues
const VChart = defineAsyncComponent(async () => {
        const { default: VChart } = await import("vue-echarts");
        const { use } = await import("echarts/core");
        const { LineChart } = await import("echarts/charts");
        const {
            TitleComponent,
            TooltipComponent,
            LegendComponent,
            GridComponent,
        } = await import("echarts/components");
        const { CanvasRenderer } = await import("echarts/renderers");

        use([
            LineChart,
            TitleComponent,
            TooltipComponent,
            LegendComponent,
            GridComponent,
            CanvasRenderer,
        ]);

        return VChart;
    });

    const { t } = useSafeI18n("minecraft-damage-chart", {
        incomingDamage: "Incoming Damage",
        armorToughness: "Armor Toughness",
        minDamage: "Min Damage",
        maxDamage: "Max Damage",
        actualDamage: "Actual Damage",
        damageReduction: "Damage Reduction (%)",
        armorPoints: "Armor Points",
        maxArmorPoints: "Max Armor Points",
        javaEdition: "Java Edition",
        bedrockEdition: "Bedrock Edition",
    });

    const props = defineProps({
        mode: {
            type: String,
            default: "interactive",
            validator: (value: string) =>
                ["interactive", "static"].includes(value),
        },
        incomingDamage: { type: Number, default: 201 },
        armorToughness: { type: Number, default: 150 },
        minDamage: { type: Number, default: 0 },
        maxDamage: { type: Number, default: 200 },
        maxArmorPoints: { type: Number, default: 50 },
        isJavaEdition: { type: Boolean, default: true },
    });

    const { isDark, lang } = useData();

    const incomingDamageRef = ref(props.incomingDamage);
    const armorToughnessRef = ref(props.armorToughness);
    const minDamageRef = ref(props.minDamage);
    const maxDamageRef = ref(props.maxDamage);
    const maxArmorPointsRef = ref(props.maxArmorPoints);
    const isJavaEditionRef = ref(props.isJavaEdition);

    const debugInfo = ref("");
    const chartRef = ref(null);
    const isChartReady = ref(false);

    /**
     * Calculate damage based on armor points, toughness, and edition
     */
    const calculateDamage = (
        armor: number,
        toughness: number,
        damage: number,
        isJava: boolean
    ) => {
        if (isJava) {
            const defensePoints = Math.min(
                20,
                Math.max(armor / 5, armor - damage / (2 + toughness / 4))
            );
            return damage * (1 - defensePoints / 25);
        } else {
            return damage * (1 - Math.min(20, armor) * 0.04);
        }
    };

    /**
     * Generate chart options for ECharts
     */
    const chartOptions = computed(() => {
        const labels: number[] = [];
        const actualDamage: number[] = [];
        const damageReduction: number[] = [];

        for (let armor = 0; armor <= maxArmorPointsRef.value; armor += 1) {
            labels.push(armor);
            const damage = calculateDamage(
                armor,
                armorToughnessRef.value,
                incomingDamageRef.value,
                isJavaEditionRef.value
            );
            const clampedDamage = Math.max(
                minDamageRef.value,
                Math.min(maxDamageRef.value, damage)
            );
            actualDamage.push(Number(clampedDamage.toFixed(2)));
            damageReduction.push(
                Number(
                    (
                        (1 - clampedDamage / incomingDamageRef.value) *
                        100
                    ).toFixed(2)
                )
            );
        }

        const textColor = isDark.value ? "#ffffff" : "#363636";
        const gridColor = isDark.value
            ? "rgba(255, 255, 255, 0.1)"
            : "rgba(0, 0, 0, 0.1)";

        return {
            tooltip: {
                trigger: "axis",
                axisPointer: {
                    type: "cross",
                },
                backgroundColor: isDark.value ? "#1a1a1a" : "#ffffff",
                borderColor: isDark.value ? "#333" : "#ccc",
                textStyle: {
                    color: textColor,
                },
            },
            legend: {
                data: [
                                            t.actualDamage,
                        t.damageReduction,
                ],
                textStyle: {
                    color: textColor,
                },
            },
            grid: {
                left: "3%",
                right: "4%",
                bottom: "3%",
                containLabel: true,
            },
            xAxis: {
                type: "category",
                boundaryGap: false,
                data: labels,
                                        name: t.armorPoints,
                nameTextStyle: {
                    color: textColor,
                },
                axisLabel: {
                    color: textColor,
                },
                axisLine: {
                    lineStyle: {
                        color: gridColor,
                    },
                },
                splitLine: {
                    lineStyle: {
                        color: gridColor,
                    },
                },
            },
            yAxis: [
                {
                    type: "value",
                    name: t.actualDamage,
                    position: "left",
                    nameTextStyle: {
                        color: textColor,
                    },
                    axisLabel: {
                        color: textColor,
                    },
                    axisLine: {
                        lineStyle: {
                            color: gridColor,
                        },
                    },
                    splitLine: {
                        lineStyle: {
                            color: gridColor,
                        },
                    },
                },
                {
                    type: "value",
                    name: t.damageReduction,
                    position: "right",
                    min: 0,
                    max: 100,
                    nameTextStyle: {
                        color: textColor,
                    },
                    axisLabel: {
                        formatter: "{value}%",
                        color: textColor,
                    },
                    axisLine: {
                        lineStyle: {
                            color: gridColor,
                        },
                    },
                    splitLine: {
                        show: false,
                    },
                },
            ],
            series: [
                {
                    name: t.actualDamage,
                    type: "line",
                    yAxisIndex: 0,
                    data: actualDamage,
                    lineStyle: {
                        color: isDark.value ? "#ff6384" : "#ff3860",
                        width: 2,
                    },
                    itemStyle: {
                        color: isDark.value ? "#ff6384" : "#ff3860",
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
                                    color: isDark.value
                                        ? "rgba(255, 99, 132, 0.3)"
                                        : "rgba(255, 56, 96, 0.3)",
                                },
                                {
                                    offset: 1,
                                    color: isDark.value
                                        ? "rgba(255, 99, 132, 0.1)"
                                        : "rgba(255, 56, 96, 0.1)",
                                },
                            ],
                        },
                    },
                },
                {
                    name: t.damageReduction,
                    type: "line",
                    yAxisIndex: 1,
                    data: damageReduction,
                    lineStyle: {
                        color: isDark.value ? "#36a2eb" : "#3273dc",
                        width: 2,
                    },
                    itemStyle: {
                        color: isDark.value ? "#36a2eb" : "#3273dc",
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
                                    color: isDark.value
                                        ? "rgba(54, 162, 235, 0.3)"
                                        : "rgba(50, 115, 220, 0.3)",
                                },
                                {
                                    offset: 1,
                                    color: isDark.value
                                        ? "rgba(54, 162, 235, 0.1)"
                                        : "rgba(50, 115, 220, 0.1)",
                                },
                            ],
                        },
                    },
                },
            ],
        };
    });

    const handleInput = (event: Event, key: string) => {
        if (props.mode === "static") return;
        const value = parseFloat((event.target as HTMLInputElement).value);
        if (!isNaN(value)) {
            switch (key) {
                case "incomingDamage":
                    incomingDamageRef.value = value;
                    break;
                case "armorToughness":
                    armorToughnessRef.value = value;
                    break;
                case "minDamage":
                    minDamageRef.value = value;
                    break;
                case "maxDamage":
                    maxDamageRef.value = value;
                    break;
                case "maxArmorPoints":
                    maxArmorPointsRef.value = value;
                    break;
            }
            debugInfo.value = `${key} changed to ${value}`;
        }
    };

    const toggleEdition = () => {
        if (props.mode === "static") return;
        isJavaEditionRef.value = !isJavaEditionRef.value;
    };

    // Wait for component to mount and DOM to be ready
    onMounted(async () => {
        await nextTick();
        // Small delay to ensure DOM is fully rendered
        setTimeout(() => {
            isChartReady.value = true;
        }, 100);
    });
</script>

<template>
    <div class="minecraft-damage-chart" :class="{ 'dark-mode': isDark }">
        <div v-if="mode === 'interactive'" class="input-container">
            <div
                class="input-group"
                v-for="key in [
                    'incomingDamage',
                    'armorToughness',
                    'minDamage',
                    'maxDamage',
                    'maxArmorPoints',
                ]"
                :key="key"
            >
                <label :for="key">{{ t[key] }}</label>
                <input
                    :id="key"
                    type="number"
                    :value="
                        key === 'incomingDamage'
                            ? incomingDamageRef
                            : key === 'armorToughness'
                            ? armorToughnessRef
                            : key === 'minDamage'
                            ? minDamageRef
                            : key === 'maxDamage'
                            ? maxDamageRef
                            : maxArmorPointsRef
                    "
                    @input="(event) => handleInput(event, key)"
                />
            </div>
            <div class="input-group">
                <label>&nbsp;</label>
                <button
                    @click="toggleEdition"
                    :class="{
                        java: isJavaEditionRef,
                        bedrock: !isJavaEditionRef,
                    }"
                >
                    {{
                        isJavaEditionRef
                            ? t.javaEdition
                            : t.bedrockEdition
                    }}
                </button>
            </div>
        </div>
        <div class="chart-container" ref="chartRef">
            <ClientOnly>
                <v-chart
                    v-if="isChartReady"
                    :option="chartOptions"
                    :autoresize="true"
                    class="chart"
                />
                <div v-else class="chart-loading">
                    Loading chart...
                </div>
            </ClientOnly>
        </div>
        <div v-if="mode === 'interactive'" class="debug-info">
            {{ debugInfo }}
        </div>
    </div>
</template>

<style scoped>
    .minecraft-damage-chart {
        font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto,
            Helvetica, Arial, sans-serif;
        background-color: var(--vp-c-bg);
        color: var(--vp-c-text-1);
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    .input-container {
        display: flex;
        flex-wrap: wrap;
        gap: 15px;
        margin-bottom: 20px;
    }

    .input-group {
        flex: 1 1 calc(33.333% - 15px);
        display: flex;
        flex-direction: column;
    }

    label {
        display: block;
        margin-bottom: 5px;
        font-weight: bold;
        color: var(--vp-c-text-1);
    }

    input,
    button {
        width: 100%;
        padding: 8px;
        border: 1px solid var(--vp-c-divider);
        border-radius: 4px;
        background-color: var(--vp-c-bg-alt);
        color: var(--vp-c-text-1);
        font-size: 14px;
        transition: all 0.3s ease;
    }

    input:focus,
    button:focus {
        outline: none;
        border-color: var(--vp-c-brand);
        box-shadow: 0 0 0 2px var(--vp-c-brand-light);
    }

    button {
        cursor: pointer;
        text-transform: uppercase;
        letter-spacing: 0.5px;
        background-color: var(--chart-button-bg);
        color: var(--chart-button-color);
    }

    button:hover {
        background-color: var(--chart-button-hover-bg);
    }

    .chart-container {
        height: 400px;
        min-height: 300px;
        width: 100%;
        background-color: var(--vp-c-bg-alt);
        border-radius: 4px;
        padding: 10px;
        position: relative;
    }

    .chart-loading {
        display: flex;
        align-items: center;
        justify-content: center;
        height: 100%;
        color: var(--vp-c-text-2);
        font-style: italic;
    }

    .chart {
        width: 100%;
        height: 100%;
    }

    .debug-info {
        margin-top: 10px;
        font-size: 12px;
        color: var(--vp-c-text-2);
    }

    @media (max-width: 768px) {
        .input-group {
            flex: 1 1 100%;
        }

        .chart-container {
            height: 300px;
            min-height: 250px;
        }

        .minecraft-damage-chart {
            padding: 10px;
        }

        .input-container {
            gap: 10px;
        }
    }
</style>
