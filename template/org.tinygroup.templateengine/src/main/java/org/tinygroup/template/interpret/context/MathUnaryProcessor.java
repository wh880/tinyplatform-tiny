package org.tinygroup.template.interpret.context;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.interpret.ContextProcessor;
import org.tinygroup.template.interpret.TemplateFromContext;
import org.tinygroup.template.interpret.TemplateInterpretEngine;
import org.tinygroup.template.interpret.TemplateInterpreter;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;
import org.tinygroup.template.rumtime.O;

import java.io.Writer;

/**
 * Created by luog on 15/7/17.
 */
public class MathUnaryProcessor implements ContextProcessor<TinyTemplateParser.Expr_math_unary_prefixContext> {


    public Class<TinyTemplateParser.Expr_math_unary_prefixContext> getType() {
        return TinyTemplateParser.Expr_math_unary_prefixContext.class;
    }

    public boolean processChildren() {
        return false;
    }


    public Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, TinyTemplateParser.Expr_math_unary_prefixContext parseTree, TemplateContext pageContext, TemplateContext context, TemplateInterpretEngine engine, Writer writer) throws Exception {
        return O.e("l" + parseTree.getChild(0).getText(), interpreter.interpretTree(engine, templateFromContext, parseTree.expression(), pageContext,context, writer));
    }
}

