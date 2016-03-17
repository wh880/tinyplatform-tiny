package org.tinygroup.httpclient451;

import java.util.ArrayList;

import org.tinygroup.httpclient451.mock.MockServer;
import org.tinygroup.tinyrunner.Runner;

import junit.framework.TestCase;

/**
 * 扩展了Mock服务
 * @author yancheng11334
 *
 */
public abstract class ServerTestCase extends TestCase{

    private MockServer server = new MockServer();
	
	protected void setUp() {
		Runner.init("application.xml", new ArrayList<String>());
		server.start();
	}
	
	protected void tearDown() {
		server.stop();
	}

}
