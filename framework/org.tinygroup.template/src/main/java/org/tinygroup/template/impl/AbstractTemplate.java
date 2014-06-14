package org.tinygroup.template.impl;

import org.tinygroup.template.*;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by luoguo on 2014/6/6.
 */
public abstract class AbstractTemplate implements Template {
    private Map<String, Macro> macroMap = new HashMap<String, Macro>();
    private TemplateEngine templateEngine;
    private TemplateContext templateContext = new TemplateContextDefault();

    public TemplateEngine getTemplateEngine() {
        return templateEngine;
    }

    protected void write(Writer writer, Object object) throws IOException {
        writer.write(object.toString());
    }

    protected void addMacro(Macro macro) {
        macroMap.put(macro.getName(), macro);
    }

    public Map<String, Macro> getMacroMap() {
        return macroMap;
    }

    public void render(TemplateContext context, Writer writer) throws TemplateException {
        try {
            context.putSubContext("$currentTemplateContext",getTemplateContext());
            renderContent(context, writer);
            writer.flush();
        } catch (IOException e) {
            throw new TemplateException(e);
        } finally {
            context.removeSubContext("$currentTemplateContext");
        }
    }

    public void render() throws TemplateException {
        render(new TemplateContextDefault(), new OutputStreamWriter(System.out));
    }

    protected abstract void renderContent(TemplateContext context, Writer writer) throws IOException, TemplateException;

    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public TemplateContext getTemplateContext() {
        return templateContext;
    }


    public Object put(String key, Object value) {
        return templateContext.put(key, value);
    }
}
