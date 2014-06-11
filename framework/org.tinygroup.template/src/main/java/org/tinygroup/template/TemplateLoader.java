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

    boolean isModified(String path);

    /**
     * 返回模板对象，如果不存在则返回null
     *
     * @param path
     * @return
     */
    Template getTemplate(String path) throws TemplateException;

    /**
     * 获取资源对应的文本
     *
     * @param path
     * @return
     */
    String getResourceContent(String path, String encode) throws TemplateException;

    /**
     * 添加模板对象
     *
     * @param template
     * @return
     */
    TemplateLoader putTemplate(String path, Template template);

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

    Map<String, Template> getTemplateMap();
}
