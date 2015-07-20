package org.tinygroup.tinypc.test.plus;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.tinygroup.tinypc.Foreman;
import org.tinygroup.tinypc.JobCenter;
import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.Work;
import org.tinygroup.tinypc.Worker;
import org.tinygroup.tinypc.impl.ForemanSelectOneWorker;
import org.tinygroup.tinypc.impl.JobCenterLocal;
import org.tinygroup.tinypc.impl.WarehouseDefault;

public class PlusWorkTestOneSelectCase extends TestCase {

	public void testAllSelectCase() {
		try {
			JobCenter jobCenter = new JobCenterLocal();
			Work work = createWork();
			Foreman f = regForman(jobCenter);
			List<Worker> list = regWoker(jobCenter, 5);
			Warehouse resultWarehouse = jobCenter.doWork(work);
			jobCenter.unregisterForeMan(f);
			unregWorker(jobCenter, list);
			int result = resultWarehouse.get(PlusWork.RESULT);
			assertEquals(55, result);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void unregWorker(JobCenter jobCenter, List<Worker> list) {
		for (Worker worker : list) {
			try {
				jobCenter.unregisterWorker(worker);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	private List<Worker> regWoker(JobCenter jobCenter, int length)
			throws RemoteException {
		List<Worker> list = new ArrayList<Worker>();
		for (int i = 0; i < length; i++) {
			Worker worker = new PlusWorker();
			jobCenter.registerWorker(worker);
			list.add(worker);
		}
		return list;
	}

	private Foreman regForman(JobCenter jobCenter) throws RemoteException {
		Foreman f = new ForemanSelectOneWorker(PlusWork.TYPE);
		jobCenter.registerForeman(f);
		return f;
	}

	private Work createWork() throws RemoteException {
		Warehouse inputWarehouse = new WarehouseDefault();
		int[] plusParams = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		inputWarehouse.put(PlusWork.PARAM, plusParams);
		Work work = new PlusWork(PlusWork.TYPE, "work1", inputWarehouse);
		return work;
	}
}
