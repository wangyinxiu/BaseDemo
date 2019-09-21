package com.xiu.core.manager;

import android.app.Dialog;
import android.content.DialogInterface;

import com.xiu.core.app.core.CoreActivity;
import com.xiu.core.app.dialog.CoreDialog;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CoreDialogManager implements DialogInterface.OnDismissListener {


    private Map<Class, CoreDialog> manager;


    public void showDialog(CoreDialog dialog) {
        dialog.setOnDismissListener(this::onDismiss);
        if (manager == null) {
            manager = new HashMap<>();
        }
        dismiss(dialog.getClass());
        manager.put(dialog.getClass(), dialog);
        dialog.show();
    }

    public void showAsSingle(CoreDialog dialog) {
        for (Class cls : manager.keySet()) {
            dismiss(cls);
        }

    }

    private void dismiss(Class key) {
        CoreDialog dialog = manager.get(key);
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
            manager.remove(dialog);
        }
    }


    @Override
    public void onDismiss(DialogInterface dialog) {

    }
}
