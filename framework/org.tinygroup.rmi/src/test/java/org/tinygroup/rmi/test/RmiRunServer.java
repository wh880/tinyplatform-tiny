/**
 *  Copyright (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
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
 * --------------------------------------------------------------------------
 *  版权 (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  本开源软件遵循 GPL 3.0 协议;
 *  如果您不遵循此协议，则不被允许使用此文件。
 *  你可以从下面的地址获取完整的协议文本
 *
 *       http://www.gnu.org/licenses/gpl.html
 */
package org.tinygroup.rmi.test;

import java.rmi.RemoteException;

import org.tinygroup.rmi.RmiServer;
import org.tinygroup.rmi.impl.RmiServerLocal;
import org.tinygroup.rmi.impl.RmiUtil;

public class RmiRunServer {
	
	private static String LOCALIP = "192.168.84.23";

	public static void main(String[] args) {
		RmiServer localServer = null;
		try {
			localServer = new RmiServerLocal(LOCALIP, 8888);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		RmiUtil.start((Runnable)localServer);
		try {
			localServer.registerRemoteObject(new HelloImpl(), "hello");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		RmiRunServer r = new RmiRunServer();
		r.runThread();
	}

	public void runThread() {
		MyThread t = new RmiRunServer.MyThread();
		t.run();
	}

	class MyThread extends Thread {
		private boolean end = false;

		public void run() {
			if (!end) {
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
