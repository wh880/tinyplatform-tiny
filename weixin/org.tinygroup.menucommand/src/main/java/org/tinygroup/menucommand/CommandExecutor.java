package org.tinygroup.menucommand;

import org.tinygroup.context.Context;


/**
 * 命令执行器
 * @author yancheng11334
 *
 */
public interface CommandExecutor {
	
	/**
	 * 执行渲染，并返回处理结果
	 * @param context
	 * @return
	 */
	CommandResult execute(Context context);
	
}
