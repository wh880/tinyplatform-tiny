package org.tinygroup.template.impl;

import org.tinygroup.template.ClassName;
import org.tinygroup.template.ClassNameGetter;

/**
 * Created by luoguo on 2014/6/9.
 */
public class ClassNameGetterDefault implements ClassNameGetter {

    private String convertGoodStylePath(String path) {
        StringBuffer sb = new StringBuffer(200);
        //除了下面的几种情况，全部替换成"_"
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


    public ClassName getClassName(String path) {
        String name = path;
        name = convertGoodStylePath(path);
        //去掉前置"/"
        if (name.startsWith("/")) {
            name = name.substring(1);
        }
        int pos = path.indexOf('.');
        if (pos >= 0) {
            //去掉文件扩展名
            name = name.substring(0, pos - 1);
        }
        name = name + "Template";
        ClassName className = new ClassName();
        String fullClassName = name.replaceAll("/", ".");
        className.setClassName(fullClassName);
        className.setSimpleClassName(fullClassName.substring(fullClassName.lastIndexOf('.') + 1));
        className.setPackageName(fullClassName.substring(0, fullClassName.lastIndexOf('.')));
        return className;
    }
}
