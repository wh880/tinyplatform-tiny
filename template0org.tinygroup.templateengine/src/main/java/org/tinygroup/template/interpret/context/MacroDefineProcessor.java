package org.tinygroup.template.interpret.context;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.impl.TemplateEngineDefault;
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

    public Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, TinyTemplateParser.Macro_directiveContext parseTree, TemplateContext pageContext, TemplateContext context, TemplateEngineDefault engine, Writer writer) throws Exception {
        String name =parseTree.getChild(0).getText();
        name=name.substring(6,name.length()-1).trim();
        MacroFromContext macroFromContext = new MacroFromContext(engine.interpreter, name, parseTree.block());
        if (parseTree.define_expression_list() != null) {
            for (TinyTemplateParser.Define_expressionContext exp : parseTree.define_expression_list().define_expression()) {
                if (exp.expression() == null) {
                    macroFromContext.addParameter(exp.IDENTIFIER().getText(), null);
                } else {
                    macroFromContext.addParameter(exp.IDENTIFIER().getText(), new EvaluateExpressionImpl(interpreter, engine, templateFromContext, exp.expression()));
                }
            }
        }
        templateFromContext.addMacro(macroFromContext);
        return null;
    }


}

