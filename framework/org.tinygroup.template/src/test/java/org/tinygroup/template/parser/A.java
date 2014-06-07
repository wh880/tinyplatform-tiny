package org.tinygroup.template.parser;

import org.tinygroup.template.Macro;
import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.impl.AbstractTemplate;
import org.tinygroup.template.rumtime.ForIterator;

import java.io.IOException;
import java.io.Writer;
public class A extends AbstractTemplate{
    {
    }
    protected void renderTemplate(TemplateContext $context, Writer $writer) throws IOException, TemplateException{
        Macro $macro=null;
        Template $template=this;
        TemplateContext $newContext=null;
        ForIterator $aFor = new ForIterator(new Object[]{1,2,3,4,5});
        $context.put("$aFor",$aFor);
        while($aFor.hasNext()){
            $context.put("a",$aFor.next());
        }
        $context.remove("$:For");
        $context.remove(":");
    }
    public String getPath(){
        return "hello/hello";
    }
}