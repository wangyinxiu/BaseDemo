package com.xiu.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import java.lang.reflect.Field;

public class UIUtil {

    private DisplayMetrics dm;
    private Context context;
    private static final UIUtil ourInstance = new UIUtil();

    public static final String CALC_STATUS_BAR = "status_bar_height";
    public static final String CALC_NAVIGATION_BAR = "navigation_bar_height";

    public static UIUtil getInstance() {
        return ourInstance;
    }

    private UIUtil() {
    }


    public void init(Context context) {
        this.context = context;
    }


    public int getStatusBarHeight() {
        return calcBarHeight(CALC_STATUS_BAR);
    }

    public int getNavigationBarHeight() {
        return calcBarHeight(CALC_NAVIGATION_BAR);
    }

    private int calcBarHeight(String type) {
        int height = 0;
        try {
            Class<?> cls = Class.forName("com.android.internal.R$dimen");
            Object obj = cls.newInstance();
            Field field = cls.getField(type);
            height = context.getResources().getDimensionPixelSize(Integer.parseInt(field.get(obj).toString()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return height;
    }

    public int dp2px(float value) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return (int) (dm.density * value + 0.5f);
    }

    public int sp2px(float value) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return (int) (dm.scaledDensity * value + 0.5f);
    }



}
