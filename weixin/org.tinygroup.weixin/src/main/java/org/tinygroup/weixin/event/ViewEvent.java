package org.tinygroup.weixin.event;

import org.tinygroup.weixin.util.WeiXinEventMode;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 自定义菜单事件<br>
 * 点击菜单跳转链接时的事件推送 
 * @author yancheng11334
 *
 */
@XStreamAlias("xml")
public class ViewEvent extends CommonEvent{
	
	public ViewEvent(){
		super();
		setEvent(WeiXinEventMode.VIEW.getEvent());
	}

	@XStreamAlias("EventKey")
	private String eventKey;
	
	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
}
