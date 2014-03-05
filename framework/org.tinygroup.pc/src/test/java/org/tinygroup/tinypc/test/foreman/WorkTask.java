package org.tinygroup.tinypc.test.foreman;

import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.Work;
import org.tinygroup.tinypc.WorkStatus;

public class WorkTask implements Work {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9073481341113299557L;
	private String type;
	private String id;
	private Work nextWork;
	private boolean needSerialize;
	private Warehouse w;
	private WorkStatus workStatus;
	private String foremanType;
	public WorkTask(String type, String id,String foremanType) {
		this.type = type;
		this.id = id;
		this.foremanType = foremanType;
	}

	public String getType() {
		return type;
	}

	public String getId() {
		return id;
	}

	public Work getNextWork() {
		return nextWork;
	}

	public Work setNextWork(Work nextWork) {
		this.nextWork = nextWork;
		return this.nextWork;
	}

	public boolean isNeedSerialize() {
		return needSerialize;
	}

	public void setNeedSerialize(boolean needSerialize) {
		this.needSerialize = needSerialize;
	}

	public Warehouse getInputWarehouse() {
		return w;
	}

	public void setInputWarehouse(Warehouse inputWarehouse) {
		this.w = inputWarehouse;
	}

	public void setWorkStatus(WorkStatus workStatus) {
		this.workStatus = workStatus;
	}

	public WorkStatus getWorkStatus() {
		return workStatus;
	}

	public String getForemanType() {
		return foremanType;
	}

}
