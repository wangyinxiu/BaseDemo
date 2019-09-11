package com.xiu.core.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.xiu.core.R;
import com.xiu.core.compat.ViewCache;

public abstract class CoreDialog extends Dialog {

    private ViewCache viewCache;

    public CoreDialog(@NonNull Context context) {
        this(context, R.style.Dialog);
    }

    public CoreDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setDialogGravity();
        View view = LayoutInflater.from(getContext()).inflate(setLayoutId(), null, false);
        viewCache = new ViewCache(view);
        setContentView(view);
    }


    public abstract int setLayoutId();


    private void setDialogGravity() {
        Window window = this.getWindow();
        if (isBottom()) {
            window.setGravity(Gravity.BOTTOM);
        } else {
            window.setGravity(Gravity.CENTER);
        }
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
    }


    public ViewCache getViewCache() {
        return viewCache;
    }

    /**
     * 是否在底部
     *
     * @return
     */

    public boolean isBottom() {
        return false;
    }

    /**
     * 是否允许back
     *
     * @return
     */
    public boolean enableHardKeyBack() {
        return true;
    }

    /**
     * 是否允许home
     *
     * @return
     */
    public boolean enableHardKeyHome() {
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return enableHardKeyBack();
            case KeyEvent.KEYCODE_HOME:
                return enableHardKeyHome();
            default:
                return super.onKeyDown(keyCode, event);
        }

    }
}
