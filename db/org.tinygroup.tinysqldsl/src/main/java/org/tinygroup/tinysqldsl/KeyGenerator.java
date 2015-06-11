package org.tinygroup.tinysqldsl;

import org.tinygroup.tinysqldsl.base.InsertContext;

/**
 * 主键生成接口
 * @author renhui
 *
 */
public interface KeyGenerator {

	/**
	 * 根据插入上下文生成主键值
	 * @param <T>
	 * @param insertContext
	 * @return
	 */
	<T> T generate(InsertContext insertContext);
}
