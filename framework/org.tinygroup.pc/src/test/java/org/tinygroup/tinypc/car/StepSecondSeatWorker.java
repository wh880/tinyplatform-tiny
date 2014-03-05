package org.tinygroup.tinypc.car;

import java.rmi.RemoteException;

import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.Work;

/**
 * Created by luoguo on 14-1-28.
 */
public class StepSecondSeatWorker extends StepSecondWorker {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6557087444570280543L;
	public static final String SEAT = "seat";

    public StepSecondSeatWorker() throws RemoteException {
        super(SEAT);
    }


    public boolean acceptWork(Work work) {
       return acceptWork(work,SEAT);
    }


    protected Warehouse doWork(Work work) throws RemoteException {
        return super.doWork(work, SEAT);
    }
}
