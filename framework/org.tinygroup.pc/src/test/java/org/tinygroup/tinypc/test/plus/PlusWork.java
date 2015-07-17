package org.tinygroup.tinypc.test.plus;

import java.rmi.RemoteException;

import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.impl.WorkDefault;

public class PlusWork extends WorkDefault {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2455954240812679366L;
	public PlusWork(String type, String id, Warehouse inputWarehouse)
			throws RemoteException {
		super(type, id, inputWarehouse);
	}
	public static final String PARAM = "param";
	public static final String RESULT = "result";
	public static final String TYPE = "plus";

}
