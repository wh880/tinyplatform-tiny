package org.tinygroup.compiler.test;

import junit.framework.TestCase;

import org.tinygroup.compiler.CompileException;
import org.tinygroup.compiler.impl.FileSource;
import org.tinygroup.compiler.impl.FileSourceJdtCompiler;

public class FileSourceJdtCompilerTest extends TestCase{

//	public static void main(String[] args) throws CompileException {
//		String path = System.getProperty("user.dir");
//		String newPath = path+"\\test";
//		String newPath1 = path+"\\test1";
//		FileSourceJdtCompiler compiler = new FileSourceJdtCompiler();
//		FileSource[] fileSources = { new FileSource(newPath),
//				new FileSource(newPath1) };
//		compiler.compile(fileSources);
//	}
	
	public  void testCompile() {
		String path = System.getProperty("user.dir");
		String newPath = path+"\\test";
		String newPath1 = path+"\\test1";
		FileSourceJdtCompiler compiler = new FileSourceJdtCompiler();
		FileSource[] fileSources = { new FileSource(newPath),
				new FileSource(newPath1) };
		try {
			boolean flag = compiler.compile(fileSources);
			assertTrue(flag);
		} catch (CompileException e) {
			assertTrue(false);
		}
	}
	
	
}
