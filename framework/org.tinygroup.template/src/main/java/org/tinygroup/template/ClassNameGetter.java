package org.tinygroup.template;

/**
 * Created by luoguo on 2014/6/9.
 */
public interface ClassNameGetter {
    String getPackageName(String path);

    String getSimpleClassName(String path);

    String getClassName(String path);

}
