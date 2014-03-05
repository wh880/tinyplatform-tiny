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
package org.tinygroup.tinypc.dfile;

import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.Work;
import org.tinygroup.tinypc.impl.AbstractWorker;
import org.tinygroup.tinypc.impl.WarehouseDefault;

import java.io.File;
import java.rmi.RemoteException;

/**
 * Created by luoguo on 14-1-8.
 */
public class WorkerFile extends AbstractWorker {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2563140489903993098L;
	private final String namePattern;

    public WorkerFile(String includePattern) throws RemoteException {
        super("file");
        namePattern = includePattern;
    }

    /**
     * 默认全部接受
     *
     * @param work
     * @return
     */
    public boolean acceptWork(Work work) throws RemoteException {
        String fileName = work.getInputWarehouse().get("fileName");
        return fileName.indexOf(fileName) >= 0;
    }


    public Warehouse doWork(Work work) throws RemoteException {
        String fileName = work.getInputWarehouse().get("fileName");
        System.out.println(String.format("NamePattern:%s FileName %s", namePattern, getId(), fileName));
        Warehouse outputWarehouse = new WarehouseDefault();
        outputWarehouse.put("subFiles", new File(fileName).listFiles());
        return outputWarehouse;
    }


}
