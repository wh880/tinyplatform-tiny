package org.tinygroup.template.interpret;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.impl.EvaluateExpression;
import org.tinygroup.template.interpret.TemplateFromContext;
import org.tinygroup.template.interpret.TemplateInterpretEngine;
import org.tinygroup.template.interpret.TemplateInterpreter;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;

/**
 * Created by luog on 15/7/20.
 */
public class EvaluateExpressionImpl implements EvaluateExpression {
    private final TinyTemplateParser.ExpressionContext expressionContext;
    private final TemplateInterpretEngine engine;
    private final TemplateFromContext templateFromContext;
    private final TemplateInterpreter interpreter;

    public EvaluateExpressionImpl(TemplateInterpreter interpreter, TemplateInterpretEngine engine, TemplateFromContext templateFromContext, TinyTemplateParser.ExpressionContext expressionContext) {
        this.interpreter = interpreter;
        this.expressionContext = expressionContext;
        this.engine = engine;
        this.templateFromContext = templateFromContext;
    }

    public Object evaluate(TemplateContext context) throws TemplateException {
        try {
            return interpreter.interpretTree(engine, templateFromContext, expressionContext, context, null);
        } catch (Exception e) {
            throw new TemplateException(e);
        }
    }
}

