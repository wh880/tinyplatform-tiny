/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tinygroup.nettyremote.test;

import org.tinygroup.nettyremote.impl.ClientImpl;
import org.tinygroup.nettyremote.impl.ServerImpl;
import org.tinygroup.threadgroup.MultiThreadProcessor;

public class TestThreadGroup{
	int serverPort = 9090;
	int beginPort = 7000;
	String host = "127.0.0.1";
	MultiThreadProcessor processors = new MultiThreadProcessor("net deal");
	int count = 11;
	int threadNum = 11;

	public static void main(String[] args) {
		TestThreadGroup t = new TestThreadGroup();
		t.testStart();
	}
	public void testStart() {
		startServer();
		sleep(2000);
		startClient();
		sleep(5000);
		long startTime = System.currentTimeMillis();
		processors.start();
		long endTime = System.currentTimeMillis();
		System.out.println("Total USE:");
		System.out.println(endTime - startTime);
	}

	private void startServer() {
		ServerImpl serverImpl = new ServerImpl(serverPort,true);
		serverImpl.start();
	}

	private void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void startClient() {
		for (int i = 0; i < threadNum; i++) {
			ClientImpl c = new ClientImpl(serverPort, host,false);
			c.start();
			processors
					.addProcessor(new DealProcessor(c, "net deal" + i, count));
		}
	}
}
