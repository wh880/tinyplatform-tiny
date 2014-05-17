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
package org.tinygroup.tinypc.sum;

import org.tinygroup.tinypc.Foreman;
import org.tinygroup.tinypc.JobCenter;
import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.Work;
import org.tinygroup.tinypc.impl.*;

import java.io.IOException;

/**
 * Created by luoguo on 14-1-8.
 */
public class Test {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        JobCenter jobCenter = new JobCenterLocal("localhost",8888);
        JobCenter center = new JobCenterRemote("localhost",8888);
        for (int i = 0; i < 5; i++) {
            center.registerWorker(new WorkerSum());
        }
        Foreman helloForeman = new ForemanSelectAllWorker("sum", new SumSplitterCombiner());
        center.registerForeman(helloForeman);
        Warehouse inputWarehouse = new WarehouseDefault();
        inputWarehouse.put("start", 1l);
        inputWarehouse.put("end", 10000l);
        Work work = new WorkDefault("sum", inputWarehouse);
        Warehouse outputWarehouse = center.doWork(work);
        System.out.println(outputWarehouse.get("sum"));
        center.stop();
        jobCenter.stop();
    }
}
