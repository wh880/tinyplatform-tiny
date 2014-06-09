package org.tinygroup.template.rumtime.operator;

import org.tinygroup.template.rumtime.Operator;

/**
 * Created by luoguo on 2014/6/5.
 */
public abstract class AbstractOperator implements Operator {
    protected boolean isType(Object left,String type) {
        return left.getClass().getName().equals(type);
    }
}
