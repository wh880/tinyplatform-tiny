package org.tinygroup.template.interpret.context;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.interpret.ContextProcessor;
import org.tinygroup.template.interpret.TemplateFromContext;
import org.tinygroup.template.interpret.TemplateInterpretEngine;
import org.tinygroup.template.interpret.TemplateInterpreter;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;
import org.tinygroup.template.rumtime.U;

import java.io.Writer;
import java.util.List;

/**
 * Created by luog on 15/7/17.
 */
public class IfProcessor implements ContextProcessor<TinyTemplateParser.If_directiveContext> {

    public Class<TinyTemplateParser.If_directiveContext> getType() {
        return TinyTemplateParser.If_directiveContext.class;
    }

    public boolean processChildren() {
        return false;
    }

    public Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, TinyTemplateParser.If_directiveContext parseTree, TemplateContext context, Writer writer, TemplateInterpretEngine engine) throws Exception {
        //如果条件成立
        if (U.b(interpreter.interpretTree(engine, templateFromContext, parseTree.expression(), context, writer))) {
            interpreter.interpretTree(engine, templateFromContext, parseTree.block(), context, writer);
        } else {
            boolean processed = false;
            List<TinyTemplateParser.Elseif_directiveContext> elseifDirectiveContexts = parseTree.elseif_directive();
            for (TinyTemplateParser.Elseif_directiveContext elseifDirectiveContext : elseifDirectiveContexts) {
                processed = (Boolean) interpreter.interpretTree(engine, templateFromContext, elseifDirectiveContext, context, writer);
                if (processed) {
                    return null;
                }
            }
            if (!processed) {
                TinyTemplateParser.Else_directiveContext elseDirectiveContext = parseTree.else_directive();
                if (elseDirectiveContext != null) {
                    interpreter.interpretTree(engine, templateFromContext, elseDirectiveContext, context, writer);
                }
            }
        }
        return null;
    }


}
