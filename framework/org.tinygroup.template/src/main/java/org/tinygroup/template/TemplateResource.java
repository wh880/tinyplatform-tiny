package org.tinygroup.template;

/**
 * Created by luoguo on 2014/6/7.
 */
public interface TemplateResource {
    /**
     * 返回时间戳
     *
     * @return
     */
    long getTimestamp();

    /**
     * 返回路径
     *
     * @return
     */
    String getPath();

    /**
     * 返回内容
     *
     * @return
     */
    String getContent() throws TemplateException;
}
