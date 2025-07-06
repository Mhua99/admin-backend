package com.mhua.adminbackend.utils;

import java.util.HashMap;
import java.util.Map;

public class BaseContext {

    // 使用 Map 来存储多个值
    public static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    /**
     * 设置值
     */
    public static void set(String key, Object value) {
        if (threadLocal.get() == null) {
            threadLocal.set(new HashMap<>());
        }
        threadLocal.get().put(key, value);
    }

    /**
     * 获取值
     */
    public static <T> T get(String key) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            return null;
        }
        return (T) map.get(key);
    }

    /**
     * 获取整个 Map
     */
    public static Map<String, Object> getAll() {
        return threadLocal.get();
    }

    /**
     * 移除所有值
     */
    public static void remove() {
        threadLocal.remove();
    }
}
