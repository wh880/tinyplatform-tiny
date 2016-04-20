package org.tinygroup.menucommand.handler;

import org.tinygroup.context.Context;
import org.tinygroup.menucommand.GuessNumberOperator;
import org.tinygroup.menucommand.MenuCommandConstants;
import org.tinygroup.menucommand.config.MenuCommand;

/**
 * 清理游戏数据
 * @author yancheng11334
 *
 */
public class DelGuessNumberSessionHandler extends MenuCommandHandler{

	private GuessNumberOperator operator = new GuessNumberOperator();
	
	@Override
	protected void execute(String command, MenuCommand menuCommand,
			Context context) {
		String userId = context.get("userId");
		operator.removeGuessNumberSession(userId);
		
		//返回上级菜单
		context.put(MenuCommandConstants.MENU_CONFIG_PAGE_NAME, menuCommand.getMenuConfig().getParent());
		context.put(MenuCommandConstants.GOTO_MENU_ID_NAME, menuCommand.getMenuConfig().getParent().getId());
	}

}
