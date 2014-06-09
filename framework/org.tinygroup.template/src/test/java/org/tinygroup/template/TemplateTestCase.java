package org.tinygroup.template;

import org.tinygroup.template.impl.TemplateEngineDefault;

import java.io.OutputStreamWriter;

/**
 * Created by luoguo on 2014/6/7.
 */
public class TemplateTestCase {
    public static void main(String[] args) throws TemplateException {
        final TemplateEngine engine = new TemplateEngineDefault();
        Template template = engine.getDefaultTemplateLoader().createTemplate("#for(i:[1,2,3,4,5])#for(j:[1,2,3,4,5])number ${i*j}\n#end#end");
        template.render(null, new OutputStreamWriter(System.out));
    }
}
