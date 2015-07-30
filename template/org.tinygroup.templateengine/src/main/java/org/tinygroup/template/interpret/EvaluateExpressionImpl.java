/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tinygroup.template.interpret;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.impl.EvaluateExpression;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;

/**
 * Created by luog on 15/7/20.
 */
public class EvaluateExpressionImpl implements EvaluateExpression {
    private final TinyTemplateParser.ExpressionContext expressionContext;
    private final TemplateEngineDefault engine;
    private final TemplateFromContext templateFromContext;
    private final TemplateInterpreter interpreter;

    public EvaluateExpressionImpl(TemplateInterpreter interpreter, TemplateEngineDefault engine, TemplateFromContext templateFromContext, TinyTemplateParser.ExpressionContext expressionContext) {
        this.interpreter = interpreter;
        this.expressionContext = expressionContext;
        this.engine = engine;
        this.templateFromContext = templateFromContext;
    }

    public Object evaluate(TemplateContext context) throws TemplateException {
        try {
            return interpreter.interpretTree(engine, templateFromContext, expressionContext,context, context, null,templateFromContext.getPath());
        } catch (Exception e) {
            throw new TemplateException(e);
        }
    }
}

