package org.tinygroup.flow.test;

import org.tinygroup.context.Context;
import org.tinygroup.flow.ComponentInterface;

public class SumComponent implements ComponentInterface {
	private int a;
	private int b;
	private String resultKey = "sum";

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public void execute(Context context) {
		context.put(resultKey, a+b);
	}

}
