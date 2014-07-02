package org.tinygroup.cepcorepcsc.test;

import org.tinygroup.tinypc.Foreman;
import org.tinygroup.tinypc.JobCenter;
import org.tinygroup.tinypc.impl.ForemanSelectAllWorker;
import org.tinygroup.tinypc.impl.ForemanSelectOneWorker;
import org.tinygroup.tinypc.impl.JobCenterLocal;

public class Server {
	public static JobCenter jobCenter = null;
	public static void main(String[] args) {
		try {
			jobCenter = new JobCenterLocal(AddressUtil.SIP,
					AddressUtil.SP);
			jobCenter.registerWorker(new ScTestWorker("1"));
			Foreman cepForeman = new ForemanSelectOneWorker("sc");
			Foreman arForeman = new ForemanSelectAllWorker("ar");
			jobCenter.registerForeman(cepForeman);
			jobCenter.registerForeman(arForeman);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Server r = new Server();
		r.runThread();
	}

	public void runThread() {
		MyThread t = new Server.MyThread();
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
