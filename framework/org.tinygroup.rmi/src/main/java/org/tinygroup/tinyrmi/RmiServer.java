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
package org.tinygroup.tinyrmi;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.List;

/**
 * RMI服务器接口
 * Created by luoguo on 14-1-10.
 */
public interface RmiServer {
    int DEFAULT_RMI_PORT = 8828;

    /**
     * 返回远程对象注册表
     *
     * @return
     */
    Registry getRegistry();

    /**
     * 注册并行对象，类型及ID
     *
     * @param type   注册的对象类型，可以重复
     * @param id     注册的对象的ID，相同类型不可以重复，不同类型可以重复
     * @param object
     */
    void registerRemoteObject(Remote object, Class type, String id);

    /**
     * 注册并行对象
     *
     * @param type   注册的对象类型，可以重复
     * @param id     注册的对象的ID，相同类型不可以重复，不同类型可以重复
     * @param object
     */
    void registerRemoteObject(Remote object, String type, String id);

    /**
     * 注册远程对象
     *
     * @param name   名字如果重复，已经存在的对象将被替换，全局唯一
     * @param object
     */
    void registerRemoteObject(Remote object, String name);

    /**
     * 按类型名称注册远程对象,如果此类型已经存在对象，则已经存在的对象将被替换
     *
     * @param type
     * @param object
     */
    void registerRemoteObject(Remote object, Class type);


    /**
     * 根据名称注销远程对象
     *
     * @param name 要注销的对象名
     */
    void unregisterRemoteObject(String name);

    /**
     * 根据类型注销远程对象
     *
     * @param type 要注销的对象类型，所有匹配的对象都会被注销
     */
    void unregisterRemoteObjectByType(Class type);

    /**
     * 根据类型注销远程对象
     *
     * @param type
     */
    void unregisterRemoteObjectByType(String type);

    /**
     * 根据类型注销远程对象
     *
     * @param type 要注销的类型
     * @param id   要注销的ID
     */
    void unregisterRemoteObject(String type, String id);

    /**
     * 根据类型注销远程对象
     *
     * @param type 要注销的类型
     * @param id   要注销的ID
     */
    void unregisterRemoteObject(Class type, String id);


    /**
     * 返回远程对象
     *
     * @param name
     * @param <T>
     * @return
     * @throws RemoteException
     */
    <T> T getRemoteObject(String name) throws RemoteException;

    /**
     * 根据类型获取对象
     *
     * @param type
     * @param <T>
     * @return
     * @throws RemoteException
     */
    <T> T getRemoteObject(Class<T> type) throws RemoteException;

    /**
     * 根据类型返回远程对象列表
     *
     * @param type
     * @param <T>
     * @return
     */
    <T> List<T> getRemoteObjectList(Class<T> type);

    /**
     * 返回某种类型的子类对象列表
     *
     * @param type
     * @param <T>
     * @return
     */
    <T> List<T> getRemoteObjectListInstanceOf(Class<T> type);

    /**
     * 根据类型返回远程对象列表
     *
     * @param typeName
     * @param <T>
     * @return
     */
    <T> List<T> getRemoteObjectList(String typeName);

    /**
     * 停止所有提供远程访问的对象，会把注册在此远程服务中心的远程对象全部停止
     *
     * @throws RemoteException
     */
    void unexportObjects() throws RemoteException;

    /**
     * 停止具体的注册在此远程服务中心的本地对象
     *
     * @param object
     * @throws RemoteException
     */
    void unregisterRemoteObject(Remote object) throws RemoteException;
}
