package org.tinygroup.bundle;

import junit.framework.TestCase;
import org.tinygroup.bundle.Bundle;

public class HelloBundleTest extends TestCase {
	Bundle helloBundle = new HelloBundle();

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testGetServiceStringString() {
		Hello hello = helloBundle.getService("hello", "1.0");
		assertEquals("1.0: hello, yes", hello.sayHello("yes"));
	}

	public void testGetServiceString() {
		Hello hello = helloBundle.getService("hello");
		assertEquals("1.0: hello, yes", hello.sayHello("yes"));
	}

	public void testGetServiceClassOfTString() {
		Hello hello = helloBundle.getService(Hello.class, "1.0");
		assertEquals("1.0: hello, yes", hello.sayHello("yes"));
	}

	public void testGetServiceClassOfT() {
		Hello hello = helloBundle.getService(Hello.class);
		assertEquals("1.0: hello, yes", hello.sayHello("yes"));
	}

}
