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
package org.tinygroup.commons.cpu;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * 返回当前java进程占用的CPU情况
 *
 * @author luoguo
 */
public final class MonitorUtil {
    public static final int INTERVAL_TIME = 1000;
    static OperatingSystemMXBean osbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    static RuntimeMXBean runbean = (RuntimeMXBean) ManagementFactory.getRuntimeMXBean();
    static long beforeProcessTime = osbean.getProcessCpuTime();
    static long beforeUpTime = runbean.getUptime();
    static long lastGetTime = System.currentTimeMillis();
    static double cpuUage = 0;

    /*
    * 获取CPU利用率
    */
    public static double getCpuUsage() {
        return getCpuUsage(INTERVAL_TIME);
    }

    /**
     * 获取CPU利用率
     *
     * @param intervalTime 间隔时间,单位ms，如果没有超过间隔时间，则取上次取到的数据，如果有超过则重新计算
     * @return
     */
    public static double getCpuUsage(int intervalTime) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastGetTime >= intervalTime) {
            long afterProcessTime = osbean.getProcessCpuTime();
            long afterUpTime = runbean.getUptime();
            double cal = (afterProcessTime - beforeProcessTime) / ((afterUpTime - beforeUpTime) * 10000f);
            cpuUage = Math.min(100f, cal);
            beforeProcessTime = afterProcessTime;
            beforeUpTime = beforeUpTime;
            lastGetTime = currentTime;
        }
        return cpuUage;
    }

    public static void main(String[] args) {
        while (true) {
            long sum = 0;
            for (long s = 0; s < 999999; s++) {
                sum += s * s;
            }
            System.out.println(MonitorUtil.getCpuUsage());
        }
    }
}
