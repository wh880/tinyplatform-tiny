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
package org.tinygroup.tinypc.helloforeach.rmi;

import org.tinygroup.tinypc.Foreman;
import org.tinygroup.tinypc.JobCenter;
import org.tinygroup.tinypc.helloforeach.HelloWorkCombiner;
import org.tinygroup.tinypc.impl.ForemanSelectAllWorker;
import org.tinygroup.tinypc.impl.JobCenterLocal;

public class TestServer {
    // private static String LOCALIP = "192.168.84.52";

    public static void main(String[] args) {
        try {
            JobCenter jobCenter = new JobCenterLocal("192.168.84.23", 8888);
            Foreman helloForeman = new ForemanSelectAllWorker("hello",
                    new HelloWorkCombiner());
            jobCenter.registerForeman(helloForeman);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TestServer r = new TestServer();
        r.runThread();
    }

    public void runThread() {
        MyThread t = new TestServer.MyThread();
        t.run();
    }

    class MyThread extends Thread {
        private boolean end = false;

        public void run() {
            if (!end) {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
