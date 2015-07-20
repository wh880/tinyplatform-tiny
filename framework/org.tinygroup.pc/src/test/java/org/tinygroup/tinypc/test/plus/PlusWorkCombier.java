package org.tinygroup.tinypc.test.plus;

import java.rmi.RemoteException;
import java.util.List;

import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.WorkCombiner;
import org.tinygroup.tinypc.impl.WarehouseDefault;

public class PlusWorkCombier implements WorkCombiner {

	public Warehouse combine(List<Warehouse> warehouseList)
			throws RemoteException {
		Warehouse w = new WarehouseDefault();
		int total = 0;
		for (Warehouse wsub : warehouseList) {
			int sub = wsub.get(PlusWork.RESULT);
			total += sub;
		}
		w.put(PlusWork.RESULT, total);
		return w;
	}
}
