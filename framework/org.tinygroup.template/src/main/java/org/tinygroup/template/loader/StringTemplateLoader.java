package org.tinygroup.template.loader;

import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateException;

/**
 * Created by luoguo on 2014/6/9.
 */
public class StringTemplateLoader extends AbstractTemplateLoader<String> {
    public StringTemplateLoader(String type) {
        super(type);
    }

    @Override
    public String getResource(String path) {
        return null;
    }

    public Template createTemplate(String stringTemplateMaterial) throws TemplateException {
        Template template = TemplateCompilerUtils.compileTemplate(StringTemplateLoader.class.getClassLoader(),stringTemplateMaterial, getRandomPath());
        //这里没有调用putTemplate是避免内存泄露
        template.setTemplateEngine(getTemplateEngine());
        return template;
    }

    private String getRandomPath() {
        return "/string/template/NoT" + System.nanoTime();
    }
}
