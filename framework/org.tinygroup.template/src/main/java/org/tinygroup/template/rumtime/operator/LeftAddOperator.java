package org.tinygroup.template.rumtime.operator;

/**
 * Created by luoguo on 2014/6/8.
 */
public class LeftAddOperator extends SingleOperator {


    public String getOperation() {
        return "l+";
    }



    protected Object operation(Object var) {
        return var;
    }
}
