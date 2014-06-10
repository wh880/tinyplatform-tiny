package org.tinygroup.template.function;

import org.tinygroup.template.TemplateEngine;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.TemplateFunction;

/**
 * Created by luoguo on 2014/6/9.
 */
public abstract class AbstractTemplateFunction implements TemplateFunction {
    private final String name;

    public AbstractTemplateFunction(String name) {
        this.name = name;
    }

    private TemplateEngine templateEngine;

    public TemplateEngine getTemplateEngine() {
        return templateEngine;
    }

    @Override
    public String getNames() {
        return name;
    }

    @Override
    public String getBindingTypes() {
        return null;
    }

    @Override
    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    protected TemplateException notSupported(Object[] parameters) throws TemplateException {
        StringBuffer sb = new StringBuffer(getNames() + "不支持下面的参数：[\n");
        for (Object parameter : parameters) {
            sb.append(parameter.getClass().getName()).append("\n");
        }
        sb.append("]\n");
        throw new TemplateException(sb.toString());
    }
}

