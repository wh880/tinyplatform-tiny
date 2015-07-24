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
public class FunctionCallProcessor implements ContextProcessor<TinyTemplateParser.Expr_function_callContext> {


    public Class<TinyTemplateParser.Expr_function_callContext> getType() {
        return TinyTemplateParser.Expr_function_callContext.class;
    }

    public boolean processChildren() {
        return false;
    }

    public Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, TinyTemplateParser.Expr_function_callContext parseTree, TemplateContext pageContext, TemplateContext context, TemplateEngineDefault engine, Writer writer) throws Exception {
        String name = parseTree.IDENTIFIER().getText();
        Object[] paraList = null;
        if (parseTree.expression_list() != null) {
            paraList = new Object[parseTree.expression_list().expression().size()];
            int i = 0;
            for (TinyTemplateParser.ExpressionContext expr : parseTree.expression_list().expression()) {
                paraList[i++] = interpreter.interpretTree(engine, templateFromContext, expr, pageContext, context, writer);
            }
        }
        return engine.executeFunction(templateFromContext, context, name, paraList);
    }

}
