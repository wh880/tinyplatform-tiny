package org.tinygroup.tinypc.test.plus;

import java.rmi.RemoteException;

import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.Work;
import org.tinygroup.tinypc.impl.AbstractWorker;

public class PlusWorker extends AbstractWorker {

	public PlusWorker() throws RemoteException {
		super(PlusWork.TYPE);
	}

	@Override
	protected Warehouse doWork(Work work) throws RemoteException {
		Warehouse warehouse = work.getInputWarehouse();
		int[] param = warehouse.get(PlusWork.PARAM);
		int result = 0;
		if (param == null || param.length < 0) {
			warehouse.put(PlusWork.RESULT, result);
			return warehouse;
		}
		for (int index = 0; index < param.length; index++) {
			result += param[index];
		}
		warehouse.put(PlusWork.RESULT, result);
		return warehouse;
	}

}
