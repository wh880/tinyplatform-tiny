package org.tinygroup.template;

import java.io.Reader;

/**
 * 模板来源
 * Created by luoguo on 2014/6/6.
 */
public interface TemplateResource {
    String getPath();

    Reader getReader();
}
