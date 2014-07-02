package org.tinygroup.cepcorepcsc;

import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.Work;
import org.tinygroup.tinypc.WorkStatus;

public class ScWork  implements Work {

	public static String WORK_TYPE = "sc";
	
	private static final long serialVersionUID = -2351110050683109862L;

	private Warehouse warehouse;

	private String worker;
	
	private String id;

	public String getWorker() {
		return worker;
	}

	public void setWorker(String worker) {
		this.worker = worker;
	}

	public String getType() {
		return WORK_TYPE;
	}

	public String getForemanType() {
		return WORK_TYPE;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}

	public Work getNextWork() {
		return null;
	}

	public Work setNextWork(Work nextWork) {
		return null;
	}

	public boolean isNeedSerialize() {
		return false;
	}

	public void setNeedSerialize(boolean needSerialize) {

	}

	public Warehouse getInputWarehouse() {
		return warehouse;
	}

	public void setInputWarehouse(Warehouse inputWarehouse) {
		warehouse = inputWarehouse;
	}

	public void setWorkStatus(WorkStatus workStatus) {

	}

	public WorkStatus getWorkStatus() {
		return null;
	}

}
