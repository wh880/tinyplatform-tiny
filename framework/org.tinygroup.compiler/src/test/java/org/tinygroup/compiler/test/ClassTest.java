package org.tinygroup.compiler.test;

import org.eclipse.jdt.core.compiler.CompilationProgress;
import org.eclipse.jdt.core.compiler.batch.BatchCompiler;

import java.io.PrintWriter;

import junit.framework.TestCase;

/**
 * Created by luoguo on 2014/5/21.
 */
public class ClassTest extends TestCase {
	public void test() {
		CompilationProgress progress = null; // instantiate your subclass
		String path = System.getProperty("user.dir");
		String filePath = path + "\\test1\\org\\tinygroup\\Grade.java";
		String filePath1 = path
				+ "\\test1\\org\\tinygroup\\MyTestInterface.java";
		boolean flag = BatchCompiler.compile(
				"-classpath rt.jar " + filePath + " " + filePath1,
				new PrintWriter(System.out), new PrintWriter(System.err),
				progress);
		assertTrue(flag);
	}
}
