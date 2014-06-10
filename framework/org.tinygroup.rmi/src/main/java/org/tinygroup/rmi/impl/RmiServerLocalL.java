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

import java.io.Serializable;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.rmi.RmiServer;
import org.tinygroup.rmi.Verifiable;

public final class RmiServerLocalL extends UnicastRemoteObject implements
		RmiServer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8847587819458611248L;

	private final static Logger logger = LoggerFactory
			.getLogger(RmiServerRemote.class);

	int port = DEFAULT_RMI_PORT;
	String hostName = "localhost";
	int remotePort = DEFAULT_RMI_PORT;
	String remoteHostName = "";

	private ValidateThread validateThread = new ValidateThread();
	Registry registry = null;
	Registry remoteRegistry = null;
	RmiServer server = null;
	Map<String, Remote> registeredRemoteObjectMap = new HashMap<String, Remote>();
	Map<String, Remote> registeredLocalObjectMap = new HashMap<String, Remote>();

	public RmiServerLocalL() throws RemoteException {
		this("localhost", DEFAULT_RMI_PORT);
	}

	public RmiServerLocalL(int port) throws RemoteException {
		this("localhost", port);
	}

	public RmiServerLocalL(String hostName, int port) throws RemoteException {
		this(hostName, port, null, 0);
	}

	public RmiServerLocalL(String hostName, int port, String remoteHostName,
			int remotePort) throws RemoteException {
		if (hostName != null && !"".equals(hostName)) {
			this.hostName = hostName;
		}
		this.port = port;
		this.remoteHostName = remoteHostName;
		this.remotePort = remotePort;
		getRegistry();
		getRemoteRegistry();
		validateThread.start();
	}

	public Registry getRegistry() throws RemoteException {
		if (registry == null) {
			try {
				registry = LocateRegistry.getRegistry(hostName, port);
				registry.list();
			} catch (Exception e) {
				try {
					registry = LocateRegistry.createRegistry(port);
				} catch (RemoteException e1) {
					throw new RuntimeException(e1);
				}
			}
		}
		try {
			registry.rebind(hostName, this);
		} catch (AccessException e) {
			throw new RuntimeException(e);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}

		return registry;
	}

	public Registry getRemoteRegistry() throws RemoteException {

		if (remoteHostName == null || "".equals(remoteHostName)) {
			return null;
		}
		System.setProperty("java.rmi.server.hostname", remoteHostName);
		remoteRegistry = LocateRegistry.getRegistry(remoteHostName, remotePort);
		try {
			server = (RmiServer) remoteRegistry.lookup(remoteHostName);

		} catch (NotBoundException e) {
			throw new RuntimeException("获取RmiServer:" + remoteHostName
					+ "时出错,该对象未曾注册", e);
		}
		return remoteRegistry;
	}

	public void stop() throws RemoteException {
		validateThread.setStop(true);
		unexportObjects();
	}

	class ValidateThread extends Thread implements Serializable {
		private static final int MILLISECOND_PER_SECOND = 1000;
		private volatile boolean stop = false;
		private int breathInterval = 5;// 单位秒

		public void setStop(boolean stop) {
			this.stop = stop;
		}

		public void run() {
			while (!stop) {
				logger.logMessage(LogLevel.DEBUG, "开始检测已注册对象的可用性");
				try {
					sleep(breathInterval * MILLISECOND_PER_SECOND);
				} catch (InterruptedException e) {
					continue;
				}
				String[] names = null;
				try {
					names = registry.list();
				} catch (RemoteException e) {
					logger.errorMessage("查询已注册对象失败", e);
					continue;
				}

				for (String name : names) {
					Remote remote = null;
					try {
						remote = registry.lookup(name);
						if (remote instanceof Verifiable) {
							((Verifiable) remote).verify();
						}
					} catch (RemoteException e) {
						logger.errorMessage("检测到对象{0}已失效", e, name);
						try {
							logger.logMessage(LogLevel.INFO, "开始注销对象{0}", name);
							if (remote != null) {
								unregisterObject(remote);
							}
							logger.logMessage(LogLevel.INFO, "注销对象{0}完成", name);
						} catch (RemoteException e1) {
							logger.errorMessage("注销对象{0}失败", e, name);
						}
					} catch (NotBoundException e2) {
						logger.errorMessage("对象{0}未邦定", e2, name);
					}
				}
				logger.logMessage(LogLevel.DEBUG, "检测已注册对象的可用性完成");
			}

		}
	}

	public void registerLocalObject(Remote object, String name)
			throws RemoteException {
		try {
			logger.logMessage(LogLevel.DEBUG, "开始注册本地对象:{}", name);

			registeredLocalObjectMap.put(name, object);
			if (object instanceof UnicastRemoteObject) {
				registry.rebind(name, object);
			} else {
				Remote stub = UnicastRemoteObject.exportObject(object, 0);
				registry.rebind(name, stub);
			}
			if (server != null) {
				server.registerRemoteObject(object, name);
			}

			logger.logMessage(LogLevel.DEBUG, "结束注册本地对象:{}", name);
		} catch (RemoteException e) {
			logger.errorMessage("注册本地对象:{}时发生异常:{}！", e, name, e.getMessage());
			registeredLocalObjectMap.remove(name);
			throw new RuntimeException(e);
		}
	}

	public void registerLocalObject(Remote object, Class type, String id)
			throws RemoteException {
		registerLocalObject(object, type.getName(), id);
	}

	public void registerLocalObject(Remote object, String type, String id)
			throws RemoteException {
		registerLocalObject(object, getName(type, id));
	}

	public void registerLocalObject(Remote object, Class type)
			throws RemoteException {
		registerLocalObject(object, type.getName());
	}

	public void registerRemoteObject(Remote object, Class type, String id)
			throws RemoteException {
		registerRemoteObject(object, getName(type.getName(), id));
	}

	public void registerRemoteObject(Remote object, String type, String id)
			throws RemoteException {
		registerRemoteObject(object, getName(type, id));
	}

	public void registerRemoteObject(Remote object, String name)
			throws RemoteException {
		logger.logMessage(LogLevel.DEBUG, "开始注册远程对象:{}", name);
		registeredRemoteObjectMap.put(name, object);
		logger.logMessage(LogLevel.DEBUG, "注册远程对象:{}结束", name);
	}

	public void registerRemoteObject(Remote object, Class type)
			throws RemoteException {
		registerRemoteObject(object, type.getName());
	}

	public void unregisterObject(Remote object) throws RemoteException {
		logger.logMessage(LogLevel.DEBUG, "开始注销对象object:{}", object);
		boolean flag = false;
		for (String name : registeredLocalObjectMap.keySet()) {
			Remote r = registeredLocalObjectMap.get(name);
			if (r.equals(object)) {
				unregisterLocalObject(name);
				flag = true;
				break;
			}
		}
		if (!flag) {
			for (String name : registeredRemoteObjectMap.keySet()) {
				Remote r = registeredRemoteObjectMap.get(name);
				if (r.equals(object)) {
					unregisterRemoteObject(name);
					flag = true;
					break;
				}
			}
		}
		if (!flag) {
			logger.logMessage(LogLevel.ERROR, "需要注销的对象object:{}不存在", object);
		}
		logger.logMessage(LogLevel.DEBUG, "注销对象object:{}完成", object);
	}

	private void unregisterLocalObject(String name) throws RemoteException {
		try {
			logger.logMessage(LogLevel.DEBUG, "开始注销本地对象:{}", name);
			registry.unbind(name);
			if (registeredLocalObjectMap.get(name) != null) {
				UnicastRemoteObject.unexportObject(
						registeredLocalObjectMap.get(name), true);
			}
			registeredLocalObjectMap.remove(name);
			if (server != null) {
				server.unregisterObject(name);
			}
			logger.logMessage(LogLevel.DEBUG, "注销本地对象:{}完成", name);
		} catch (Exception e) {
			logger.errorMessage("注销对象:{}时发生异常:{}！", e, name, e.getMessage());
		}
	}

	private void unregisterRemoteObject(String name) throws RemoteException {
		logger.logMessage(LogLevel.DEBUG, "开始注销远程对象:{}", name);
		registeredRemoteObjectMap.remove(name);
		logger.logMessage(LogLevel.DEBUG, "注销远程对象:{}完成", name);
	}

	public void unregisterObject(String name) throws RemoteException {
		logger.logMessage(LogLevel.DEBUG, "开始注销对象:{}", name);
		if (registeredLocalObjectMap.containsKey(name)) {
			unregisterLocalObject(name);
		} else if (registeredRemoteObjectMap.containsKey(name)) {
			unregisterRemoteObject(name);
		} else {
			logger.logMessage(LogLevel.ERROR, "需要注销的对象name:{}不存在", name);
		}
		logger.logMessage(LogLevel.DEBUG, "结束注销对象:{}", name);
	}

	public void unregisterObjectByType(Class type) throws RemoteException {
		logger.logMessage(LogLevel.DEBUG, "开始注销对象type:{}", type.getName());
		unregisterLocalObjectByType(type);
		unregisterRemoteObjecttByType(type);
		logger.logMessage(LogLevel.DEBUG, "注销对象type:{}完成", type.getName());
	}

	private void unregisterLocalObjectByType(Class type) throws RemoteException {
		String typeName = type.getName();
		for (String name : registeredLocalObjectMap.keySet()) {
			if (name.startsWith(typeName + "|")) { // 如果名称是以typeName打头
				unregisterLocalObject(name);
			} else {
				Object obj = registeredLocalObjectMap.get(name); // 如果名称不匹配再进行类型判断
				if (type.isAssignableFrom(obj.getClass())) {
					unregisterLocalObject(name);
				}
			}
		}
	}

	private void unregisterRemoteObjecttByType(Class type)
			throws RemoteException {
		String typeName = type.getName();
		for (String name : registeredRemoteObjectMap.keySet()) {
			if (name.startsWith(typeName + "|")) { // 如果名称是以typeName打头
				unregisterRemoteObject(name);
			} else {
				Object obj = registeredRemoteObjectMap.get(name); // 如果名称不匹配再进行类型判断
				if (type.isInstance(obj)) {// type.isAssignableFrom(obj.getClass())
					unregisterRemoteObject(name);
				}
			}

		}
	}

	public void unregisterObjectByType(String type) throws RemoteException {
		try {
			unregisterObjectByType(Class.forName(type));
		} catch (ClassNotFoundException e) {
			logger.errorMessage("注销类型为:{}的对象时发生异常:{}!", e, type, e.getMessage());
		}

	}

	public void unregisterObject(String type, String id) throws RemoteException {
		unregisterObject(getName(type, id));
	}

	public void unregisterObject(Class type, String id) throws RemoteException {
		unregisterObject(getName(type.getName(), id));
	}

	public <T> T getObject(String name) throws RemoteException {
		if (registeredRemoteObjectMap.containsKey(name)) {
			return (T) registeredRemoteObjectMap.get(name);
		}
		if (registeredLocalObjectMap.containsKey(name)) {
			return (T) registeredLocalObjectMap.get(name);
		}
		if (server != null) {
			return server.getObject(name);
		}
		return null;
	}

	public <T> T getObject(Class<T> type) throws RemoteException {
		for (String sName : registeredRemoteObjectMap.keySet()) {
			try {
				Remote object = getObject(sName);
				if (type.isInstance(object)) {
					return (T) object;
				}
			} catch (RemoteException e) {
				logger.errorMessage("获取对象Name:{}时出错", e, sName);
			}
		}
		for (String sName : registeredLocalObjectMap.keySet()) {
			try {
				Remote object = getObject(sName);
				if (type.isInstance(object)) {
					return (T) object;
				}
			} catch (RemoteException e) {
				logger.errorMessage("获取对象Name:{}时出错", e, sName);
			}
		}
		if (server != null) {
			return server.getObject(type);
		}
		return null;
	}

	private <T> void getObjectListInstanceOf(Class<T> type, List<T> result,
			Map<String, Remote> map) throws RemoteException {
		for (String sName : map.keySet()) {
			try {
				Remote object = getObject(sName);
				if (type.isInstance(object) && !result.contains(object)) {
					result.add((T) object);
				}
			} catch (RemoteException e) {
				logger.errorMessage("获取对象Name:{}时出错", e, sName);
			}
		}
	}

	public <T> List<T> getObjectList(Class<T> type) throws RemoteException {
		return getObjectList(type.getName());
	}

	private <T> void getObjectList(String typeName, List<T> result,
			Map<String, Remote> map) throws RemoteException {
		for (String sName : map.keySet()) {
			Object o = map.get(sName);
			if (result.contains(o)) {
				continue;
			}
			if (sName.startsWith(typeName + "|")) {
				result.add((T) o);
			} else if (o.getClass().toString().equals(typeName)) {
				result.add((T) o);
			}
		}
	}

	public <T> List<T> getObjectList(String typeName) throws RemoteException {
		List<T> result = new ArrayList<T>();
		getObjectList(typeName, result, registeredLocalObjectMap);
		getObjectList(typeName, result, registeredLocalObjectMap);
		if (server != null) {
			for (Object t : server.getObjectList(typeName)) {
				if (!result.contains(t)) {
					result.add((T) t);
				}
			}
		}
		return result;
	}

	public <T> List<T> getRemoteObjectListInstanceOf(Class<T> type)
			throws RemoteException {
		List<T> result = new ArrayList<T>();
		getObjectListInstanceOf(type, result, registeredLocalObjectMap);
		getObjectListInstanceOf(type, result, registeredRemoteObjectMap);
		if (server != null) {
			for (T t : server.getObjectList(type)) {
				if (!result.contains(t)) {
					result.add(t);
				}
			}
		}
		return result;
	}

	public void unexportObjects() throws RemoteException {
		for (String name : registeredLocalObjectMap.keySet()) {
			try {
				unregisterLocalObject(name);
			} catch (Exception e) {
				logger.errorMessage("注销对象name:{}时失败", e, name);
			}
		}
		for (String name : registeredRemoteObjectMap.keySet()) {
			try {
				unregisterRemoteObject(name);
			} catch (Exception e) {
				logger.errorMessage("注销对象name:{}时失败", e, name);
			}
		}
	}

	private String getName(String name, String id) throws RemoteException {
		return RmiUtil.getName(name, id);
	}

}
