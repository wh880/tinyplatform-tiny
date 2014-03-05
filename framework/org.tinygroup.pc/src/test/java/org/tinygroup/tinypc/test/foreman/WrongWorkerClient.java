package org.tinygroup.tinypc.test.foreman;

import org.tinygroup.tinypc.JobCenter;
import org.tinygroup.tinypc.impl.JobCenterRemote;

public class WrongWorkerClient {
	private static String SERVERIP = "192.168.84.52";

	public static void main(String[] args) {
		JobCenter jobCenter;
		try {
			jobCenter = new JobCenterRemote(SERVERIP, 8888);
			jobCenter.registerWorker(new WrongWorker("a"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		WrongWorkerClient c = new WrongWorkerClient();
		c.run();

	}


	
	public void run(){
		MyThread t = new MyThread();
		t.run();
	}

	class MyThread extends Thread {
		boolean end = false;
		public void run() {
			while(!end){
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
