package org.tinygroup.menucommand.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.context.Context;
import org.tinygroup.menucommand.CommandExecutor;
import org.tinygroup.menucommand.CommandHandler;
import org.tinygroup.menucommand.MenuCommandConstants;
import org.tinygroup.menucommand.MenuConfigManager;
import org.tinygroup.menucommand.config.BaseCommand;
import org.tinygroup.menucommand.config.MenuCommand;
import org.tinygroup.menucommand.config.MenuConfig;
import org.tinygroup.menucommand.config.MenuConfigs;
import org.tinygroup.menucommand.config.SystemCommand;
import org.tinygroup.menucommand.executor.DefaultCommandExecutor;
import org.tinygroup.menucommand.handler.ShowCommandHandler;
import org.tinygroup.template.TemplateRender;
import org.tinygroup.template.impl.TemplateRenderDefault;

/**
 * 默认的定义菜单管理器
 * 
 * @author yancheng11334
 * 
 */
public class MenuConfigManagerDefault implements MenuConfigManager {

	/**
	 * 菜单管理
	 */
	private Map<String, MenuConfig> menuConfigMaps = new HashMap<String, MenuConfig>();

	/**
	 * 系统命令管理
	 */
	private List<SystemCommand> systemCommandList = new ArrayList<SystemCommand>();

	private TemplateRender templateRender;
	
	private CommandHandler defaultCommandHandler= new ShowCommandHandler();

	public MenuConfigManagerDefault() {
		templateRender = new TemplateRenderDefault();
	}

	public TemplateRender getTemplateRender() {
		return templateRender;
	}

	public void setTemplateRender(TemplateRender templateRender) {
		this.templateRender = templateRender;
	}

	public void addMenuConfigs(MenuConfigs configs) {
		if (configs != null) {
			if (configs.getMenuConfigList() != null) {
				for (MenuConfig config : configs.getMenuConfigList()) {
					addMenuConfig(config);
				}
			}
			if (configs.getSystemCommandList() != null) {
				for (SystemCommand command : configs.getSystemCommandList()) {
					addSystemCommand(command);
				}
			}
		}
	}

	public void removeMenuConfigs(MenuConfigs configs) {
		if (configs != null) {
			if (configs.getMenuConfigList() != null) {
				for (MenuConfig config : configs.getMenuConfigList()) {
					removeMenuConfig(config);
				}
			}
			if (configs.getSystemCommandList() != null) {
				for (SystemCommand command : configs.getSystemCommandList()) {
					removeSystemCommand(command);
				}
			}
		}
	}

	public MenuConfig getMenuConfig(String menuId) {
		return menuConfigMaps.get(menuId);
	}

	/**
	 * 递归添加子菜单
	 */
	public void addMenuConfig(MenuConfig config) {
		List<MenuConfig> subMenus = config.getMenuConfigList();
		// 先添加自身菜单
		menuConfigMaps.put(config.getId(), config);
		config.compile();

		if (subMenus != null) {
			// 最后添加子菜单
			for (MenuConfig subMenu : subMenus) {
				subMenu.setParent(config);
				addMenuConfig(subMenu);
			}
		}

		// 设置菜单和菜单命令之间的层级关系
		List<MenuCommand> commands = config.getMenuCommandList();
		if (commands != null) {
			for (MenuCommand command : commands) {
				command.setMenuConfig(config);
				command.compile();
			}
		}

	}

	public void removeMenuConfig(MenuConfig config) {

		// 解除菜单和菜单命令之间的层级关系
		List<MenuCommand> commands = config.getMenuCommandList();
		if (commands != null) {
			for (MenuCommand command : commands) {
				command.setMenuConfig(null);
			}
		}

		List<MenuConfig> subMenus = config.getMenuConfigList();
		if (subMenus != null) {
			// 先删除子菜单
			for (MenuConfig subMenu : subMenus) {
				subMenu.setParent(null);
				removeMenuConfig(subMenu);
			}
		}
		// 最后删除自身菜单
		menuConfigMaps.remove(config.getId());
	}

