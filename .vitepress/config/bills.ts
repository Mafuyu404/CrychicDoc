import type { Bill } from "../theme/components/content/bill";

/**
 * @description Bill data configuration
 * @type {Bill[]}
 */
export const bills: Bill[] = [
    {
        date: "2024-01-15",
        "exchanged-amount": 216.35,
        operator: "测试操作员",
        description: "测试交易内容",
        target: "测试目标",
        type: "income"
    }
];

export default bills; 