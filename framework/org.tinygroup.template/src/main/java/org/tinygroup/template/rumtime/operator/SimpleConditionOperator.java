package org.tinygroup.template.rumtime.operator;

import org.tinygroup.commons.tools.ArrayUtil;
import org.tinygroup.commons.tools.Enumerator;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by luoguo on 2014/6/5.
 */
public class SimpleConditionOperator extends TwoOperator {


    protected Object operation(Object left, Object right) {
        //如果为空返回right
        if (left == null) {
            return right;
        }
        if (left instanceof Collection) {
            Collection collection = (Collection) left;
            if (collection.size() == 0) {
                return right;
            }
        }
        if (left instanceof Map) {
            Map map = (Map) left;
            if (map.size() == 0) {
                return right;
            }
        }
        if (left.getClass().isArray() &&ArrayUtil.arrayLength(left) == 0) {
                return right;
        }
        if (left instanceof Iterator) {
            Iterator iterator= (Iterator) left;
            if ( iterator.hasNext()) {
                return right;
            }
        }
        if (left instanceof Enumerator) {
            Enumerator enumerator= (Enumerator) left;
            if ( enumerator.hasMoreElements()) {
                return right;
            }
        }
        return left;
    }

    public String getOperation() {
        return "?:";
    }

}
