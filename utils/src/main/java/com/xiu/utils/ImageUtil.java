package com.xiu.utils;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

public class ImageUtil {



    public static void load(String url, ImageView view) {
        Picasso.get().load(url)
//                .placeholder()
//                .error()
                .into(view);
    }

    public static void load(File file, ImageView view) {
        Picasso.get().load(file)
//                .placeholder()
//                .error()
                .into(view);
    }

    public static void load(int resId, ImageView view) {
        Picasso.get().load(resId)
//                .placeholder()
//                .error()
                .into(view);

    }





}
