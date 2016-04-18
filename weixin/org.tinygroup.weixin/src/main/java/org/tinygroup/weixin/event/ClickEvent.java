package org.tinygroup.weixin.event;

import org.tinygroup.weixin.util.WeiXinEventMode;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 自定义菜单事件<br>
 * 点击菜单拉取消息时的事件推送
 * @author yancheng11334
 *
 */
@XStreamAlias("xml")
public class ClickEvent extends CommonEvent{
	
	public ClickEvent(){
		super();
		setEvent(WeiXinEventMode.CLICK.getEvent());
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
