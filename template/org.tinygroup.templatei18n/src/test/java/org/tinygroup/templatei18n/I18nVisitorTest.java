package org.tinygroup.templatei18n;

import java.util.Locale;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.tinygroup.commons.i18n.LocaleUtil;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.impl.TemplateContextDefault;
import org.tinygroup.tinytestutil.AbstractTestUtil;

public class I18nVisitorTest extends TestCase{

	protected void setUp() throws Exception {
		AbstractTestUtil.init(null, false);
	}
	
	/**
	 * 测试上下文获取Locale
	 */
	public void testContextLocaleI18nVisitor(){
		TemplateContext context = new TemplateContextDefault();
		ContextLocaleI18nVisitor i18nVisitor = new ContextLocaleI18nVisitor();
		i18nVisitor.setLocaleName("i18nlocale");
		String result;
		Assert.assertNull(i18nVisitor.getLocale(context));
		
		context.put("i18nlocale", Locale.US);
		result=i18nVisitor.getI18nMessage(context, "sex");
		Assert.assertEquals("male", result);
		
		context.put("name", "dog");
		result=i18nVisitor.getI18nMessage(context, "loginInfoContext");
		Assert.assertEquals("user dog login faild.", result);
		
		//测试不存在的键值
		result=i18nVisitor.getI18nMessage(context, "aaa");
		Assert.assertEquals("", result);
	}
	
	/**
	 * 测试LocaleUtil取得默认的locale
	 */
    public void testDefaultLocaleI18nVisitor(){
    	TemplateContext context = new TemplateContextDefault();
    	DefaultLocaleI18nVisitor i18nVisitor = new DefaultLocaleI18nVisitor();
    	String result;
    	
    	Assert.assertNotNull(i18nVisitor.getLocale(context));
    	
		LocaleUtil.setDefault(Locale.US);
		result=i18nVisitor.getI18nMessage(context, "sex");
		Assert.assertEquals("male", result);
		
		context.put("name", "dog");
		result=i18nVisitor.getI18nMessage(context, "loginInfoContext");
		Assert.assertEquals("user dog login faild.", result);
		
		//测试不存在的键值
		result=i18nVisitor.getI18nMessage(context, "aaa");
		Assert.assertEquals("", result);
		
	}
    
    /**
     * 测试LocaleUtil取得系统的locale(因为不同系统的locale不同，所以不能比对内容)
     */
    public void testSystemLocaleI18nVisitor(){
 	    TemplateContext context = new TemplateContextDefault();
    	ThreadLocaleI18nVisitor i18nVisitor = new ThreadLocaleI18nVisitor();
    	
    	Assert.assertNotNull(i18nVisitor.getLocale(context));
	}
    
    /**
     * 测试LocaleUtil取得当前线程的locale
     */
    public void testThreadLocaleI18nVisitor(){

		LocaleUtil.setContext(Locale.CHINESE);
		
    	TemplateContext context = new TemplateContextDefault();
    	ThreadLocaleI18nVisitor i18nVisitor = new ThreadLocaleI18nVisitor();
    	
    	Assert.assertNotNull(i18nVisitor.getLocale(context));
		
		//测试不存在的键值
		String result=i18nVisitor.getI18nMessage(context, "aaa");
		Assert.assertEquals("", result);
		
	}
}
