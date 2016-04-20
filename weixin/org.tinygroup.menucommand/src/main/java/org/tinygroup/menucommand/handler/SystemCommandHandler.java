package org.tinygroup.menucommand.handler;

import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.context.Context;
import org.tinygroup.menucommand.CommandHandler;
import org.tinygroup.menucommand.MenuCommandConstants;
import org.tinygroup.menucommand.config.MenuCommand;
import org.tinygroup.menucommand.config.MenuConfig;
import org.tinygroup.menucommand.config.SystemCommand;

/**
 * 抽象的系统命令的钩子
 * @author yancheng11334
 *
 */
public abstract class SystemCommandHandler implements CommandHandler{

	public void beforeExecute(Context context) {
		MenuConfig config = context.get(MenuCommandConstants.MENU_CONFIG_PAGE_NAME);
		SystemCommand systemCommand = context.get(MenuCommandConstants.SYSTEM_COMMAND_NAME);
		String command = context.get(MenuCommandConstants.USER_INPUT_COMMAND_NAME);
		execute(command,systemCommand,config,context);
	}

	public void afterExecute(Context context) {
		context.remove(MenuCommandConstants.MENU_CONFIG_PAGE_NAME);
		context.remove(MenuCommandConstants.GOTO_MENU_ID_NAME);
		context.remove(MenuCommandConstants.RENDER_PAGE_PATH_NAME);
		context.remove(MenuCommandConstants.SYSTEM_COMMAND_NAME);
		context.remove(MenuCommandConstants.USER_INPUT_COMMAND_NAME);
	}
	
	protected String getRenderPath(SystemCommand systemCommand,Context context){
		return StringUtil.isEmpty(systemCommand.getPath())?MenuCommandConstants.getRenderPath(context):systemCommand.getPath();
	}
	
	/**
	 * 执行退出操作
	 * @param config
	 * @param context
	 */
	protected void dealExitEvent(String command,MenuConfig config,Context context){
		MenuCommand menuCommand = config.matchEvent(MenuCommandConstants.EXIT_EVENT_TYPE);
		if(menuCommand!=null){
			MenuCommandHandler handler = menuCommand.createCommandObject();
			if(handler!=null){
				 //执行操作
				handler.execute(command, menuCommand, context);
			}
		   
		}
		
	}
	
	/**
	 * 执行相关菜单逻辑，保存渲染路径和跳转菜单id
	 * @param command
	 * @param systemCommand
	 * @param config
	 * @param context
	 */
	protected abstract void execute(String command,SystemCommand systemCommand,MenuConfig config,Context context);

}
