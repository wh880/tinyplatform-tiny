package org.tinygroup.flow.test;

import org.tinygroup.context.Context;
import org.tinygroup.flow.ComponentInterface;

public class AbcPlusComponent implements ComponentInterface {
	private int aa;
	private int bb;

	public int getAa() {
		return aa;
	}

	public void setAa(int aa) {
		this.aa = aa;
	}

	public int getBb() {
		return bb;
	}

	public void setBb(int bb) {
		this.bb = bb;
	}

	public void execute(Context context) {
		context.put("c", aa + bb);
	}
}
