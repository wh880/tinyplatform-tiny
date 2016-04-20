package org.tinygroup.weixinmenu.button;

import org.tinygroup.weixin.util.WeiXinEventMode;

/**
 * scancode_push：扫码推事件。用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后显示扫描结果（如果是URL，将进入URL），且会将扫码的结果传给开发者，开发者可以下发消息。
 * @author yancheng11334
 *
 */
public class PushSubButton extends CommonSubButton{

	public PushSubButton(){
		this(null,null);
	}
	
	public PushSubButton(String name){
		this(name,null);
	}
	
    public PushSubButton(String name,String key){
		super(name,WeiXinEventMode.SCANCODE_PUSH.getEvent());
		this.key = key;
	}
	
	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
