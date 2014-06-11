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
public class ClassLoaderTemplateloader extends AbstractTemplateLoader<String> {
    private ClassLoader classLoader;

    public ClassLoaderTemplateloader(String type) {
        super(type);
        classLoader=ClassLoaderTemplateloader.class.getClassLoader();
    }

    @Override
    protected Template loadTemplate(String path) throws TemplateException {
        return createTemplate(path);
    }

    public ClassLoaderTemplateloader(String type, URL[] urls) throws TemplateException {
        super(type);
        classLoader = URLClassLoader.newInstance(urls);
    }

    public ClassLoaderTemplateloader(String type, ClassLoader classLoader) {
        super(type);
        if (classLoader != null) {
            this.classLoader = classLoader;
        } else {
            this.classLoader = ClassLoaderTemplateloader.class.getClassLoader();
        }
    }

    @Override
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
            String className = TemplateCompilerUtils.getClassNameGetter().getClassName(path).getClassName();
            Template template = (Template) classLoader.loadClass(className).newInstance();
            putTemplate(path, template);
            return template;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
