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

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * 本地Rmi服务器 Created by luoguo on 14-1-10.
 */
public final class RmiServerLocal extends AbstractRmiServer {
    private transient final static Logger logger = LoggerFactory
            .getLogger(RmiServerRemote.class);

    public RmiServerLocal() throws RemoteException {
        super();
    }

    public RmiServerLocal(int port) throws RemoteException {
        super(port);
    }

    public RmiServerLocal(String hostName, int port) throws RemoteException {
        super(hostName, port);
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
        registerLocalObject(this, "RmiServer");

        return registry;
    }

    public void stop() throws RemoteException {
        super.stop();
    }

    public void registerLocalObject(Remote object, String name) throws RemoteException {
        logger.logMessage(LogLevel.DEBUG, "开始注册对象:{}", name);
        registeredObjectMap.put(name, object);
        if (object instanceof UnicastRemoteObject) {
            registry.rebind(name, object);
        } else {
            Remote stub = UnicastRemoteObject.exportObject(object, 0);
            registry.rebind(name, stub);
        }
        logger.logMessage(LogLevel.DEBUG, "结束注册对象:{}", name);
    }
}
