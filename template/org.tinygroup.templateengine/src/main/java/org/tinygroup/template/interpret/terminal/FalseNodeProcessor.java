package org.tinygroup.template.interpret.terminal;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.interpret.TerminalNodeProcessor;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;

import java.io.Writer;

/**
 * Created by luog on 15/7/17.
 */
public class FalseNodeProcessor implements TerminalNodeProcessor<TerminalNode> {


    public int getType() {
        return TinyTemplateParser.KEYWORD_FALSE;
    }

    public boolean processChildren() {
        return false;
    }

    public Object process(TerminalNode terminalNode, TemplateContext context, Writer writer) {
        return false;
    }
}
