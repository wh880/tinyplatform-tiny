package org.tinygroup.template.loader;

import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateEngine;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.TemplateLoader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 抽象模板加载器
 * Created by luoguo on 2014/6/9.
 */
public abstract class AbstractTemplateLoader<T> implements TemplateLoader<T> {
    private final String type;
    private ClassLoader gettemplateEngineClassLoader;

    public ClassLoader getTemplateEngineClassLoader(){
        return gettemplateEngineClassLoader;
    }

    public void setTemplateEngineClassLoader(ClassLoader classLoader){
        this.gettemplateEngineClassLoader=classLoader;
    }

    private Map<T, Template> templateMap = new ConcurrentHashMap<T, Template>();
    private TemplateEngine templateEngine;
    public AbstractTemplateLoader(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public Map<T, Template> getTemplateMap() {
        return templateMap;
    }

    public Template getTemplate(String path) throws TemplateException{
        return templateMap.get(path);
    }
    public TemplateLoader putTemplate(T key,Template template){
        templateMap.put(key, template);
        template.setTemplateEngine(templateEngine);
        template.getTemplateContext().setParent(templateEngine.getTemplateContext());
        return this;
    }
    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine=templateEngine;
    }

    public TemplateEngine getTemplateEngine() {
        return templateEngine;
    }
}
