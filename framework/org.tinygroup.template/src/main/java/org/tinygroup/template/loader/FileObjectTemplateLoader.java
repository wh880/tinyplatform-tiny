package org.tinygroup.template.loader;

import org.tinygroup.commons.file.IOUtils;
import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateException;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.FileObjectProcessor;
import org.tinygroup.vfs.VFS;
import org.tinygroup.vfs.impl.filter.EqualsPathFileObjectFilter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by luoguo on 2014/6/9.
 */
public class FileObjectTemplateLoader extends AbstractTemplateLoader<FileObject> {
    private FileObject root = null;
    Map<String, FileObject> pathMap = new ConcurrentHashMap<String, FileObject>();

    public FileObjectTemplateLoader(String type, String root) {
        this(type, VFS.resolveFile(root));
    }

    public FileObjectTemplateLoader(String type, FileObject root) {
        super(type);
        this.root = root;
    }

    public Template createTemplate(FileObject templateMaterial) throws TemplateException {
        return null;
    }

    public Template getTemplate(final String path) throws TemplateException {
        FileObject fileObject = pathMap.get(path);
        if (fileObject == null) {//如果文件缓冲里没有找到加载的文件
            root.foreach(new EqualsPathFileObjectFilter(path), new FileObjectProcessor() {
                public void process(FileObject fileObject) {
                    loadTemplate(path, fileObject,getTemplateEngineClassLoader());
                }
            });
        } else {
            if (!fileObject.isExist()) {
                //如果已经被删除
                pathMap.remove(path);
                getTemplateMap().remove(fileObject);
                return null;
            } else if (fileObject.isModified()) {
                //如果文件已经被修改
                loadTemplate(path, fileObject,getTemplateEngineClassLoader());
            }
        }
        fileObject = pathMap.get(path);
        if (fileObject != null) {
            return super.getTemplateMap().get(fileObject);
        }
        return null;
    }

    private void loadTemplate(String path, FileObject fileObject,ClassLoader classLoader) {
        try {
            Template template = TemplateCompilerUtils.compileTemplate(classLoader,IOUtils.readFromInputStream(fileObject.getInputStream(), getTemplateEngine().getEncode()), fileObject.getPath());
            putTemplate(fileObject, template);
            pathMap.put(path, fileObject);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
