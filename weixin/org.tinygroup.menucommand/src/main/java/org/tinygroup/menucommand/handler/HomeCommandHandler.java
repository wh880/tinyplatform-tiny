package org.tinygroup.menucommand.handler;

import org.tinygroup.context.Context;
import org.tinygroup.menucommand.MenuCommandConstants;
import org.tinygroup.menucommand.config.MenuConfig;
import org.tinygroup.menucommand.config.SystemCommand;

/**
 * 首页系统命令
 * @author yancheng11334
 *
 */
public class HomeCommandHandler extends SystemCommandHandler{

	protected void execute(String command,SystemCommand systemCommand,MenuConfig config, Context context) {
		dealExitEvent(command,config, context);
		//返回首页
		while(config.getParent()!=null){
		   config = config.getParent();
		}
		context.put(MenuCommandConstants.RENDER_PAGE_PATH_NAME, getRenderPath(systemCommand, context));
		context.put(MenuCommandConstants.GOTO_MENU_ID_NAME, config.getId());
		context.put(MenuCommandConstants.MENU_CONFIG_PAGE_NAME, config);
	}

}
