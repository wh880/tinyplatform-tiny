package org.tinygroup.menucommand.handler;

import org.tinygroup.context.Context;
import org.tinygroup.menucommand.MenuCommandConstants;
import org.tinygroup.menucommand.config.MenuConfig;
import org.tinygroup.menucommand.config.SystemCommand;

/**
 * 退出系统命令
 * @author yancheng11334
 *
 */
public class ExitCommandHandler extends SystemCommandHandler{

	protected void execute(String command,SystemCommand systemCommand,MenuConfig config, Context context) {
		dealExitEvent(command,config, context);
		context.put(MenuCommandConstants.RENDER_PAGE_PATH_NAME, getRenderPath(systemCommand, context));
		context.put(MenuCommandConstants.GOTO_MENU_ID_NAME, null);
	}

}
