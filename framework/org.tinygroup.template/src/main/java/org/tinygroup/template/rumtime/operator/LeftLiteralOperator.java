package org.tinygroup.template.rumtime.operator;

import org.tinygroup.template.TemplateException;

/**
 * Created by luoguo on 2014/6/8.
 */
public class LeftLiteralOperator extends SingleOperator {


    public String getOperation() {
        return "l!";
    }


    protected Object operation(Object var) throws TemplateException {
        if (isType(var, Boolean.class)) {
            return !(Boolean) var;
        }
        throw getUnsupportedOperationException(var);
    }

}
