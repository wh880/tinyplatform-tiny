/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tinygroup.tinynetty.remote;

import java.util.HashMap;
import java.util.Map;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcore.CEPCoreOperator;
import org.tinygroup.cepcore.exception.CEPConnectException;
import org.tinygroup.event.Event;
import org.tinygroup.event.central.Node;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.net.daemon.DaemonUtils;
import org.tinygroup.net.exception.InterruptedRuntimeException;
import org.tinygroup.tinynetty.EventClient;
import org.tinygroup.tinynetty.EventServer;

public class EventNetRemoteImpl implements CEPCoreOperator {
	private static Logger logger = LoggerFactory
			.getLogger(EventNetRemoteImpl.class);
	private EventServer server;
	Map<String, EventClientDaemonRunnable> clientMap = new HashMap<String, EventClientDaemonRunnable>();
	private int timeout = 10;

	public void startCEPCore(CEPCore cep, Node node) {
		logger.logMessage(LogLevel.INFO, "本地节点服务开始启动");
		logger.logMessage(LogLevel.INFO, "IP:{0},PORT:{1},NAME:{2}",
				node.getIp(), node.getPort(), node.getNodeName());
		try {
			server = new EventServer(Integer.parseInt(node.getPort()));
			server.run();
			logger.logMessage(LogLevel.INFO, "本地节点服务启动成功");
		} catch (Exception e) {
			logger.errorMessage("本地节点服务启动失败,ip:{0},port:{1},nodeName:{2}", e,
					node.getIp(), node.getPort(), node.getNodeName());
		}
	}

	public void stopCEPCore(CEPCore cep, Node node) {
		logger.logMessage(LogLevel.INFO, "本地节点服务开始关闭");
		try {
			server.stop();
			logger.logMessage(LogLevel.INFO, "本地节点服务关闭完成");
		} catch (Exception e) {
			logger.errorMessage("本地节点关闭时出现异常,Node:{0}", e, node);
		}

	}

	private EventClientDaemonRunnable getNewClient( Node remoteNode){
		String nodeInfo = remoteNode.toString();
		EventClientDaemonRunnable client = new EventClientDaemonRunnable(remoteNode.getIp(),
				Integer.parseInt(remoteNode.getPort()),false);
		DaemonUtils.daemon(nodeInfo, client);
		return client;
	}
	public Event remoteprocess(Event event, Node remoteNode) {
		String nodeInfo = remoteNode.toString();
		EventClientDaemonRunnable client = clientMap.get(nodeInfo);
		EventClient eventClient = null;//client.getClient();
		if (client == null){
			client = getNewClient(remoteNode);
			clientMap.put(nodeInfo, client);
			eventClient = client.getClient();
		}else{
			eventClient = client.getClient();
//			if(eventClient.isClosed()){
//				removeConnect(remoteNode);
//				client = getNewClient(remoteNode);
//				clientMap.put(nodeInfo, client);
//				eventClient = client.getClient();
//			}
		}
		
		
		logger.logMessage(LogLevel.INFO,
				"发送请求,目标节点{0}:{1}:{2},请求信息:[serviceId:{3}]",
				remoteNode.getIp(), remoteNode.getPort(), remoteNode
						.getNodeName(), event.getServiceRequest()
						.getServiceId());
		try {
			
			int i = 1;
			while (!eventClient.isReady()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					//此处无须处理
				}
				i++;
				if (i > timeout) {
					break;
				}
			}
			Event result = eventClient.sendObject(event);

			logger.logMessage(
					LogLevel.INFO,
					"请求成功,目标节点{0}:{1}:{2},请求信息:[serviceId:{3}]",
					remoteNode.getIp(), remoteNode.getPort(), remoteNode
							.getNodeName(), event.getServiceRequest()
							.getServiceId());
			return result;
		} catch (InterruptedRuntimeException e) {
			logger.logMessage(
					LogLevel.ERROR,
					"请求失败,目标节点{0}:{1}:{2},请求信息:[serviceId:{3},信息:{5}",
					remoteNode.getIp(), remoteNode.getPort(), remoteNode
							.getNodeName(), event.getServiceRequest()
							.getServiceId(), e.getMessage());

			removeConnect(remoteNode);
			throw new CEPConnectException(e, remoteNode);
		}catch (RuntimeException e) {
			removeConnect(remoteNode);
			throw e;
		}

	}

	public void removeConnect(Node remoteNode) {
		logger.logMessage(LogLevel.INFO, "开始移除连接,目标节点:{0}",
				remoteNode.toString());
		String nodeInfo = remoteNode.toString();
		if (clientMap.containsKey(nodeInfo)) {
			clientMap.remove(nodeInfo).stop();
			logger.logMessage(LogLevel.INFO, "移除连接完成,目标节点:{0}",
					remoteNode.toString());
		} else {
			logger.logMessage(LogLevel.INFO, "连接不存在,无需移除,目标节点:{0}",
					remoteNode.toString());
		}
	}

	public void startCEPCore(CEPCore cep) {
		// TODO Auto-generated method stub
		
	}

	public void stopCEPCore(CEPCore cep) {
		// TODO Auto-generated method stub
		
	}

	public void setCEPCore(CEPCore cep) {
		// TODO Auto-generated method stub
		
	}

}
