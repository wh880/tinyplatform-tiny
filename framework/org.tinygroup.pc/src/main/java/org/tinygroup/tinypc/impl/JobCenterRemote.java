package org.tinygroup.tinypc.impl;

import org.tinygroup.tinypc.WorkQueue;
import org.tinygroup.rmi.impl.RmiServerRemote;

import java.io.IOException;

/**
 * Created by luoguo on 14-1-23.
 */
public class JobCenterRemote extends AbstractJobCenter {

    public JobCenterRemote() throws IOException {
        this("localhost", DEFAULT_PORT);
    }

    public JobCenterRemote(String hostName, int port) throws IOException {
        setRmiServer(new RmiServerRemote(hostName, port));
        setWorkQueue((WorkQueue) getRmiServer().getRemoteObject("WorkQueue"));
    }
}
