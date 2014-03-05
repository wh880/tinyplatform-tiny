package org.tinygroup.tinypc.test;

public class MyWorker {
	
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public MyWorker(String type){
		this.type = type;
	}
	
	public void deal(MyWork work){
		System.out.println(work.getType());
	}
}
