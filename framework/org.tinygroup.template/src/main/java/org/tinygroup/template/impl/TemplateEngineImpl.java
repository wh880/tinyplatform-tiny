package org.tinygroup.template.impl;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.tinygroup.template.*;
import org.tinygroup.template.compiler.MemorySource;
import org.tinygroup.template.compiler.MemorySourceCompiler;
import org.tinygroup.template.parser.CodeBlock;
import org.tinygroup.template.parser.JetTemplateCodeVisitor;
import org.tinygroup.template.parser.JetTemplateErrorListener;
import org.tinygroup.template.parser.JetTemplateErrorStrategy;
import org.tinygroup.template.parser.grammer.JetTemplateLexer;
import org.tinygroup.template.parser.grammer.JetTemplateParser;

import java.io.Writer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 模板引擎实现类
 * Created by luoguo on 2014/6/6.
 */
public class TemplateEngineImpl implements TemplateEngine {
    private Map<String, Template> templateMap = new ConcurrentHashMap<String, Template>();
    static MemorySourceCompiler compiler = new MemorySourceCompiler();

    public static CodeBlock preCompile(String template, String sourceName) {
        System.out.println("模板内容：\n"+template);
        char[] source = template.toCharArray();
        ANTLRInputStream is = new ANTLRInputStream(source, source.length);
        is.name = sourceName; // set source file name, it will be displayed in error report.
        JetTemplateParser parser = new JetTemplateParser(new CommonTokenStream(new JetTemplateLexer(is)));
        parser.removeErrorListeners(); // remove ConsoleErrorListener
        parser.addErrorListener(JetTemplateErrorListener.getInstance());
        parser.setErrorHandler(new JetTemplateErrorStrategy());
        JetTemplateParser.TemplateContext templateParseTree = parser.template();
        JetTemplateCodeVisitor visitor = new JetTemplateCodeVisitor();
        CodeBlock codeBlock = templateParseTree.accept(visitor);
        return codeBlock;
    }

    @Override
    public Template getTemplate(TemplateResource templateResource) throws TemplateException {

        CodeBlock codeBlock = preCompile(templateResource.getContent(), templateResource.getPath());
        codeBlock.insertSubCode("package "+getPackageName(templateResource.getPath())+";");
        MemorySource memorySource = new MemorySource(getClassName(templateResource.getPath()),codeBlock.toString().replace("$TEMPLATE_PATH",templateResource.getPath())
                .replace("$TEMPLATE_CLASS_NAME",getSimpleClassName(templateResource.getPath())));
        System.out.println("java代码：\n"+memorySource.getContent());
        Template template = compiler.loadInstance(memorySource);
        addTemplate(template);
        return template;
    }

    @Override
    public String getPackageName(String path) {
        String name = path;
        if (name.startsWith("/")) {//去掉前置"/"
            name = name.substring(1);
        }
        name = name.substring(0, name.lastIndexOf("/"));
        return name.replaceAll("/", ".");
    }

    public static void main(String[] args) {
        System.out.println(new TemplateEngineImpl().getPackageName("/aa/bb/cc/def.aa"));
    }
    @Override
    public String getSimpleClassName(String path) {
        String name = path.substring(path.lastIndexOf('/')+1).split("[.]")[0];
        return name;
    }

    @Override
    public String getClassName(String path) {
        String name = path;
        if (name.startsWith("/")) {//去掉前置"/"
            name = name.substring(1);
        }
        int pos = path.indexOf('.');
        if (pos >= 0) {
            name = name.substring(0, pos-1);//去掉文件扩展名
        }
        return name.replaceAll("/", ".");
    }


    public void addTemplate(Template template) {
        templateMap.put(template.getPath(), template);
        template.setTemplateEngine(this);
    }

    public Map<String, Template> getTemplateMap() {
        return templateMap;
    }

    public void renderMacro(String macroName, Template template, TemplateContext context, Writer writer) throws TemplateException {
        Macro macro = findMacro(macroName, template);
        macro.render(template, context, writer);
    }


    public void renderTemplate(String path, TemplateContext context, Writer writer) throws TemplateException {
        Template template = templateMap.get(path);
        if (template != null) {
            renderTemplate(template, context, writer);
        }
        throw new TemplateException("找不到模板：" + path);
    }

    @Override
    public void renderTemplate(Template template, TemplateContext context, Writer writer) throws TemplateException {
        template.render(context, writer);
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
