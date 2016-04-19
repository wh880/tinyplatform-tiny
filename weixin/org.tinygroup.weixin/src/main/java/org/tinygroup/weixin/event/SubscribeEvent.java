package org.tinygroup.weixin.event;

import org.tinygroup.weixin.util.WeiXinEventMode;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 订阅事件模型
 * @author yancheng11334
 *
 */
@XStreamAlias("xml")
public class SubscribeEvent extends CommonEvent{
    
	public SubscribeEvent(){
		super();
		setEvent(WeiXinEventMode.SUBSCRIBE.getEvent());
	}
	
	@XStreamAlias("EventKey")
	private String eventKey;
	
	@XStreamAlias("Ticket")
	private String ticket;

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
}
