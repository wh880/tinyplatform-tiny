package org.tinygroup.template.impl;

import org.tinygroup.template.*;

import java.io.Writer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 模板引擎实现类
 * Created by luoguo on 2014/6/6.
 */
public class TemplateEngineImpl implements TemplateEngine {
    private Map<String, Template> templateMap = new ConcurrentHashMap<String, Template>();

    public void addTemplate(Template template) {
        templateMap.put(template.getPath(), template);
        template.setTemplateEngine(this);
    }

    public Map<String, Template> getTemplateMap() {
        return templateMap;
    }

    public void renderMacro(String macroName, Template template, TemplateContext context, Writer writer) throws TemplateException {
        Macro macro = findMacro(macroName, template);
        macro.render(template,context, writer);
    }


    public void renderTemplate(String path, TemplateContext context, Writer writer) throws TemplateException {
        Template template = templateMap.get(path);
        if (template != null) {
            template.render(context, writer);
            return;
        }
        throw new TemplateException("找不到模板：" + path);
    }

    public Macro findMacro(String macroName, Template template) throws TemplateException {
        Macro macro = template.getMacroMap().get(macroName);
        if (macro == null) {
            //到整个引擎查找
            //先找相同路径下的，再找子目录下的，再找上级的，再找兄弟的

            throw new TemplateException("找不到宏：" + macroName);
        }
        return macro;
    }
}
