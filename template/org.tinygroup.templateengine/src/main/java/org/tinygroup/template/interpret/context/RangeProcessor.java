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
import org.tinygroup.template.rumtime.RangeList;

import java.io.Writer;

/**
 * Created by luog on 15/7/17.
 */
public class RangeProcessor implements ContextProcessor<TinyTemplateParser.Expression_rangeContext> {


    public Class<TinyTemplateParser.Expression_rangeContext> getType() {
        return TinyTemplateParser.Expression_rangeContext.class;
    }

    public boolean processChildren() {
        return false;
    }


    public Object process(TemplateInterpreter interpreter, TemplateFromContext templateFromContext, TinyTemplateParser.Expression_rangeContext parseTree, TemplateContext pageContext, TemplateContext context, TemplateEngineDefault engine, Writer writer, String fileName) throws Exception {
        Number start = (Number) interpreter.interpretTree(engine, templateFromContext, parseTree.getChild(0), pageContext, context, writer,fileName);
        Number end = (Number) interpreter.interpretTree(engine, templateFromContext, parseTree.getChild(2), pageContext,context, writer,fileName);
        return new RangeList(start.intValue(), end.intValue(), 1);
    }
}