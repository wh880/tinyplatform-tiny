package org.tinygroup.cepcorepc.test;

import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.Work;
import org.tinygroup.tinypc.WorkStatus;

public class ScTestWork implements Work {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2351110050683109862L;

	Warehouse w;

	String worker;

	boolean regService = true;

	public boolean isRegService() {
		return regService;
	}

	public void setRegService(boolean regService) {
		this.regService = regService;
	}

	public String getWorker() {
		return worker;
	}

	public void setWorker(String worker) {
		this.worker = worker;
	}

	public String getType() {
		return "sc";
	}

	public String getForemanType() {
		return "sc";
	}

	public String getId() {
		// TODO Auto-generated method stub
		return "CepcoreWork";
	}

	public Work getNextWork() {
		// TODO Auto-generated method stub
		return null;
	}

	public Work setNextWork(Work nextWork) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isNeedSerialize() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setNeedSerialize(boolean needSerialize) {
		// TODO Auto-generated method stub

	}

	public Warehouse getInputWarehouse() {
		return w;
	}

	public void setInputWarehouse(Warehouse inputWarehouse) {
		w = inputWarehouse;
	}

	public void setWorkStatus(WorkStatus workStatus) {
		// TODO Auto-generated method stub

	}

	public WorkStatus getWorkStatus() {
		// TODO Auto-generated method stub
		return null;
	}

}
