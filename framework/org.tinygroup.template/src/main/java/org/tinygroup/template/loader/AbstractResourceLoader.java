package org.tinygroup.template.loader;

import org.tinygroup.template.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 抽象模板加载器
 * Created by luoguo on 2014/6/9.
 */
public abstract class AbstractResourceLoader<T> implements ResourceLoader<T> {
    private boolean checkModified = false;
    private ClassLoader classLoader;
    private Map<String, Layout> layoutMap = new ConcurrentHashMap<String, Layout>();
    private Map<String, Template> templateMap = new ConcurrentHashMap<String, Template>();
    private TemplateEngine templateEngine;
    private String templateExtName;
    private String layoutExtName;

    public void setCheckModified(boolean checkModified) {
        this.checkModified = checkModified;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }


    public AbstractResourceLoader(String templateExtName, String layoutExtName) {
        this.templateExtName = "." + templateExtName;
        this.layoutExtName = "." + layoutExtName;
    }


    public String getLayoutPath(String templatePath) {
        if (templatePath.endsWith(templateExtName)) {
            return templatePath.substring(0, templatePath.length() - templateExtName.length()) + layoutExtName;
        }
        return null;
    }

    public Map<String, Template> getTemplateMap() {
        return templateMap;
    }


    public Map<String, Layout> getLayoutMap() {
        return layoutMap;
    }


    public String getTemplateExtName() {
        return templateExtName;
    }


    public String getLayoutExtName() {
        return layoutExtName;
    }

    public Template getTemplate(String path) throws TemplateException {
        if (!path.endsWith(templateExtName)) {
            return null;
        }
        Template template = templateMap.get(path);
        if (template != null && (!checkModified || !isModified(path))) {
            return template;
        }
        return loadTemplate(path);
    }

    public Layout getLayout(String path) throws TemplateException {
        if (!path.endsWith(layoutExtName)) {
            return null;
        }
        Layout layout = layoutMap.get(path);
        if (layout != null && (templateEngine.isCacheEnabled() || !isModified(path))) {
            return layout;
        }
        return loadLayout(path);
    }

    protected abstract Template loadTemplate(String path) throws TemplateException;

    protected abstract Layout loadLayout(String path) throws TemplateException;

    public ResourceLoader addTemplate(Template template) {
        templateMap.put(template.getPath(), template);
        template.setTemplateEngine(templateEngine);
        template.getTemplateContext().setParent(templateEngine.getTemplateContext());
        return this;
    }


    public ResourceLoader addLayout(Layout layout) {
        layoutMap.put(layout.getPath(), layout);
        return this;
    }

    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public TemplateEngine getTemplateEngine() {
        return templateEngine;
    }
}
