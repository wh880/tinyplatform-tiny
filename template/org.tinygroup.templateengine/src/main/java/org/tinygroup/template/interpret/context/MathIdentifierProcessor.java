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
public class MathIdentifierProcessor implements ContextProcessor<TinyTemplateParser.Expr_identifierContext> {


    public Class<TinyTemplateParser.Expr_identifierContext> getType() {
        return TinyTemplateParser.Expr_identifierContext.class;
    }

    public boolean processChildren() {
        return false;
    }


    public Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, TinyTemplateParser.Expr_identifierContext parseTree, TemplateContext pageContext, TemplateContext context, TemplateInterpretEngine engine, Writer writer) throws Exception {
        Object a = interpreter.interpretTree(engine, templateFromContext, parseTree.IDENTIFIER(), pageContext,context, writer);
        return U.v(context, a);
    }
}

