package org.tinygroup.template;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * 新需求
 Sept(626135288)  21:43:50
 map = { 'parama': '1', 'paramb': '2' }
 假设 1.参数是不确定的
 #call('func', map)
 假设 2.参数可能是需要多处使用的
 #call('common_1', map)
 #call('common_2', map)
 Sept(626135288)  21:45:14
 #call(hasOpen?'func_open':'func_close', map)
 #call(selector_function(status), map)
 等等情况
 */

/**
 * 模板引擎
 * Created by luoguo on 2014/6/6.
 */
public interface TemplateEngine {
    /**
     * 添加宏
     *
     * @param templateResource
     */
    Template getTemplate(TemplateResource templateResource) throws TemplateException;
    public String getPackageName(String path);
    public String getSimpleClassName(String path);
    public String getClassName(String path);
    /**
     * 添加一个模板
     *
     * @param template
     */
    void addTemplate(Template template);

    /**
     * 返回所有的模板
     *
     * @return
     */
    Map<String, Template> getTemplateMap();

    /**
     * 执行宏
     *
     * @param macroName 要执行的宏名称
     * @param template  当前宏
     */
    void renderMacro(String macroName, Template template, TemplateContext context, Writer writer) throws IOException, TemplateException;


    /**
     * 根据路径渲染一个模板文件
     *
     * @param path
     * @param context
     * @param writer
     */
    void renderTemplate(String path, TemplateContext context, Writer writer) throws TemplateException;

    /**
     * 直接渲染一个模板
     * @param template
     * @param context
     * @param writer
     * @throws TemplateException
     */
    void renderTemplate(Template template, TemplateContext context, Writer writer) throws TemplateException;

    /**
     * 根据宏名查找要调用的宏
     *
     * @param macroName
     * @param template
     * @return
     * @throws TemplateException
     */
    Macro findMacro(String macroName, Template template) throws TemplateException;
}
