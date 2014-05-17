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
package org.tinygroup.rmi.test;

import org.tinygroup.rmi.RmiServer;
import org.tinygroup.rmi.impl.RmiServerRemote;

import java.rmi.RemoteException;

public class RmiRunClient {
    private static String SERVERIP = "192.168.84.30";

    public static void main(String[] args) throws RemoteException {
        RmiServer remoteServer = new RmiServerRemote();
        remoteServer.registerRemoteObject(new HelloImpl(), "hello1");
        Hello hello = remoteServer.getRemoteObject("hello");
        Hello hello1 = remoteServer.getRemoteObject("hello1");
        System.out.println(hello.sayHello("abc"));
        System.out.println(hello1.sayHello("abc1"));
        remoteServer.stop();
    }
 }
