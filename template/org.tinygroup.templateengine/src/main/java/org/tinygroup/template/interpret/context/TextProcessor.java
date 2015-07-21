package org.tinygroup.template.interpret.context;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.interpret.ContextProcessor;
import org.tinygroup.template.interpret.TemplateFromContext;
import org.tinygroup.template.interpret.TemplateInterpretEngine;
import org.tinygroup.template.interpret.TemplateInterpreter;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;
import org.tinygroup.template.rumtime.U;

import java.io.Writer;

/**
 * Created by luog on 15/7/17.
 */
public class TextProcessor implements ContextProcessor<TinyTemplateParser.TextContext> {

    public Class<TinyTemplateParser.TextContext> getType() {
        return TinyTemplateParser.TextContext.class;
    }

    public boolean processChildren() {
        return false;
    }

    public Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, TinyTemplateParser.TextContext parseTree, TemplateContext context, Writer writer, TemplateInterpretEngine engine) throws Exception {
        TemplateInterpreter.write(writer,interpreter.interpretTree(engine,templateFromContext,parseTree.getChild(0),context,writer));
        return null;
    }

}
