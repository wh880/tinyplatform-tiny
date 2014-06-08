package org.tinygroup.template.rumtime.impl.operator;

import org.tinygroup.template.TemplateException;

/**
 * Created by luoguo on 2014/6/8.
 */
public class LeftLiteralOperator extends SingleOperator {

    @Override
    public String getOperation() {
        return "l!";
    }


    @Override
    protected Object operation(Object var) throws TemplateException {
        String typeVar = var.getClass().getName();
        if (isType(typeVar, "Boolean")) {
            return !(Boolean) var;
        }
        throw new TemplateException("类型" + typeVar + "不支持“!”操作。");
    }

    public static void main(String[] args) {
        System.out.println(!true);
    }
}
