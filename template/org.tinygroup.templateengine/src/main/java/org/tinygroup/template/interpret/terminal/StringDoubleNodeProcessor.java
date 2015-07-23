package org.tinygroup.template.interpret.terminal;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.interpret.TerminalNodeProcessor;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;

import java.io.Writer;

/**
 * Created by luog on 15/7/17.
 */
public class StringDoubleNodeProcessor implements TerminalNodeProcessor<TerminalNode> {


    public int getType() {
        return TinyTemplateParser.STRING_DOUBLE;
    }

    public boolean processChildren() {
        return false;
    }

    public Object process(TerminalNode terminalNode, TemplateContext context, Writer writer) {
        String text=terminalNode.getText();
        text=text.replaceAll("[\\\\][\\\\]","\\\\");
        text=text.replaceAll("\\\\\"","\"");
        return text.substring(1, text.length() - 1);
    }

}
