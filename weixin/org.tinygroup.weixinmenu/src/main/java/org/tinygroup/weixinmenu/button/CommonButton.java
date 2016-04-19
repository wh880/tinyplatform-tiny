package org.tinygroup.weixinmenu.button;

/**
 * 一级菜单按钮
 * @author yancheng11334
 *
 */
public class CommonButton {
    public CommonButton(){
    	
    }
    
    public CommonButton(String name){
    	this.name = name;
    }
    
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
