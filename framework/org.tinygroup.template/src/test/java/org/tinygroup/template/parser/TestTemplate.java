package org.tinygroup.template.parser;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateEngine;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.impl.AbstractTemplate;
import org.tinygroup.template.impl.TemplateContextImpl;
import org.tinygroup.template.impl.TemplateEngineImpl;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Created by luoguo on 2014/6/6.
 */
public class TestTemplate extends AbstractTemplate {

    @Override
    protected void renderTemplate(TemplateContext $context, Writer writer) throws IOException {
        writer.write("abc");
    }

    public String getPath() {
        return "abc";
    }

    public static void main(String[] args) throws TemplateException {
        TemplateEngine engine=new TemplateEngineImpl();
        engine.addTemplate(new TestTemplate());
        engine.renderTemplate("abc",new TemplateContextImpl(),new OutputStreamWriter(System.out));
    }
}
