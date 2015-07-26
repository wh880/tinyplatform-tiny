package org.tinygroup.template.interpret.context;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.interpret.ContextProcessor;
import org.tinygroup.template.interpret.TemplateFromContext;
import org.tinygroup.template.interpret.TemplateInterpreter;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;
import org.tinygroup.template.rumtime.U;

import java.io.Writer;

/**
 * Created by luog on 15/7/17.
 */
public class MathConditionSimpleProcessor implements ContextProcessor<TinyTemplateParser.Expr_simple_condition_ternaryContext> {


    public Class<TinyTemplateParser.Expr_simple_condition_ternaryContext> getType() {
        return TinyTemplateParser.Expr_simple_condition_ternaryContext.class;
    }

    public boolean processChildren() {
        return false;
    }


    public Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, TinyTemplateParser.Expr_simple_condition_ternaryContext parseTree, TemplateContext pageContext, TemplateContext context, TemplateEngineDefault engine, Writer writer) throws Exception {
        boolean condition = U.b(interpreter.interpretTree(engine, templateFromContext, parseTree.expression(0),pageContext, context, writer));
        Object right = interpreter.interpretTree(engine, templateFromContext, parseTree.expression(1),pageContext, context, writer);
        return condition ? null : right;
    }
}

