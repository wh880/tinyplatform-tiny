package org.tinygroup.template.impl;

import org.tinygroup.template.*;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by luoguo on 2014/6/6.
 */
public abstract class AbstractTemplate implements Template {
    private Map<String, Macro> macroMap=new HashMap<String, Macro>();
    private TemplateEngine templateEngine;

    public TemplateEngine getTemplateEngine() {
        return templateEngine;
    }

    public Map<String, Macro> getMacroMap() {
        return macroMap;
    }
    public void render(TemplateContext $context, Writer writer) throws TemplateException {
        try {
            renderTemplate($context, writer);
            writer.flush();
        } catch (IOException e) {
            throw new TemplateException(e);
        }
    }

    protected abstract void renderTemplate(TemplateContext $context, Writer writer)throws IOException,TemplateException;

    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine=templateEngine;
    }
}
