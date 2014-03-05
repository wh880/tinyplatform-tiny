package org.tinygroup.tinypc.test.foreman;

import java.io.IOException;

import org.tinygroup.tinypc.JobCenter;
import org.tinygroup.tinypc.impl.JobCenterLocal;

public class WorkerServer {

	public static void main(String[] args) {
		try {
			JobCenter jobCenter = new JobCenterLocal(8888);
			jobCenter.getRmiServer();
		} catch (IOException e) {
			e.printStackTrace();
		}

		WorkerServer r = new WorkerServer();
		r.runThread();
	}

	public void runThread() {
		MyThread t = new WorkerServer.MyThread();
		t.run();
	}

	class MyThread extends Thread {
		private boolean end = false;

		public void run() {
			while (!end) {
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
