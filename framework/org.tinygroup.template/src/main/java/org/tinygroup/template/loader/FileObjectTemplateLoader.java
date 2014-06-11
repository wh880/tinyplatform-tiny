package org.tinygroup.template.loader;

import org.tinygroup.commons.file.IOUtils;
import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateException;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.VFS;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by luoguo on 2014/6/9.
 */
public class FileObjectTemplateLoader extends AbstractTemplateLoader<FileObject> {
    private FileObject root = null;
    private Map<String, FileObject> pathMap = new ConcurrentHashMap<String, FileObject>();

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
        if (fileObject != null) {
            if (!fileObject.isExist()) {
                //如果已经被删除
                pathMap.remove(path);
                getTemplateMap().remove(fileObject);
                return null;
            } else {
                //返回缓冲的对象
                return super.getTemplateMap().get(fileObject);
            }
        } else {
            //如果没有渲染过
            fileObject = root.getFileObject(path);
            if (fileObject != null) {
                //对文件进行重新加载
                return loadTemplate(path, fileObject, getTemplateEngineClassLoader());
            }
        }
        return null;
    }


    public FileObject getResource(String path) {
        return root.getFileObject(path);
    }

    @Override
    public String getResourceContent(String path, String encode) throws TemplateException {
        FileObject fileObject=getResource(path);
        if(fileObject!=null) {
            try {
                return IOUtils.readFromInputStream(fileObject.getInputStream(),encode);
            } catch (Exception e) {
                throw new TemplateException(e);
            }
        }
        return null;
    }

    private Template loadTemplate(String path, FileObject fileObject, ClassLoader classLoader) {
        try {
            Template template = TemplateCompilerUtils.compileTemplate(classLoader, IOUtils.readFromInputStream(fileObject.getInputStream(), getTemplateEngine().getEncode()), fileObject.getPath());
            putTemplate(fileObject, template);
            pathMap.put(path, fileObject);
            return template;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
