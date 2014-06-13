package org.tinygroup.template.impl;

import org.tinygroup.template.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by luoguo on 2014/6/12.
 */
public abstract class AbstractLayout implements Layout {
    private Map<String, Macro> macroMap = new HashMap<String, Macro>();
    private TemplateEngine templateEngine;
    private TemplateContext templateContext = new TemplateContextDefault();

    public TemplateEngine getTemplateEngine() {
        return templateEngine;
    }

    protected void write(OutputStream outputStream, Object object) throws IOException {
        getTemplateEngine().write(outputStream,object);
    }

    protected void addMacro(Macro macro) {
        macroMap.put(macro.getName(), macro);
    }

    public Map<String, Macro> getMacroMap() {
        return macroMap;
    }

    public void render(TemplateContext invokeContext, OutputStream outputStream) throws TemplateException {
        try {
            renderLayout(invokeContext, outputStream);
            invokeContext.putSubContext("$currentTemplateContext",getTemplateContext());
        } catch (IOException e) {
            throw new TemplateException(e);
        } finally {
            invokeContext.removeSubContext("$currentTemplateContext");
        }
    }

    public void render() throws TemplateException {
        render(new TemplateContextDefault(), System.out);
    }

    protected abstract void renderLayout(TemplateContext $context, OutputStream outputStream) throws IOException, TemplateException;

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
