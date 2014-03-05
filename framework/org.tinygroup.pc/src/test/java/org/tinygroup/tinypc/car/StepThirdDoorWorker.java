package org.tinygroup.tinypc.car;

import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.Work;

import java.rmi.RemoteException;

/**
 * Created by luoguo on 14-1-28.
 */
public class StepThirdDoorWorker extends StepThirdWorker {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7295028639938346454L;
	public static final String DOOR = "door";

    public StepThirdDoorWorker() throws RemoteException {
        super(DOOR);
    }


    public boolean acceptWork(Work work) {
        return acceptWork(work, DOOR);
    }


    protected Warehouse doWork(Work work) throws RemoteException {
        return super.doWork(work, DOOR);
    }
}
