package org.tinygroup.compiler.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import junit.framework.TestCase;

import org.tinygroup.compiler.CompileException;
import org.tinygroup.compiler.impl.MemorySource;
import org.tinygroup.compiler.impl.MemorySourceJdtCompiler;

public class MemorySourceJdtCompilerTest extends TestCase{
	public void testCompile() {
		
		String path = System.getProperty("user.dir");
		String filePath = path+"\\test1\\org\\tinygroup\\Grade.java";
		String sourceFolder = path+"\\test2";
		String content = selfReadFile(filePath);
		MemorySource source = new MemorySource("org.tinygroup.Grade", content);
		MemorySourceJdtCompiler msc = new MemorySourceJdtCompiler(sourceFolder);
		try {
			boolean flag = msc.compile(source);
			assertTrue(flag);
		} catch (CompileException e) {
			assertTrue(false);
		}
		
	}
	
	private String selfReadFile(String strFileName) {
        //Read the file into a string buffer, then return as a string.
        StringBuffer buf = null;//the intermediary, mutable buffer
        BufferedReader breader = null;//reader for the template files
        try {
            breader = new BufferedReader(new InputStreamReader(new FileInputStream((strFileName)), Charset.forName("utf-8")));
            buf = new StringBuffer();
            while (breader.ready()) {
                buf.append((char) breader.read());
            }
            breader.close();
        }//try
        catch (Exception e) {
            e.printStackTrace();
        }//catch
        return buf.toString();
    }
}
