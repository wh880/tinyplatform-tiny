package org.tinygroup.template.interpret.context;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.interpret.TemplateFromContext;
import org.tinygroup.template.interpret.TemplateInterpreter;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;

import java.io.Writer;

/**
 * Created by luog on 15/7/17.
 */
public class CallMacroProcessor extends AbstractCallMacroProcessor<TinyTemplateParser.Call_macro_directiveContext> {


    public Class<TinyTemplateParser.Call_macro_directiveContext> getType() {
        return TinyTemplateParser.Call_macro_directiveContext.class;
    }

    public boolean processChildren() {
        return false;
    }

    public  Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, TinyTemplateParser.Call_macro_directiveContext parseTree, TemplateContext pageContext, TemplateContext context, TemplateEngineDefault engine, Writer writer) throws Exception {
        String name = parseTree.getChild(0).getText();
        name = name.substring(1, name.length());
        if (name.endsWith("(")) {
            name = name.substring(0, name.length() - 1).trim();
        }
        callMacro(engine, templateFromContext, name, parseTree.para_expression_list(),pageContext, context, writer);
        return null;
    }

}
