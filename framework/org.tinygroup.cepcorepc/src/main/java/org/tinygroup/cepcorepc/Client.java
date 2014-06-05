package org.tinygroup.cepcorepc;

import java.util.HashMap;
import java.util.Map;

import org.tinygroup.event.ServiceInfo;
import org.tinygroup.tinypc.JobCenter;
import org.tinygroup.tinypc.Work;
import org.tinygroup.tinypc.impl.JobCenterRemote;
import org.tinygroup.tinypc.impl.WarehouseDefault;

public class Client {
	private static String SERVERIP = "192.168.84.23";

	public static void main(String[] args) {
				try {
			JobCenter jobCenter = new JobCenterRemote(SERVERIP, 8888);
			jobCenter.registerWorker(new CepcoreWorker("a"));
			Work w = new CepcoreWork();
			Map<String,ServiceInfo> services = new HashMap<String, ServiceInfo>();
			services.put("a", null);
			services.put("a1", null);
			services.put("a2", null);
			services.put("a3", null);
			services.put("a4", null);
			w.setInputWarehouse(new WarehouseDefault());
			System.out.println(w.getInputWarehouse());
			w.getInputWarehouse().put("services", services);
			jobCenter.doWork(w);
			Client c = new Client();
			c.run();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		MyThread t = new MyThread();
		t.run();
	}

	class MyThread extends Thread {
		boolean end = false;

		public void run() {
			while (!end) {

				try {
					sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}
}
