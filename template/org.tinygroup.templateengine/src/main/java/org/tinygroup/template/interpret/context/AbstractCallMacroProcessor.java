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

import org.antlr.v4.runtime.ParserRuleContext;
import org.tinygroup.template.Macro;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.impl.TemplateContextDefault;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.interpret.ContextProcessor;
import org.tinygroup.template.interpret.TemplateFromContext;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;

import java.io.Writer;
import java.util.Stack;

/**
 * Created by luoguo on 15/7/26.
 */
public abstract class AbstractCallMacroProcessor<T extends ParserRuleContext> implements ContextProcessor<T> {
    public void callMacro(TemplateEngineDefault engine, TemplateFromContext templateFromContext, String name, TinyTemplateParser.Para_expression_listContext paraList, TemplateContext pageContext, TemplateContext context, Writer writer) throws Exception {
        callBlockMacro(engine,templateFromContext,name,null,paraList,pageContext,writer,context);
    }

    public void callBlockMacro(TemplateEngineDefault engine, TemplateFromContext templateFromContext, String name, TinyTemplateParser.BlockContext block, TinyTemplateParser.Para_expression_listContext paraList, TemplateContext pageContext, Writer writer, TemplateContext context) throws Exception {
        Macro macro = engine.findMacro(name, templateFromContext, context);
        TemplateContext newContext = new TemplateContextDefault();
        newContext.setParent(context);
        if (paraList != null) {
            int i = 0;
            for (TinyTemplateParser.Para_expressionContext para : paraList.para_expression()) {
                if (para.getChildCount() == 3) {
                    //如果是带参数的
                    newContext.put(para.IDENTIFIER().getText(), engine.interpreter.interpretTree(engine, templateFromContext, para.expression(), pageContext, context, writer,templateFromContext.getPath()));
                } else {
                    if(i>=macro.getParameterNames().size()){
                        throw new TemplateException("参数数量超过宏<"+macro.getName()+">允许接受的数量",paraList,templateFromContext.getPath());
                    }
                    newContext.put(macro.getParameterName(i), engine.interpreter.interpretTree(engine, templateFromContext, para.expression(), pageContext, context, writer,templateFromContext.getPath()));
                }
                i++;
            }
        }

        Stack<TinyTemplateParser.BlockContext> stack = context.get("$bodyContent");
        if (stack == null) {
            stack = new Stack<TinyTemplateParser.BlockContext>();
            newContext.put("$bodyContent", stack);
        }
        stack.push(block);
        int stackSize=stack.size();
        macro.render(templateFromContext, pageContext, newContext, writer);
        if(stack.size()==stackSize){
            //检查是否有#bodyContent,如果没有,主要主动弹出刚才放的空bodyContent
            stack.pop();
        }
    }
}
