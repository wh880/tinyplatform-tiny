package org.tinygroup.template;

import org.tinygroup.template.impl.TemplateEngineImpl;
import org.tinygroup.template.loader.FileObjectTemplateLoader;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.FileObjectProcessor;
import org.tinygroup.vfs.VFS;
import org.tinygroup.vfs.impl.filter.FileNameFileObjectFilter;

import java.io.OutputStreamWriter;

/**
 * Created by luoguo on 2014/6/7.
 */
public class JetTemplateTestCase {
    public static void main(String[] args) throws TemplateException {
        final TemplateEngine engine = new TemplateEngineImpl();
        FileObjectTemplateLoader jetSample = new FileObjectTemplateLoader("jetSample", "src/test/resources");
        engine.addTemplateLoader(jetSample);
        FileObject fileObject = VFS.resolveFile("src/test/resources");
        fileObject.foreach(new FileNameFileObjectFilter("for-else\\.jetx", true), new FileObjectProcessor() {
            @Override
            public void process(FileObject fileObject) {
                try {
                    System.out.println(fileObject.getPath());
                    engine.renderTemplate(fileObject.getPath(), null, new OutputStreamWriter(System.out));
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
