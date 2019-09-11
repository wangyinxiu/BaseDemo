package com.xiu.core.app.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.xiu.core.compat.ViewCache;

public class CoreFragment extends Fragment {

    private ViewCache viewCache;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewCache = new ViewCache(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewCache.clear();
        viewCache = null;
    }

    public ViewCache getViewCache() {
        return viewCache;
    }
}
