package org.tinygroup.template.interpret;

import org.antlr.v4.runtime.tree.ParseTree;
import org.tinygroup.template.TemplateContext;

import java.io.Writer;

/**
 * Created by luog on 15/7/17.
 */
public interface TerminalNodeProcessor<T extends ParseTree> {
    int getType();
    Object process(T parseTree, TemplateContext context, Writer writer) throws Exception;
}
