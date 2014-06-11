package org.tinygroup.template.function;

import org.tinygroup.template.TemplateException;

/**
 * Created by luoguo on 2014/6/9.
 */
public class GetResourceContentFunction extends AbstractTemplateFunction{

    public GetResourceContentFunction() {
        super("getResourceContent");
    }



    @Override
    public Object execute(Object... parameters) throws TemplateException {
        String encode=super.getTemplateEngine().getEncode();
        String path=null;
        if(parameters.length==0||!(parameters[0] instanceof String)){
            notSupported(parameters);
        }else{
            path= (String) parameters[0];
        }
        if(parameters.length>2){
            encode=parameters[1].toString();
        }

        return getTemplateEngine().getResourceContent(path,encode);
    }

}

