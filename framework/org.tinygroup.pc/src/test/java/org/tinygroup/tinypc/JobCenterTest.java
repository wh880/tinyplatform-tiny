package org.tinygroup.tinypc;

import junit.framework.TestCase;

import org.tinygroup.tinyrmi.RmiServer;
import org.tinygroup.tinyrmi.impl.RmiServerLocal;
import org.tinygroup.tinyrmi.impl.RmiServerRemote;

/**
 * Created by luoguo on 14-1-24.
 */
public class JobCenterTest extends TestCase {
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
