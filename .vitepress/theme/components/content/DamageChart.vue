<script setup lang="ts">
import { computed, onMounted, ref, watch } from "vue";
import { useData } from "vitepress";
import { useSafeI18n } from "@utils/i18n/locale";
import VChart from "./VChart.vue";

type ChartMode = "interactive" | "static";
type NumericFieldKey =
    | "incomingDamage"
    | "armorToughness"
    | "minDamage"
    | "maxDamage"
    | "maxArmorPoints";

interface Props {
    mode?: ChartMode;
    incomingDamage?: number;
    armorToughness?: number;
    minDamage?: number;
    maxDamage?: number;
    maxArmorPoints?: number;
    isJavaEdition?: boolean;
}

const props = withDefaults(defineProps<Props>(), {
    mode: "interactive",
    incomingDamage: 201,
    armorToughness: 150,
    minDamage: 0,
    maxDamage: 200,
    maxArmorPoints: 50,
    isJavaEdition: true,
});

const { t } = useSafeI18n("minecraft-damage-chart", {
    actualDamage: "Actual Damage",
    armorPoints: "Armor Points",
    armorScan: "Armor Scan",
    armorToughness: "Armor Toughness",
    bedrockEdition: "Bedrock",
    bedrockFormulaNote: "Uses armor-only percentage reduction.",
    chartDescription:
        "Compares armor scaling against the final clamped damage and reduction curve.",
    chartEyebrow: "Damage Curve",
    clampRange: "Clamp Range",
    controlsTitle: "Parameters",
    damageReduction: "Damage Reduction (%)",
    editionLabel: "Edition",
    formula: "Formula",
    incomingDamage: "Incoming Damage",
    interactiveHint:
        "Adjust the values below to compare formulas, armor scan range, and clamp limits.",
    javaEdition: "Java",
    javaFormulaNote:
        "Uses armor and armor toughness together when resolving the final reduction.",
    loadingChart: "Loading chart...",
    maxArmorPoints: "Max Armor Points",
    maxDamage: "Max Damage",
    minDamage: "Min Damage",
    tooltipIncomingDamage: "Incoming",
});

const { lang, isDark } = useData();

const incomingDamageRef = ref(props.incomingDamage);
const armorToughnessRef = ref(props.armorToughness);
const minDamageRef = ref(props.minDamage);
const maxDamageRef = ref(props.maxDamage);
const maxArmorPointsRef = ref(props.maxArmorPoints);
const isJavaEditionRef = ref(props.isJavaEdition);
const isMounted = ref(false);

const numericFields: Array<{
    key: NumericFieldKey;
    min: number;
    step: number;
}> = [
    { key: "incomingDamage", min: 0, step: 1 },
    { key: "armorToughness", min: 0, step: 1 },
    { key: "minDamage", min: 0, step: 0.1 },
    { key: "maxDamage", min: 0, step: 0.1 },
    { key: "maxArmorPoints", min: 0, step: 1 },
];

watch(
    () => [
        props.incomingDamage,
        props.armorToughness,
        props.minDamage,
        props.maxDamage,
        props.maxArmorPoints,
        props.isJavaEdition,
    ],
    ([
        incomingDamage,
        armorToughness,
        minDamage,
        maxDamage,
        maxArmorPoints,
        isJavaEdition,
    ]) => {
        incomingDamageRef.value = incomingDamage;
        armorToughnessRef.value = armorToughness;
        minDamageRef.value = minDamage;
        maxDamageRef.value = maxDamage;
        maxArmorPointsRef.value = maxArmorPoints;
        isJavaEditionRef.value = isJavaEdition;
    }
);

onMounted(() => {
    isMounted.value = true;
});

function sanitizeNonNegative(value: number): number {
    if (!Number.isFinite(value)) return 0;
    return Math.max(0, value);
}

function roundToTwo(value: number): number {
    return Number(value.toFixed(2));
}

