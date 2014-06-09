package org.tinygroup.template.loader;

import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateEngine;
import org.tinygroup.template.TemplateLoader;

import java.util.HashMap;

/**
 * 抽象模板加载器
 * Created by luoguo on 2014/6/9.
 */
public abstract class AbstractTemplateLoader<T> implements TemplateLoader<T> {
    private final String type;

    private HashMap<String, Template> templateMap = new HashMap<String, Template>();
    private TemplateEngine templateEngine;
    public AbstractTemplateLoader(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public HashMap<String, Template> getTemplateMap() {
        return templateMap;
    }

    public Template getTemplate(String path) {
        return templateMap.get(path);
    }
    public TemplateLoader putTemplate(Template template){
        templateMap.put(template.getPath(), template);
        template.setTemplateEngine(templateEngine);
        return this;
    }
    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine=templateEngine;
    }

    public TemplateEngine getTemplateEngine() {
        return templateEngine;
    }
}
