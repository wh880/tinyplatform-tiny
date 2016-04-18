package org.tinygroup.menucommand.handler;

import org.tinygroup.context.Context;
import org.tinygroup.menucommand.MenuCommandConstants;
import org.tinygroup.menucommand.config.MenuConfig;
import org.tinygroup.menucommand.config.SystemCommand;

/**
 * 查询系统命令
 * @author yancheng11334
 *
 */
public class QueryCommandHandler extends SystemCommandHandler{

	protected void execute(String command,SystemCommand systemCommand,MenuConfig config, Context context) {
		//按空格进行切分
		String[] keys = command.split("\\s+");
		if(keys!=null && keys.length>=2){
		   context.put(MenuCommandConstants.USER_QUERY_KEY_NAME, keys[1]);
		}
		context.put(MenuCommandConstants.RENDER_PAGE_PATH_NAME, systemCommand.getPath());
		context.put(MenuCommandConstants.GOTO_MENU_ID_NAME, config.getId());
	}
	
	public void afterExecute(Context context) {
		super.afterExecute(context);
		context.remove(MenuCommandConstants.USER_QUERY_KEY_NAME);
	}

}