function getFieldValue(key: NumericFieldKey): number {
    switch (key) {
        case "incomingDamage":
            return incomingDamageRef.value;
        case "armorToughness":
            return armorToughnessRef.value;
        case "minDamage":
            return minDamageRef.value;
        case "maxDamage":
            return maxDamageRef.value;
        case "maxArmorPoints":
            return maxArmorPointsRef.value;
    }
}

function setFieldValue(key: NumericFieldKey, value: number): void {
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
}

function handleNumericInput(event: Event, key: NumericFieldKey): void {
    if (props.mode === "static") return;
    const nextValue = Number.parseFloat(
        (event.target as HTMLInputElement).value
    );
    if (!Number.isFinite(nextValue)) return;
    setFieldValue(key, nextValue);
}

function selectEdition(isJavaEdition: boolean): void {
    if (props.mode === "static") return;
    isJavaEditionRef.value = isJavaEdition;
}

function calculateDamage(
    armor: number,
    toughness: number,
    damage: number,
    isJavaEdition: boolean
): number {
    if (isJavaEdition) {
        const defensePoints = Math.min(
            20,
            Math.max(armor / 5, armor - damage / (2 + toughness / 4))
        );
        return damage * (1 - defensePoints / 25);
    }

    return damage * (1 - Math.min(20, armor) * 0.04);
}

const normalizedState = computed(() => {
    const incomingDamage = sanitizeNonNegative(incomingDamageRef.value);
    const armorToughness = sanitizeNonNegative(armorToughnessRef.value);
    const minDamage = sanitizeNonNegative(minDamageRef.value);
    const maxDamage = sanitizeNonNegative(maxDamageRef.value);

    return {
        incomingDamage,
        armorToughness,
        minDamage: Math.min(minDamage, maxDamage),
        maxDamage: Math.max(minDamage, maxDamage),
        maxArmorPoints: Math.max(
            0,
            Math.floor(sanitizeNonNegative(maxArmorPointsRef.value))
        ),
    };
});

const chartRows = computed(() => {
    const { incomingDamage, armorToughness, minDamage, maxDamage, maxArmorPoints } =
        normalizedState.value;

    return Array.from({ length: maxArmorPoints + 1 }, (_, armor) => {
        const resolvedDamage = calculateDamage(
            armor,
            armorToughness,
            incomingDamage,
            isJavaEditionRef.value
        );
        const actualDamage = Math.max(
            minDamage,
            Math.min(maxDamage, resolvedDamage)
        );
        const damageReduction =
            incomingDamage <= 0
                ? 0
                : (1 - actualDamage / incomingDamage) * 100;

        return {
            armor,
            actualDamage: roundToTwo(actualDamage),
            damageReduction: roundToTwo(damageReduction),
        };
    });
});

function formatNumber(value: number, maximumFractionDigits = 2): string {
    return new Intl.NumberFormat(lang.value || undefined, {
        maximumFractionDigits,
        minimumFractionDigits: 0,
    }).format(value);
}

const editionLabel = computed(() =>
    isJavaEditionRef.value ? t.javaEdition : t.bedrockEdition
);

const editionFormula = computed(() =>
    isJavaEditionRef.value
        ? "damage * (1 - min(20, max(armor / 5, armor - damage / (2 + toughness / 4))) / 25)"
        : "damage * (1 - min(20, armor) * 0.04)"
);

const editionFormulaNote = computed(() =>
    isJavaEditionRef.value ? t.javaFormulaNote : t.bedrockFormulaNote
);

