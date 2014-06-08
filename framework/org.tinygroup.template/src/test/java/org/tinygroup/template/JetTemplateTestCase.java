package org.tinygroup.template;

import org.tinygroup.template.impl.TemplateEngineImpl;
import org.tinygroup.template.resource.FileObjectTemplateResource;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.FileObjectProcessor;
import org.tinygroup.vfs.VFS;
import org.tinygroup.vfs.impl.filter.FileNameFileObjectFilter;

import java.io.File;
import java.io.OutputStreamWriter;

/**
 * Created by luoguo on 2014/6/7.
 */
public class JetTemplateTestCase {
    public static void main(String[] args) {
        final TemplateEngine engine=new TemplateEngineImpl();

        File file =new File(".");
        FileObject root= VFS.resolveFile("src/test/resources");
        root.foreach(new FileNameFileObjectFilter("context-.*\\.jetx",true),new FileObjectProcessor() {
            @Override
            public void process(FileObject fileObject) {
                try {
                    System.out.println("\n"+fileObject.getAbsolutePath());
                    FileObjectTemplateResource resource=new FileObjectTemplateResource(fileObject);
                    Template template=engine.getTemplate(resource);
                    template.render(null,new OutputStreamWriter(System.out));
                } catch (TemplateException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
