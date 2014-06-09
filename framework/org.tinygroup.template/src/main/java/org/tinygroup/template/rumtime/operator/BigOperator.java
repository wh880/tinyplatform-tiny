package org.tinygroup.template.rumtime.operator;

/**
 * Created by luoguo on 2014/6/5.
 */
public class BigOperator extends DoubleOperator {


    protected Object operation(Object left, Object right) {
        if(left instanceof Comparable&&right instanceof Comparable){
            return ((Comparable) left).compareTo(right)>0;
        }
        throw new UnsupportedOperationException(left.getClass().getName() + ">" + right.getClass().getName());
    }


    public String getOperation() {
        return ">";
    }

}
