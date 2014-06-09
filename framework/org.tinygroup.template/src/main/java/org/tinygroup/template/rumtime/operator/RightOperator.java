package org.tinygroup.template.rumtime.operator;

/**
 * Created by luoguo on 2014/6/8.
 */
public class RightOperator extends SingleOperator {


    public String getOperation() {
        return ">>";
    }



    protected Object operation(Object var) {
        return var;
    }
}
