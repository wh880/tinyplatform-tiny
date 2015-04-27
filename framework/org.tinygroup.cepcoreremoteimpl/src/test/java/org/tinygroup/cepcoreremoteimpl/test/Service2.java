package org.tinygroup.cepcoreremoteimpl.test;

public class Service2 {
	public static volatile Integer i = 0;
	public void add(){
		synchronized (i) {
			i++;
		}
	}
}
