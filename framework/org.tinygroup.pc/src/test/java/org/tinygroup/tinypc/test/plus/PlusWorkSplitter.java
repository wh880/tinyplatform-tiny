package org.tinygroup.tinypc.test.plus;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.Work;
import org.tinygroup.tinypc.WorkSplitter;
import org.tinygroup.tinypc.Worker;
import org.tinygroup.tinypc.impl.WarehouseDefault;

public class PlusWorkSplitter implements WorkSplitter {

	public List<Warehouse> split(Work work, List<Worker> workers)
			throws RemoteException {
		int[] params = work.getInputWarehouse().get(PlusWork.PARAM);
		int workerSize = workers.size();
		return deal(params, workerSize);
	}

	private List<Warehouse> deal(int[] params, int workerSize) {
		List<Warehouse> list = new ArrayList<Warehouse>();
		int length = params.length;
		int eachLength = length / workerSize;
		int moreLength = length % workerSize;
		int begin = 0;
		for (int i = 0; i < workerSize; i++) {
			Warehouse warehouse = new WarehouseDefault();
			int thisLength = eachLength;
			if (i < moreLength) {
				thisLength++;
			}
			warehouse.put(PlusWork.PARAM,
					getIntArray2(params, begin, thisLength));
			begin += thisLength;
			list.add(warehouse);
		}
		return list;
	}

	private int[] getIntArray2(int[] params, int begin, int length) {
		if (length <= 0) {
			throw new IndexOutOfBoundsException("end必须大于begin");
		}
		int[] newArray = new int[length];
		for (int i = 0; i < length; i++) {
			newArray[i] = params[begin + i];
		}
		return newArray;
	}

}
