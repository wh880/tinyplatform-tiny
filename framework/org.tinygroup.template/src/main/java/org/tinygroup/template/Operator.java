package org.tinygroup.template;

/**
 * Created by luoguo on 2014/6/5.
 */
public interface Operator {
    public Object operation(Object... parameter) throws JetTemplateException;

    public String getOperation();
}
