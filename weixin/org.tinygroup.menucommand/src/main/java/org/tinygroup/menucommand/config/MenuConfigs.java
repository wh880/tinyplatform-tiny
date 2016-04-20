package org.tinygroup.menucommand.config;

import java.util.List;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 命令菜单定义
 * @author yancheng11334
 *
 */
@XStreamAlias("menu-configs")
public class MenuConfigs {

	/**
	 * 菜单结构组
	 */
	@XStreamImplicit
	private List<MenuConfig> menuConfigList;
	
	/**
	 * 系统命令组
	 */
	@XStreamImplicit
	private List<SystemCommand> systemCommandList;


	public List<MenuConfig> getMenuConfigList() {
		return menuConfigList;
	}

	public void setMenuConfigList(List<MenuConfig> menuConfigList) {
		this.menuConfigList = menuConfigList;
	}

	public List<SystemCommand> getSystemCommandList() {
		return systemCommandList;
	}

	public void setSystemCommandList(List<SystemCommand> systemCommandList) {
		this.systemCommandList = systemCommandList;
	}
	
}
