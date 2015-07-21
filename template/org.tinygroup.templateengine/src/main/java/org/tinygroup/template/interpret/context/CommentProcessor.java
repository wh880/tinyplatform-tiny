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
public class CommentProcessor implements ContextProcessor<TinyTemplateParser.CommentContext> {

    public Class<TinyTemplateParser.CommentContext> getType() {
        return TinyTemplateParser.CommentContext.class;
    }

    public boolean processChildren() {
        return false;
    }

    public Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, TinyTemplateParser.CommentContext parseTree, TemplateContext context, Writer writer, TemplateInterpretEngine engine) throws Exception {
        return null;
    }

}
