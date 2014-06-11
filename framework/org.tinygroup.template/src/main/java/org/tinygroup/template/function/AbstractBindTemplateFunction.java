package org.tinygroup.template.function;

import org.tinygroup.template.TemplateException;

/**
 * Created by luoguo on 2014/6/9.
 */
public abstract class AbstractBindTemplateFunction extends AbstractTemplateFunction{
    private final String bindingType;

    public AbstractBindTemplateFunction(String name,String bindingType){
        super(name);
        this.bindingType=bindingType;
    }

    @Override
    public String getBindingTypes() {
        return bindingType;
    }

    protected TemplateException notSupported(Object[]parameters) throws TemplateException {
        StringBuffer sb=new StringBuffer(getNames()+"不支持下面的参数：[\n");
        for(Object parameter:parameters){
            sb.append(parameter.getClass().getName()).append("\n");
        }
        sb.append("]\n");
        throw new TemplateException(sb.toString());
    }
}

