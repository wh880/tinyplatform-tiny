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
package org.tinygroup.template.interpret.context;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.interpret.ContextProcessor;
import org.tinygroup.template.interpret.TemplateFromContext;
import org.tinygroup.template.interpret.TemplateInterpreter;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;

import java.io.Writer;

/**
 * Created by luog on 15/7/17.
 */
public class SetProcessor implements ContextProcessor<TinyTemplateParser.Set_directiveContext> {


    public Class<TinyTemplateParser.Set_directiveContext> getType() {
        return TinyTemplateParser.Set_directiveContext.class;
    }

    public boolean processChildren() {
        return false;
    }


    public Object process(TemplateInterpreter interpreter, TemplateFromContext template, TinyTemplateParser.Set_directiveContext parseTree, TemplateContext pageContext, TemplateContext context, TemplateEngineDefault engine, Writer writer) throws Exception {
        boolean localVar = parseTree.getChild(0).getText().startsWith("#set");
        for (TinyTemplateParser.Set_expressionContext exp : parseTree.set_expression()) {
            String key = interpreter.interpretTree(engine, template, exp.IDENTIFIER(), pageContext, context, writer).toString();
            Object value = interpreter.interpretTree(engine, template, exp.expression(), pageContext, context, writer);
            if (localVar) {
                context.put(key, value);
            } else {
                pageContext.put(key, value);
            }
        }
        return null;
    }
}

