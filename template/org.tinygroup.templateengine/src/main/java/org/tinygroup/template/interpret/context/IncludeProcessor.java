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
public class IncludeProcessor implements ContextProcessor<TinyTemplateParser.Include_directiveContext> {


    public Class<TinyTemplateParser.Include_directiveContext> getType() {
        return TinyTemplateParser.Include_directiveContext.class;
    }

    public boolean processChildren() {
        return false;
    }


    public Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, TinyTemplateParser.Include_directiveContext parseTree, TemplateContext pageContext, TemplateContext context, TemplateInterpretEngine engine, Writer writer) throws Exception {
        //        String key=interpreter.interpretTree(engine,templateFromContext,parseTree.IDENTIFIER(),context,writer).toString();
        //        Object value=interpreter.interpretTree(engine,templateFromContext,parseTree.expression(),context,writer);
        //        context.put(key,value);
        return null;
    }
}