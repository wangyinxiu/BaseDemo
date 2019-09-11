package com.xiu.core.utils;

import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.xiu.core.app.core.CoreActivity;

import java.lang.reflect.Field;

public class UIUtil {

    private DisplayMetrics dm;
    private CoreActivity activity;
    private static final UIUtil ourInstance = new UIUtil();

    public static final String CALC_STATUS_BAR = "status_bar_height";
    public static final String CALC_NAVIGATION_BAR = "navigation_bar_height";

    public static UIUtil getInstance() {
        return ourInstance;
    }

    private UIUtil() {
    }


    public void init(CoreActivity activity) {
        this.activity = activity;
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
            height = activity.getResources().getDimensionPixelSize(Integer.parseInt(field.get(obj).toString()));
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
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        return (int) (dm.density * value + 0.5f);
    }

    public int sp2px(float value) {
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        return (int) (dm.scaledDensity * value + 0.5f);
    }

    public static void immersionStatusBar(Window window){
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View decor = window.getDecorView();
        decor.setBackgroundColor(Color.TRANSPARENT);
        int ui = decor.getSystemUiVisibility();
        ui |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ui |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        decor.setSystemUiVisibility(ui);
    }

}
