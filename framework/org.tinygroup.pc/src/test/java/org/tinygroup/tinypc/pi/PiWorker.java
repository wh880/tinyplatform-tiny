package org.tinygroup.tinypc.pi;

import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.Work;
import org.tinygroup.tinypc.impl.AbstractWorker;

import java.rmi.RemoteException;

/**
 * Created by luoguo on 14-1-26.
 */
public class PiWorker extends AbstractWorker {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1455631976934664413L;

	public PiWorker() throws RemoteException {
        super("pi");
    }


    protected Warehouse doWork(Work work) throws RemoteException {
        long m = (Long) work.getInputWarehouse().get("start");
        long n = (Long) work.getInputWarehouse().get("end");
        double pi = 0.0d;
        for (double i = m; i < n; i++) {
            pi += Math.pow(-1, i + 1) / (2 * i - 1);
        }
        work.getInputWarehouse().put("pi", 4 * pi);
        return work.getInputWarehouse();
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        double pi = 0.0;
        for (double i = 1.0; i < 1000000001d; i++) {
            pi += Math.pow(-1, i + 1) / (2 * i - 1);
        }
        System.out.println(4 * pi);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
