package org.tinygroup.tinynetty.remote;

public interface EventTrigger {
	void execute();
	void setEventClientDaemonRunnable(EventClientDaemonRunnable runable);
}
