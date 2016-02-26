package org.tinygroup.httpvisitor;



/**
 * HTTP具体执行接口
 * @author yancheng11334
 *
 */
public interface Executable<T> {

	/**
	 * 执行请求操作
	 * @return
	 */
	T execute();
	
}
