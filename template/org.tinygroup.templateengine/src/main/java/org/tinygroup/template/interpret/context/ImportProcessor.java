package org.tinygroup.template.interpret.context;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.interpret.ContextProcessor;
import org.tinygroup.template.interpret.TemplateFromContext;
import org.tinygroup.template.interpret.TemplateInterpretEngine;
import org.tinygroup.template.interpret.TemplateInterpreter;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;

import java.io.Writer;

/**
 * Created by luog on 15/7/17.
 */
public class ImportProcessor implements ContextProcessor<TinyTemplateParser.Import_directiveContext> {

    public Class<TinyTemplateParser.Import_directiveContext> getType() {
        return TinyTemplateParser.Import_directiveContext.class;
    }

    public boolean processChildren() {
        return false;
    }

    public Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, TinyTemplateParser.Import_directiveContext parseTree, TemplateContext pageContext, TemplateContext context, TemplateInterpretEngine engine, Writer writer) throws Exception {
        Object path = interpreter.interpretTree(engine, templateFromContext, parseTree.expression(), pageContext,context, writer);
        templateFromContext.addImport(path);
        return null;
    }

}
