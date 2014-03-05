package org.tinygroup.tinypc.car;

import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.Work;

import java.rmi.RemoteException;

/**
 * Created by luoguo on 14-1-28.
 */
public class StepSecondEngineWorker extends StepSecondWorker {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7070174122638971173L;
	public static final String ENGINE = "engine";

    public StepSecondEngineWorker() throws RemoteException {
        super(ENGINE);
    }


    public boolean acceptWork(Work work) {
        return acceptWork(work, ENGINE);
    }


    protected Warehouse doWork(Work work) throws RemoteException {
        return super.doWork(work, ENGINE);
    }
}
