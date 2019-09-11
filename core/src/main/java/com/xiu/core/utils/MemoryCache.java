package com.xiu.core.utils;

import android.util.SparseArray;

public class MemoryCache {

    private static SparseArray<Object> cache;

    public static <T> void put(int key, T value) {

        if (cache == null) {
            synchronized (MemoryCache.class) {
                if (cache == null) {
                    cache = new SparseArray<>();
                }
            }
        }
        cache.put(key, value);
    }

    public static <T> T get(int key, T defalutValue) {
        return (T) cache.get(key, null);
    }

}
