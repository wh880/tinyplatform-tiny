package org.tinygroup.template.interpret.context;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.interpret.*;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;

import java.io.Writer;

/**
 * Created by luog on 15/7/17.
 */
public class CallMacroWithBodyProcessor implements ContextProcessor<TinyTemplateParser.Call_macro_block_directiveContext> {


    public Class<TinyTemplateParser.Call_macro_block_directiveContext> getType() {
        return TinyTemplateParser.Call_macro_block_directiveContext.class;
    }

    public boolean processChildren() {
        return false;
    }

    public Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, TinyTemplateParser.Call_macro_block_directiveContext parseTree, TemplateContext pageContext, TemplateContext context, TemplateEngineDefault engine, Writer writer) throws Exception {
        String name = parseTree.getChild(0).getText();
        name = name.substring(2, name.length() - 1).trim();
//        MacroFromContext macro= (MacroFromContext) engine.findMacro(name,templateFromContext,context);
        interpreter.callBlockMacro(engine,templateFromContext,name,parseTree.block(),parseTree.para_expression_list(),pageContext , writer, context);
//        macro.render(templateFromContext,context,context,writer);
        return null;
    }


}
