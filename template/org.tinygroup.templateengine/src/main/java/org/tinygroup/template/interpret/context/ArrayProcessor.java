package org.tinygroup.template.interpret.context;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.interpret.ContextProcessor;
import org.tinygroup.template.interpret.TemplateFromContext;
import org.tinygroup.template.interpret.TemplateInterpreter;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;

import java.io.Writer;

/**
 * Created by luog on 15/7/17.
 */
public class ArrayProcessor implements ContextProcessor<TinyTemplateParser.Expr_array_listContext> {

    public Class<TinyTemplateParser.Expr_array_listContext> getType() {
        return TinyTemplateParser.Expr_array_listContext.class;
    }

    public boolean processChildren() {
        return false;
    }

    public Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, TinyTemplateParser.Expr_array_listContext parseTree, TemplateContext pageContext, TemplateContext context, TemplateEngineDefault engine, Writer writer) throws Exception {
        if (parseTree.expression_list() != null) {
            Object[] objects = new Object[parseTree.expression_list().expression().size()];
            for (int i = 0; i < parseTree.expression_list().expression().size(); i++) {
                objects[i] = interpreter.interpretTree(engine, templateFromContext, parseTree.expression_list().expression().get(i), pageContext,context, writer);
            }
            return objects;
        }
        if (parseTree.expression_range() != null) {
            return interpreter.interpretTree(engine, templateFromContext, parseTree.expression_range(),pageContext, context, writer);
        }
        return null;
    }

}
