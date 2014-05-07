package org.tinygroup.bundle.test.manager;

import org.tinygroup.bundle.BundleManager;
import org.tinygroup.bundle.impl.BundleContextImpl;
import org.tinygroup.bundle.test.util.TestUtil;
import org.tinygroup.springutil.SpringUtil;

import junit.framework.TestCase;

public class BundleManagerTest extends TestCase{
	public void test(){
		TestUtil.init();
		BundleManager manager = SpringUtil.getBean(BundleManager.BEAN_NAME);
		manager.start(new BundleContextImpl());
		try {
			manager.getTinyClassLoader().findClass1("org.tinygroup.hello.HelloImpl1");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
