package org.tinygroup.template;

import java.io.Writer;
import java.util.Map;

/**
 * 布局对象
 * Created by luoguo on 2014/6/12.
 */
public interface Layout {
    /**
     * 返回宏的内容
     *
     * @return
     */
    Map<String, Macro> getMacroMap();

    /**
     * 进行渲染
     *
     * @param $writer
     */
    void render(TemplateContext $context, Writer $writer) throws TemplateException;

    /**
     * 返回宏对应的路径
     *
     * @return
     */
    String getPath();

    /**
     * 设置对应的模板引擎
     *
     * @param templateEngine
     */
    void setTemplateEngine(TemplateEngine templateEngine);

    /**
     * 返回模板引擎
     * @return
     */
    TemplateEngine getTemplateEngine();
}
