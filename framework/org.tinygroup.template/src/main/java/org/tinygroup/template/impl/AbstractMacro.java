package org.tinygroup.template.impl;

import org.tinygroup.template.*;

import java.io.IOException;
import java.io.Writer;

/**
 * 抽象宏
 * Created by luoguo on 2014/6/6.
 */
public abstract class AbstractMacro implements Macro {
    public AbstractMacro(String name) {
        this.name = name;
    }

    public AbstractMacro(String name, String[] parameterNames) {
        this(name);
        this.parameterNames = parameterNames.clone();
    }

    private String name;
    private String[] parameterNames;
    private TemplateEngine templateEngine;

    public TemplateEngine getTemplateEngine() {
        return templateEngine;
    }

    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public void setParameterNames(String[] parameterNames) {
        this.parameterNames = parameterNames.clone();
    }

    protected void init(String name, String[] parameterNames) {
        this.name = name;
        this.parameterNames = parameterNames.clone();
    }

    protected void write(Writer writer, Object object) throws IOException {
        writer.write(object.toString());
    }

    public void render(Template template, TemplateContext context, Writer writer) throws TemplateException {
        try {
            renderMacro(template, context, writer);
        } catch (IOException e) {
            throw new TemplateException(e);
        }
    }

    protected abstract void renderMacro(Template template, TemplateContext context, Writer writer) throws IOException, TemplateException;


    public String getName() {
        return name;
    }

    public String[] getParameterNames() {
        return parameterNames.clone();
    }


}
