package org.tinygroup.template.rumtime.impl.operator;

import java.math.BigDecimal;

/**
 * Created by luoguo on 2014/6/5.
 */
public class AddOperator extends DoubleOperator {


    protected Object operation(Object left, Object right) {
        if (isType(left,"java.lang.Byte")) {
            return (Byte) left + (Byte) right;
        }
        if (isType(left,"java.lang.Character")) {
            return (Character) left + (Character) right;
        }
        if (isType(left,"java.lang.Integer")) {
            return (Integer) left + (Integer) right;
        }
        if (isType(left,"java.lang.Float")) {
            return (Float) left + (Float) right;
        }
        if (isType(left,"java.lang.Double")) {
            return (Double) left + (Double) right;
        }
        if (isType(left,"java.math.BigDecimal")) {
            BigDecimal b1 = (BigDecimal) left;
            BigDecimal b2 = (BigDecimal) right;
            return b1.add(b2);
        }

        throw new UnsupportedOperationException(left.getClass().getName() + "+" + right.getClass().getName());
    }


    public String getOperation() {
        return "+";
    }

}
