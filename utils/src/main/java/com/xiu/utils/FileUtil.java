package com.xiu.utils;

import android.graphics.Bitmap;
import android.os.Environment;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {


    public static boolean mkdirs(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return file.mkdir();
        }
        return true;
    }

    public static boolean exists(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static File getRootFile() {
        File systemRoot = getExternalStorageDirectory();
        if (!systemRoot.canWrite()) {
            systemRoot = getDataDirectory();
        }
        File root = new File(systemRoot, BuildConfig.APPLICATION_ID);
        if (!root.exists()) {
            root.mkdirs();
        }
        return root;
    }

    public static String getFileName(String path) {
        int index = path.lastIndexOf('/');
        return path.substring(index + 1);
    }

    public static String getRootPath() {
        return getRootFile().getAbsolutePath();
    }

    private static File getDataDirectory() {
        return Environment.getDataDirectory();
    }


    public static File getImageCache() {
        return new File(getRootPath() + File.separator + "imageCache");
    }

    private static File getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory();
    }

    public static void saveBytes(byte[] bytes, String path) {
        String fileName = getFileName(path);
        String parent = path.substring(0, path.lastIndexOf("/"));
        saveBytes(bytes, parent, fileName);
    }

    public static void saveBytes(byte[] bytes, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath, fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean saveBitmap(Bitmap bitmap, File file) {
        if (bitmap == null)
            return false;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file, false);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static boolean saveBitmap(Bitmap bitmap, String absPath) {
        return saveBitmap(bitmap, new File(absPath));
    }


    public static void deleteDir(String path) {
        File dir = new File(path);
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                deleteDir(path);
            } else {
                file.delete();
            }
        }
    }

    public static void clearImageCache() {
        deleteDir(getImageCache().getAbsolutePath());
    }


}
