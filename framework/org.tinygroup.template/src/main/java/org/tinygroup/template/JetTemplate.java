package org.tinygroup.template;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by luoguo on 2014/6/4.
 */
public interface JetTemplate {
    /**
     * 进行渲染
     *
     * @param writer
     */
    void render(Writer writer,JetContext context) throws IOException;

    String getTemplatePath();
}
