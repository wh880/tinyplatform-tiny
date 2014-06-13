package org.tinygroup.template.impl;

import com.thoughtworks.xstream.io.path.Path;
import org.tinygroup.commons.io.ByteArrayOutputStream;
import org.tinygroup.template.*;
import org.tinygroup.template.function.FormatterTemplateFunction;
import org.tinygroup.template.function.GetResourceContentFunction;
import org.tinygroup.template.function.InstanceOfTemplateFunction;
import org.tinygroup.template.loader.StringResourceLoader;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模板引擎实现类
 * Created by luoguo on 2014/6/6.
 */
public class TemplateEngineDefault implements TemplateEngine {
    private Path root = new Path("");
    private Map<String, TemplateFunction> functionMap = new HashMap<String, TemplateFunction>();
    private Map<String, TemplateFunction> typeFunctionMap = new HashMap<String, TemplateFunction>();
    private TemplateContext templateEngineContext = new TemplateContextDefault();

    private Map<String, ResourceLoader> templateLoaderMap = new HashMap();
    private String encode = "UTF-8";
    private I18nVistor i18nVistor;

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


    public void write(OutputStream outputStream, byte[] data) throws IOException {
        outputStream.write(data);
    }

    public void write(OutputStream outputStream, Object object) throws IOException {
        outputStream.write(object.toString().getBytes(this.getEncode()));
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


    public void putTemplateLoader(String type, ResourceLoader templateLoader) {
        templateLoader.setTemplateEngine(this);
        templateLoaderMap.put(type, templateLoader);
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


    public void renderMacro(String macroName, Template template, TemplateContext context, OutputStream outputStream) throws TemplateException {
        Macro macro = findMacro(macroName, template, context);
        if (macro != null) {
            macro.render(template, context, outputStream);
        } else {
            throw new TemplateException("找不到宏：" + macroName);
        }
    }


    public void renderMacro(Macro macro, Template template, TemplateContext context, OutputStream outputStream) throws TemplateException {
        macro.render(template, context, outputStream);
    }

    public void renderTemplate(String path, TemplateContext context, OutputStream outputStream) throws TemplateException {
        Layout layout = null;
        Template template = null;
        for (ResourceLoader loader : templateLoaderMap.values()) {
            template = loader.getTemplate(path);
            if (template != null) {
                break;
            }
        }
        if (template != null) {
            List<Layout> layoutPaths = getLayoutList(template.getPath());
            if (layoutPaths.size() > 0) {
                ByteArrayOutputStream templateOutputStream = new ByteArrayOutputStream();
                template.render(context, templateOutputStream);
                context.put("pageContent", new String(templateOutputStream.toByteArray().toByteArray()));
                ByteArrayOutputStream layoutOutputStream = null;
                TemplateContext layoutContext = context;
                for (int i = layoutPaths.size() - 1; i >= 0; i--) {
                    //每次都构建新的Writer和Context来执行
                    TemplateContext tempContext = new TemplateContextDefault();
                    layoutOutputStream = new ByteArrayOutputStream();
                    tempContext.setParent(layoutContext);
                    layoutContext = tempContext;
                    layoutPaths.get(i).render(layoutContext, layoutOutputStream);
                    if (i >= 0) {
                        layoutContext.put("pageContent", new String(layoutOutputStream.toByteArray().toByteArray()));
                    }
                }
                try {
                    outputStream.write(layoutOutputStream.toByteArray().toByteArray());
                    outputStream.flush();
                } catch (IOException e) {
                    throw new TemplateException(e);
                }
            } else {
                renderTemplate(template, context, outputStream);
            }
        } else {
            throw new TemplateException("找不到模板：" + path);
        }
    }


    private List<Layout> getLayoutList(String templatePath) throws TemplateException {
        List<Layout> layoutPathList = new ArrayList<Layout>();
        String[] paths = templatePath.split("/");
        String path = "";

        String templateFileName = paths[paths.length - 1];
        for (int i = 0; i < paths.length - 1; i++) {
            path += paths[i] + "/";
            String template = path + templateFileName;
            for (ResourceLoader loader : templateLoaderMap.values()) {
                String layoutPath = loader.getLayoutPath(template);
                Layout layout = loader.getLayout(layoutPath);
                if (layout == null) {
                    String defaultTemplateName = path + DEFAULT + templateFileName.substring(templateFileName.lastIndexOf('.'));
                    layout = loader.getLayout(defaultTemplateName);
                }
                if (layout != null) {
                    layoutPathList.add(layout);
                }
            }
        }
        return layoutPathList;
    }


    public void renderTemplate(String path) throws TemplateException {
        renderTemplate(path, new TemplateContextDefault(), System.out);
    }


    public void renderTemplate(Template template) throws TemplateException {
        renderTemplate(template, new TemplateContextDefault(), System.out);
    }


    public void renderTemplate(Template template, TemplateContext context, OutputStream outputStream) throws TemplateException {
        template.render(context, outputStream);
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
        for (ResourceLoader templateLoader : templateLoaderMap.values()) {
            String content = templateLoader.getResourceContent(path, encode);
            if (content != null) {
                return content;
            }
        }
        throw new TemplateException("找不到资源：" + path);
    }
}
