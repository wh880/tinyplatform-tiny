package org.tinygroup.template.rumtime.impl.operator;

import org.tinygroup.template.rumtime.U;

/**
 * Created by luoguo on 2014/6/5.
 */
public class AndLogicOperator extends DoubleOperator {


    protected Object operation(Object left, Object right) {
        return U.b(left)&& U.b(right);
    }


    public String getOperation() {
        return "&&";
    }

}
