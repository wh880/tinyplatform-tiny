package org.tinygroup.template.impl;

import org.tinygroup.template.*;
import org.tinygroup.template.function.FormatterTemplateFunction;
import org.tinygroup.template.function.GetResourceContentFunction;
import org.tinygroup.template.function.InstanceOfTemplateFunction;
import org.tinygroup.template.loader.StringResourceLoader;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模板引擎实现类
 * Created by luoguo on 2014/6/6.
 */
public class TemplateEngineDefault implements TemplateEngine {
    private Map<String, TemplateFunction> functionMap = new HashMap<String, TemplateFunction>();
    private Map<String, TemplateFunction> typeFunctionMap = new HashMap<String, TemplateFunction>();
    private TemplateContext templateEngineContext = new TemplateContextDefault();

    private Map<String, ResourceLoader> templateLoaderMap = new HashMap();
    private String encode = "UTF-8";
    private I18nVistor i18nVistor;
    private boolean cacheEnabled = false;
    private TemplateCache<List<Template>> layoutPathListCache = new TemplateCacheDefault<List<Template>>();
    private TemplateCache<Macro> macroCache = new TemplateCacheDefault<Macro>();
    private List<String> macroLibraryList = new ArrayList<String>();

    public boolean isCacheEnabled() {
        return cacheEnabled;
    }

    public TemplateEngine setCacheEnabled(boolean cacheEnabled) {
        this.cacheEnabled = cacheEnabled;
        return this;
    }

    public void registerMacroLibrary(String path) throws TemplateException {
        macroLibraryList.add(path);
    }

    public void registerMacroLibrary(Template template) throws TemplateException {
        getDefaultTemplateLoader().addTemplate(template);
        registerMacroLibrary(template.getPath());
    }

    public TemplateEngineDefault() {
        //添加一个默认的加载器
        putTemplateLoader("default", new StringResourceLoader());
        addTemplateFunction(new FormatterTemplateFunction());
        addTemplateFunction(new InstanceOfTemplateFunction());
        addTemplateFunction(new GetResourceContentFunction());
    }

    public TemplateContext getTemplateContext() {
        return templateEngineContext;
    }

    public TemplateEngine setEncode(String encode) {
        this.encode = encode;
        return this;
    }


    public TemplateEngine setI18nVistor(I18nVistor i18nVistor) {
        this.i18nVistor = i18nVistor;
        return this;
    }


    public I18nVistor getI18nVistor() {
        return i18nVistor;
    }


    public TemplateEngine addTemplateFunction(TemplateFunction function) {
        function.setTemplateEngine(this);
        String[] names = function.getNames().split(",");
        if (function.getBindingTypes() == null) {
            for (String name : names) {
                functionMap.put(name, function);
            }
        } else {
            String[] types = function.getBindingTypes().split(",");
            for (String type : types) {
                for (String name : names) {
                    typeFunctionMap.put(getkeyName(type, name), function);
                }
            }
        }
        return this;
    }


    public TemplateFunction getTemplateFunction(String methodName) {
        return functionMap.get(methodName);
    }


    public TemplateFunction getTemplateFunction(String className, String methodName) {
        return typeFunctionMap.get(getkeyName(className, methodName));
    }

    private String getkeyName(String className, String methodName) {
        return className + ":" + methodName;
    }

    public String getEncode() {
        return encode;
    }


    public TemplateEngine putTemplateLoader(String type, ResourceLoader templateLoader) {
        templateLoader.setTemplateEngine(this);
        templateLoaderMap.put(type, templateLoader);
        return this;
    }


    public Template getTemplate(String path) throws TemplateException {
        for (ResourceLoader loader : templateLoaderMap.values()) {
            Template template = loader.getTemplate(path);
            if (template != null) {
                return template;
            }
        }
        throw new TemplateException("找不到模板：" + path);
    }

    public ResourceLoader getTemplateLoader(String type) throws TemplateException {
        return templateLoaderMap.get(type);
    }


    public ResourceLoader getDefaultTemplateLoader() throws TemplateException {
        return getTemplateLoader(DEFAULT);
    }


    public Map<String, ResourceLoader> getTemplateLoaderMap() {
        return templateLoaderMap;
    }


    public TemplateEngine put(String key, Object value) {
        templateEngineContext.put(key, value);
        return this;
    }


    public void renderMacro(String macroName, Template template, TemplateContext context, Writer writer) throws TemplateException {
        findMacro(macroName, template, context).render(template, context, writer);
    }


    public void renderMacro(Macro macro, Template template, TemplateContext context, Writer writer) throws TemplateException {
        macro.render(template, context, writer);
    }

