package org.tinygroup.codegen;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.tinygroup.codegen.config.CodeGenMetaData;
import org.tinygroup.codegen.util.CodeGenUtil;
import org.tinygroup.context.Context;
import org.tinygroup.context.util.ContextFactory;
import org.tinygroup.tinytestutil.AbstractTestUtil;
import org.tinygroup.xstream.XStreamFactory;

import com.thoughtworks.xstream.XStream;

public class CodeGenTest extends TestCase {
	String testJavaPath;
	protected void setUp() throws Exception {
		super.setUp();
		AbstractTestUtil.init(null, true);
	}

	protected void tearDown() throws Exception {
		File file=new File(testJavaPath+CodeGenUtil.packageToPath("org.tinygroup.codegen")+"HelloWorld.java");
		assertTrue(file.exists());
		file.deleteOnExit();
	}

	
	public void testCodeGen(){
		
		XStream xStream=XStreamFactory.getXStream(CodeGenerator.XSTEAM_PACKAGE_NAME);
		CodeGenMetaData metaData=(CodeGenMetaData) xStream.fromXML(getClass().getResourceAsStream("/test.codegen.xml"));
		Context context=ContextFactory.getContext();
		String projectPath=System.getProperty("user.dir");
		testJavaPath=projectPath+File.separator+"src"+File.separator+"test"+File.separator+"java"+File.separator;
		context.put(CodeGenerator.JAVA_ROOT, projectPath+File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator);
		context.put(CodeGenerator.JAVA_RES_ROOT, projectPath+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator);
		context.put(CodeGenerator.JAVA_TEST_ROOT, testJavaPath);
		context.put(CodeGenerator.JAVA_TEST_RES_ROOT, projectPath+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator);
		context.put(CodeGenerator.CODE_META_DATA, metaData);
		context.put("beanPackageName", "org.tinygroup.codegen");
		context.put("className", "HelloWorld");
		CodeGenerator generator=new CodeGenerator();
		try {
			generator.generate(context);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

}
