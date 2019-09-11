package com.xiu.core.compat;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;

public class ViewCache {

    private SparseArray<View> cache;

    private View itemView;


    public ViewCache(View itemView) {
        this.itemView = itemView;
        cache = new SparseArray<>();
    }

    public <T extends View> T getView(int id) {
        View view = cache.get(id);
        if (null == view) {
            view = itemView.findViewById(id);
            cache.put(id, view);
        }
        return (T) view;
    }

    public TextView getTextView(int id) {
        return getView(id);
    }

    public void setTextSafe(int id, String text) {
        TextView textView = getView(id);
        if (textView != null) {
            textView.setText(text == null ? "" : text);
        }
    }

    public ImageView getImageView(int id) {
        return getView(id);
    }

    public void setImageUrl(int id, String url) {
        Picasso.get().load(url)
//                .placeholder()
//                .error()
                .into(getImageView(id));
    }

    public void setImagePath(int id, String path) {
        Picasso.get().load(new File(path))
//                .placeholder()
//                .error()
                .into(getImageView(id));
    }

    public void setOnClickListener(View.OnClickListener onClickListener, int... ids) {
        for (int id : ids) {
            View view = getView(id);
            if (view != null) {
                view.setOnClickListener(onClickListener);
            }
        }
    }

    public ViewGroup getRootView() {
        return (ViewGroup) itemView;
    }

    public void clear() {
        cache.clear();
        cache = null;
        itemView = null;
    }


}
