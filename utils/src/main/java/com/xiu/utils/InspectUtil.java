package com.xiu.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InspectUtil {
    public static boolean isListEmpty(List list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isListNotEmpty(List list) {
        return !isListEmpty(list);
    }

    public static boolean isStringEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static boolean isStringNotEmpty(String s) {
        return !isStringEmpty(s);
    }

    public static boolean isObjEmpty(Object obj) {
        return obj == null;
    }

    public static boolean isObjNotEmpty(Object obj) {
        return !isObjEmpty(obj);
    }

    public static boolean isPhone(String str) {
        if (str != null && str.length() == 11) {
            Pattern pattern = Pattern.compile("^1[3456789]\\d{9}$");
            Matcher matcher = pattern.matcher(str);
            return matcher.matches();
        }
        return false;
    }

    public static boolean isChinese(String str) {
        if (isStringNotEmpty(str)) {
            Pattern pattern = Pattern.compile("[^\u4E00-\u9FA5]");
            Matcher matcher = pattern.matcher(str);
            return matcher.matches();
        }
        return false;
    }

}
