package org.tinygroup.tinypc.impl;

import org.tinygroup.tinypc.WorkQueue;
import org.tinygroup.rmi.impl.RmiServerLocal;

import java.io.IOException;

/**
 * Created by luoguo on 14-1-23.
 */
public class JobCenterLocal extends AbstractJobCenter {
    public JobCenterLocal() throws IOException {
        this(DEFAULT_PORT);
    }

    public JobCenterLocal(int port) throws IOException {
        setRmiServer(new RmiServerLocal(port));
        WorkQueue workQueue = new WorkQueueImpl();
        setWorkQueue(workQueue);
        getRmiServer().registerRemoteObject(workQueue, "WorkQueue");
    }
}
