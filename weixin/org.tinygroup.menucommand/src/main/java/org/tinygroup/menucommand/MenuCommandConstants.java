package org.tinygroup.menucommand;

import org.tinygroup.context.Context;

/**
 * 菜单命令的常量辅助类
 * @author yancheng11334
 *
 */
public final class MenuCommandConstants {

	/**
	 * 进入菜单事件
	 */
	public static final String ENTER_EVENT_TYPE = "enter";
	
	/**
	 * 离开菜单事件
	 */
	public static final String EXIT_EVENT_TYPE = "exit";
	
	/**
	 * 模板渲染接口的上下文参数名称
	 */
	public static final String TEMPLATE_RENDER_NAME = "_template_render";
	
	/**
	 * 渲染页面路径的上下文参数名称
	 */
	public static final String RENDER_PAGE_PATH_NAME = "_render_page_path";
	
	/**
	 * 渲染页面默认路径
	 */
	public static final String DEFAULT_PAGE_PATH = "/menucommand/showMenuConfig.page";
	
	/**
	 * 命令执行后页面所处的菜单Id
	 */
	public static final String GOTO_MENU_ID_NAME = "_goto_menu_id";
	
	/**
	 * 命令执行前页面所处的菜单Id
	 */
	public static final String BEFORE_MENU_ID_NAME = "_before_menu_id";
	
	/**
	 * 系统命令对象在上下文参数名称
	 */
	public static final String SYSTEM_COMMAND_NAME = "_system_command";
	
	/**
	 * 菜单命令对象在上下文参数名称
	 */
	public static final String MENU_COMMAND_NAME = "_menu_command";
	
	/**
	 * 用户输入的命令在上下文参数名称
	 */
	public static final String USER_INPUT_COMMAND_NAME = "_user_input_command";
	
	/**
	 * 用户查询关键字在上下文参数名称
	 */
	public static final String USER_QUERY_KEY_NAME = "queryKey";
	
	/**
	 * MenuConfig对象保存在页面渲染时的参数名
	 */
	public static final String MENU_CONFIG_PAGE_NAME= "menuConfig";
	
	/**
	 * 系统命令参数列表保存在页面渲染时的参数名
	 */
	public static final String SYSTEM_COMMAND_LIST_PAGE_NAME= "systemCommandList";
	
	/**
	 * 得到渲染路径
	 * @param context
	 * @return
	 */
	public static String getRenderPath(Context context){
		String path = context.get(RENDER_PAGE_PATH_NAME);
		return path!=null?path:DEFAULT_PAGE_PATH;
	}
}
