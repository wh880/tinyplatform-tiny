package org.tinygroup.template.loader;

import org.tinygroup.template.impl.ClassName;

/**
 * 类名称获取器
 * Created by luoguo on 2014/6/9.
 */
public interface ClassNameGetter {

    ClassName getClassName(String path);

}
