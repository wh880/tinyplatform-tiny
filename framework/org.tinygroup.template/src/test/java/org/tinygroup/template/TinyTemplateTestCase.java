package org.tinygroup.template;

import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.loader.FileObjectResourceLoader;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.VFS;

/**
 * Created by luoguo on 2014/6/7.
 */
public class TinyTemplateTestCase {
    public static void main(String[] args) throws TemplateException {
        final TemplateEngine engine = new TemplateEngineDefault();
        FileObjectResourceLoader tinySample = new FileObjectResourceLoader("html",null, "src/test/resources");
        engine.putTemplateLoader("TinySample",tinySample);
        FileObject fileObject = VFS.resolveFile("src/test/resources");
 /*       fileObject.foreach(new FileNameFileObjectFilter(".*\\.vm", true), new FileObjectProcessor() {

            public void process(FileObject fileObject) {
                try {
                    System.out.println("\n" + fileObject.getPath());
                    engine.renderTemplate(fileObject.getPath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });*/
        System.out.println("======");
        engine.renderTemplate("/template/tiny/test1.html");


    }
}
