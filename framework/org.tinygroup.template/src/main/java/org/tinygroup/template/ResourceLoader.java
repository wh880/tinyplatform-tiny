package org.tinygroup.template;

import java.util.Map;

/**
 * 用于载入模板
 * Created by luoguo on 2014/6/9.
 */
public interface ResourceLoader<T> {

    void setCheckModified(boolean checkModified);
    /**
     * 确定某个路径对应的文件是否被修改
     *
     * @param path
     * @return
     */
    boolean isModified(String path);

    /**
     * 返回路径
     *
     * @param templatePath
     * @return
     * @throws TemplateException
     */
    String getLayoutPath(String templatePath);

    /**
     * 返回模板对象，如果不存在则返回null
     *
     * @param path
     * @return
     */
    Template getTemplate(String path) throws TemplateException;

    /**
     * 返回而已对象
     *
     * @param path
     * @return
     * @throws TemplateException
     */
    Template getLayout(String path) throws TemplateException;

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
    ResourceLoader addTemplate(Template template);

    /**
     * 添加布局对象
     *
     * @param layout
     * @return
     */
    ResourceLoader addLayout(Template layout);

    /**
     * 创建并注册模板
     *
     * @param templateMaterial
     * @return
     */
    Template createTemplate(T templateMaterial) throws TemplateException;

    /**
     * 创建并注册布局
     *
     * @param templateMaterial
     * @return
     * @throws TemplateException
     */
    Template createLayout(T templateMaterial) throws TemplateException;

    /**
     * 返回注入模板引擎
     *
     * @param templateEngine
     */
    void setTemplateEngine(TemplateEngine templateEngine);

    ClassLoader getClassLoader();

    void setClassLoader(ClassLoader classLoader);

    /**
     * 获取流程引擎
     *
     * @return
     */
    TemplateEngine getTemplateEngine();

    /**
     * 获取模板仓库
     *
     * @return
     */
    Map<String, Template> getTemplateMap();

    /**
     * 获取布局仓库
     *
     * @return
     */
    Map<String, Template> getLayoutMap();

    /**
     * 返回模板文件的扩展
     *
     * @return
     */
    String getTemplateExtName();

    /**
     * 返回布局文件的扩展名
     *
     * @return
     */
    String getLayoutExtName();
}
