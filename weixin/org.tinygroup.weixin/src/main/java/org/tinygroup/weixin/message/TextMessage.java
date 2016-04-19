package org.tinygroup.weixin.message;

import org.tinygroup.weixin.util.WeiXinMessageMode;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 文本消息
 * @author yancheng11334
 *
 */
@XStreamAlias("xml")
public class TextMessage extends CommonMessage{

	public TextMessage(){
	   setMsgType(WeiXinMessageMode.TEXT.getType());	
	}
	
	@XStreamAlias("Content")
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
