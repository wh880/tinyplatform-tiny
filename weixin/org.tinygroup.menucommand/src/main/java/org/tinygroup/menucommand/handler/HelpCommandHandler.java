package org.tinygroup.menucommand.handler;

import org.tinygroup.context.Context;
import org.tinygroup.menucommand.MenuCommandConstants;
import org.tinygroup.menucommand.config.MenuCommand;
import org.tinygroup.menucommand.config.MenuConfig;
import org.tinygroup.menucommand.config.SystemCommand;

/**
 * 列出命令的细节
 * @author yancheng11334
 *
 */
public class HelpCommandHandler extends SystemCommandHandler{

	protected void execute(String command, SystemCommand systemCommand,
			MenuConfig config, Context context) {
		
		//按空格进行切分
		String[] keys = command.split("\\s+");
		if(keys!=null && keys.length>=2){
		   String key = keys[1].trim();
		   
		   //处理下级菜单
		   if(config.getMenuConfigList()!=null){
			   for(MenuConfig menuConfig:config.getMenuConfigList()){
				   if(menuConfig.getName().equals(key)){
					  context.put("helpConfig",config);
					  break;
				   }
			   }  
		   }
		   
		   //处理命令
		   if(config.getMenuCommandList()!=null){
			   for(MenuCommand menuCommand:config.getMenuCommandList()){
				   if(menuCommand.getName().equals(key)){
					  context.put("helpCommand",menuCommand);
				   }
			   }
		   }
		  
		}else{
		   context.put("helpConfig",config);
		}
		
		context.put(MenuCommandConstants.RENDER_PAGE_PATH_NAME, systemCommand.getPath());
		context.put(MenuCommandConstants.GOTO_MENU_ID_NAME, config.getId());
	}

}
