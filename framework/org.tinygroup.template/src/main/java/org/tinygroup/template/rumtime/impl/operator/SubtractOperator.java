package org.tinygroup.template.rumtime.impl.operator;

import java.math.BigDecimal;

/**
 * Created by luoguo on 2014/6/5.
 */
public class SubtractOperator extends DoubleOperator {


    protected Object operation(Object left, Object right) {
        if (isType(left,"Byte")) {
            return (Byte) left - (Byte) right;
        }
        if (isType(left,"Character")) {
            return (Character) left - (Character) right;
        }
        if (isType(left,"Integer")) {
            return (Integer) left - (Integer) right;
        }
        if (isType(left,"FloatO")) {
            return (Float) left - (Float) right;
        }
        if (isType(left,"Double")) {
            return (Double) left - (Double) right;
        }
        if (isType(left,"BigDecimal")) {
            BigDecimal b1 = (BigDecimal) left;
            BigDecimal b2 = (BigDecimal) right;
            return b1.subtract(b2);
        }

        throw new UnsupportedOperationException(left.getClass().getName() + "-" + right.getClass().getName());
    }


    public String getOperation() {
        return "-";
    }

}
