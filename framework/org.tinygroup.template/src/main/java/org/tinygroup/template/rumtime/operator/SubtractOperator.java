package org.tinygroup.template.rumtime.operator;

import java.math.BigDecimal;

/**
 * Created by luoguo on 2014/6/5.
 */
public class SubtractOperator extends TwoOperator {


    protected Object operation(Object left, Object right) {
        if (isType(left,"java.lang.Byte")) {
            return (Byte) left - (Byte) right;
        }
        if (isType(left,"java.lang.Character")) {
            return (Character) left - (Character) right;
        }
        if (isType(left,"java.lang.Integer")) {
            return (Integer) left - (Integer) right;
        }
        if (isType(left,"FloatO")) {
            return (Float) left - (Float) right;
        }
        if (isType(left,"java.lang.Double")) {
            return (Double) left - (Double) right;
        }
        if (isType(left,"java.math.BigDecimal")) {
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
