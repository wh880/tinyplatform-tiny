package org.tinygroup.template.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.tinygroup.template.parser.grammer.JetTemplateLexer;
import org.tinygroup.template.parser.grammer.JetTemplateParser;

/**
 * Created by luoguo on 2014/6/3.
 */
public class Test {
    public static void main(String[] args) {
//        char[] source="#--aaa--##*aaa*#abc#*aaa*#aa#[[#*aaaaa*#]]#${abc}".toCharArray();
//        char[] source="${abc.name}".toCharArray();
        char[] source="${(1+2)}".toCharArray();
        ANTLRInputStream is = new ANTLRInputStream(source, source.length);
        is.name = "testname"; // set source file name, it will be displayed in error report.
        JetTemplateParser parser = new JetTemplateParser(new CommonTokenStream(new JetTemplateLexer(is)));
        parser.removeErrorListeners(); // remove ConsoleErrorListener
        parser.addErrorListener(JetTemplateErrorListener.getInstance());
        parser.setErrorHandler(new JetTemplateErrorStrategy());

        JetTemplateParser.TemplateContext templateParseTree = parser.template();
        JetTemplateCodeVisitor visitor = new JetTemplateCodeVisitor();
        CodeBlock codeBlock = templateParseTree.accept(visitor);
        System.out.println(codeBlock);
    }
}
