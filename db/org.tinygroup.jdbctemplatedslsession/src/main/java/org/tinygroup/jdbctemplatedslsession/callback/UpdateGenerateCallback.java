package org.tinygroup.jdbctemplatedslsession.callback;

import org.tinygroup.tinysqldsl.Update;

/**
 * 生成Dsl Update对象
 * @author renhui
 *
 * @param <T>
 */
public interface UpdateGenerateCallback<T> {

	/**
	 * 根据参数对象生成Update对象
	 * @param t
	 * @return
	 */
	Update generate(T t);
	
}
