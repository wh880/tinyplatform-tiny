package org.tinygroup.template;

import org.tinygroup.template.impl.TemplateEngineImpl;

import java.io.OutputStreamWriter;

/**
 * Created by luoguo on 2014/6/7.
 */
public class TemplateTestCase {
    public static void main(String[] args) throws TemplateException {
        final TemplateEngine engine = new TemplateEngineImpl();
        Template template = engine.getTemplateLoader("default").createTemplate("你好");
        template.render(null, new OutputStreamWriter(System.out));
        engine.renderTemplate(template, null, new OutputStreamWriter(System.out));
    }
}
