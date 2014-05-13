package org.tinygroup.springutil.test.loader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import junit.framework.TestCase;

import org.springframework.context.support.FileSystemXmlApplicationContext;

public class LoaderFactoryTest extends TestCase{

	public void testLoader() {
		String file1 = "J:\\tinygroupgit\\tiny\\framework\\org.tinygroup.springutil\\src\\test\\resources\\grade.beans.xml";
		String file2 = "J:\\tinygroupgit\\tiny\\framework\\org.tinygroup.springutil\\src\\test\\resources\\user.beans.xml";
		String jar1 = "J:\\tinygroupgit\\tiny\\framework\\org.tinygroup.springutil\\src\\vtest\\resources\\test1-0.0.1-SNAPSHOT.jar";
		String jar2 = "J:\\tinygroupgit\\tiny\\framework\\org.tinygroup.springutil\\src\\test\\resources\\test2-0.0.1-SNAPSHOT.jar";
		ClassLoader loader1 = null;
		ClassLoader loader2 = null;
		try {
			
			URL u1 = new File(jar1).toURL();
			URL u2 = new File(jar2).toURL();
			
			loader1 = new URLClassLoader(new URL[]{u1});
			loader2 = new URLClassLoader(new URL[]{u2});
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] s1 = new String[] { file1 };
		String[] s2 = new String[] { file2 };
		FileSystemXmlApplicationContext app1 = new FileSystemXmlApplicationContext(s1);
		app1.setClassLoader(loader1);
		FileSystemXmlApplicationContext app2 = new FileSystemXmlApplicationContext(s2,app1);
		app2.setClassLoader(loader2);
		app1.refresh();
		app2.refresh();
		
		app2.getBean("user");
	}
}
