package org.tinygroup.cepcorepcsc.test;

import java.util.HashMap;
import java.util.Map;

import org.tinygroup.event.ServiceInfo;
import org.tinygroup.tinypc.JobCenter;
import org.tinygroup.tinypc.impl.JobCenterRemote;
import org.tinygroup.tinypc.impl.WarehouseDefault;

public class Client {

	public static void main(String[] args) {
				try {
			JobCenter jobCenter = new JobCenterRemote(AddressUtil.CIP,AddressUtil.CP,AddressUtil.SIP,AddressUtil.SP);
			ArTestWorker worker = new ArTestWorker("a"); 
			jobCenter.registerWorker(worker);
			ScTestWork w = new ScTestWork();
			w.setWorker("a");
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
