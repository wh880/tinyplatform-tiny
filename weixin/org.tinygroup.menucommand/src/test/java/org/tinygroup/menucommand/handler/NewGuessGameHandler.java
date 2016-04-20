package org.tinygroup.menucommand.handler;

import org.tinygroup.context.Context;
import org.tinygroup.menucommand.GuessNumberOperator;
import org.tinygroup.menucommand.GuessNumberSession;
import org.tinygroup.menucommand.config.MenuCommand;

/**
 * 新建猜数字游戏
 * @author yancheng11334
 *
 */
public class NewGuessGameHandler extends MenuCommandHandler{

	private GuessNumberOperator operator = new GuessNumberOperator();
	
	@Override
	protected void execute(String command, MenuCommand menuCommand,
			Context context) {
		String userId = context.get("userId");
		GuessNumberSession session = new GuessNumberSession();
		session.userId = userId;
		operator.addGuessNumberSession(session);
	}

}
