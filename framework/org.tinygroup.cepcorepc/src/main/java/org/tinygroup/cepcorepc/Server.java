package org.tinygroup.cepcorepc;

import org.tinygroup.tinypc.Foreman;
import org.tinygroup.tinypc.JobCenter;
import org.tinygroup.tinypc.impl.ForemanSelectAllWorker;
import org.tinygroup.tinypc.impl.JobCenterLocal;

public class Server {
	public static void main(String[] args) {
        try {
            JobCenter jobCenter = new JobCenterLocal("192.168.84.23", 8888);
            jobCenter.registerWorker(new CepcoreWorker("1"));
            Foreman cepForeman = new ForemanSelectAllWorker("reg",
                    null);
            jobCenter.registerForeman(cepForeman);
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
