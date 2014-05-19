package org.tinygroup.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;

/**
 * Created by luoguo on 2014/5/19.
 */
public class RemoteRmiHeartBeatChecker implements Runnable {
    private final Registry registry;
    private int interval;
    private transient boolean stop = false;

    public RemoteRmiHeartBeatChecker(Registry registry, int interval) {
        this.registry = registry;
        this.interval = interval;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public void run() {
        boolean isDown = false;
        while (!stop) {
            try {
                registry.list();//检查registry是否可用
                //如果可用
                if (isDown) {

                }
            } catch (RemoteException e) {
                isDown = true;
            }
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                //DoNothing
            }
        }
    }
}
