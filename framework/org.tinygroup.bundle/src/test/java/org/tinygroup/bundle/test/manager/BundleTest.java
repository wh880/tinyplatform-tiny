package org.tinygroup.bundle.test.manager;

import junit.framework.TestCase;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.bundle.BundleException;
import org.tinygroup.bundle.BundleManager;
import org.tinygroup.bundle.config.BundleDefine;
import org.tinygroup.bundle.test.util.TestUtil;

public class BundleTest extends TestCase{
	public void setUp(){
		
	}
	
	public void testAddAndRemove(){
		TestUtil.init();
		BundleManager bundleManager = BeanContainerFactory.getBeanContainer(
				TestUtil.class.getClassLoader()).getBean(
				BundleManager.BEAN_NAME);
		BundleDefine d = new BundleDefine();
		d.setName("testJar");
		bundleManager.addBundleDefine(d);
		bundleManager.start("testJar");
		ClassLoader loader = null;
		loader = bundleManager.getTinyClassLoader(d);
		try {
			BeanContainerFactory.getBeanContainer(
					loader).getBean(
					"bundleTestService");
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
		try {
			bundleManager.removeBundle(bundleManager.getBundleDefine("testJar"));
		} catch (BundleException e) {
			e.printStackTrace();
		}
		try {
			BeanContainerFactory.getBeanContainer(
					loader).getBean(
					"bundleTestService");
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
	}
}
