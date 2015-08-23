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
package org.tinygroup.template.interpret;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.impl.AbstractTemplate;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by luog on 15/7/19.
 */
public class TemplateFromContext extends AbstractTemplate {
    Map<ParseTree, byte[]> terminalNodeMap = new ConcurrentHashMap<ParseTree, byte[]>();
    Map<ParseTree, Object> objectMap = new ConcurrentHashMap<ParseTree, Object>();
    private String path;
    TinyTemplateParser.TemplateContext templateContext;

    public String getText(ParseTree parseTree) {
        String result = (String) objectMap.get(parseTree);
        if (result == null) {
            result = parseTree.getText();
            objectMap.put(parseTree, result);
        }
        return result;
    }

    public void putObject(ParseTree parseTree, Object object) {
        objectMap.put(parseTree, object);
    }

    public <T> T getObject(ParseTree parseTree) {
        return (T) objectMap.get(parseTree);
    }

    public byte[] getTerminalNodeBytes(TerminalNode terminalNode, TemplateFromContext templateFromContext, int left, int right) throws TemplateException {
        byte[] bytes = terminalNodeMap.get(terminalNode);
        if (bytes == null) {
            try {
                String text = terminalNode.getSymbol().getText();
                if (left > 0 || right > 0) {
                    text = text.substring(left, text.length() - right);
                }
                bytes = text.getBytes(getTemplateEngine().getEncode());
                terminalNodeMap.put(terminalNode, bytes);
            } catch (UnsupportedEncodingException e) {
                throw new TemplateException(e);
            }
        }
        return bytes;
    }

    public TemplateFromContext(String path, TinyTemplateParser.TemplateContext templateContext) {
        this.path = path;
        this.templateContext = templateContext;
    }

    @Override
    protected void renderContent(TemplateContext context, OutputStream outputStream) throws IOException, TemplateException {
        try {
            TemplateEngineDefault templateEngine = (TemplateEngineDefault) getTemplateEngine();
            templateEngine.interpreter.interpret(templateEngine, this, templateContext, context, context, outputStream, path);
        } catch (StopException e) {
            //Do Nothing
        } catch (ReturnException e) {
            //Do Nothing
        } catch (TemplateException e) {
            throw e;
        } catch (Exception e) {
            throw new TemplateException(e);
        }
    }

    public String getPath() {
        return path;
    }
}
