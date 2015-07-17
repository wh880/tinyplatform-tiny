package org.tinygroup.tinypc.test.plus;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.tinygroup.tinypc.Foreman;
import org.tinygroup.tinypc.JobCenter;
import org.tinygroup.tinypc.TestUtil;
import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.Work;
import org.tinygroup.tinypc.Worker;
import org.tinygroup.tinypc.impl.ForemanSelectOneWorker;
import org.tinygroup.tinypc.impl.JobCenterRemote;
import org.tinygroup.tinypc.impl.WarehouseDefault;

public class PlusWorkTestOneSelectCase {
	public static void main(String[] args) {
		testAllSelectCase();
	}

	public static void testAllSelectCase() {
		try {
			JobCenter jobCenter = new JobCenterRemote(TestUtil.CIP,
					TestUtil.CP, TestUtil.SIP, TestUtil.SP);
			Work work = createWork();
			Foreman f = regForman(jobCenter);
			List<Worker> list = regWoker(jobCenter, 5);
			Warehouse resultWarehouse = jobCenter.doWork(work);
			jobCenter.unregisterForeMan(f);
			unregWorker(jobCenter, list);
			int result = resultWarehouse.get(PlusWork.RESULT);
			System.out.println("result:" + result);
			System.out.println("if not 55 ,failure");

		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	private static void unregWorker(JobCenter jobCenter,List<Worker> list){
		for(Worker worker:list){
			try {
				jobCenter.unregisterWorker(worker);
			} catch (RemoteException e) {
				e.printStackTrace();
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

	private static Work createWork() throws RemoteException {
		Warehouse inputWarehouse = new WarehouseDefault();
		int[] plusParams = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		inputWarehouse.put(PlusWork.PARAM, plusParams);
		Work work = new PlusWork(PlusWork.TYPE, "work1", inputWarehouse);
		return work;
	}
}
