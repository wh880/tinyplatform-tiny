package org.tinygroup.template.interpret.context;

import org.antlr.v4.runtime.tree.ParseTree;
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
public class BodyContentProcessor implements ContextProcessor<TinyTemplateParser.Bodycontent_directiveContext> {


    public Class<TinyTemplateParser.Bodycontent_directiveContext> getType() {
        return TinyTemplateParser.Bodycontent_directiveContext.class;
    }

    public boolean processChildren() {
        return false;
    }


    public Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, TinyTemplateParser.Bodycontent_directiveContext parseTree, TemplateContext context, Writer writer, TemplateInterpretEngine engine) throws Exception {
        //从上下文获得BODY中传入的内容,然后进行渲染

        ParseTree $bodyContent = (ParseTree) context.get("$bodyContent");
        context.remove("$bodyContent");
        interpreter.interpretTree(engine,templateFromContext, $bodyContent,context,writer);
        return null;
    }
}
