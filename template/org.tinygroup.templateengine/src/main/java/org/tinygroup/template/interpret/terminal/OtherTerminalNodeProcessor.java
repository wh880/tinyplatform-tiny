package org.tinygroup.template.interpret.terminal;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.interpret.TerminalNodeProcessor;

import java.io.Writer;

/**
 * Created by luog on 15/7/17.
 */
public class OtherTerminalNodeProcessor implements TerminalNodeProcessor<TerminalNode> {


    public int getType() {
        return 0;
    }

    public boolean processChildren() {
        return false;
    }

    public Object process(TerminalNode terminalNode, TemplateContext context, Writer writer) {
        return terminalNode.getText();
    }
}
