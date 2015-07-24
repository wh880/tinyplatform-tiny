package org.tinygroup.template.interpret.context;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.interpret.ContextProcessor;
import org.tinygroup.template.interpret.TemplateFromContext;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.interpret.TemplateInterpreter;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;

import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luog on 15/7/17.
 */
public class MapProcessor implements ContextProcessor<TinyTemplateParser.Expr_hash_mapContext> {


    public Class<TinyTemplateParser.Expr_hash_mapContext> getType() {
        return TinyTemplateParser.Expr_hash_mapContext.class;
    }

    public boolean processChildren() {
        return false;
    }

    public Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, TinyTemplateParser.Expr_hash_mapContext parseTree, TemplateContext pageContext, TemplateContext context, TemplateEngineDefault engine, Writer writer) throws Exception {
        List<TinyTemplateParser.ExpressionContext> expressions = parseTree.hash_map_entry_list().expression();
        List<TinyTemplateParser.ExpressionContext> expressionContexts = expressions;
        Map<String, Object> map = new HashMap<String, Object>();
        if (expressions != null) {
            for (int i = 0; i < expressions.size(); i += 2) {
                String key = interpreter.interpretTree(engine, templateFromContext, expressions.get(i), pageContext,context, writer).toString();
                String value = interpreter.interpretTree(engine, templateFromContext, expressions.get(i + 1),pageContext, context, writer).toString();
                map.put(key, value);
            }
        }
        return map;
    }

}

