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
package org.tinygroup.tinypc.impl;

import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.Work;
import org.tinygroup.tinypc.Worker;

import java.rmi.RemoteException;


/**
 * 抽象工人，建议所有的工人都继承自此类
 * Created by luoguo on 14-1-8.
 */
public abstract class AbstractWorker implements Worker {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6507469432280204341L;
	private String id;
    private String type;
    protected volatile boolean cancel = false;


    public void reset() {
        cancel = false;
    }

    public boolean isCancel() {
        return cancel;
    }

    /**
     * 默认全部接受
     *
     * @param work
     * @return
     */
    public boolean acceptWork(Work work) throws RemoteException {
        return true;
    }

    public AbstractWorker(String type) throws RemoteException {
        this.type = type;
        id = Util.getUuid();
    }


    public Warehouse work(Work work) throws RemoteException {
        if (work == null) {
            throw new RuntimeException("任务为空！");
        }
        if (!work.getType().equalsIgnoreCase(type)) {
            throw new RuntimeException((String.format("[%s]类型的工人不可以做[%s]类型的工作", type, work.getType())));
        }
        Warehouse outputWarehouse = doWork(work);
        return outputWarehouse;
    }

    /**
     * <b>真正的工作处理，在这里，如果提供取消工作需要对isCancel()的值进行判断，
     * 如果是true的时候，表示要停止处理，这个时候要立即返回</b>
     *
     * @param work
     * @throws RemoteException
     */
    abstract protected Warehouse doWork(Work work) throws RemoteException;

    public String getType() {
        return type;
    }

    public void cancel() {
        cancel = true;
    }


    public String getId() {
        return id;
    }
}
