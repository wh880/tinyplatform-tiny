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
package org.tinygroup.tinypc.rmi;

import org.tinygroup.rmi.RmiServer;
import org.tinygroup.rmi.impl.RmiServerLocal;
import org.tinygroup.rmi.impl.RmiServerRemote;

import junit.framework.TestCase;

/**
 * Created by luoguo on 14-1-24.
 */
public class RmiServerTest extends TestCase {
    RmiServer localServer;
    RmiServer remoteServer;

    public void setUp() throws Exception {
        super.setUp();
        localServer = new RmiServerLocal();
        remoteServer = new RmiServerRemote();

    }

    public void tearDown() throws Exception {
        super.tearDown();
        localServer.unexportObjects();
        remoteServer.unexportObjects();
    }

    public void testGetRegistry() throws Exception {
        localServer.registerRemoteObject(new HelloImpl(), "hello");
        Thread.sleep(100);
        assertEquals(localServer.getRegistry().list().length, 2);
        assertEquals(remoteServer.getRegistry().list().length, 2);
        Hello hello = remoteServer.getRemoteObject("hello");
        String info = hello.sayHello("abc");
        assertEquals(info, "Hello,abc");

        remoteServer.registerRemoteObject(new HelloImpl(), "hello1");

        Thread.sleep(100);

        assertEquals(localServer.getRegistry().list().length, 3);
        assertEquals(remoteServer.getRegistry().list().length, 3);
        hello = localServer.getRemoteObject("hello1");
        info = hello.sayHello("def");
        assertEquals(info, "Hello,def");
    }

    public void testRegisterRemoteObject() throws Exception {

    }

    public void testRegisterRemoteObject1() throws Exception {

    }

    public void testRegisterRemoteObject2() throws Exception {

    }

    public void testRegisterRemoteObject3() throws Exception {

    }

    public void testUnregisterRemoteObject() throws Exception {

    }

    public void testUnregisterRemoteObjectByType() throws Exception {

    }

    public void testUnregisterRemoteObjectByType1() throws Exception {

    }

    public void testUnregisterRemoteObject1() throws Exception {

    }

    public void testUnregisterRemoteObject2() throws Exception {

    }

    public void testGetRemoteObject() throws Exception {

    }

    public void testGetRemoteObject1() throws Exception {

    }

    public void testGetRemoteObjectList() throws Exception {

    }

    public void testGetRemoteObjectListInstanceOf() throws Exception {

    }

    public void testGetRemoteObjectList1() throws Exception {

    }

    public void testUnexportObjects() throws Exception {

    }
}
