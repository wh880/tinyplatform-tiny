package org.tinygroup.template;

/**
 * 静态类执行器
 * @author yancheng11334
 *
 */
public interface StaticClassOperator {

	/**
	 * 获得静态类别名
	 * @return
	 */
	String getName();
	
	/**
	 * 获得静态类
	 * @return
	 */
	Class<?> getStaticClass();
	
	/**
	 * 执行静态方法
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	Object invokeStaticMethod(String methodName,Object[] args) throws Exception;
}