const summaryItems = computed(() => [
    {
        key: "edition",
        label: t.editionLabel,
        value: editionLabel.value,
        accent: "edition",
    },
    {
        key: "incoming",
        label: t.incomingDamage,
        value: formatNumber(normalizedState.value.incomingDamage),
        accent: "neutral",
    },
    {
        key: "toughness",
        label: t.armorToughness,
        value: formatNumber(normalizedState.value.armorToughness),
        accent: "neutral",
    },
    {
        key: "clamp",
        label: t.clampRange,
        value: `${formatNumber(normalizedState.value.minDamage)} - ${formatNumber(
            normalizedState.value.maxDamage
        )}`,
        accent: "neutral",
    },
    {
        key: "scan",
        label: t.armorScan,
        value: `0 - ${formatNumber(normalizedState.value.maxArmorPoints, 0)}`,
        accent: "neutral",
    },
]);

function resolveAxisInterval(pointCount: number): number {
    if (pointCount <= 10) return 0;
    return Math.max(0, Math.ceil(pointCount / 10) - 1);
}

const chartOptions = computed(() => {
    const rows = chartRows.value;
    const interval = resolveAxisInterval(rows.length);
    const { incomingDamage, minDamage, maxDamage } = normalizedState.value;

    const actualLineColor = isDark.value ? "#f2994a" : "#c66a1b";
    const actualAreaTopColor = isDark.value
        ? "rgba(242, 153, 74, 0.28)"
        : "rgba(198, 106, 27, 0.2)";
    const actualAreaBottomColor = isDark.value
        ? "rgba(242, 153, 74, 0.06)"
        : "rgba(198, 106, 27, 0.04)";
    const reductionLineColor = isDark.value ? "#79a6ff" : "#3f6ccf";
    const reductionAreaTopColor = isDark.value
        ? "rgba(121, 166, 255, 0.22)"
        : "rgba(63, 108, 207, 0.14)";
    const reductionAreaBottomColor = isDark.value
        ? "rgba(121, 166, 255, 0.04)"
        : "rgba(63, 108, 207, 0.03)";

    return {
        __m1Palettes: {
            light: [actualLineColor, reductionLineColor],
            dark: [actualLineColor, reductionLineColor],
        },
        grid: {
            top: 58,
            right: 56,
            bottom: 44,
            left: 48,
            containLabel: true,
        },
        legend: {
            top: 10,
            left: 0,
            itemWidth: 10,
            itemHeight: 10,
            textStyle: {
                fontSize: 12,
            },
        },
        tooltip: {
            trigger: "axis",
            axisPointer: {
                type: "cross",
            },
            padding: [10, 12],
            extraCssText: "border-radius: 10px;",
            formatter: (params: Array<{ dataIndex: number }>) => {
                const dataIndex = params?.[0]?.dataIndex ?? 0;
                const row = rows[dataIndex];
                if (!row) return "";

                return [
                    `<strong>${t.armorPoints}: ${formatNumber(row.armor, 0)}</strong>`,
                    `${t.tooltipIncomingDamage}: ${formatNumber(incomingDamage)}`,
                    `${t.actualDamage}: ${formatNumber(row.actualDamage)}`,
                    `${t.damageReduction}: ${formatNumber(
                        row.damageReduction
                    )}%`,
                ].join("<br>");
            },
        },
        xAxis: {
            type: "category",
            boundaryGap: false,
            name: t.armorPoints,
            nameLocation: "middle",
            nameGap: 30,
            axisTick: {
                show: false,
            },
            axisLabel: {
                fontSize: 12,
                interval,
            },
            data: rows.map((row) => row.armor),
        },
        yAxis: [
            {
                type: "value",
                name: t.actualDamage,
                min: minDamage,
                max: maxDamage === minDamage ? maxDamage + 1 : maxDamage,
                splitNumber: 5,
                axisLabel: {
                    formatter: (value: number) => formatNumber(value),
                },
            },
            {
                type: "value",
                name: t.damageReduction,
                min: 0,
                max: 100,
                splitNumber: 5,
                axisLabel: {
                    formatter: (value: number) => `${formatNumber(value, 0)}%`,
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
                smooth: true,
                showSymbol: false,
                symbol: "circle",
                symbolSize: 7,
                emphasis: {
                    focus: "series",
                    scale: true,
                },
                lineStyle: {
                    width: 2.6,
                    color: actualLineColor,
                },
                itemStyle: {
                    color: actualLineColor,
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
                                color: actualAreaTopColor,
                            },
                            {
                                offset: 1,
                                color: actualAreaBottomColor,
                            },
                        ],
                    },
                },
                data: rows.map((row) => row.actualDamage),
            },
            {
                name: t.damageReduction,
                type: "line",
                yAxisIndex: 1,
                smooth: true,
                showSymbol: false,
                symbol: "circle",
                symbolSize: 7,
                emphasis: {
                    focus: "series",
                    scale: true,
                },
                lineStyle: {
                    width: 2.1,
                    type: "dashed",
                    color: reductionLineColor,
                },
                itemStyle: {
                    color: reductionLineColor,
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
                                color: reductionAreaTopColor,
                            },
                            {
                                offset: 1,
                                color: reductionAreaBottomColor,
                            },
                        ],
                    },
                },
                data: rows.map((row) => row.damageReduction),
            },
        ],
    };
});
</script>

