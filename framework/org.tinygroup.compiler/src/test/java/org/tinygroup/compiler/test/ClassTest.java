package org.tinygroup.compiler.test;

import junit.framework.TestCase;
import org.eclipse.jdt.core.compiler.CompilationProgress;
import org.eclipse.jdt.core.compiler.batch.BatchCompiler;

import java.io.File;
import java.io.PrintWriter;

/**
 * Created by luoguo on 2014/5/21.
 */
public class ClassTest extends TestCase {
    public void test() {
        CompilationProgress progress = null; // instantiate your subclass
        String path = System.getProperty("user.dir");
        String filePath = path + File.separatorChar + "test1" + File.separatorChar + "org" + File.separatorChar + "tinygroup" + File.separatorChar + "Grade.java";
        String filePath1 = path
                + "" + File.separatorChar + "test1" + File.separatorChar + "org" + File.separatorChar + "tinygroup" + File.separatorChar + "MyTestInterface.java";
        boolean flag = BatchCompiler.compile(
                "-classpath rt.jar " + filePath + " " + filePath1,
                new PrintWriter(System.out), new PrintWriter(System.err),
                progress);
        assertTrue(flag);
    }
}
