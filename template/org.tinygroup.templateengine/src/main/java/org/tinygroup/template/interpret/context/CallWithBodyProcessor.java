package org.tinygroup.template.interpret.context;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.interpret.ContextProcessor;
import org.tinygroup.template.interpret.TemplateFromContext;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.interpret.TemplateInterpreter;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;

import java.io.Writer;

/**
 * Created by luog on 15/7/17.
 */
public class CallWithBodyProcessor implements ContextProcessor<TinyTemplateParser.Call_block_directiveContext> {


    public Class<TinyTemplateParser.Call_block_directiveContext> getType() {
        return TinyTemplateParser.Call_block_directiveContext.class;
    }

    public boolean processChildren() {
        return false;
    }

    public Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, TinyTemplateParser.Call_block_directiveContext parseTree, TemplateContext pageContext, TemplateContext context, TemplateEngineDefault engine, Writer writer) throws Exception {
        String name = interpreter.interpretTree(engine, templateFromContext, parseTree.expression(),pageContext, context, writer).toString();
        interpreter.callBlockMacro(engine, templateFromContext, name, parseTree.block(), parseTree.para_expression_list(),pageContext, writer, context);
        return null;
    }

}
