package org.tinygroup.template.interpret.context;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.interpret.ContextProcessor;
import org.tinygroup.template.interpret.TemplateFromContext;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.interpret.TemplateInterpreter;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;
import org.tinygroup.template.rumtime.U;

import java.io.Writer;

/**
 * Created by luog on 15/7/17.
 */
public class ArrayGetProcessor implements ContextProcessor<TinyTemplateParser.Expr_array_getContext> {

    public Class<TinyTemplateParser.Expr_array_getContext> getType() {
        return TinyTemplateParser.Expr_array_getContext.class;
    }

    public boolean processChildren() {
        return false;
    }

    public Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, TinyTemplateParser.Expr_array_getContext parseTree, TemplateContext pageContext, TemplateContext context, TemplateEngineDefault engine, Writer writer) throws Exception {
        Object a = interpreter.interpretTree(engine, templateFromContext, parseTree.expression(0),pageContext, context, writer);
        Object b = interpreter.interpretTree(engine, templateFromContext, parseTree.expression(1), pageContext,context, writer);
        if (parseTree.getChild(1).getText().equals("?")) {
            return U.sa(a, b);
        } else {
            return U.a(a, b);
        }
    }

}
