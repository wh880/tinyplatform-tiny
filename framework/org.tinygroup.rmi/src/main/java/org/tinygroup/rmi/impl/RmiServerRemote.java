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

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * 远程RMI服务器
 * Created by luoguo on 14-1-10.
 */
public class RmiServerRemote extends AbstractRmiServer {
    private final static Logger logger = LoggerFactory.getLogger(RmiServerRemote.class);
    HeartThread heartThread = new HeartThread();

    public RmiServerRemote() {
        super();
        heartThread.start();
    }

    public RmiServerRemote(int port) {
        super(port);
        heartThread.start();
    }

    public RmiServerRemote(String hostName, int port) {
        super(hostName, port);
        heartThread.start();
    }

    public Registry getRegistry() {
        if (registry == null) {
            try {
                registry = LocateRegistry.getRegistry(hostName, port);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return registry;
    }

    public void stop() {
        heartThread.setStop(true);
        super.stop();

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
                    logger.logMessage(LogLevel.DEBUG, "开始向远端服务器{0}:{1}发起心跳检测", hostName, port);
                    registry.list();
                    if (isDown) {
                        logger.logMessage(LogLevel.INFO, "开始重新向远端服务器{0}:{1}注册对象", hostName, port);
                        registerAllRemoteObject();
                        logger.logMessage(LogLevel.INFO, "重新向远端服务器{0}:{1}注册对象结束", hostName, port);
                        isDown = false;
                    }
                    logger.logMessage(LogLevel.DEBUG, "向远端服务器{0}:{1}心跳检测结束", hostName, port);
                } catch (RemoteException e) {
                    logger.errorMessage("向远端服务器{0}:{1}心跳检测失败", e, hostName, port);
                    isDown = true;
                }
            }

        }
    }
}
