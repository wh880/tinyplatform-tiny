package org.tinygroup.template.rumtime.operator;

import org.tinygroup.template.TemplateException;

/**
 * 补数
 * Created by luoguo on 2014/6/8.
 */
public class ComplementOperator extends SingleOperator {


    public String getOperation() {
        return "l~";
    }



    protected Object operation(Object var) throws TemplateException {
        if (isType(var,"java.lang.Byte")) {
            return ~((Byte) var);
        }
        if (isType(var,"java.lang.Integer")) {
            return ~((Integer) var);
        }
        if (isType(var,"Long")) {
            return ~((Long) var);
        }
        throw getUnsupportedOperationException(var);
    }

}
