package org.tinygroup.template;

/**
 * 上下文操作接口
 * Created by luoguo on 2014/6/9.
 */
public interface TemplateContextOperator<T> {
    /**
     * 返回上下文
     *
     * @return
     */
    TemplateContext getTemplateContext();

    /**
     * 向上下文放置内容
     *
     * @param key
     * @param value
     * @return
     */
    T put(String key, Object value);

}
