package com.mhua.adminbackend.utils;

public class NumberUtils {

    /**
     * 检查是为整数，并且大于0，否则返回默认值
     * @param input
     * @param defaultValue
     * @return
     */
    public static int checkInteger(Object input, int defaultValue) {
        // 处理null情况
        if (input == null) return defaultValue;

        try {
            // 字符串类型处理
            if (input instanceof String) {
                String str = ((String) input).trim();
                if (str.contains(".")) {
                    return defaultValue; // 规则3：小数字符串返回1
                }
                int num = Integer.parseInt(str);
                return num < 0 ? defaultValue : num; // 规则4：负数返回1
            }

            // 数字类型处理
            if (input instanceof Number) {
                double value = ((Number) input).doubleValue();
                if (value < 0) return defaultValue; // 规则4
                return value % 1 == 0 ? (int) value : defaultValue;
            }

        } catch (NumberFormatException e) {
            // 字符串转换失败情况
            return defaultValue;
        }
        return defaultValue;
    }
}