	public void addSystemCommand(SystemCommand command) {
		command.compile();
		if (!systemCommandList.contains(command)) {
			systemCommandList.add(command);
		}
	}

	public void removeSystemCommand(SystemCommand command) {
		systemCommandList.remove(command);
	}

	public List<SystemCommand> getSystemCommandList() {
		return systemCommandList;
	}

	/**
	 * 根据命令匹配处理系统级命令，如home、back、exit
	 * 
	 * @param command
	 * @return
	 */
	public SystemCommand getSystemCommand(String command) {
		for (SystemCommand systemCommand : systemCommandList) {
			if (systemCommand.match(command)) {
				return systemCommand;
			}
		}
		return null;
	}

	public CommandExecutor getCommandExecutor(String menuId, String command,Context context) {

		context.put(MenuCommandConstants.BEFORE_MENU_ID_NAME, menuId);
		context.put(MenuCommandConstants.USER_INPUT_COMMAND_NAME, command);
		context.put(MenuCommandConstants.SYSTEM_COMMAND_LIST_PAGE_NAME, systemCommandList);
		// 先查询菜单命令
		if (!StringUtil.isEmpty(menuId)) {
			// 根据菜单环境查询命令
			MenuConfig parentConfig = menuConfigMaps.get(menuId);
			if (parentConfig == null) {
				return null;
			}else{
				context.put(MenuCommandConstants.MENU_CONFIG_PAGE_NAME, parentConfig);
			}
			

			// 匹配当前菜单的菜单命令,例如进入非终端的菜单
			MenuCommand menuCommand = parentConfig.getMatchMenuCommand(command);

			// 匹配系统命令
			SystemCommand systemCommand = getSystemCommand(command);
			
			if (menuCommand != null) {
				context.put(MenuCommandConstants.MENU_COMMAND_NAME, menuCommand);
				////用户菜单支持系统命令;并且输入的命令刚好匹配系统命令
				if(menuCommand.isSystemEnable() && systemCommand!=null){
					context.put(MenuCommandConstants.SYSTEM_COMMAND_NAME, systemCommand);
					return createDefaultCommandExecutor(systemCommand);
				}else{
					return createDefaultCommandExecutor(menuCommand);
				}
			}

			if (systemCommand != null) {
				context.put(MenuCommandConstants.SYSTEM_COMMAND_NAME, systemCommand);
				return createDefaultCommandExecutor(systemCommand);
			}

			// 匹配子菜单列表的命令,例如进入终端菜单
			MenuConfig menuConfig = parentConfig.getMatchMenuConfig(command);
			if (menuConfig != null) {
				context.put(MenuCommandConstants.MENU_CONFIG_PAGE_NAME, menuConfig);
				return createMenuConfigShowExecutor();
			}

			// 走默认的展示处理器
			return createMenuConfigShowExecutor();
			
		} else {
			// 首次查询菜单，匹配当前菜单
			for (MenuConfig config : menuConfigMaps.values()) {
				// 父节点存在的菜单表示不是根目录，忽略
				if (config.getParent() == null && config.match(command)) {
					context.put(MenuCommandConstants.MENU_CONFIG_PAGE_NAME, config);
					return createMenuConfigShowExecutor();
				}
			}

			return null;
		}
	}
	
	/**
	 * 返回默认的命令执行器
	 * @param handler
	 * @return
	 */
	protected DefaultCommandExecutor createDefaultCommandExecutor(CommandHandler handler){
		DefaultCommandExecutor executor = new DefaultCommandExecutor();
		executor.setTemplateRender(templateRender);
		executor.setCommandHandler(handler);
		return executor;
	}
	
	/**
	 * 返回默认的命令处理器
	 * @param baseCommand
	 * @return
	 */
	protected DefaultCommandExecutor createDefaultCommandExecutor(BaseCommand baseCommand){
		return createDefaultCommandExecutor((CommandHandler)baseCommand.createCommandObject());
	}
	
	/**
	 * 返回默认的命令处理器
	 * @return
	 */
	protected DefaultCommandExecutor createMenuConfigShowExecutor(){
		return createDefaultCommandExecutor(defaultCommandHandler);
	}

}
