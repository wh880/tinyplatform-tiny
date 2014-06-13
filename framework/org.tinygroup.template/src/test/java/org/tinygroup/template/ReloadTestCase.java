package org.tinygroup.template;

import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.loader.FileObjectResourceLoader;

/**
 * Created by luoguo on 2014/6/7.
 */
public class ReloadTestCase {
    public static void main(String[] args) throws TemplateException {
        final TemplateEngine engine = new TemplateEngineDefault();
        FileObjectResourceLoader jetSample = new FileObjectResourceLoader("jetx",null, "src/test/resources");
        engine.putTemplateLoader("jetSample",jetSample);
        Template template = engine.getTemplate("/template/jet/trim.jetx");
        System.out.println("======" + template);
        System.out.println("======" + template.getClass().getClassLoader());
        template.render();
        template = engine.getTemplate("/template/jet/trim.jetx");
        System.out.println("======" + template.getClass().getClassLoader());
        template.render();
    }
}
