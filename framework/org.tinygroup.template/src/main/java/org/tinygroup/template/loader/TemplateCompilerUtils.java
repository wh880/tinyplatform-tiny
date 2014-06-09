package org.tinygroup.template.loader;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.tinygroup.template.ClassName;
import org.tinygroup.template.ClassNameGetter;
import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.compiler.MemorySource;
import org.tinygroup.template.compiler.MemorySourceCompiler;
import org.tinygroup.template.impl.ClassNameGetterDefault;
import org.tinygroup.template.parser.CodeBlock;
import org.tinygroup.template.parser.JetTemplateCodeVisitor;
import org.tinygroup.template.parser.JetTemplateErrorListener;
import org.tinygroup.template.parser.JetTemplateErrorStrategy;
import org.tinygroup.template.parser.grammer.JetTemplateLexer;
import org.tinygroup.template.parser.grammer.JetTemplateParser;

/**
 * Created by luoguo on 2014/6/9.
 */
public class TemplateCompilerUtils {
    private static ClassNameGetter classNameGetter = new ClassNameGetterDefault();
    private static MemorySourceCompiler compiler = new MemorySourceCompiler();

    public static void setClassNameGetter(ClassNameGetter classNameGetter) {
        TemplateCompilerUtils.classNameGetter = classNameGetter;
    }

    public static ClassNameGetter getClassNameGetter() {
        return classNameGetter;
    }

    public static Template compileTemplate(String content, String path) throws TemplateException {
        CodeBlock codeBlock = preCompile(content, path);
        ClassName className = classNameGetter.getClassName(path);
        codeBlock.insertSubCode("package " + className.getPackageName() + ";");
        MemorySource memorySource = new MemorySource(className.getClassName(), codeBlock.toString().replace("$TEMPLATE_PATH", path)
                .replace("$TEMPLATE_CLASS_NAME", className.getSimpleClassName()));
        Template template = compiler.loadInstance(memorySource);
        return template;

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
        JetTemplateCodeVisitor visitor = new JetTemplateCodeVisitor(parser);
        CodeBlock codeBlock = templateParseTree.accept(visitor);
        return codeBlock;
    }
}
