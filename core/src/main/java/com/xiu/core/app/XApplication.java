package com.xiu.core.app;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.squareup.picasso.Picasso;
import com.xiu.core.BuildConfig;
import com.xiu.core.app.core.AppManager;
import com.xiu.utils.FileUtil;
import com.xiu.utils.LogUtil;

import java.io.File;

public class XApplication extends Application {

    private static final String TAG = "XApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        AppManager.init(this);
        initPicasso();
    }


    private void initPicasso() {
        File dir = new File(Environment.getDownloadCacheDirectory().getAbsolutePath() + File.separator + BuildConfig.APPLICATION_ID);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.memoryCache(new com.squareup.picasso.Cache() {
            @Override
            public Bitmap get(String key) {
                String name = FileUtil.getFileName(key);
                String path = FileUtil.getImageCache().getAbsolutePath() + File.separator + name;
                File file = new File(path);
                if (file.exists()) {
                    return BitmapFactory.decodeFile(path);
                }
                return null;
            }

            @Override
            public void set(String key, Bitmap bitmap) {
                FileUtil.saveBitmap(bitmap, new File(FileUtil.getImageCache(),
                        FileUtil.getFileName(key)));
            }

            @Override
            public int size() {
                long size = FileUtil.getImageCache().length();
                return (int) size;
            }

            @Override
            public int maxSize() {
                return Integer.MAX_VALUE;
            }

            @Override
            public void clear() {
                FileUtil.clearImageCache();
            }

            @Override
            public void clearKeyUri(String keyPrefix) {

            }
        });
        builder.indicatorsEnabled(true);
        builder.loggingEnabled(true);
        Picasso picasso = builder.build();
        Picasso.setSingletonInstance(picasso);
    }


}
