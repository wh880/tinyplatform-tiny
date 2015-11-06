/**
 * Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 * <p/>
 * Licensed under the GPL, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.gnu.org/licenses/gpl.html
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tinygroup.template.interpret.context;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.interpret.ContextProcessor;
import org.tinygroup.template.interpret.TemplateFromContext;
import org.tinygroup.template.interpret.TemplateInterpreter;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;
import org.tinygroup.template.rumtime.TemplateUtil;

import java.io.OutputStream;

/**
 * Created by luog on 15/7/17.
 */
public class MemberFunctionCallProcessor implements ContextProcessor<TinyTemplateParser.Expr_member_function_callContext> {


    public Class<TinyTemplateParser.Expr_member_function_callContext> getType() {
        return TinyTemplateParser.Expr_member_function_callContext.class;
    }


    public Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, TinyTemplateParser.Expr_member_function_callContext parseTree, TemplateContext pageContext, TemplateContext context, TemplateEngineDefault engine, OutputStream outputStream, String fileName) throws Exception {
        try {
            Object object = interpreter.interpretTree(engine, templateFromContext, parseTree.expression(), pageContext, context, outputStream, fileName);
            String name = templateFromContext.getObject(parseTree);
            if (name == null) {
                name = parseTree.IDENTIFIER().getSymbol().getText();
                templateFromContext.putObject(parseTree, name);
            }
            Object[] paraList = null;
            if (parseTree.expression_list() != null) {
                paraList = new Object[parseTree.expression_list().expression().size()];
                int i = 0;
                for (TinyTemplateParser.ExpressionContext expr : parseTree.expression_list().expression()) {
                    paraList[i++] = interpreter.interpretTree(engine, templateFromContext, expr, pageContext, context, outputStream, fileName);
                }
            }
            if (parseTree.getChild(1).getText().equals(".")) {
            	if(object==null){
            		//增加静态类逻辑
                	object = TemplateUtil.getVariableValue(templateFromContext.getTemplateEngine().getTemplateContext(), parseTree.getChild(0).getText());
            	}
                if (object == null) {
                    throw new TemplateException("调用成员函数[" + name + "]的对象不能为空", parseTree, templateFromContext.getPath());
                }
                return TemplateUtil.callMethod(templateFromContext, context, object, name, paraList);
            } else {
                return TemplateUtil.safeCallMethod(templateFromContext, context, object, name, paraList);
            }
        } catch (TemplateException e) {
            e.setContext(parseTree, fileName);
            throw e;
        }
    }
}
