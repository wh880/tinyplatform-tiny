package org.tinygroup.template.rumtime.impl.operator;

/**
 * Created by luoguo on 2014/6/8.
 */
public class LeftAddOperator extends SingleOperator {

    @Override
    public String getOperation() {
        return "l+";
    }


    @Override
    protected Object operation(Object var) {
        return var;
    }
}
