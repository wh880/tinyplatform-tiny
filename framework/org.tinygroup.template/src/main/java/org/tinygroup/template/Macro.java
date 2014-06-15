package org.tinygroup.template;

import java.io.Writer;

/**
 * 宏，就可以理解为一个对方法，它有输入参数，但没有输出参数，而是直接针对Writer对象进行内容输出
 * Created by luoguo on 2014/6/6.
 */
public interface Macro {
    /**
     * 返回宏的名字
     *
     * @return
     */
    String getName();

    /**
     * 返回宏的参数名称
     *
     * @return
     */
    String[] getParameterNames();

    /**
     * 设置模板引擎
     * @param templateEngine
     */
    void setTemplateEngine(TemplateEngine templateEngine);

    /**
     * 获得模板引擎
     * @return
     */
    TemplateEngine getTemplateEngine();

    /**
     * 进行渲染
     *
     * @param context
     * @param writer
     */
    void render(Template template, TemplateContext context, Writer writer) throws TemplateException;
}
