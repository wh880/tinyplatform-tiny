package org.tinygroup.template.interpret.context;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.interpret.ContextProcessor;
import org.tinygroup.template.interpret.TemplateFromContext;
import org.tinygroup.template.interpret.TemplateInterpreter;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;
import org.tinygroup.template.rumtime.U;

import java.io.Writer;

/**
 * Created by luog on 15/7/17.
 */
public class FieldProcessor implements ContextProcessor<TinyTemplateParser.Expr_field_accessContext> {

    public Class<TinyTemplateParser.Expr_field_accessContext> getType() {
        return TinyTemplateParser.Expr_field_accessContext.class;
    }

    public boolean processChildren() {
        return false;
    }

    public Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, TinyTemplateParser.Expr_field_accessContext parseTree, TemplateContext pageContext, TemplateContext context, TemplateEngineDefault engine, Writer writer) throws Exception {
        Object a = interpreter.interpretTree(engine, templateFromContext, parseTree.expression(), pageContext,context, writer);
        String fieldName=parseTree.IDENTIFIER().getText();
        if(parseTree.getChild(1).getText().startsWith("?")){
            return U.sp(a,fieldName);
        }else{
            return U.p(a,fieldName);
        }
    }

}
