package org.tinygroup.mbean.testcase;


import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.tinygroup.mbean.Hello;

/**
 * 
 * @description：MBean注册测试
 * @author: qiuqn
 * @version: 2016年5月13日 上午11:27:50
 */
public class MBeanTest {

	public static void main(String[] args) throws Exception {
		MBeanServer mb = ManagementFactory.getPlatformMBeanServer();
		ObjectName name = new ObjectName("TinyMBean:name=Hello");
        Hello testMBean = new Hello();
        mb.registerMBean(testMBean, name);
        mb.invoke(name, "print", new Object[] { "我叫小明"}, new String[] {"java.lang.String"});   
	}
}
