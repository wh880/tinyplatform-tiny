package org.tinygroup.rmi;

import java.rmi.RemoteException;

/**
 * 是否可验证,实现了此接口的类，可以进行校验
 */
public interface Verifiable {
    /**
     * 校验，如果校验时不出现异常，就表示是OK的
     *
     * @throws RemoteException
     */
    void verify() throws RemoteException;
}
