package org.tinygroup.database.util;

import org.tinygroup.commons.tools.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangwy11342 on 2016/6/7.
 */
public class SqlUtil {
    /**
     *去除首尾的特殊字符
     * @param str        要处理的字符串
     * @param stripChars 要除去的字符
     * @return
     */
    public static String trim(String str, String stripChars){
        Pattern pattern = Pattern.compile(stripChars);
        Matcher matcher = pattern.matcher(str);
        return matcher.replaceAll("");
    }


}
