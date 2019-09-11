package com.xiu.core.app.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.xiu.core.R;

public abstract class AppToastDialog extends CoreDialog {


    public AppToastDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        getViewCache().getView(R.id.dialog_btn).setOnClickListener(v -> dismiss());
        setTipText(setTitle());
    }

    @Override
    public int setLayoutId() {
        return R.layout.dialog_tips;
    }


    public AppToastDialog setTipText(String text) {
        if (TextUtils.isEmpty(text)) {
            throw new RuntimeException("show tip dialog , but tip is null");
        }
        getViewCache().setTextSafe(R.id.dialog_tip, text);
        return this;
    }

    public AppToastDialog setBtnText(String text) {
        if (!TextUtils.isEmpty(text)) {
            getViewCache().setTextSafe(R.id.dialog_btn, text);
        }
        return this;
    }

    public abstract String setTitle();


}
