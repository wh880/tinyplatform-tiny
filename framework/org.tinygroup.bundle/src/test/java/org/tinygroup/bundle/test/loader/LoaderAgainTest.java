package org.tinygroup.bundle.test.loader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.tinygroup.bundle.loader.TinyClassLoader;
import org.tinygroup.bundle.test.util.TestUtil;

import junit.framework.TestCase;

public class LoaderAgainTest extends TestCase{

	public void testLoadAgain(){
		String path1 = System.getProperty("user.dir")+"/test1"+"/test1-0.0.1-SNAPSHOT.jar";
		String path2 = System.getProperty("user.dir")+"/test2"+"/test2-0.0.1-SNAPSHOT.jar";
		try {
			URL u1 = new File(path1).toURL();
			TinyClassLoader l1 = new TinyClassLoader(new URL[]{u1});
			
			URL u2 = new File(path2).toURL();
			TinyClassLoader l2 = new TinyClassLoader(new URL[]{u2});
			
			l2.addDependClassLoader(l1);
			l1.loadClass("org.tinygroup.MyTestInterface");
			l1.loadClass("org.tinygroup.MyTestImpl");
			l2.loadClass("org.tinygroup.MyTestInterface");
			l2.loadClass("org.tinygroup.MyTestImpl");
			l2.loadClass("org.tinygroup.MyTestImpl2");
			
			
			TinyClassLoader l3 = new TinyClassLoader(new URL[]{u1});
			l2.removeDependTinyClassLoader(l1);
			l2.addDependClassLoader(l3);
			l3.loadClass("org.tinygroup.MyTestInterface");
			l3.loadClass("org.tinygroup.MyTestImpl");
			l2.loadClass("org.tinygroup.MyTestInterface");
			l2.loadClass("org.tinygroup.MyTestImpl");
			l2.loadClass("org.tinygroup.MyTestImpl2");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
