package org.tinygroup.menucommand;

/**
 * 命令处理结果
 * @author yancheng11334
 *
 */
public class CommandResult {

	/**
	 * 命令反馈信息
	 */
	private String message;
	
	/**
	 * 菜单Id
	 */
	private String menuId;
	
	public CommandResult(){
		
	}
	
    public CommandResult(String message,String menuId){
		this.message = message;
		this.menuId = menuId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@Override
	public String toString() {
		return "CommandResult [message=" + message + ", menuId=" + menuId + "]";
	}
	
}
