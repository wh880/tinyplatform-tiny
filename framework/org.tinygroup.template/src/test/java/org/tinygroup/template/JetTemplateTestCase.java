package org.tinygroup.template;

import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.loader.FileObjectResourceLoader;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.FileObjectProcessor;
import org.tinygroup.vfs.VFS;
import org.tinygroup.vfs.impl.filter.FileNameFileObjectFilter;

/**
 * Created by luoguo on 2014/6/7.
 */
public class JetTemplateTestCase {
    public static void main(String[] args) throws TemplateException {
        final TemplateEngine engine = new TemplateEngineDefault();
        FileObjectResourceLoader jetSample = new FileObjectResourceLoader("jetx",null, "src/test/resources");
        engine.putTemplateLoader("jetSample",jetSample);
        FileObject fileObject = VFS.resolveFile("src/test/resources");
        fileObject.foreach(new FileNameFileObjectFilter(".*\\.jetx", true), new FileObjectProcessor() {

            public void process(FileObject fileObject) {
                try {
                    System.out.println("\n" + fileObject.getPath());
                    engine.renderTemplate(fileObject.getPath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Template template = engine.getDefaultTemplateLoader().createTemplate("${getResourceContent('/template/jet/constant-string.jetx')}");
        template.render();
    }
}
