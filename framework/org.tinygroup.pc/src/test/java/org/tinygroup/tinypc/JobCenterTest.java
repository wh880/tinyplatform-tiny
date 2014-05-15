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
package org.tinygroup.tinypc;

import junit.framework.TestCase;

import org.tinygroup.rmi.RmiServer;
import org.tinygroup.rmi.impl.RmiServerLocal;
import org.tinygroup.rmi.impl.RmiServerRemote;

/**
 * Created by luoguo on 14-1-24.
 */
public class JobCenterTest extends TestCase {
    RmiServer localServer;
    RmiServer remoteServer;

    public void setUp() throws Exception {
        super.setUp();
        localServer = new RmiServerLocal();
        remoteServer = new RmiServerRemote("localhost",8888);
    }

    public void tearDown() throws Exception {
        super.tearDown();
        localServer.unexportObjects();
        remoteServer.unexportObjects();
    }

    public void testGetRmiServer() throws Exception {

    }

    public void testSetRmiServer() throws Exception {

    }

    public void testRegisterWorker() throws Exception {

    }

    public void testGetWorkQueue() throws Exception {

    }

    public void testUnregisterWorker() throws Exception {

    }

    public void testRegisterWork() throws Exception {

    }

    public void testUnregisterWork() throws Exception {

    }

    public void testGetWorkStatus() throws Exception {

    }

    public void testDoWork() throws Exception {

    }

    public void testRegisterForeman() throws Exception {

    }

    public void testUnregisterForeMan() throws Exception {

    }

    public void testGetIdleWorkerList() throws Exception {

    }

    public void testGetWorkList() throws Exception {

    }

    public void testGetWorkList1() throws Exception {

    }

    public void testGetIdleForeman() throws Exception {

    }

    public void testAutoMatch() throws Exception {

    }
}
