package org.tinygroup.tinypc.test.plus;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.tinygroup.tinypc.Foreman;
import org.tinygroup.tinypc.JobCenter;
import org.tinygroup.tinypc.TestUtil;
import org.tinygroup.tinypc.Worker;
import org.tinygroup.tinypc.impl.ForemanSelectOneWorker;
import org.tinygroup.tinypc.impl.JobCenterLocal;

public class PlusWorkJobCenterServer {
	public static void main(String[] args) {
		try {
			JobCenter jobCenter = new JobCenterLocal(TestUtil.SIP, TestUtil.SP);
			jobCenter.getRmiServer();
			regForman(jobCenter);
			regWoker(jobCenter, 5);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void runThread() {
		MyThread t = new PlusWorkJobCenterServer.MyThread();
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

	private static List<Worker> regWoker(JobCenter jobCenter, int length)
			throws RemoteException {
		List<Worker> list = new ArrayList<Worker>();
		for (int i = 0; i < length; i++) {
			Worker worker = new PlusWorker();
			jobCenter.registerWorker(worker);
			list.add(worker);
		}
		return list;
	}

	private static Foreman regForman(JobCenter jobCenter)
			throws RemoteException {
		Foreman f = new ForemanSelectOneWorker(PlusWork.TYPE);
		jobCenter.registerForeman(f);
		return f;
	}
}
