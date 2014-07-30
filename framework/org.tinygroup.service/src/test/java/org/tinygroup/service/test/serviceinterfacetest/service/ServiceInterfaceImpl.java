package org.tinygroup.service.test.serviceinterfacetest.service;

import org.tinygroup.context.Context;
import org.tinygroup.context.util.ContextFactory;
import org.tinygroup.service.ServiceInterface;

public class ServiceInterfaceImpl implements ServiceInterface {

	public String getServiceId() {
		return "testService";
	}

	public String getAlias() {
		return "testAliasService";
	}

	public String getCategory() {
		return "";
	}

	public String getResultKey() {
		return "result";
	}

	public Context execute(Context context) {
		Context newContext=ContextFactory.getContext();
		newContext.putSubContext("sub", context);
		return newContext;
	}

}
