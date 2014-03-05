package org.tinygroup.tinypc.car;

import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.Work;
import org.tinygroup.tinypc.impl.AbstractWorker;

import java.rmi.RemoteException;

/**
 * Created by luoguo on 14-1-28.
 */
public class StepFirstWorker extends AbstractWorker {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3631711170608988710L;

	public StepFirstWorker() throws RemoteException {
        super("first");
    }


    protected Warehouse doWork(Work work) throws RemoteException {
        System.out.println(String.format("%s 构建底盘完成.", work.getInputWarehouse().get("carType")));
        Warehouse outputWarehouse = work.getInputWarehouse();
        outputWarehouse.put("baseInfo", "something about baseInfo");
        return outputWarehouse;
    }
}
