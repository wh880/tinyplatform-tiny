package org.tinygroup.cepcorepc;

import java.util.ArrayList;

import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.Work;
import org.tinygroup.tinypc.WorkStatus;

public class CepcoreWork implements Work {

	Warehouse w;
	/**
	 * 
	 */
	private static final long serialVersionUID = 8667837999575269710L;

	

	public String getType() {
		return "reg";
	}

	public String getForemanType() {
		return "reg";
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
