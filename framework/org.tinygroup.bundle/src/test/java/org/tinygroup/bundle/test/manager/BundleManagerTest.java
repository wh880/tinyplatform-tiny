package org.tinygroup.bundle.test.manager;

import junit.framework.TestCase;

import org.tinygroup.bundle.BundleException;
import org.tinygroup.bundle.BundleManager;
import org.tinygroup.bundle.config.BundleDefine;
import org.tinygroup.bundle.test.util.TestUtil;
import org.tinygroup.springutil.SpringUtil;

public class BundleManagerTest extends TestCase{
	public void testStart(){
		TestUtil.init();
		BundleManager manager = SpringUtil.getBean(BundleManager.BEAN_NAME);
		manager.start();
		try {
//			manager.getTinyClassLoader("test2").loadClass("org.tinygroup.MyTestImpl2");
			manager.getTinyClassLoader("test1").loadClass("org.tinygroup.MyTestInterface");
			manager.getTinyClassLoader("test1").loadClass("org.tinygroup.MyTestImpl");
			manager.getTinyClassLoader("test2").loadClass("org.tinygroup.MyTestImpl2");
			manager.getTinyClassLoader("test3").loadClass("org.tinygroup.MyTestImpl3");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			assertFalse(true);
		}
		manager.stop();
	}
	
	public void testStop(){
		TestUtil.init();
		BundleManager manager = SpringUtil.getBean(BundleManager.BEAN_NAME);
		manager.start();
		try {
			manager.getTinyClassLoader("test2").loadClass("org.tinygroup.MyTestImpl2");
			
		} catch (ClassNotFoundException e) {
			assertFalse(true);
		}
		manager.stop();
		if(manager.getTinyClassLoader("test2")==null){
			assertTrue(true);
		}else{
			assertTrue(false);
		}
		
	}
	
	public void testLoad() {
		TestUtil.init();
		BundleManager manager = SpringUtil.getBean(BundleManager.BEAN_NAME);
		BundleDefine b;
		try {
			b = manager.getBundleDefine("test1");
			assertNotNull(b);
		} catch (BundleException e) {
			e.printStackTrace();
			assertFalse(true);
		}
		manager.stop();
	}
	public void testRemove(){
		TestUtil.init();
		BundleManager manager = SpringUtil.getBean(BundleManager.BEAN_NAME);
		manager.start();
		try {
			manager.getTinyClassLoader("test2").loadClass("org.tinygroup.MyTestImpl2");
			//manager.getTinyClassLoader().loadClass("org.tinygroup.hello.HelloImpl2");
			
		} catch (ClassNotFoundException e) {
			assertFalse(true);
		}
		try {
			manager.removeBundle(manager.getBundleDefine("test1"));
		} catch (BundleException e) {
			
			e.printStackTrace();
		}
		manager.stop();
	}
	

}
