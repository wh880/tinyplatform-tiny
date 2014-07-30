package org.tinygroup.logger;

import java.util.Map;

/**
 * 
 * @author renhui
 *
 */
public interface MDCAdaptor {

	/**
	 * 将键/对象映射放入上下文
	 * 
	 * @param key
	 *            键
	 * @param val
	 *            对象
	 */
	public void put(String key, Object val);

	/**
	 * 根据键，从上下文获得对象
	 * 
	 * @param key
	 *            键
	 * @return 对象
	 */
	public Object get(String key);

	/**
	 * 根据键移去一个对象
	 * 
	 * @param key
	 *            键
	 */
	public void remove(String key);

	/**
	 * 清空上下文
	 */
	public void clear();

	/**
	 * 得到上下文的拷贝
	 * 
	 * @return 上下文拷贝
	 */
	public Map getCopyOfContextMap();

	/**
	 * 设置Map到上下文
	 * 
	 * @param contextMap
	 */
	public void setContextMap(Map contextMap);
}
