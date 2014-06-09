package org.tinygroup.template;

/**
 * 函数扩展
 * Created by luoguo on 2014/6/9.
 */
public interface TemplateFunction {
    /**
     * 返回函数名
     *
     * @return
     */
    String getName();

    /**
     * 设置模板引擎
     *
     * @param templateEngine
     */
    void setTemplateEngine(TemplateEngine templateEngine);

    /**
     * 执行函数体
     *
     * @param context
     * @param parameters
     * @return
     */
    Object execute(TemplateContext context, Object... parameters) throws TemplateException;
}
