package org.tinygroup.i18n;

import java.io.IOException;
import java.util.Properties;

import junit.framework.TestCase;

import org.tinygroup.commons.i18n.LocaleUtil;
import org.tinygroup.context.impl.ContextImpl;

public class I18nMessagesTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Properties properties = new Properties();
		properties.load(getClass().getResource("/i18n/info_zh_CN.properties").openStream());
		I18nMessageFactory.addCustomizeResource(LocaleUtil.getContext().getLocale(), properties);
	}
	
	public void testGetMessage(){
		I18nMessages i18 = I18nMessageFactory.getI18nMessages();
		//测试name
		assertEquals("罗果", i18.getMessage("name"));
		assertEquals("罗果", i18.getMessage("name", new ContextImpl()));
		assertEquals("罗果", i18.getMessage("name", new ContextImpl(), LocaleUtil.getContext().getLocale()));
		assertEquals("罗果", i18.getMessage("name", "default", new ContextImpl()));
		assertEquals("罗果", i18.getMessage("name", ""));
		assertEquals("罗果", i18.getMessage("name", LocaleUtil.getContext().getLocale()));
		assertEquals("罗果", i18.getMessage("name", "", new ContextImpl(), LocaleUtil.getContext().getLocale()));
		
		//测试defaultValue
		assertEquals(null, i18.getMessage("name1"));
		assertEquals("", i18.getMessage("name1", new ContextImpl()));
		assertEquals("", i18.getMessage("name1", new ContextImpl(), LocaleUtil.getContext().getLocale()));
		assertEquals("default", i18.getMessage("name1", "default", new ContextImpl()));
		assertEquals("default", i18.getMessage("name1", "default", new Object()));
		assertEquals("default", i18.getMessage("name1", LocaleUtil.getContext().getLocale(), "default",new Object()));
		assertEquals("default", i18.getMessage("name1", "default", new ContextImpl(), LocaleUtil.getContext().getLocale()));
	}

	public void testGetMessage1(){
		I18nMessages i18 = I18nMessageFactory.getI18nMessages();
		//占位符多余参数
		try {
			i18.getMessage("loginInfo1", LocaleUtil.getContext().getLocale(), "default","z" ,"zz");
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
		//占位符多余参数，无参
		try {
			i18.getMessage("loginInfo1", LocaleUtil.getContext().getLocale(), "default");
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
		//占位符多余参数，无参
		try {
			i18.getMessage("loginInfo1", LocaleUtil.getContext().getLocale(), "default","z");
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
		//正确写法
		try {
			i18.getMessage("loginInfo1", LocaleUtil.getContext().getLocale(), "default","z" ,"zz" ,"zzz");
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
		//参数多余占位符
		try {
			i18.getMessage("loginInfo1", LocaleUtil.getContext().getLocale(), "default","z" ,"zz" ,"zzz" ,"zzzz");
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
		//----------------------------------------
		//占位符多余参数
		try {
			i18.getMessage("loginInfo", LocaleUtil.getContext().getLocale(), "default");
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
		//正常写法
		try {
			i18.getMessage("loginInfo", LocaleUtil.getContext().getLocale(), "default" ,"z");
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
		//参数多余占位符
		try {
			i18.getMessage("loginInfo", LocaleUtil.getContext().getLocale(), "default" ,"z" ,"zz");
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
		//-----------------------------------
		//正确写法
		try {
			i18.getMessage("name", LocaleUtil.getContext().getLocale(), "default" );
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
		//参数多余占位符
		try {
			i18.getMessage("name", LocaleUtil.getContext().getLocale(), "default" ,"z" ,"zz");
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
}
