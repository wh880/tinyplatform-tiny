package org.tinygroup.i18n;

import java.io.IOException;
import java.util.Properties;

import org.tinygroup.commons.i18n.LocaleUtil;
import org.tinygroup.context.impl.ContextImpl;

import junit.framework.TestCase;

public class I18nMessagesTest extends TestCase {

	public void testGetMessage(){
		Properties properties = new Properties();
		try {
			properties.load(getClass().getResource("/i18n/info_zh_CN.properties").openStream());
			I18nMessageFactory.addCustomizeResource(LocaleUtil.getContext().getLocale(), properties);
			
			
			I18nMessages i18 = I18nMessageFactory.getI18nMessages();
			//测试name
			assertEquals("罗果", i18.getMessage("name"));
			assertEquals("罗果", i18.getMessage("name", new ContextImpl()));
			assertEquals("罗果", i18.getMessage("name", new ContextImpl(), LocaleUtil.getContext().getLocale()));
			assertEquals("罗果", i18.getMessage("name", "default", new ContextImpl()));
			assertEquals("罗果", i18.getMessage("name", "", new Object()));
			assertEquals("罗果", i18.getMessage("name", LocaleUtil.getContext().getLocale(), "",new Object()));
			assertEquals("罗果", i18.getMessage("name", "", new ContextImpl(), LocaleUtil.getContext().getLocale()));
			
			//测试defaultValue
			assertEquals(null, i18.getMessage("name1"));
			assertEquals("", i18.getMessage("name1", new ContextImpl()));
			assertEquals("", i18.getMessage("name1", new ContextImpl(), LocaleUtil.getContext().getLocale()));
			assertEquals("default", i18.getMessage("name1", "default", new ContextImpl()));
			assertEquals("default", i18.getMessage("name1", "default", new Object()));
			assertEquals("default", i18.getMessage("name1", LocaleUtil.getContext().getLocale(), "default",new Object()));
			assertEquals("default", i18.getMessage("name1", "default", new ContextImpl(), LocaleUtil.getContext().getLocale()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
