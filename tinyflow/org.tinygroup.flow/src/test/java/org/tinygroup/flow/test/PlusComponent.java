package org.tinygroup.flow.test;

import org.tinygroup.context.Context;
import org.tinygroup.flow.ComponentInterface;
import org.tinygroup.flow.test.testcase.DataUtil;

public class PlusComponent implements ComponentInterface{
	int ii;
	
	public int getIi() {
		return ii;
	}



	public void setIi(int ii) {
		this.ii = ii;
	}



	public void execute(Context context) {
		DataUtil.plus(ii);
	}

}
