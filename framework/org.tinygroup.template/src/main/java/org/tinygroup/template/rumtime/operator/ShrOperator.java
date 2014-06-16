package org.tinygroup.template.rumtime.operator;

/**
 * Created by luoguo on 2014/6/5.
 */
public class ShrOperator extends TwoOperator {


    protected Object operation(Object left, Object right) {
        if (isType(left,Byte.class)) {
            return (Byte) left >> (Byte) right;
        }
        if (isType(left,Character.class)) {
            return (Character) left >> (Character) right;
        }
        if (isType(left,Integer.class)) {
            return (Integer) left >> (Integer) right;
        }

        throw getUnsupportedOperationException(left,right);
    }


    public String getOperation() {
        return ">>";
    }

}
