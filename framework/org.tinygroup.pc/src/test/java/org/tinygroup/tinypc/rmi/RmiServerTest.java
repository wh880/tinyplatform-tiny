package org.tinygroup.tinypc.rmi;

import org.tinygroup.tinyrmi.RmiServer;
import org.tinygroup.tinyrmi.impl.RmiServerLocal;
import org.tinygroup.tinyrmi.impl.RmiServerRemote;

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
        assertEquals(localServer.getRegistry().list().length, 1);
        assertEquals(remoteServer.getRegistry().list().length, 1);
        Hello hello = remoteServer.getRemoteObject("hello");
        String info = hello.sayHello("abc");
        assertEquals(info, "Hello,abc");

        remoteServer.registerRemoteObject(new HelloImpl(), "hello1");
        assertEquals(localServer.getRegistry().list().length, 2);
        assertEquals(remoteServer.getRegistry().list().length, 2);
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
