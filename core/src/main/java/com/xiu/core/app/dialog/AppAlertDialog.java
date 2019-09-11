package com.xiu.core.app.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.xiu.core.R;

public class AppAlertDialog extends CoreDialog {


    public AppAlertDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public int setLayoutId() {
        return R.layout.dialog_alert;
    }

    @Override
    public boolean isBottom() {
        return false;
    }


    public AppAlertDialog setTitle(String text) {
        if (text == null) {
            getViewCache().getView(R.id.dialog_title).setVisibility(View.GONE);
        } else {
            getViewCache().getTextView(R.id.toolbar_title).setText(text);
        }
        return this;
    }

    public AppAlertDialog setContent(String text) {
        if (text == null) {
            throw new RuntimeException("alert dialog content is null");
        }
        getViewCache().getTextView(R.id.dialog_content).setText(text);
        return this;
    }

    public AppAlertDialog setNegative(String text, View.OnClickListener onClickListener) {
        TextView negative = getViewCache().getTextView(R.id.dialog_negative);
        if (text != null) {
            negative.setText(text);
        }
        if (onClickListener == null) {
            negative.setOnClickListener(v -> dismiss());
        } else {
            negative.setOnClickListener(onClickListener);
        }
        return this;
    }

    public AppAlertDialog setNegativeTextColor(int color){
        TextView negative = getViewCache().getTextView(R.id.dialog_negative);
        negative.setTextColor(color);
        return this;
    }

    public AppAlertDialog setPositive(String text, View.OnClickListener onClickListener) {
        TextView positive = getViewCache().getTextView(R.id.dialog_positive);
        if (text != null) {
            positive.setText(text);
        }
        if (onClickListener == null) {
            positive.setOnClickListener(v -> dismiss());
        } else {
            positive.setOnClickListener(onClickListener);
        }
        return this;
    }


    public AppAlertDialog setPositiveTextColor(int color){
        TextView positive = getViewCache().getTextView(R.id.dialog_positive);
        positive.setTextColor(color);
        return this;
    }

}
