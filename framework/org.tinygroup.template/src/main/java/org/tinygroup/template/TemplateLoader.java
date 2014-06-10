package org.tinygroup.template;

import java.util.Map;

/**
 * 用于载入模板
 * Created by luoguo on 2014/6/9.
 */
public interface TemplateLoader<T> {
    /**
     * 返回模板加载器的类型
     *
     * @return
     */
    String getType();

    /**
     * 返回模板对象，如果不存在则返回null
     *
     * @param path
     * @return
     */
    Template getTemplate(String path) throws Exception;

    /**
     * 添加模板对象
     *
     * @param template
     * @return
     */
    TemplateLoader putTemplate(T key,Template template);

    /**
     * 创建并注册模板
     *
     * @param templateMaterial
     * @return
     */
    Template createTemplate(T templateMaterial) throws TemplateException;

    /**
     * 返回注入模板引擎
     *
     * @param templateEngine
     */
    void setTemplateEngine(TemplateEngine templateEngine);

    /**
     * 获取流程引擎
     *
     * @return
     */
    TemplateEngine getTemplateEngine();

    Map<T,Template>getTemplateMap();
}
