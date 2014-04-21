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
import org.tinygroup.rmi.Verifiable;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * 本地Rmi服务器 Created by luoguo on 14-1-10.
 */
public final class RmiServerLocal extends AbstractRmiServer {
    private final static Logger logger = LoggerFactory.getLogger(RmiServerRemote.class);
    private ValidateThread validateThread = new ValidateThread();

    public RmiServerLocal() {
        super();
        validateThread.start();
    }

    public RmiServerLocal(int port) {
        super(port);
        validateThread.start();
    }

    public RmiServerLocal(String hostName, int port) {
        super(hostName, port);
        validateThread.start();
    }

    public Registry getRegistry() {
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
        return registry;
    }

    public void stop() {
        validateThread.setStop(true);
        super.stop();
    }

    class ValidateThread extends Thread {
        private static final int MILLISECOND_PER_SECOND = 1000;
        private volatile boolean stop = false;
        private int breathInterval = 5;//单位秒

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
                                unregisterRemoteObject(remote);
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
}
