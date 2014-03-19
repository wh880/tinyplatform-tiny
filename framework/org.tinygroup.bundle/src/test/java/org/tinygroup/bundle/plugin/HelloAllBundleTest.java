package org.tinygroup.bundle.bundle;

import junit.framework.TestCase;
import org.tinygroup.bundle.Bundle;

public class HelloAllBundleTest extends TestCase {
	Bundle helloBundle = new HelloBundle();
	Bundle helloAllBundle = new HelloAllBundle();

	protected void setUp() throws Exception {
		super.setUp();
		Hello hello = helloBundle.getService("hello");
		helloAllBundle.setService(hello, Hello.class);
	}

	public void testGetServiceStringString() {
		HelloAll hello = helloAllBundle.getService("helloAll", "1.0");
		assertEquals("1.0: hello, yes", hello.sayHello("yes"));
	}

	public void testGetServiceString() {
		HelloAll hello = helloAllBundle.getService("helloAll");
		assertEquals("1.0: hello, yes", hello.sayHello("yes"));
	}

	public void testGetServiceClassOfTString() {
		HelloAll hello = helloAllBundle.getService(HelloAll.class, "1.0");
		assertEquals("1.0: hello, yes", hello.sayHello("yes"));
	}

	public void testGetServiceClassOfT() {
		HelloAll hello = helloAllBundle.getService(HelloAll.class);
		assertEquals("1.0: hello, yes", hello.sayHello("yes"));
	}

}
