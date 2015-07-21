package org.tinygroup.template.interpret.context;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.interpret.*;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;

import java.io.Writer;

/**
 * Created by luog on 15/7/17.
 */
public class MacroDefineProcessor implements ContextProcessor<TinyTemplateParser.Macro_directiveContext> {

    public Class<TinyTemplateParser.Macro_directiveContext> getType() {
        return TinyTemplateParser.Macro_directiveContext.class;
    }

    public boolean processChildren() {
        return false;
    }

    public Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, TinyTemplateParser.Macro_directiveContext parseTree, TemplateContext context, Writer writer, TemplateInterpretEngine engine) throws Exception {
        String name = parseTree.getChild(0).getText();
        MacroFromContext macroFromContext = new MacroFromContext(interpreter, name, parseTree);
        if (parseTree.define_expression_list() != null) {
            for (TinyTemplateParser.Define_expressionContext exp : parseTree.define_expression_list().define_expression()) {
                if (exp.expression() == null) {
                    macroFromContext.addParameter(exp.IDENTIFIER().getText(), null);
                } else {
                    macroFromContext.addParameter(exp.IDENTIFIER().getText(), new EvaluateExpressionImpl(interpreter, engine, templateFromContext, exp.expression()));
                }
            }
        }
        return null;
    }


}