<template>
    <section class="damage-chart" :data-mode="mode">
        <header class="damage-chart__header">
            <div class="damage-chart__eyebrow">{{ t.chartEyebrow }}</div>
            <div class="damage-chart__summary">
                <span
                    v-for="item in summaryItems"
                    :key="item.key"
                    class="damage-chart__summary-item"
                    :data-accent="item.accent"
                >
                    <span class="damage-chart__summary-label">{{
                        item.label
                    }}</span>
                    <strong class="damage-chart__summary-value">{{
                        item.value
                    }}</strong>
                </span>
            </div>
            <p class="damage-chart__description">{{ t.chartDescription }}</p>
            <p class="damage-chart__formula-row">
                <span class="damage-chart__formula-label">{{ t.formula }}</span>
                <code class="damage-chart__formula-code">{{
                    editionFormula
                }}</code>
            </p>
            <p class="damage-chart__formula-note">{{ editionFormulaNote }}</p>
        </header>

        <section
            v-if="mode === 'interactive'"
            class="damage-chart__controls"
            :aria-label="t.controlsTitle"
        >
            <div class="damage-chart__controls-head">
                <strong>{{ t.controlsTitle }}</strong>
                <span>{{ t.interactiveHint }}</span>
            </div>
            <div class="damage-chart__fields">
                <label
                    v-for="field in numericFields"
                    :key="field.key"
                    class="damage-chart__field"
                >
                    <span class="damage-chart__field-label">{{
                        t[field.key]
                    }}</span>
                    <input
                        class="damage-chart__input"
                        type="number"
                        :min="field.min"
                        :step="field.step"
                        :value="getFieldValue(field.key)"
                        @input="(event) => handleNumericInput(event, field.key)"
                    />
                </label>

                <div class="damage-chart__field damage-chart__field--edition">
                    <span class="damage-chart__field-label">{{
                        t.editionLabel
                    }}</span>
                    <div
                        class="damage-chart__edition-toggle"
                        role="tablist"
                        :aria-label="t.editionLabel"
                    >
                        <button
                            type="button"
                            class="damage-chart__edition-button"
                            :class="{ 'is-active': isJavaEditionRef }"
                            @click="selectEdition(true)"
                        >
                            {{ t.javaEdition }}
                        </button>
                        <button
                            type="button"
                            class="damage-chart__edition-button"
                            :class="{ 'is-active': !isJavaEditionRef }"
                            @click="selectEdition(false)"
                        >
                            {{ t.bedrockEdition }}
                        </button>
                    </div>
                </div>
            </div>
        </section>

        <div class="damage-chart__plot">
            <div v-if="!isMounted" class="damage-chart__loading">
                {{ t.loadingChart }}
            </div>
            <VChart
                v-else
                :options="chartOptions"
                height="360px"
                class="damage-chart__vchart"
            />
        </div>
    </section>
</template>

