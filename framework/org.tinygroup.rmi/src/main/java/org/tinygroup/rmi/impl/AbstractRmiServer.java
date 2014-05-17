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

import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.rmi.RemoteObjectDescription;
import org.tinygroup.rmi.RmiServer;

import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抽象rmi服务器 Created by luoguo on 14-1-10.
 */
public abstract class AbstractRmiServer extends UnicastRemoteObject implements RmiServer {
    private transient final static Logger logger = LoggerFactory
            .getLogger(AbstractRmiServer.class);
    int port = DEFAULT_RMI_PORT;
    String hostName = "localhost";
    Registry registry = null;
    Map<String, Remote> registeredObjectMap = new HashMap<String, Remote>();
//	RegisterThread regThread = new RegisterThread();

    public void stop() throws RemoteException {
        try {
            unexportObjects();
        } catch (RemoteException e) {
            logger.error(e);
        }
    }

    public AbstractRmiServer() throws RemoteException {
        this("localhost", DEFAULT_RMI_PORT);
    }

    public AbstractRmiServer(int port) throws RemoteException {
        this("localhost", port);
    }

    public AbstractRmiServer(String hostName, int port) throws RemoteException {
        this.hostName = hostName;
        this.port = port;
        getRegistry();
    }

    protected void registerAllRemoteObject() {
        for (String name : registeredObjectMap.keySet()) {
            try {
                registerRemoteObject(registeredObjectMap.get(name), name);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void registerRemoteObject(Remote object, String name) throws RemoteException {
        logger.logMessage(LogLevel.DEBUG, "将对象加入注册列表:{}", name);
        registry.rebind(name, object);
        logger.logMessage(LogLevel.DEBUG, "对象:{}加入注册列表完成", name);
    }

    public void registerObject(RemoteObjectDescription remoteObj) {
        String name = remoteObj.getName();
        Remote object = remoteObj.getObject();
        try {

            logger.logMessage(LogLevel.DEBUG, "开始注册对象:{}", name);
            registeredObjectMap.put(name, object);
            if (object instanceof UnicastRemoteObject) {
                registry.rebind(name, object);
            } else {
                Remote stub = UnicastRemoteObject.exportObject(object, 0);
                registry.rebind(name, stub);
            }
            logger.logMessage(LogLevel.DEBUG, "结束注册对象:{}", name);
        } catch (RemoteException e) {
            logger.errorMessage("注册对象:{}时发生异常:{}！", e, name, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void registerRemoteObject(Remote object, Class type) throws RemoteException {
        registerRemoteObject(object, type.getName());
    }

    public <T> T getRemoteObject(Class<T> type) throws RemoteException {
        return (T) getRemoteObject(type.getName());
    }

    public <T> T getRemoteObject(String name) throws RemoteException {
        try {
            logger.logMessage(LogLevel.DEBUG, "获取对象Name:{}", name);
            return (T) registry.lookup(name);
        } catch (ConnectException e) {
            throw new RuntimeException("获取对象Name:" + name + "时连接发生错误", e);
        } catch (NotBoundException e) {
            throw new RuntimeException("获取对象Name:" + name + "时出错,该对象未曾注册", e);
        }
    }

    public <T> List<T> getRemoteObjectList(Class<T> type) throws RemoteException {
        return getRemoteObjectList(type.getName());
    }

    public <T> List<T> getRemoteObjectListInstanceOf(Class<T> type) throws RemoteException {
        try {
            List<T> result = new ArrayList<T>();
            String[] sNames = getRemoteObjectNames();
            for (String sName : sNames) {
                Remote object = getRemoteObject(sName);
                if (type.isInstance(object)) {
                    result.add((T) object);
                }

            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("获取对象Type:" + type.getName() + "时出错", e);
        }
    }

    public <T> List<T> getRemoteObjectList(String name) throws RemoteException {
        try {
            List<T> result = new ArrayList<T>();
            for (String sName : registry.list()) {
                if (sName.startsWith(name + "|")) {
                    result.add((T) getRemoteObject(sName));
                }
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void registerRemoteObject(Remote object, Class type, String id) throws RemoteException {
        // /registerRemoteObject(object, type, id);
        // 20140214修改，原逻辑是无限递归死循环
        registerRemoteObject(object, type.getName(), id);
    }

    public void registerRemoteObject(Remote object, String type, String id) throws RemoteException {
        registerRemoteObject(object, getName(type, id));
    }

    public void unregisterRemoteObject(String type, String id) throws RemoteException {
        unregisterRemoteObject(getName(type, id));
    }

    public void unregisterRemoteObject(Class type, String id) throws RemoteException {
        unregisterRemoteObject(getName(type.getName(), id));
    }

    private String getName(String name, String id) {
        return RmiUtil.getName(name, id);
    }

    public void unregisterRemoteObject(Class type) throws RemoteException {
        unregisterRemoteObject(type.getName());
    }

    public void unregisterRemoteObject(String name) throws RemoteException {

        logger.logMessage(LogLevel.DEBUG, "开始注销对象:{}", name);
        try {
            registry.unbind(name);
            logger.logMessage(LogLevel.DEBUG, "结束注销对象:{}", name);
        } catch (NotBoundException e) {
            logger.errorMessage("注销对象:{}时发生异常:{}！", e, name, e.getMessage());
        }
    }

    public void unregisterObject(String name) {
        try {
            logger.logMessage(LogLevel.DEBUG, "开始注销对象:{}", name);
            registry.unbind(name);
            if (registeredObjectMap.get(name) != null) {
                UnicastRemoteObject.unexportObject(
                        registeredObjectMap.get(name), true);
            }
            logger.logMessage(LogLevel.DEBUG, "结束注销对象:{}", name);
        } catch (Exception e) {
            logger.errorMessage("注销对象:{}时发生异常:{}！", e, name, e.getMessage());
        }
    }

    public void unregisterRemoteObjectByType(Class type) throws RemoteException {
        unregisterRemoteObject(type.getName());
    }

    public void unregisterRemoteObjectByType(String type) throws RemoteException {
        try {
            for (String name : registry.list()) {
                if (name.startsWith(type + "|")) {
                    unregisterRemoteObject(name);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void unexportObjects() throws RemoteException {
        for (Map.Entry<String, Remote> entry : registeredObjectMap.entrySet()) {
            unregisterObject(entry.getKey());
        }
    }

    public void unregisterRemoteObject(Remote object) throws RemoteException {
        for (String name : registry.list()) {
            Remote obj = getRemoteObject(name);
            if (obj.equals(object)) {
                unregisterRemoteObject(name);
                break;
            }
        }
    }

    private String[] getRemoteObjectNames() {
        try {
            return registry.list();
        } catch (Exception e) {
            throw new RuntimeException("查询所有远程对象时出错", e);
        }
    }
}
