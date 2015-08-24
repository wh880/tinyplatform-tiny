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
package org.tinygroup.template.interpret.terminal;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.interpret.TemplateFromContext;
import org.tinygroup.template.interpret.TerminalNodeProcessor;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;

import java.io.OutputStream;

/**
 * Created by luog on 15/7/17.
 */
public class TextCdataNodeProcessor implements TerminalNodeProcessor<TerminalNode> {


    public int getType() {
        return TinyTemplateParser.TEXT_CDATA;
    }

    public boolean processChildren() {
        return false;
    }

    public Object process(TerminalNode terminalNode, TemplateContext context, OutputStream outputStream, TemplateFromContext templateFromContext) throws TemplateException {
        templateFromContext.getTemplateEngine().write(outputStream, templateFromContext.getTerminalNodeBytes(terminalNode, templateFromContext, 3, 3));
        return null;
    }
}
