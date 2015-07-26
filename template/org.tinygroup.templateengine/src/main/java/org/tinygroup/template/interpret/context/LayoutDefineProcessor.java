package org.tinygroup.template.interpret.context;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.interpret.ContextProcessor;
import org.tinygroup.template.interpret.TemplateFromContext;
import org.tinygroup.template.interpret.TemplateInterpreter;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;

import java.io.Writer;

/**
 * Created by luog on 15/7/17.
 */
public class LayoutDefineProcessor implements ContextProcessor<TinyTemplateParser.Layout_directiveContext> {

    public Class<TinyTemplateParser.Layout_directiveContext> getType() {
        return TinyTemplateParser.Layout_directiveContext.class;
    }

    public boolean processChildren() {
        return false;
    }

    public Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, TinyTemplateParser.Layout_directiveContext parseTree, TemplateContext pageContext, TemplateContext context, TemplateEngineDefault engine, Writer writer) throws Exception {
        String name = "$" + parseTree.IDENTIFIER().getText();
        if (context.exist(name)) {
            interpreter.interpretTree(engine, templateFromContext, (TinyTemplateParser.BlockContext) context.get(name), pageContext,context, writer);
        } else {
            interpreter.interpretTree(engine, templateFromContext, parseTree.block(),pageContext, context, writer);
        }
        return null;
    }
}