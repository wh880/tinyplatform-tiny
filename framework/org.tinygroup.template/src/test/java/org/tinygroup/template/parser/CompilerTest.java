package org.tinygroup.template.parser;


import org.tinygroup.template.compiler.MemorySource;
import org.tinygroup.template.compiler.MemorySourceCompiler;

/**
 * Created by luoguo on 2014/6/7.
 */
public class CompilerTest {
    public static void main(String[] args)  {
        MemorySourceCompiler compiler=new MemorySourceCompiler();
        MemorySource source=new MemorySource("org.tinygroup.template.parser.Context1","package org.tinygroup.template.parser;import org.tinygroup.context.impl.ContextImpl;public class Context1 extends ContextImpl{}\n");
        compiler.compile(source);
    }
}
