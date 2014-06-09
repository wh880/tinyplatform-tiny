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
public interface TemplateEngine extends TemplateContextOperator {
    TemplateEngine setEncode(String encode);

    String getEncode();

    /**
     * 添加类型加载器
     *
     * @param templateLoader
     */
    void addTemplateLoader(TemplateLoader templateLoader);

    /**
     * 返回指定类型的加载器
     *
     * @return
     */
    TemplateLoader getTemplateLoader(String type) throws TemplateException;

    /**
     * 返回默认加载器
     * @return
     * @throws TemplateException
     */
    TemplateLoader getDefaultTemplateLoader() throws TemplateException;
    /**
     * 返回所有的 Loader
     *
     * @return
     */
    Map<String, TemplateLoader> getTemplateLoaderMap();

    /**
     * 渲染宏
     *
     * @param macroName 要执行的宏名称
     * @param template  调用宏的模板
     * @param context   上下文
     * @param writer    输出器
     */
    void renderMacro(String macroName, Template template, TemplateContext context, Writer writer) throws IOException, TemplateException;

    /**
     * 直接渲染指定的模板
     *
     * @param macro    要执行的宏
     * @param template 调用宏的模板
     * @param context  上下文
     * @param writer   输出器
     * @throws IOException
     * @throws TemplateException
     */
    void renderMacro(Macro macro, Template template, TemplateContext context, Writer writer) throws IOException, TemplateException;


    /**
     * 根据路径渲染一个模板文件
     *
     * @param path    模板对应的路径
     * @param context 上下文
     * @param writer  输出器
     */
    void renderTemplate(String path, TemplateContext context, Writer writer) throws TemplateException;

    void renderTemplate(String path) throws TemplateException;
    void renderTemplate(Template template) throws TemplateException;

    /**
     * 直接渲染一个模板
     *
     * @param template 要渲染的模板
     * @param context  上下文
     * @param writer   输出器
     * @throws TemplateException
     */
    void renderTemplate(Template template, TemplateContext context, Writer writer) throws TemplateException;

    /**
     * 根据宏名查找要调用的宏
     *
     * @param macroName
     * @param template
     * @param $context
     * @return
     * @throws TemplateException
     */
    Macro findMacro(String macroName, Template template, TemplateContext $context) throws TemplateException;
}
