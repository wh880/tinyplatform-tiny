package org.tinygroup.template;

import junit.framework.TestCase;

import org.tinygroup.template.impl.TemplateContextDefault;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.impl.TemplateRenderDefault;

/**
 * 中文标识符测试
 * @author yancheng11334
 *
 */
public class ChineseIdentifierTest extends TestCase{

	 private TemplateRender templateRender;
	 
	 protected void setUp(){
		 templateRender = new TemplateRenderDefault();
		 templateRender.setTemplateEngine(new TemplateEngineDefault());
	 }
	 
	 /**
	  * 中文参数测试用例
	  * @throws TemplateException
	  */
	 public void testParameter() throws TemplateException{
		 assertEquals("abc", templateRender.renderTemplateContent("#set(中文a='abc')${中文a}", new TemplateContextDefault()));
		 assertEquals("3", templateRender.renderTemplateContent("#set(中文b=1+2)${中文b}", new TemplateContextDefault()));
	 }
}
