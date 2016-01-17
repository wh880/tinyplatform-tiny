package org.tinygroup.jdbctemplatedslsession.callback;

import org.tinygroup.tinysqldsl.Update;

/**
 * 生成Dsl Update对象
 * @author renhui
 *
 * @param <T>
 */
public interface NoParamUpdateGenerateCallback{

	/**
	 * 根据参数对象生成Update对象
	 * @return
	 */
	Update generate();
	
}
