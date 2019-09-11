package com.xiu.core.app.core;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import com.xiu.core.utils.LogUtil;

import java.util.List;
import java.util.Stack;

public class AppManager {
    private static Stack<CoreActivity> activityStack;
    private static AppManager instance;
    private static Context context;

    private AppManager() {

    }

    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }


    public static void init(Context context) {
        if (AppManager.context == null) {
            AppManager.context = context;
        }
    }

    public void addActivity(CoreActivity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    public CoreActivity currentActivity() {
        if (activityStack != null) {
            return activityStack.lastElement();
        }
        return null;
    }

    public synchronized void finishCurrentActivity() {
        finishActivity(activityStack.lastElement());
    }


    public synchronized void finishActivity(CoreActivity activity) {
        if (activity != null) {
            if (activityStack != null && activityStack.contains(activity)) {
                activityStack.remove(activity);
            }
            activity.finish();
            activity = null;
        }
    }

    public synchronized void finishActivity(Class<?> cls) {
        if (null != activityStack && activityStack.size() > 0) {
            for (CoreActivity activity : activityStack) {
                if (null != activity) {
                    if (cls.equals(activity.getClass())) {
                        finishActivity(activity);
                    }
                }
            }
        }
    }

    public synchronized void finishAllActivity() {
        if (null != activityStack && activityStack.size() > 0) {
            for (int i = 0, size = activityStack.size(); i < size; i++) {
                if (null != activityStack.get(i)) {
                    Activity activity = activityStack.get(i);
                    if (null != activity) {
                        if (!activity.isFinishing()) {
                            activity.finish();
                        }
                    }

                }
            }
            activityStack.clear();
        }
    }

    public boolean isHaveActivity() {
        if (null != activityStack && activityStack.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
        }
    }

    public static String getRunningActivityName() {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        LogUtil.i("=============getRunningActivityName==========runningActivity======",
                runningActivity);
        return runningActivity;
    }

    public boolean isAppRunningForeground() {
        if (context == null) {
            return false;
        }
        ActivityManager localActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List localList = localActivityManager.getRunningTasks(1);
        return context.getPackageName().equalsIgnoreCase(((ActivityManager.RunningTaskInfo) localList.get(0)).baseActivity.getPackageName());
    }

    public static Context getContext(){
        return context;
    }


}
