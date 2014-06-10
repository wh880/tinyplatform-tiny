package org.tinygroup.template.function;

import org.tinygroup.template.TemplateException;

import java.util.Arrays;

/**
 * Created by luoguo on 2014/6/9.
 */
public class InstanceOfTemplateFunction extends AbstractTemplateFunction {


    public InstanceOfTemplateFunction() {
        super("is,instanceOf,instance");
    }


    @Override
    public Object execute(Object... parameters) throws TemplateException {
        boolean result=true;
        Object object= parameters[0];

        for(Object parameter:parameters){
            if(parameter instanceof Class){
                //如果后面是类型
               Class clazz= (Class) parameter;
               if(!clazz.isInstance(object)){
                   return false;
               }
            }else if(parameter instanceof String){
                //如果是字符串
                try {
                    Class clazz=Class.forName((String)parameter);
                } catch (ClassNotFoundException e) {
                    throw new TemplateException(e);
                }
            }else{
                notSupported(parameters);
            }
        }
        Object[] objects = Arrays.copyOfRange(parameters, 1, parameters.length);
        return result;
    }

 }