    public void renderTemplate(String path, TemplateContext context, Writer writer) throws TemplateException {
        try {
            Template template = getTemplate(path);
            if (template != null) {
                List<Template> layoutPaths = getLayoutList(template.getPath());
                if (layoutPaths.size() > 0) {
                    Writer templateWriter = new CharArrayWriter();
                    template.render(context, templateWriter);
                    context.put("pageContent", templateWriter.toString());
                    Writer layoutWriter = null;
                    TemplateContext layoutContext = context;
                    for (int i = layoutPaths.size() - 1; i >= 0; i--) {
                        //每次都构建新的Writer和Context来执行
                        TemplateContext tempContext = new TemplateContextDefault();
                        tempContext.setParent(layoutContext);
                        layoutContext = tempContext;
                        layoutWriter = new CharArrayWriter();
                        layoutPaths.get(i).render(layoutContext, layoutWriter);
                        if (i > 0) {
                            layoutContext.put("pageContent", layoutWriter);
                        }
                    }
                    writer.write(layoutWriter.toString());
                } else {
                    renderTemplate(template, context, writer);
                }
                writer.flush();
            } else {
                throw new TemplateException("找不到模板：" + path);
            }
        } catch (IOException e) {
            throw new TemplateException(e);
        }
    }


    private List<Template> getLayoutList(String templatePath) throws TemplateException {
        List<Template> layoutPathList = null;
        if (cacheEnabled) {
            layoutPathList = layoutPathListCache.get(templatePath);
            if (layoutPathList != null) {
                return layoutPathList;
            }
        }
        if (layoutPathList == null) {
            layoutPathList = new ArrayList<Template>();
        }
        String[] paths = templatePath.split("/");
        String path = "";

        String templateFileName = paths[paths.length - 1];
        for (int i = 0; i < paths.length - 1; i++) {
            path += paths[i] + "/";
            String template = path + templateFileName;
            for (ResourceLoader loader : templateLoaderMap.values()) {
                String layoutPath = loader.getLayoutPath(template);
                if (layoutPath != null) {
                    Template layout = loader.getLayout(layoutPath);
                    if (layout == null) {
                        String defaultTemplateName = path + DEFAULT + templateFileName.substring(templateFileName.lastIndexOf('.'));
                        layout = loader.getLayout(defaultTemplateName);
                    }
                    if (layout != null) {
                        layoutPathList.add(layout);
                    }
                }
            }
        }
        if (cacheEnabled) {
            layoutPathListCache.put(templatePath, layoutPathList);
        }

        return layoutPathList;
    }


    public void renderTemplate(String path) throws TemplateException {
        renderTemplate(path, new TemplateContextDefault(), new OutputStreamWriter(System.out));
    }


    public void renderTemplate(Template template) throws TemplateException {
        renderTemplate(template, new TemplateContextDefault(), new OutputStreamWriter(System.out));
    }


    public void renderTemplate(Template template, TemplateContext context, Writer writer) throws TemplateException {
        template.render(context, writer);
    }

    public Macro findMacro(Object macroNameObject, Template template, TemplateContext context) throws TemplateException {
        //上下文中的宏优先处理，主要是考虑bodyContent宏
        String macroName = macroNameObject.toString();
        Object obj = context.getItemMap().get(macroName);
        if (obj instanceof Macro) {
            return (Macro) obj;
        }
        //查找私有宏
        Macro macro = template.getMacroMap().get(macroName);
        if (macro != null) {
            return macro;
        }
        if (cacheEnabled) {
            macro = macroCache.get(macroName);
            if (macro != null) {
                return macro;
            }
        }
        /**
         * 查找公共宏，后添加加的优先
         */
        for (int i = macroLibraryList.size() - 1; i >= 0; i--) {
            Template macroLibrary = getTemplate(macroLibraryList.get(i));
            if (macroLibrary != null) {
                macro = macroLibrary.getMacroMap().get(macroName);
                if (macro != null) {
                    if (cacheEnabled) {
                        macroCache.put(macroName, macro);
                    }
                    return macro;
                }
            }
        }
        throw new TemplateException("找不到宏：" + macroName);
    }

    public Object executeFunction(String functionName, Object... parameters) throws TemplateException {
        TemplateFunction function = functionMap.get(functionName);
        if (function != null) {
            return function.execute(parameters);
        }
        throw new TemplateException("找不到函数：" + functionName);
    }


    public String getResourceContent(String path, String encode) throws TemplateException {
        for (ResourceLoader templateLoader : templateLoaderMap.values()) {
            String content = templateLoader.getResourceContent(path, encode);
            if (content != null) {
                return content;
            }
        }
        throw new TemplateException("找不到资源：" + path);
    }

    public String getResourceContent(String path) throws TemplateException {
        return getResourceContent(path, getEncode());
    }

}
