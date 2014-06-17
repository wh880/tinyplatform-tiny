package org.tinygroup.template.rumtime.operator;

/**
 * Created by luoguo on 2014/6/5.
 */
public class EqualsOperator extends TwoConvertOperator {


    protected Object operation(Object left, Object right) {
        return left.equals(right);
    }


    public String getOperation() {
        return "==";
    }

    public static void main(String[] args) {
        Integer i=1;
        Long b=1l;
        System.out.println(i<=b);
    }
}
