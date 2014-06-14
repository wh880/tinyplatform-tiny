package org.tinygroup.template.rumtime.operator;

import org.tinygroup.template.TemplateException;
import org.tinygroup.template.rumtime.O;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by luoguo on 2014/6/6.
 */
public abstract class DoubleOperator extends AbstractOperator {

    public int getParameterCount() {
        return 2;
    }
    static Map<Class,Boolean>typeCache= new ConcurrentHashMap<Class,Boolean>();
    public Object operation(Object... parameter) throws TemplateException {
/*
        if (parameter == null) {
            throw new TemplateException("参数变量不能为空");
        }
        if (parameter.length != getParameterCount()) {
            throw new TemplateException("参数变量数量不匹配，期望:" + getParameterCount() + ",实际:" + parameter.length);
        }
*/
        Object left = parameter[0];
        Object right = parameter[1];
        if (O.isNumber(left.getClass()) && O.isNumber(right.getClass())) {
            //如果两个都是数字类型
            return operation(left, right, left.getClass(), right.getClass());
        } else {
            return left.toString() + right.toString();
        }
    }

    private Object operation(Object left, Object right, Class type1, Class type2) {
        if (!type1.equals(type2)) {
            if (O.compare(type1, type2) > 0) {
                right = O.convert(right, type2, type1);
            } else {
                left = O.convert(left, type1, type2);
            }
        }
        return operation(left, right);
    }

    protected abstract Object operation(Object left, Object right);

    protected UnsupportedOperationException getUnsupportedOperationException(Object left, Object right) {
        throw new UnsupportedOperationException("类型" + left.getClass().getName() + ","+right.getClass().getName()+"不支持" + "+" + getOperation() + "操作");
    }

}
