package org.tinygroup.template.impl;

import org.tinygroup.template.Macro;
import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;

import java.io.IOException;
import java.io.Writer;

/**
 * 抽象宏
 * Created by luoguo on 2014/6/6.
 */
public abstract class AbstractMacro implements Macro {
    private String name;
    private String[] parameterNames;
    private TemplateContext templateContext = new TemplateContextDefault();
    private Template template;
    public void setName(String name) {
        this.name = name;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public void setParameterNames(String[] parameterNames) {
        this.parameterNames = parameterNames.clone();
    }

    protected void init(String name, String[] parameterNames) {
        this.name = name;
        this.parameterNames = parameterNames.clone();
    }

    protected void write(Writer $writer, Object object) throws IOException {
        $writer.write(object.toString());
    }

    public void render(Template $template, TemplateContext invokeContext, Writer writer) throws TemplateException {
        TemplateContext newContext = new TemplateContextDefault();
        try {
            if (invokeContext != null) {
                newContext.putSubContext("$invokeContext", invokeContext);
            }
            invokeContext.putSubContext("$currentMacroContext", getTemplateContext());
            renderTemplate($template, newContext, writer);
            writer.flush();
        } catch (IOException e) {
            throw new TemplateException(e);
        } finally {
            //无论如何从里面拿掉
            if (invokeContext != null) {
                newContext.removeSubContext("$invokeContext");
            }
            invokeContext.removeSubContext("$currentMacroContext");
        }
    }

    protected abstract void renderTemplate(Template template, TemplateContext $context, Writer $writer) throws IOException, TemplateException;


    public String getName() {
        return name;
    }

    public String[] getParameterNames() {
        return parameterNames.clone();
    }


    public TemplateContext getTemplateContext() {
        return templateContext;
    }


    public Object put(String key, Object value) {
        return templateContext.put(key, value);
    }
}
