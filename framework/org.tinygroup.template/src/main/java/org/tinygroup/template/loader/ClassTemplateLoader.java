package org.tinygroup.template.loader;

import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateException;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by luoguo on 2014/6/9.
 */
public class ClassTemplateLoader extends AbstractTemplateLoader<String> {
    private final URLClassLoader templateClassLoader;

    public ClassTemplateLoader(String type, URL[] urls) {
        this(type, urls, null);
    }

    public ClassTemplateLoader(String type, URL[] urls, ClassLoader classLoader) {
        super(type);
         templateClassLoader = new URLClassLoader(urls.clone(),classLoader);
    }

    @Override
    public String getResource(String path) {
        return null;
    }

    @Override
    public Template createTemplate(String path) throws TemplateException {
        String className=TemplateCompilerUtils.getClassNameGetter().getClassName(path).getClassName();
        try {
            return (Template) templateClassLoader.loadClass(className).newInstance();
        } catch (Exception e) {
            throw new TemplateException(e);
        }
    }
}
