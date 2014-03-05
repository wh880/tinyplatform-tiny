package org.tinygroup.tinypc.test;

import java.util.ArrayList;
import java.util.List;

public class MyWork {
	private String type;
	private List<MyWork> subWorks = new ArrayList<MyWork>();
	private MyWork nextWork;

	public MyWork(String type) {
		this.type = type;
	}

	public MyWork getNextWork() {
		return nextWork;
	}

	public void setNextWork(MyWork nextWork) {
		this.nextWork = nextWork;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<MyWork> getSubWorks() {
		return subWorks;
	}

	public void setSubWorks(List<MyWork> subWorks) {
		this.subWorks = subWorks;
	}

}
