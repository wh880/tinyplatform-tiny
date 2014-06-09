package org.tinygroup.template.rumtime.operator;

/**
 * Created by luoguo on 2014/6/5.
 */
public class ShrOperator extends DoubleOperator {


    protected Object operation(Object left, Object right) {
        if (isType(left,"java.lang.Byte")) {
            return (Byte) left >> (Byte) right;
        }
        if (isType(left,"java.lang.Character")) {
            return (Character) left >> (Character) right;
        }
        if (isType(left,"java.lang.Integer")) {
            return (Integer) left >> (Integer) right;
        }

        throw getUnsupportedOperationException(left,right);
    }


    public String getOperation() {
        return ">>";
    }

}
