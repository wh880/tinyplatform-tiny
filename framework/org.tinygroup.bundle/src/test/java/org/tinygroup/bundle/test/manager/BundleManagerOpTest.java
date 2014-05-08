package org.tinygroup.bundle.test.manager;

import junit.framework.TestCase;

import org.tinygroup.bundle.BundleException;
import org.tinygroup.bundle.BundleManager;
import org.tinygroup.bundle.config.BundleDefine;
import org.tinygroup.bundle.test.util.TestUtil;
import org.tinygroup.springutil.SpringUtil;

public class BundleManagerOpTest extends TestCase{
	public void testPath() {
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

	}
}
