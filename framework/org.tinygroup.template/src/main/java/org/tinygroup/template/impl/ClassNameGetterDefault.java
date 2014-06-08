package org.tinygroup.template.impl;

import org.tinygroup.template.ClassNameGetter;

/**
 * Created by luoguo on 2014/6/9.
 */
public class ClassNameGetterDefault implements ClassNameGetter {
    @Override
    public String getPackageName(String path) {
        String className = getClassName(path);
        return className.substring(0, className.lastIndexOf('.'));
    }


    @Override
    public String getSimpleClassName(String path) {
        String className = getClassName(path);
        return className.substring(className.lastIndexOf('.') + 1);
    }

    @Override
    public String getClassName(String path) {
        String name = path;
        name = convertGoodStylePath(path);
        if (name.startsWith("/")) {//去掉前置"/"
            name = name.substring(1);
        }
        int pos = path.indexOf('.');
        if (pos >= 0) {
            name = name.substring(0, pos - 1);//去掉文件扩展名
        }
        name = name + "Template";
        return name.replaceAll("/", ".");
    }

    private String convertGoodStylePath(String path) {
        StringBuffer sb = new StringBuffer(200);

        for (char c : path.toCharArray()) {
            if (c == '/' || c == '.') {
                sb.append(c);
            } else if (c >= '0' && c <= '9') {
                sb.append(c);
            } else if (c >= 'a' && c <= 'z') {
                sb.append(c);
            } else if (c >= 'A' && c <= 'Z') {
                sb.append(c);
            } else {
                sb.append("_");
            }
        }
        return sb.toString();
    }

}
