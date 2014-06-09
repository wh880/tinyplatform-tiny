package org.tinygroup.template.rumtime.operator;

import org.tinygroup.template.TemplateException;
import org.tinygroup.template.rumtime.O;

/**
 * Created by luoguo on 2014/6/6.
 */
public abstract class DoubleOperator extends AbstractOperator {

    public int getParameterCount() {
        return 2;
    }
    public Object operation(Object... parameter) throws TemplateException {
        if (parameter == null ) {
            throw new TemplateException("参数变量不能为空");
        }
        if( parameter.length != getParameterCount()){
            throw new TemplateException("参数变量数量不匹配，期望:"+getParameterCount()+",实际:"+parameter.length);
        }
        Object left = parameter[0];
        Object right = parameter[1];
        String typeLeft = left.getClass().getName();
        String typeRight = right.getClass().getName();
        if (O.isNumberic(typeLeft) && O.isNumberic(typeRight)) {
            //如果两个都是数字类型
            return operation(left, right, typeLeft, typeRight);
        } else {
            return left.toString() + right.toString();
        }
    }

    private Object operation(Object left, Object right, String type1, String type2) {
        if (!type1.equals(type2)) {
            if (O.compare(type1, type2) > 0) {
                right = O.convert(right, type2, type1);
            } else {
                left = O.convert(left, type1, type2);
            }
        }
        return operation(left, right);
    }

    protected abstract Object operation(Object left, Object right) ;

}
