package org.tinygroup.template.interpret.terminal;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.interpret.TemplateInterpreter;
import org.tinygroup.template.interpret.TerminalNodeProcessor;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;

import java.io.IOException;
import java.io.Writer;

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

    public Object process(TerminalNode terminalNode, TemplateContext context, Writer writer) throws IOException {
        String text = terminalNode.getText();
        TemplateInterpreter.write(writer, text.substring(3, text.length() - 3));
        return null;
    }
}
