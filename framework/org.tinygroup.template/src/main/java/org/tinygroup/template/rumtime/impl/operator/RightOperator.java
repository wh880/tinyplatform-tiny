package org.tinygroup.template.rumtime.impl.operator;

/**
 * Created by luoguo on 2014/6/8.
 */
public class RightOperator extends SingleOperator {

    @Override
    public String getOperation() {
        return ">>";
    }


    @Override
    protected Object operation(Object var) {
        return var;
    }
}