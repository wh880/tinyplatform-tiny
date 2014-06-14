package org.tinygroup.template.rumtime.operator;

import org.tinygroup.template.TemplateException;

/**
 * Created by luoguo on 2014/6/8.
 */
public abstract class SingleOperator extends AbstractOperator {

    public int getParameterCount() {
        return 1;
    }

    public Object operation(Object... parameter) throws TemplateException {
        return operation(parameter[0]);
    }

    protected abstract Object operation(Object var) throws TemplateException;

    protected UnsupportedOperationException getUnsupportedOperationException(Object object) {
        throw new UnsupportedOperationException("类型" + object.getClass().getName() + "不支持" + "+" + getOperation() + "操作");
    }
}
