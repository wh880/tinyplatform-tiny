package org.tinygroup.template.interpret.context;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.interpret.*;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;
import org.tinygroup.template.rumtime.U;

import java.io.Writer;

/**
 * Created by luog on 15/7/17.
 */
public class StopProcessor implements ContextProcessor<TinyTemplateParser.Stop_directiveContext> {

    public Class<TinyTemplateParser.Stop_directiveContext> getType() {
        return TinyTemplateParser.Stop_directiveContext.class;
    }


    public boolean processChildren() {
        return false;
    }

    public Object process(TemplateInterpreter interpreter, TemplateFromContext template, TinyTemplateParser.Stop_directiveContext parseTree, TemplateContext context, Writer writer, TemplateInterpretEngine engine) throws Exception {
        boolean stop = true;
        if (parseTree.expression() != null) {
            stop = U.b(interpreter.interpretTree(engine, template, parseTree.expression(), context, writer));
        }
        if (stop) {
            throw new StopException();
        }
        return null;
    }
}
