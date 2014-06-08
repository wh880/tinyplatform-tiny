package org.tinygroup.template.rumtime.impl.operator;

import org.tinygroup.template.TemplateException;
import org.tinygroup.template.rumtime.O;

/**
 * Created by luoguo on 2014/6/8.
 */
public class LeftPlusPlusOperator extends SingleOperator {

    @Override
    public String getOperation() {
        return "l++";
    }


    @Override
    protected Object operation(Object var) throws TemplateException {
        return O.e("+", var, 1);
    }
}
