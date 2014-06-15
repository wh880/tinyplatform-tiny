package org.tinygroup.template;

/**
 * 模板函数扩展
 * Created by luoguo on 2014/6/9.
 */
public interface TemplateFunction {
    /**
     * 绑定某种类上，使之成为这些类型的成员函数
     *
     * @return
     */
    String getBindingTypes();

    /**
     * 返回函数名，如果有多个名字，则用逗号分隔
     *
     * @return
     */
    String getNames();

    /**
     * 设置模板引擎
     *
     * @param templateEngine
     */
    void setTemplateEngine(TemplateEngine templateEngine);

    /**
     * 返回模板引擎
     * @return
     */
    TemplateEngine getTemplateEngine();
    /**
     * 执行函数体
     *
     * @param parameters
     * @return
     */
    Object execute(TemplateContext context, Object... parameters) throws TemplateException;
}
