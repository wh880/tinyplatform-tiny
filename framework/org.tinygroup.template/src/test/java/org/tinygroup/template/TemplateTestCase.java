package org.tinygroup.template;

import org.tinygroup.template.impl.TemplateEngineImpl;

import java.io.OutputStreamWriter;

/**
 * Created by luoguo on 2014/6/7.
 */
public class TemplateTestCase {
    public static void main(String[] args) throws TemplateException {
        final TemplateEngine engine = new TemplateEngineImpl();
        Template template = engine.getTemplateLoader("default").createTemplate("#for(i:[1,2,3,4,5])#for(j:[1,2,3,4,5])number ${i*j}\n#end \n #end");
        template.render(null, new OutputStreamWriter(System.out));
    }
}
