package org.tinygroup.template.loader;

import org.tinygroup.commons.file.IOUtils;
import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateException;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.FileObjectProcessor;
import org.tinygroup.vfs.VFS;
import org.tinygroup.vfs.impl.filter.EqualsPathFileObjectFilter;

/**
 * Created by luoguo on 2014/6/9.
 */
public class FileObjectTemplateLoader extends AbstractTemplateLoader<FileObject> {
    private FileObject root=null;

    public FileObjectTemplateLoader(String type,String root) {
        this(type, VFS.resolveFile(root));
    }
    public FileObjectTemplateLoader(String type,FileObject root) {
        super(type);
        this.root=root;
    }

    public Template createTemplate(FileObject templateMaterial) throws TemplateException {
        return null;
    }

    public Template getTemplate(String path) {
        final Template[] template = {super.getTemplate(path)};
        if(template[0] ==null){//如果不存在，则说明要查找并构建之
            root.foreach(new EqualsPathFileObjectFilter(path),new FileObjectProcessor() {

                public void process(FileObject fileObject) {
                    //这里载入之
                    try {
                        template[0] =TemplateCompilerUtils.compileTemplate(IOUtils.readFromInputStream(fileObject.getInputStream(),getTemplateEngine().getEncode()),fileObject.getPath());
                        putTemplate(template[0]);
                    } catch (Exception e) {
                       throw new RuntimeException(e);
                        //DO Nothing
                    }
                }
            });
        }
        return template[0];
    }
}
