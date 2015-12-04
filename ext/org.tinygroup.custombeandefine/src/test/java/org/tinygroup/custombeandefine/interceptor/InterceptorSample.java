package org.tinygroup.custombeandefine.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.tinygroup.custombeandefine.VariableHolder;

public class InterceptorSample implements MethodInterceptor {

	public Object invoke(MethodInvocation invocation) throws Throwable {
		VariableHolder.getInstance().setVariable("invoke method");		
		return null;
	}

}
