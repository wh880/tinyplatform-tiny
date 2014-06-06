package org.tinygroup.template;

import java.io.IOException;
import java.io.Writer;

/**
 * 宏，就是个方法
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
     * 进行渲染
     *
     * @param writer
     * @param context
     * @throws IOException
     */
    void render(Writer writer, TemplateContext context) throws TemplateException;
}
