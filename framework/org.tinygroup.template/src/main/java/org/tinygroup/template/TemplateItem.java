package org.tinygroup.template;

/**
 * 表达式
 * Created by luoguo on 2014/5/12.
 */
public interface TemplateItem<T> {
    T execute(TemplateContext templateContext);
}