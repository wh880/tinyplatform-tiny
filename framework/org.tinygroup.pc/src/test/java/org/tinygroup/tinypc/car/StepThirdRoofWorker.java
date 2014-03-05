package org.tinygroup.tinypc.car;

import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.Work;

import java.rmi.RemoteException;

/**
 * Created by luoguo on 14-1-28.
 */
public class StepThirdRoofWorker extends StepThirdWorker {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8977627369610986658L;
	public static final String ROOF = "roof";

    public StepThirdRoofWorker() throws RemoteException {
        super(ROOF);
    }


    public boolean acceptWork(Work work) {
        return acceptWork(work, ROOF);
    }


    protected Warehouse doWork(Work work) throws RemoteException {
        return super.doWork(work, ROOF);
    }
}
