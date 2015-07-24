package org.tinygroup.template.interpret;

import org.tinygroup.template.*;
import org.tinygroup.template.impl.EvaluateExpression;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luog on 15/7/19.
 */
public class MacroFromContext implements Macro {
    private final String name;
    private final TemplateInterpreter interpreter;
    private final TinyTemplateParser.BlockContext blockContext;
    private List<String> parameterNames = new ArrayList<String>();
    private List<EvaluateExpression> parameterDefaultValues = new ArrayList<EvaluateExpression>();
    private TemplateEngineDefault templateEngineDefault;

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
        return templateEngineDefault;
    }

    public void setTemplateEngine(TemplateEngine engine) {
        this.templateEngineDefault = (TemplateEngineDefault) engine;
    }

    public void render(Template templateFromContext, TemplateContext pageContext, TemplateContext context, Writer writer) throws TemplateException {
        try {
            interpreter.interpretTree(templateEngineDefault, (TemplateFromContext) templateFromContext, blockContext, pageContext, context, writer);
        } catch (TemplateException te) {
            throw te;
        } catch (Exception e) {
            throw new TemplateException(e);
        }
    }
}

