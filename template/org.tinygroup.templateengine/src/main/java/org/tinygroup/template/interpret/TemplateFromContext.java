package org.tinygroup.template.interpret;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.impl.AbstractTemplate;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by luog on 15/7/19.
 */
public class TemplateFromContext extends AbstractTemplate {
    private final String path;
    private final TemplateInterpreter interpreter;
    TinyTemplateParser.TemplateContext templateContext;
    public TemplateFromContext(TemplateInterpreter interpreter, String path, TinyTemplateParser.TemplateContext templateContext){
        this.path=path;
        this.templateContext=templateContext;
        this.interpreter=interpreter;
    }
    @Override
    protected void renderContent(TemplateContext context, Writer writer) throws IOException, TemplateException {
        try {
            TemplateEngineDefault templateEngine = (TemplateEngineDefault) getTemplateEngine();
            templateEngine.interpreter.interpret(templateEngine,this,templateContext, context, context,writer);
        } catch (StopException e) {
            //Do Nothing
        } catch (TemplateException e) {
            throw e;
        } catch (Exception e) {
            throw new TemplateException(e);
        }
    }

    public String getPath() {
        return path;
    }
}
