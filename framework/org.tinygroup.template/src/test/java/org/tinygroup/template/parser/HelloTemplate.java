package org.tinygroup.template.parser;

import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateEngine;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.impl.TemplateContextImpl;
import org.tinygroup.template.impl.TemplateEngineImpl;
import org.tinygroup.template.resource.StringTemplateResource;

import java.io.OutputStreamWriter;

/**
 * Created by luoguo on 2014/6/7.
 */
public class HelloTemplate {
    public static void main(String[] args) throws TemplateException {
        TemplateEngine engine=new TemplateEngineImpl();
        StringTemplateResource resource=new StringTemplateResource("hello/hello","你好,$name");
        Template template=engine.getTemplate(resource);
        TemplateContext context=new TemplateContextImpl();
        context.put("name","悠然");
        template.render(context,new OutputStreamWriter(System.out));
    }
}
