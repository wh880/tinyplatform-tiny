package org.tinygroup.weixinmenu.button;

import org.tinygroup.weixin.util.WeiXinEventMode;

/**
 * pic_weixin：弹出微信相册发图器。用户点击按钮后，微信客户端将调起微信相册，完成选择操作后，将选择的相片发送给开发者的服务器，并推送事件给开发者，同时收起相册，随后可能会收到开发者下发的消息。
 * @author yancheng11334
 *
 */
public class PicWeiXinSubButton extends CommonSubButton{

	public PicWeiXinSubButton(){
		this(null,null);
	}
	
	public PicWeiXinSubButton(String name){
		this(name,null);
	}
	
    public PicWeiXinSubButton(String name,String key){
		super(name,WeiXinEventMode.PIC_WEIXIN.getEvent());
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
