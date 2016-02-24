package org.tinygroup.springmerge;

import java.util.Map;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.tinygroup.springmerge.beanfactory.SpringMergeApplicationContext;

import junit.framework.TestCase;

public class SpringMergeTest extends TestCase {

	private static SpringMergeApplicationContext applicationContext;
	private static boolean isInited;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		if (!isInited) {
			applicationContext = new SpringMergeApplicationContext(
					loadMergeBeanDefinition());
			applicationContext.setConfigLocation("classpath:test.xml");
			applicationContext.refresh();
			isInited = true;
		}
	}
	
	private BeanDefinitionRegistry loadMergeBeanDefinition() {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource resource = resourceLoader.getResource("MERGE-INFO/merge.xml");
		BeanDefinitionRegistry registry=null;
		if(resource.exists()){
			registry=new XmlBeanFactory(resource);
		}else{
			registry=new DefaultListableBeanFactory();
		}
		return registry;
	}

	public void testStringMerge() {
		MergeObject mergeObject = (MergeObject) applicationContext
				.getBean("stringMerge");
		assertEquals("jpg,docx,png", mergeObject.getSuffix());
	}

	public void testMapMerge() {
		MergeObject mergeObject = (MergeObject) applicationContext
				.getBean("mapMerge");
		Map<String, String> map = mergeObject.getParamsMap();
		assertEquals(5, map.size());
		assertEquals("value11", map.get("key1"));
		assertEquals("value5", map.get("key5"));
	}

}
