/**
 *  Copyright (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
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
 * --------------------------------------------------------------------------
 *  版权 (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  本开源软件遵循 GPL 3.0 协议;
 *  如果您不遵循此协议，则不被允许使用此文件。
 *  你可以从下面的地址获取完整的协议文本
 *
 *       http://www.gnu.org/licenses/gpl.html
 */
package org.tinygroup.rmi.impl;

import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.rmi.RmiServer;

/**
 * 远程RMI服务器 Created by luoguo on 14-1-10.
 */
public class RmiServerRemote implements RmiServer {
	private final static Logger logger = LoggerFactory
			.getLogger(RmiServerRemote.class);
	HeartThread heartThread = new HeartThread();
	int port = DEFAULT_RMI_PORT;
	String hostName = "localhost";
	Registry registry = null;
	Map<String, Remote> registeredObjectMap = new HashMap<String, Remote>();
	RmiServer server = null;

	public RmiServerRemote(String hostName, int port) {
		this.hostName = hostName;
		this.port = port;
		getRegistry();
		heartThread.start();
	}

	public Registry getRegistry() {
		try {
			registry = LocateRegistry.getRegistry(hostName, port);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
		try {
			server = (RmiServer) registry.lookup(hostName);
		} catch (ConnectException e) {
			throw new RuntimeException("获取RmiServer:" + hostName + "时连接发生错误", e);
		} catch (RemoteException e) {
			throw new RuntimeException("获取RmiServer:" + hostName + "时出错", e);
		} catch (NotBoundException e) {
			throw new RuntimeException("获取RmiServer:" + hostName
					+ "时出错,该对象未曾注册", e);
		}
		// RmiUtil.start((RmiServerLocal)server);
		return registry;
	}

	private void addObjectToMap(Remote object, String name) {
		registeredObjectMap.put(name, object);
	}

	private void removeObjectFromMap(String name) {
		registeredObjectMap.remove(name);
	}

	public void registerRemoteObject(Remote object, Class type, String id) {
		addObjectToMap(object, RmiUtil.getName(type.getName(), id));
		server.registerRemoteObject(object, type, id);
	}

	public void registerRemoteObject(Remote object, String type, String id) {
		addObjectToMap(object, RmiUtil.getName(type, id));
		server.registerRemoteObject(object, type, id);
	}

	public void registerRemoteObject(Remote object, String name) {
		addObjectToMap(object, name);
		server.registerRemoteObject(object, name);
	}

	public void registerRemoteObject(Remote object, Class type) {
		addObjectToMap(object, type.getName());
		server.registerRemoteObject(object, type);
	}

	public void unregisterRemoteObject(String name) {
		removeObjectFromMap(name);
		server.unregisterRemoteObject(name);
	}

	public void unregisterRemoteObjectByType(Class type) {
		removeObjectFromMap(type.getName());
		server.unregisterRemoteObjectByType(type);
	}

	public void unregisterRemoteObjectByType(String type) {
		removeObjectFromMap(type);
		server.unregisterRemoteObjectByType(type);
	}

	public void unregisterRemoteObject(String type, String id) {
		removeObjectFromMap(RmiUtil.getName(type, id));
		server.unregisterRemoteObject(type, id);
	}

	public void unregisterRemoteObject(Class type, String id) {
		removeObjectFromMap(RmiUtil.getName(type.getName(), id));
		server.unregisterRemoteObject(type, id);
	}

	public <T> T getRemoteObject(String name) throws RemoteException {
		return (T) server.getRemoteObject(name);
	}

	public <T> T getRemoteObject(Class<T> type) throws RemoteException {
		return (T) server.getRemoteObject(type);
	}

	public <T> List<T> getRemoteObjectList(Class<T> type) {
		return (List<T>) server.getRemoteObjectList(type);
	}

	public <T> List<T> getRemoteObjectListInstanceOf(Class<T> type) {
		return (List<T>) server.getRemoteObjectListInstanceOf(type);
	}

	public <T> List<T> getRemoteObjectList(String typeName) {
		return (List<T>) server.getRemoteObjectList(typeName);
	}

	public void unexportObjects() throws RemoteException {
		for (String name : registeredObjectMap.keySet()) {
			unregisterRemoteObject(name);
		}

	}

	public void unregisterRemoteObject(Remote object) throws RemoteException {
		for (String name : registeredObjectMap.keySet()) {
			Remote r = registeredObjectMap.get(name);
			if (r.equals(object)) {
				registeredObjectMap.remove(name);
				break;
			}
		}
		server.unregisterRemoteObject(object);
	}

	public void stop() {
		try {
			unexportObjects();
		} catch (RemoteException e) {
			logger.error(e);
		}
	}

	protected void registerAllRemoteObject() {
		for (String name : registeredObjectMap.keySet()) {
			registerRemoteObject(registeredObjectMap.get(name), name);
		}
	}

	class HeartThread extends Thread {
		private static final int MILLISECOND_PER_SECOND = 1000;
		private boolean stop = false;
		private int breathInterval = 5;
		private boolean isDown = false;

		public void setStop(boolean stop) {
			this.stop = stop;
		}

		public void run() {
			while (!stop) {
				try {
					sleep(breathInterval * MILLISECOND_PER_SECOND);
				} catch (InterruptedException e) {
					continue;
				}
				try {
					logger.logMessage(LogLevel.DEBUG, "开始向远端服务器{0}:{1}发起心跳检测",
							hostName, port);
					registry.list();
					if (isDown) {
						logger.logMessage(LogLevel.INFO,
								"开始重新向远端服务器{0}:{1}注册对象", hostName, port);
						registerAllRemoteObject();
						logger.logMessage(LogLevel.INFO,
								"重新向远端服务器{0}:{1}注册对象结束", hostName, port);
						isDown = false;
					}
					logger.logMessage(LogLevel.DEBUG, "向远端服务器{0}:{1}心跳检测结束",
							hostName, port);
				} catch (RemoteException e) {
					logger.errorMessage("向远端服务器{0}:{1}心跳检测失败", e, hostName,
							port);
					isDown = true;
				}
			}

		}
	}
}
