package org.tinygroup.cepcorenetty.operator;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.event.central.Node;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.tinynetty.remote.EventClientDaemonRunnable;
import org.tinygroup.tinynetty.remote.EventTrigger;

public class ArRegTrigger implements EventTrigger {
	protected static Logger logger = LoggerFactory.getLogger(ArRegTrigger.class);
	EventClientDaemonRunnable client;
	CEPCore core;
	Node node;
	public ArRegTrigger(CEPCore core,Node node){
		this.core = core;
		this.node = node;
	}
	public void execute() {
		regToSc();
	}
	public void setEventClientDaemonRunnable(EventClientDaemonRunnable runable){
		this.client = runable;
	}
	private void regToSc() {
		ArRegister ar = new ArRegister(client, core, node);
		ar.regToSc();
	}
	
}
