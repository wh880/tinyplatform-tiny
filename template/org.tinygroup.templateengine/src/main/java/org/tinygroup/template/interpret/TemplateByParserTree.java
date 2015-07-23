package org.tinygroup.template.interpret;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.impl.AbstractTemplate;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by luog on 15/7/18.
 */
public class TemplateByParserTree extends AbstractTemplate {
    private final TinyTemplateParser.TemplateContext tree;

    public TemplateByParserTree(TinyTemplateParser.TemplateContext tree) {
        this.tree = tree;
    }

    @Override
    protected void renderContent(TemplateContext context, Writer writer) throws IOException, TemplateException {

    }

    public String getPath() {
        return null;
    }
}
