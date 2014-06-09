package org.tinygroup.template.function;

import org.tinygroup.template.TemplateEngine;
import org.tinygroup.template.TemplateFunction;

/**
 * Created by luoguo on 2014/6/9.
 */
public abstract class AbstractFunctionWrapper implements TemplateFunction {
    private final String name;
    private TemplateEngine templateEngine;

    public AbstractFunctionWrapper(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }


}
