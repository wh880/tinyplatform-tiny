package org.tinygroup.flow.test.newtestcase.params.component;

import org.tinygroup.context.Context;
import org.tinygroup.flow.ComponentInterface;
import org.tinygroup.flow.util.FlowElUtil;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

public class FlowParamElComponent implements ComponentInterface {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FlowParamElComponent.class);
	private String el;
	
	public void execute(Context context) {
		LOGGER.logMessage(LogLevel.DEBUG, "流程参数el表达式传递测试");
		FlowElUtil.execute(el, context, this.getClass().getClassLoader());
		LOGGER.logMessage(LogLevel.DEBUG, "流程参数表达式传递测试");
	}

	public String getEl() {
		return el;
	}

	public void setEl(String el) {
		this.el = el;
	}

}
