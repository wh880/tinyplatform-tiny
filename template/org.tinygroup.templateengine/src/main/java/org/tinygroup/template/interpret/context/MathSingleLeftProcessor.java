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
public class MathSingleLeftProcessor implements ContextProcessor<TinyTemplateParser.Expr_single_leftContext> {


    public Class<TinyTemplateParser.Expr_single_leftContext> getType() {
        return TinyTemplateParser.Expr_single_leftContext.class;
    }

    public boolean processChildren() {
        return false;
    }


    public Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, TinyTemplateParser.Expr_single_leftContext parseTree, TemplateContext context, Writer writer, TemplateInterpretEngine engine) throws Exception {
        Object a = interpreter.interpretTree(engine, templateFromContext, parseTree.getChild(1), context, writer);
        Object op = interpreter.interpretTree(engine, templateFromContext, parseTree.getChild(0), context, writer);
        return O.ce(context, op.toString(), a.toString(), null);
    }
}

