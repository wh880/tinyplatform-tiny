package org.tinygroup.template.rumtime.operator;

import org.tinygroup.template.TemplateException;

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
        return operation(left, right);
    }


    protected abstract Object operation(Object left, Object right);

    protected UnsupportedOperationException getUnsupportedOperationException(Object left, Object right) {
        throw new UnsupportedOperationException("类型" + left.getClass().getName() + "," + right.getClass().getName() + "不支持" + "+" + getOperation() + "操作");
    }

}
