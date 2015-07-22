package org.tinygroup.template.interpret;

import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.impl.TemplateContextDefault;
import org.tinygroup.template.interpret.loader.FileObjectInterpretResourceLoader;
import org.tinygroup.template.interpret.loader.StringInterpretResourceLoader;
import org.tinygroup.vfs.VFS;
import org.tinygroup.vfs.impl.FileObjectImpl;

import java.io.OutputStreamWriter;

/**
 * Created by luoguo on 15/7/22.
 */
public class TestInterpretFileObject {
    public static void main(String[] args) throws TemplateException {
        TemplateContext context=new TemplateContextDefault();
        FileObjectInterpretResourceLoader loader = new FileObjectInterpretResourceLoader("page","layout","component","/Users/luoguo/resourceroot/");
        TemplateInterpretEngine engine =new TemplateInterpretEngine();
        engine.addResourceLoader(loader);
        engine.registerMacroLibrary("/a.component");
//        Template template = loader.createTemplate(VFS.resolveFile("/Users/luoguo/resourceroot/b.page"));
//        template.render(context,new OutputStreamWriter(System.out));
        engine.renderTemplate("/b.page");
        System.out.flush();
    }
}
