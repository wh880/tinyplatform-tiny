package org.tinygroup.bundle.test.manager;

import org.tinygroup.bundle.BundleException;
import org.tinygroup.bundle.BundleManager;
import org.tinygroup.bundle.impl.BundleContextImpl;
import org.tinygroup.bundle.test.util.TestUtil;
import org.tinygroup.springutil.SpringUtil;

import junit.framework.TestCase;

public class BundleManagerTest extends TestCase{
	public void testStart(){
		TestUtil.init();
		BundleManager manager = SpringUtil.getBean(BundleManager.BEAN_NAME);
		manager.start();
		try {
			manager.getTinyClassLoader().loadClass("org.tinygroup.hello.HelloImpl1");
		} catch (ClassNotFoundException e) {
			assertFalse(true);
		}
		manager.stop();
	}
	
	public void testStop(){
		TestUtil.init();
		BundleManager manager = SpringUtil.getBean(BundleManager.BEAN_NAME);
		manager.start();
		try {
			manager.getTinyClassLoader().loadClass("org.tinygroup.hello.HelloImpl1");
			
		} catch (ClassNotFoundException e) {
			assertFalse(true);
		}
		manager.stop();
		boolean flag = false;
		try {
			manager.getTinyClassLoader().loadClass("org.tinygroup.hello.HelloImpl1");
		} catch (ClassNotFoundException e) {
			flag = true;
		}
		assertTrue(flag);
	}
	
	public void testRemove(){
		TestUtil.init();
		BundleManager manager = SpringUtil.getBean(BundleManager.BEAN_NAME);
		manager.start();
		try {
			manager.getTinyClassLoader().loadClass("org.tinygroup.hello.HelloImpl1");
			
		} catch (ClassNotFoundException e) {
			assertFalse(true);
		}
		try {
			manager.removeBundle(manager.getBundleDefine("hello"));
		} catch (BundleException e) {
			
			e.printStackTrace();
		}
		manager.stop();
	}
}
