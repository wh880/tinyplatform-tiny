package org.tinygroup.template.impl;

import org.tinygroup.template.*;

import java.io.IOException;
import java.io.OutputStream;
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

    protected void write(OutputStream outputStream, Object object) throws IOException {
        templateEngine.write(outputStream,object.toString().getBytes());
    }

    protected void addMacro(Macro macro) {
        macroMap.put(macro.getName(), macro);
    }

    public Map<String, Macro> getMacroMap() {
        return macroMap;
    }

    public void render(TemplateContext invokeContext, OutputStream outputStream) throws TemplateException {
        try {
            invokeContext.putSubContext("$currentTemplateContext", getTemplateContext());
            renderTemplate(invokeContext, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            throw new TemplateException(e);
        } finally {
            invokeContext.removeSubContext("$currentTemplateContext");
        }
    }

    public void render() throws TemplateException {
        render(new TemplateContextDefault(), System.out);
    }

    protected abstract void renderTemplate(TemplateContext $context,OutputStream outputStream ) throws IOException, TemplateException;

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
