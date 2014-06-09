package org.tinygroup.template;

import org.tinygroup.template.impl.TemplateEngineDefault;

/**
 * Created by luoguo on 2014/6/7.
 */
public class TemplateTestCase {

    public static void main(String[] args) throws TemplateException {
        final TemplateEngine engine = new TemplateEngineDefault();
        System.out.println(engine.executeFunction("format", null, "this is %s %s", 1, 2));
        Template template = engine.getDefaultTemplateLoader().createTemplate("#macro a2b()Hello#end  #call(format('a%sb',2))");
        template.render();
    }
}
