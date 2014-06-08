package org.tinygroup.template.rumtime.impl.operator;

/**
 * Created by luoguo on 2014/6/5.
 */
public class LessEqualsOperator extends DoubleOperator {


    protected Object operation(Object left, Object right) {
        if(left instanceof Comparable&&right instanceof Comparable){
            return ((Comparable) left).compareTo(right)<=0;
        }
        throw new UnsupportedOperationException(left.getClass().getName() + "<=" + right.getClass().getName());
    }

    public static void main(String[] args) {
        System.out.println("2".compareTo("3"));
    }

    public String getOperation() {
        return "<=";
    }

}
