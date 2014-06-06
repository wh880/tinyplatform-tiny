package org.tinygroup.template.parser;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateEngine;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.impl.AbstractMacro;
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
    {
        getMacroMap().put("aaa", new aaa());
    }

    @Override
    protected void renderTemplate(TemplateContext $context, Writer writer) throws IOException, TemplateException {
        writer.write("abc");
        getTemplateEngine().executeMacro("aaa", this, $context, writer);
    }

    class aaa extends AbstractMacro {

        public aaa() {
            String[] args = {"", ""};
            init("aaa", args);
        }

        @Override
        protected void renderTemplate(TemplateContext $context, Writer writer) throws IOException {
            writer.write("def");
        }
    }

    public String getPath() {
        return "abc";
    }

    public static void main(String[] args) throws TemplateException {
        TemplateEngine engine = new TemplateEngineImpl();
        engine.addTemplate(new TestTemplate());
        engine.renderTemplate("abc", new TemplateContextImpl(), new OutputStreamWriter(System.out));
    }
}
