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
	private String str;
	
	public void execute(Context context) {
		LOGGER.logMessage(LogLevel.DEBUG, "流程参数传递测试");
//		System.out.println("参数el的值为：      " + el);
//		System.out.println("参数str的值为：      " + str);
		FlowElUtil.execute(el, context, this.getClass().getClassLoader());
		context.put("str", str);
		context.put("el", el);
		LOGGER.logMessage(LogLevel.DEBUG, "流程参数传递测试");
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
