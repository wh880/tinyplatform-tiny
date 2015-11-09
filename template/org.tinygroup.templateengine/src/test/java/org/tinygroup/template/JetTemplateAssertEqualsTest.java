package org.tinygroup.template;

import org.tinygroup.commons.io.ByteArrayOutputStream;
import org.tinygroup.commons.tools.FileUtil;
import org.tinygroup.template.TemplateEngine;
import org.tinygroup.template.impl.TemplateContextDefault;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.loader.FileObjectResourceLoader;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.FileObjectProcessor;
import org.tinygroup.vfs.VFS;
import org.tinygroup.vfs.impl.filter.FileNameFileObjectFilter;

import junit.framework.TestCase;

/*
 * 执行/src/test/resources/templateJunitRight中的测试用例，以Junit方式。
 * 本次执行的都是正确格式输入的
 * */
public class JetTemplateAssertEqualsTest extends TestCase {
	
	TemplateEngine engine ;//模板引擎

	//执行每一个测试方法前都会调用的方法 -- 用于初始化
	public void setUp(){
		engine = new TemplateEngineDefault();
		FileObjectResourceLoader jetSample = new FileObjectResourceLoader(
				"jetx", null, "component", "src/test/resources/templateassertequalstest");
		engine.addResourceLoader(jetSample);	 		
	}
	
	//每个测试方法执行完以后主动调用的方法 -- 用于清理或释放资源
	public void tearDown(){
		
	}
	
	public void testTemplateJunit(){
		
        FileObject fileObject = VFS.resolveFile("src/test/resources/templateassertequalstest");
        fileObject.foreach(new FileNameFileObjectFilter(".*\\.jetx", true), new FileObjectProcessor() {
            public void process(FileObject fileObject) {
                try {
                	ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
                    System.out.println(String.format("正在测试模板文件：%s\n",fileObject.getAbsolutePath()));
                    engine.renderTemplate(fileObject.getPath(),new TemplateContextDefault(),outputStream);
                    String result = new String(outputStream.toByteArray().toByteArray(),"UTF-8");
                    String expectResult = FileUtil.readStreamContent(VFS.resolveFile(fileObject.getAbsolutePath()+".txt").getInputStream(), "UTF-8");
                    assertEquals(result, expectResult);//断言方式比对实际输出和预期输出
                } catch (Exception e) {
                	e.printStackTrace();
                    fail("测试模板文件fail："+fileObject.getAbsolutePath());
                }
            }
        });
	}
}
