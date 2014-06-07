package org.tinygroup.template.parser.template;

import org.tinygroup.template.*;
import org.tinygroup.template.impl.AbstractMacro;
import org.tinygroup.template.impl.AbstractTemplate;
import org.tinygroup.template.impl.TemplateContextImpl;
import org.tinygroup.template.impl.TemplateEngineImpl;
import org.tinygroup.template.rumtime.U;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class test7 extends AbstractTemplate{
    {
        getMacroMap().put("aa", new aa());
    }
    class aa extends AbstractMacro {
        public aa() {
            String[] args = {"a"};
            init("%s", args);
        }
        protected void renderTemplate(Template $template, TemplateContext $context, Writer $writer) throws IOException, TemplateException{
            Macro $macro=null;
            TemplateContext $newContext=null;
            write($writer,"\r\nThis is ");
            write($writer,U.c($context,"a"));
            write($writer,"\r\n");
            $macro=getTemplateEngine().findMacro("bodyContent",$template,$context);
            $newContext = new TemplateContextImpl();
            $context.putSubContext("$newContext",$newContext);
            getTemplateEngine().renderMacro($macro, $template, $newContext, $writer);
            $context.removeSubContext("$newContext");
            write($writer,"\r\n");
        }
    }
    protected void renderTemplate(TemplateContext $context, Writer $writer) throws IOException, TemplateException{
        Macro $macro=null;
        Template $template=this;
        TemplateContext $newContext=null;
        write($writer,"\r\n\r\n");
        $macro=getTemplateEngine().findMacro("aa",$template,$context);
        $newContext=new TemplateContextImpl();
        $context.putSubContext("$newContext",$newContext);
        $newContext.put($macro.getParameterNames()[0],3);
        $newContext.put("bodyContent",new AbstractMacro() {
            protected void renderTemplate(Template $template, TemplateContext $context, Writer $writer) throws IOException, TemplateException{
                Macro $macro=null;
                TemplateContext $newContext=null;
                write($writer,"\r\naaa\r\n");
            }
        });
        getTemplateEngine().renderMacro("aa", $template, $newContext, $writer);
        $context.removeSubContext("$newContext");
    }
    public String getPath(){
        return "/template/test7.vm";
    }
    public static void main(String[] args) throws TemplateException {
        TemplateEngine engine=new TemplateEngineImpl();
        Template template=engine.addTemplate(new test7());
       template.render(null,new OutputStreamWriter(System.out));
    }
}