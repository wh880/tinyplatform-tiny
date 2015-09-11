package org.tinygroup.cepcoregovernance.test;

public class RemoteService {
	public void remoteService() {
		try {
			Thread.currentThread().sleep(111);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
