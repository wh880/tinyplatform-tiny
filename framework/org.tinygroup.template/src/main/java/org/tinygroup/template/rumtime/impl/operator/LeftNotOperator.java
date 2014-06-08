package org.tinygroup.template.rumtime.impl.operator;

import org.tinygroup.template.TemplateException;

/**
 * Created by luoguo on 2014/6/8.
 */
public class LeftNotOperator extends SingleOperator {

    @Override
    public String getOperation() {
        return "l~";
    }


    @Override
    protected Object operation(Object var) throws TemplateException {
        String type=var.getClass().getName();
        if (isType(type,"java.lang.Byte")) {
            return ~((Byte) var);
        }
        if (isType(type,"java.lang.Integer")) {
            return ~((Integer) var);
        }
        if (isType(type,"Long")) {
            return ~((Long) var);
        }
        throw new TemplateException("类型" + type + "不支持“!”操作。");
    }


}
