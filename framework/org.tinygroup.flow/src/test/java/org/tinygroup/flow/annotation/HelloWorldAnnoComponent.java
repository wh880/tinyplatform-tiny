package org.tinygroup.flow.annotation;

import org.tinygroup.context.Context;
import org.tinygroup.flow.ComponentInterface;
import org.tinygroup.flow.annotation.config.ComponentDefine;
import org.tinygroup.flow.annotation.config.ComponentParameter;
import org.tinygroup.flow.annotation.config.ComponentResult;

@ComponentDefine(name="helloWorldAnnoComponent",bean="helloWorldAnnoComponent")
public class HelloWorldAnnoComponent implements ComponentInterface {
	@ComponentParameter(name="name",type="java.lang.String")
	String name;
	@ComponentResult(name="resultKey",type="java.lang.String")
	String resultKey;

	public String getResultKey() {
		return resultKey;
	}

	public void setResultKey(String resultKey) {
		this.resultKey = resultKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void execute(Context context) {
		context.put(resultKey, String.format("Hello, %s", name));		
	}

}
