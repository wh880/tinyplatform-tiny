package org.tinygroup.flow.test;

import org.tinygroup.context.Context;
import org.tinygroup.flow.ComponentInterface;
import org.tinygroup.flow.test.testcase.DataUtil;

public class ImsubComponent implements ComponentInterface {
	int jj;

	public int getJj() {
		return jj;
	}

	public void setJj(int jj) {
		this.jj = jj;
	}

	public void execute(Context context) {
		DataUtil.imsub(jj);
	}

}
