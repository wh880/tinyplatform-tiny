package org.tinygroup.template.rumtime.impl.operator;

/**
 * Created by luoguo on 2014/6/5.
 */
public class XorOperator extends AbstractOperator {


    protected Object operation(Object left, Object right) {
        if (isType(left,"Byte")) {
            return (Byte) left ^ (Byte) right;
        }
        if (isType(left,"Character")) {
            return (Character) left ^ (Character) right;
        }
        if (isType(left,"Integer")) {
            return (Integer) left ^ (Integer) right;
        }

        throw new UnsupportedOperationException(left.getClass().getName() + "^" + right.getClass().getName());
    }



    public String getOperation() {
        return "^";
    }

}
