package org.tinygroup.template.rumtime;

import org.tinygroup.template.TemplateException;

/**
 * Created by luoguo on 2014/6/5.
 */
public interface Operator {
     Object operation(Object... parameter) throws TemplateException;

     String getOperation();

    int getParameterCount();
}
