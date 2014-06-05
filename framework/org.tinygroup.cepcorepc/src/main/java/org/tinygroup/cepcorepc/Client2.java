package org.tinygroup.cepcorepc;

import java.util.HashMap;
import java.util.Map;

import org.tinygroup.event.ServiceInfo;
import org.tinygroup.tinypc.JobCenter;
import org.tinygroup.tinypc.Work;
import org.tinygroup.tinypc.impl.JobCenterRemote;
import org.tinygroup.tinypc.impl.WarehouseDefault;

public class Client2 {
	private static String SERVERIP = "192.168.84.23";

	public static void main(String[] args) {
				try {
			JobCenter jobCenter = new JobCenterRemote(SERVERIP, 8888);
			jobCenter.registerWorker(new CepcoreWorker("b"));
			Work w = new CepcoreWork();
			Map<String,ServiceInfo> services = new HashMap<String, ServiceInfo>();
			services.put("b", null);
			services.put("b1", null);
			services.put("b2", null);
			services.put("b3", null);
			services.put("b4", null);
			w.setInputWarehouse(new WarehouseDefault());
			System.out.println(w.getInputWarehouse());
			w.getInputWarehouse().put("services", services);
			jobCenter.doWork(w);
			Client2 c = new Client2();
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
