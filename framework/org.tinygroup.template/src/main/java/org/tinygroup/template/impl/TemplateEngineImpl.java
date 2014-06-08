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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 模板引擎实现类
 * Created by luoguo on 2014/6/6.
 */
public class TemplateEngineImpl implements TemplateEngine {
    private Map<String, Template> templateMap = new ConcurrentHashMap<String, Template>();
    static MemorySourceCompiler compiler = new MemorySourceCompiler();
    Path root = new Path("");

    public static void main(String[] args) {
        TemplateEngineImpl engine = new TemplateEngineImpl();
        engine.buildPath("/a/b/c/d.page");
        engine.buildPath("/a/b/e/f.page");
        engine.buildPath("/a/c/c/g.page");
        System.out.println();
        ;
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

        @Override
        public int compareTo(Path o) {
            return -name.compareTo(o.name);
        }
    }

    public static CodeBlock preCompile(String template, String sourceName) {
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
        codeBlock.insertSubCode("package " + getPackageName(templateResource.getPath()) + ";");
        MemorySource memorySource = new MemorySource(getClassName(templateResource.getPath()), codeBlock.toString().replace("$TEMPLATE_PATH", templateResource.getPath())
                .replace("$TEMPLATE_CLASS_NAME", getSimpleClassName(templateResource.getPath())));
        Template template = compiler.loadInstance(memorySource);
        addTemplate(template);
        return template;
    }

    @Override
    public String getPackageName(String path) {
        String className=getClassName(path);
        return className.substring(0,className.lastIndexOf('.'));
    }


    @Override
    public String getSimpleClassName(String path) {
        String className=getClassName(path);
        return className.substring(className.lastIndexOf('.')+1);
    }

    @Override
    public String getClassName(String path) {
        String name = path;
        name = convertGoodStylePath(path);
        if (name.startsWith("/")) {//去掉前置"/"
            name = name.substring(1);
        }
        int pos = path.indexOf('.');
        if (pos >= 0) {
            name = name.substring(0, pos - 1);//去掉文件扩展名
        }
        return name.replaceAll("/", ".");
    }

    private String convertGoodStylePath(String path) {
        StringBuffer sb = new StringBuffer(200);

        for (char c : path.toCharArray()) {
            if (c == '/'||c=='.') {
                sb.append(c);
            } else if (c >= 0 && c <= 0) {
                sb.append(c);
            } else if (c >= 'a' && c <= 'z') {
                sb.append(c);
            } else if (c >= 'A' && c <= 'Z') {
                sb.append(c);
            }else {
                sb.append("_");
            }
        }
        return sb.toString();
    }


    public Template addTemplate(Template template) {
        templateMap.put(template.getPath(), template);
        buildPath(template.getPath());
        template.setTemplateEngine(this);
        return template;
    }

    private void buildPath(String path) {
        String[] paths = path.split("/");
        Path current = root;
        for (String name : paths) {
            //   /a/b/c/aa.def
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

    public Map<String, Template> getTemplateMap() {
        return templateMap;
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
