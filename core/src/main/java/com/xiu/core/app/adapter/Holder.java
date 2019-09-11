package com.xiu.core.app.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xiu.core.compat.ViewCache;

public class Holder extends RecyclerView.ViewHolder {

    private ViewCache cache;

    public Holder(@NonNull View itemView) {
        super(itemView);
        cache = new ViewCache(itemView);
    }

    public ViewCache getViewCache() {
        return cache;
    }
}
