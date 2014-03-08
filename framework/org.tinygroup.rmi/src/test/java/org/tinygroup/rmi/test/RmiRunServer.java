package org.tinygroup.rmi.test;

import java.rmi.RemoteException;

import org.tinygroup.rmi.RmiServer;
import org.tinygroup.rmi.impl.RmiServerLocal;

public class RmiRunServer {
	
	private static String LOCALIP = "192.168.84.52";

	public static void main(String[] args) {
		RmiServer localServer = new RmiServerLocal(LOCALIP, 8888);
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
