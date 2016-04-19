package org.tinygroup.menucommand.handler;

import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.context.Context;
import org.tinygroup.menucommand.CommandHandler;
import org.tinygroup.menucommand.MenuCommandConstants;
import org.tinygroup.menucommand.config.MenuCommand;

/**
 * 抽象的菜单命令的钩子
 * @author yancheng11334
 *
 */
public abstract class MenuCommandHandler implements CommandHandler{
	
	public void beforeExecute(Context context) {
		MenuCommand menuCommand = context.get(MenuCommandConstants.MENU_COMMAND_NAME);
		String command = context.get(MenuCommandConstants.USER_INPUT_COMMAND_NAME);
		//默认菜单命令应该是停留在当前菜单
		context.put(MenuCommandConstants.GOTO_MENU_ID_NAME, menuCommand.getMenuConfig().getId());
		//设置渲染页面
		String path = StringUtil.isEmpty(menuCommand.getPath())?menuCommand.getMenuConfig().getPath():menuCommand.getPath();
		context.put(MenuCommandConstants.RENDER_PAGE_PATH_NAME, path);
		execute(command,menuCommand,context);
	}

	public void afterExecute(Context context) {
		context.remove(MenuCommandConstants.MENU_CONFIG_PAGE_NAME);
		context.remove(MenuCommandConstants.GOTO_MENU_ID_NAME);
		context.remove(MenuCommandConstants.RENDER_PAGE_PATH_NAME);
		context.remove(MenuCommandConstants.MENU_COMMAND_NAME);
		context.remove(MenuCommandConstants.USER_INPUT_COMMAND_NAME);
	}

	/**
	 * 执行相关菜单逻辑，保存渲染路径和跳转菜单id
	 * @param command
	 * @param menuCommand
	 * @param context
	 */
	protected abstract void execute(String command,MenuCommand menuCommand,Context context);
}
