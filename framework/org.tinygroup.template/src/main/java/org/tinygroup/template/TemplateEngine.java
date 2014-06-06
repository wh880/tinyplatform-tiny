package org.tinygroup.template;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * 模板引擎
 * Created by luoguo on 2014/6/6.
 */
public interface TemplateEngine {
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
    void executeMacro(String macroName, Template template, Writer writer, TemplateContext context) throws IOException, TemplateException;

    /**
     * 渲染一个模板文件
     *
     * @param path
     * @param context
     * @param writer
     */
    void renderTemplate(String path, TemplateContext context, Writer writer) throws TemplateException;
}
