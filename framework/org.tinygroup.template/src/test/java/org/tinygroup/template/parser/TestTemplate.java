package org.tinygroup.template.parser;

import org.tinygroup.template.*;
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
        Macro $macro=null;
        Template $template=null;
        TemplateContext $newContext=null;
        writer.write("abc");
        $macro=getTemplateEngine().findMacro("aaa",this);
        $newContext=new TemplateContextImpl();
        $context.putSubContext("$newContext", $newContext);
        $newContext.put("aa",1);
        $newContext.put($macro.getParameterNames()[1],1);
        getTemplateEngine().renderMacro("aaa", this, $newContext, writer);
        $context.removeSubContext("$newContext");
    }

    class aaa extends AbstractMacro {

        public aaa() {
            String[] args = {"", ""};
            init("aaa", args);
        }

        @Override
        protected void renderTemplate(Template template, TemplateContext $context, Writer writer) throws IOException {
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
