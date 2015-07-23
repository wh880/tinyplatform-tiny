package org.tinygroup.template.interpret;

import org.tinygroup.template.*;
import org.tinygroup.template.impl.EvaluateExpression;
import org.tinygroup.template.interpret.TemplateFromContext;
import org.tinygroup.template.interpret.TemplateInterpretEngine;
import org.tinygroup.template.interpret.TemplateInterpreter;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by luog on 15/7/19.
 */
public class MacroFromContext implements Macro {
    private final String name;
    private final TemplateInterpreter interpreter;
    private final TinyTemplateParser.BlockContext blockContext;
    private List<String> parameterNames = new ArrayList<String>();
    private List<EvaluateExpression> parameterDefaultValues = new ArrayList<EvaluateExpression>();
    private TemplateInterpretEngine templateInterpretEngine;

    public MacroFromContext(TemplateInterpreter interpreter, String name, TinyTemplateParser.BlockContext blockContext) {
        this.name = name;
        this.blockContext = blockContext;
        this.interpreter = interpreter;
    }

    public String getName() {
        return name;
    }

    public List<String> getParameterNames() {
        return parameterNames;
    }

    public void addParameter(String parameterName, EvaluateExpression defaultValue) {
        parameterNames.add(parameterName);
        parameterDefaultValues.add(defaultValue);
    }

    public String getParameterName(int index) {
        return parameterNames.get(index);
    }

    public List<EvaluateExpression> getParameterDefaultValues() {
        return parameterDefaultValues;
    }

    public TemplateEngine getTemplateEngine() {
        return templateInterpretEngine;
    }

    public void setTemplateEngine(TemplateEngine engine) {
        this.templateInterpretEngine = (TemplateInterpretEngine) engine;
    }

    public void render(Template templateFromContext, TemplateContext pageContext, TemplateContext context, Writer writer) throws TemplateException {
        try {
            interpreter.interpretTree(templateInterpretEngine, (TemplateFromContext) templateFromContext, blockContext,pageContext, context, writer);
        } catch (Exception e) {
            throw new TemplateException(e);
        }
    }
}

