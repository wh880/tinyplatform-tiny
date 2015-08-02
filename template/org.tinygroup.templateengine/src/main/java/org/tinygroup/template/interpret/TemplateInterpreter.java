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

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.interpret.terminal.OtherTerminalNodeProcessor;
import org.tinygroup.template.parser.grammer.TinyTemplateLexer;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by luog on 15/7/17.
 */
public class TemplateInterpreter {
    TerminalNodeProcessor[] terminalNodeProcessors = new TerminalNodeProcessor[200];
    Map<Class<ParserRuleContext>, ContextProcessor> contextProcessorMap = new HashMap<Class<ParserRuleContext>, ContextProcessor>();
    OtherTerminalNodeProcessor otherNodeProcessor = new OtherTerminalNodeProcessor();


    public void addTerminalNodeProcessor(TerminalNodeProcessor processor) {
        terminalNodeProcessors[processor.getType()] = processor;
    }

    public void addContextProcessor(ContextProcessor contextProcessor) {
        contextProcessorMap.put(contextProcessor.getType(), contextProcessor);
    }

    public TinyTemplateParser.TemplateContext parserTemplateTree(String sourceName, String templateString) {
        char[] source = templateString.toCharArray();
        ANTLRInputStream is = new ANTLRInputStream(source, source.length);
        // set source file name, it will be displayed in error report.
        is.name = sourceName;
        TinyTemplateParser parser = new TinyTemplateParser(new CommonTokenStream(new TinyTemplateLexer(is)));
        return parser.template();
    }

    public void interpret(TemplateEngineDefault engine, TemplateFromContext templateFromContext, String templateString, String sourceName, TemplateContext pageContext, TemplateContext context, Writer writer, String fileName) throws Exception {
        interpret(engine, templateFromContext, parserTemplateTree(sourceName, templateString), pageContext, context, writer,fileName );
        writer.flush();
    }

    public void interpret(TemplateEngineDefault engine, TemplateFromContext templateFromContext, TinyTemplateParser.TemplateContext templateParseTree, TemplateContext pageContext, TemplateContext context, Writer writer, String fileName) throws Exception {
        for (int i = 0; i < templateParseTree.getChildCount(); i++) {
            interpretTree(engine, templateFromContext, templateParseTree.getChild(i), pageContext, context, writer,fileName );
        }
    }

    public Object interpretTree(TemplateEngineDefault engine, TemplateFromContext templateFromContext, ParseTree tree, TemplateContext pageContext, TemplateContext context, Writer writer, String fileName) throws Exception {
        Object returnValue = null;
        if (tree instanceof TerminalNode) {
            TerminalNode terminalNode = (TerminalNode) tree;
            TerminalNodeProcessor processor = terminalNodeProcessors[terminalNode.getSymbol().getType()];
            if (processor != null) {
                return processor.process(terminalNode, context, writer);
            } else {
                return otherNodeProcessor.process(terminalNode, context, writer);
            }
        } else if (tree instanceof ParserRuleContext) {
            try {
                ContextProcessor processor = contextProcessorMap.get(tree.getClass());
                if (processor != null) {
                    returnValue = processor.process(this, templateFromContext, (ParserRuleContext) tree, pageContext, context, engine, writer,fileName);
                }
                if (processor == null ) {
                    for (int i = 0; i < tree.getChildCount(); i++) {
                        Object value = interpretTree(engine, templateFromContext, tree.getChild(i), pageContext, context, writer,fileName );
                        if (value != null) {
                            returnValue = value;
                        }
                    }
                }
            } catch (StopException se) {
                throw se;
            } catch (TemplateException te) {
                if (te.getContext() == null) {
                    te.setContext((ParserRuleContext) tree,fileName);
                }
                throw te;
            } catch (Exception e) {
                throw new TemplateException(e, (ParserRuleContext) tree,fileName);
            }
        } else {
            //这段应该是没有用的
            for (int i = 0; i < tree.getChildCount(); i++) {
                Object value = interpretTree(engine, templateFromContext, tree.getChild(i), pageContext, context, writer,fileName );
                if (returnValue == null && value != null) {
                    returnValue = value;
                }
            }
        }
        return returnValue;
    }

    public static void write(Writer writer, Object object) throws IOException {
        if (object != null) {
            writer.write(object.toString());
        }
    }


}
