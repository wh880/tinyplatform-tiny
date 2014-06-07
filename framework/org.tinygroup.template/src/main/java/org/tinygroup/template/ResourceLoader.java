package org.tinygroup.template;

/**
 * 模板来源
 * Created by luoguo on 2014/6/6.
 */
public interface ResourceLoader<T> {
    Template load(T resource);
}
