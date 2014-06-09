package org.tinygroup.template.rumtime.impl.operator;

import org.tinygroup.template.TemplateException;
import org.tinygroup.template.rumtime.O;

/**
 * Created by luoguo on 2014/6/8.
 */
public class LeftPlusPlusOperator extends SingleOperator {


    public String getOperation() {
        return "l++";
    }



    protected Object operation(Object var) throws TemplateException {
        return O.e("+", var, 1);
    }
}
