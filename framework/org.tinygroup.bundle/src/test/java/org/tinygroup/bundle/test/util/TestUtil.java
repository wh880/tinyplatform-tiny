package org.tinygroup.bundle.test.util;

import org.tinygroup.bundle.BundleManager;
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.tinytestutil.AbstractTestUtil;



public class TestUtil {
	private static boolean init = false;
	public static void init(){
		System.out.println(TestUtil.class.getResource("/").getPath());
		System.out.println(System.getProperty("user.dir"));
		if(!init){
			AbstractTestUtil.init("application.xml", true);
			init = true;
			BundleManager manager = SpringUtil.getBean(BundleManager.BEAN_NAME);
			
			manager.setCommonRoot(System.getProperty("user.dir"));
			manager.setBundleRoot(System.getProperty("user.dir"));
		}
		
	}
}
