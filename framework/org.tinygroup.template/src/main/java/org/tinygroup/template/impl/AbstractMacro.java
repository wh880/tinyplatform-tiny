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
    private TemplateContext templateContext = new TemplateContextImpl();

    public void setName(String name) {
        this.name = name;
    }

    public void setParameterNames(String[] parameterNames) {
        this.parameterNames = parameterNames;
    }

    protected void init(String name, String[] parameterNames) {
        this.name = name;
        this.parameterNames = parameterNames;
    }

    protected void write(Writer $writer, Object object) throws IOException {
        $writer.write(object.toString());
    }

    public void render(Template $template, TemplateContext $context, Writer writer) throws TemplateException {
        try {
            $context.putSubContext("$currentMacroContext", getTemplateContext());
            renderTemplate($template, $context, writer);
            writer.flush();
        } catch (IOException e) {
            throw new TemplateException(e);
        } finally {
            //无论如何从里面拿掉
            $context.removeSubContext("$currentMacroContext");
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
