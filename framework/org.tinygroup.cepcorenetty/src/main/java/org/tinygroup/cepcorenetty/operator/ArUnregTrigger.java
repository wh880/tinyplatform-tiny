package org.tinygroup.cepcorenetty.operator;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.tinynetty.remote.EventClientDaemonRunnable;
import org.tinygroup.tinynetty.remote.EventTrigger;
import org.tinygroup.tinynetty.remote.NettyEventProcessor;
import org.tinygroup.tinynetty.remote.NettyEventProcessorConatiner;

public class ArUnregTrigger implements EventTrigger {
	CEPCore core;
	NettyEventProcessor processor;

	public ArUnregTrigger(CEPCore core, NettyEventProcessor processor) {
		this.core = core;
		this.processor = processor;
	}

	EventClientDaemonRunnable runable;

	public void execute() {
		NettyEventProcessorConatiner.remove(processor, core);
	}

	public void setEventClientDaemonRunnable(EventClientDaemonRunnable runable) {
		this.runable = runable;
	}

}
