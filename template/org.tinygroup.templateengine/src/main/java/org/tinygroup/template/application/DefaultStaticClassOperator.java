package org.tinygroup.template.application;

import org.apache.commons.beanutils.MethodUtils;

public class DefaultStaticClassOperator extends AbstractStaticClassOperator{

	/**
	 * 注册类(兼容模式)
	 * @param clazz
	 */
	public DefaultStaticClassOperator(String name,Class<?> clazz) {
		super(name,clazz);
	}

	public Object invokeStaticMethod(String methodName, Object[] args)
			throws Exception {
		return MethodUtils.invokeStaticMethod(getStaticClass(), methodName, args);
	}


}
