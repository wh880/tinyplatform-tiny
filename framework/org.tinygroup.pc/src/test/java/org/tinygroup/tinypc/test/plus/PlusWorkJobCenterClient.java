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
import org.tinygroup.tinypc.impl.JobCenterLocal;
import org.tinygroup.tinypc.impl.JobCenterRemote;
import org.tinygroup.tinypc.impl.WarehouseDefault;

public class PlusWorkJobCenterClient {
	public static void main(String[] args) {
		try {
			JobCenter jobCenter = new JobCenterRemote(TestUtil.CIP,
					TestUtil.CP, TestUtil.SIP, TestUtil.SP);
			Work work = createWork();
			Warehouse resultWarehouse = jobCenter.doWork(work);
			Integer result = resultWarehouse.get(PlusWork.RESULT);
			System.out.println("result is "+result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private static Work createWork() throws RemoteException {
		Warehouse inputWarehouse = new WarehouseDefault();
		int[] plusParams = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		inputWarehouse.put(PlusWork.PARAM, plusParams);
		Work work = new PlusWork(PlusWork.TYPE, "work1", inputWarehouse);
		return work;
	}
}
