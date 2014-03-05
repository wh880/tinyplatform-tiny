package org.tinygroup.tinypc.car;

import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.Work;

import java.rmi.RemoteException;

/**
 * Created by luoguo on 14-1-28.
 */
public class StepSecondTyreWorker extends StepSecondWorker {
    /**
	 * 
	 */
	private static final long serialVersionUID = -858025873687064762L;
	public static final String TYRE = "tyre";

    public StepSecondTyreWorker() throws RemoteException {
        super(TYRE);
    }


    public boolean acceptWork(Work work) {
        return acceptWork(work, TYRE);
    }


    protected Warehouse doWork(Work work) throws RemoteException {
        return super.doWork(work, TYRE);
    }
}
