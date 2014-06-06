package org.tinygroup.template.rumtime;

import org.tinygroup.template.TemplateException;

/**
 * Created by luoguo on 2014/6/5.
 */
public interface Operator {
    public Object operation(Object... parameter) throws TemplateException;

    public String getOperation();
}
