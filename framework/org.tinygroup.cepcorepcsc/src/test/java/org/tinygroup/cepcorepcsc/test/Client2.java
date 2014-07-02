package org.tinygroup.cepcorepcsc.test;

import java.util.HashMap;
import java.util.Map;

import org.tinygroup.event.ServiceInfo;
import org.tinygroup.tinypc.JobCenter;
import org.tinygroup.tinypc.impl.JobCenterRemote;
import org.tinygroup.tinypc.impl.WarehouseDefault;

public class Client2 {
	

	public static void main(String[] args) {
				try {
			JobCenter jobCenter = new JobCenterRemote(AddressUtil.CIP,AddressUtil.CP,AddressUtil.SIP,AddressUtil.SP);
			ArTestWorker worker = new ArTestWorker("b"); 
			jobCenter.registerWorker(worker);
			ScTestWork w = new ScTestWork();
			w.setWorker("b");
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
