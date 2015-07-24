package org.tinygroup.template.interpret.context;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.interpret.ContextProcessor;
import org.tinygroup.template.interpret.TemplateFromContext;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.interpret.TemplateInterpreter;
import org.tinygroup.template.interpret.terminal.ForBreakException;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;
import org.tinygroup.template.rumtime.U;

import java.io.Writer;

/**
 * Created by luog on 15/7/17.
 */
public class ForBreakProcessor implements ContextProcessor<TinyTemplateParser.Break_directiveContext> {


    public Class<TinyTemplateParser.Break_directiveContext> getType() {
        return TinyTemplateParser.Break_directiveContext.class;
    }

    public boolean processChildren() {
        return false;
    }

    public Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, TinyTemplateParser.Break_directiveContext parseTree, TemplateContext pageContext, TemplateContext context, TemplateEngineDefault engine, Writer writer) throws Exception {
        boolean breakFor = true;
        if (parseTree.expression() != null) {
            breakFor = U.b(interpreter.interpretTree(engine, templateFromContext, parseTree.expression(), pageContext,context, writer));
        }
        if (breakFor) {
            throw new ForBreakException();
        }
        return null;
    }


}
