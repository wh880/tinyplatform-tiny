package org.tinygroup.template.loader;

import org.tinygroup.commons.file.IOUtils;
import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateException;

import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by luoguo on 2014/6/11.
 */
public class ClassLoaderResourceLoader extends AbstractResourceLoader<String> {
    private ClassLoader classLoader;

    public ClassLoaderResourceLoader(String templateExtName, String layoutExtName, String macroLibraryExtName) {
        super(templateExtName, layoutExtName, macroLibraryExtName);
        classLoader = ClassLoaderResourceLoader.class.getClassLoader();
    }

    protected Template loadTemplateItem(String path) throws TemplateException {
        return createTemplate(path);
    }


    public ClassLoaderResourceLoader(String templateExtName, String layoutExtName,String macroLibraryExtName, URL[] urls) throws TemplateException {
        super(templateExtName, layoutExtName, macroLibraryExtName);
        classLoader = URLClassLoader.newInstance(urls);
    }

    public ClassLoaderResourceLoader(String templateExtName, String layoutExtName,String macroLibraryExtName, ClassLoader classLoader) {
        super(templateExtName, layoutExtName,macroLibraryExtName);
        if (classLoader != null) {
            this.classLoader = classLoader;
        } else {
            this.classLoader = ClassLoaderResourceLoader.class.getClassLoader();
        }
    }


    public boolean isModified(String path) {
        return false;
    }

    public String getResourceContent(String path, String encode) throws TemplateException {
        try {
            InputStream inputStream = classLoader.getResourceAsStream(path);
            return IOUtils.readFromInputStream(inputStream, encode);
        } catch (Exception e) {
            throw new TemplateException(e);
        }
    }


    public Template createTemplate(String path) throws TemplateException {
        try {
            String className = ResourceCompilerUtils.getClassNameGetter().getClassName(path).getClassName();
            Template template = (Template) classLoader.loadClass(className).newInstance();
            addTemplate(template);
            return template;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
