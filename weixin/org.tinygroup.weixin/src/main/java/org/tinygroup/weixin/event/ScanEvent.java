package org.tinygroup.weixin.event;

import org.tinygroup.weixin.util.WeiXinEventMode;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 用户扫描带场景值二维码
 * @author yancheng11334
 *
 */
@XStreamAlias("xml")
public class ScanEvent extends CommonEvent{

	public ScanEvent(){
		super();
		setEvent(WeiXinEventMode.SCAN.getEvent());
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
