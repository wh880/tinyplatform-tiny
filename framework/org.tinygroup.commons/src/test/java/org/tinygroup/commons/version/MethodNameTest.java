package org.tinygroup.commons.version;

import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.tinygroup.commons.beanutil.BeanUtil;
import org.tinygroup.commons.tools.ReflectionUtils;

public class MethodNameTest extends TestCase {

	public void testMethodName() {
		String[] parameterNames = BeanUtil
				.getMethodParameterName(ReflectionUtils.findMethod(
						TestObject.class, "method1", String.class, int.class));
		assertEquals("name1", parameterNames[0]);
		assertEquals("name2", parameterNames[1]);

		parameterNames = BeanUtil.getMethodParameterName(ReflectionUtils
				.findMethod(TestObject.class, "method2", Class.class));
		assertEquals("name3", parameterNames[0]);

		parameterNames = BeanUtil.getMethodParameterName(ReflectionUtils
				.findMethod(TestObject.class, "method3", Class[].class));
		assertEquals("name4", parameterNames[0]);

		parameterNames = BeanUtil.getMethodParameterName(ReflectionUtils
				.findMethod(TestObject.class, "method4", List.class));
		assertEquals("name5", parameterNames[0]);

		parameterNames = BeanUtil.getMethodParameterName(ReflectionUtils
				.findMethod(TestObject.class, "method5", Map.class));
		assertEquals("name6", parameterNames[0]);

	}

}
