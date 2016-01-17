package org.tinygroup.jdbctemplatedslsession.callback;

import org.tinygroup.tinysqldsl.Insert;

/**
 * 生成Dsl insert对象
 * @author renhui
 *
 * @param <T>
 */
public interface NoParamInsertGenerateCallback {

	/**
	 * 根据参数对象生成insert对象
	 * @return
	 */
	Insert generate();
	
}
