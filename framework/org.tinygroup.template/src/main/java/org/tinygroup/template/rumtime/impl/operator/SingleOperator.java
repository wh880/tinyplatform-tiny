package org.tinygroup.template.rumtime.impl.operator;

import org.tinygroup.template.TemplateException;

/**
 * Created by luoguo on 2014/6/8.
 */
public abstract class SingleOperator extends AbstractOperator {

    public int getParameterCount() {
        return 1;
    }

    public Object operation(Object... parameter) throws TemplateException {
        if (parameter == null || parameter.length != getParameterCount()) {
            throw new TemplateException("参数变量不能为空");
        }
        Object var = parameter[0];

        //如果两个都是数字类型
        return operation(var);
    }

    protected abstract Object operation(Object var) throws TemplateException;
}
