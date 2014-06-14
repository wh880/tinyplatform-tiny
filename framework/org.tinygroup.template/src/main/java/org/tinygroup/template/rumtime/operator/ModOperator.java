package org.tinygroup.template.rumtime.operator;

/**
 * Created by luoguo on 2014/6/5.
 */
public class ModOperator extends TwoOperator {


    protected Object operation(Object left, Object right) {
        if (isType(left,"java.lang.Byte")) {
            return (Byte) left % (Byte) right;
        }
        if (isType(left,"java.lang.Character")) {
            return (Character) left % (Character) right;
        }
        if (isType(left,"java.lang.Integer")) {
            return (Integer) left % (Integer) right;
        }
        if (isType(left,"java.lang.Float")) {
            return (Float) left % (Float) right;
        }
        if (isType(left,"java.lang.Double")) {
            return (Double) left % (Double) right;
        }
        throw getUnsupportedOperationException(left,right);
    }


    public String getOperation() {
        return "%";
    }

}
