package org.tinygroup.flow.test;

import org.tinygroup.context.Context;
import org.tinygroup.flow.ComponentInterface;

public class AbcImsubComponent implements ComponentInterface {
	private int num;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void execute(Context context) {
		int c = (Integer) context.get("c");
		context.put("c", c - num);
	}
}
