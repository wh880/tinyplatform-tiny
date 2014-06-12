package org.tinygroup.template.impl;

import com.thoughtworks.xstream.io.path.Path;
import org.tinygroup.template.*;
import org.tinygroup.template.function.FormatterTemplateFunction;
import org.tinygroup.template.function.GetResourceContentFunction;
import org.tinygroup.template.function.InstanceOfTemplateFunction;
import org.tinygroup.template.loader.StringTemplateLoader;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * 模板引擎实现类
 * Created by luoguo on 2014/6/6.
 */
public class TemplateEngineDefault implements TemplateEngine {
    public static final String DEFAULT = "default";
    private Path root = new Path("");
    private Map<String, TemplateFunction> functionMap = new HashMap<String, TemplateFunction>();
    private Map<String, TemplateFunction> typeFunctionMap = new HashMap<String, TemplateFunction>();
    private TemplateContext templateEngineContext = new TemplateContextDefault();

    private Map<String, TemplateLoader> templateLoaderMap = new HashMap();
    private String encode = "UTF-8";
    private I18nVistor i18nVistor;

    public TemplateEngineDefault() {
        //添加一个默认的加载器
        addTemplateLoader(new StringTemplateLoader("default"));
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


    public void setI18nVistor(I18nVistor i18nVistor) {
        this.i18nVistor = i18nVistor;
    }


    public I18nVistor getI18nVistor() {
        return i18nVistor;
    }


    public void addTemplateFunction(TemplateFunction function) {
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


    public void addTemplateLoader(TemplateLoader templateLoader) {
        templateLoader.setTemplateEngine(this);
        templateLoaderMap.put(templateLoader.getType(), templateLoader);
    }


    public Template getTemplate(String path) throws TemplateException {
        for (TemplateLoader loader : templateLoaderMap.values()) {
            Template template = loader.getTemplate(path);
            if (template != null) {
                return template;
            }
        }
        throw new TemplateException("找不到模板：" + path);
    }

    public TemplateLoader getTemplateLoader(String type) throws TemplateException {
        return templateLoaderMap.get(type);
    }


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


    public void renderTemplate(String path) throws TemplateException {
        renderTemplate(path, new TemplateContextDefault(), new OutputStreamWriter(System.out));
    }


    public void renderTemplate(Template template) throws TemplateException {
        renderTemplate(template, new TemplateContextDefault(), new OutputStreamWriter(System.out));
    }


    public void renderTemplate(Template template, TemplateContext context, Writer writer) throws TemplateException {
        template.render(context, writer);
    }

    public Macro findMacro(Object macroName, Template template, TemplateContext $context) throws TemplateException {
        Macro macro = template.getMacroMap().get(macroName);
        if (macro == null) {
            Object obj = $context.getItemMap().get(macroName);
            if (obj instanceof Macro) {
                macro = (Macro) obj;
            }
            if (macro == null) {
                //TODO
                //到整个引擎查找
                //先找相同路径下的，再找子目录下的，再找上级的，再找兄弟的

                throw new TemplateException("找不到宏：" + macroName);
            }
        }
        return macro;
    }

    public Object executeFunction(String functionName, Object... parameters) throws TemplateException {
        TemplateFunction function = functionMap.get(functionName);
        if (function != null) {
            return function.execute(parameters);
        }
        throw new TemplateException("找不到函数：" + functionName);
    }


    public String getResourceContent(String path, String encode) throws TemplateException {
        for (TemplateLoader templateLoader : templateLoaderMap.values()) {
            String content = templateLoader.getResourceContent(path, encode);
            if (content != null) {
                return content;
            }
        }
        throw new TemplateException("找不到资源：" + path);
    }
}
