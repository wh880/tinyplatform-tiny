package org.tinygroup.template.interpret.context;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.interpret.ContextProcessor;
import org.tinygroup.template.interpret.TemplateFromContext;
import org.tinygroup.template.interpret.TemplateInterpretEngine;
import org.tinygroup.template.interpret.TemplateInterpreter;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;
import org.tinygroup.template.rumtime.O;
import org.tinygroup.template.rumtime.U;

import java.io.Writer;

/**
 * Created by luog on 15/7/17.
 */
public class MathCompareConditionProcessor implements ContextProcessor<TinyTemplateParser.Expr_compare_conditionContext> {


    public Class<TinyTemplateParser.Expr_compare_conditionContext> getType() {
        return TinyTemplateParser.Expr_compare_conditionContext.class;
    }

    public boolean processChildren() {
        return false;
    }


    public Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, TinyTemplateParser.Expr_compare_conditionContext parseTree, TemplateContext pageContext, TemplateContext context, TemplateInterpretEngine engine, Writer writer) throws Exception {
        boolean a = U.b(interpreter.interpretTree(engine, templateFromContext, parseTree.expression().get(0),pageContext, context, writer));
        boolean b = U.b(interpreter.interpretTree(engine, templateFromContext, parseTree.expression().get(1),pageContext, context, writer));
        return O.e(parseTree.getChild(1).getText(), a, b);
    }
}

