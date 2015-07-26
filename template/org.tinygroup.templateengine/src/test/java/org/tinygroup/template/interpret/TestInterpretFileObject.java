package org.tinygroup.template.interpret;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.impl.TemplateContextDefault;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.loader.FileObjectResourceLoader;

/**
 * Created by luoguo on 15/7/22.
 */
public class TestInterpretFileObject {
    public static void main(String[] args) throws TemplateException {
        TemplateContext context=new TemplateContextDefault();
        FileObjectResourceLoader loader = new FileObjectResourceLoader("page","layout","component","/Users/luoguo/resourceroot/");
        TemplateEngineDefault engine =new TemplateEngineDefault();
        engine.addResourceLoader(loader);
        engine.registerMacroLibrary("/a.component");
        engine.registerMacroLibrary("/b.component");
//        Template template = loader.createTemplate(VFS.resolveFile("/Users/luoguo/resourceroot/b.page"));
//        template.render(context,new OutputStreamWriter(System.out));
        engine.renderTemplate("/b.page");
        System.out.flush();
    }
}
