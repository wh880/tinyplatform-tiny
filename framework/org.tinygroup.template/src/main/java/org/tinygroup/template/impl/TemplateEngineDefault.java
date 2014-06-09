package org.tinygroup.template.impl;

import org.tinygroup.template.*;
import org.tinygroup.template.loader.StringTemplateLoader;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 模板引擎实现类
 * Created by luoguo on 2014/6/6.
 */
public class TemplateEngineDefault implements TemplateEngine {
    public static final String DEFAULT = "default";
    Path root = new Path("");
    private TemplateContext templateEngineContext = new TemplateContextImpl();

    private Map<String, TemplateLoader> templateLoaderMap = new HashMap();
    private String encode = "UTF-8";
    private I18nVistor i18nVistor;

    public TemplateEngineDefault() {
        //添加一个默认的加载器
        addTemplateLoader(new StringTemplateLoader("default"));
    }

    public TemplateContext getTemplateContext() {
        return templateEngineContext;
    }


    class Path implements Comparable<Path> {
        public Path(String name) {
            this.name = name;
        }

        public Path setParent(Path path) {
            this.parent = path;
            path.subPaths.add(this);
            return this;
        }

        public Path addPath(Path path) {
            subPaths.add(path);
            path.parent = this;
            return this;
        }

        String name;
        Path parent;
        Set<Path> subPaths = new HashSet<Path>();


        public int compareTo(Path o) {
            return -name.compareTo(o.name);
        }
    }


    public TemplateEngine setEncode(String encode) {
        this.encode = encode;
        return this;
    }

    @Override
    public void setI18nVistor(I18nVistor i18nVistor) {
        this.i18nVistor=i18nVistor;
    }

    @Override
    public I18nVistor getI18nVistor() {
        return i18nVistor;
    }


    public String getEncode() {
        return encode;
    }


    public void addTemplateLoader(TemplateLoader templateLoader) {
        templateLoader.setTemplateEngine(this);
        templateLoaderMap.put(templateLoader.getType(), templateLoader);
    }

    public TemplateLoader getTemplateLoader(String type) throws TemplateException {
        return templateLoaderMap.get(type);
    }

    @Override
    public TemplateLoader getDefaultTemplateLoader() throws TemplateException {
        return getTemplateLoader(DEFAULT);
    }


    public Map<String, TemplateLoader> getTemplateLoaderMap() {
        return templateLoaderMap;
    }


    public TemplateEngine put(String key, Object value) {
        templateEngineContext.put(key, value);
        return this;
    }

    private void buildPath(String path) {
        String[] paths = path.split("/");
        Path current = root;
        for (String name : paths) {
            //   /a/b/v/aa.def
            if (name.equals(current.name)) {//如果是根，则继续
                continue;
            } else {
                boolean has = false;
                for (Path p : current.subPaths) {
                    if (p.name.equals(name)) {
                        current = p;//如果对上了，则继续向下找
                        has = true;
                        break;
                    }
                }
                if (!has) {//如果不包含，则添加之后继续
                    Path newPath = new Path(name);
                    current.addPath(newPath);
                    current = newPath;
                }
            }
        }
    }


    public void renderMacro(String macroName, Template template, TemplateContext context, Writer writer) throws TemplateException {
        Macro macro = findMacro(macroName, template, context);
        if (macro != null) {
            macro.render(template, context, writer);
        } else {
            throw new TemplateException("找不到宏：" + macroName);
        }
    }


    public void renderMacro(Macro macro, Template template, TemplateContext context, Writer writer) throws TemplateException {
        macro.render(template, context, writer);
    }

    public void renderTemplate(String path, TemplateContext context, Writer writer) throws TemplateException {
        for (TemplateLoader loader : templateLoaderMap.values()) {
            Template template = loader.getTemplate(path);
            if (template != null) {
                renderTemplate(template, context, writer);
                return;
            }
        }
        throw new TemplateException("找不到模板：" + path);
    }

    @Override
    public void renderTemplate(String path) throws TemplateException {
        renderTemplate(path, new TemplateContextImpl(), new OutputStreamWriter(System.out));
    }

    @Override
    public void renderTemplate(Template template) throws TemplateException {
        renderTemplate(template, new TemplateContextImpl(), new OutputStreamWriter(System.out));
    }


    public void renderTemplate(Template template, TemplateContext context, Writer writer) throws TemplateException {
        template.render(context, writer);
    }

    public Macro findMacro(String macroName, Template template, TemplateContext $context) throws TemplateException {
        Macro macro = template.getMacroMap().get(macroName);
        if (macro == null) {
            Object obj = $context.getItemMap().get(macroName);
            if (obj != null && obj instanceof Macro) {
                macro = (Macro) obj;
            }
            if (macro == null) {
                //到整个引擎查找
                //先找相同路径下的，再找子目录下的，再找上级的，再找兄弟的

                throw new TemplateException("找不到宏：" + macroName);
            }
        }
        return macro;
    }
}
