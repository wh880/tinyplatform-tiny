package org.tinygroup.tinypc.test.foreman;

import java.io.IOException;

import org.tinygroup.tinypc.Foreman;
import org.tinygroup.tinypc.JobCenter;
import org.tinygroup.tinypc.Work;
import org.tinygroup.tinypc.impl.ForemanSelectAllWorker;
import org.tinygroup.tinypc.impl.JobCenterRemote;

public class TestSelectAll {
	private static String SERVERIP = "192.168.84.52";
	public static void main(String[] args) {
		try {
			JobCenter jobCenter = new JobCenterRemote(SERVERIP,8888);
			Work work  = new WorkTask("a","aaa","");
			Foreman f = new ForemanSelectAllWorker("a");
			jobCenter.registerForeman(f);
			jobCenter.doWork(work);
			jobCenter.unregisterForeMan(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
