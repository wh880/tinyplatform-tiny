package org.tinygroup.cepcorepcsc;

import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.Work;
import org.tinygroup.tinypc.WorkStatus;

public class ArWork  implements Work {

	public static String WORK_TYPE = "ar";

	private static final long serialVersionUID = 1412133310731128120L;

	private String id ;
	
	private Warehouse warehouse;

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
