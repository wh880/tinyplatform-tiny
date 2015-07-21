package org.tinygroup.template.interpret.context;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.interpret.ContextProcessor;
import org.tinygroup.template.interpret.TemplateFromContext;
import org.tinygroup.template.interpret.TemplateInterpretEngine;
import org.tinygroup.template.interpret.TemplateInterpreter;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;
import org.tinygroup.template.rumtime.ForIterator;

import java.io.Writer;

/**
 * Created by luog on 15/7/17.
 */
public class ForProcessor implements ContextProcessor<TinyTemplateParser.For_directiveContext> {

    public Class<TinyTemplateParser.For_directiveContext> getType() {
        return TinyTemplateParser.For_directiveContext.class;
    }

    public boolean processChildren() {
        return false;
    }

    public Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, TinyTemplateParser.For_directiveContext parseTree, TemplateContext context, Writer writer, TemplateInterpretEngine engine) throws Exception {
        String name = parseTree.for_expression().IDENTIFIER().getText();
        Object values = interpreter.interpretTree(engine, templateFromContext, parseTree.for_expression().expression(), context, writer);
        ForIterator forIterator = new ForIterator(values);
        context.put(name + "For", forIterator);
        boolean hasItem = false;
        while (forIterator.hasNext()) {
            //TODO continue, break check
            Object value = forIterator.next();
            context.put(name, value);
            interpreter.interpretTree(engine, templateFromContext, parseTree.block(), context, writer);
            hasItem = true;
        }
        if (!hasItem) {
            TinyTemplateParser.Else_directiveContext elseDirectiveContext = parseTree.else_directive();
            if (elseDirectiveContext != null) {
                interpreter.interpretTree(engine, templateFromContext, elseDirectiveContext.block(), context, writer);
            }
        }
        return null;
    }


}
