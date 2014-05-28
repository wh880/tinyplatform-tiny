package org.tinygroup.codegen;

import java.io.IOException;

import junit.framework.TestCase;

import org.tinygroup.codegen.config.CodeGenMetaData;
import org.tinygroup.context.Context;
import org.tinygroup.context.util.ContextFactory;
import org.tinygroup.tinytestutil.AbstractTestUtil;
import org.tinygroup.xstream.XStreamFactory;

import com.thoughtworks.xstream.XStream;

public class CodeGenTest extends TestCase {
	
	protected void setUp() throws Exception {
		super.setUp();
		AbstractTestUtil.init(null, true);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	
	public void testCodeGen(){
		XStream xStream=XStreamFactory.getXStream(CodeGenerater.XSTEAM_PACKAGE_NAME);
		CodeGenMetaData metaData=(CodeGenMetaData) xStream.fromXML(getClass().getResourceAsStream("/test.codegen.xml"));
		Context context=ContextFactory.getContext();
		context.put(CodeGenerater.JAVA_ROOT, "D:\\tiny-git\\tiny\\framework\\org.tinygroup.codegen\\src\\main\\java\\");
		context.put(CodeGenerater.JAVA_RES_ROOT, "D:\\tiny-git\\tiny\\framework\\org.tinygroup.codegen\\src\\main\\resources\\");
		context.put(CodeGenerater.JAVA_TEST_ROOT, "D:\\tiny-git\\tiny\\framework\\org.tinygroup.codegen\\src\\test\\java\\");
		context.put(CodeGenerater.JAVA_TEST_RES_ROOT, "D:\\tiny-git\\tiny\\framework\\org.tinygroup.codegen\\src\\test\\resources\\");
		context.put(CodeGenerater.CODE_META_DATA, metaData);
		context.put("beanPackageName", "org.tinygroup.codegen.test");
		context.put("className", "HelloWorld");
		CodeGenerater generater=new CodeGenerater();
		try {
			generater.generate(context);
		} catch (IOException e) {
			fail(e.getMessage());
		}
		
	}

}
