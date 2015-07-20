package org.tinygroup.tinypc.test.plus;

import java.rmi.RemoteException;

import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.impl.WorkDefault;

public class PlusWork extends WorkDefault {
	//加法参数在上下文中的key
	public static final String PARAM = "param";
	//加法结果在上下文中的key
	public static final String RESULT = "result";
	//任务类型
	public static final String TYPE = "plus";

	public PlusWork(String type, String id, Warehouse inputWarehouse)
			throws RemoteException {
		super(type, id, inputWarehouse);
	}

}
