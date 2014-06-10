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
    Template getTemplate(String path) throws TemplateException;

    /**
     * 获取其它的资源
     *
     * @param path
     * @return
     */
    T getResource(String path);

    /**
     * 添加模板对象
     *
     * @param template
     * @return
     */
    TemplateLoader putTemplate(T key, Template template);

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

    ClassLoader getTemplateEngineClassLoader();

    void setTemplateEngineClassLoader(ClassLoader classLoader);

    /**
     * 获取流程引擎
     *
     * @return
     */
    TemplateEngine getTemplateEngine();

    Map<T, Template> getTemplateMap();
}
