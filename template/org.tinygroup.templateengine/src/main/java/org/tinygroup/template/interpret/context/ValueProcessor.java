package org.tinygroup.template.interpret.context;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.interpret.ContextProcessor;
import org.tinygroup.template.interpret.TemplateFromContext;
import org.tinygroup.template.interpret.TemplateInterpretEngine;
import org.tinygroup.template.interpret.TemplateInterpreter;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;
import org.tinygroup.template.rumtime.U;

import java.io.Writer;

/**
 * Created by luog on 15/7/17.
 */
public class ValueProcessor implements ContextProcessor<TinyTemplateParser.ValueContext> {

    public Class<TinyTemplateParser.ValueContext> getType() {
        return TinyTemplateParser.ValueContext.class;
    }

    public boolean processChildren() {
        return false;
    }

    public Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, TinyTemplateParser.ValueContext parseTree, TemplateContext pageContext, TemplateContext context, TemplateInterpretEngine engine, Writer writer) throws Exception {
        Object value = interpreter.interpretTree(engine, templateFromContext, parseTree.getChild(1), pageContext,context, writer);
        if (parseTree.getChild(0).getText().equals("${")) {
            TemplateInterpreter.write(writer, value);
        } else if (parseTree.getChild(0).getText().equals("$!{")) {
            TemplateInterpreter.write(writer, U.escapeHtml(value));
        } else if (parseTree.getChild(0).getText().equals("$${")) {
            TemplateInterpreter.write(writer, U.getI18n(null, context, value.toString()));
        }
        return null;
    }

}

