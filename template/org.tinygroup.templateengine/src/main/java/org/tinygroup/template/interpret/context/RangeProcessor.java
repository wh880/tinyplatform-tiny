package org.tinygroup.template.interpret.context;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.interpret.ContextProcessor;
import org.tinygroup.template.interpret.TemplateFromContext;
import org.tinygroup.template.interpret.TemplateInterpretEngine;
import org.tinygroup.template.interpret.TemplateInterpreter;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;
import org.tinygroup.template.rumtime.RangeList;

import java.io.Writer;

/**
 * Created by luog on 15/7/17.
 */
public class RangeProcessor implements ContextProcessor<TinyTemplateParser.Expression_rangeContext> {


    public Class<TinyTemplateParser.Expression_rangeContext> getType() {
        return TinyTemplateParser.Expression_rangeContext.class;
    }

    public boolean processChildren() {
        return false;
    }


    public Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, TinyTemplateParser.Expression_rangeContext parseTree, TemplateContext context, Writer writer, TemplateInterpretEngine engine) throws Exception {
        int start = (Integer) interpreter.interpretTree(engine, templateFromContext, parseTree.getChild(0), context, writer);
        int end = (Integer) interpreter.interpretTree(engine, templateFromContext, parseTree.getChild(2), context, writer);
        return new RangeList(start, end, 1);
    }
}