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

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.impl.AbstractTemplate;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;
import org.tinygroup.template.rumtime.TemplateUtil;

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
    private static String[] stripWithSpaceChars = {" ","    "};// 空格 \t

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
                String text = terminalNode.getText();

                // 紧凑模型
                if (getTemplateEngine().isCompactMode()) {
                    // Trim 注释、Set指令和以下指令之间的空格 及 换行
                    text = trimCommentsDirectiveWhileSpaceNewLine(text, terminalNode, true);
                } else {
                    // Trim 注释、Set指令和以下指令生成的多余\r\n
                    text = trimCommentsDirectiveWhileSpaceNewLine(text, terminalNode, false);
                }

                bytes = text.getBytes(getTemplateEngine().getEncode());
                terminalNodeMap.put(terminalNode, bytes);
            } catch (UnsupportedEncodingException e) {
                throw new TemplateException(e);
            }
        }
        return bytes;
    }

    /**
     * Trim 注释、Set指令和以下指令之间的空格 及 换行
     * @param text 文本
     * @param nowTerminalNode 文本所在的节点
     * @param trimWhileSpace 是否要处理空格
     * @return
     */
    private String trimCommentsDirectiveWhileSpaceNewLine(String text, TerminalNode nowTerminalNode, boolean trimWhileSpace) {
        if (text == null || text.length() == 0) {
            return "";
        }

        if (nowTerminalNode.getParent() instanceof TinyTemplateParser.TextContext) {
            // 获取当前TextContext节点
            TinyTemplateParser.TextContext parseTree = (TinyTemplateParser.TextContext) nowTerminalNode.getParent();
            // 获取Text Context所在的父亲（或父亲的父亲）节点，且非Block
            ParserRuleContext parentParserRuleContext = getParseTrreeParentButBlock(parseTree);
            if (isDirectiveNeedTrim(parentParserRuleContext)) {// 处理需要Trim其\r\n的指令节点
                text = trimTextLeft(text);
                if (trimWhileSpace) {
                    text = TemplateUtil.trimStart(text, stripWithSpaceChars);
                    text = TemplateUtil.trimEnd(text, stripWithSpaceChars);
                }
            } else if (parentParserRuleContext instanceof TinyTemplateParser.BlockContext) {
                // 获取Text Context的BlockContext的子节点下标
                int parentChildrenIndex = getParentChildrenIndex(parseTree, parentParserRuleContext);
                // 判断该节点是否为第一个节点
                if (parentChildrenIndex > 0) {
                    // 获取其上一个节点
                    ParseTree previousParseContext = parentParserRuleContext.getChild(parentChildrenIndex - 1);
                    // 如果是注释，除去其text前面的空格
                    if ( previousParseContext instanceof TinyTemplateParser.CommentContext
                            ||(   previousParseContext instanceof TinyTemplateParser.DirectiveContext
                            && (    previousParseContext.getChild(0) instanceof TinyTemplateParser.Set_directiveContext
                            ||  previousParseContext.getChild(0) instanceof TinyTemplateParser.Blank_directiveContext
                            ||  previousParseContext.getChild(0) instanceof TinyTemplateParser.Call_macro_directiveContext
                            ||  previousParseContext.getChild(0) instanceof TinyTemplateParser.Call_macro_block_directiveContext
                            ||  previousParseContext.getChild(0) instanceof TinyTemplateParser.Import_directiveContext
                            ||  previousParseContext.getChild(0) instanceof TinyTemplateParser.If_directiveContext
                    ))) {
                        text = trimTextLeft(text);
                        if (trimWhileSpace) {
                            text = TemplateUtil.trimStart(text,stripWithSpaceChars);
                        }
                    }

                }

                if (trimWhileSpace) {
                    // 判断该节点是否为非最后节点
                    if (parentChildrenIndex < parentParserRuleContext.getChildCount()) {
                        // 获取其下一个节点
                        ParseTree previousParseContext = parentParserRuleContext.getChild(parentChildrenIndex + 1);
                        // 如果是注释，除去其text后面的空格
                        if (previousParseContext instanceof TinyTemplateParser.CommentContext
                                || (previousParseContext instanceof TinyTemplateParser.DirectiveContext
                                && (previousParseContext.getChild(0) instanceof TinyTemplateParser.Set_directiveContext
                                || previousParseContext.getChild(0) instanceof TinyTemplateParser.Blank_directiveContext
                                || previousParseContext.getChild(0) instanceof TinyTemplateParser.Call_macro_directiveContext
                                || previousParseContext.getChild(0) instanceof TinyTemplateParser.Call_macro_block_directiveContext
                                || previousParseContext.getChild(0) instanceof TinyTemplateParser.Import_directiveContext
                                || previousParseContext.getChild(0) instanceof TinyTemplateParser.If_directiveContext
                        ))) {
                            text = TemplateUtil.trimEnd(text,stripWithSpaceChars);
                        }
                    }
                }
            }
        }

        return text;
    }

    /**
     * Trim 文本前缀的空格
     * @param str 文本
     * @return

    private static String trimStartWhiteSpace(String str) {
    int strLen;
    if (str != null && (strLen = str.length()) != 0) {
    int start = 0;
    while (start < strLen
    && (      " ".indexOf(str.charAt(start)) != -1          // ' '
    || "    ".indexOf(str.charAt(start)) != -1)) {      // '\t'
    ++start;
    }

    return str.substring(start);
    }
    return str;
    }*/

    /**
     * Trim 文本后缀的空格
     * @param str 文本
     * @return

    private static String trimEndWhiteSpace(String str) {
    int end;
    if (str != null && (end = str.length()) != 0) {
    while ( end != 0
    && (      " ".indexOf(str.charAt(end - 1)) != -1         // ' '
    || "    ".indexOf(str.charAt(end - 1)) != -1 )) {    // '\t'
    --end;
    }

    return str.substring(0,end);
    }
    return str;
    }*/

    /**
     * Trim TextContext左边多余的\r\n
     * @param text 文本
     * @return
     */
    private String trimTextLeft(String text) {
        int len = text.length();
        int leftPos = 0;
        for (int i = 0; i < len; i++) {
            char c = text.charAt(i);
            if (c == ' ' || c == '\t') {
                continue;
            } else if (c == '\r') {
                int n = i + 1;
                if (n < len && text.charAt(n) == '\n') {
                    leftPos = n + 1; // window: \r\n
                } else {
                    leftPos = n;     // mac: \r
                }
                break;
            } else if (c == '\n') {
                leftPos = i + 1;     // linux: \n
                break;
            } else {
                break;
            }
        }

        if (leftPos > 0) {
            if (text.equals("\r\n")) { // 处理text为'\r\n'
                text = "";
            } else {
                text = text.substring(leftPos, len);
            }
        }
        return text;
    }

    /**
     * 获取当前TextContext其父亲节点（及父亲的父亲...）的非BlockContext节点
     * @param parseTree
     * @return
     */
    private ParserRuleContext getParseTrreeParentButBlock(ParserRuleContext parseTree) {
        ParserRuleContext parentParserRuleContext = parseTree.getParent();
        if (parentParserRuleContext instanceof TinyTemplateParser.TemplateContext)
            return parentParserRuleContext;
        if (parentParserRuleContext instanceof TinyTemplateParser.BlockContext) {
            if (parentParserRuleContext.getParent() instanceof TinyTemplateParser.TemplateContext)
                return parentParserRuleContext;
            if ( parentParserRuleContext.getParent() instanceof TinyTemplateParser.BlockContext) {
                return getParseTrreeParentButBlock(parentParserRuleContext.getParent());
            } else
                return parentParserRuleContext.getParent();
        }
        return parentParserRuleContext;
    }

    /**
     * 获取当前TextContext(获取TextContext所在的BlockContext节点)节点在总树的子节点中（List）的标识位
     * @param parseTree 当前节点
     * @param parserRuleContext 总树
     * @return
     */
    private int getParentChildrenIndex(ParserRuleContext parseTree, ParserRuleContext parserRuleContext) {
        int parentChildrenIndex = 0;
        for (int i = 0; i < parserRuleContext.getChildCount(); i++) {
            ParserRuleContext childrenTree = (ParserRuleContext) parserRuleContext.getChild(i);
            if (childrenTree == parseTree) {
                parentChildrenIndex = i;
                break;
            }
        }
        return parentChildrenIndex;
    }

    /**
     * 是否需要Trim其\r\n的指令节点 === Set指令除外
     * #if #else #elseif #end #eol ${}
     * #for #foreach #while #break #continue
     * #macro
     * #@layout
     * @param parseTree 当前节点
     * @return
     */
    public boolean isDirectiveNeedTrim (ParseTree parseTree) {
        if (        parseTree instanceof TinyTemplateParser.ValueContext
                ||  parseTree instanceof TinyTemplateParser.If_directiveContext
                ||  parseTree instanceof TinyTemplateParser.For_directiveContext
                ||  parseTree instanceof TinyTemplateParser.Else_directiveContext
                ||  parseTree instanceof TinyTemplateParser.Call_directiveContext
                ||  parseTree instanceof TinyTemplateParser.While_directiveContext
                ||  parseTree instanceof TinyTemplateParser.Break_directiveContext
                ||  parseTree instanceof TinyTemplateParser.Elseif_directiveContext
                ||  parseTree instanceof TinyTemplateParser.Return_directiveContext
                ||  parseTree instanceof TinyTemplateParser.Continue_directiveContext
                ||  parseTree instanceof TinyTemplateParser.Endofline_directiveContext
                ||  parseTree instanceof TinyTemplateParser.Call_macro_block_directiveContext
                ||  parseTree instanceof TinyTemplateParser.Macro_directiveContext
                ||  parseTree instanceof TinyTemplateParser.Call_block_directiveContext
                ||  parseTree instanceof TinyTemplateParser.Layout_impl_directiveContext)
            return true;
        return false;
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
        } catch (MacroException e) {
            throw e;
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