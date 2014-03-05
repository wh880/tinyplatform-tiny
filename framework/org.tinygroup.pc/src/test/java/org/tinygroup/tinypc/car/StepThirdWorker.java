package org.tinygroup.tinypc.car;

import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.Work;
import org.tinygroup.tinypc.impl.AbstractWorker;

import java.rmi.RemoteException;

/**
 * Created by luoguo on 14-1-28.
 */
public abstract class StepThirdWorker extends AbstractWorker {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4310493148996750301L;

	public StepThirdWorker(String s) throws RemoteException {
        super(s);
    }

    protected boolean acceptWork(Work work, String type) {
//        String workClass = work.getInputWarehouse().get("class");
//        if (workClass != null && workClass.equals(type)) {
//            return true;
//        }
        return true;
    }

    protected Warehouse doWork(Work work, String type) throws RemoteException {
        System.out.println(String.format("Base:%s ", work.getInputWarehouse().get("baseInfo")));
        System.out.println(String.format("%s is Ok", type));
        return work.getInputWarehouse();
    }
}
