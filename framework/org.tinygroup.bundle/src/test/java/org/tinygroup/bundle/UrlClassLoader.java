package org.tinygroup.bundle;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class UrlClassLoader {
	public static void main(String[] args) throws Exception {
		URL u = new URL("file:c:/SayHello.jar");
		URLClassLoader ucl = new URLClassLoader(new URL[] { u },
				UrlClassLoader.class.getClassLoader());
		Object o = ucl.loadClass("org.snowrain.Hello");

		URL u1 = new URL("http://localhost:8088/SayHello.jar");
		URLClassLoader ucl1 = new URLClassLoader(new URL[] { u },
				UrlClassLoader.class.getClassLoader());
		Object o1 = ucl1.loadClass("org.snowrain.Hello").newInstance();
		Thread.currentThread().setContextClassLoader(ucl);
		Class[] pc = { String.class };
		Method m = o1.getClass().getMethod("sayHello", pc);
		if (m != null) {
			Object[] p = { "aa" };
			m.invoke(o1, p);
		}
		//		Class.forName("org.snowrain.Hello");
	}
}
