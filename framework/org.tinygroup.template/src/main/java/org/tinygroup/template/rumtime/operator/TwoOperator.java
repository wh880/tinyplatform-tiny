package org.tinygroup.template.rumtime.operator;

import org.tinygroup.template.TemplateException;
import org.tinygroup.template.rumtime.O;

/**
 * Created by luoguo on 2014/6/6.
 */
public abstract class TwoOperator extends AbstractOperator {

    public int getParameterCount() {
        return 2;
    }

    public Object operation(Object... parameter) throws TemplateException {
        Object left = parameter[0];
        Object right = parameter[1];
        if (O.isNumber(left.getClass()) && O.isNumber(right.getClass())) {
            //如果两个都是数字类型
            return operationNumber(left, right, left.getClass(), right.getClass());
        } else {
            return operation(left, right);
        }
    }

    private Object operationNumber(Object left, Object right, Class type1, Class type2) {
        Object leftObject = left, rightObject = right;
        if (!type1.equals(type2)) {
            if (O.compare(type1, type2) > 0) {
                rightObject = O.convert(rightObject, type2, type1);
            } else {
                leftObject = O.convert(leftObject, type1, type2);
            }
        }
        return operation(leftObject, rightObject);
    }

    protected abstract Object operation(Object left, Object right);

    protected UnsupportedOperationException getUnsupportedOperationException(Object left, Object right) {
        throw new UnsupportedOperationException("类型" + left.getClass().getName() + "," + right.getClass().getName() + "不支持" + "+" + getOperation() + "操作");
    }

}
