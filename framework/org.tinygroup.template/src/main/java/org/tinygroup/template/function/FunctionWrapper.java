package org.tinygroup.template.function;

import org.tinygroup.template.TemplateException;
import org.tinygroup.template.rumtime.U;

import java.lang.reflect.Method;

/**
 * Created by luoguo on 2014/6/9.
 */
public class FunctionWrapper extends AbstractFunctionWrapper {
    private Method method;
    private Object object;

    public FunctionWrapper(String functionName, Class clazz, Method method) {
        super(functionName);
        try {
            this.object = clazz.newInstance();
            this.method = method;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.method = method;
    }


    public FunctionWrapper(String functionName, String className, String methodName) {
        super(functionName);
        try {
            Class<?> clazz = Class.forName(className);
            method = clazz.getMethod(methodName, U.getParameterTypes(clazz, functionName));
            object = clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public FunctionWrapper(String functionName, Object object, String methodName) {
        super(functionName);
        try {
            this.object = object;
            method = object.getClass().getMethod(methodName, U.getParameterTypes(object.getClass(), methodName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }



    public Object execute(Object... parameters) throws TemplateException {
        try {
            return method.invoke(parameters);
        } catch (Exception e) {
            throw new TemplateException(e);
        }
    }
}
