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
package org.tinygroup.rpc;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcore.CEPCoreRemoteInterface;
import org.tinygroup.cepcore.exception.CEPConnectException;
import org.tinygroup.event.Event;
import org.tinygroup.event.central.Node;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.rpc.util.CEPCoreRMIServer;
import org.tinygroup.rpc.util.RMIRemoteUtil;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CEPCoreRMIRemoteImpl implements CEPCoreRemoteInterface {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CEPCoreRMIRemoteImpl.class);
	private ConcurrentMap<String, CEPCoreRMI> rmiMap = new ConcurrentHashMap<String, CEPCoreRMI>();
	// private static CEPCoreRMIServer server;
	private static Registry registry;

	public void startCEPCore(CEPCore cep, Node node) {
		try {
			LOGGER.logMessage(LogLevel.INFO, "本地节点服务开始启动");
			LOGGER.logMessage(LogLevel.INFO, "IP:{0},PORT:{1},NAME:{2}",
					node.getIp(), node.getPort(), node.getNodeName());
			System.setProperty("java.rmi.server.hostname", node.getIp());
			int port = Integer.parseInt(node.getPort());
			//registry = LocateRegistry.createRegistry(port);// 绑定端口
			try {
				registry = LocateRegistry.getRegistry(node.getIp(), port);
				registry.list();
			} catch (Exception e) {
				
					registry = LocateRegistry.createRegistry(port);
				
			}

			String url = RMIRemoteUtil.getURL(node);// 地址
			// server = new CEPCoreRMIServer();
			Naming.rebind(url, new CEPCoreRMIServer());// 绑定
			LOGGER.logMessage(LogLevel.INFO, "本地节点服务启动成功");
		} catch (Exception e) {
			LOGGER.errorMessage("本地节点RMI服务启动失败,ip:{0},port:{1},nodeName:{2}",
					e, node.getIp(), node.getPort(), node.getNodeName());
		}

	}

	public void stopCEPCore(CEPCore cep, Node node) {
		LOGGER.logMessage(LogLevel.INFO, "本地节点服务开始关闭");
		try {
			registry.unbind(node.getNodeName());
			UnicastRemoteObject.unexportObject(registry, true);
			// System.exit(0);
		} catch (Exception e) {
			LOGGER.errorMessage("本地节点关闭RMI服务时出现异常,Node:{0}", e, node);
		}
		LOGGER.logMessage(LogLevel.INFO, "本地节点服务关闭完成");

	}

	private CEPCoreRMI createObject(Node node) {
		String url = RMIRemoteUtil.getURL(node);
		CEPCoreRMI rmi = null;
		try {
			rmi = (CEPCoreRMI) Naming.lookup(url);
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR, "获取连接失败,目标节点{0}:{1}:{2},{3}",
					node.getIp(), node.getPort(), node.getNodeName(),
					e.getMessage());
			throw new CEPConnectException(e, node);
		}
		return rmi;
	}

	public CEPCoreRMI getConnect(Node remoteNode) {
		String url = RMIRemoteUtil.getURL(remoteNode);
		if (!rmiMap.containsKey(url)) {
			rmiMap.put(url, createObject(remoteNode));
		}
		return rmiMap.get(url);
	}

	public void returnConnect(Node remoteNode, CEPCoreRMI rmi) {
	}

	public void removeConnect(Node remoteNode) {
		LOGGER.logMessage(LogLevel.INFO, "移除远端节点的连接,IP:{0},PORT:{1}",
				remoteNode.getIp(), remoteNode.getPort());
		String url = RMIRemoteUtil.getURL(remoteNode);
		rmiMap.remove(url);
		LOGGER.logMessage(LogLevel.INFO, "移除远端节点的连接完成,IP:{0},PORT:{1}",
				remoteNode.getIp(), remoteNode.getPort());
	}

	public Event remoteprocess(Event event, Node remoteNode) {
		// logger.logMessage(LogLevel.ERROR,
		// "=======begin getConnect====="+System.currentTimeMillis());
		CEPCoreRMI rmiCep = getConnect(remoteNode);
		// logger.logMessage(LogLevel.ERROR,
		// "=======after getConnect====="+System.currentTimeMillis());
		LOGGER.logMessage(LogLevel.INFO,
				"发送请求,目标节点{0}:{1}:{2},请求信息:[serviceId:{3}]",
				remoteNode.getIp(), remoteNode.getPort(), remoteNode
						.getNodeName(), event.getServiceRequest()
						.getServiceId());

		Event result = null;
		try {
			// logger.logMessage(LogLevel.ERROR,
			// "=======begin deal====="+System.currentTimeMillis());
			result = rmiCep.processFromRemote(event);
			// logger.logMessage(LogLevel.ERROR,
			// "=======after deal====="+System.currentTimeMillis());
			LOGGER.logMessage(LogLevel.INFO,
					"请求成功,目标节点{0}:{1}:{2},请求信息:[serviceId:{3}]", remoteNode
							.getIp(), remoteNode.getPort(), remoteNode
							.getNodeName(), event.getServiceRequest()
							.getServiceId());
		} catch (RemoteException e) {
			LOGGER.logMessage(LogLevel.ERROR,
					"请求失败,目标节点{0}:{1}:{2},请求信息:[serviceId:{3}],信息:{5}",
					remoteNode.getIp(), remoteNode.getPort(), remoteNode
							.getNodeName(), event.getServiceRequest()
							.getServiceId());
			throw new CEPConnectException(e, remoteNode);
		} finally {
			if (rmiCep != null) {
				returnConnect(remoteNode, rmiCep);
			}
		}
		return result;
	}
}
