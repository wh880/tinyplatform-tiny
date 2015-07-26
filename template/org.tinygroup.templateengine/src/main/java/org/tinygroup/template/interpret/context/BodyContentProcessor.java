package org.tinygroup.template.interpret.context;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.interpret.ContextProcessor;
import org.tinygroup.template.interpret.TemplateFromContext;
import org.tinygroup.template.interpret.TemplateInterpreter;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;

import java.io.Writer;
import java.util.Stack;

/**
 * Created by luog on 15/7/17.
 */
public class BodyContentProcessor implements ContextProcessor<TinyTemplateParser.Bodycontent_directiveContext> {


    public Class<TinyTemplateParser.Bodycontent_directiveContext> getType() {
        return TinyTemplateParser.Bodycontent_directiveContext.class;
    }

    public boolean processChildren() {
        return false;
    }


    public Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, TinyTemplateParser.Bodycontent_directiveContext parseTree, TemplateContext pageContext, TemplateContext context, TemplateEngineDefault engine, Writer writer) throws Exception {
        //从上下文获得BODY中传入的内容,然后进行渲染
        Stack<TinyTemplateParser.BlockContext> stack = context.get("$bodyContent");
        TinyTemplateParser.BlockContext bodyContent = stack.pop();
        if (bodyContent != null) {
            interpreter.interpretTree(engine, templateFromContext, bodyContent, pageContext, context, writer);
        }
        return null;
    }
}
