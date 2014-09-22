package org.tinygroup.cepcorenetty.operator;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcore.CEPCoreOperator;
import org.tinygroup.tinynetty.EventServer;

public class ScOperator implements CEPCoreOperator{
	private CEPCore core;
	private String port;
	EventServer server;
	public void startCEPCore(CEPCore cep) {
		server = new EventServer(Integer.parseInt(port));
		server.run();
	}

	public void stopCEPCore(CEPCore cep) {
		server.stop();
	}

	public void setCEPCore(CEPCore cep) {
		this.core = cep;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

}
