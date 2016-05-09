package org.tinygroup.flow.test.newtestcase.params.component;

import org.tinygroup.context.Context;
import org.tinygroup.flow.ComponentInterface;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

public class FlowParamStringComponent implements ComponentInterface {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FlowParamStringComponent.class);
	
	private String str;
	
	public void execute(Context context) {
		LOGGER.logMessage(LogLevel.DEBUG, "流程参数String类型传递测试");
		context.put("result", str);
		LOGGER.logMessage(LogLevel.DEBUG, "流程参数String类型传递测试");
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

}
