package org.tinygroup.template.loader;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.compiler.MemorySource;
import org.tinygroup.template.compiler.MemorySourceCompiler;
import org.tinygroup.template.impl.ClassName;
import org.tinygroup.template.impl.ClassNameGetterDefault;
import org.tinygroup.template.parser.CodeBlock;
import org.tinygroup.template.parser.TinyTemplateCodeVisitor;
import org.tinygroup.template.parser.TinyTemplateErrorListener;
import org.tinygroup.template.parser.TinyTemplateErrorStrategy;
import org.tinygroup.template.parser.grammer.TinyTemplateLexer;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;

/**
 * Created by luoguo on 2014/6/9.
 */
public class ResourceCompilerUtils {
    private static ClassNameGetter classNameGetter = new ClassNameGetterDefault();
    private static MemorySourceCompiler compiler = new MemorySourceCompiler();

    public static void setClassNameGetter(ClassNameGetter classNameGetter) {
        ResourceCompilerUtils.classNameGetter = classNameGetter;
    }

    public static ClassNameGetter getClassNameGetter() {
        return classNameGetter;
    }

    public static <T> T compileResource(ClassLoader classLoader, String content, String path) throws TemplateException {
        CodeBlock codeBlock = preCompile(content, path);
        ClassName className = classNameGetter.getClassName(path);
        if(className.getPackageName()!=null) {
            codeBlock.insertSubCode("package " + className.getPackageName() + ";");
        }
        MemorySource memorySource = new MemorySource(className.getClassName(), codeBlock.toString().replace("$TEMPLATE_PATH", path)
                .replace("$TEMPLATE_CLASS_NAME", className.getSimpleClassName()));
        return compiler.loadInstance(classLoader,memorySource);

    }

    public static CodeBlock preCompile(String template, String sourceName) {
        char[] source = template.toCharArray();
        ANTLRInputStream is = new ANTLRInputStream(source, source.length);
        // set source file name, it will be displayed in error report.
        is.name = sourceName;
        TinyTemplateParser parser = new TinyTemplateParser(new CommonTokenStream(new TinyTemplateLexer(is)));
        // remove ConsoleErrorListener
        parser.removeErrorListeners();
        parser.addErrorListener(TinyTemplateErrorListener.getInstance());
        parser.setErrorHandler(new TinyTemplateErrorStrategy());
        TinyTemplateParser.TemplateContext templateParseTree = parser.template();
        TinyTemplateCodeVisitor visitor = new TinyTemplateCodeVisitor(parser);
        return templateParseTree.accept(visitor);
    }

}
