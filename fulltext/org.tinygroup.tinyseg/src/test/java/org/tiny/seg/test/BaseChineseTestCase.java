package org.tiny.seg.test;

import junit.framework.TestCase;

import org.tinygroup.tinyrunner.Runner;

public abstract class BaseChineseTestCase extends TestCase{
	protected void setUp() throws Exception {
		super.setUp();
		Runner.init("application.xml", null);
	}
}
