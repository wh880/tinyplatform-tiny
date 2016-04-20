package org.tinygroup.menucommand.handler;

import org.tinygroup.context.Context;
import org.tinygroup.menucommand.GuessNumberOperator;
import org.tinygroup.menucommand.GuessNumberSession;
import org.tinygroup.menucommand.config.MenuCommand;

/**
 * 判断数字的业务逻辑
 * @author yancheng11334
 *
 */
public class GuessNumberHandler extends MenuCommandHandler{

	private GuessNumberOperator operator = new GuessNumberOperator();
	
	@Override
	protected void execute(String command, MenuCommand menuCommand,
			Context context) {
		String userId = context.get("userId");
		GuessNumberSession session = operator.getGuessNumberSession(userId);
		try{
			int num = Integer.parseInt(command);
			if(num<1 || num >100){
				context.put("gamestatus", "error");
				return ;
			}
			
			session.count++;
			if(num>session.num){
				context.put("gamestatus", "large");
			}else if(num<session.num){
				context.put("gamestatus", "small");
			}else{
				context.put("gamestatus", "right");
				context.put("gamenumber", session.count);
			}
			operator.addGuessNumberSession(session);
		}catch(Exception e){
			context.put("gamestatus", "error");
		}
		
	}

}
