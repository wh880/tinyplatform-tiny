package org.tinygroup.template.interpret;

import org.antlr.v4.runtime.ParserRuleContext;
import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateContext;

import java.io.Writer;

/**
 * Created by luog on 15/7/17.
 */
public interface ContextProcessor<T extends ParserRuleContext> {
    Class<T> getType();

    boolean processChildren();

    Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, T parseTree, TemplateContext pageContext, TemplateContext context, TemplateInterpretEngine engine, Writer writer) throws Exception;

}
