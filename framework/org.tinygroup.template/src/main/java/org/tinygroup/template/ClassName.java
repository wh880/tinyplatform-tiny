package org.tinygroup.template;

/**
 * 用来描述类名的对象
 * Created by luoguo on 2014/6/9.
 */
public final class ClassName {
    private String simpleClassName;
    private String className;
    private String packageName;

    public String getSimpleClassName() {
        return simpleClassName;
    }

    public void setSimpleClassName(String simpleClassName) {
        this.simpleClassName = simpleClassName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
