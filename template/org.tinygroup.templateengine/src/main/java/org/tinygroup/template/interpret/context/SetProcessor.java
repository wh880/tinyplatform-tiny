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
public class SetProcessor implements ContextProcessor<TinyTemplateParser.Set_expressionContext> {


    public Class<TinyTemplateParser.Set_expressionContext> getType() {
        return TinyTemplateParser.Set_expressionContext.class;
    }

    public boolean processChildren() {
        return false;
    }


    public Object process(TemplateInterpreter interpreter, TemplateFromContext template, TinyTemplateParser.Set_expressionContext parseTree, TemplateContext pageContext, TemplateContext context, TemplateInterpretEngine engine, Writer writer) throws Exception {
        String key = interpreter.interpretTree(engine, template, parseTree.IDENTIFIER(),pageContext, context, writer).toString();
        Object value = interpreter.interpretTree(engine, template, parseTree.expression(),pageContext, context, writer);
        context.put(key, value);
        return null;
    }
}

