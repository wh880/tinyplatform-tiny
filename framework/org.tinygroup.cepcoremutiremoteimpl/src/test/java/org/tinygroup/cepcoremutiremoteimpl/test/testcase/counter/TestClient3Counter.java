package org.tinygroup.cepcoremutiremoteimpl.test.testcase.counter;

import java.util.ArrayList;

import org.tinygroup.tinyrunner.Runner;

public class TestClient3Counter {
	public static void main(String[] args) throws Exception {
		Runner.init("application3.xml", new ArrayList<String>());
		PrintThread p  = new PrintThread();
		p.start();
	}
	
	
}