<style scoped>
.damage-chart {
    margin: 1.4rem 0;
    border: 1px solid color-mix(in srgb, var(--vp-c-divider) 72%, transparent);
    border-radius: 14px;
    background:
        linear-gradient(
            180deg,
            color-mix(in srgb, var(--vp-c-bg-soft) 76%, var(--vp-c-bg) 24%) 0%,
            var(--vp-c-bg) 100%
        );
    overflow: hidden;
}

.damage-chart__header {
    padding: 1rem 1rem 0.85rem;
    border-bottom: 1px solid
        color-mix(in srgb, var(--vp-c-divider) 62%, transparent);
}

.damage-chart__eyebrow {
    margin-bottom: 0.7rem;
    font-size: 0.72rem;
    font-weight: 700;
    letter-spacing: 0.08em;
    text-transform: uppercase;
    color: var(--vp-c-text-3);
}

.damage-chart__summary {
    display: flex;
    flex-wrap: wrap;
    gap: 0.55rem;
}

.damage-chart__summary-item {
    display: inline-flex;
    align-items: baseline;
    gap: 0.38rem;
    padding: 0.38rem 0.62rem;
    border-radius: 999px;
    border: 1px solid color-mix(in srgb, var(--vp-c-divider) 80%, transparent);
    background: color-mix(in srgb, var(--vp-c-bg) 90%, var(--vp-c-bg-soft) 10%);
    color: var(--vp-c-text-2);
    line-height: 1.2;
}

.damage-chart__summary-item[data-accent="edition"] {
    border-color: color-mix(in srgb, var(--vp-c-brand-1) 22%, var(--vp-c-divider));
    background: color-mix(in srgb, var(--vp-c-brand-1) 10%, var(--vp-c-bg) 90%);
}

.damage-chart__summary-label {
    font-size: 0.72rem;
    color: var(--vp-c-text-3);
}

.damage-chart__summary-value {
    font-size: 0.8rem;
    font-weight: 600;
    color: var(--vp-c-text-1);
}

.damage-chart__description,
.damage-chart__formula-note {
    margin: 0.72rem 0 0;
    font-size: 0.92rem;
    line-height: 1.6;
    color: var(--vp-c-text-2);
}

.damage-chart__formula-row {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: 0.45rem;
    margin: 0.72rem 0 0;
}

.damage-chart__formula-label {
    font-size: 0.78rem;
    font-weight: 600;
    color: var(--vp-c-text-3);
    text-transform: uppercase;
    letter-spacing: 0.05em;
}

.damage-chart__formula-code {
    padding: 0.18rem 0.45rem;
    border-radius: 6px;
    background: color-mix(in srgb, var(--vp-c-default-soft) 82%, transparent);
    font-size: 0.8rem;
    color: var(--vp-c-text-1);
    word-break: break-word;
}

.damage-chart__controls {
    padding: 0.9rem 1rem 1rem;
    border-bottom: 1px solid
        color-mix(in srgb, var(--vp-c-divider) 62%, transparent);
    background: color-mix(in srgb, var(--vp-c-bg) 92%, var(--vp-c-default-soft) 8%);
}

.damage-chart__controls-head {
    display: flex;
    flex-wrap: wrap;
    align-items: baseline;
    gap: 0.55rem 0.8rem;
    margin-bottom: 0.85rem;
}

.damage-chart__controls-head strong {
    font-size: 0.96rem;
    font-weight: 600;
    color: var(--vp-c-text-1);
}

.damage-chart__controls-head span {
    font-size: 0.9rem;
    color: var(--vp-c-text-2);
}

.damage-chart__fields {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(170px, 1fr));
    gap: 0.8rem;
}

.damage-chart__field {
    display: flex;
    flex-direction: column;
    gap: 0.42rem;
}

.damage-chart__field-label {
    font-size: 0.8rem;
    font-weight: 600;
    color: var(--vp-c-text-2);
}

