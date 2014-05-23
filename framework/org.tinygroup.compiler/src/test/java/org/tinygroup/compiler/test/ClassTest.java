package org.tinygroup.compiler.test;
import org.eclipse.jdt.core.compiler.CompilationProgress;
import org.eclipse.jdt.core.compiler.batch.BatchCompiler;

import java.io.PrintWriter;

/**
 * Created by luoguo on 2014/5/21.
 */
public class ClassTest {
    public static void main(String[] args) {
        CompilationProgress progress = null; // instantiate your subclass
        BatchCompiler.compile(
                "-classpath rt.jar E:\\test\\test\\Hello1.java E:\\test\\test\\Hello2.java",
                new PrintWriter(System.out),
                new PrintWriter(System.err),
                progress);
    }
}
