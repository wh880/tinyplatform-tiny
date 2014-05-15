package org.tinygroup.rmi;

import java.rmi.Remote;

public class RemoteObjectDescription {
	String name;
	Remote object;
	public RemoteObjectDescription(Remote object, String name){
		this.object = object;
		this.name = name;
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Remote getObject() {
		return object;
	}
	public void setObject(Remote object) {
		this.object = object;
	}
	
}