.damage-chart__input {
    width: 100%;
    min-height: 2.5rem;
    padding: 0.58rem 0.7rem;
    border: 1px solid color-mix(in srgb, var(--vp-c-divider) 80%, transparent);
    border-radius: 10px;
    background: color-mix(in srgb, var(--vp-c-bg) 94%, var(--vp-c-bg-soft) 6%);
    color: var(--vp-c-text-1);
    font: inherit;
    transition:
        border-color 0.2s ease,
        background-color 0.2s ease,
        box-shadow 0.2s ease;
}

.damage-chart__input:focus {
    outline: none;
    border-color: color-mix(in srgb, var(--vp-c-brand-1) 54%, var(--vp-c-divider));
    box-shadow: 0 0 0 3px color-mix(in srgb, var(--vp-c-brand-1) 14%, transparent);
}

.damage-chart__field--edition {
    min-width: 0;
}

.damage-chart__edition-toggle {
    display: grid;
    grid-template-columns: repeat(2, minmax(0, 1fr));
    gap: 0.24rem;
    min-height: 2.5rem;
    padding: 0.18rem;
    border: 1px solid color-mix(in srgb, var(--vp-c-divider) 72%, transparent);
    border-radius: 12px;
    background: color-mix(in srgb, var(--vp-c-bg) 92%, var(--vp-c-bg-soft) 8%);
}

.damage-chart__edition-button {
    min-height: 100%;
    padding: 0 0.7rem;
    border: 0;
    border-radius: 9px;
    background: transparent;
    color: var(--vp-c-text-2);
    font: inherit;
    font-size: 0.85rem;
    font-weight: 600;
    cursor: pointer;
    transition:
        background-color 0.18s ease,
        color 0.18s ease,
        box-shadow 0.18s ease;
}

.damage-chart__edition-button:hover {
    background: color-mix(in srgb, var(--vp-c-default-soft) 80%, transparent);
    color: var(--vp-c-text-1);
}

.damage-chart__edition-button.is-active {
    background: color-mix(in srgb, var(--vp-c-brand-1) 14%, var(--vp-c-bg) 86%);
    color: var(--vp-c-text-1);
    box-shadow: inset 0 0 0 1px
        color-mix(in srgb, var(--vp-c-brand-1) 30%, transparent);
}

.damage-chart__plot {
    padding: 0.8rem 0.8rem 0.95rem;
    background:
        linear-gradient(
            180deg,
            color-mix(in srgb, var(--vp-c-bg) 96%, var(--vp-c-default-soft) 4%) 0%,
            color-mix(in srgb, var(--vp-c-bg) 88%, var(--vp-c-bg-soft) 12%) 100%
        );
}

.damage-chart__loading {
    display: flex;
    align-items: center;
    justify-content: center;
    min-height: 360px;
    border-radius: 12px;
    border: 1px dashed color-mix(in srgb, var(--vp-c-divider) 72%, transparent);
    color: var(--vp-c-text-3);
    background:
        linear-gradient(
            135deg,
            color-mix(in srgb, var(--vp-c-bg-soft) 78%, transparent) 0%,
            color-mix(in srgb, var(--vp-c-bg) 96%, transparent) 100%
        );
}

.damage-chart__vchart {
    margin: 0;
}

@media (max-width: 768px) {
    .damage-chart {
        margin: 1.15rem 0;
        border-radius: 12px;
    }

    .damage-chart__header,
    .damage-chart__controls {
        padding-left: 0.85rem;
        padding-right: 0.85rem;
    }

    .damage-chart__plot {
        padding: 0.7rem 0.65rem 0.8rem;
    }

    .damage-chart__formula-row {
        align-items: flex-start;
    }

    .damage-chart__summary-item {
        width: 100%;
        justify-content: space-between;
    }

    .damage-chart__edition-toggle {
        grid-template-columns: 1fr;
    }

    .damage-chart__loading {
        min-height: 300px;
    }
}
</style>
