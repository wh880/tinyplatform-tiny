package org.tinygroup.flow.test;

import org.tinygroup.context.Context;
import org.tinygroup.flow.ComponentInterface;
import org.tinygroup.flow.test.testcase.DataUtil;

public class ResetComponent implements ComponentInterface {
	public void execute(Context context) {
		DataUtil.reset();
	}
}
