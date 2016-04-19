package org.tinygroup.weixin.event;

import org.tinygroup.weixin.util.WeiXinEventMode;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 取消订阅事件模型
 * @author yancheng11334
 *
 */
@XStreamAlias("xml")
public class UnSubscribeEvent extends CommonEvent{

	public UnSubscribeEvent(){
		super();
		setEvent(WeiXinEventMode.UNSUBSCRIBE.getEvent());
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
