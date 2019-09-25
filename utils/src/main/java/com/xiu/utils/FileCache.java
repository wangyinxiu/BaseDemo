package com.xiu.utils;

import java.io.File;

class FileCache {

    private static final String FILE_CACHE_PATH = FileUtil.getRootPath() + File.separator +
            "fileCache";
    private static final String IMAGE_CAHCE_PATH = FileUtil.getRootPath() + File.separator +
            "imageCache";

    private static final long MAX_IMAGE_SIZE = 500 * 1024 * 1024;




}
