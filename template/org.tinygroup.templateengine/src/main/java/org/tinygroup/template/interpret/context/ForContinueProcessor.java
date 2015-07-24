package org.tinygroup.template.interpret.context;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.interpret.ContextProcessor;
import org.tinygroup.template.interpret.TemplateFromContext;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.interpret.TemplateInterpreter;
import org.tinygroup.template.interpret.terminal.ForContinueException;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;
import org.tinygroup.template.rumtime.U;

import java.io.Writer;

/**
 * Created by luog on 15/7/17.
 */
public class ForContinueProcessor implements ContextProcessor<TinyTemplateParser.Continue_directiveContext> {


    public Class<TinyTemplateParser.Continue_directiveContext> getType() {
        return TinyTemplateParser.Continue_directiveContext.class;
    }

    public boolean processChildren() {
        return false;
    }

    public Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, TinyTemplateParser.Continue_directiveContext parseTree, TemplateContext pageContext, TemplateContext context, TemplateEngineDefault engine, Writer writer) throws Exception {
        boolean continueFor = true;
        if (parseTree.expression() != null) {
            continueFor = U.b(interpreter.interpretTree(engine, templateFromContext, parseTree.expression(),pageContext, context, writer));
        }
        if (continueFor) {
            throw new ForContinueException();
        }
        return null;
    }


}
