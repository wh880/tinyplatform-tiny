package org.tinygroup.template.interpret;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.tinygroup.template.Macro;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.impl.TemplateContextDefault;
import org.tinygroup.template.interpret.terminal.OtherTerminalNodeProcessor;
import org.tinygroup.template.parser.grammer.TinyTemplateLexer;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;

import java.io.IOException;
import java.io.OutputStreamWriter;
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

    public void interpret(TemplateInterpretEngine engine, TemplateFromContext templateFromContext, String templateString, String sourceName, TemplateContext context, Writer writer) throws Exception {
        interpret(engine, templateFromContext, parserTemplateTree(sourceName, templateString), context, writer);
        writer.flush();
    }

    public void interpret(TemplateInterpretEngine engine, TemplateFromContext templateFromContext, TinyTemplateParser.TemplateContext templateParseTree, TemplateContext context, Writer writer) throws Exception {
        for (int i = 0; i < templateParseTree.getChildCount(); i++) {
            interpretTree(engine, templateFromContext, templateParseTree.getChild(i), context, writer);
        }
    }

    public Object interpretTree(TemplateInterpretEngine engine, TemplateFromContext templateFromContext, ParseTree tree, TemplateContext context, Writer writer) throws Exception {
        Object returnValue = null;
        if (tree instanceof TerminalNode) {
            TerminalNode terminalNode = (TerminalNode) tree;
            TerminalNodeProcessor processor = terminalNodeProcessors[terminalNode.getSymbol().getType()];
            if (processor != null) {
                returnValue = processor.process(terminalNode, context, writer);
            } else {
                returnValue = otherNodeProcessor.process(terminalNode, context, writer);
            }
        } else if (tree instanceof ParserRuleContext) {
            ContextProcessor processor = contextProcessorMap.get(tree.getClass());
            if (processor != null) {
                returnValue = processor.process(this, templateFromContext, (ParserRuleContext) tree, context, writer, engine);
            }
            if (processor == null || processor != null && processor.processChildren()) {
                for (int i = 0; i < tree.getChildCount(); i++) {
                    Object value = interpretTree(engine, templateFromContext, tree.getChild(i), context, writer);
                    if (value != null) {
                        returnValue = value;
                    }
                }
            }

        } else {
            for (int i = 0; i < tree.getChildCount(); i++) {
                Object value = interpretTree(engine, templateFromContext, tree.getChild(i), context, writer);
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

    public void callMacro(TemplateInterpretEngine engine, TemplateFromContext templateFromContext, String name, TinyTemplateParser.Para_expression_listContext paraList, TemplateContext context, Writer writer) throws Exception {
        Macro macro = engine.findMacro(name,templateFromContext,context);
        TemplateContext newContext = new TemplateContextDefault();
        newContext.setParent(context);
        TinyTemplateParser.Para_expression_listContext expList = paraList;
        if (expList != null) {
            int i = 0;
            for (TinyTemplateParser.Para_expressionContext para : expList.para_expression()) {
                if (para.getChildCount() == 3) {
                    //如果是带参数的
                    newContext.put(para.IDENTIFIER().getText(), interpretTree(engine, templateFromContext, para.expression(), context, writer));
                } else {
                    newContext.put(macro.getParameterName(i), interpretTree(engine, templateFromContext, para.expression(), context, writer));
                }
                i++;
            }
        }
        macro.render(null, null, newContext, writer);
    }

    public void callBlockMacro(TemplateInterpretEngine engine, TemplateFromContext templateFromContext, String name, TinyTemplateParser.BlockContext block, TinyTemplateParser.Para_expression_listContext paraList, TemplateContext context, Writer writer) throws Exception {
        Macro macro = engine.findMacro(name,templateFromContext,context);
        TemplateContext newContext = new TemplateContextDefault();
        newContext.setParent(context);
        if (paraList != null) {
            int i = 0;
            for (TinyTemplateParser.Para_expressionContext para : paraList.para_expression()) {
                if (para.getChildCount() == 3) {
                    //如果是带参数的
                    newContext.put(para.IDENTIFIER().getText(), interpretTree(engine, templateFromContext, para.expression(), context, writer));
                } else {
                    newContext.put(macro.getParameterName(i), interpretTree(engine, templateFromContext, para.expression(), context, writer));
                }
                i++;
            }
        }
        newContext.put("$bodyContent", block);
        macro.render(null, null, newContext, writer);
    }

}
