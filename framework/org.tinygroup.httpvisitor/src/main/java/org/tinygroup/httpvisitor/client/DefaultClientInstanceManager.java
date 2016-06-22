package org.tinygroup.httpvisitor.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DefaultClientInstanceManager implements ClientInstanceManager{

	private Map<String,ClientInterface> clientPools = new HashMap<String,ClientInterface>();
	
	public void registerClient(String templateId, ClientInterface client) {
		clientPools.put(templateId, client);
	}

	public void closeClient(String templateId) throws IOException {
		 ClientInterface client = clientPools.get(templateId);
		 if(client!=null){
			client.close(); 
			clientPools.remove(templateId);
		 }
	}

	public void closeAllClients() throws IOException {
		for(String templateId:clientPools.keySet()){
			closeClient(templateId);
		}
	}

	public ClientInterface getClientInterface(String templateId) {
		return clientPools.get(templateId);
	}

}
