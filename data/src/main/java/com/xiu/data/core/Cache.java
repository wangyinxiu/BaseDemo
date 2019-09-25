package com.xiu.data.core;

import android.content.Context;
import android.content.SharedPreferences;


public class Cache {

    private static final String PER_NAME_USER = "base_xiu_preferences_user";
    private static final String PER_NAME_APP = "base_xiu_preferences_app";
    private Context context;

    public Cache(Context context) {
        this.context = context;
    }


    public <T> boolean put(String key, T value) {
        return putValue(PER_NAME_USER, key, value);
    }

    public <T> T get(String key, T defaultValue) {
        return getValue(PER_NAME_USER, key, defaultValue);
    }

    public <T> boolean putApp(String key, T value) {
        return putValue(PER_NAME_APP, key, value);
    }

    public <T> T getApp(String key, T defaultValue) {
        return getValue(PER_NAME_APP, key, defaultValue);
    }


    private <T> boolean putValue(String name, String key, T value) {
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

    private <T> T getValue(String name, String key, T defaultValue) {
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


    private SharedPreferences getPreferences(String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor getEditor(String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE).edit();
    }

    public boolean clearUserCache() {
        return getEditor(PER_NAME_USER).clear().commit();
    }

    public boolean clearAppCache() {
        return getEditor(PER_NAME_APP).clear().commit();
    }


    public interface Key {
        String KEY_TOKEN = "key_token";
    }

}
