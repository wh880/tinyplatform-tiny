package org.tinygroup.template.function;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateEngine;
import org.tinygroup.template.TemplateException;

import java.util.Arrays;
import java.util.Formatter;

/**
 * Created by luoguo on 2014/6/9.
 */
public class FormatterTemplateFunction extends AbstractTemplateFunction{
    Formatter formatter=new Formatter();
    private TemplateEngine templateEngine;

    public FormatterTemplateFunction() {
        super("fmt,format,formatter");
    }

    @Override
    public Object execute(TemplateContext context, Object... parameters) throws TemplateException {
        if(parameters.length==0||!(parameters[0] instanceof String)){
            notSupported(parameters);
        }
        String formatString = parameters[0].toString();

        Object[] objects = Arrays.copyOfRange(parameters, 1, parameters.length);
        return formatter.format(formatString, objects);
    }

}

