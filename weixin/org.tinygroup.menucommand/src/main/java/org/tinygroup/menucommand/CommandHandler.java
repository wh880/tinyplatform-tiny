package org.tinygroup.menucommand;

import org.tinygroup.context.Context;

/**
 * 命令处理句柄
 * @author yancheng11334
 *
 */
public interface CommandHandler {

	/**
	 * 命令前置操作
	 * @param context
	 */
	void beforeExecute(Context context);
	
	
	/**
	 * 命令后置操作
	 * @param context
	 */
	void afterExecute(Context context);
}
