package org.tinygroup.flow.test.newtestcase.exception.component;

import org.tinygroup.context.Context;
import org.tinygroup.flow.ComponentInterface;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

public class FlowPropertyComponent implements ComponentInterface{
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FlowPropertyComponent.class);
	
	private String el;
	private String str;

	public void execute(Context context) {
		LOGGER.logMessage(LogLevel.DEBUG, "流程属性校验测试");
		context.put("el", el);
		context.put("str", str);
		LOGGER.logMessage(LogLevel.DEBUG, "流程属性校验测试");
	}

	public String getEl() {
		return el;
	}

	public void setEl(String el) {
		this.el = el;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

}
