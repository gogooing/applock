package com.sanqiu.loro.applocktest.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则验证帮助类
 * Created by loro on 2018/4/8.
 */

public class RegularUtil {


    public final static String WEB_PATTERN = "^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)?(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\\\/])+$";//网址正则表达式
    public final static String HEAD_PATTERN = "<head>(.*?)</head>";
    public final static String ICON_PATTERN = "<link(.*?)rel=\"apple-touch-icon(.*?)/>";
    public final static String SUB_START = "href=\"";
    public final static String SUB_END = "\"/>";

    /**
     * 验证是否为网址
     *
     * @param code
     * @return
     */
    public static boolean isWebPattern(String code) {
        if (TextUtils.isEmpty(code)) {
            return false;
        }
        Pattern pattern = Pattern.compile(WEB_PATTERN);
        Matcher m = pattern.matcher(code);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证是否为网址
     *
     * @param code
     * @return
     */
    public static String getHtmlIcon(String code) {
        if (TextUtils.isEmpty(code)) {
            return "";
        }
        String head = matcher(code, HEAD_PATTERN);
        if (!TextUtils.isEmpty(head)) {
            String icon = matcher(head, ICON_PATTERN);
            if (!TextUtils.isEmpty(icon)) {
                int start = icon.lastIndexOf(SUB_START);
                start = start + SUB_START.length();
                int end = icon.lastIndexOf(SUB_END);
                icon = icon.substring(start, end);
                return icon;
            }
        }
        return "";
    }

    public static String matcher(String code, String regex) {
        if (TextUtils.isEmpty(code)) {
            return "";
        }
        //把正则表达式编译成一个正则对象
        Pattern p = Pattern.compile(regex);
        //获取匹配器
        Matcher m = p.matcher(code);
        if (m.find()) {
            String str = m.group();
            return str;
        }
        return "";
    }
}
