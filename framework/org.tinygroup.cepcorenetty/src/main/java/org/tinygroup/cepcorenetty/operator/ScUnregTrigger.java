package org.tinygroup.cepcorenetty.operator;

import org.tinygroup.tinynetty.EventServerHandler;
import org.tinygroup.tinynetty.remote.EventClientDaemonRunnable;
import org.tinygroup.tinynetty.remote.EventTrigger;

public class ScUnregTrigger implements EventTrigger {
	EventClientDaemonRunnable runable;

	public void execute() {
		EventServerHandler.remove(runable);
	}

	public void setEventClientDaemonRunnable(EventClientDaemonRunnable runable) {
		this.runable = runable;
	}

}
