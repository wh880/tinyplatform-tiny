package org.tinygroup.template.interpret;

import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.impl.TemplateContextDefault;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.loader.StringResourceLoader;

import java.io.OutputStreamWriter;

/**
 * Created by luoguo on 15/7/22.
 */
public class TestInterpret {
    public static void main(String[] args) throws TemplateException {
        TemplateContext context=new TemplateContextDefault();
        StringResourceLoader loader = new StringResourceLoader();
        TemplateEngineDefault engine =new TemplateEngineDefault();
        engine.addResourceLoader(loader);
        Template template = loader.createTemplate("#macro bbb()ddd #end #macro abc()#bbb()-abc111#end #abc()");
        template.render(context,new OutputStreamWriter(System.out));
        System.out.flush();
    }
}
