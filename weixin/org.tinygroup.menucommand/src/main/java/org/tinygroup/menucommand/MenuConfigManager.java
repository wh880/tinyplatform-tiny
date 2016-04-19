package org.tinygroup.menucommand;

import java.util.List;

import org.tinygroup.context.Context;
import org.tinygroup.menucommand.config.MenuConfig;
import org.tinygroup.menucommand.config.MenuConfigs;
import org.tinygroup.menucommand.config.SystemCommand;

/**
 * 菜单管理器
 * @author yancheng11334
 *
 */
public interface MenuConfigManager {

	/**
	 * 添加一组定义菜单
	 * @param configs
	 */
	void addMenuConfigs(MenuConfigs configs);
	
	/**
	 * 删除一组定义菜单
	 * @param configs
	 */
	void removeMenuConfigs(MenuConfigs configs);
	
	/**
	 * 添加定义菜单
	 * @param config
	 */
	void addMenuConfig(MenuConfig config);
	
	/**
	 * 删除定义菜单
	 * @param config
	 */
	void removeMenuConfig(MenuConfig config);
	
	/**
	 * 获得指定的菜单
	 * @param menuId
	 * @return
	 */
	MenuConfig getMenuConfig(String menuId);
	
	/**
	 * 添加系统命令
	 * @param command
	 */
	void addSystemCommand(SystemCommand command);
	
	/**
	 * 删除系统命令
	 * @param command
	 */
	void removeSystemCommand(SystemCommand command);
	
	/**
	 * 匹配系统命令
	 * @param command
	 * @return
	 */
	SystemCommand getSystemCommand(String command);
	
	/**
	 * 获得支持的系统命令列表
	 * @return
	 */
	List<SystemCommand>  getSystemCommandList();
	
	/**
	 * 得到对应的命令执行器
	 * @param menuId
	 * @param command
	 * @param context
	 * @return
	 */
	CommandExecutor getCommandExecutor(String menuId,String command,Context context);
}
