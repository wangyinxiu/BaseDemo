package com.xiu.core.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.xiu.core.app.core.AppManager;

public class SPCache {

    private static final String PER_NAME_USER = "base_xiu_preferences_user";
    private static final String PER_NAME_APP = "base_xiu_preferences_app";

    private SPCache() {
    }

    public static <T> boolean put(String key, T value) {
        return putValue(PER_NAME_USER, key, value);
    }

    public static <T> T get(String key, T defaultValue) {
        return getValue(PER_NAME_USER, key, defaultValue);
    }

    public static <T> boolean putApp(String key, T value) {
        return putValue(PER_NAME_APP, key, value);
    }

    public static <T> T getApp(String key, T defaultValue) {
        return getValue(PER_NAME_APP, key, defaultValue);
    }


    private static <T> boolean putValue(String name, String key, T value) {
        SharedPreferences.Editor editor = getEditor(name);
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            if (value != null) {
                editor.putString(key, value.toString());
            } else {
                editor.putString(key, "");
            }
        }
        return editor.commit();
    }

    private static <T> T getValue(String name, String key, T defaultValue) {
        SharedPreferences sp = getPreferences(name);
        if (defaultValue instanceof String) {
            return (T) sp.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return (T) ((Integer) sp.getInt(key, (Integer) defaultValue));
        } else if (defaultValue instanceof Boolean) {
            return (T) ((Boolean) sp.getBoolean(key, (Boolean) defaultValue));
        } else if (defaultValue instanceof Float) {
            return (T) ((Float) sp.getFloat(key, (Float) defaultValue));
        } else if (defaultValue instanceof Long) {
            return (T) ((Long) sp.getLong(key, (Long) defaultValue));
        }
        return defaultValue;
    }


    private static SharedPreferences getPreferences(String name) {
        return AppManager.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(String name) {
        return AppManager.getContext().getSharedPreferences(name, Context.MODE_PRIVATE).edit();
    }

    public static boolean clearUserCache() {
        return getEditor(PER_NAME_USER).clear().commit();
    }

    public static boolean clearAppCache() {
        return getEditor(PER_NAME_APP).clear().commit();
    }


    public interface Key {
        String KEY_TOKEN = "key_token";
    }

}
