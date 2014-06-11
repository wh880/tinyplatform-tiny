package org.tinygroup.template.loader;

import org.tinygroup.commons.file.IOUtils;
import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateException;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.VFS;

/**
 * Created by luoguo on 2014/6/9.
 */
public class FileObjectTemplateLoader extends AbstractTemplateLoader<FileObject> {
    private FileObject root = null;

    public FileObjectTemplateLoader(String type, String root) {
        this(type, VFS.resolveFile(root));
    }

    public FileObjectTemplateLoader(String type, FileObject root) {
        super(type);
        this.root = root;
    }

    public Template createTemplate(FileObject fileObject) throws TemplateException {
        if (fileObject != null) {
            return loadTemplate(fileObject, getTemplateEngineClassLoader());
        }
        return null;
    }

    protected Template loadTemplate(final String path) throws TemplateException {
        return createTemplate(root.getFileObject(path));
    }


    @Override
    public boolean isModified(String path) {
        return root.getFileObject(path).isModified();
    }

    public String getResourceContent(String path, String encode) throws TemplateException {
        FileObject fileObject = root.getFileObject(path);
        if (fileObject != null) {
            try {
                return IOUtils.readFromInputStream(fileObject.getInputStream(), encode);
            } catch (Exception e) {
                throw new TemplateException(e);
            }
        }
        return null;
    }

    private Template loadTemplate(FileObject fileObject, ClassLoader classLoader) {
        try {
            Template template = TemplateCompilerUtils.compileTemplate(classLoader, IOUtils.readFromInputStream(fileObject.getInputStream(), getTemplateEngine().getEncode()), fileObject.getPath());
            putTemplate(fileObject.getPath(), template);
            return template;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
