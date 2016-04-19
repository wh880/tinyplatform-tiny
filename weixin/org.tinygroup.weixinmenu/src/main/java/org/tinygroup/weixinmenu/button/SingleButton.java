package org.tinygroup.weixinmenu.button;

/**
 * 没有二级菜单的一级菜单
 * @author yancheng11334
 *
 */
public class SingleButton extends CommonButton{
	public SingleButton(){
		super();
	}
	
	public SingleButton(String name){
		super(name);
	}
	
	public SingleButton(String name,String type,String key){
		super(name);
		this.type = type;
		this.key = key;
	}
	
	private  String type;
	
	private String key;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
