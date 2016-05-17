package org.tinygroup.mbean.testcase;

import java.util.ArrayList;

import org.tinygroup.tinyrunner.Runner;

/**
 * 
 * @description：TinyMBean注册测试
 * @author: qiuqn
 * @version: 2016年5月13日 上午11:28:01
 */
public class TinyMBeanTest {

	public static void main(String[] args) throws Exception {
		Runner.init("application.xml", new ArrayList<String>());
		Thread.sleep(5000000);
	}	
}
