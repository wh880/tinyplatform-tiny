package org.tinygroup.template.function;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateEngine;
import org.tinygroup.template.TemplateFunction;

import java.util.Arrays;

/**
 * Created by luoguo on 2014/6/9.
 */
public class Formatter implements TemplateFunction {

    private TemplateEngine templateEngine;

    @Override
    public String getName() {
        return "format";
    }

    @Override
    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public Object execute(TemplateContext context, Object... parameters) {
        String formatString = parameters[0].toString();
        Object[] objects = Arrays.copyOfRange(parameters, 1, parameters.length);
        return String.format(formatString, objects);
    }

    public static void main(String[] args) {
        Formatter formatter = new Formatter();
        System.out.println(formatter.execute(null,new Object[]{"this is %s %s",1,2}));
    }
}

