package org.tinygroup.template.rumtime.operator;

import java.math.BigDecimal;

/**
 * Created by luoguo on 2014/6/5.
 */
public class SubtractOperator extends TwoOperator {


    protected Object operation(Object left, Object right) {
        if (isType(left,Byte.class)) {
            return (Byte) left - (Byte) right;
        }
        if (isType(left,Character.class)) {
            return (Character) left - (Character) right;
        }
        if (isType(left,Integer.class)) {
            return (Integer) left - (Integer) right;
        }
        if (isType(left,Float.class)) {
            return (Float) left - (Float) right;
        }
        if (isType(left,Double.class)) {
            return (Double) left - (Double) right;
        }
        if (isType(left,BigDecimal.class)) {
            BigDecimal b1 = (BigDecimal) left;
            BigDecimal b2 = (BigDecimal) right;
            return b1.subtract(b2);
        }

        throw getUnsupportedOperationException(left,right);
    }


    public String getOperation() {
        return "-";
    }

}
